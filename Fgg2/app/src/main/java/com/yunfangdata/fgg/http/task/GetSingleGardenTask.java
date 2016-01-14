package com.yunfangdata.fgg.http.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.dto.GardenDto;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.GardenDetail;

import java.util.Hashtable;

/**
 * Created by Kevin on 2015/12/19.
 */
public class GetSingleGardenTask implements IRequestTask {

    private byte[] mData;

    @Override
    public void setContext(byte[] bytes) {
        this.mData = bytes;
    }

    public DataResult<GardenDetail> request(int areaId, String areaName) {
        DataResult<GardenDetail> result;
        try {
            String url = Constant.HTTP_ALL + Constant.HTTP_GET_SINGLE_GARDEN;
            // 填充参数，key-value。key是接口要求传的变量名称
            Hashtable<String, Object> params = new Hashtable<>(3);
            params.put("cityName", FggApplication.getInstance().city.getCityPinYin());
            params.put("residentialAreaID", areaId);
            params.put("residentialAreaName", areaName);
            CommonRequestPackage requestPackage = new CommonRequestPackage(url,
                    RequestTypeEnum.GET, params);
            YFHttpClient.request(requestPackage, this);
            result = getResponseData();
        } catch (Exception e) {
            e.printStackTrace();
            result = new DataResult<>();
        }
        return result;
    }

    @Override
    public DataResult<GardenDetail> getResponseData() {
        DataResult<GardenDetail> result = new DataResult<>();
        try {
            if (this.mData != null) {
                String str = new String(this.mData);
                DataResult<JSONObject> resultDto = JSON.parseObject(str,DataResult.class);
                String dataStr=resultDto.getResultData().toJSONString();
                GardenDto gdDTO = JSON.parseObject(dataStr, GardenDto.class);
                if (gdDTO == null) {
                    result.setSuccess(false);
                    result.setMessage("查询失败！");
                    return result;
                }
                GardenDetail gd=gdDTO.getGardenDetail();
                result.setResultData(gd);
                result.setSuccess(resultDto.getSuccess());
                result.setMessage(resultDto.getMessage());
                result.setEndTime(resultDto.getEndTime());
                result.setStartTime(resultDto.getStartTime());
            } else {
                result.setSuccess(false);
                result.setMessage("查询失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询失败！");
        }
        return result;
    }
}
