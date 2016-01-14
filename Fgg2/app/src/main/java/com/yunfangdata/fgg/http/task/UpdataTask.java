package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.UpdataBeen;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 获取买房估值的返回
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class UpdataTask implements IRequestTask {

    private static final String TAG = "BuyHouseReturnTask";
    private static final int LOGLEVEL = 1;
    /**
     * 响应数据
     */
    private byte[] mData;

    @Override
    public void setContext(byte[] data) {
        this.mData = data;
    }

    @Override
    public ResultInfo<UpdataBeen> getResponseData() {
        ResultInfo<UpdataBeen> result = new ResultInfo<UpdataBeen>();
        if (mData != null) {
            String resultStr = new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    UpdataBeen updataBeen = JSON.parseObject(resultStr, UpdataBeen.class);
                    ZLog.Zlogi("updataBeen = " + updataBeen.toString(), TAG, LOGLEVEL);

                    if (!updataBeen.getSuccess().equals("true")) {
                        result.Success = false;
                        result.Message = "系统错误,请稍后重试";
                    } else {
                        result.Data = updataBeen;
                    }
                }

            } catch (Exception e) {
                result.Success = false;
                result.Message = "服务器异常! 请重试。";
                e.printStackTrace();
            }
        } else {
            result.Success = false;
            result.Message = "请求服务器数据失败！";
        }
        return result;
    }


    public ResultInfo<UpdataBeen> request() {
        ResultInfo<UpdataBeen> result = null;

        String url = Constant.HTTP_ALL + Constant.HTTP_GET_GET_VERSION;
        Hashtable<String, Object> params = new Hashtable<String, Object>(0);
        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if (result == null) {
                result = new ResultInfo<UpdataBeen>();
            }
            result.Success = false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
