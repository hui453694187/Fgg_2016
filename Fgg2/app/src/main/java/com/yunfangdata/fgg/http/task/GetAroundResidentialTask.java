package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.AroundResidentialBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 获取小区房子详情的任务栈
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class GetAroundResidentialTask implements IRequestTask {

    private static final String TAG = "GetAroundResidentialTask";
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
    public ResultInfo<AroundResidentialBeen> getResponseData() {
        ResultInfo<AroundResidentialBeen> result = new ResultInfo<AroundResidentialBeen>();
        if(mData!=null){
            String resultStr=new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    AroundResidentialBeen aroundDetailBeen = JSON.parseObject(resultStr, AroundResidentialBeen.class);
                    ZLog.Zlogi("aroundDetailBeen = " + aroundDetailBeen.toString(), TAG, LOGLEVEL);

                    if("false".equals(aroundDetailBeen.getSuccess())){
                        result.Success=false;
                        result.Message=aroundDetailBeen.getMsg();
                    }else{
                        result.Data=aroundDetailBeen;
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
     * 请求小区周边详情
     * @param cityName   //城市名称  例如: 北京
     * @param residentialAreaID  //小区id   例如:12944
     * @return
     */
    public ResultInfo<AroundResidentialBeen> request(String cityName,int residentialAreaID) {
        ResultInfo<AroundResidentialBeen> result = null;

        String url = Constant.HTTP_ALL+Constant.HTTP_GETAROUNDRESIDENTIALAREAINFO;
        Hashtable<String, Object> params = new Hashtable<String, Object>(2);
        params.put("cityName", cityName);
        params.put("residentialAreaID",residentialAreaID );

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if(result==null){
                result=new ResultInfo<AroundResidentialBeen>();
            }
            result.Success=false;
            result.Message = e.getMessage();
        }

        return result;
    }

}
