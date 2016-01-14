package com.yunfangdata.fgg.viewmodel;

import com.yunfang.framework.utils.ListUtil;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.model.District;
import com.yunfangdata.fgg.model.GardenDetail;
import com.yunfangdata.fgg.model.SideMenuItemModel;
import com.yunfangdata.fgg.model.SubWay;
import com.yunfangdata.fgg.utils.DensityHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2015/12/9.
 * 主页视图模型
 */
public class HomeViewModel {

    public DensityHelper getDensityHelper() {
        if (densityHelper == null) {
            densityHelper = DensityHelper.getInstance();
        }

        return densityHelper;
    }

    /**
     * 当前行政区数据
     */
    private List<District> districtList;
    /**
     * 当前城市地铁信息
     */
    private List<SubWay> subWays;
    /**
     * 当前小区信息
     */
    private List<GardenDetail> residentialAreas;
    /**
     * 辅助类
     */
    private DensityHelper densityHelper;

    public boolean isShowAear=false;


    /***
     * 侧滑菜单图标ID
     */
    private int[] iconIds = {
            R.drawable.user_icon, R.drawable.mfgz_menu,
            R.drawable.mfgz_menu, R.drawable.mfgz_menu_1,
            R.drawable.seek_head, R.drawable.seek_head,
            R.drawable.setting_menu, R.drawable.wtjl_menu,
            R.drawable.wtpg_menu, R.drawable.wtjl_menu,
            R.drawable.person_center
    };

    /***
     * 侧滑菜单类型
     */
    private SideMenuItemModel.ItemType[] itemTypes = {
            SideMenuItemModel.ItemType.Head,
            SideMenuItemModel.ItemType.Label,
            SideMenuItemModel.ItemType.Item,
            SideMenuItemModel.ItemType.Item,
            SideMenuItemModel.ItemType.Item,
            SideMenuItemModel.ItemType.Item,
            SideMenuItemModel.ItemType.Item,
            SideMenuItemModel.ItemType.Label,
            SideMenuItemModel.ItemType.Item,
            SideMenuItemModel.ItemType.Item,
            SideMenuItemModel.ItemType.Item
    };

    /**
     * 侧滑菜单列表
     */
    private List<SideMenuItemModel> sideItemList;

    public List<SideMenuItemModel> getSideItemList(String[] itemNames) {
        if (sideItemList == null) {
            sideItemList = new ArrayList<>();
        }
        for (int i = 0; i < itemNames.length; i++) {
            String name = itemNames[i];
            SideMenuItemModel item = new SideMenuItemModel(name, iconIds[i], itemTypes[i]);
            sideItemList.add(item);
        }
        return sideItemList;
    }


    //筛选框第一列数据
    /**
     * 区域
     */
    public String[] areaFirstColumn = {"行政区", "地铁"};
    /**
     * 户型
     */
    public String[] houseFirstColumn = {"不限", "一居室", "二居室", "三居室", "四居室", "五居室", "五居室以上"};
    /**
     * 价格
     */
    public String[] priceFirstColumn = {"总价", "首付"};
    /**
     * 学校
     */
    public String[] schoolFirstColumn = {"小学"};

    /**
     * 获取城市行政区
     */
    public final static int GET_DISTRICT = 0;
    /***
     * 获取城市 地铁线路信息
     */
    public final static int GET_SUBWAY_INFO = 1;
    /**
     * 获取片区信息
     */
    public final static int GET_AREA_INFO = 3;


    /**
     * 根据条件查询小区信息
     */
    public static final int GET_GARDEN_DETAIL_BY_CONDITION = 4;
    /***
     * 当城市切换后获取小区信息
     */
    public static final int GET_GARDEN_DETAIL_ON_CITY_CHANG=5;
    /***
     * Fuzzy Search
     */
    public static final int FUZZY_SEARCH_GARDEN_DETAIL=6;

    public static final int GET_SINGLE_GARDEN_DATAIL=7;

    public final static int CHECK_PACKAGE=8;
    /**
     * 检查更新
     */
    public static final int HANDLE_GET_SEARCH_NAME = 9;

    public List<District> getDistrictList() {
        if (districtList == null) {
            districtList = new ArrayList<>();
        }
        return districtList;
    }

    public void setDistrictList(List<District> districtList) {
        this.districtList = districtList;
    }

    public List<SubWay> getSubWays() {
        if (subWays == null) {
            subWays = new ArrayList<>();
        }
        return subWays;
    }

    public void setSubWays(List<SubWay> subWays) {
        this.subWays = subWays;
    }

    public List<GardenDetail> getResidentialAreas() {
        if (residentialAreas == null) {
            residentialAreas = new ArrayList<>();
        }
        return residentialAreas;
    }

    public void setResidentialAreas(List<GardenDetail> residentialAreas) {
        if(ListUtil.hasData(this.residentialAreas)){//清除原来的数据
            this.residentialAreas.clear();
        }
        // 填充新的数据源
        if(this.residentialAreas==null){
            this.residentialAreas=new ArrayList<>();
        }
        this.residentialAreas.addAll(residentialAreas);
    }
}
