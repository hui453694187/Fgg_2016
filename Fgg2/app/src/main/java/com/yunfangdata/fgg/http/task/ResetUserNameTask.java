package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.ResetUserNameBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 绑定邮箱
 * Created by zjt on 2015-12-10.
 */
public class ResetUserNameTask implements IRequestTask {

    private static final String TAG = "ResetUserNameTask";
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
    public ResultInfo<ResetUserNameBeen> getResponseData() {
        ResultInfo<ResetUserNameBeen> result = new ResultInfo<ResetUserNameBeen>();
        if (mData != null) {
            String resultStr = new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    ResetUserNameBeen resetUserNameBeen = JSON.parseObject(resultStr, ResetUserNameBeen.class);
                    ZLog.Zlogi("resetUserNameBeen = " + resetUserNameBeen.toString(), TAG, LOGLEVEL);

                    if (resetUserNameBeen.getSuccess().equals("false")) {
                        result.Success = false;
                        result.Message = resetUserNameBeen.getMsg();
                    } else {
                        result.Data = resetUserNameBeen;
                        result.Message = resetUserNameBeen.getMsg();
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


    public ResultInfo<ResetUserNameBeen> request(String phone,String newphone,  String verificationCode) {
        ResultInfo<ResetUserNameBeen> result = null;

        String url = Constant.HTTP_ALL + Constant.HTTP_GET_CONFIRM_EIDT_PHONE;
        Hashtable<String, Object> params = new Hashtable<String, Object>(3);
        params.put("oldPhone", phone);
        params.put("newPhone", newphone);
        params.put("captcha", verificationCode);


        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if (result == null) {
                result = new ResultInfo<ResetUserNameBeen>();
            }
            result.Success = false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
