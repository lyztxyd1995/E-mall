package com.o2o.service;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.exceptions.ProductOperationException;

import java.io.InputStream;
import java.util.List;

public interface ProductService {
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder>productImgList) throws ProductOperationException;

    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    Product getProductById(long productId);

    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder>productImgList)throws ProductOperationException;
}
