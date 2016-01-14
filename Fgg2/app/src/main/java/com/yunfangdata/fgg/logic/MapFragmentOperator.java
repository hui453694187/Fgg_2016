package com.yunfangdata.fgg.logic;

import com.baidu.mapapi.model.LatLngBounds;
import com.yunfangdata.fgg.http.task.GetGardenDetailByXyTask;
import com.yunfangdata.fgg.http.task.GetResidentialAreaByCondition;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.GardenDetail;
import com.yunfangdata.fgg.model.ResidentialParameters;

import java.util.List;

/**
 * Created by Kevin on 2015/12/14.
 */
public class MapFragmentOperator {

    /***
     * 获取当前中心矩形区域内所有的小区信息
     * @param latLngBounds 当前地图现在范围内的 坐标信息
     * @return
     */
    public static DataResult<List<GardenDetail>> getGardenDetailByXY(LatLngBounds latLngBounds){
        DataResult<List<GardenDetail>> result;
        try {
            GetGardenDetailByXyTask task=new GetGardenDetailByXyTask();
            result=task.request(latLngBounds);
        } catch (Exception e) {
            e.printStackTrace();
            result=new DataResult<>(false,"网络异常");
        }
        return result;
    }

    /***
     * 根据条件获取小区信息
     * @param parameters
     * @return
     */
    public static DataResult<List<GardenDetail>> getGardenDetailByCondition(ResidentialParameters parameters){
        DataResult<List<GardenDetail>> result;
        try{
            GetResidentialAreaByCondition task=new GetResidentialAreaByCondition();
            result=task.request(parameters);
        }catch(Exception e){
            result=new DataResult<>(false,"请求失败，请检查网络。");
            e.printStackTrace();
        }
        return result;

    }
}
