package com.yunfangdata.fgg.model;

/**
 * 用户注册
 *
 * Created by zjt on 2015-12-17.
 */
public class SMSRegisterBeen {


    /**
     * msg : 失败!已存在该用户登录名
     * success : false
     */

    private String msg;
    private String success;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public String getSuccess() {
        return success;
    }
}
