package com.yunfangdata.fgg.http.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.model.DataResult;

import java.util.Hashtable;

/**
 * Created by Administrator on 2015/12/23.
 */
public class EditUserPasswordTask implements IRequestTask{

    private byte[] mData;

    @Override
    public void setContext(byte[] bytes) {
        this.mData=bytes;
    }

    public DataResult<Boolean> request(String newPwd,String oldPwd){
        DataResult<Boolean> result = null;

        String url = Constant.HTTP_ALL+Constant.HTTP_EDT_PWD;
        Hashtable<String, Object> params = new Hashtable<String, Object>(3);
        params.put("phone", FggApplication.getUserInfo().getUserName());
        params.put("newPwd", newPwd);
        params.put("oldPwd", oldPwd);

        CommonRequestPackage commRequest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commRequest, this);
            result = getResponseData();
        } catch (Exception e) {
            if(result==null){
                result=new DataResult<>(false,"请检查网络");
            }
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public DataResult<Boolean> getResponseData() {
        DataResult<Boolean> result = new DataResult<>();
        try{
            if(this.mData!=null){
                String jsonStr=new String(this.mData);
                JSONObject json=JSON.parseObject(jsonStr);
                String msg=json.getString("msg");
                boolean success=json.getBoolean("success");
                result.setMessage(msg);
                result.setSuccess(success);
                //{"msg":"新密码不能与旧密码相同","success":"false"}
            }else{
                result.setMessage("修改失败，请检查网络");
                result.setSuccess(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("");
            result.setSuccess(false);
        }
        return result;
    }
}
