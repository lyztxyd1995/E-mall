package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.Area;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;
import com.o2o.util.ImageUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest{
    @Autowired
    private ShopDao shopDao;

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    @Test
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(10L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("testing shop");
        shop.setShopDesc("test");
        shop.setPhone("8888888");
        shop.setAdvice("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setShopImg("test");
        int effectImg = shopDao.insertShop(shop);
        assertEquals(effectImg,1);
    }

    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(36L);
        shop.setLastEditTime(new Date());
        int effectImg = shopDao.updateShop(shop);
        assertEquals(effectImg,1);
    }

    @Test
    public void testQueryByShopId(){
        long shopId = 1;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaId: " + shop.getArea().getAreaId());
        System.out.println("areaName" + shop.getArea().getAreaName());
    }


    @Test
    public void testQueryShopListAndCount(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(22L);
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        shopCondition.setShopCategory(shopCategory);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,1,5);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("shop total numbers: " + count);
        System.out.println(shopList);
    }
    @Test
    public void testQueryShopLisyOfParent(){
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(12L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
        List<Shop>shopList = shopDao.queryShopList(shopCondition, 0, 999);
        for(Shop shop : shopList){
            System.out.println(shop.getShopName());
        }
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("total numbers: " + count);
    }

}
