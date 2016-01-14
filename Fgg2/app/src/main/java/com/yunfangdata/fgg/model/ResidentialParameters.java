package com.yunfangdata.fgg.model;

/**
 * Created by Kevin on 2015/12/16.
 * 请求小区数据参数模型
 */
public class ResidentialParameters {
    /*页码**/
    private int pageIndex;
    /*页总数**/
    private int pageSize;
    /*城市名 拼音**/
    private String cityName;
    /*行政区**/
    private String districtName;
    /*片区，字符串**/
    private String plate;
    /*小区名称，字符串**/
    private String residentialAreaName;
    /*居室类型**/
    private String unitType;
    /*总价格 ，字符串**/
    private double totalPrice;
    /*面积，float**/
    private float area;
    /*特殊条件，字符串**/
    private String specialFactor;
    /*地铁线名**/
    private String subWayNO;
    /*地铁站名**/
    private String subWayName;
    /*学校名称**/
    private String schoolName;
    /*学校类型，字符串 举值，枚举类型为｛小学、初中｝**/
    private String schoolType;
    /*最大价格**/
    private double maxPrice;
    /*最小价格**/
    private double minPrice;
    /*zongjia,shoufu 找房类型**/
    private String type;


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getResidentialAreaName() {
        return residentialAreaName;
    }

    public void setResidentialAreaName(String residentialAreaName) {
        this.residentialAreaName = residentialAreaName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public String getSpecialFactor() {
        return specialFactor;
    }

    public void setSpecialFactor(String specialFactor) {
        this.specialFactor = specialFactor;
    }

    public String getSubWayNO() {
        return subWayNO;
    }

    public void setSubWayNO(String subWayNO) {
        this.subWayNO = subWayNO;
    }

    public String getSubWayName() {
        return subWayName;
    }

    public void setSubWayName(String subWayName) {
        this.subWayName = subWayName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void clearParam() {
            /*页码**/
//       this.pageIndex;
//       this.pageSize;
       this.cityName="beijin";
       this.districtName="";
       this.plate="";
       this.residentialAreaName="";
       this.unitType="";
       this.totalPrice=-1;
       this.area=-1;
       this.specialFactor="";
       this.subWayNO="";
       this.subWayName="";
       this.schoolName="";
       this.schoolType="";
       this.maxPrice=-1;
       this.minPrice=-1;
       this.type="";
    }
}


