package com.o2o.dto;

import com.o2o.entity.Product;
import com.o2o.enums.ProductSateEnum;

import java.util.List;

public class ProductExecution{
   private int state;
   private String stateInfo;
   private int count;
   private Product product;
   private List<Product> productList;

   public ProductExecution(){};
   public ProductExecution(ProductSateEnum stateEnum, Product product){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;
   }

   public ProductExecution(ProductSateEnum stateEnum, List<Product>productList){
       this.state = stateEnum.getState();
       this.stateInfo = stateEnum.getStateInfo();
       this.productList = productList;
   }

   public ProductExecution(ProductSateEnum stateEnum){
       this.state = stateEnum.getState();
       this.stateInfo = stateEnum.getStateInfo();
   }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
