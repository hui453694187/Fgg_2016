package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.HyporisyQueryBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 进度查询
 * <p/>
 * Created by zjt on 2015-12-14.
 */
public class HypocrisyQueryTask implements IRequestTask {

    private static final String TAG = "HypocrisyQueryTask";
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
    public ResultInfo<HyporisyQueryBeen> getResponseData() {
        ResultInfo<HyporisyQueryBeen> result = new ResultInfo<HyporisyQueryBeen>();
        if (mData != null) {
            String resultStr = new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    HyporisyQueryBeen hyporisyQueryBeen = JSON.parseObject(resultStr, HyporisyQueryBeen.class);
                    ZLog.Zlogi("hyporisyQueryBeen = " + hyporisyQueryBeen.toString(), TAG, LOGLEVEL);

                    if (!hyporisyQueryBeen.getSuccess().equals("true")) {
                        result.Success = false;
                        result.Message = "报告编号错误";
                    } else {
                        result.Data = hyporisyQueryBeen;
                    }
                }

            } catch (Exception e) {
                result.Success = false;
                result.Message = "服务器异常! 请重试。";
                e.printStackTrace();
            }
        } else {
            result.Success = false;
            result.Message = "请求服务器数据失败！";
        }
        return result;
    }


    public ResultInfo<HyporisyQueryBeen> request(String cityName,String reportId,String weituorenName) {
        ResultInfo<HyporisyQueryBeen> result = null;

        String url = Constant.HTTP_ALL + Constant.HTTP_GETREPORTDIFFERENCE;
        Hashtable<String, Object> params = new Hashtable<String, Object>(3);
        params.put("cityName", cityName);
        params.put("reportId", reportId);
        params.put("weituorenName", weituorenName);

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if (result == null) {
                result = new ResultInfo<HyporisyQueryBeen>();
            }
            result.Success = false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
