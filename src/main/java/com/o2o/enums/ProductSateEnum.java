package com.o2o.enums;

public enum ProductSateEnum {
    OFFLINE(-1, "Illegal Product"), DOWN(0, "Off line"),SUCCESS(1, "success"), INNER_ERROR(-1001, "fail"), EMPTY_LIST(-1002, "empty list");
    private int state;

    private String stateInfo;

    private ProductSateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
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
}
