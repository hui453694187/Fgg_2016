package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.PersonContacts;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.SpecialFactorsBeen;
import com.yunfangdata.fgg.utils.ZLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * 获取买房的特殊因素
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class BuyHouseSpecialFactorsTask implements IRequestTask {

    private static final String TAG = "BuyHouseSpecialFactorsTask";
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
    public ResultInfo<SpecialFactorsBeen> getResponseData() {
        ResultInfo<SpecialFactorsBeen> result = new ResultInfo<SpecialFactorsBeen>();
        if (mData != null) {
            String resultStr = new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    SpecialFactorsBeen buyHouseReturnBeen = JSON.parseObject(resultStr, SpecialFactorsBeen.class);
                    ZLog.Zlogi("buyHouseReturnBeen = " + buyHouseReturnBeen.toString(), TAG, LOGLEVEL);

                    if (!buyHouseReturnBeen.isSuccess()) {
                        result.Success = false;
                        result.Message = "系统错误,请稍后重试";
                    } else {
                        result.Data = buyHouseReturnBeen;
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


    public ResultInfo<SpecialFactorsBeen> request(String city,String residentialAreaName) {
        ResultInfo<SpecialFactorsBeen> result = null;

        String url = Constant.HTTP_ALL + Constant.HTTP_SPECIALFACTORS;
        Hashtable<String, Object> params = new Hashtable<String, Object>(2);
        params.put("city", city);
        params.put("residentialAreaName", residentialAreaName);

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if (result == null) {
                result = new ResultInfo<SpecialFactorsBeen>();
            }
            result.Success = false;
            result.Message = e.getMessage();
        }

        return result;
    }
}
