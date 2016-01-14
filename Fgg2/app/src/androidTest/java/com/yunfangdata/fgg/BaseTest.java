package com.yunfangdata.fgg;

import com.robotium.solo.Solo;

/**
 * 基础测试
 *
 * Created by zjt on 2015-12-30.
 */
public class BaseTest {
    /**
     * 初始化主屏幕
     * @param solo
     */
    public void initHomeActivity(Solo solo) {
        //解锁屏幕 并没有什么用
        solo.unlockScreen();

        //如果出现"加载"卡住页面,,,按下返回键
        if (solo.searchText("加载")) {
            solo.goBack();
        }
        //如果出现"取消"卡住页面,,,按下返回键  -- 升级对话框
        if (solo.searchText("取消")) {
            solo.goBack();
        }

        //当前是不是HomeActivity
        solo.assertCurrentActivity("Expected HomeActivity activity", "HomeActivity");

        if (solo.waitForText("区域")) {

            //获取侧边滑动栏
//		ImageView user_icon_img = (ImageView)solo.getView(R.id.user_icon_img);
            //点击侧边栏按钮,滑出侧边栏
            solo.clickOnView(solo.getView(R.id.user_icon_img));

        }
    }

}
