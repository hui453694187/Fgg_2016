package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.SmsLoginBeen;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 手机短信登录的任务站
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class SmsLoginTask implements IRequestTask {

    private static final String TAG = "SmsLoginTask";
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
    public ResultInfo<SmsLoginBeen> getResponseData() {
        ResultInfo<SmsLoginBeen> result = new ResultInfo<SmsLoginBeen>();
        if(mData!=null){
            String resultStr=new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    SmsLoginBeen smsLoginBeen = JSON.parseObject(resultStr, SmsLoginBeen.class);
                    ZLog.Zlogi("smsLoginBeen = " + smsLoginBeen.toString(), TAG, LOGLEVEL);

                    if("false".equals(smsLoginBeen.getSuccess())){
                        result.Success=false;
                        if (smsLoginBeen.getMsg()!= null){
                            result.Message=smsLoginBeen.getMsg();
                        }else {
                            result.Message = "验证码出错";
                        }
                    }else{
                        result.Data=smsLoginBeen;
                    }
                }

            } catch (Exception e) {
                result.Success=false;
                result.Message="服务器异常! 请重试。";
                e.printStackTrace();
            }
        }else{
            result.Success=false;
            result.Message="请求服务器数据失败！";
        }
        return result;
    }


    /**
     * 短信登录
     * @param phone   //电话号码
     * @param securityCode   //手机验证网
     * @return
     */
    public ResultInfo<SmsLoginBeen> request(String phone,String securityCode) {
        ResultInfo<SmsLoginBeen> result = null;

        String url = Constant.HTTP_ALL+Constant.HTTP_GET_LOGIN_NO_PWD;
        Hashtable<String, Object> params = new Hashtable<String, Object>(2);
        params.put("phone", phone);
        params.put("securityCode", securityCode);

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if(result==null){
                result=new ResultInfo<SmsLoginBeen>();
            }
            result.Success=false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
