package com.yunfangdata.fgg.ui;

import android.os.Bundle;
import android.os.Message;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.helper.IntentHelper;
import com.yunfangdata.fgg.utils.SPUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * logo页面
 *
 * 2015年12月23日 17:08:40  zjt
 */
public class SplashActivity extends NewBaseActivity {

    private Timer timer;
    /**
     * 延时多少秒后进入主页面
     */
    private int delayTime = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //是不是第一次登录
        final boolean isfirst = SPUtil.Isfirst(SplashActivity.this);

        timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {


                if (isfirst) { //欢迎页面
                    IntentHelper.intentToWelcomeActivity(SplashActivity.this);
                    SplashActivity.this.finish();
                } else {
                    IntentHelper.intentToHomeActivity(SplashActivity.this);
                    SplashActivity.this.finish();
                }
            }
        }, delayTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void handleBackgroundMessage(Message message) {

    }
}
