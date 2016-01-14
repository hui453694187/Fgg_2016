package com.yunfangdata.fgg.viewmodel;

import android.util.SparseArray;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.yunfangdata.fgg.model.GardenDetail;
import com.yunfangdata.fgg.ui.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2015/12/14.
 *  地图界面ViewModel
 */
public class MapViewModel {

    /**根据坐标中心点，获取小区信息*/
    public static final int GET_GARDEN_DETAIL_BY_XY=101;
    /**创建地图标注物 *//*
    public static final int CREATE_DRAW_MARK_TO_MAP=103;*/

    /** 地图默认缩放级别 */
    public static final float ZOOM_LEVEL =13;
    /** 搜索小区后的地图所犯级别 */
    public static final float THE_ZOOM_LEVEL =17;

    public static final float CRITICALTHE_ZOOM_LEVEL=13.5f;
    /** 父activity 引用 */
    private HomeActivity homeActivity;

    /**未选中标注图标*/
    public BitmapDescriptor markBitmap;
    /** 选中标注图标*/
    public BitmapDescriptor checkableMarkBitmap;

    public BitmapDescriptor locationMarkBitmap;

    public LatLng userLatLng;
    /**行政区价格textView*/
    public TextView areaTv;

    /** 当前选中的mark*/
    public Marker currentMarker;
    /** 上一个选中的mark*/
    public Marker lastMarker;
    /**当前选中的小区详情信息*/
    private GardenDetail currentGardenDetail;

    /***
     * 因为定位信息不准确， 重复定位一次
     */
   // public boolean isFirstLocation=true;

    private List<Marker> markers;

    private List<OverlayOptions> optionsList;

    private SparseArray<Integer> markerSparseArray;
    public boolean firstLocation=false;

    public MapViewModel() {
    }


    public HomeActivity getHomeActivity() {
        return homeActivity;
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    public List<Marker> getMarkers() {
        if(markers ==null){
            markers =new ArrayList<>();
        }
        return markers;
    }

    public void setMarkers(List<Marker> markers) {
        this.markers = markers;
    }

    public List<OverlayOptions> getOptionsList() {
        if(optionsList==null){
            optionsList=new ArrayList<>();
        }

        return optionsList;
    }

    public void setOptionsList(List<OverlayOptions> optionsList) {
        this.optionsList = optionsList;
    }

    public SparseArray<Integer> getMarkerSparseArray() {
        if(this.markerSparseArray==null){
            this.markerSparseArray=new SparseArray<>();
        }
        return markerSparseArray;
    }

    public void setMarkerSparseArray(SparseArray<Integer> markerSparseArray) {
        this.markerSparseArray = markerSparseArray;
    }

    public GardenDetail getCurrentGardenDetail() {
        return currentGardenDetail;
    }

    public void setCurrentGardenDetail(GardenDetail currentGardenDetail) {
        this.currentGardenDetail = currentGardenDetail;
    }
}
