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
 * Created by Kevin on 2015/12/22.
 */
public class SendRetroactionTask implements IRequestTask{

    private byte[] mData;



    @Override
    public void setContext(byte[] bytes) {
        this.mData=bytes;
    }

    public DataResult<Boolean> request(String msgStr){
        DataResult<Boolean> result=null;

        String url= Constant.HTTP_ALL +Constant.HTTP_SEND_RETROACTIONTASK;
        try{
            Hashtable<String, Object> params = new Hashtable<>(2);
            params.put("phone", FggApplication.getUserInfo().getUserName());
            params.put("content",msgStr);

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
                String str=new String(this.mData);
                JSONObject jsonObject= JSON.parseObject(str);
                boolean success=jsonObject.getBoolean("success");
                result.setResultData(success);
                result.setSuccess(success);
                if(success){
                    result.setMessage("反馈成功");
                }else{
                    result.setMessage("请检查网络");
                }

            }else{
                result.setResultData(false);
                result.setSuccess(false);
                result.setMessage("请检查网络");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setResultData(false);
            result.setMessage("请检查网络");
        }

        return result;
    }
}
