package com.o2o.service.impl;

import com.o2o.dao.ProductCategoryDao;
import com.o2o.dao.ProductDao;
import com.o2o.dto.ProductCategoryExecution;
import com.o2o.entity.ProductCategory;
import com.o2o.enums.ProductCategoryStateEnum;
import com.o2o.exceptions.ProductCategoryOperationException;
import com.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;
    @Override
    public List<ProductCategory> getProductCategory(long shopId) {
        return productCategoryDao.queryProductCategory(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) {
        if(productCategoryList != null && productCategoryList.size() > 0){
            try{
                int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if(effectNum <= 0){
                    throw  new ProductCategoryOperationException("shop create error");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e){
                throw new ProductCategoryOperationException("batch add product category error" + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        //TODO 将此商品类别下的商品的类别Id设置为空
        try{
            int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
            if(effectedNum < 0){
                throw new ProductCategoryOperationException("fail to update product category");
            }
        } catch (Exception e){
            throw new ProductCategoryOperationException("deleteProductCategory error: " + e.getMessage());
        }
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if(effectedNum <= 0){
                throw  new ProductCategoryOperationException("delete product category failed");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e){
            throw  new ProductCategoryOperationException("delete product category error " + e.getMessage());
        }
    }
}
