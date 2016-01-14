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

import com.robotium.solo.Solo;
import com.yunfangdata.fgg.ui.SplashActivity;
import com.yunfangdata.fgg.utils.DensityHelper;


/**
 * 买房估值
 */
public class BuyHouseTest extends ActivityInstrumentationTestCase2<SplashActivity> {

    private Solo solo;
    private int screenWidth;
    private int screenHeight;

    public BuyHouseTest() {
        super(SplashActivity.class);

    }

    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation(), getActivity());
        screenWidth = DensityHelper.getScreenWidth(getActivity());
        screenHeight = DensityHelper.getScreenHeight(getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }


    /**
     * 测试买房估值
     *
     * @throws Exception
     */
    public void testBuyHouse() throws Exception {

        //初始化主屏幕
        initHomeActivity(solo);

        //如果出现  买房估值 -- 点他
        if (solo.searchText("买房估值")) {
            solo.clickOnText("买房估值");
        }

        //当前是不是   BuyHouseActivity
        solo.assertCurrentActivity("Expected BuyHouseActivity activity", "BuyHouseActivity");

        //另外一种输入的方法  小区名称
        solo.enterText(0, "五栋大楼");
        //另外一种输入的方法  面积
        solo.enterText(1, "233");

        //点击居室类型
        solo.clickOnText("居室类型");
        if (solo.waitForText("确定")) {
            int random = (int) (Math.random() * (screenHeight / 3));
            //滚动选择器
            solo.drag(screenWidth / 2, screenWidth / 2, screenHeight - 10, screenHeight - random, 10);
        }
        //点确定
        solo.clickOnText("确定");

        //另外一种输入的方法  楼栋
        solo.enterText(2, "发财大楼");
        //另外一种输入的方法  门牌号
        solo.enterText(3, "4栋404");

        //点击居室类型
        solo.clickOnText("房屋朝向");
        if (solo.waitForText("确定")) {
            //滚动选择器
            int random2 = (int) (Math.random() * (screenHeight / 3));
            solo.drag(screenWidth / 2, screenWidth / 2, screenHeight - 10, screenHeight - random2, 10);
        }
        //点确定
        solo.clickOnText("确定");

        //另外一种输入的方法  所在楼层
        solo.enterText(4, "44");
        //另外一种输入的方法  总楼层
        solo.enterText(5, "88");

        //另外一种输入的方法  建成年代
        solo.enterText(6, "2015");
        //另外一种输入的方法  预购总价
        solo.enterText(7, "2333");

        //点击 我要估值
        solo.clickOnText("我要估值");

        //当前是不是 ValuationWebViewActivity
        solo.assertCurrentActivity("Expected ValuationWebViewActivity activity", "ValuationWebViewActivity");

        //睡个5秒 等待网页加载
        solo.sleep(5000);

        boolean noteFound = solo.waitForText("买房估值");
        //Assert that Note 1 test is found
        assertTrue("买房估值 is not found", noteFound);

    }

    /**
     * 初始化主屏幕
     *
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
