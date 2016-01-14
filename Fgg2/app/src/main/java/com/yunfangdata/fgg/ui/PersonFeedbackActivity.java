package com.yunfangdata.fgg.ui;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yunfang.framework.utils.ToastUtil;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.logic.AppHeader;
import com.yunfangdata.fgg.logic.PersonOperator;
import com.yunfangdata.fgg.model.DataResult;

/**
 * Created by 贺隽 on 2015/12/14.
 * 个人中心-意见反馈
 */
public class PersonFeedbackActivity extends NewBaseActivity {

    /**
     * 主菜单的广播
     */
    public AppHeader appHeader;

    private final static int SENT_MSG=101;

    private EditText text_edt;

    private Button submit_bt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_feedback);
        assignViews();
    }

    /**
     * 初始化UI
     */
    private void assignViews() {
        appHeader = new AppHeader(this, R.id.nav_title);
        text_edt=$(R.id.text_edt);
        submit_bt=$(R.id.submit_bt);

        Bundle bundle = this.getIntent().getExtras();
        //设置显示信息
        appHeader.setTitle(bundle.getString("title"));
        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(text_edt.getText().toString())){
                    doWork("正在反馈....",PersonFeedbackActivity.SENT_MSG,null);
                }else{
                    ToastUtil.shortShow(PersonFeedbackActivity.this,"请先输入意见内容");
                }

            }
        });

    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case PersonFeedbackActivity.SENT_MSG:
                resultMsg.obj=PersonOperator.sendMsg(text_edt.getText().toString());
                break;
        }
        mUiHandler.sendMessage(resultMsg);
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case PersonFeedbackActivity.SENT_MSG:
                DataResult<Boolean> result=(DataResult)msg.obj;
                if(result.getSuccess()){
                    this.finish();
                    this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                }
                this.loadingWorker.closeLoading();
                ToastUtil.shortShow(this,result.getMessage());
                break;
        }
    }

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                default:
                    break;
            }
        }
    };

}
