package com.yunfangdata.fgg.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by 贺隽 on 2015/12/16.
 * 看房联系人
 */
public class PersonContacts implements Serializable {

    /**
     * 勾选用
     */
    public Boolean IsCheck = false;

    /**
     * 客户编号
     */
    private String keHuId;

    public String getKeHuId() {
        return keHuId;
    }

    public void setKeHuId(String keHuId) {
        this.keHuId = keHuId;
    }

    /**
     * 联系人编号
     */
    private String lianXiRenId;

    public String getLianXiRenId() {
        return lianXiRenId;
    }

    public void setLianXiRenId(String lianXiRenId) {
        this.lianXiRenId = lianXiRenId;
    }

    /**
     * 联系人标记删除,0为未删除
     */
    private int lianXiRenIsDel;

    public int getLianXiRenIsDel() {
        return lianXiRenIsDel;
    }

    public void setLianXiRenIsDel(int lianXiRenIsDel) {
        this.lianXiRenIsDel = lianXiRenIsDel;
    }

    /**
     * 联系人姓名
     */
    private String lianXiRenName;

    public String getLianXiRenName() {
        return lianXiRenName;
    }

    public void setLianXiRenName(String lianXiRenName) {
        this.lianXiRenName = lianXiRenName;
    }

    /**
     * 联系人电话
     */
    private String lianXiRenTel;

    public String getLianXiRenTel() {
        return lianXiRenTel;
    }

    public void setKeLianXiRenTel(String lianXiRenTel) {
        this.lianXiRenTel = lianXiRenTel;
    }

    /**
     * 是否为默认联系人,1代表是
     */
    private int moRenLianXiRen;

    public int getMoRenLianXiRen() {
        return moRenLianXiRen;
    }

    public void setMoRenLianXiRen(int moRenLianXiRen) {
        this.moRenLianXiRen = moRenLianXiRen;
    }

    /**
     * 手机
     */
    private String shouJi;

    public String getShouJi() {
        return shouJi;
    }

    public void setShouJi(String shouJi) {
        this.shouJi = shouJi;
    }

    /**
     * JsonObject 转换实例
     * @param obj
     * @throws JSONException
     */
    public PersonContacts(JSONObject obj) throws JSONException {
        keHuId = obj.optString("keHuId");
        lianXiRenId = obj.optString("lianXiRenId");
        lianXiRenIsDel = obj.optInt("lianXiRenIsDel");
        lianXiRenName = obj.optString("lianXiRenName");
        lianXiRenTel = obj.optString("lianXiRenTel");
        moRenLianXiRen= obj.optInt("moRenLianXiRen");
        shouJi = obj.optString("shouJi");
    }

    /**
     * 空构造函数
     * @throws JSONException
     */
    public PersonContacts()  {

    }
}
