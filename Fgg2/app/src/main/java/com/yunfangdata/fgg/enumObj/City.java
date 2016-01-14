package com.yunfangdata.fgg.enumObj;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by Kevin on 2015/12/18.
 * 城市列表枚举
 */
public enum City {
    //广州("广州","guangzhou","257",new LatLng(23.120049,113.30765)),//g:"113.30765,23.120049
    上海("上海","shanghai","289",new LatLng(31.249162,121.487899)),
    北京("北京","beijing","131",new LatLng(39.929986,116.395645));


    /*城市拼音**/
    private String cityPinYin;
    /*百度定位的cityCode**/
    private String cityCode;
    /*城市名字**/
    private String cityName;
    //城市中心坐标
    private LatLng cityCenter;

    City(String cityName,String cityPinYin,String cityCode,LatLng cityCenter){
        this.cityName=cityName;
        this.cityPinYin=cityPinYin;
        this.cityCode=cityCode;
        this.cityCenter=cityCenter;
    }

    public static City getCityByCityCode(String cityCode){
        for(City c:City.values()){
            if(cityCode.equals(c.getCityCode())){
                return c;
            }
        }
        return null;
    }

    public String getCityPinYin() {
        return cityPinYin;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public LatLng getCityCenter() {
        return cityCenter;
    }
}
