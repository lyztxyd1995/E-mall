package com.o2o.service.impl;

import com.o2o.dao.ProductDao;
import com.o2o.dao.ProductImgDao;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductImg;
import com.o2o.enums.ProductSateEnum;
import com.o2o.exceptions.ProductOperationException;
import com.o2o.service.ProductService;
import com.o2o.util.ImageUtil;
import com.o2o.util.PageCalculator;
import com.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if(product != null && product.getShop() != null && product.getShop().getShopId() != null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if(thumbnail != null){
                addThumbnail(product, thumbnail);
            }
            try{
                int effectNum = productDao.insertProduct(product);
                if(effectNum <= 0){
                    throw new ProductOperationException("fail to create new product");
                }
            } catch(Exception e){
                throw new ProductOperationException("fail to create new product " + e.getMessage());
            }

            if(productImgList != null && productImgList.size() > 0){
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(ProductSateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductSateEnum.EMPTY_LIST);
        }
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product>productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if(product != null && product.getShop() != null && product.getShop().getShopId() != null){
            product.setLastEditTime(new Date());
            if(thumbnail != null){
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if(tempProduct.getImgAddr() != null){
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product,thumbnail);
            }

            if(productImgList != null && productImgList.size() > 0){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgList);
            }

            try{
                int effectedNum = productDao.updateProduct(product);
                if(effectedNum <= 0){
                    throw new ProductOperationException("fail to update product");
                }
                return new ProductExecution(ProductSateEnum.SUCCESS, product);
            } catch (Exception e){
                throw new ProductOperationException("fail to update product " + e.toString());
            }
        } else {
            return new ProductExecution(ProductSateEnum.EMPTY_LIST);
        }
    }

    private void addThumbnail(Product product, ImageHolder thumbnail){
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList){
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg>productImgList = new ArrayList<>();
        for(ImageHolder imageHolder: productImgHolderList){
            String imgAddr = ImageUtil.generateNormalImg(imageHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        if(productImgList.size() > 0){
            try{
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if(effectedNum <= 0){
                    throw new ProductOperationException("fail to add details image");
                }
            } catch (Exception e){
                throw new ProductOperationException("fail to add details image" + e.toString());
            }
        }
    }

    private void deleteProductImgList(Long productId){
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        for(ProductImg productImg : productImgList){
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        productImgDao.deleteProductImgByProductId(productId);
    }
}
