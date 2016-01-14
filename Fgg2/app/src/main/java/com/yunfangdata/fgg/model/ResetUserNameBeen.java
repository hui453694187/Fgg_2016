package com.yunfangdata.fgg.model;

/**
 *重新设置用户名
 *
 * Created by zjt on 2015-12-22.
 */
public class ResetUserNameBeen {

    /**
     * success : true
     * msg : 成功
     * others :
     */

    private String success;
    private String msg;
    private String others;

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public String getOthers() {
        return others;
    }
}
