package com.yunfangdata.fgg.http.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfang.framework.utils.YFLog;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.UserInfo;

import java.util.Hashtable;

/**
 * Created by Kevin on 2015/12/21.
 * 修改用户信息
 */
public class SaveUserInfoTask implements IRequestTask{

    private byte[] mData;

    @Override
    public void setContext(byte[] bytes) {
        this.mData=bytes;
    }

    public DataResult<Boolean> request(UserInfo userInfo){
        DataResult<Boolean> result = null;
        String url= Constant.HTTP_ALL +Constant.HTTP_EDIT_USER_INFO;
        try{
            Hashtable<String, Object> params = new Hashtable<>(5);
            params.put("name", userInfo.getRealName());
            params.put("phone", userInfo.getUserName());
            params.put("gender", userInfo.getGender());
            params.put("birthday", userInfo.getBirthday());
            params.put("occupation", userInfo.getOccupation());

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
    public DataResult<Boolean> getResponseData() {
        DataResult<Boolean> result=new DataResult<>();
        try{
            if(this.mData!=null){
                String jsonStr=new String(this.mData);
                    JSONObject jsonObject=JSON.parseObject(jsonStr);
                String msg=jsonObject.getString("msg");
                boolean isSuccess=jsonObject.getBoolean("success");
                YFLog.d(jsonStr);
                result.setSuccess(isSuccess);
                result.setResultData(isSuccess);
                result.setMessage(msg);

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
