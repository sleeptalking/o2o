package com.zx.o2o.dao;

import com.zx.o2o.BaseTest;
import com.zx.o2o.entity.Product;
import com.zx.o2o.entity.ProductCategory;
import com.zx.o2o.entity.Shop;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ProductDaoTest extends BaseTest {

    @Autowired
    ProductDao productDao;

    @Test
    @Ignore
    public void testInsertProduct(){
        Product product = new Product();
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1描述");
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setNormalPrice("10.0");
        product.setPromotionPrice("8.0");
        product.setImgAddr("/shop/html");
        product.setPriority(1);
        Shop shop = new Shop();
        shop.setShopId(1l);
        product.setShop(shop);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(2l);
        product.setProductCategory(pc);
        int num = productDao.insertProduct(product);
        System.out.println("num  :"+num);
    }

    @Test
    public void testQueryProductList(){
        Product product = new Product();
        product.setProductName("2");
        product.setEnableStatus(1);
        Shop shop = new Shop();
        shop.setShopId(1l);
        product.setShop(shop);
        List<Product> list = productDao.queryProductList(product,0,100);
        System.out.println(list.size());
    }

}
