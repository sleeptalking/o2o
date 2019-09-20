package com.zx.o2o.service;

import com.zx.o2o.BaseTest;
import com.zx.o2o.dto.ImageHolder;
import com.zx.o2o.dto.ShopExecution;
import com.zx.o2o.entity.Area;
import com.zx.o2o.entity.PersonInfo;
import com.zx.o2o.entity.Shop;
import com.zx.o2o.entity.ShopCategory;
import com.zx.o2o.enums.ShopStateEnum;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;


    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1l);
        shopCondition.setShopCategory(shopCategory);
        ShopExecution se = shopService.getShopList(shopCondition,2,2);
        System.out.println("店铺列表数量"+se.getShopList().size());
        System.out.println("店铺总数量"+se.getCount());
    }

    @Ignore
    @Test
    public void testModifyShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(60l);
        shop.setShopName("修改后的店铺名称");
        File file = new File("D:\\timg1.jpg");
        InputStream inputStream = new FileInputStream(file);
        ShopExecution shopExecution = shopService.modifyShop(shop,new ImageHolder("timg1.jpg",inputStream));
        System.out.println("shopImg :"+shopExecution.getShop().getShopImg());

    }

    @Test
    @Ignore
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1l);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1l);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺5");
        shop.setShopDesc("test5");
        shop.setShopAddr("test5");
        shop.setPhone("test5");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("D:\\timg.jpg");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution se = shopService.addShop(shop,new ImageHolder("timg1.jpg",is));
        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());

    }

}
