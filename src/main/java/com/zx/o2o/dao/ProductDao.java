package com.zx.o2o.dao;

import com.zx.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    /**
     * 添加商品
     * @param product
     * @return
     */
    int insertProduct(Product product);


    /**
     *查询商品列表 可输入的条件有个 ：分页 、商品名称（模糊查询）、商品分类、店铺信息、商品状态
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,
                                   @Param("rowIndex") int rowIndex,@Param("pageSize")int pageSize);

    /**
     *
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

}
