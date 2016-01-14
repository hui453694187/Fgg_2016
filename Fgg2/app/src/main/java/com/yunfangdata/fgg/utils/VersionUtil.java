package com.yunfangdata.fgg.utils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 版本管理
 * Created by zjt on 2015-12-30.
 */
public class VersionUtil {

    /**
     * 得到应用程序的版本名称
     */
    public static String getVersionName(Activity activity) {
        // 用来管理手机的APK
        PackageManager pm = activity.getPackageManager();

        try {
            // 得到知道APK的功能清单文件
            PackageInfo info = pm.getPackageInfo(activity.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
