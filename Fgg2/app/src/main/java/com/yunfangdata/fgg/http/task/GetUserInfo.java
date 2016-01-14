package com.yunfangdata.fgg.http.task;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.dto.UserInfoDto;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.UserInfo;

import java.util.Hashtable;

/**
 * Created by Kevin on 2015/12/21.
 * 获取用户信息获取用户信息 请求
 */
public class GetUserInfo implements IRequestTask{

    private byte[] mData;

    @Override
    public void setContext(byte[] bytes) {
        this.mData=bytes;
    }

    public DataResult<UserInfo> request(){
        DataResult<UserInfo> result = null;
        String url= Constant.HTTP_ALL +Constant.HTTP_GET_USER_INFO;
        try{
            Hashtable<String, Object> params = new Hashtable<>(2);
            params.put("phone", FggApplication.getUserInfo().getUserName());

            CommonRequestPackage requestPackage = new CommonRequestPackage(url,
                    RequestTypeEnum.GET, params);
            YFHttpClient.request(requestPackage, this);
            result = getResponseData();
        }catch(Exception e){
            e.printStackTrace();
            result=new DataResult<>(false,"网络异常");
        }
        return result;
    }

    @Override
    public DataResult<UserInfo> getResponseData() {
        DataResult<UserInfo> result=new DataResult<>();
        try{
            if(this.mData!=null){
                String jsonStr=new String(this.mData);
                UserInfoDto user;
                user=JSON.parseObject(jsonStr,UserInfoDto.class);
                if(user==null){
                    result.setSuccess(false);
                    result.setMessage("网络异常");
                }else{
                    result.setSuccess(user.isSuccess());
                    result.setResultData(user.getUser());
                }
            }else{
                result.setSuccess(false);
                result.setMessage("网络异常");
            }

        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("网络异常");
        }
        return result;
    }
}
