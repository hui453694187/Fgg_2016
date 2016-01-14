package com.yunfangdata.fgg.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.PersonMessage;
import com.yunfangdata.fgg.ui.BuyHouseActivity;
import com.yunfangdata.fgg.ui.EntrustBookWebViewActivity;
import com.yunfangdata.fgg.ui.EntrustEvaluateActivity;
import com.yunfangdata.fgg.ui.EntrustListActivity;
import com.yunfangdata.fgg.ui.HomeActivity;
import com.yunfangdata.fgg.ui.HouseDetailActivity;
import com.yunfangdata.fgg.ui.HypocrisyQueryActivity;
import com.yunfangdata.fgg.ui.PersonActivity;
import com.yunfangdata.fgg.ui.PersonContactsActivity;
import com.yunfangdata.fgg.ui.PersonMessageDetailsActivity;
import com.yunfangdata.fgg.ui.PersonRecipientsActivity;
import com.yunfangdata.fgg.ui.RegisterAndLoginActivity;
import com.yunfangdata.fgg.ui.SaleHouseActivity;
import com.yunfangdata.fgg.ui.ScheduleActivity;
import com.yunfangdata.fgg.ui.SettingActivity;
import com.yunfangdata.fgg.ui.ValuationWebViewActivity;
import com.yunfangdata.fgg.ui.WelcomeActivity;

/**
 * 跳转帮助类
 *
 * Created by zjt on 2015-12-13.
 */
public class IntentHelper {

    /**
     * 带参数
     * 跳转到买房页面
     * @param activity  //Activity
     * @param cityName  //城市名称  例如:beijing
     * @param residentialAreaName  //小区名称  例如:缺德小区
     * @param residentialAreaID   //小区ID  例如：110
     */
    public static final void intentToBuyHouse(Activity activity,String cityName,String residentialAreaName,int residentialAreaID){
        Intent intent = new Intent(activity, BuyHouseActivity.class);
        intent.putExtra(Constant.KEY_INTENT_TO_BUY_RESIDENTIALAREANAME, residentialAreaName);
        intent.putExtra(Constant.KEY_INTENT_TO_HOUSE_DETAIL_RESIDENTIALAREAID, residentialAreaID);
        intent.putExtra(Constant.KEY_INTENT_TO_HOUSE_DETAIL_CITYNAME, cityName);
        activity.startActivity(intent);
    }

    /**
     * 仅仅带城市名称
     *
     * 跳转到买房页面
     * @param activity  //Activity
     * @param cityName  //城市名称  例如:beijing
     */
    public static final void intentToBuyHouse(Activity activity,String cityName){
        Intent intent = new Intent(activity, BuyHouseActivity.class);
        intent.putExtra(Constant.KEY_INTENT_TO_HOUSE_DETAIL_CITYNAME, cityName);
        activity.startActivity(intent);
    }

    /**
     * 带参数
     * 跳转到卖房页面
     * @param activity  //Activity
     * @param cityName  //城市名称  例如:beijing
     * @param residentialAreaName  //小区名称  例如:缺德小区
     * @param residentialAreaID   //小区ID  例如：110
     */
    public static final void intentToSaleHouse(Activity activity,String cityName,String residentialAreaName,int residentialAreaID){
        Intent intent = new Intent(activity, SaleHouseActivity.class);
        intent.putExtra(Constant.KEY_INTENT_TO_BUY_RESIDENTIALAREANAME, residentialAreaName);
        intent.putExtra(Constant.KEY_INTENT_TO_HOUSE_DETAIL_RESIDENTIALAREAID, residentialAreaID);
        intent.putExtra(Constant.KEY_INTENT_TO_HOUSE_DETAIL_CITYNAME, cityName);
        activity.startActivity(intent);
    }

    /**
     * 仅仅带城市名称
     *
     * 跳转到卖房页面
     * @param activity  //Activity
     * @param cityName  //城市名称  例如:beijing
     */
    public static final void intentToSaleHouse(Activity activity,String cityName){
        Intent intent = new Intent(activity, SaleHouseActivity.class);
        intent.putExtra(Constant.KEY_INTENT_TO_HOUSE_DETAIL_CITYNAME, cityName);
        activity.startActivity(intent);
    }

    /**
     * 仅仅带城市名称
     *
     * 跳转到进度查询页面
     * @param activity  //Activity
     * @param cityName  //城市名称  例如:beijing
     */
    public static final void intentToScheduleQuery(Activity activity,String cityName){
        Intent intent = new Intent(activity, ScheduleActivity.class);
        intent.putExtra(Constant.KEY_INTENT_TO_HOUSE_DETAIL_CITYNAME, cityName);
        activity.startActivity(intent);
    }

