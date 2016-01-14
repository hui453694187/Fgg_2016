package com.yunfangdata.fgg.model;

/**
 * 短信登录类
 *
 * Created by zjt on 2015-12-16.
 */
public class SmsLoginBeen {

    /**
     * msg : 验证码不正确
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
