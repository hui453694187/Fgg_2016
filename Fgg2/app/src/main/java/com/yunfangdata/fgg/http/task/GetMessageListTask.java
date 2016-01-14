package com.yunfangdata.fgg.http.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfang.framework.utils.ListUtil;
import com.yunfang.framework.utils.YFLog;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.logic.PersonOperator;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.PersonMessage;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Kevin on 2015/12/21.
 * 获取消息列表
 */
public class GetMessageListTask implements IRequestTask{

    private byte[]mData;

    @Override
    public void setContext(byte[] bytes) {
        this.mData=bytes;
    }

    public DataResult<List<PersonMessage>> request(){
        DataResult<List<PersonMessage>> result;
        try{
            String url= Constant.HTTP_ALL+Constant.HTTP_GET_MESSAGE_INFO;
            Hashtable<String, Object> params = new Hashtable<>(3);
            params.put("phone", FggApplication.getUserInfo().getUserName());
            params.put("pageSize",20);
            params.put("curPage",1);

            CommonRequestPackage requestPackage = new CommonRequestPackage(url,
                    RequestTypeEnum.GET, params);
            YFHttpClient.request(requestPackage, this);
            result = getResponseData();
        }catch (Exception e){
            e.printStackTrace();
            result=new DataResult<>(false,"请检查网络");
        }


        return result;
    }

    @Override
    public DataResult<List<PersonMessage>> getResponseData() {
        DataResult<List<PersonMessage>> result=new DataResult<>();
        try{
            if(this.mData!=null){
                String strJson=new String(this.mData);
                YFLog.d(strJson);
                JSONObject resObj= JSON.parseObject(strJson);
                String jsonData=resObj.getString("data");
                boolean success=resObj.getBoolean("success");
                List<PersonMessage> messageList=JSON.parseArray(jsonData,PersonMessage.class);
                YFLog.d(messageList.toString());
                if(!ListUtil.hasData(messageList)){
                    messageList=new ArrayList<>();
                }
                result.setResultData(messageList);
                result.setSuccess(success);

            }else{
                result.setSuccess(false);
                result.setMessage("网络异常");
            }
        }catch(Exception e){
            e.printStackTrace();
            result=new DataResult<>(false,"网络异常");
        }

        return result;
    }
}
