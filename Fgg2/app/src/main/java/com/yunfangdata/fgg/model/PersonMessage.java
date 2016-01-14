package com.yunfangdata.fgg.model;

import java.io.Serializable;

/**
 * Created by Kevin on 2015/12/15
 * 个人消息
 */
public class PersonMessage implements Serializable{
    /**
     * 标题
     */
    private String xiaoXiTitle;
    /**
     * 是否读取
     */
    private boolean xiaoXiIsDel;
    /**
     * 内容
     */
    private String xiaoXiNeiRong;
    /***
     * ID
     */
    private String keHuId;

    private String xiaoXiChaKanShiJian;

    private String xiaoXiFaSongShiJian;

    private String xiaoXiId;

    private String xiaoXiLeiBieId;

    private boolean xiaoXiZhuangTai;

    private String xiaoxiSendRen;

    public String getXiaoXiTitle() {
        return xiaoXiTitle;
    }

    public void setXiaoXiTitle(String xiaoXiTitle) {
        this.xiaoXiTitle = xiaoXiTitle;
    }

    public boolean getXiaoXiIsDel() {
        return xiaoXiIsDel;
    }

    public void setXiaoXiIsDel(boolean xiaoXiIsDel) {
        this.xiaoXiIsDel = xiaoXiIsDel;
    }

    public String getXiaoXiNeiRong() {
        return xiaoXiNeiRong;
    }

    public void setXiaoXiNeiRong(String xiaoXiNeiRong) {
        this.xiaoXiNeiRong = xiaoXiNeiRong;
    }

    public String getKeHuId() {
        return keHuId;
    }

    public void setKeHuId(String keHuId) {
        this.keHuId = keHuId;
    }

    public String getXiaoXiChaKanShiJian() {
        return xiaoXiChaKanShiJian;
    }

    public void setXiaoXiChaKanShiJian(String xiaoXiChaKanShiJian) {
        this.xiaoXiChaKanShiJian = xiaoXiChaKanShiJian;
    }

    public String getXiaoXiFaSongShiJian() {
        return xiaoXiFaSongShiJian;
    }

    public void setXiaoXiFaSongShiJian(String xiaoXiFaSongShiJian) {
        this.xiaoXiFaSongShiJian = xiaoXiFaSongShiJian;
    }

    public String getXiaoXiId() {
        return xiaoXiId;
    }

    public void setXiaoXiId(String xiaoXiId) {
        this.xiaoXiId = xiaoXiId;
    }

    public String getXiaoXiLeiBieId() {
        return xiaoXiLeiBieId;
    }

    public void setXiaoXiLeiBieId(String xiaoXiLeiBieId) {
        this.xiaoXiLeiBieId = xiaoXiLeiBieId;
    }

    public boolean getXiaoXiZhuangTai() {
        return xiaoXiZhuangTai;
    }

    public void setXiaoXiZhuangTai(boolean xiaoXiZhuangTai) {
        this.xiaoXiZhuangTai = xiaoXiZhuangTai;
    }

    public String getXiaoxiSendRen() {
        return xiaoxiSendRen;
    }

    public void setXiaoxiSendRen(String xiaoxiSendRen) {
        this.xiaoxiSendRen = xiaoxiSendRen;
    }
}
