package com.yunfangdata.fgg.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/13.
 */
public class DataResult<E> implements Serializable{
    /** 请求信息 */
    private String message;
    /** 开始时间 */
    private String startTime;
    /**结束时间*/
    private String endTime;
    /** 请求结果 */
    private boolean success=false;
    /** 返回数据*/
    private Object resultData;

    public DataResult(){}

    public DataResult(boolean success, E resultData, String message){
        this.message=message;
        this.success=success;
        this.resultData=resultData;
    }

    public DataResult(boolean success, String message) {
        this.success=success;
        this.message=message;
    }

    public DataResult(boolean success, E resultData) {
        this.success=success;
        this.resultData=resultData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
       this.success=success;
    }

    public E getResultData() {
        return (E)resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    @Override
    public String toString() {
        return "ResultInfo [success=" + success + ", data=" + resultData
                + ", Message=" + message + "]";
    }
}
