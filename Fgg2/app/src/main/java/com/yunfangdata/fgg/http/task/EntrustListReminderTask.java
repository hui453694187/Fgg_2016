package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.EntrustListReminderBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 催单和撤单
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class EntrustListReminderTask implements IRequestTask {

    private static final String TAG = "EntrustListReminderTask";
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
    public ResultInfo<EntrustListReminderBeen> getResponseData() {
        ResultInfo<EntrustListReminderBeen> result = new ResultInfo<EntrustListReminderBeen>();
        if (mData != null) {
            String resultStr = new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    EntrustListReminderBeen entrustListReminderBeen = JSON.parseObject(resultStr, EntrustListReminderBeen.class);
                    ZLog.Zlogi("entrustListReminderBeen = " + entrustListReminderBeen.toString(), TAG, LOGLEVEL);

                    if ("false".equals(entrustListReminderBeen.getSuccess())) {
                        result.Success = false;
                        result.Message = "";
                    } else {
                        result.Data = entrustListReminderBeen;
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
     * 请求催单,撤单
     *
     * @return
     */
    public ResultInfo<EntrustListReminderBeen> request(String entrustID, String type, String city) {
        ResultInfo<EntrustListReminderBeen> result = null;

        String url = Constant.HTTP_ALL + Constant.HTTP_GET_DO_WEITUO_PINGGU_CD;
        Hashtable<String, Object> params = new Hashtable<String, Object>(7);
        //反馈id
        entrustID = (entrustID == null) ? "" : entrustID;  //是否为空
        params.put("entrustID", entrustID);

        //操作:催单,撤单
        type = (type == null) ? "催单" : type;  //是否为空
        params.put("type", type);

        //城市(拼音)
        city = (city == null) ? "beijing" : city;  //是否为空
        params.put("city", city);


        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if (result == null) {
                result = new ResultInfo<EntrustListReminderBeen>();
            }
            result.Success = false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