    /**
     * 仅仅带城市名称
     *
     * 跳转到真伪查询页面
     * @param activity  //Activity
     * @param cityName  //城市名称  例如:beijing
     */
    public static final void intentToHypocrisyQuery(Activity activity,String cityName){
        Intent intent = new Intent(activity, HypocrisyQueryActivity.class);
        intent.putExtra(Constant.KEY_INTENT_TO_HOUSE_DETAIL_CITYNAME, cityName);
        activity.startActivity(intent);
    }

    /**
     * 跳转到估值页面
     * @param activity  //Activity
     * @param url  //网址 例如 www.baidu.com
     */
    public static final void intentToValuationWebView(Activity activity,String url,int type){
        Intent intent = new Intent(activity, ValuationWebViewActivity.class);
        intent.putExtra(Constant.KEY_INTENT_TO_VALUATION_WEB_VIEW, url);
        intent.putExtra(Constant.KEY_INTENT_TO_VALUATION_WEB_VIEW_TYPE, type);
        activity.startActivity(intent);
    }

    /**
     * 跳转到注册登录页面
     * @param activity  //Activity
     */
    public static final void intentToRegisterAndLogin(Activity activity){
        Intent intent = new Intent(activity, RegisterAndLoginActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 
     *
     * 跳转到个人中心
     * @param activity  //Activity
     */
    public static final void intentToPerson(Activity activity){
        Intent intent = new Intent(activity, PersonActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 跳转到委托评估页面
     * 协议页面
     * @param activity  //Activity
     */
    public static final void intentToEntrustBookWebView(Activity activity){
        Intent intent = new Intent(activity, EntrustBookWebViewActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 跳转到小区详情
     * @param activity  //Activity
     */
    public static final void intentToHouseDetial(Activity activity,int residentialAreaID){
        Intent intent = new Intent(activity, HouseDetailActivity.class);
        intent.putExtra(Constant.KEY_INTENT_TO_HOUSE_DETAIL_RESIDENTIALAREAID, residentialAreaID);
        activity.startActivity(intent);
    }

    /**
     * 跳转到委托评估
     * @param activity  //Activity
     */
    public static final void intentToEntrustEvaluation(Activity activity){
        Intent intent = new Intent(activity, EntrustEvaluateActivity.class);
        activity.startActivityForResult(intent, Constant.KEY_INTENT_TO_CLOSE_YOURSELF_REQUESTCODE);
    }
    /**
     * 跳转到委托列表
     * @param activity  //Activity
     */
    public static final void intentToEntrustList(Activity activity){
        Intent intent = new Intent(activity, EntrustListActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 跳转到设置
     * @param activity  //Activity
     */
    public static final void intentToSettingActivity(Activity activity){
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    /***
     * 跳转获取看房联系人
     * @param activity
     */
    public static void toPersonContacts4Result(Activity activity){
        Intent intent=new Intent(activity,PersonContactsActivity.class);
        intent.putExtra(Constant.GET_PERSION_CONTACTS_4_RESULT + "", true);
        Bundle bundle = new Bundle();
        bundle.putString("title", "看房联系人");
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, Constant.GET_PERSION_CONTACTS_4_RESULT);
    }

    /***
     * 跳转获取收件人信息
     * @param activity
     */
    public static void toPersonRecipients4Result(Activity activity){
        Intent intent=new Intent(activity,PersonRecipientsActivity.class);
        intent.putExtra(Constant.GET_RECIPIENTS_4_RESULT+"", true);
        Bundle bundle = new Bundle();
        bundle.putString("title", "收件人信息");
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, Constant.GET_RECIPIENTS_4_RESULT);
    }

    public static void toMessageDetails(Activity activity,PersonMessage msg){
        Intent i=new Intent(activity, PersonMessageDetailsActivity.class);
        Bundle b=new Bundle();
        b.putSerializable("msgObj", msg);
        i.putExtras(b);
        activity.startActivity(i);
    }

    /**
     * 跳转到主页面
     * @param activity  //Activity
     */
    public static final void intentToHomeActivity(Activity activity){
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 跳转到欢迎页面
     * @param activity  //Activity
     */
    public static final void intentToWelcomeActivity(Activity activity){
        Intent intent = new Intent(activity, WelcomeActivity.class);
        activity.startActivity(intent);
    }
     /**
     * 跳转到网页更新
      * @param activity  //Activity
      * @param updata_url  //网址
      */
    public static final void intentToWebUpdata(Activity activity, String updata_url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(updata_url);
        intent.setData(content_url);
        activity.startActivity(intent);
    }
}
