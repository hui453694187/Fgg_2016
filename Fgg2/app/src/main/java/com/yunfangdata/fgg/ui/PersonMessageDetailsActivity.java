package com.yunfangdata.fgg.ui;

import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.adapter.PersonMenuAdapter;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.logic.AppHeader;
import com.yunfangdata.fgg.model.PersonMessage;

/**
 * Created by 贺隽 on 2015/12/14.
 * 个人中心-消息中心-详情
 */
public class PersonMessageDetailsActivity extends NewBaseActivity {

    /**
     * 主菜单的广播
     */
    public AppHeader appHeader;

    /**
     * 获取数据
     */
    private final int HANDLE_GET_DATA = 100;

    /**
     * 标题
     */
    TextView person_msg_details_txt_title;

    /**
     * 详细信息
     */
    TextView person_msg_details_txt_concent;

    /**
     * 显示数据
     */
    PersonMessage personMessage;

    /**
     * 菜单数据源
     */
    private PersonMenuAdapter personMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_message_details);
        assignViews();
    }

    /**
     * 初始化UI
     */
    private void assignViews() {
        appHeader = new AppHeader(this, R.id.nav_title);
        person_msg_details_txt_title = (TextView) findViewById(R.id.person_msg_details_txt_title);
        person_msg_details_txt_concent = (TextView) findViewById(R.id.person_msg_details_txt_concent);
        try {
            personMessage = (PersonMessage) this.getIntent().getExtras().get("msgObj");
        }catch (Exception e){
            e.printStackTrace();
            personMessage=new PersonMessage();
        }
        person_msg_details_txt_title.setText(personMessage.getXiaoXiTitle());
        person_msg_details_txt_concent.setText(personMessage.getXiaoXiNeiRong());
        //设置显示信息
        appHeader.setTitle("消息详情");
    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        resultMsg.what = msg.what;
        switch (msg.what) {

        }
        mUiHandler.sendMessage(resultMsg);
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {

        }
    }

}
