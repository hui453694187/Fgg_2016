package com.yunfangdata.fgg.model;

import com.baidu.mapapi.model.LatLng;
import com.yunfangdata.fgg.db.base.TableWorkerBase;

import java.util.List;

/**
 * Created by kevin on 2015/12/11.
 * 行政区模型
 */
public class District{

    /***
     * 行政区经纬度对象
     */
    private LatLng latLng;

    private double x;

    private double y;

    /***
     * 行政区拼音
     */
    private String districtID;
    /***
     * 行政区价格
     */
    private float Price;
    /***
     * 行政区名
     */
    private String DistrictName;

    private List<Area> areaList;


    public LatLng getLatLng() {
        if(latLng==null){
            latLng=new LatLng(y,x);
        }
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "拼音："+ districtID +"-name:"+DistrictName+"-price:"+Price+"-latlng:"+this.getLatLng().toString();
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
