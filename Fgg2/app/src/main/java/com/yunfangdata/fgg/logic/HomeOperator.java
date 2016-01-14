package com.yunfangdata.fgg.logic;

import com.yunfangdata.fgg.dto.GardenDto;
import com.yunfangdata.fgg.http.task.FuzzySearchTask;
import com.yunfangdata.fgg.http.task.GetAreaTask;
import com.yunfangdata.fgg.http.task.GetDistrictTask;
import com.yunfangdata.fgg.http.task.GetResidentialAreaByCondition;
import com.yunfangdata.fgg.http.task.GetSingleGardenTask;
import com.yunfangdata.fgg.http.task.GetSubWayTask;
import com.yunfangdata.fgg.model.Area;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.District;
import com.yunfangdata.fgg.model.GardenDetail;
import com.yunfangdata.fgg.model.ResidentialParameters;
import com.yunfangdata.fgg.model.SearchResult;
import com.yunfangdata.fgg.model.SubWay;

import java.util.List;

/**
 * Created by Kevin on 2015/12/13.
 * 主页逻辑类
 */
public class HomeOperator {

    /***
     * 获取行政区
     *
     * @param cityName 城市名拼音
     * @return
     */
    public static DataResult<List<District>> getDistrict(String cityName) {
        DataResult<List<District>> result = null;
        try {
            GetDistrictTask task = new GetDistrictTask();
            result = task.request(cityName);
        } catch (Exception e) {
            result = new DataResult<>(false, "网络异常");
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 获取城市地铁信息
     *
     * @param cityName 城市名拼音
     * @return
     */
    public static DataResult<List<SubWay>> getSubWayInfo(String cityName) {
        DataResult<List<SubWay>> result = null;
        try {
            GetSubWayTask task = new GetSubWayTask();
            result = task.request(cityName);
        } catch (Exception e) {
            result = new DataResult<>(false, "网络异常");
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 获取片区信息
     *
     * @param cityName 城市名拼音
     * @param district 行政区对象
     * @return
     */
    public static DataResult<District> getAreaInfo(String cityName, District district) {
        DataResult<District> result = new DataResult<>();
        try {
            GetAreaTask task = new GetAreaTask();
            DataResult<List<Area>> areaResult = task.request(cityName, district.getDistrictName());
            if (areaResult.getSuccess()) {
                List<Area> areas = areaResult.getResultData();
                district.setAreaList(areas);
                result.setResultData(district);
                result.setSuccess(true);
                result.setMessage("获取片区成功");
            } else {
                result.setSuccess(false);
                result.setMessage("获取片区失败");
            }

        } catch (Exception e) {
            result = new DataResult<>(false, "网络异常");
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 模糊查询小区数据
     * @param searchStr
     * @return
     */
    public static DataResult<List<SearchResult>> fuzzySearch(String searchStr){
        DataResult<List<SearchResult>> result;
        try{
            FuzzySearchTask task=new FuzzySearchTask();
            result=task.request(searchStr);
        }catch(Exception e){
            result=new DataResult<>(false,"");
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 根据ID 查询一个小区信息
     * @param id id
     * @return
     */
    public static DataResult<GardenDetail> getSingleGardenById(int id){
        DataResult<GardenDetail> result;
        try{
            GetSingleGardenTask task=new GetSingleGardenTask();
            result=task.request(id,"");
        }catch (Exception e){
            e.printStackTrace();
            result=new DataResult<>(false,"查询失败");
        }
        return result;
    }

}
