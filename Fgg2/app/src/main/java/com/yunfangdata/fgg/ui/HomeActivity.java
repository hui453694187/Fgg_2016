package com.yunfangdata.fgg.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yunfang.framework.utils.ToastUtil;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.NewBaseFragmentActivity;
import com.yunfangdata.fgg.enumObj.FiltrateType;
import com.yunfangdata.fgg.fragment.SettingBaseFragment;
import com.yunfangdata.fgg.helper.IntentHelper;
import com.yunfangdata.fgg.logic.CityListOperator;
import com.yunfangdata.fgg.logic.FiltrateViewOperator;
import com.yunfangdata.fgg.logic.GardenSearchOperator;
import com.yunfangdata.fgg.logic.HomeOperator;
import com.yunfangdata.fgg.logic.MapFragmentOperator;
import com.yunfangdata.fgg.logic.SettingOperator;
import com.yunfangdata.fgg.logic.SideMenuOperator;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.DeviceInfo;
import com.yunfangdata.fgg.model.District;
import com.yunfangdata.fgg.model.GardenDetail;
import com.yunfangdata.fgg.model.ResidentialParameters;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.SearchResult;
import com.yunfangdata.fgg.model.SubWay;
import com.yunfangdata.fgg.model.UpdataBeen;
import com.yunfangdata.fgg.utils.Util_Rotate3DAnimation;
import com.yunfangdata.fgg.utils.VersionUtil;
import com.yunfangdata.fgg.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/***
 * @author kevin
 *         　主界面
 */
public class HomeActivity extends NewBaseFragmentActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    /**
     * 主界面ViewModel
     */
    public HomeViewModel viewModel;

    {
        viewModel = new HomeViewModel();
    }

    /**
     * 当前fragment
     */
    private Fragment currentFragment;
    /**
     * 菜单弹出icon
     */
    private ImageView user_icon_img;
    /**
     * 小区筛选条件栏
     */
    private RadioGroup search_bar_rdg;

    private RadioButton area_rdb;

    private RadioButton house_rdb;

    private RadioButton price_rdb;

    private RadioButton school_rdb;
    /*搜索按钮**/
    private ImageView search_img;
    /*返回按钮**/
    private TextView back_tv;

    private TextView city_name_tv;


    private FrameLayout framelayout;

    /**
     * 头部标题栏视图
     */
    private View head_bar_view;
    /**
     * 侧滑菜单弹出框
     */
    public PopupWindow sideMenuPopWin;

    public PopupWindow filtratePopup;

    private PopupWindow cityListPopup;

    private PopupWindow gardenSearchPopup;

    private GardenSearchOperator searchOperator;
    /**
     * 侧滑菜单逻辑处理类
     */
    private SideMenuOperator sideMenuOperator;
    /**
     * 小区提交筛选区域逻辑类
     */
    private FiltrateViewOperator filtrateViewOperator;

    private String oldPackageName = "org.zywx.wbpalmstar.widgetone.uexbaidumap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        changFragmentByTag(FrameTag.Map);
        doWork("", HomeViewModel.CHECK_PACKAGE, null);

        doWork("",HomeViewModel.HANDLE_GET_SEARCH_NAME,null);
    }


    /**
     * 获取系统中所有应用信息，
     * 并将应用软件信息保存到list列表中。
     **/
    public boolean getAllApps() {
        PackageManager packageManager = getPackageManager();
        //获取到所有安装了的应用程序的信息，包括那些卸载了的，但没有清除数据的应用程序
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo info : packageInfos) {
            //拿到包名
            String packageName = info.packageName;
            if (packageName != null && packageName.equals(oldPackageName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 展示对话框
     */
    private void showDialog() {

        AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this).create();
        dialog.setTitle("尊敬的用户");
        dialog.setMessage("房估估已经升级到了2.0版本,是否卸载1.0?");
        // 确定
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "卸载", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                uninstall();
                dialog.dismiss();
            }
        });

        // 显示对话框
        dialog.show();

    }

    /**
     *  卸载程序
     */
    public void uninstall() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + oldPackageName));
        startActivity(intent);
    }


    /***
     * 控件初始化
     */
    private void initView() {
        user_icon_img = $(R.id.user_icon_img);
        search_bar_rdg = $(R.id.search_bar_rdg);
        head_bar_view = $(R.id.head_bar_view);
        area_rdb = $(R.id.area_rdb);
        house_rdb = $(R.id.house_rdb);
        price_rdb = $(R.id.price_rdb);
        school_rdb = $(R.id.school_rdb);
        search_img = $(R.id.search_img);
        back_tv = $(R.id.back_tv);
        framelayout = $(R.id.home_frame);
        city_name_tv = $(R.id.city_name_tv);
        //单选按钮选中监听
        user_icon_img.setOnClickListener(this);

        area_rdb.setOnClickListener(this);
        house_rdb.setOnClickListener(this);
        price_rdb.setOnClickListener(this);
        school_rdb.setOnClickListener(this);
        search_img.setOnClickListener(this);
        back_tv.setOnClickListener(this);
        city_name_tv.setOnClickListener(this);

        setCityName();
    }

    /***
     * 切换fragment
     *
     * @param fragmentTag fragmentTag 标签
     */
    public void changFragmentByTag(FrameTag fragmentTag) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //隐藏当前fragment
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        currentFragment = fm.findFragmentByTag(fragmentTag.name);
        /*boolean isMap=false;
        int rount=90;*/
        switch (fragmentTag) {
            case Map:
                if (currentFragment == null) {
                    currentFragment = new MapFragment();
                }
                search_img.setVisibility(View.VISIBLE);
                back_tv.setVisibility(View.GONE);
                /*isMap=true;
                rount=90;*/
                break;
            case List:
                if (currentFragment == null) {
                    currentFragment = new ApartmentListFragment();
                }
                search_img.setVisibility(View.GONE);
                back_tv.setVisibility(View.VISIBLE);
                /*isMap=false;
                rount=-90;*/
                break;
            default:
        }
        //applyRotation(isMap,currentFragment,0,rount,fragmentTag);
