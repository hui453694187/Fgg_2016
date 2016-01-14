package com.yunfangdata.fgg.model;

/**
 * Created by Kevin on 2015/12/14.
 */
public class SubStation {

    private String diTieXianID;

    private String diTieZhanId;

    private String diTieZhanName;

    private int diTieZhanIsDel;

    public String getDiTieXianID() {
        return diTieXianID;
    }

    public void setDiTieXianID(String diTieXianID) {
        this.diTieXianID = diTieXianID;
    }

    public String getDiTieZhanId() {
        return diTieZhanId;
    }

    public void setDiTieZhanId(String diTieZhanId) {
        this.diTieZhanId = diTieZhanId;
    }

    public String getDiTieZhanName() {
        return diTieZhanName;
    }

    public void setDiTieZhanName(String diTieZhanName) {
        this.diTieZhanName = diTieZhanName;
    }

    public int getDiTieZhanIsDel() {
        return diTieZhanIsDel;
    }

    public void setDiTieZhanIsDel(int diTieZhanIsDel) {
        this.diTieZhanIsDel = diTieZhanIsDel;
    }

    @Override
    public String toString() {
        return "diTieXianID:" + diTieXianID + "-diTieZhanId:" + diTieZhanId
                + "-diTieZhanName:" + diTieZhanName + "-diTieZhanIsDel" + diTieZhanIsDel;
    }
}
