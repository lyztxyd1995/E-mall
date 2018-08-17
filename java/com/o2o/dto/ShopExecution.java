package com.o2o.dto;

import com.o2o.entity.Shop;
import com.o2o.enums.ShopStateEnum;

import java.util.List;

public class ShopExecution {
    private int state;
    private String stateInfo;
    private int count;
    private Shop shop;
    private List<Shop> shopList;
    public ShopExecution(){

    }
    //The constructor when the manipulation of shop is failed
    public ShopExecution(ShopStateEnum stateEnum){
        this.state =stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //The constructor when the manipulation of shop is successful
    public ShopExecution(ShopStateEnum stateEnum, Shop shop){
        this.state =stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }
    //The constructor when the manipulation of shop is successful
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shoplist){
        this.state =stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shoplist;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
