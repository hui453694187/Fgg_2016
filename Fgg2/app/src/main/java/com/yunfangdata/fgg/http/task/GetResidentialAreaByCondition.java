package com.yunfangdata.fgg.http.task;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfang.framework.utils.ListUtil;
import com.yunfang.framework.utils.StringUtil;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.GardenDetail;
import com.yunfangdata.fgg.model.ResidentialParameters;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Kevin on 2015/12/16.
 *   条件查询小区信息
 */
public class GetResidentialAreaByCondition implements IRequestTask {

    private byte[]mData;

    @Override
    public void setContext(byte[] bytes) {
        this.mData=bytes;
    }

    @Override
    public DataResult<List<GardenDetail>> getResponseData() {
        DataResult<List<GardenDetail>> result=new DataResult<>();
        try{
            if(this.mData!=null){
                String resultStr=new String(this.mData);
                List<GardenDetail> gardenDetails=null;
                result=JSON.parseObject(resultStr,result.getClass());
                String listDataStr=result.getResultData().toString();
                gardenDetails=JSON.parseArray(listDataStr,GardenDetail.class);
                if(!ListUtil.hasData(gardenDetails)){
                    gardenDetails=new ArrayList<>();
                }
                result.setResultData(gardenDetails);
            }else{
                result.setSuccess(false);
                result.setMessage("查询失败，请检查网络");
            }
        }catch(Exception e){
            result.setSuccess(false);
            result.setMessage("查询失败，请检查网络");
            e.printStackTrace();
        }

        return result;
    }

    public DataResult<List<GardenDetail>> request(ResidentialParameters parameters){
        DataResult<List<GardenDetail>> result;
        String url= Constant.HTTP_ALL + Constant.HTTP_GET_RESIDENTIAL_BY_CONDITION;
        try{
            // 填充参数，key-value。key是接口要求传的变量名称
            Hashtable<String, Object> params =getParameters(parameters);

            CommonRequestPackage requestPackage = new CommonRequestPackage(url,
                    RequestTypeEnum.GET, params);

            YFHttpClient.request(requestPackage, this);
            result = getResponseData();
        }catch(Exception e){
            result = new DataResult<>(false, "请检查网络");
            e.printStackTrace();
        }
        return result;
    }

    private Hashtable<String, Object> getParameters(ResidentialParameters parameters){
        // 填充参数，key-value。key是接口要求传的变量名称
        Hashtable<String, Object> params = new Hashtable<>(17);
        params.put("pageIndex",1);
        params.put("pageSize",50);
        params.put("cityName", FggApplication.getInstance().city.getCityPinYin());

        setParams("districtName", parameters.getDistrictName(), params);
        //params.put("districtName", 1);//行政区

        setParams("plate", parameters.getPlate(), params);
        //params.put("plate", 1);//片区，字符串

        setParams("residentialAreaName", parameters.getResidentialAreaName(), params);
        //params.put("residentialAreaName", 1);//小区名称，字符串

        setParams("unitType", parameters.getUnitType(), params);
        //params.put("unitType", 1);//居室类型

        setParams("totalPrice", parameters.getTotalPrice(), params);
        //params.put("totalPrice", 1);//房屋总价

        setParams("totalPrice", parameters.getArea(), params);
        //params.put("area", 1);//面积，float

        setParams("specialFactor", parameters.getSpecialFactor(), params);
        //params.put("specialFactor", 1);//特殊条件，字符串

        setParams("subWayNO", parameters.getSubWayNO(), params);
        //params.put("subWayNO", 1);//地铁线名，字符串

        setParams("subWayName", parameters.getSubWayName(), params);
        //params.put("subWayName", 1);//地铁站名，字符串

        setParams("schoolName", parameters.getSchoolName(), params);
        //params.put("schoolName", 1);//学校名称，字符串

        setParams("schoolType", parameters.getSchoolType(), params);
        //params.put("schoolType", 1);//学校类型，字符串 举值，枚举类型为｛小学、初中｝

        setParams("maxPrice", parameters.getMaxPrice(), params);
        //params.put("maxPrice", 1);//最大价格

        setParams("minPrice", parameters.getMinPrice(), params);
        //params.put("minPrice", 1);//最小价格

        setParams("type", parameters.getType(), params);
        //params.put("type",1);//zongjia,shoufu 找房类型

        return params;
    }

    private void setParams(String key,String value,Hashtable<String, Object> params){
        if(!StringUtil.IsNullOrEmpty(value)){
            params.put(key,value);
        }
    }

    private void setParams(String key,int value,Hashtable<String, Object> params){
        if(value>0){
            params.put(key,value);
        }
    }

    private void setParams(String key,double value,Hashtable<String, Object> params){
        if(value>0){
            params.put(key,value);
        }
    }

    private void setParams(String key,float value,Hashtable<String, Object> params){
        if(value>0){
            params.put(key,value);
        }
    }
}
