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
import com.yunfangdata.fgg.model.PersonMessage;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by Kevin on 2015/12/22.
 *  消息中心--删除消息
 */
public class DeleteMsgTask implements IRequestTask{

    private byte[]mData;

    @Override
    public void setContext(byte[] bytes) {
        this.mData=bytes;
    }

    public DataResult<Boolean> request(List<PersonMessage> msgIds){
        DataResult<Boolean> result;
        try{
            String intefaceStr= Constant.HTTP_DELETE_MSG;
            //是否是单个删除
            boolean isSimgleDel=true;
            if(msgIds.size()>1){
                intefaceStr=Constant.HTTP_DELETE_ALL_MSG;
                isSimgleDel=false;
            }
            StringBuilder msgIdStr=new StringBuilder();
            for(PersonMessage msgId:msgIds){
                msgIdStr.append(msgId.getXiaoXiId()+",");
            }
            msgIdStr.delete(msgIdStr.length()-1,msgIdStr.length());

            String url= Constant.HTTP_ALL2 +intefaceStr;
            try{
                Hashtable<String, Object> params = new Hashtable<>(2);
                params.put("phone", FggApplication.getUserInfo().getUserName());
                params.put("xiaoxiId", msgIdStr.toString());

                CommonRequestPackage requestPackage = new CommonRequestPackage(url,
                        RequestTypeEnum.GET, params);
                YFHttpClient.request(requestPackage, this);
                if(isSimgleDel){//单个删除
                    result=getResponseData2();
                }else{// 批量删除返回结果
                    result = getResponseData();
                }

            }catch(Exception e){
                e.printStackTrace();
                result=new DataResult<>(false,"网络异常");
            }
        }catch (Exception e){
            e.printStackTrace();
            result=new DataResult<>(false,"检查网络");
        }

        return result;
    }

    public DataResult<Boolean> getResponseData2() {
        DataResult<Boolean> result=new DataResult<>();
        try{
            if(this.mData!=null){
                String str=new String(this.mData);
                JSONObject jsonObject= JSON.parseObject(str);
                String success=jsonObject.getString("success");

                if("ture".equals(success)){
                    result.setSuccess(true);
                    result.setResultData(true);
                }else{
                    result.setSuccess(false);
                    result.setResultData(false);
                }
            }else{
                result.setResultData(false);
                result.setSuccess(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setResultData(false);
            result.setMessage("请检查网络");
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

            }else{
                result.setResultData(false);
                result.setSuccess(false);
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
