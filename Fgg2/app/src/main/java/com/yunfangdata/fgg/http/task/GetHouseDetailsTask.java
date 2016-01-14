package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.HouseDetailBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 获取小区房子详情的任务站
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class GetHouseDetailsTask implements IRequestTask {

    private static final String TAG = "getHouseDetailsTask";
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
    public ResultInfo<HouseDetailBeen> getResponseData() {
        ResultInfo<HouseDetailBeen> result = new ResultInfo<HouseDetailBeen>();
        if(mData!=null){
            String resultStr=new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    HouseDetailBeen houseDetailBeen = JSON.parseObject(resultStr, HouseDetailBeen.class);
                    ZLog.Zlogi("houseDetailBeen = " + houseDetailBeen.toString(), TAG, LOGLEVEL);

                    if("false".equals(houseDetailBeen.getSuccess())){
                        result.Success=false;
                        result.Message=houseDetailBeen.getMessage();
                    }else{
                        result.Data=houseDetailBeen;
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
     * @param residentialAreaID  //小区id   例如:12944
     * @return
     */
    public ResultInfo<HouseDetailBeen> request(String cityName,int residentialAreaID) {
        ResultInfo<HouseDetailBeen> result = null;

        String url = Constant.HTTP_ALL+Constant.HTTP_GETRESIDENTIALAREADETAIL;
        Hashtable<String, Object> params = new Hashtable<String, Object>(2);
        params.put("cityName", cityName);
        params.put("residentialAreaID", residentialAreaID);

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if(result==null){
                result=new ResultInfo<HouseDetailBeen>();
            }
            result.Success=false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