//        ft.setCustomAnimations(R.anim.popup_window_enter,R.anim.popup_window_exit);
        if (currentFragment != null && currentFragment.isAdded()) {
            ft.show(currentFragment);
        } else {
            ft.add(R.id.home_frame, currentFragment, fragmentTag.name);
        }
        ft.commit();
    }

    @Override
    protected void handleBackgroundMessage(Message message) {
        Message uiMessage = new Message();
        uiMessage.what = message.what;
        switch (uiMessage.what) {
            case HomeViewModel.GET_DISTRICT:
                uiMessage.obj = HomeOperator.getDistrict(FggApplication.getInstance().city.getCityPinYin());
                break;
            case HomeViewModel.GET_SUBWAY_INFO:
                uiMessage.obj = HomeOperator.getSubWayInfo(FggApplication.getInstance().city.getCityPinYin());
                break;
            case HomeViewModel.GET_AREA_INFO:
                District district = (District) message.obj;
                uiMessage.obj = HomeOperator.getAreaInfo(FggApplication.getInstance().city.getCityPinYin(), district);
                break;
            case HomeViewModel.GET_GARDEN_DETAIL_BY_CONDITION:
                ResidentialParameters parameters = (ResidentialParameters) message.obj;
                viewModel.isShowAear= !TextUtils.isEmpty(parameters.getType());
                uiMessage.obj = MapFragmentOperator.getGardenDetailByCondition(parameters);
            case HomeViewModel.GET_GARDEN_DETAIL_ON_CITY_CHANG:
                ResidentialParameters param = (ResidentialParameters) message.obj;
                uiMessage.obj = MapFragmentOperator.getGardenDetailByCondition(param);
                break;
            case HomeViewModel.FUZZY_SEARCH_GARDEN_DETAIL:
                String searchStr=(String)message.obj;
                uiMessage.obj =HomeOperator.fuzzySearch(searchStr);
                break;
            case HomeViewModel.GET_SINGLE_GARDEN_DATAIL:
                int obj = (int) message.obj;
                uiMessage.obj =HomeOperator.getSingleGardenById(obj);
                break;
            case HomeViewModel.CHECK_PACKAGE:
                uiMessage.obj =getAllApps();
                break;
            case HomeViewModel.HANDLE_GET_SEARCH_NAME:// 版本更新
                uiMessage.obj = SettingOperator.getUpdate();
                break;
            default:
        }

        this.sendUiMessage(uiMessage);
    }

    @Override
    protected void handleUiMessage(Message msg) {
        switch (msg.what) {
            case HomeViewModel.GET_DISTRICT:
                DataResult<List<District>> result = (DataResult) msg.obj;
                if (result.getSuccess()) {
                    viewModel.setDistrictList(result.getResultData());
                } else {
                    ToastUtil.shortShow(this, result.getMessage());
                }
                if (filtratePopup != null && filtratePopup.isShowing()) {
                    filtrateViewOperator.showColumnTow(FiltrateViewOperator.IS_DISTRICT);
                } else {
                    if (currentFragment instanceof MapFragment) {// 当前在地图界面
                        ((MapFragment) currentFragment).showDistrictTv();
                    }
                }
                this.loadingWorker.closeLoading();
                break;
            case HomeViewModel.GET_SUBWAY_INFO:// 获取地铁信息
                DataResult<List<SubWay>> subWayResult = (DataResult) msg.obj;
                if (subWayResult.getSuccess()) {
                    viewModel.setSubWays(subWayResult.getResultData());
                }
                if(filtrateViewOperator!=null){
                    filtrateViewOperator.showColumnTow(FiltrateViewOperator.IS_SUBWAY);
                }
                this.loadingWorker.closeLoading();
                break;
            case HomeViewModel.GET_AREA_INFO:
                DataResult<District> areaResult = (DataResult) msg.obj;
                if (areaResult.getSuccess()) {
                    filtrateViewOperator.showColumnThreeArea(areaResult.getResultData().getAreaList());
                } else {
                    ToastUtil.shortShow(this, areaResult.getMessage());
                }
                this.loadingWorker.closeLoading();
                break;
            case HomeViewModel.GET_GARDEN_DETAIL_BY_CONDITION:
                DataResult<List<GardenDetail>> gardenDataResult = (DataResult) msg.obj;
                if (gardenDataResult.getSuccess()) {
                    viewModel.setResidentialAreas(gardenDataResult.getResultData());
                    if (currentFragment instanceof MapFragment) {// 当前在地图界面
                        ((MapFragment) currentFragment).gardenDataSet();
                    } else if (currentFragment instanceof ApartmentListFragment) {// 当前在小区列表界面
                        ((ApartmentListFragment) currentFragment).resetData();
                    }
                    if(filtratePopup!=null&&filtratePopup.isShowing()){
                        filtratePopup.dismiss();
                    }

                } else {
                    ToastUtil.shortShow(this, gardenDataResult.getMessage());
                }
                this.loadingWorker.closeLoading();
                break;
            case HomeViewModel.GET_GARDEN_DETAIL_ON_CITY_CHANG:
                DataResult<List<GardenDetail>> dataResult = (DataResult) msg.obj;
                if (dataResult.getSuccess()) {
                    viewModel.setResidentialAreas(dataResult.getResultData());
                    if (currentFragment instanceof MapFragment) {// 当前在地图界面
                        ((MapFragment) currentFragment).showDistrictTv();
                    } else if (currentFragment instanceof ApartmentListFragment) {// 当前在小区列表界面
                        ((ApartmentListFragment) currentFragment).resetData();
                    }
                    if(filtratePopup!=null&&filtratePopup.isShowing()){
                        filtratePopup.dismiss();
                    }

                } else {
                    ToastUtil.shortShow(this, dataResult.getMessage());
                }
                break;
            case HomeViewModel.FUZZY_SEARCH_GARDEN_DETAIL:
                DataResult<List<SearchResult>> searchResult = (DataResult) msg.obj;
                if(searchResult.getSuccess()){
                    if(gardenSearchPopup !=null&& gardenSearchPopup.isShowing()){
                        searchOperator.refreshAdapter(searchResult.getResultData());
                    }
                }
                break;
            case HomeViewModel.GET_SINGLE_GARDEN_DATAIL:
                DataResult<GardenDetail> obj = (DataResult) msg.obj;
                if(obj.getSuccess()){
                   List<GardenDetail> ads=new ArrayList<>();
                    ads.add(obj.getResultData());
                    viewModel.setResidentialAreas(ads);
                    MapFragment f=(MapFragment)getFragmentByTag(FrameTag.Map);
                    f.gardenDataSet();
                    if(gardenSearchPopup !=null&& gardenSearchPopup.isShowing()){
                        gardenSearchPopup.dismiss();
                    }
                }else{
                    ToastUtil.shortShow(this,obj.getMessage());
                }
                break;
            case HomeViewModel.CHECK_PACKAGE:
                boolean isDeleter=(boolean) msg.obj;
                if(isDeleter){
                    showDialog();
                }
                break;
            case HomeViewModel.HANDLE_GET_SEARCH_NAME:// 获取模糊搜索
                ResultInfo<UpdataBeen> resultup = (ResultInfo<UpdataBeen>) msg.obj;
                if (resultup.Success) {
                    if (resultup.Data != null) {
                        UpdataBeen updataBeen = resultup.Data;
                        String version = updataBeen.getVersion();
                        String versionName = VersionUtil.getVersionName(HomeActivity.this);
                        if (versionName != null&& version!= null){
                            if (!versionName.equals(version)){
                                showUpdataDialog(version);
                            }
                        }
                    }
                }
                break;
            default:
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        showFiltratePopup(FiltrateType.getFiltrateTypeById(id));
    }

    @Override
    public void onClick(View view) {
        boolean isRadioButton = false;
        int id = view.getId();
        switch (id) {
            case R.id.user_icon_img:
                //侧边菜单弹出，和隐藏
                createdSidePopWind();
                break;
            case R.id.back_tv:
                changFragmentByTag(FrameTag.Map);
                break;
            case R.id.search_img:
                showSearchPopup();
                break;
            case R.id.city_name_tv:
                showCityListPopWind();
                break;
            case R.id.area_rdb:
                isRadioButton = true;
                break;
            case R.id.house_rdb:
                isRadioButton = true;
                break;
            case R.id.price_rdb:
                isRadioButton = true;
                break;
            case R.id.school_rdb:
                isRadioButton = true;
                break;
        }
        if (isRadioButton) {
            showFiltratePopup(FiltrateType.getFiltrateTypeById(id));
            search_bar_rdg.check(id);
        }
    }

    private void showFiltratePopup(FiltrateType filtrateType) {
        if (filtratePopup == null) {
            DeviceInfo deviceInfo = FggApplication.getInstance().deviceInfo;
            int width = deviceInfo.ScreenWeight;
            int height = (int) (deviceInfo.ScreenHeight / 3 + 0.08 * deviceInfo.ScreenHeight);
            filtratePopup = viewModel.getDensityHelper().createPopWindows(this,
                    R.layout.layout_filtrate, R.color.white, width, height, true, false);
            filtrateViewOperator = new FiltrateViewOperator(this,
                    filtratePopup.getContentView());
            filtratePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    search_bar_rdg.clearCheck();
                }
            });
        }
        if (!filtratePopup.isShowing()) {
            filtratePopup.showAsDropDown(head_bar_view, 0, 0);
        }
        filtrateViewOperator.setCurrentFiltrateType(filtrateType);
    }

    private void showSearchPopup(){

        if(gardenSearchPopup ==null){
            DeviceInfo deviceInfo = FggApplication.getInstance().deviceInfo;
            gardenSearchPopup = viewModel.getDensityHelper().createPopWindows(this,
                    R.layout.layout_garden_search, R.color.white, deviceInfo.ScreenWeight,
                    deviceInfo.ScreenHeight, true, false);
            View view= gardenSearchPopup.getContentView();
            searchOperator=new GardenSearchOperator(this,view);
            gardenSearchPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
            });
        }
        gardenSearchPopup.showAtLocation(head_bar_view.getRootView(), Gravity.CENTER, 0, 0);
    }

    /***
     * 显示侧边菜单栏
     */
    private void createdSidePopWind() {
        if (sideMenuPopWin == null) {
            DeviceInfo deviceInfo = FggApplication.getInstance().deviceInfo;
            int width = (deviceInfo.ScreenWeight / 3) * 2;
            int height = (deviceInfo.ScreenHeight / 10) * 9;
            sideMenuPopWin = viewModel.getDensityHelper().createPopWindows(this,
                    R.layout.layout_side_menu, R.color.white, width, height, true, true);
            View view = sideMenuPopWin.getContentView();
            sideMenuOperator = new SideMenuOperator(view, this);
            sideMenuPopWin.setAnimationStyle(R.style.PopupWindow_animation);
        }
        sideMenuPopWin.showAsDropDown(head_bar_view, 0, 0);
        sideMenuOperator.isLogin();
    }

    /**
     * 城市切换窗口
     * */
    public void showCityListPopWind() {
        if (cityListPopup == null) {
            DeviceInfo deviceInfo = FggApplication.getInstance().deviceInfo;
            cityListPopup = viewModel.getDensityHelper().createPopWindows(this,
                    R.layout.layout_city_list, R.color.white, deviceInfo.ScreenWeight,
                    deviceInfo.ScreenHeight, true, true);
            View view=cityListPopup.getContentView();
            CityListOperator cityListOperator=new CityListOperator(this,view);

        }

        cityListPopup.showAtLocation(head_bar_view.getRootView(), Gravity.CENTER, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * frame tag
     */
    protected enum FrameTag {
        Map("mapFrame", 1),
        List("ListFrame", 2);

        private String name;
        private int index;

        FrameTag(String name, int index) {
            this.name = name;
            this.index = index;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sideMenuPopWin = null;
        sideMenuOperator = null;
    }

    /**
     * 设置一个新的三维旋转的容器视图。只翻一般，然后设置新的现实内容
     *
     * @param zheng    一个判断机制 如果为true 则向右翻转，如果false则向左翻转
     * @param fragment 传入的片段
     * @param start    起始位置
     * @param end      结束位置
     */
    public void applyRotation(final boolean zheng, final Fragment fragment,
                              final float start, final float end, FrameTag frameTag) {
        // Find the center of the container
        final float centerX = framelayout.getWidth() / 2.0f;
        final float centerY = framelayout.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Util_Rotate3DAnimation rotation = new Util_Rotate3DAnimation(
                start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(100);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(zheng, fragment, frameTag));// 添加监听执行现实内容的切换
        framelayout.startAnimation(rotation);// 执行上半场翻转动画
    }

    /**
     * 执行完上半部分旋转之后，设置要显示的新的View然后继续执行下半部分旋转
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        private final boolean mPosition;
        private final Fragment fragment;
        private FrameTag frameTag;

        private DisplayNextView(boolean zheng, Fragment fragment, FrameTag frameTag) {
            mPosition = zheng;
            this.fragment = fragment;
            this.frameTag = frameTag;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            framelayout.post(new SwapViews(mPosition, fragment, this.frameTag));// 添加新的View
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * 添加要显示的新的View，并执行下半部分的旋转操作
     */
    private final class SwapViews implements Runnable {
        private final boolean mPosition;
        private final Fragment fragment;
        private FrameTag frameTag;

        public SwapViews(boolean position, Fragment fragment, FrameTag frameTag) {
            mPosition = position;
            this.fragment = fragment;
            this.frameTag = frameTag;
        }

        public void run() {
            final float centerX = framelayout.getWidth() / 2.0f;
            final float centerY = framelayout.getHeight() / 2.0f;
            Util_Rotate3DAnimation rotation;
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            if (mPosition) {
                rotation = new Util_Rotate3DAnimation(-90, 0, centerX, centerY,
                        310.0f, false);
            } else {
                rotation = new Util_Rotate3DAnimation(90, 0, centerX, centerY,
                        310.0f, false);
            }
            /*if (fragment != null && fragment.isAdded()) {
                ft.show(fragment);
            } else {
                ft.add(R.id.home_frame, fragment, this.frameTag.name);
            }*/
            ft.replace(R.id.home_frame, fragment);
            ft.commit();
            rotation.setDuration(100);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            framelayout.startAnimation(rotation);
        }
    }

    public void setCityName() {
        city_name_tv.setText(FggApplication.getInstance().city.getCityName());
    }

    //选择城市后重新设置中心点
    public void dismissCityListPop(){
        if(cityListPopup!=null&&cityListPopup.isShowing()){
            cityListPopup.dismiss();
            setCityName();
            MapFragment mapFragment=(MapFragment)getFragmentByTag(FrameTag.Map);
            mapFragment.toCityCenter(FggApplication.getInstance().city);
            //无论出于哪个界面都重新加载该城市对应的数据
            //重新加载行政区数据
            doWork("加载.....", HomeViewModel.GET_DISTRICT, null);
            //重新加载地铁线路数据
            doWork("加载.....", HomeViewModel.GET_SUBWAY_INFO, null);
            //请求小区数据，根据城市中心点求情城市小区数据
//            ApartmentListFragment aparListFrm=(ApartmentListFragment)getFragmentByTag(FrameTag.List);
            ResidentialParameters residentialParameters=new ResidentialParameters();
            doWork("", HomeViewModel.GET_GARDEN_DETAIL_ON_CITY_CHANG, residentialParameters);

        }
        if(searchOperator!=null){
            searchOperator.setCityTv();
        }
    }

    private Fragment getFragmentByTag(FrameTag tag){
        FragmentManager fm = this.getSupportFragmentManager();
        Fragment fragment= fm.findFragmentByTag(tag.name);
        return fragment;
    }

    public void dismissGardenSearchPopup(){
        if(gardenSearchPopup!=null&&gardenSearchPopup.isShowing()){
            gardenSearchPopup.dismiss();
        }
    }

    /**
     * 双击返回键退出
     *
     */
    long[] mHits = new long[2];//该参数控制点击多少次触发事件,现在是2次
    @Override
    public void onBackPressed() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 1000)) {
            //双击事件触发
            super.onBackPressed();
            System.exit(0);
        }else {
            Toast.makeText(HomeActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 展示升级对话框
     * @param version
     */
    private void showUpdataDialog(String version) {

        AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this).create();
        dialog.setTitle("提示");
        dialog.setMessage("发现新版本 v" + version + "\n是否升级?");
        // 确定
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //跳转到浏览器
                IntentHelper.intentToWebUpdata(HomeActivity.this, SettingBaseFragment.updata_url);
                dialog.dismiss();
            }
        });
        // 确定
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // 显示对话框
        dialog.show();
    }
}
