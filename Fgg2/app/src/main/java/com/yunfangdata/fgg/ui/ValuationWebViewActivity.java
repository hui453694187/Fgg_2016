package com.yunfangdata.fgg.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.utils.ZLog;

/**
 * 展示估值的webView
 *
 * 2015年12月15日 12:34:10 zjt
 */
public class ValuationWebViewActivity extends NewBaseActivity {

    /**
     * 当前是买还是卖房
     */
    private int type;

    private static final String TAG = "ValuationWebViewActivity";
    private static final int LOGLEVEL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valuation_web_view);

        assignViews();

        getIntentData();

    }

    @Override
    protected void handleBackgroundMessage(Message message) {

    }

    /**
     * 获取传递过来的数据
     */
    private void getIntentData() {
        //获取传过来数据
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            //获取传过来的网址
            String url = extras.getString(Constant.KEY_INTENT_TO_VALUATION_WEB_VIEW);
            type = extras.getInt(Constant.KEY_INTENT_TO_VALUATION_WEB_VIEW_TYPE);

            if (type == Constant.TYPE_BUY_HOUSE){
                //设置标题
                navTxtTitle.setText(R.string.hd_bt_buy_values);
            }else {
                //设置标题
                navTxtTitle.setText(R.string.hd_bt_sell_values);
            }

            if (url != null && !url.isEmpty()) {

                ZLog.Zlogi("url = " + url ,TAG,LOGLEVEL);
                loadWebView(url);
            }
        }
    }


    /**
     * 加载估值页面
     * @param url
     */
    private void loadWebView(String url) {
        WebSettings webSettings = webviewBase.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webviewBase.loadUrl(url);
        webviewBase.canGoBack();
        //	myWebView.c
        webviewBase.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (webviewProgressbar.getVisibility() != View.VISIBLE) {
                    webviewProgressbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webviewProgressbar.setVisibility(View.GONE);
                //显示webView
                webviewBase.setVisibility(View.VISIBLE);
            }
        });

    }

    private ImageView buyButtonBack;
    private WebView webviewBase;
    private ProgressBar webviewProgressbar;
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
        //隐藏webView
        webviewBase.setVisibility(View.INVISIBLE);




        //返回键
        navBtnBack.setOnClickListener(myOnClickListener);
    }
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
                default:
                    break;
            }
        }

    };
}
