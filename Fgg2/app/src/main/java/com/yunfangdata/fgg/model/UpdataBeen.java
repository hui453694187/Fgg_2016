package com.yunfangdata.fgg.model;

/**
 * 版本更新
 * Created by zjt on 2015-12-21.
 */
public class UpdataBeen {


    /**
     * content :
     * success : true
     * version : 00.00.0051
     */

    private String content;
    private String success;
    private String version;

    public void setContent(String content) {
        this.content = content;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public String getSuccess() {
        return success;
    }

    public String getVersion() {
        return version;
    }
}
