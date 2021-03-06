package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductImgDaoTest extends BaseTest {
    @Autowired
    ProductImgDao productImgDao;

    @Test
    public void testbatchInsertProductImg(){
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1L);

        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);

        List<ProductImg>productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2,effectedNum);
    }

    @Test
    public void testqueryProductImgList(){
        Long productId = 1L;
        List<ProductImg>list = productImgDao.queryProductImgList(productId);
        System.out.println(list.size());
    }

    @Test
    public void testdeleteProductImgByProductId(){
        Long productId = 1L;
        List<ProductImg>list = productImgDao.queryProductImgList(productId);
        int effectedNum = productImgDao.deleteProductImgByProductId(productId);
        System.out.println(effectedNum);
        productImgDao.batchInsertProductImg(list);
    }
}
