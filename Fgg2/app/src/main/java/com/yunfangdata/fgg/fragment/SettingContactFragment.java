package com.yunfangdata.fgg.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yunfang.framework.base.BaseWorkerFragment;
import com.yunfangdata.fgg.R;

/**
 * 联系我们
 *
 *2015年12月20日 21:06:57  zjt
 */
public class SettingContactFragment extends BaseWorkerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_contact, container, false);

        changetitel();
        assignViews(view);

        loadWebView();

        return view;
    }

    private WebView webviewBase;
    private ProgressBar webviewProgressbar;

    private void assignViews(View view) {
        webviewBase = (WebView) view.findViewById(R.id.webview_base);
        webviewProgressbar = (ProgressBar) view.findViewById(R.id.webview_progressbar);
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
        webviewBase.loadUrl("file:///android_asset/Contact.html");
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


    /**
     * 显示的时候再次改变标题
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            changetitel();
        }
    }

    /**
     * 改变标题文字
     */
    public void changetitel(){
        if (getActivity() instanceof ChangeTitleFromContact) {
            ((ChangeTitleFromContact) getActivity()).changeTitleFromContact();
        }
    }

    @Override
    protected void handlerBackgroundHandler(Message message) {

    }

    /**
     * 改变标题文字的接口
     */
    public interface ChangeTitleFromContact{
        /**
         * 改变标题文字的接口
         */
        void changeTitleFromContact();
    }
}
