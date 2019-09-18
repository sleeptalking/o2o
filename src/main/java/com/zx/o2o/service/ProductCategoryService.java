package com.zx.o2o.service;

import com.zx.o2o.dto.ProductCategoryExecution;
import com.zx.o2o.entity.ProductCategory;
import com.zx.o2o.exceptions.ProductCategoryOperationExceptions;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 查询某个指定店铺下的商品分类信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(Long shopId);

    /**
     *
     *
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationExceptions
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationExceptions;


    /**
     * 删除商品类别的同时也要清空该商品类别下的商品
     * @param productCategory
     * @param shopId
     * @return
     */
    ProductCategoryExecution deleteProductCategory(long productCategory,long shopId)throws ProductCategoryOperationExceptions;



}
