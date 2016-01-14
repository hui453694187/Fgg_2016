package com.yunfangdata.fgg.model;

import java.util.List;

/**
 * 特殊因素
 *
 * Created by zjt on 2015-12-15.
 */
public class SpecialFactorsBeen {


    /**
     * Success : true
     * Data : ["复式"]
     */

    private boolean Success;
    private List<String> Data;

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public void setData(List<String> Data) {
        this.Data = Data;
    }

    public boolean isSuccess() {
        return Success;
    }

    public List<String> getData() {
        return Data;
    }
}
