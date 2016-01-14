package com.yunfangdata.fgg.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by 贺隽 on 2015/12/16.
 * 收件人信息
 */
public class PersonRecipients implements Serializable {

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
     * 默认收件地址
     */
    private int moRenShouJianDiZhi;

    public int getMoRenShouJianDiZhi() {
        return moRenShouJianDiZhi;
    }

    public void setMoRenShouJianDiZhi(int moRenShouJianDiZhi) {
        this.moRenShouJianDiZhi = moRenShouJianDiZhi;
    }

    /**
     * 收件人
     */
    private String shouJianRen;

    public String getShouJianRen() {
        return shouJianRen;
    }

    public void setShouJianRen(String shouJianRen) {
        this.shouJianRen = shouJianRen;
    }

    /**
     * 收件人电话
     */
    private String shouJianRenDianHua;

    public String getShouJianRenDianHua() {
        return shouJianRenDianHua;
    }

    public void setShouJianRenDianHua(String shouJianRenDianHua) {
        this.shouJianRenDianHua = shouJianRenDianHua;
    }

    /**
     * 收件人固定电话
     */
    private String shouJianRenGudingDianHua;

    public String getShouJianRenGudingDianHua() {
        return shouJianRenGudingDianHua;
    }

    public void setShouJianRenGudingDianHua(String shouJianRenGudingDianHua) {
        this.shouJianRenGudingDianHua = shouJianRenGudingDianHua;
    }

    /**
     * 邮件地址
     */
    private String youJiDiZhi;

    public String getYouJiDiZhi() {
        return youJiDiZhi;
    }

    public void setYouJiDiZhi(String youJiDiZhi) {
        this.youJiDiZhi = youJiDiZhi;
    }

    /**
     *邮件地址编号
     */
    private String youJiDiZhiId;

    public String getYouJiDiZhiId() {
        return youJiDiZhiId;
    }

    public void setYouJiDiZhiId(String youJiDiZhiId) {
        this.youJiDiZhiId = youJiDiZhiId;
    }

    /**
     * 是否标记删除 1 为删除
     */
    private int youJiDiZhiIsDel;

    public int getYouJiDiZhiIsDel() {
        return youJiDiZhiIsDel;
    }

    public void setYouJiDiZhiIsDel(int youJiDiZhiIsDel) {
        this.youJiDiZhiIsDel = youJiDiZhiIsDel;
    }

    /**
     *  邮件范围
     */
    private String youJifangwei;

    public String getYouJifangwei() {
        return youJifangwei;
    }

    public void setYouJifangwei(String youJifangwei) {
        this.youJifangwei = youJifangwei;
    }

    /**
     * 邮政编码
     */
    private String youZhengBianMa;

    public String getYouZhengBianMa() {
        return youZhengBianMa;
    }

    public void setYouZhengBianMa(String youZhengBianMa) {
        this.youZhengBianMa = youZhengBianMa;
    }

    /**
     * JsonObject 转换实例
     * @param obj
     * @throws JSONException
     */
    public PersonRecipients(JSONObject obj) throws JSONException {
        keHuId = obj.optString("keHuId");
        moRenShouJianDiZhi = obj.optInt("moRenShouJianDiZhi");
        shouJianRen = obj.optString("shouJianRen");
        shouJianRenDianHua = obj.optString("shouJianRenDianHua");
        shouJianRenGudingDianHua = obj.optString("shouJianRenGudingDianHua");
        youJiDiZhi = obj.optString("youJiDiZhi");
        youJiDiZhiId = obj.optString("youJiDiZhiId");
        youJiDiZhiIsDel= obj.optInt("youJiDiZhiIsDel");
        youJifangwei = obj.optString("youJifangwei");
        youZhengBianMa = obj.optString("youZhengBianMa");
    }

    /**
     * 空构造函数
     * @throws JSONException
     */
    public PersonRecipients()  {

    }
}
