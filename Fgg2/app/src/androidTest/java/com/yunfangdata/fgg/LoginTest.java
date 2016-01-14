/*
 * This is an example test project created in Eclipse to test NotePad which is a sample 
 * project located in AndroidSDK/samples/android-11/NotePad
 * 
 * 
 * You can run these test cases either on the emulator or on device. Right click
 * the test project and select Run As --> Run As Android JUnit Test
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

package com.yunfangdata.fgg;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.yunfangdata.fgg.ui.SplashActivity;


/**
 * 登录
 */
public class LoginTest extends ActivityInstrumentationTestCase2<SplashActivity> {

    private Solo solo;

    public LoginTest() {
        super(SplashActivity.class);

    }

    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testLogin() throws Exception {

        //初始化主屏幕
        initHomeActivity();

        //如果出现  会员登录 -- 点他
        if (solo.searchText("会员登录")) {
            solo.clickOnText("会员登录");
        }

        solo.waitForText("密码登录");

        //当前是不是RegisterAndLoginActivity
        solo.assertCurrentActivity("Expected RegisterAndLoginActivity activity", "RegisterAndLoginActivity");

        //如果出现  密码登录 -- 点他
        if (solo.searchText("密码登录")) {
            solo.clickOnText("密码登录");
        }

        //获取帐号 输入框
        EditText rl_pass_name = (EditText) solo.getView(R.id.rl_pass_name);
        //清除输入内容
        solo.clearEditText(rl_pass_name);
        //输入帐号
        solo.enterText(rl_pass_name, "18126727670");

        //另外一种输入的方法  密码
        solo.enterText(1, "q123456789");

        //点击登录
        solo.clickOnView(solo.getView(R.id.rl_bt_login));

        //当前是不是HomeActivity
        solo.assertCurrentActivity("Expected HomeActivity activity", "HomeActivity");

        boolean noteFound = solo.waitForText("登录成功");
        //Assert that Note 1 test is found
        assertTrue("登录成功 is not found", noteFound);

    }

    /**
     * 初始化主屏幕
     */
    private void initHomeActivity() {
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
