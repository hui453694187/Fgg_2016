package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfang.framework.utils.YFLog;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.PasswordLoginBeen;
import com.yunfangdata.fgg.model.ResultInfo;

import java.util.Hashtable;

/**
 * 密码登录的任务站
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class PasswordLoginTask implements IRequestTask {

    private static final String TAG = "PasswordLoginTask";
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
    public ResultInfo<PasswordLoginBeen> getResponseData() {
        ResultInfo<PasswordLoginBeen> result = new ResultInfo<PasswordLoginBeen>();
        if(mData!=null){
            String resultStr=new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    YFLog.d("数据返回 = " + resultStr);
                    PasswordLoginBeen passwordLoginBeen = JSON.parseObject(resultStr, PasswordLoginBeen.class);
                    YFLog.d("passwordLoginBeen = " + passwordLoginBeen.toString());

                    if("false".equals(passwordLoginBeen.getSuccess())){
                        result.Success=false;
                        if (passwordLoginBeen.getMsg()!= null){
                            result.Message=passwordLoginBeen.getMsg();
                        }else {
                            result.Message = "登录失败";
                        }
                    }else{
                        result.Data=passwordLoginBeen;
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
     * 请求登录
     * @param username  //用户名
     * @param psw   //密码
     * @return
     */
    public ResultInfo<PasswordLoginBeen> request(String username,String psw) {
        ResultInfo<PasswordLoginBeen> result = null;

        String url = Constant.HTTP_ALL+Constant.HTTP_LOGIN_BY_USERNAME;
        Hashtable<String, Object> params = new Hashtable<String, Object>(2);
        params.put("userName", username);
        params.put("pwd", psw);

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if(result==null){
                result=new ResultInfo<PasswordLoginBeen>();
            }
            result.Success=false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
