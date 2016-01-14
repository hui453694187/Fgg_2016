package com.yunfangdata.fgg.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.model.UserInfo;

/**
 * sp 帮助类
 *
 * Created by zjt on 2015-12-17.
 */
public class SPUtil {

    /**
     * 保存用户名到SharedPreferences
     * @param context  //上下文
     * @param userName  //用户名
     */
    public static void saveUserName(Context context,String userName){
        //获取全局用户
        UserInfo userInfo = FggApplication.getUserInfo();
        //设置用户名称
        userInfo.setUserName(userName);

        //保存到sp
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SP_CONFIG, Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString(Constant.SP_CONFIG_USER_NAME, userName);
        editor.commit();//提交修改
    }


    /**
     * 保存是否第一次
     * @param context  //上下文
     */
    public static void saveIsfirst(Context context){

        //保存到sp
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SP_CONFIG, Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean(Constant.SP_CONFIG_IS_FIRST_USE, false);
        editor.commit();//提交修改
    }


    /**
     *  获取是否第一次使用软件
     * @param context
     * @return
     */
    public static boolean Isfirst(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SP_CONFIG, Context.MODE_PRIVATE); //私有数据
        return sharedPreferences.getBoolean(Constant.SP_CONFIG_IS_FIRST_USE, true);
    }

    /**
     * 获取保存的用户名
     *
     * @param context 上下文
     * @return  存在时返回用户明----不存在时返回为空
     */
    public static String getUserName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SP_CONFIG, Context.MODE_PRIVATE); //私有数据
        String userName=sharedPreferences.getString(Constant.SP_CONFIG_USER_NAME, null);
        if(!TextUtils.isEmpty(userName)){
            //获取全局用户
            UserInfo userInfo = FggApplication.getUserInfo();
            //设置用户名称
            userInfo.setUserName(userName);
        }
        return userName;
    }


}
