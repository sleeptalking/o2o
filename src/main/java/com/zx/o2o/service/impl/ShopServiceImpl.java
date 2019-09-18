package com.zx.o2o.service.impl;

import com.zx.o2o.dao.ShopDao;
import com.zx.o2o.dto.ImageHolder;
import com.zx.o2o.dto.ShopExecution;
import com.zx.o2o.entity.Shop;
import com.zx.o2o.enums.ShopStateEnum;
import com.zx.o2o.exceptions.ShopOperationExceptions;
import com.zx.o2o.service.ShopService;
import com.zx.o2o.util.ImageUtils;
import com.zx.o2o.util.PageCalculator;
import com.zx.o2o.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;
    Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if(shopList != null){
            se.setShopList(shopList);
            se.setCount(count);
        }else{
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    public ShopExecution addShop(Shop shop, ImageHolder imageHolder) {

        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try {
            //给店铺信息赋初始值
            shop.setEnableStatus(ShopStateEnum.CHECK.getState());
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            logger.info("effectedNum:" + effectedNum);
            if (effectedNum <= 0) {
                throw new ShopOperationExceptions("店铺创建失败");
            } else {
                if (imageHolder.getImage() != null) {
                    try {
                        //存储店铺图片
                        addShopImg(shop, imageHolder);
                    } catch (Exception e) {
                        throw new ShopOperationExceptions("添加图片失败");
                    }

                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationExceptions("更新图片地址失败");
                    }
                }
            }

        } catch (Exception e) {
            throw new ShopOperationExceptions("addshop error:" + e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, ImageHolder imageHolder) {
        //获取shop图片目录的相对值路径
        String dest = PathUtils.getShopImagePath(shop.getShopId());
        logger.info("dest:" + dest.toString());

        String shopImgAddr = ImageUtils.generateThumbnail(imageHolder, dest);

        logger.info("shopImgAddr:" + shopImgAddr.toString());

        shop.setShopImg(shopImgAddr);
    }

    @Override
    public Shop getByShopId(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modityShop(Shop shop,ImageHolder imageHolder) throws ShopOperationExceptions {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            try {
                //1.判断是否需要处理图片
                if (imageHolder.getImage() != null && imageHolder.getImageName() != null && !"".equals(imageHolder.getImageName())) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtils.deleteFileOrpat(tempShop.getShopImg());
                    }
                    addShopImg(shop, imageHolder);
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationExceptions("modityShop error:" + e.getMessage());
            }
        }
    }

}
