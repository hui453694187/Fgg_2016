package com.yunfangdata.fgg.ui;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.NewBaseFragmentActivity;
import com.yunfangdata.fgg.fragment.FragmentHelper;
import com.yunfangdata.fgg.fragment.FragmentTag;
import com.yunfangdata.fgg.fragment.SettingAboutUsFragment;
import com.yunfangdata.fgg.fragment.SettingBaseFragment;
import com.yunfangdata.fgg.fragment.SettingContactFragment;
import com.yunfangdata.fgg.fragment.SettingStatementFragment;

public class SettingActivity extends NewBaseFragmentActivity implements
        SettingAboutUsFragment.ChangeTitleFromAboutUs,
        SettingContactFragment.ChangeTitleFromContact,
        SettingStatementFragment.ChangeTitleFromStatement{

    /**
     * FragmentManager管理者
     */
    private FragmentManager fMgr;

    /**
     * 所有标题
     */
    private String[] allTitle = {"设置","关于我们","免则声明","联系我们"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

         // 初始化界面
        assignViews();

        //添加基础的Fragment
        AddBaseFragment();
    }



    @Override
    protected void handleBackgroundMessage(Message message) {

    }


    private ImageButton navBtnBack;
    private TextView navTxtTitle;
    private TextView navTxtOp;

    private RelativeLayout settingContain;
    /**
     * 初始化界面
     */
    private void assignViews() {

        //头部
        navBtnBack = (ImageButton) findViewById(R.id.nav_btn_back);
        navTxtTitle = (TextView) findViewById(R.id.nav_txt_title);
        navTxtOp = (TextView) findViewById(R.id.nav_txt_op);

        settingContain = (RelativeLayout) findViewById(R.id.setting_contain);

        //设置标题
        navTxtTitle.setText(allTitle[0]);

        //返回按钮
        navBtnBack.setOnClickListener(myBaseOnClickListener);
    }

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myBaseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nav_btn_back: //返回
                    actionClickback();
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void onBackPressed() {
        //如果标题是 -- 设置
        if (navTxtTitle.getText()!= null && navTxtTitle.getText().toString().equals(allTitle[0])){
            super.onBackPressed();
        }else { //如果标题 -- 不是 设置
            AddBaseFragment();
        }
    }

    /**
     * 点击返回
     */
    private void actionClickback() {
        //如果标题是 -- 设置
        if (navTxtTitle.getText()!= null && navTxtTitle.getText().toString().equals(allTitle[0])){
            finish();
        }else { //如果标题 -- 不是设置
            AddBaseFragment();
        }
    }

    /**
     * 添加基础的Fragment
     */
    private void AddBaseFragment() {
        //设置标题
        navTxtTitle.setText(allTitle[0]);

        if (fMgr == null) {
            fMgr = getSupportFragmentManager();
        }
        Fragment fragment_settings = fMgr.findFragmentByTag(FragmentTag.SETTING_BASE_FRAGMENT);
        if (fragment_settings != null) {
            FragmentHelper.hideAllFragment(fMgr);
            FragmentHelper.showFragment(fMgr, fragment_settings);
        } else {
            FragmentHelper.hideAllFragment(fMgr);
            fragment_settings = new SettingBaseFragment();
            FragmentHelper.addFragment(fMgr, fragment_settings,FragmentTag.SETTING_BASE_FRAGMENT,R.id.setting_contain);
        }
    }


    @Override
    public void changeTitleFromAboutUs() {
        //设置标题
        navTxtTitle.setText(allTitle[1]);
    }


    @Override
    public void changeTitleFromContact() {
        //设置标题
        navTxtTitle.setText(allTitle[3]);
    }

    @Override
    public void changeTitleFromStatement() {
        //设置标题
        navTxtTitle.setText(allTitle[2]);
    }
}
