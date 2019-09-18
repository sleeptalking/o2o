package com.zx.o2o.service;

import com.zx.o2o.BaseTest;
import com.zx.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoryServiceTest extends BaseTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void testGetProductCategoryByShopId(){

        List<ProductCategory> list = productCategoryService.getProductCategoryList(1l);
        System.out.println(list.size());

    }
}
