package com.yunfangdata.fgg.model;

/**
 * Created by Kevin on 2015/12/14.
 * 片区模型
 */
public class Area {

    private Double Price;
    private String AreaName;
    private int ResidentialRreaCount;

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public int getResidentialRreaCount() {
        return ResidentialRreaCount;
    }

    public void setResidentialRreaCount(int residentialRreaCount) {
        ResidentialRreaCount = residentialRreaCount;
    }

    @Override
    public String toString() {
        return "Price:" + Price + "--AreaName:" + AreaName + "--ResidentialRreaCount:" + ResidentialRreaCount;
    }
}
