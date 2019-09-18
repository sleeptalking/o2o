package com.zx.o2o.dao;

import com.zx.o2o.entity.Product;

public interface ProductDao {

    /**
     * 添加商品
     * @param product
     * @return
     */
    int insertProduct(Product product);
}
