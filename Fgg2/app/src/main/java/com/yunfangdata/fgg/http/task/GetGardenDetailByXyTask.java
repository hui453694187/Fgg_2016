package com.yunfangdata.fgg.http.task;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfang.framework.utils.ListUtil;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.GardenDetail;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Kevin on 2015/12/15.
 */
public class GetGardenDetailByXyTask implements IRequestTask {

    private byte[] mData;

    @Override
    public void setContext(byte[] bytes) {
        this.mData = bytes;
    }

    @Override
    public DataResult<List<GardenDetail>> getResponseData() {
        DataResult<List<GardenDetail>> result = new DataResult<>();
        try {
            if (this.mData != null) {
                List<GardenDetail> gardenDetailList = null;
                String resultStr = new String(this.mData);
                result = JSON.parseObject(resultStr, result.getClass());
                String listStr = result.getResultData().toString();

                gardenDetailList = JSON.parseArray(listStr, GardenDetail.class);
                if (!ListUtil.hasData(gardenDetailList)) {
                    gardenDetailList = new ArrayList<>();
                }
                result.setResultData(gardenDetailList);
            } else {
                result.setSuccess(false);
                result.setMessage("获取数据失败，请检查网络");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取数据失败，请检查网络");
            e.printStackTrace();
        }

        return result;
    }

    public DataResult<List<GardenDetail>> request(LatLngBounds latLngBounds) {
        DataResult<List<GardenDetail>> result;
        try {
            String url = Constant.HTTP_ALL + Constant.HTTP_GET_RESIDENTIAL_AREA;
            // 填充参数，key-value。key是接口要求传的变量名称
            Hashtable<String, Object> params = new Hashtable<>(9);
            LatLng northeast = latLngBounds.northeast;//东北角点 大
            LatLng southwest = latLngBounds.southwest;//西南角点 小
            params.put("minX", southwest.longitude);// min 经度
            params.put("maxX", northeast.longitude);//max  经度
            params.put("minY", southwest.latitude);// min  纬度
            params.put("maxY", northeast.latitude);// max  纬度
            params.put("pageIndex",1);
            params.put("pageSize",50);
            params.put("cityName", FggApplication.getInstance().city.getCityPinYin());

            CommonRequestPackage requestPackage = new CommonRequestPackage(url,
                    RequestTypeEnum.GET, params);

            YFHttpClient.request(requestPackage, this);
            result = getResponseData();
        } catch (Exception e) {
            result = new DataResult<>(false, "请检查网络");
            e.printStackTrace();
        }

        return result;
    }

}
