package com.yunfangdata.fgg.model;


import com.yunfang.framework.utils.PullXmlUtil;

import java.io.Serializable;

/**
 * APP函数间交互返回的结果对象
 *
 * @author gorson
 *
 */
@SuppressWarnings("serial")
public class ResultInfo<T> implements Serializable {
    // {{ Properties
    /**
     * 操作是否成功
     */
    public boolean Success = true;

    /**
     * 返回的结果对象值
     */
    public T Data;

    /**
     * 反馈相关内容
     */
    public String Message = "";

    /**
     * 其他相关内容
     */
    public Object Others;

    // }}

    // {{ 构造函数
    /**
     * 构造函数
     *
     * @param success
     *            操作是否成功
     * @param data
     *            返回的结果对象值
     * @param message
     *            反馈相关内容
     */
    public ResultInfo(boolean success, T data, String message) {
        super();
        Success = success;
        Data = data;
        Message = message;
    }

    /**
     * 构造函数
     *
     * @param success
     *            操作是否成功
     * @param data
     *            返回的结果对象值
     */
    public ResultInfo(boolean success, T data) {
        super();
        Success = success;
        Data = data;
    }

    /**
     * 构造函数
     *
     * @param success
     *            操作是否成功
     */
    public ResultInfo(boolean success) {
        super();
        Success = success;
    }

    /**
     * 构造函数
     *
     * @param message
     *            反馈相关内容
     */
    public ResultInfo(String message) {
        super();
        Message = message;
    }

    /**
     * 构造函数
     */
    public ResultInfo() {

    }

    // }}

    // {{ 方法
    @Override
    public String toString() {
        return "ResultInfo [Success=" + Success + ", Data=" + Data
                + ", Message=" + Message + ", Others=" + Others + "]";
    }

    public String toXMLString() throws Exception {
        return PullXmlUtil.serializeObject(this);
    }
    // }}
}
