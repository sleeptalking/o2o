package com.zx.o2o.dao;

import com.zx.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Property;

import java.util.List;

public interface ProductCategoryDao {
    /**
     * 通过shop id查询店铺商品分类
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryByShopId(@Param("shopId")long shopId);

    /**
     * 批量添加店铺商品分类
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);


    /**
     * 通过商品类别id和店铺id删除商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId")long shopId);
}
