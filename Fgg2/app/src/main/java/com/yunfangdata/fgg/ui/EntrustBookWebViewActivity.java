package com.yunfangdata.fgg.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.helper.IntentHelper;

/**
 * 展示委托评估协议的webView
 *
 * 2015年12月15日 12:34:10 zjt
 */
public class EntrustBookWebViewActivity extends NewBaseActivity{
    private static final String TAG = "EntrustBookWebViewActivity";
    private static final int LOGLEVEL = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrust_book_web_view);

        assignViews();

        loadWebView();
    }

    @Override
    protected void handleBackgroundMessage(Message message) {

    }


    /**
     * 加载估值页面
     * AssessmentCommission
     */
    private void loadWebView() {
        WebSettings webSettings = webviewBase.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webviewBase.loadUrl("file:///android_asset/AssessmentCommission.html");
        webviewBase.canGoBack();
        //	myWebView.c
        webviewBase.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webviewProgressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webviewProgressbar.setVisibility(View.GONE);
            }
        });

    }

    private WebView webviewBase;
    private ProgressBar webviewProgressbar;
    private LinearLayout ebRlBottom;
    private CheckBox ebCheckBox;
    private Button ebBtEnter;
    private ImageButton navBtnBack;
    private TextView navTxtTitle;
    private TextView navTxtOp;

    /**
     * 初始化界面
     */
    private void assignViews() {

        //头部
        navBtnBack = (ImageButton) findViewById(R.id.nav_btn_back);
        navTxtTitle = (TextView) findViewById(R.id.nav_txt_title);
        navTxtOp = (TextView) findViewById(R.id.nav_txt_op);

        webviewBase = (WebView) findViewById(R.id.webview_base);
        webviewProgressbar = (ProgressBar) findViewById(R.id.webview_progressbar);
        ebRlBottom = (LinearLayout) findViewById(R.id.eb_rl_bottom);
        ebCheckBox = (CheckBox) findViewById(R.id.eb_checkBox);
        ebBtEnter = (Button) findViewById(R.id.eb_bt_enter);


        //设置标题
        navTxtTitle.setText(R.string.eb_title);


        //返回键
        navBtnBack.setOnClickListener(myOnClickListener);
        //同意协议
        ebBtEnter.setOnClickListener(myOnClickListener);

//        //监听是否同意
//        ebCheckBox.setOnCheckedChangeListener(myOnCheckedChangeListener);
    }
//
//
//    /**
//     * 监听checkBox
//     */
//    private CompoundButton.OnCheckedChangeListener myOnCheckedChangeListener =  new CompoundButton.OnCheckedChangeListener(){
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//        }
//    };
//

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nav_btn_back: //点击返回
                    finish();
                    break;
                case R.id.eb_bt_enter: //进入委托评估
                    if (ebCheckBox.isChecked()){ //是否同意
                        //跳转到委托评估
                        IntentHelper.intentToEntrustEvaluation(EntrustBookWebViewActivity.this);
                    }else {
                        showToast("必须同意协议书,才能做委托");
                    }
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //从委托成功 回来的话,自己结束
        if (Constant.KEY_INTENT_TO_CLOSE_YOURSELF_REQUESTCODE == requestCode && resultCode == Constant.KEY_INTENT_TO_CLOSE_YOURSELF){
            finish();
        }
    }
}
