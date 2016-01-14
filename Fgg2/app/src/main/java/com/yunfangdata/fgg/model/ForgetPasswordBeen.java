package com.yunfangdata.fgg.model;

/**
 * 找回密码请求
 *
 * {"msg":"成功","success":"true"}
 * {"others":"短信验证未通过,请重新填写!","data":"","msg":"失败","success":"false"}
 * Created by zjt on 2015-12-17.
 */
public class ForgetPasswordBeen {


    /**
     * others : 短信验证未通过,请重新填写!
     * data :
     * msg : 失败
     * success : false
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
