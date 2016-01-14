package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.BuyHouseReturnBeen;
import com.yunfangdata.fgg.model.GetAppraisementBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 获取买房估值的返回
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class BuyHouseReturnTask implements IRequestTask {

    private static final String TAG = "BuyHouseReturnTask";
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
    public ResultInfo<BuyHouseReturnBeen> getResponseData() {
        ResultInfo<BuyHouseReturnBeen> result = new ResultInfo<BuyHouseReturnBeen>();
        if (mData != null) {
            String resultStr = new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    BuyHouseReturnBeen buyHouseReturnBeen = JSON.parseObject(resultStr, BuyHouseReturnBeen.class);
                    ZLog.Zlogi("buyHouseReturnBeen = " + buyHouseReturnBeen.toString(), TAG, LOGLEVEL);

                    if (!buyHouseReturnBeen.isSuccess()) {
                        result.Success = false;
                        result.Message = "系统错误,请稍后重试";
                    } else {
                        result.Data = buyHouseReturnBeen;
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


    public ResultInfo<BuyHouseReturnBeen> request(GetAppraisementBeen gAB) {
        ResultInfo<BuyHouseReturnBeen> result = null;

        String url = Constant.HTTP_ALL + Constant.HTTP_GETAPPRAISEMENT;
        Hashtable<String, Object> params = new Hashtable<String, Object>(12);

        //城市名称
        String s0 = (gAB.getCity() != null) ? gAB.getCity() : "";
        params.put("city", s0);
        //小区名字
        String s001 = (gAB.getResidentialAreaName() != null) ? gAB.getResidentialAreaName() : "";
        params.put("residentialAreaName", s001);

        //小区面积
        String s002 = (gAB.getArea() > 0) ? gAB.getArea() + "" : "";
        params.put("area", s002);

        //小区ID
        String s1 = (gAB.getResidentialAreaId() != null) ? gAB.getResidentialAreaId() : "";
        params.put("residentialAreaId", s1);

        //住宅类型
        String s2 = (gAB.getHouseType() != null) ? gAB.getHouseType() : "";
        params.put("houseType", s2);

        //居室类型
        if (gAB.getUnitType() != null) {
            params.put("unitType", gAB.getUnitType());
        }

        //楼幢
        if (gAB.getBuildNumber() != null) {
            params.put("buildNumber", gAB.getBuildNumber());
        }

        //户号，字符串，UTF8编码，不进行加密；
        if (gAB.getHouseNumber() != null) {
            params.put("house_number", gAB.getHouseNumber());
        }

        //总楼层，int，
        if (gAB.getTotalfloor() > 0) {
            params.put("totalfloor", gAB.getTotalfloor());
        }

        //朝向，
        if (gAB.getToward() != null) {
            params.put("toward", gAB.getToward());
        }

        //建成年代，int，
        if (gAB.getBuiltTime() > 0) {
            params.put("builtTime", gAB.getBuiltTime());
        }
        //特殊因素，
        if (gAB.getSpecialFactor() != null) {
            params.put("specialFactor", gAB.getSpecialFactor());
        }

        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);

        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if (result == null) {
                result = new ResultInfo<BuyHouseReturnBeen>();
            }
            result.Success = false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
