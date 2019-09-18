package com.zx.o2o.dao;

import com.zx.o2o.BaseTest;
import com.zx.o2o.entity.Product;
import com.zx.o2o.entity.ProductCategory;
import com.zx.o2o.entity.ProductImg;
import com.zx.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductImgDaoTest extends BaseTest {

    @Autowired
    ProductImgDao productImgDao;

    @Test
    public void testInsertProductImg(){
        List<ProductImg> list = new ArrayList<>();

        ProductImg productImg = new ProductImg();
        productImg.setCreateTime(new Date());
        productImg.setImgAddr("/shop/s");
        productImg.setImgDesc("详情图片");
        productImg.setProductId(17l);
        productImg.setPriority(1);
        ProductImg productImg1 = new ProductImg();
        productImg1.setCreateTime(new Date());
        productImg1.setImgAddr("/shop/s1");
        productImg1.setImgDesc("详情图片1");
        productImg1.setProductId(17l);
        productImg1.setPriority(2);
        list.add(productImg);
        list.add(productImg1);
        int num = productImgDao.batchInsertProductImg(list);
        System.out.println("num  :"+num);
    }

}
