package com.yunfangdata.fgg.model;

/**
 * 委托评估返回
 *
 * Created by zjt on 2015-12-19.
 */
public class EntrustEvaluateReturnBeen {


    /**
     * others : 失败
     * msg : 委托失败
     * success : false
     */

    private String others;
    private String msg;
    private String success;

    public void setOthers(String others) {
        this.others = others;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getOthers() {
        return others;
    }

    public String getMsg() {
        return msg;
    }

    public String getSuccess() {
        return success;
    }
}
