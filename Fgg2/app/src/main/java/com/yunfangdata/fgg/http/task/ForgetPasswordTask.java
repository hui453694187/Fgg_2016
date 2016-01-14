package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.ForgetPasswordBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 请求修改密码
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class ForgetPasswordTask implements IRequestTask {

    private static final String TAG = "ForgetPasswordTask";
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
    public ResultInfo<ForgetPasswordBeen> getResponseData() {
        ResultInfo<ForgetPasswordBeen> result = new ResultInfo<ForgetPasswordBeen>();
        if(mData!=null){
            String resultStr=new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    ForgetPasswordBeen forgetPasswordVerificationCodeBeen = JSON.parseObject(resultStr, ForgetPasswordBeen.class);
                    ZLog.Zlogi("forgetPasswordVerificationCodeBeen = " + forgetPasswordVerificationCodeBeen.toString(), TAG, LOGLEVEL);

                    if("false".equals(forgetPasswordVerificationCodeBeen.getSuccess())){
                        result.Success=false;
                        if (forgetPasswordVerificationCodeBeen.getMsg()!= null){
                            result.Message=forgetPasswordVerificationCodeBeen.getOthers();
                        }else {
                            result.Message = "获取验证码失败";
                        }
                    }else{
                        result.Data=forgetPasswordVerificationCodeBeen;
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
     * 请求修改密码
     *
     * @param phone   //电话号码
     * @param newPwd   //新密码
     * @param captcha   //验证码
     * @return
     */
    public ResultInfo<ForgetPasswordBeen> request(String phone,String newPwd,String captcha) {
        ResultInfo<ForgetPasswordBeen> result = null;

        String url = Constant.HTTP_ALL+Constant.HTTP_GET_RESET_PWD;
        Hashtable<String, Object> params = new Hashtable<String, Object>(3);
        params.put("phone", phone);
        params.put("newPwd", newPwd);
        params.put("captcha", captcha);

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if(result==null){
                result=new ResultInfo<ForgetPasswordBeen>();
            }
            result.Success=false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
