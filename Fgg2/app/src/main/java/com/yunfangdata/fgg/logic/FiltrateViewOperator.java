package com.yunfangdata.fgg.logic;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yunfang.framework.utils.ListUtil;
import com.yunfang.framework.utils.ToastUtil;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.enumObj.FiltrateType;
import com.yunfangdata.fgg.enumObj.HouseType;
import com.yunfangdata.fgg.enumObj.SchoolType;
import com.yunfangdata.fgg.model.Area;
import com.yunfangdata.fgg.model.District;
import com.yunfangdata.fgg.model.ResidentialParameters;
import com.yunfangdata.fgg.model.SubStation;
import com.yunfangdata.fgg.model.SubWay;
import com.yunfangdata.fgg.ui.HomeActivity;
import com.yunfangdata.fgg.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kevin on 2015/12/11.
 * 小区信息条件筛选视图
 */
public class FiltrateViewOperator implements AdapterView.OnItemClickListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    /**
     * 当前选中行政区
     */
    public static final int IS_DISTRICT = 1000;
    /**
     * 当前选中地铁
     */
    public static final int IS_SUBWAY = 1001;
    /**
     * 当前选中片区
     */
    public static final int IS_AREA = 1002;
    /**
     * 当前选中地铁站
     */
    public static final int IS_SUB_STATION = 1003;

    /**
     * 当前选中区域类型
     */
    private int currentType = IS_DISTRICT;
    /**当前地三列选显示的类型*/
    private int currentThirdRow = IS_AREA;

    /**父Activity*/
    private HomeActivity homeActivity;
    /**布局加载器*/
    private LayoutInflater inflater;
    /**当前窗体的View*/
    private View view;
    /**
     * 当前选中条件类型
     */
    private FiltrateType currentFiltrateType;
    /**第一列数据*/
    private List<String> firstColumnList;
    /** 当前 选择的参数模型*/
    private ResidentialParameters residentialParameters;

    /**
     * 条件列表
     */
    private ListView columnOneLv, columnTwoLv, columnThreeLv;
    /**
     * 第一列数据适配器
     */
    private FirstColumnAdapter firstColumnAdapter;
    /**
     * 行政区数据适配器
     */
    private SecondColumnAdapter secondColumnAdapter;

    private SecondColumnAdapter thirdColumnAdapter;
    /**
     * 价格输入布局
     */
    private LinearLayout priceEdtLayout;
    /**
     * 价格输入框
     */
    private EditText price_det;
    /**
     * 确认按钮
     */
    private Button price_confirm_but;

    private RadioGroup popupRdg;

    private RadioButton area_rdb;

    private RadioButton house_rdb;

    private RadioButton price_rdb;

    private RadioButton school_rdb;



    public FiltrateViewOperator(HomeActivity homeActivity, View popView) {
        this.homeActivity = homeActivity;
        inflater = LayoutInflater.from(this.homeActivity);
        this.view = popView;
        initView();
        residentialParameters =new ResidentialParameters();
    }

    private void initView() {
        columnOneLv = $(R.id.column_one_lv);
        columnTwoLv = $(R.id.column_two_lv);
        columnThreeLv = $(R.id.column_three_lv);
        priceEdtLayout = $(R.id.price_edt_linear);
        popupRdg = $(R.id.search_bar_rdg);
        area_rdb = $(R.id.area_rdb);
        house_rdb = $(R.id.house_rdb);
        price_rdb = $(R.id.price_rdb);
        school_rdb = $(R.id.school_rdb);

        price_det = $(R.id.price_det);
        price_confirm_but = $(R.id.price_confirm_but);

        firstColumnList = new ArrayList<>();
        firstColumnAdapter = new FirstColumnAdapter(firstColumnList);

        columnOneLv.setAdapter(firstColumnAdapter);

        columnOneLv.setOnItemClickListener(this);
        columnTwoLv.setOnItemClickListener(new SecondColumnClickListener());
        columnThreeLv.setOnItemClickListener(new ThirdColumnClickListener());

        price_confirm_but.setOnClickListener(this);
        popupRdg.setOnCheckedChangeListener(this);

        area_rdb.setOnClickListener(new RadioButClick());
        house_rdb.setOnClickListener(new RadioButClick());
        price_rdb.setOnClickListener(new RadioButClick());
        school_rdb.setOnClickListener(new RadioButClick());
    }

    /***
     * 设置当前筛选类型
     *
     * @param currentFiltrateType 枚举类型
     */
    public void setCurrentFiltrateType(FiltrateType currentFiltrateType) {
        this.currentFiltrateType = currentFiltrateType;
        firstColumnList.clear();
        switch (currentFiltrateType) {
            case AREA:
                firstColumnList.addAll(Arrays.asList(homeActivity.viewModel.areaFirstColumn));
                break;
            case HOUSE:
                firstColumnList.addAll(Arrays.asList(HouseType.getEnumToString()));
                break;
            case PRICE:
                firstColumnList.addAll(Arrays.asList(homeActivity.viewModel.priceFirstColumn));
                break;
            case SCHOOL:
                firstColumnList.addAll(Arrays.asList(SchoolType.getEnumToString()));
                break;
        }
        firstColumnAdapter.currentPosition=-1;//重置当前选中item下标
        firstColumnAdapter.notifyDataSetChanged();
        columnTwoLv.setVisibility(View.GONE);
        columnThreeLv.setVisibility(View.GONE);
        priceEdtLayout.setVisibility(View.GONE);
        popupRdg.check(this.currentFiltrateType.getId());
    }

    /***
     * 第一列点击事件
     * @param adapterView adapterView
     * @param view view
     * @param position position
     * @param l l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //记录当前选中的项索引
        firstColumnAdapter.currentPosition=position;
        if(secondColumnAdapter!=null){
            secondColumnAdapter.currentPostion=-1;
        }
        switch (currentFiltrateType) {
            case AREA: // 从网络请求得到数据
                //判断缓存中是否有数据， 没数据请求取数据
                if (position == 0) {//行政区
                    if (!ListUtil.hasData(homeActivity.viewModel.getDistrictList())) {
                        homeActivity.doWork("加载.....", homeActivity.viewModel.GET_DISTRICT, null);
                    } else {
                        showColumnTow(IS_DISTRICT);
                    }
                } else {//地铁线
                    if (!ListUtil.hasData(homeActivity.viewModel.getSubWays())) {
                        homeActivity.doWork("加载.....", homeActivity.viewModel.GET_SUBWAY_INFO, null);
                    } else {
                        showColumnTow(IS_SUBWAY);
                    }
                }
                columnThreeLv.setVisibility(View.GONE);
                firstColumnAdapter.notifyDataSetChanged();
                break;
            case HOUSE:
                //只有一个参数，户型
                residentialParameters.clearParam();
                residentialParameters.setUnitType(HouseType.getEnumByPostion(position).getName());
                //根据户型请求数据
                homeActivity.doWork("搜索中....",HomeViewModel.GET_GARDEN_DETAIL_BY_CONDITION,residentialParameters);
                break;
            case PRICE://显示价格输入框
                priceEdtLayout.setVisibility(View.VISIBLE);
                price_confirm_but.setTag(position);
                price_det.setHint(position == 0 ? R.string.price_hint : R.string.price_hint2);
                firstColumnAdapter.notifyDataSetChanged();
                break;
            case SCHOOL:
                residentialParameters.clearParam();
                residentialParameters.setSchoolType(SchoolType.getEnumByPostion(position).getName());
                //根据户型请求数据
                homeActivity.doWork("搜索中....", HomeViewModel.GET_GARDEN_DETAIL_BY_CONDITION, residentialParameters);
                break;
        }

    }

    /***
     * 第二列ItemClickListener
     */
    private class SecondColumnClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            secondColumnAdapter.currentPostion=i;
            secondColumnAdapter.notifyDataSetChanged();
            if (i == 0) {// 点击不限条件
                residentialParameters.clearParam();
                List<District> districtList = homeActivity.viewModel.getDistrictList();
                String rim="周边";
                if(ListUtil.hasData(districtList)){
                    rim=districtList.get(districtList.size()-1).getDistrictName();
                }
                residentialParameters.setDistrictName(rim);
                homeActivity.doWork("搜索中....", HomeViewModel.GET_GARDEN_DETAIL_BY_CONDITION, residentialParameters);
                return;
            }
            //显示第三列
            switch (currentType) {
                case IS_DISTRICT:
                    District d = (District) secondColumnAdapter.getItem(i);
                    if(ListUtil.hasData(d.getAreaList())){
                        showColumnThreeArea(d.getAreaList());
                    }else{
                        homeActivity.doWork("加载...", HomeViewModel.GET_AREA_INFO, d);
                    }
                    //设置查询条件 行政区名
                    residentialParameters.clearParam();
                    residentialParameters.setDistrictName(d.getDistrictName());
                    break;
                case IS_SUBWAY:
                    SubWay subWay = (SubWay) secondColumnAdapter.getItem(i);
                    showColumnThree(subWay.getChildren());
                    //设置参数 地铁线名
                    residentialParameters.clearParam();
                    residentialParameters.setSubWayName(subWay.getSubWayNo());
                    break;
            }
        }
    }

    /***
     * 地三列点击事件
     */
    private class ThirdColumnClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(i==0){// 不限
                homeActivity.doWork("搜索中....", HomeViewModel.GET_GARDEN_DETAIL_BY_CONDITION, residentialParameters);
                return;
            }
            switch (currentThirdRow){
                case IS_SUB_STATION://地铁站
                    SubStation station=(SubStation)thirdColumnAdapter.getAreaItem(i, currentThirdRow);
                    residentialParameters.setSubWayName(station.getDiTieZhanName());
                    homeActivity.doWork("搜索中....", HomeViewModel.GET_GARDEN_DETAIL_BY_CONDITION, residentialParameters);
                    break;
                case IS_AREA:// 片区
                    Area area=(Area)thirdColumnAdapter.getAreaItem(i, currentThirdRow);
                    residentialParameters.setPlate(area.getAreaName());
                    homeActivity.doWork("搜索中....", HomeViewModel.GET_GARDEN_DETAIL_BY_CONDITION, residentialParameters);
                    break;
            }
        }
    }

    private void showColumnThree(List<SubStation> subStations) {
        columnThreeLv.setVisibility(View.VISIBLE);
        if (thirdColumnAdapter == null) {
            thirdColumnAdapter = new SecondColumnAdapter();
            columnThreeLv.setAdapter(thirdColumnAdapter);
        }
        thirdColumnAdapter.setSubStation(subStations);

    }

    public void showColumnThreeArea(List<Area> areas) {
        columnThreeLv.setVisibility(View.VISIBLE);
        if (thirdColumnAdapter == null) {
            thirdColumnAdapter = new SecondColumnAdapter();
            columnThreeLv.setAdapter(thirdColumnAdapter);
        }
        thirdColumnAdapter.setAreas(areas);
    }

    public void showColumnTow(int tag) {
        switch (tag) {
            case IS_DISTRICT:
                columnTwoLv.setVisibility(View.VISIBLE);
                if (secondColumnAdapter == null) {
                    secondColumnAdapter = new SecondColumnAdapter();
                    columnTwoLv.setAdapter(secondColumnAdapter);
                }
                secondColumnAdapter.setDistrictList(homeActivity.viewModel.getDistrictList());
                break;
            case IS_SUBWAY:
                columnTwoLv.setVisibility(View.VISIBLE);
                if (secondColumnAdapter == null) {
                    secondColumnAdapter = new SecondColumnAdapter();
                    columnTwoLv.setAdapter(secondColumnAdapter);
                }
                secondColumnAdapter.setSubWays(homeActivity.viewModel.getSubWays());

                break;
        }


    }

    @Override
    public void onClick(View view) {
        residentialParameters.clearParam();
        if((int)view.getTag()==0){ //总价
            //zongjia,shoufu
            residentialParameters.setType("zongjia");
        }else{//首付
            residentialParameters.setType("shoufu");
        }
        try {
            double price = Double.valueOf(price_det.getText().toString());
            if(price>0){
                residentialParameters.setTotalPrice(price);
                //根据户型请求数据
                homeActivity.doWork("搜索中....", HomeViewModel.GET_GARDEN_DETAIL_BY_CONDITION, residentialParameters);
            }else{
                ToastUtil.shortShow(homeActivity,"输入有误");
            }
        }catch(Exception e){
            e.printStackTrace();
            ToastUtil.shortShow(homeActivity,"输入有误");
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        //setCurrentFiltrateType(FiltrateType.getFiltrateTypeById(i));
    }

    /***
     * 第一行数据适配其
     */
    private class FirstColumnAdapter extends BaseAdapter {

        private List<String> itemList;

        private int currentPosition=-1;

        public FirstColumnAdapter(List<String> itemList) {
            this.itemList = itemList;
        }

        @Override
        public int getCount() {
            return this.itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return firstColumnList.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView name_tv;
            if (view == null) {
                view = inflater.inflate(R.layout.list_item_filtrate_bar, null, false);
                name_tv = (TextView) view.findViewById(R.id.name_tv);
                view.setTag(name_tv);
                int heightDp = (int) homeActivity.getResources().getDimension(R.dimen.itemHeight);
                /*view.setLayoutParams(new ViewGroup.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT, heightDp));*/
                view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,heightDp));
            } else {
                name_tv = (TextView) view.getTag();
            }
            name_tv.setText(this.itemList.get(i));

            //区域和价格类型条件，有选中状态设置
            if(currentFiltrateType==FiltrateType.AREA||currentFiltrateType==FiltrateType.PRICE){
                if(currentPosition==i){
                    name_tv.setTextColor(getResourcesColor(R.color.title_blue));
                }else{
                    name_tv.setTextColor(getResourcesColor(R.color.black));
                }
            }else{
                name_tv.setTextColor(getResourcesColor(R.color.black));
            }


            return view;
        }
    }

    private class SecondColumnAdapter extends BaseAdapter {

        /**
         * 行政区数据
         */
        private List<District> districtList;

        private int currentPostion=-1;
        /**
         * 地铁数据
         */
        private List<SubWay> subWays;

        private List<SubStation> subStations;

        private List<Area> areas;

        private List<String> strings;


        SecondColumnAdapter() {
            strings = new ArrayList<String>();
        }

        public void setDistrictList(List<District> districtList) {
            this.districtList = districtList;
            strings.clear();
            strings.add("不限");
            for (District d : districtList) {
                strings.add(d.getDistrictName());
            }
            currentType = IS_DISTRICT;
            this.notifyDataSetChanged();
        }

        public void setSubWays(List<SubWay> subWays) {
            this.subWays = subWays;
            strings.clear();
            strings.add("不限");
            for (SubWay d : subWays) {
                strings.add(d.getSubWayNo());
            }
            currentType = IS_SUBWAY;
            this.notifyDataSetChanged();
        }

        public void setSubStation(List<SubStation> subStations) {
            this.subStations = subStations;
            strings.clear();
            strings.add("不限");
            for (SubStation subStation : this.subStations) {
                strings.add(subStation.getDiTieZhanName());
            }
            currentThirdRow = IS_SUB_STATION;
            this.notifyDataSetChanged();
        }

        public void setAreas(List<Area> areas) {
            this.areas = areas;
            strings.clear();
            strings.add("不限");
            for (Area a : this.areas) {
                strings.add(a.getAreaName());
            }
            currentThirdRow = IS_AREA;
            this.notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return this.strings.size();
        }

        @Override
        public Object getItem(int i) {
            if (i == 0) {
                return null;
            }
            try {
                switch (currentType) {
                    case IS_SUBWAY:
                        return this.subWays.get(i - 1);
                    case IS_DISTRICT:
                        return this.districtList.get(i - 1);
                    default:
                        return this.subWays.get(i - 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }

        }

        public Object getAreaItem(int position,int type){
            if (position == 0) {
                return null;
            }
            try {
                switch (type) {
                    case IS_SUB_STATION:
                        return this.subStations.get(position - 1);
                    case IS_AREA:
                        return this.areas.get(position - 1);
                    default:
                        return this.subStations.get(position - 1);
                }
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView name_tv;
            if (view == null) {
                view = inflater.inflate(R.layout.list_item_filtrate_bar, null, false);
                name_tv = (TextView) view.findViewById(R.id.name_tv);
                view.setTag(name_tv);
                int heightDp = (int) homeActivity.getResources().getDimension(R.dimen.itemHeight);
                /*view.setLayoutParams(new ViewGroup.LayoutParams
                        (AbsListView.LayoutParamst.MATCH_PARENT, heightDp));*/
                view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,heightDp));
            } else {
                name_tv = (TextView) view.getTag();
            }
            name_tv.setText(this.strings.get(i));
            if(currentPostion==i){
                name_tv.setTextColor(getResourcesColor(R.color.title_blue));
            }else{
                name_tv.setTextColor(getResourcesColor(R.color.black));
            }

            return view;
        }
    }


    private class RadioButClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            setCurrentFiltrateType(FiltrateType.getFiltrateTypeById(view.getId()));
        }
    }

    private <E extends View> E $(int id) {
        return (E) this.view.findViewById(id);
    }

    private String getResourcesString(int id) {
        String resourcesStr = homeActivity.getResources().getString(id);
        if (resourcesStr != null) {
            return resourcesStr;
        }
        return "";
    }

    private int getResourcesColor(int id) {
        int color = homeActivity.getResources().getColor(id);

        return color;
    }
}
