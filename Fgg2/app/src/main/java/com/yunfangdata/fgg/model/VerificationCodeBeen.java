package com.yunfangdata.fgg.model;

/**
 * 获取手机验证码的beem
 *
 * Created by zjt on 2015-12-16.
 */
public class VerificationCodeBeen {


    /**
     * msg : 发送成功
     * success : true
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
