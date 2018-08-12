package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testQueryByShopId(){
        long shopId = 29L;
        List<ProductCategory>productCategoryList = productCategoryDao.queryProductCategory(shopId);
        System.out.println(productCategoryList.size());
    }

    @Test
    public void testbatchInsertProductCategory(){
        ProductCategory p1 = new ProductCategory();
        p1.setCreateTime(new Date());
        p1.setPriority(1);
        p1.setProductCategoryName("商品类别1");
        p1.setShopId(28L);
        ProductCategory p2 = new ProductCategory();
        p2.setCreateTime(new Date());
        p2.setPriority(2);
        p2.setProductCategoryName("商品类别2");
        p2.setShopId(28L);
        List<ProductCategory>list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        int effectNum = productCategoryDao.batchInsertProductCategory(list);
        assertEquals(effectNum,2);
    }

    @Test
    public void testdeleteProductCategory(){
        Long shopId = 28L;
        Long productCategoryId = 33L;
        int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
        assertEquals(effectedNum, 1);
    }
}
