package com.yunfangdata.fgg.base;

import android.os.Message;

import com.umeng.analytics.MobclickAgent;
import com.yunfang.framework.base.BaseWorkerFragmentActivity;

/**
 * Created by Administrator on 2015-12-25.
 */
public class NewBaseFragmentActivity extends BaseWorkerFragmentActivity {
    @Override
    protected void handleBackgroundMessage(Message message) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
