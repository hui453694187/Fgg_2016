package com.yunfangdata.fgg.model;

import java.util.List;

/**
 * 催单 和 撤单
 *
 * Created by zjt on 2015-12-21.
 */
public class EntrustListReminderBeen {


    /**
     * others : 其他
     * data : []
     * msg : 撤单成功
     * success : true
     */

    private String others;
    private String msg;
    private String success;
    private List<?> data;

    public void setOthers(String others) {
        this.others = others;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setData(List<?> data) {
        this.data = data;
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

    public List<?> getData() {
        return data;
    }
}

