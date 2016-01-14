package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.SMSRegisterBeen;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 手机短信注册的任务站
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class SmsRegisterTask implements IRequestTask {

    private static final String TAG = "SmsRegisterTask";
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
    public ResultInfo<SMSRegisterBeen> getResponseData() {
        ResultInfo<SMSRegisterBeen> result = new ResultInfo<SMSRegisterBeen>();
        if(mData!=null){
            String resultStr=new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    SMSRegisterBeen smsRegisterBeen = JSON.parseObject(resultStr, SMSRegisterBeen.class);
                    ZLog.Zlogi("smsRegisterBeen = " + smsRegisterBeen.toString(), TAG, LOGLEVEL);

                    if("false".equals(smsRegisterBeen.getSuccess())){
                        result.Success=false;
                        if (smsRegisterBeen.getMsg()!= null){
                            result.Message=smsRegisterBeen.getMsg();
                        }else {
                            result.Message = "验证码出错";
                        }
                    }else{
                        result.Data=smsRegisterBeen;
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
     * @param pwd   //密码
     * @param securityCode   //手机验证网
     * @return
     */
    public ResultInfo<SMSRegisterBeen> request(String phone,String pwd ,String securityCode) {
        ResultInfo<SMSRegisterBeen> result = null;

        String url = Constant.HTTP_ALL+Constant.HTTP_GET_REGISTER;
        Hashtable<String, Object> params = new Hashtable<String, Object>(3);
        params.put("phone", phone);
        params.put("pwd", pwd);
        params.put("securityCode", securityCode);

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if(result==null){
                result=new ResultInfo<SMSRegisterBeen>();
            }
            result.Success=false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
