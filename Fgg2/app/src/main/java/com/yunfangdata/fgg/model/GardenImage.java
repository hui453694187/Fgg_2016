package com.yunfangdata.fgg.model;

/**
 * Created by Kevin on 2015/12/15.
 *  小区图片信息
 */
public class GardenImage {

    /**图片地址*/
    private String Url;
    /** 图片类型*/
    private String Type;
    /** 图片标题*/
    private String Title;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public String toString() {
        return "Url:"+Url+"-->Type:"+Type+"-->Title"+getTitle();
    }
}
