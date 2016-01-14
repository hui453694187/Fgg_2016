package com.yunfangdata.fgg.dto;

import com.yunfang.framework.utils.ListUtil;
import com.yunfangdata.fgg.model.GardenDetail;
import com.yunfangdata.fgg.model.GardenImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2015/12/19.
 */
public class GardenDto {

    private String ResidentialAreaName;

    private double RatioByLastMonthForPrice;

    private double RentPrice;

    private String CityName;
    /*容积率**/
    private double FloorAreaRatio;

    private int ResidentialAreaID;

    private double UnitPrice;

    private double RatioByLastYearForPrice;

    private double RatioByLastMonthForRent;

    private String RoomType;

    private String Address;

    private String DistrictName;

    private List<GardenImage> ImagesList;

    private double Y;

    private double X;

    private double BuildingAreaInt;

    private double RatioByLastYearForRent;
    /*绿化率**/
    private double GreeningRate;

    public String getResidentialAreaName() {
        return ResidentialAreaName;
    }

    public void setResidentialAreaName(String residentialAreaName) {
        ResidentialAreaName = residentialAreaName;
    }

    public double getRatioByLastMonthForPrice() {
        return RatioByLastMonthForPrice;
    }

    public void setRatioByLastMonthForPrice(double ratioByLastMonthForPrice) {
        RatioByLastMonthForPrice = ratioByLastMonthForPrice;
    }

    public double getRentPrice() {
        return RentPrice;
    }

    public void setRentPrice(double rentPrice) {
        RentPrice = rentPrice;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public double getFloorAreaRatio() {
        return FloorAreaRatio;
    }

    public void setFloorAreaRatio(double floorAreaRatio) {
        FloorAreaRatio = floorAreaRatio;
    }

    public int getResidentialAreaID() {
        return ResidentialAreaID;
    }

    public void setResidentialAreaID(int residentialAreaID) {
        ResidentialAreaID = residentialAreaID;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public double getRatioByLastYearForPrice() {
        return RatioByLastYearForPrice;
    }

    public void setRatioByLastYearForPrice(double ratioByLastYearForPrice) {
        RatioByLastYearForPrice = ratioByLastYearForPrice;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public List<GardenImage> getImagesList() {
        if(!ListUtil.hasData(this.ImagesList)){
            this.ImagesList=new ArrayList<>();
        }
        return ImagesList;
    }

    public void setImagesList(List<GardenImage> imagesList) {
        ImagesList = imagesList;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getBuildingAreaInt() {
        return BuildingAreaInt;
    }

    public void setBuildingAreaInt(double buildingAreaInt) {
        BuildingAreaInt = buildingAreaInt;
    }

    public double getRatioByLastYearForRent() {
        return RatioByLastYearForRent;
    }

    public void setRatioByLastYearForRent(double ratioByLastYearForRent) {
        RatioByLastYearForRent = ratioByLastYearForRent;
    }

    public double getGreeningRate() {
        return GreeningRate;
    }

    public void setGreeningRate(double greeningRate) {
        GreeningRate = greeningRate;
    }

    public GardenDetail getGardenDetail(){
        GardenDetail gd=new GardenDetail();
        gd.setResidentialAreaName(this.ResidentialAreaName);
        gd.setAddress(this.Address);
        gd.setBuildingAreaInt(this.BuildingAreaInt);
        gd.setDistrictFullName(this.DistrictName);
        gd.setImagesList(this.getImagesList());
        //gd.setHouseType(this.H);
        //gd.setMatchName(this.M);
        gd.setRatioByLastMonthForPrice(this.RatioByLastMonthForPrice);
        gd.setRatioByLastMonthForRent(this.RatioByLastMonthForRent);
        gd.setRatioByLastYearForRent(this.RatioByLastYearForRent);
        gd.setRatioByLastYearForPrice(this.RatioByLastYearForPrice);
        gd.setRentPrice(this.RentPrice);
        gd.setX(this.X);
        gd.setY(this.Y);
        gd.setRoomType(this.RoomType);
        gd.setUnitPrice(this.UnitPrice);
        gd.setResidentialAreaID(this.ResidentialAreaID);

        return gd;
    }
}
