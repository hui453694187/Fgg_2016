package com.yunfangdata.fgg.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yunfang.framework.base.BaseApplication;
import com.yunfangdata.fgg.enumObj.City;
import com.yunfangdata.fgg.model.PersonContacts;
import com.yunfangdata.fgg.model.DeviceInfo;
import com.yunfangdata.fgg.model.PersonRecipients;
import com.yunfangdata.fgg.model.UserInfo;
import com.yunfangdata.fgg.utils.CrashHandler;
import com.yunfangdata.fgg.utils.SPUtil;
import com.yunfangdata.fgg.view.LoadingUtil;

import java.util.ArrayList;

/**
 * Created by Kevin on 2015/12/3.
 */
public class FggApplication extends BaseApplication{
    // {{

    /**
     * 整个应用的上下文
     * */
    private static FggApplication mApplication;

    /**
     * 用户信息
     */
    public DeviceInfo deviceInfo;

    /**
     * 当前城市
     * 默认北京
     */
    public City city =City.getCityByCityCode("131");

    /**
     * 当前登录的用户
     */
    private static UserInfo userInfo;
    public static UserInfo getUserInfo() {
        if(userInfo == null){
            userInfo = new UserInfo();
        }
        return userInfo;
    }
    public static void setUserInfo(UserInfo mUserInfo) {
        userInfo = mUserInfo;
    }

    /**
     * 默认看房联系人
     */
    private static PersonContacts personContacts;
    public static PersonContacts getPersonContacts() {
        if(personContacts == null){
            personContacts = new PersonContacts();
        }
        return personContacts;
    }
    public static void setPersonContacts(PersonContacts mPersonContacts) {
        personContacts = mPersonContacts;
    }

    /**
     * 默认收件人
     */
    private static PersonRecipients personRecipients;
    public static PersonRecipients getPersonRecipients() {
        if(personRecipients == null){
            personRecipients = new PersonRecipients();
        }
        return personRecipients;
    }
    public static void setPersonRecipients(PersonRecipients mPersonRecipients) {
        personRecipients = mPersonRecipients;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        setLoadingWorkerType(LoadingUtil.class);//初始化对话框
        getDeviceInfo(); // 初始化设备信息
        initImageLoader();
        SPUtil.getUserName(this);


        /*//异常捕捉
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);*/
    }

    /***
     * 初始化ImageLoader
     */
    private void initImageLoader(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)//
                .threadPriority(Thread.NORM_PRIORITY - 2)//
                .denyCacheImageMultipleSizesInMemory()//
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//
                .diskCacheSize(10 * 1024 * 1024).diskCacheFileCount(300)//
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 提供一个获取整个应用上下文的方法
     * */
    public static FggApplication getInstance() {
        return mApplication;
    }


    /**
     * 获取包信息。info.versionCode可以取得当前的版本号
     * */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info == null) {
            info = new PackageInfo();
        }
        return info;
    }


    //{{ 当前APP使用的PackageInfo，主要用于反射

    /**
     * 当前APP的包名
     */
    private ArrayList<String> appPackageInfoes = new ArrayList<String>();

    /**
     *  用于设置当前APP使用的PackageInfo，主要用于反射
     *  用在XML解释、Json解释中，一般记录DTO和Model所在的PackageInfo路径
     */
    public void setPackageInfoNames(ArrayList<String> packageInfoes){
        appPackageInfoes = packageInfoes;
    }

    /**
     *	获取用户设定的当前APP使用的PackageInfo，主要用于反射
     *  用在XML解释、Json解释中，一般记录DTO和Model所在的PackageInfo路径
     */
    public ArrayList<String> getPackageInfoNames(){
        return appPackageInfoes;
    }

    //}}

    /**
     * 获取屏幕完整高度和宽度
     */
    private void getDeviceInfo() {
        deviceInfo = new DeviceInfo();

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        // 获得屏幕的高宽（用来适配分辨率）
        deviceInfo.ScreenWeight = dm.widthPixels;
        deviceInfo.ScreenHeight = dm.heightPixels;

        FggApplication.getInstance().getApplicationContext();
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        deviceInfo.DeviceId = tm.getDeviceId();
        deviceInfo.DeviceSoftwareVersion = tm.getDeviceSoftwareVersion();
        deviceInfo.Line1Number = tm.getLine1Number();
        deviceInfo.NetworkCountryIso = tm.getNetworkCountryIso();
        deviceInfo.NetworkOperator = tm.getNetworkOperator();
        deviceInfo.NetworkOperatorName = tm.getNetworkOperatorName();
        deviceInfo.NetworkType = tm.getNetworkType();
        deviceInfo.PhoneType = tm.getPhoneType();
        deviceInfo.SimCountryIso = tm.getSimCountryIso();
        deviceInfo.SimOperator = tm.getSimOperator();
        deviceInfo.SimOperatorName = tm.getSimOperatorName();
        deviceInfo.SimSerialNumber = tm.getSimSerialNumber();
        deviceInfo.SimState = tm.getSimState();
        deviceInfo.SubscriberId = tm.getSubscriberId();
        deviceInfo.VoiceMailNumber = tm.getVoiceMailNumber();
    }
}
