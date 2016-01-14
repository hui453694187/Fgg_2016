package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.BuyHouseBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 获取小区房子详情的任务站
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class BuyHouseSearchTask implements IRequestTask {

    private static final String TAG = "BuyHouseSearchTask";
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
    public ResultInfo<BuyHouseBeen> getResponseData() {
        ResultInfo<BuyHouseBeen> result = new ResultInfo<BuyHouseBeen>();
        if(mData!=null){
            String resultStr=new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    BuyHouseBeen buyHouseBeen = JSON.parseObject(resultStr, BuyHouseBeen.class);
                    ZLog.Zlogi("buyHouseBeen = " + buyHouseBeen.toString(), TAG, LOGLEVEL);

                    if("false".equals(buyHouseBeen.getSuccess())){
                        result.Success=false;
                        result.Message=buyHouseBeen.getMessage();
                    }else{
                        result.Data=buyHouseBeen;
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
     * 请求小区详情
     * @param cityName   //城市名称  例如: 北京
     * @param content  //检索内容，字符串，UTF8编码，不进行加密；支持首字母查询
     * @param count  //返回条数，int，utf8编码，不进行加密；
     * @return
     */
    public ResultInfo<BuyHouseBeen> request(String cityName,String content,int count) {
        ResultInfo<BuyHouseBeen> result = null;

        String url = Constant.HTTP_ALL+Constant.HTTP_GETRESIDENTIALAREABYFUZZYSEARCH;
        Hashtable<String, Object> params = new Hashtable<String, Object>(3);
        params.put("cityName", cityName);
        params.put("content", content);
        params.put("count", count);

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if(result==null){
                result=new ResultInfo<BuyHouseBeen>();
            }
            result.Success=false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
