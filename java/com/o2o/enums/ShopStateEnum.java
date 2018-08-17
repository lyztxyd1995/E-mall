package com.o2o.enums;

public enum ShopStateEnum {
    CHECK(0,"in cheking"), OFFLINE(-1,"Illegal shop"), SUCCESS(1,"success"),
    PASS(2, "pass checking"), INNERT_ERROR(-1001, "inner system error"), NULL_SHOPID(-1002,"shop id is null"),
    NULLSHOP(-1003, "shop information is null");
    private int state;
    private String stateInfo;
    private ShopStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }
    public static ShopStateEnum stateOf(int state){
        for(ShopStateEnum stateEnum: values()){
            if(stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
