package com.yunfangdata.fgg.ui;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.adapter.MyViewpageAdapter;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.helper.IntentHelper;
import com.yunfangdata.fgg.utils.SPUtil;

import java.util.ArrayList;

/**
 * 欢迎界面页面
 * <p/>
 * 2015年12月23日 17:08:40  zjt
 */
public class WelcomeActivity extends NewBaseActivity {


    private TextView vpTvIntentHome;
    private LinearLayout llpoint;
    private ViewPager viewpager;

    /**
     * 上一次显示的点
     */
    private int lastpoin = 0;

    /**
     * 3个view
     */
    private View viewpage_1;
    private View viewpage_2;
    private View viewpage_3;
    /**
     * 3个view的列表
     */
    private ArrayList<View> mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        assignViews();

        initViewPage();

    }

    @Override
    protected void handleBackgroundMessage(Message message) {

    }


    private void assignViews() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        llpoint = (LinearLayout) findViewById(R.id.llpoint);
    }

    /**
     * 初始化viewpage
     */
    private void initViewPage() {
        viewpage_1 = View.inflate(WelcomeActivity.this, R.layout.viewpage_1, null);
        viewpage_2 = View.inflate(WelcomeActivity.this, R.layout.viewpage_2, null);
        viewpage_3 = View.inflate(WelcomeActivity.this, R.layout.viewpage_3, null);

        vpTvIntentHome = (TextView) viewpage_3.findViewById(R.id.vp_tv_intent_home);
        vpTvIntentHome.setOnClickListener(myOnClickListener);

        mViews = new ArrayList<View>();
        mViews.add(viewpage_1);
        mViews.add(viewpage_2);
        mViews.add(viewpage_3);

        MyViewpageAdapter adapter = new MyViewpageAdapter(mViews);
        viewpager.setAdapter(adapter);

        //添加底部的点
        addPointView();

        viewpager.addOnPageChangeListener(myOnPageChangeListener);

    }

    /**
     * 添加底部的点
     */
    private void addPointView() {
        for (int i = 0; i < mViews.size(); i++) {
            //添加点
            ImageView poind = new ImageView(WelcomeActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 20;
            poind.setLayoutParams(params);
            poind.setBackgroundResource(R.drawable.dianselector);
            if (i == 0) {
                poind.setEnabled(true);
            } else {
                poind.setEnabled(false);
            }

            llpoint.addView(poind);
        }
    }

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.vp_tv_intent_home: //点击进入主页面
                    //保存是否第一次进入软件
                    SPUtil.saveIsfirst(WelcomeActivity.this);
                    //跳转到主页面
                    IntentHelper.intentToHomeActivity(WelcomeActivity.this);
                    //挂了
                    finish();
                    break;
                default:
                    break;
            }
        }

    };


    /**
     * viewpage滚动监听
     */
    private ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            llpoint.getChildAt(position).setEnabled(true);
            llpoint.getChildAt(lastpoin).setEnabled(false);
            lastpoin = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
