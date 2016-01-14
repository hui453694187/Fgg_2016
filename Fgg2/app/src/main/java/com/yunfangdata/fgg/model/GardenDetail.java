package com.yunfangdata.fgg.model;

import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2015/12/15.
 * 小区详情 model
 */
public class GardenDetail {

    /**小区名称*/
    private String ResidentialAreaName;
    /**小区ID*/
    private int ResidentialAreaID;
    /**小区地址*/
    private String Address;
    /**小区均价*/
    private double UnitPrice;
    /**小区平均租金*/
    private double RentPrice;
    /** 可购最大面积*/
    private double BuildingAreaInt;
    /**均价同比*/
    private double RatioByLastYearForPrice;
    /**均价环比*/
    private double RatioByLastMonthForPrice;
    /**租金同比*/
    private double RatioByLastYearForRent;
    /**租金环比*/
    private double RatioByLastMonthForRent;
    /** 房间类型*/
    private String RoomType;
    /**地址*/
    private String MatchName;
    //DistrictFullName : "海淀区"
    //行政区名
    private String DistrictFullName;
    /**小区类型*/
    private String HouseType;
    /** 小区图片 */
    private List<GardenImage> ImagesList;
    /** 经度*/
    private double X;
    /** 纬度*/
    private double Y;
    /** 百度地图坐标*/
    private LatLng latLng;

    public String getResidentialAreaName() {
        return ResidentialAreaName;
    }

    public void setResidentialAreaName(String residentialAreaName) {
        ResidentialAreaName = residentialAreaName;
    }

    public int getResidentialAreaID() {
        return ResidentialAreaID;
    }

    public void setResidentialAreaID(int residentialAreaID) {
        ResidentialAreaID = residentialAreaID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public double getRentPrice() {
        return RentPrice;
    }

    public void setRentPrice(double rentPrice) {
        RentPrice = rentPrice;
    }

    public String getHouseType() {
        return HouseType;
    }

    public void setHouseType(String houseType) {
        HouseType = houseType;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public LatLng getLatLng() {
        if(this.latLng==null){
            this.latLng=new LatLng(this.getY(),this.getX());
        }
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }


    public double getBuildingAreaInt() {
        return BuildingAreaInt;
    }

    public void setBuildingAreaInt(double buildingAreaInt) {
        BuildingAreaInt = buildingAreaInt;
    }

    public double getRatioByLastYearForPrice() {
        return RatioByLastYearForPrice;
    }

    public void setRatioByLastYearForPrice(double ratioByLastYearForPrice) {
        RatioByLastYearForPrice = ratioByLastYearForPrice;
    }

    public double getRatioByLastMonthForPrice() {
        return RatioByLastMonthForPrice;
    }

    public void setRatioByLastMonthForPrice(double ratioByLastMonthForPrice) {
        RatioByLastMonthForPrice = ratioByLastMonthForPrice;
    }

    public double getRatioByLastYearForRent() {
        return RatioByLastYearForRent;
    }

    public void setRatioByLastYearForRent(double ratioByLastYearForRent) {
        RatioByLastYearForRent = ratioByLastYearForRent;
    }

    public double getRatioByLastMonthForRent() {
        return RatioByLastMonthForRent;
    }

    public void setRatioByLastMonthForRent(double ratioByLastMonthForRent) {
        RatioByLastMonthForRent = ratioByLastMonthForRent;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String roomType) {
        RoomType = roomType;
    }

    public String getMatchName() {
        return MatchName;
    }

    public void setMatchName(String matchName) {
        MatchName = matchName;
    }

    public String getDistrictFullName() {
        return DistrictFullName;
    }

    public void setDistrictFullName(String districtFullName) {
        DistrictFullName = districtFullName;
    }

    public List<GardenImage> getImagesList() {
        if(this.ImagesList==null){
            this.ImagesList=new ArrayList<>();
        }
        return ImagesList;
    }

    public void setImagesList(List<GardenImage> imagesList) {
        ImagesList = imagesList;
    }
}
