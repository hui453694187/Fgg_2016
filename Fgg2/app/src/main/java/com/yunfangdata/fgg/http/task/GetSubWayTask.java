package com.yunfangdata.fgg.http.task;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfang.framework.utils.YFLog;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.SubWay;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by Kevin on 2015/12/14.
 */
public class GetSubWayTask implements IRequestTask {

    private byte[] mData;

    @Override
    public void setContext(byte[] bytes) {
        this.mData=bytes;
    }

    @Override
    public DataResult<List<SubWay>> getResponseData() {
        DataResult<List<SubWay>> resultData=new DataResult<>();
        try{
            List<SubWay> subWaysList;
            if(mData!=null){
                String dataStr=new String(mData);
                resultData= JSON.parseObject(dataStr, resultData.getClass());
                String resultStr=resultData.getResultData().toString();
                subWaysList=JSON.parseArray(resultStr,SubWay.class);
                resultData.setResultData(subWaysList);
            }else{
                resultData.setSuccess(false);
                resultData.setMessage("请求服务器失败，检查网络");
            }
        }catch(Exception e){
            resultData.setSuccess(false);
            resultData.setMessage("服务器异常");
            e.printStackTrace();
        }


        return resultData;
    }

    public DataResult<List<SubWay>> request(String cityName){
        DataResult<List<SubWay>> result;
        String url= Constant.HTTP_ALL+Constant.HTTP_GET_SUBWAY;
        // 填充参数，key-value。key是接口要求传的变量名称
        Hashtable<String, Object> params = new Hashtable<String, Object>(4);
        params.put("cityName",cityName);
        CommonRequestPackage requestPackage = new CommonRequestPackage(url,
                RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(requestPackage, this);
            result = getResponseData();
        } catch (Exception e) {
            result=new DataResult<>(false,"服务器异常");
            e.printStackTrace();
        }
        return result;
    }
}
