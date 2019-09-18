package com.zx.o2o.dao;

import com.zx.o2o.BaseTest;
import com.zx.o2o.entity.Area;
import com.zx.o2o.entity.PersonInfo;
import com.zx.o2o.entity.Shop;
import com.zx.o2o.entity.ShopCategory;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;
    @Test
    public void testQueryShopListAndCount(){
        Shop shopCondition = new Shop();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1l);
        shopCondition.setOwner(personInfo);
        List<Shop> list = shopDao.queryShopList(shopCondition,2,2);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺的数量   "+list.size());
        System.out.println("店铺总数   "+count);


        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1l);
        shopCondition.setShopCategory(shopCategory);
        list = shopDao.queryShopList(shopCondition,2,2);
        count = shopDao.queryShopCount(shopCondition);
        System.out.println("new 店铺的数量   "+list.size());
        System.out.println("new 店铺总数   "+count);
    }

    @Ignore
    @Test
    public void testQueryByShopId(){
        Shop shop = shopDao.queryByShopId(1l);
        System.out.println(shop.getArea().getAreaId());
        System.out.println(shop.getArea().getAreaName());
    }

    @Test
    @Ignore
    public void testInsertShop(){
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
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1,effectedNum);
    }

    @Test
    @Ignore
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1,effectedNum);
    }

}
