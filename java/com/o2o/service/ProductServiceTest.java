package com.o2o.service;

import com.o2o.BaseTest;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.enums.ProductSateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductSateEnum.SUCCESS.getState());

        File thumbnailFile = new File("/Users/yizeliu/Downloads/xiaohuangren.jpeg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder imageHolder = new ImageHolder(thumbnailFile.getName(),is);


        File productImage1 = new File("/Users/yizeliu/Downloads/food.jpg");
        File productImage2 = new File("/Users/yizeliu/Downloads/dog2.jpeg");
        InputStream is1 = new FileInputStream(productImage1);
        InputStream is2 = new FileInputStream(productImage2);
        ImageHolder imageHolder1 = new ImageHolder(productImage1.getName(),is1);
        ImageHolder imageHolder2 = new ImageHolder(productImage2.getName(), is2);
        List<ImageHolder> imageHolderList = new ArrayList<>();
        imageHolderList.add(imageHolder1);
        imageHolderList.add(imageHolder2);

        ProductExecution pe = productService.addProduct(product,imageHolder,imageHolderList);
        assertEquals(pe.getState(), ProductSateEnum.SUCCESS.getState());
    }

    @Test
    public void testmodifyProduct() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(28L);
        Product product = new Product();
        product.setShop(shop);
        product.setProductId(42L);
        product.setPriority(3);
        product.setProductDesc("测试奶茶店");
        product.setProductName("Coco奶茶");
        File thumbFile = new File("/Users/yizeliu/Downloads/coco1.jpeg");
        InputStream thumbis = new FileInputStream(thumbFile);
        ImageHolder thumbnail = new ImageHolder(thumbFile.getName(), thumbis);
        File productImg = new File("/Users/yizeliu/Downloads/coco2.jpg");
        InputStream is = new FileInputStream(productImg);
        ImageHolder productImgHolder = new ImageHolder(productImg.getName(),is);
        List<ImageHolder>productImgList = new ArrayList<>();
        productImgList.add(productImgHolder);
        ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
        assertEquals(pe.getState(), ProductSateEnum.SUCCESS.getState());
    }
}
