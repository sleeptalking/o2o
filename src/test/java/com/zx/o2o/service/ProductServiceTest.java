package com.zx.o2o.service;

import com.zx.o2o.BaseTest;
import com.zx.o2o.dto.ImageHolder;
import com.zx.o2o.dto.ProductExecution;
import com.zx.o2o.entity.Product;
import com.zx.o2o.entity.Shop;
import com.zx.o2o.enums.ProductStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceTest extends BaseTest {

    @Autowired
    ProductService productService;
    @Test
    public void testAddProduct() throws FileNotFoundException {
        Product product = new Product();
        product.setProductName("测试商品2");
        product.setProductDesc("测试商品2描述");
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        product.setNormalPrice("10.0");
        product.setPromotionPrice("8.0");
        product.setPriority(1);
        Shop shop = new Shop();
        shop.setShopId(1l);
        product.setShop(shop);
        File file1 = new File("D:\\t1.jpg");
        InputStream is1 = new FileInputStream(file1);
        ImageHolder imageHolder = new ImageHolder(file1.getName(),is1);
        File file2 = new File("D:\\t2.jpg");
        InputStream is2 = new FileInputStream(file2);
        ImageHolder imageHolder1 = new ImageHolder(file2.getName() ,is2);
        File file3 = new File("D:\\t3.jpg");
        InputStream is3 = new FileInputStream(file3);
        ImageHolder imageHolder2 = new ImageHolder(file3.getName(),is3);

        List<ImageHolder> list = new ArrayList<>();
        list.add(imageHolder1);
        list.add(imageHolder2);
        ProductExecution productExecution = productService.addProduct(product,imageHolder,list);
        System.out.println(productExecution.getState());
        System.out.println(productExecution.getStateInfo());
    }



}
