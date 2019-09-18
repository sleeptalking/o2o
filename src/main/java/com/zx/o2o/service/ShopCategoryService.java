package com.zx.o2o.service;

import com.zx.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    List<ShopCategory> getShopCategory(ShopCategory shopCategoryCondition);
}
