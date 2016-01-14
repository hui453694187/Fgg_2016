package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.BindingEmailBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 绑定邮箱
 * Created by zjt on 2015-12-10.
 */
public class BindingEmailTask implements IRequestTask {

    private static final String TAG = "BindingEmailTask";
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
    public ResultInfo<BindingEmailBeen> getResponseData() {
        ResultInfo<BindingEmailBeen> result = new ResultInfo<BindingEmailBeen>();
        if (mData != null) {
            String resultStr = new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    BindingEmailBeen bindingEmailBeen = JSON.parseObject(resultStr, BindingEmailBeen.class);
                    ZLog.Zlogi("bindingEmailBeen = " + bindingEmailBeen.toString(), TAG, LOGLEVEL);

                    if (bindingEmailBeen.getSuccess().equals("false")) {
                        result.Success = false;
                        result.Message = bindingEmailBeen.getOthers();
                    } else {
                        result.Data = bindingEmailBeen;
                        result.Message = bindingEmailBeen.getOthers();
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


    public ResultInfo<BindingEmailBeen> request(String phone, String emailAddress) {
        ResultInfo<BindingEmailBeen> result = null;

        String url = Constant.HTTP_ALL + Constant.HTTP_GET_BAND_EMAIL;
        Hashtable<String, Object> params = new Hashtable<String, Object>(2);
        params.put("phone", phone);
        params.put("emailAddress", emailAddress);


        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if (result == null) {
                result = new ResultInfo<BindingEmailBeen>();
            }
            result.Success = false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
