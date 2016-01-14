package com.yunfangdata.fgg.model;

/**
 * 绑定邮箱
 *
 * Created by zjt on 2015-12-22.
 */
public class BindingEmailBeen {

    /**
     * others : 绑定成功
     * data :
     * msg : 成功
     * success : true
     */

    private String others;
    private String data;
    private String msg;
    private String success;

    public void setOthers(String others) {
        this.others = others;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public String getSuccess() {
        return success;
    }
}
