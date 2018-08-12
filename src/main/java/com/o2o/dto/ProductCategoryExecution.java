package com.o2o.dto;

import com.o2o.entity.ProductCategory;
import com.o2o.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {
    private int state;
    private String stateInfo;
    private List<ProductCategory> productCategoryList;
    public ProductCategoryExecution(){

    }
    public ProductCategoryExecution(ProductCategoryStateEnum pm){
        this.state = pm.getState();
        this.stateInfo = pm.getStateInfo();
    }
    public ProductCategoryExecution(ProductCategoryStateEnum pm, List<ProductCategory> productCategoryList){
        this.state = pm.getState();
        this.stateInfo = pm.getStateInfo();
        this.productCategoryList = productCategoryList;
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

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
