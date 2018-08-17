package com.o2o.dao;


import com.o2o.entity.ProductCategory;
import com.o2o.exceptions.ProductCategoryOperationException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    List<ProductCategory> queryProductCategory(long shopId);

    int batchInsertProductCategory(List<ProductCategory>productCategoryList) throws ProductCategoryOperationException;

    int deleteProductCategory(@Param("productCategoryId")long productCategoryId, @Param("shopId")long shopId);
}
