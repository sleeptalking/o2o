package com.zx.o2o.service.impl;

import com.zx.o2o.dao.ProductDao;
import com.zx.o2o.dao.ProductImgDao;
import com.zx.o2o.dto.ImageHolder;
import com.zx.o2o.dto.ProductExecution;
import com.zx.o2o.entity.Product;
import com.zx.o2o.entity.ProductImg;
import com.zx.o2o.enums.ProductStateEnum;
import com.zx.o2o.exceptions.ProductOperationExceptions;
import com.zx.o2o.service.ProductService;
import com.zx.o2o.util.ImageUtils;
import com.zx.o2o.util.PageCalculator;
import com.zx.o2o.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;
    @Autowired
    ProductImgDao productImgDao;

    //1.处理缩略图，获取缩略图相对路径并赋值给product
    //2.往tb_product表写入商品信息，获取productId
    //3.集合productId处理商品详情图片
    //4.将商品详情图片列表批量插入tb_product_img中
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationExceptions {

        if(product != null && product.getShop() != null &&product.getShop().getShopId() != null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);

            //若商品缩略图不为空添加商品缩略图
            if(thumbnail != null){
                addThumbnail(product,thumbnail);
            }

            //添加商品
            try{
                int effectedNum = productDao.insertProduct(product);
                if(effectedNum <= 0){
                    throw new ProductOperationExceptions("创建商品失败");
                }
            }catch (Exception e){
                throw new ProductOperationExceptions("ProductOperationExceptions :"+e.getMessage());
            }

            //添加商品详情
            if(productImgList != null && productImgList.size() > 0){
                addProductImgList(product,productImgList);
            }

            return new ProductExecution(ProductStateEnum.SUCCESS,product);

        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }

    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex,pageSize);
        List<Product> list = productDao.queryProductList(productCondition,rowIndex,pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        if(list != null){
            pe.setProductList(list);
            pe.setCount(count);
            pe.setState(ProductStateEnum.SUCCESS.getState());
            pe.setStateInfo(ProductStateEnum.SUCCESS.getStateInfo());
        }else{
            pe.setState(ProductStateEnum.INNER_ERROR.getState());
            pe.setStateInfo(ProductStateEnum.INNER_ERROR.getStateInfo());
        }
        return pe;
    }


    /**
     * 批量添加图片
     * @param product
     * @param productImgList
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgList) {
        String dest = PathUtils.getShopImagePath(product.getShop().getShopId());

        List<ProductImg> productImgs = new ArrayList<>();
        for(ImageHolder imageHolder : productImgList){
            String thumbnailAddr = ImageUtils.generateThumbnail(imageHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setProductId(product.getProductId());
            productImg.setImgAddr(thumbnailAddr);
            productImg.setCreateTime(new Date());
            productImgs.add(productImg);
        }

        if(productImgs.size() > 0){
            try{
                int effectedNum = productImgDao.batchInsertProductImg(productImgs);
                if(effectedNum <= 0){
                    throw  new ProductOperationExceptions("创建商品详情图片失败");
                }
            }catch (Exception e){
                throw  new ProductOperationExceptions("创建商品详情图片失败 ："+e.getMessage());
            }
        }
    }

    /**
     * 添加缩略图
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtils.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtils.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);
    }

}
