package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.EElistBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 获取小区房子详情的任务站
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class EntrustListActivityTask implements IRequestTask {

    private static final String TAG = "EntrustListActivityTask";
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
    public ResultInfo<EElistBeen> getResponseData() {
        ResultInfo<EElistBeen> result = new ResultInfo<EElistBeen>();
        if (mData != null) {
            String resultStr = new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    EElistBeen eElistBeen = JSON.parseObject(resultStr, EElistBeen.class);
                    ZLog.Zlogi("eElistBeen = " + eElistBeen.toString(), TAG, LOGLEVEL);

                    if ("false".equals(eElistBeen.getSuccess())) {
                        result.Success = false;
                        result.Message = "";
                    } else {
                        result.Data = eElistBeen;
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


    /**
     * 请求小区详情
     *
     * @param phone
     * @param beginTime
     * @param endTime
     * @param pageSize
     * @param curPage
     * @param state     委托状态:0.待支付,1.未受理,2.受理中,3.已完成,4.已撤单
     * @return
     */
    public ResultInfo<EElistBeen> request(String phone, String beginTime, String endTime, String pageSize, String curPage, String state) {
        ResultInfo<EElistBeen> result = null;

        String url = Constant.HTTP_ALL + Constant.HTTP_GET_LOAD_WEITUOJILU;
        Hashtable<String, Object> params = new Hashtable<String, Object>(7);
        //用户Id
        phone = (phone == null) ? "" : phone;  //是否为空
        params.put("phone", phone);

        //开始时间
        beginTime = (beginTime == null) ? "" : beginTime;  //是否为空
        params.put("beginTime", beginTime);

        //结束时间
        endTime = (endTime == null) ? "" : endTime;  //是否为空
        params.put("endTime", endTime);

        //页数
        pageSize = (pageSize == null) ? "100" : pageSize;  //是否为空
        params.put("pageSize", pageSize);

        //当前页
        curPage = (curPage == null) ? "1" : curPage;  //是否为空
        params.put("curPage", curPage);
        //状态
        state = (state == null) ? "" : state;  //是否为空
        params.put("state", state);

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if (result == null) {
                result = new ResultInfo<EElistBeen>();
            }
            result.Success = false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
