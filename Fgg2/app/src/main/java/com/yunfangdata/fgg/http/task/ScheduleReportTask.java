package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.ScheduleReportBeen;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 进度查询
 * <p/>
 * Created by zjt on 2015-12-14.
 */
public class ScheduleReportTask implements IRequestTask {

    private static final String TAG = "ScheduleReportTask";
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
    public ResultInfo<ScheduleReportBeen> getResponseData() {
        ResultInfo<ScheduleReportBeen> result = new ResultInfo<ScheduleReportBeen>();
        if (mData != null) {
            String resultStr = new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    ScheduleReportBeen scheduleReportBeen = JSON.parseObject(resultStr, ScheduleReportBeen.class);
                    ZLog.Zlogi("scheduleReportBeen = " + scheduleReportBeen.toString(), TAG, LOGLEVEL);

                    if (!scheduleReportBeen.getSuccess().equals("true")) {
                        result.Success = false;
                        result.Message = "报告编号错误";
                    } else {
                        result.Data = scheduleReportBeen;
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


    public ResultInfo<ScheduleReportBeen> request(String cityName,String reportId) {
        ResultInfo<ScheduleReportBeen> result = null;

        String url = Constant.HTTP_ALL + Constant.HTTP_GETREPORTRATE;
        Hashtable<String, Object> params = new Hashtable<String, Object>(2);
        params.put("cityName", cityName);
        params.put("reportId", reportId);

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if (result == null) {
                result = new ResultInfo<ScheduleReportBeen>();
            }
            result.Success = false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
