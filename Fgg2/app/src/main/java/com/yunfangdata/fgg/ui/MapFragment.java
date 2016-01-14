package com.yunfangdata.fgg.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.yunfang.framework.base.BaseBroadcastReceiver;
import com.yunfang.framework.base.BaseWorkerFragment;
import com.yunfang.framework.utils.BitmapHelperUtil;
import com.yunfang.framework.utils.ListUtil;
import com.yunfang.framework.utils.StringUtil;
import com.yunfang.framework.utils.ToastUtil;
import com.yunfang.framework.utils.YFLog;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.enumObj.City;
import com.yunfangdata.fgg.helper.IntentHelper;
import com.yunfangdata.fgg.logic.MapFragmentOperator;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.District;
import com.yunfangdata.fgg.model.GardenDetail;
import com.yunfangdata.fgg.model.GardenImage;
import com.yunfangdata.fgg.utils.DensityHelper;
import com.yunfangdata.fgg.viewmodel.HomeViewModel;
import com.yunfangdata.fgg.viewmodel.MapViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kevin
 */
public class MapFragment extends BaseWorkerFragment implements BaiduMap.OnMapStatusChangeListener,
        BaiduMap.OnMapLoadedCallback, BaiduMap.OnMarkerClickListener,
        View.OnClickListener, BaiduMap.OnMapClickListener {


    private MapViewModel viewModel = new MapViewModel();

    /**
     * 地图控件
     */
    private MapView mapView;
    /**
     * BaiduMap
     */
    private BaiduMap baidu_map;
    /**
     * 小区信息层
     */
    private View house_info_relative;

    /*小区图片**/
    private ImageView title_img;
    /* 小区名**/
    private TextView garden_name_tv;
    /*地址**/
    private TextView address_tv;
    /*小区均价**/
    private TextView unit_price_number_tv;
    /*同比**/
    private TextView year_on_year_price_tv;
    /*同比图标**/
    private ImageView year_on_year_img;
    /*环比**/
    private TextView qoq_price_tv;
    /*环比图标**/
    private ImageView qoq_price_img;
    /*最大可购面积**/
    private TextView max_area_price_tv;
    /*租金均价**/
    private TextView rent_price_tv;
    /*可购最大面积Linea**/
    private View max_area_linea;

    private BaseBroadcastReceiver myBroadcastReceiver;

    private MyLocationListener myLocationListener;


    private boolean isFirstLocation = true;
    /**
     * imageLoader 参数配置
     */
    private DisplayImageOptions option;
    /**
     * 图片加载工具类
     */
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private boolean startLocation;
    private LocationClient locationClient;


    public MapFragment() {

    }

    @Override
    protected void handlerBackgroundHandler(Message msg) {
        Message uiMsg = new Message();
        uiMsg.what = msg.what;
        switch (msg.what) {
            case MapViewModel.GET_GARDEN_DETAIL_BY_XY:
                //地理范围数据结构，由西南以及东北坐标点确认
                LatLngBounds bounds = (LatLngBounds) msg.obj;
                uiMsg.obj = MapFragmentOperator.getGardenDetailByXY(baidu_map.getMapStatus().bound);
                break;
            default:
        }
        this.sendUiMessage(uiMsg);
    }

    @Override
    protected void handUiMessage(Message msg) {
        switch (msg.what) {
            case MapViewModel.GET_GARDEN_DETAIL_BY_XY:
                DataResult<List<GardenDetail>> gardenResult = (DataResult<List<GardenDetail>>) msg.obj;
                HomeActivity homeActivity = (HomeActivity) this.getActivity();
                if (gardenResult.getSuccess()) {
                    homeActivity.viewModel.setResidentialAreas(gardenResult.getResultData());
                    showMark(gardenResult.getResultData());
                } else {
                    ToastUtil.shortShow(homeActivity, gardenResult.getMessage());
                }
                break;
            default:
        }

    }

    /**
     * 显示标注
     */
    private void showMark(List<GardenDetail> resultData) {
        baidu_map.clear();
        showUserLocationMark();
        for (int i = 0; i < resultData.size(); i++) {//小区标注点
            GardenDetail gd = resultData.get(i);
            OverlayOptions options = createOverlay(gd.getLatLng());
            Marker marker = (Marker) baidu_map.addOverlay(options);
            //标注对象的 hashCode 作为KEY ，点击是使用key 设置对应标注图标
            viewModel.getMarkerSparseArray().put(marker.hashCode(), i);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        registerBroadCast();
        initMap();
        initItemView();
    }

    /***
     * 初始化小区信息覆盖View
     */
    private void initItemView() {
    /*小区图片**/
        title_img = $(R.id.title_img);
    /* 小区名**/
        garden_name_tv = $(R.id.garden_name_tv);
    /*地址**/
        address_tv = $(R.id.address_tv);
    /*小区均价**/
        unit_price_number_tv = $(R.id.unit_price_number_tv);
    /*同比**/
        year_on_year_price_tv = $(R.id.year_on_year_price_tv);
    /*同比图标**/
        year_on_year_img = $(R.id.year_on_year_img);
    /*环比**/
        qoq_price_tv = $(R.id.qoq_price_tv);
    /*环比图标**/
        qoq_price_img = $(R.id.qoq_price_img);
    /*最大可购面积**/
        max_area_price_tv = $(R.id.max_area_price_tv);
    /*租金均价**/
        rent_price_tv = $(R.id.rent_price_tv);
    /*可购最大面积Linea**/
        max_area_linea = $(R.id.max_area_linea);
    }

    /***
     * 地图初始化
     */
    private void initMap() {
        /*隐藏比例标尺 缩放比例控件*/
        mapView.showZoomControls(false);
        //mapView.showScaleControl(false);
        baidu_map = mapView.getMap();


        if (myLocationListener == null) {
            myLocationListener = new MyLocationListener();
        }
        locationClient = new LocationClient(viewModel.getHomeActivity());
        locationClient.registerLocationListener(myLocationListener);
        initLocation();

        // 开启定位图层
        baidu_map.setMyLocationEnabled(true);

        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
//                .target(center)
                .zoom(viewModel.ZOOM_LEVEL)
                .build();

        //设置默认的地图缩放级别
        baidu_map.setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
        // 监听地图状态改变
        baidu_map.setOnMapStatusChangeListener(this);
        baidu_map.setOnMapLoadedCallback(this);
        baidu_map.setOnMarkerClickListener(this);
        baidu_map.setOnMapClickListener(this);
    }

    private void initLocation() {
        LocationClientOption locationOption = new LocationClientOption();
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationOption.setOpenGps(true);
        locationOption.setAddrType("all");// 返回的定位结果包含地址信息
        locationOption.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02,还有bd09
        locationOption.setIsNeedLocationPoiList(false); // 是否需要POI的电话和地址等详细信息
        //locationOption.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms，小于1秒则一次定位;大于等于1秒则定时定位
        locationOption.setPriority(LocationClientOption.NetWorkFirst);// 不设置，默认是gps优先
        locationOption.disableCache(true);// 禁止启用缓存定位
        locationOption.setNeedDeviceDirect(true);// 是否需要方向
        locationOption.setIgnoreKillProcess(true);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        locationOption.setIsNeedAddress(true);//返回的定位结果包含地址信息
        locationClient.setLocOption(locationOption);
        locationClient.start();//开始定位

        startLocation = true;

    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (isFirstLocation) {
                firstLocation(bdLocation);
                isFirstLocation = false;
            } else {
                location(bdLocation);
            }
            locationClient.stop();
            if (viewModel.firstLocation) {
                initLocation();
                viewModel.firstLocation = false;
            }
        }
    }

    private void firstLocation(BDLocation bdLocation) {
        //定位 获取当前城市
        String cityCode = StringUtil.IsNullOrEmpty(bdLocation.getCityCode()) ? "" : bdLocation.getCityCode();
        City c = City.getCityByCityCode(cityCode);
        if (c != null) {
            FggApplication.getInstance().city = c;
        } else {
            //定位不在制定城市，默认设置为北京
            FggApplication.getInstance().city = City.北京;
        }
        viewModel.getHomeActivity().setCityName();
        //移动到城市中心点
        toCityCenter(FggApplication.getInstance().city);
        //定位完加载行政区数据
        viewModel.getHomeActivity().doWork("加载.....", HomeViewModel.GET_DISTRICT, null);
    }

    private void location(BDLocation bdLocation) {
        String cityCode = StringUtil.IsNullOrEmpty(bdLocation.getCityCode()) ? "" : bdLocation.getCityCode();
        LatLng latLng = null;
        City c = City.getCityByCityCode(cityCode);
        //缓存当前用户定位坐标
        viewModel.userLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        if (c != null) { //城市是否在城市列表中
            FggApplication.getInstance().city = c;
            latLng = viewModel.userLatLng;
        } else {
            //定位不在制定城市，默认设置为北京
            FggApplication.getInstance().city = City.北京;
            latLng = City.北京.getCityCenter();
        }
        // 改变界面当前城市名称
        viewModel.getHomeActivity().setCityName();

        YFLog.d(latLng.toString());
        //请求当前位置小区信息
        LatLngBounds bound = setMapStatus(latLng).bound;
        doWork("", MapViewModel.GET_GARDEN_DETAIL_BY_XY, bound);

    }

    /***
     * 视图初始化
     */
    private void initView() {
        mapView = $(R.id.baidu_map);
        /*
      切换列表
     */
        TextView list_tv = $(R.id.list_tv);
        /*
      定位按钮
     */
        TextView location_tv = $(R.id.location_tv);
        house_info_relative = $(R.id.house_info_relative);

        list_tv.setOnClickListener(this);
        location_tv.setOnClickListener(this);
        house_info_relative.setOnClickListener(this);

    }

    /**
     * 监听地图初始化验证结果
     */
    private void registerBroadCast() {
        // 注册 SDK 广播监听者
        ArrayList<String> actions = new ArrayList<>();
        actions.add(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        actions.add(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        myBroadcastReceiver = new BaseBroadcastReceiver(viewModel.getHomeActivity(), actions);
        myBroadcastReceiver.setAfterReceiveBroadcast(new BaseBroadcastReceiver.afterReceiveBroadcast() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = intent.getAction();
                if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                    ToastUtil.longShow(MapFragment.this.getContext(), "百度地图 key 验证出错!");
                } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                    ToastUtil.longShow(MapFragment.this.getContext(), "网络出错");
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    /***
     * 创建地图标注
     *
     * @param point
     * @return
     */
    private OverlayOptions createOverlay(LatLng point) {
        if (viewModel.markBitmap == null) {
            Resources res = this.getResources();
            int wDp = 30;
            int hDp = 30;
            int wDp1 = 35;
            int hDp1 = 35;
            int wPx = DensityHelper.getInstance().dip2px(this.getContext(), wDp);
            int hPx = DensityHelper.getInstance().dip2px(this.getContext(), hDp);
            int wPx1 = DensityHelper.getInstance().dip2px(this.getContext(), wDp1);
            int hPx1 = DensityHelper.getInstance().dip2px(this.getContext(), hDp1);
            Bitmap bitmap = BitmapHelperUtil.decodeSampledBitmapFromResource(res, R.drawable.pin_purple, wPx, hPx);
            viewModel.markBitmap = BitmapDescriptorFactory.fromBitmap(bitmap);
            Bitmap bitmap2 = BitmapHelperUtil.decodeSampledBitmapFromResource(res, R.drawable.pin_green, wPx1, hPx1);
            viewModel.checkableMarkBitmap = BitmapDescriptorFactory.fromBitmap(bitmap2);
        }
        MarkerOptions overlay = new MarkerOptions().icon(viewModel.markBitmap).position(point).zIndex(9).draggable(false);
        return overlay;
    }


    /***
     * 创建定位点标注图标
     *
     * @param point
     * @return
     */
    private OverlayOptions createLocationMark(LatLng point) {
        if (viewModel.locationMarkBitmap == null) {
            Resources res = this.getResources();
            Bitmap bitmap = BitmapHelperUtil.decodeSampledBitmapFromResource(res, R.mipmap.icon_center, 100, 100);
            viewModel.locationMarkBitmap = BitmapDescriptorFactory.fromBitmap(bitmap);
        }
        MarkerOptions overlay = new MarkerOptions().icon(viewModel.locationMarkBitmap).position(point).zIndex(9).draggable(false);
        return overlay;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            viewModel.setHomeActivity((HomeActivity) activity);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /***
     * 地图状态开始改变
     *
     * @param mapStatus
     */
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    /***
     * 地图状态改变中
     *
     * @param mapStatus
     */
    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    /***
     * 地图状态改变结束
     *
     * @param mapStatus
     */
    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        float currentZoom = baidu_map.getMapStatus().zoom;
        if (currentZoom < MapViewModel.CRITICALTHE_ZOOM_LEVEL) {
            //小于临界缩放级别 显示行政区
            List<District> districtList = viewModel.getHomeActivity().viewModel.getDistrictList();
            if (ListUtil.hasData(districtList)) {
                showAllDistrictOnMap();
            } else {
                viewModel.getHomeActivity().doWork("加载.....", HomeViewModel.GET_DISTRICT, null);
            }
        } else {
            doWork("", MapViewModel.GET_GARDEN_DETAIL_BY_XY, baidu_map.getMapStatus().bound);
        }
    }

    /***
     * 当地图加载完
     */
    @Override
    public void onMapLoaded() {
        float currentZoom = baidu_map.getMapStatus().zoom;
        if (currentZoom < MapViewModel.CRITICALTHE_ZOOM_LEVEL) {
            //小于临界缩放级别 显示行政区
            List<District> districtList = viewModel.getHomeActivity().viewModel.getDistrictList();
            if (ListUtil.hasData(districtList)) {
                showAllDistrictOnMap();
            } else {
                viewModel.getHomeActivity().doWork("加载.....", HomeViewModel.GET_DISTRICT, null);
            }
        } else {
            doWork("", MapViewModel.GET_GARDEN_DETAIL_BY_XY, baidu_map.getMapStatus().bound);
        }

    }

    /***
     * 显示行政区均价
     */
    private void showAllDistrictOnMap() {
        List<District> districtList = viewModel.getHomeActivity().viewModel.getDistrictList();
        if (ListUtil.hasData(districtList)) {
            baidu_map.clear();
            showUserLocationMark();
            for (District d : districtList) {
                createTextOnMap(d);
                //createTextViewOnMap(d);
            }
        }
    }

    private void showUserLocationMark() {
        if (viewModel.userLatLng != null) {
            OverlayOptions locationOO = createLocationMark(viewModel.userLatLng);
            Marker markerS = (Marker) baidu_map.addOverlay(locationOO);
        }
    }

    private void createTextOnMap(District district) {
        //定义文字所显示的坐标点
        //LatLng llText = new LatLng(39.86923, 116.397428);
        LatLng ss = district.getLatLng();
        //构建文字Option对象，用于在地图上添加文字
        String districtName = district.getDistrictName();
        double districtPrice = district.getPrice();
        OverlayOptions textOption = new TextOptions()
                .bgColor(this.getResources().getColor(R.color.orangess))
                .fontSize(60)
                .fontColor(this.getResources().getColor(R.color.white))
                .text(districtName + "  " + districtPrice + "元/㎡")
                .rotate(0)
                .position(ss);
        //在地图上添加该文字对象并显示
        baidu_map.addOverlay(textOption);
    }

    private void createTextViewOnMap(District district) {
        //创建InfoWindow展示的view
        if (viewModel.areaTv == null) {
            viewModel.areaTv = new TextView(this.getContext());
            viewModel.areaTv.setTextColor(this.getResources().getColor(R.color.white));
            viewModel.areaTv.setBackgroundResource(R.drawable.juxing_yuangjiao_tv);
            viewModel.areaTv.setPadding(25, 25, 25, 25);
        }
        String districtName = district.getDistrictName();
        double districtPrice = district.getPrice();
        viewModel.areaTv.setText(districtName + "  " + districtPrice + "元/㎡");
        LatLng ss = district.getLatLng();
        YFLog.d(ss.toString());
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(viewModel.areaTv, ss, -47);
        //显示InfoWindow
        baidu_map.showInfoWindow(mInfoWindow);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        viewModel.lastMarker = viewModel.currentMarker;
        viewModel.currentMarker = marker;
        int position = viewModel.getMarkerSparseArray().get(marker.hashCode(), -1);
        if (position == -1) {
            return false;
        }
        GardenDetail gd = viewModel.getHomeActivity().viewModel.getResidentialAreas().get(position);
        viewModel.setCurrentGardenDetail(gd);
        showGardenDetail(gd);
        if (viewModel.lastMarker != null) {
            viewModel.lastMarker.setIcon(viewModel.markBitmap);
        }
        viewModel.currentMarker.setIcon(viewModel.checkableMarkBitmap);
        int visibility = house_info_relative.getVisibility();
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            house_info_relative.setVisibility(View.VISIBLE);
            showItemViewAnimation();
        }
        return false;
    }

    public void showGardenDetail(GardenDetail gd) {
        house_info_relative.setVisibility(View.VISIBLE);
        if (option == null) {
            // 配置ImageLoader
            option = new DisplayImageOptions.Builder()//
                    .showImageOnLoading(R.drawable.untitled_big)//
                    .showImageForEmptyUri(R.drawable.untitled_big)//
                    .showImageOnFail(R.drawable.untitled_big)//
                    .cacheInMemory(true).cacheOnDisk(true)//
                    .considerExifParams(true)//
                    .imageScaleType(ImageScaleType.EXACTLY)//
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }
        String titlePath = "";
        for (GardenImage gi : gd.getImagesList()) {
            if ("原图".equals(gi.getType()) && "小区内环境".equals(gi.getTitle())) {
                titlePath = gi.getUrl();
            }
        }
        imageLoader.displayImage(titlePath, title_img, option);// 加载图片
    /* 小区名**/
        garden_name_tv.setText(gd.getResidentialAreaName());
    /*地址**/
        address_tv.setText(gd.getAddress());
    /*小区均价**/
        unit_price_number_tv.setText(gd.getUnitPrice() + "");
    /*同比**/
    /*同比图标**/
        DecimalFormat df = new DecimalFormat("######0.00");
        double yearPrice = gd.getRatioByLastYearForPrice();
        year_on_year_price_tv.setText(df.format(yearPrice * 100) + "%");
        if (yearPrice <= 0) {
            year_on_year_price_tv.setTextColor(this.getResources().getColor(R.color.title_blue));
            year_on_year_img.setImageResource(R.drawable.sj_2);
        } else {
            year_on_year_price_tv.setTextColor(this.getResources().getColor(R.color.red));
            year_on_year_img.setImageResource(R.drawable.sj_1);
        }
    /*环比**/
    /*环比图标**/
        double monthPrice = gd.getRatioByLastMonthForPrice();
        if (monthPrice <= 0) {
            qoq_price_tv.setTextColor(this.getResources().getColor(R.color.title_blue));
            qoq_price_img.setImageResource(R.drawable.sj_2);
        } else {
            qoq_price_tv.setTextColor(this.getResources().getColor(R.color.red));
            qoq_price_img.setImageResource(R.drawable.sj_1);
        }
        qoq_price_tv.setText(df.format(monthPrice * 100) + "%");
    /*租金均价**/
        rent_price_tv.setText(gd.getRentPrice() + "元/月");
    /*可购最大面积Linea**/
        if (viewModel.getHomeActivity().viewModel.isShowAear) {
            max_area_linea.setVisibility(View.VISIBLE);
            /*最大可购面积**/
            max_area_price_tv.setText(gd.getBuildingAreaInt() + "m²");
        } else {
            max_area_linea.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.list_tv:
                viewModel.getHomeActivity().changFragmentByTag(HomeActivity.FrameTag.List);
                break;
            case R.id.location_tv:
                viewModel.firstLocation = true;
                initLocation();
                break;
            case R.id.house_info_relative://跳转到小区详情界面
                GardenDetail currentGardenDetail = viewModel.getCurrentGardenDetail();
                if (currentGardenDetail != null) {
                    IntentHelper.intentToHouseDetial(viewModel.getHomeActivity(), currentGardenDetail.getResidentialAreaID());
                }
                break;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //点击地图隐藏覆盖物
        if (house_info_relative.getVisibility() == View.VISIBLE) {
            hideItemViewAnimation();
        }
        if (viewModel.currentMarker != null) {
            viewModel.currentMarker.setIcon(viewModel.markBitmap);
        }

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    /**
     * 小区数据修改完毕，刷新地图
     */
    public void gardenDataSet() {
        List<GardenDetail> residentialAreas = viewModel.getHomeActivity().viewModel.getResidentialAreas();
        showMark(residentialAreas);
        if (ListUtil.hasData(residentialAreas)) {
            setMapStatus(residentialAreas.get(0).getLatLng());
        }
        hideItemViewAnimation();
    }

    /**
     * 显示行政区均价
     */
    public void showDistrictTv() {
        showAllDistrictOnMap();
    }

    /***
     * 重新设置地图中心点
     *
     * @param center 中心点坐标
     * @return
     */
    private MapStatus setMapStatus(LatLng center) {
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(center)
                .zoom(MapViewModel.THE_ZOOM_LEVEL)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baidu_map.setMapStatus(mMapStatusUpdate);
        return baidu_map.getMapStatus();
    }

    public void toCityCenter(City city) {
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(city.getCityCenter())
                .zoom(12)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baidu_map.setMapStatus(mMapStatusUpdate);
    }

    /***
     * findViewById
     *
     * @param id  id
     * @param <E> view
     * @return
     */
    public final <E extends View> E $(int id) {
        return (E) this.getView().findViewById(id);
    }


    /**
     * 显示小区信息
     */
    public void showItemViewAnimation() {
        TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 1f, 1, 0f);
        ta.setDuration(1000);
        house_info_relative.startAnimation(ta);
    }

    /**
     * 隐藏
     */
    public void hideItemViewAnimation() {
        TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 0f, 1, 1f);
        ta.setDuration(200);
        house_info_relative.startAnimation(ta);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                house_info_relative.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        receiver();
    }

    public void receiver() {
        myBroadcastReceiver.unregisterReceiver();
        myBroadcastReceiver = null;
    }
}
