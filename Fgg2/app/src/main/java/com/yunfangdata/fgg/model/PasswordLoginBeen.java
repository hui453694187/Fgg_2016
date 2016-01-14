package com.yunfangdata.fgg.model;

/**
 * 密码登录的业务类
 *
 * Created by zjt on 2015-12-16.
 */
public class PasswordLoginBeen {


    /**
     * msg : 用户账号密码不正确！
     * success : false
     */

    private String msg;   //登录成功的时候是没有msg的
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
