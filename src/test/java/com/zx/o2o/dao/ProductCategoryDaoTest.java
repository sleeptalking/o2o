package com.zx.o2o.dao;

import com.zx.o2o.BaseTest;
import com.zx.o2o.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    ProductCategoryDao productCategoryDao;

    @Test
    public void testBQueryProductCategoryByShopId() {
        List<ProductCategory> list = productCategoryDao.queryProductCategoryByShopId(2l);
        System.out.println(list.size());
    }

    @Test
    public void testABatchInsertProductCategory() {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商品类别1");
        productCategory.setProductCategoryDesc("测试商品类别");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(1L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别2");
        productCategory2.setProductCategoryDesc("测试商品类别2");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);

        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2, effectedNum);
    }


    @Test
    public void testCDeleteProductCategory() throws Exception{
        List<ProductCategory> list = productCategoryDao.queryProductCategoryByShopId(1l);
        for(ProductCategory pc : list){
            if("商品类别1".equals(pc.getProductCategoryName()) || "商品类别2".equals(pc.getProductCategoryName())){
                int num = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(),1L);
                assertEquals(1,num);
            }
        }

    }


}
