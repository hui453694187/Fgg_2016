package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.VerificationCodeBeen;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 获取短信验证码的任务站
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class VerificationCodeTask implements IRequestTask {

    private static final String TAG = "VerificationCodeTask";
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
    public ResultInfo<VerificationCodeBeen> getResponseData() {
        ResultInfo<VerificationCodeBeen> result = new ResultInfo<VerificationCodeBeen>();
        if(mData!=null){
            String resultStr=new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    VerificationCodeBeen verificationCodeBeen = JSON.parseObject(resultStr, VerificationCodeBeen.class);
                    ZLog.Zlogi("verificationCodeBeen = " + verificationCodeBeen.toString(), TAG, LOGLEVEL);

                    if("false".equals(verificationCodeBeen.getSuccess())){
                        result.Success=false;
                        if (verificationCodeBeen.getMsg()!= null){
                            result.Message=verificationCodeBeen.getMsg();
                        }else {
                            result.Message = "获取验证码失败";
                        }
                    }else{
                        result.Data=verificationCodeBeen;
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
     * 请求验证码
     * @param phone   //电话号码
     * @param type   //验证码类型，字符串，UTF8编码，不进行加密,1登陆码,2注册码；
     * @return
     */
    public ResultInfo<VerificationCodeBeen> request(String phone,int type) {
        ResultInfo<VerificationCodeBeen> result = null;

        String url = Constant.HTTP_ALL+Constant.HTTP_GET_SECURITY_CODE;
        Hashtable<String, Object> params = new Hashtable<String, Object>(2);
        params.put("phone", phone);
        params.put("type", type);

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if(result==null){
                result=new ResultInfo<VerificationCodeBeen>();
            }
            result.Success=false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
