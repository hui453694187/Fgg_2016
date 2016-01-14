package com.yunfangdata.fgg.http.task;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfang.framework.utils.YFLog;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.District;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by kevin on 2015/12/13.
 * 请求城市行政区信息
 */
public class GetDistrictTask implements IRequestTask {

    private byte[] mData;

    @Override
    public void setContext(byte[] mData) {
        this.mData=mData;
    }

    @Override
    public DataResult<List<District>> getResponseData() {
        DataResult<List<District>> result=new DataResult<List<District>>();
        List<District> distList=null;
        try {
            if(this.mData!=null){
                String resultString = new String(this.mData);
                result=JSON.parseObject(resultString,result.getClass());
                String dataStr=result.getResultData().toString();
                distList=JSON.parseArray(dataStr,District.class);
                result.setResultData(distList);

            }else{
                result.setSuccess(false);
                result.setMessage("网络异常，请检查网络");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("网络异常，请检查网络");
            e.printStackTrace();
        }

        return result;
    }

    public DataResult<List<District>> request(String cityName){
        DataResult<List<District>> result;
        String url= Constant.HTTP_ALL+Constant.HTTP_GET_DISTRICT;
        // 填充参数，key-value。key是接口要求传的变量名称
        Hashtable<String, Object> params = new Hashtable<String, Object>(9);
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
