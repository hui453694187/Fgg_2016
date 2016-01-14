package com.yunfangdata.fgg.http.task;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfang.framework.utils.ListUtil;
import com.yunfang.framework.utils.YFLog;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.dto.AreaDto;
import com.yunfangdata.fgg.model.Area;
import com.yunfangdata.fgg.model.DataResult;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by Kevin on 2015/12/14.
 */
public class GetAreaTask implements IRequestTask {

    private byte [] mData;

    @Override
    public void setContext(byte[] bytes) {
        this.mData=bytes;
    }

    @Override
    public DataResult<List<Area>> getResponseData() {
        DataResult<List<Area>> result=new DataResult<>();
        try{
            List<Area> areas;
            if(this.mData!=null){
                String resultStr=new String(this.mData);
                result=JSON.parseObject(resultStr, result.getClass());
                String dtoStr=result.getResultData().toString();
                List<AreaDto> areaDos=JSON.parseArray(dtoStr, AreaDto.class);
                String areasSt= areaDos.get(0).getChildren().toString();
                areas=JSON.parseArray(areasSt,Area.class);
                if(ListUtil.hasData(areas)){
                    result.setResultData(areas);
                    result.setMessage("解析成功");
                }else{
                    result.setSuccess(false);
                    result.setMessage("获取片区信息失败，请检查网络");
                }
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("服务器异常！");
        }

        return result;
    }


    public DataResult<List<Area>> request(String cityName,String districtName){
        DataResult<List<Area>> result=null;
        String url= Constant.HTTP_ALL+Constant.HTTP_GET_AEAR;
        // 填充参数，key-value。key是接口要求传的变量名称
        Hashtable<String, Object> params = new Hashtable<String, Object>(4);
        params.put("cityName",cityName);//城市名
        params.put("districtName",districtName);//行政区名
        CommonRequestPackage requestPackage = new CommonRequestPackage(url,
                RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(requestPackage, this);
            result = getResponseData();
        } catch (Exception e) {
            result=new DataResult<>(false,"服务器异常");
            e.printStackTrace();
        }
        return result;
    }
}
