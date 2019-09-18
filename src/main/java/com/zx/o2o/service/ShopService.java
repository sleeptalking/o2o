package com.zx.o2o.service;

import com.zx.o2o.dto.ImageHolder;
import com.zx.o2o.dto.ShopExecution;
import com.zx.o2o.entity.Shop;
import com.zx.o2o.exceptions.ShopOperationExceptions;

import java.io.InputStream;

public interface ShopService {

    /**
     * 根据 shopCondition 分页返回相应店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
    /**
     * 添加店铺信息
     * @param shop
     * @param imageHolder
     * @return
     * @throws ShopOperationExceptions
     */
    ShopExecution addShop(Shop shop, ImageHolder imageHolder) throws ShopOperationExceptions;

    /**
     * 通过id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(Long shopId);

    /**
     * 更新店铺信息，包括图片的处理
     * @param shop
     * @param imageHolder
     * @return
     * @throws ShopOperationExceptions
     */
    ShopExecution modityShop(Shop shop, ImageHolder imageHolder) throws ShopOperationExceptions;
}
