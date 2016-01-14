package com.yunfangdata.fgg.model;

/**
 * 找回密码验证码
 *
 * Created by zjt on 2015-12-17.
 */
public class ForgetPasswordVerificationCodeBeen {


    /**
     * others : 发送成功
     * msg : 成功
     * success : true
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
