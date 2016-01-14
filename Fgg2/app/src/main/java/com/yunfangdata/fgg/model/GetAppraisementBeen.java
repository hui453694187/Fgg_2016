package com.yunfangdata.fgg.model;

import java.io.Serializable;

/**
 *
 * 用来传递买房估值数据的业务类
 *
 * Created by zjt on 2015-12-12.
 */
public class GetAppraisementBeen implements Serializable {

    private String city;
    private String residentialAreaName; //小区名字
    private String residentialAreaId; //小区ID
    private String houseType;
    private String unitType;
    private float area;
    private String buildNumber;
    private String houseNumber;
    private int floor;
    private int totalfloor;
    private String toward;
    private int builtTime;
    private String specialFactor;

    public String getResidentialAreaId() {
        return residentialAreaId;
    }

    public void setResidentialAreaId(String residentialAreaId) {
        this.residentialAreaId = residentialAreaId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getResidentialAreaName() {
        return residentialAreaName;
    }

    public void setResidentialAreaName(String residentialAreaName) {
        this.residentialAreaName = residentialAreaName;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getTotalfloor() {
        return totalfloor;
    }

    public void setTotalfloor(int totalfloor) {
        this.totalfloor = totalfloor;
    }

    public String getToward() {
        return toward;
    }

    public void setToward(String toward) {
        this.toward = toward;
    }

    public int getBuiltTime() {
        return builtTime;
    }

    public void setBuiltTime(int builtTime) {
        this.builtTime = builtTime;
    }

    public String getSpecialFactor() {
        return specialFactor;
    }

    public void setSpecialFactor(String specialFactor) {
        this.specialFactor = specialFactor;
    }

    @Override
    public String toString() {
        return "GetAppraisementBeen{" +
                "city='" + city + '\'' +
                ", residentialAreaName='" + residentialAreaName + '\'' +
                ", houseType='" + houseType + '\'' +
                ", unitType='" + unitType + '\'' +
                ", area=" + area +
                ", buildNumber='" + buildNumber + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", floor=" + floor +
                ", totalfloor=" + totalfloor +
                ", toward='" + toward + '\'' +
                ", builtTime=" + builtTime +
                ", specialFactor='" + specialFactor + '\'' +
                '}';
    }
}
