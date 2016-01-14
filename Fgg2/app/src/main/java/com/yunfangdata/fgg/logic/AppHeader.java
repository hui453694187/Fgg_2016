package com.yunfangdata.fgg.logic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunfang.framework.utils.WinDisplay;
import com.yunfang.framework.utils.YFLog;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.utils.DialogUtil;

/**
 * Created by 贺隽 on 2015/12/15
 * 系统的主标题信息
 */
public class AppHeader {

    // {{ 元素：按钮、属性

    LinearLayout headerView;

    /**
     * 返回按钮
     */
    private ImageButton nav_btn_back;

    /**
     * 标题
     */
    private TextView nav_txt_Title;

    /**
     * 操作
     */
    private TextView nav_txt_op;

    /**
     * 当前上下文
     */
    private Context currentContext;

    // }}


    /**
     * 构造方法
     *
     * @param context 当前实例
     * @param viewID  实例化ViewID
     */
    public AppHeader(Context context, int viewID) {
        currentContext = context;
        headerView = (LinearLayout) ((Activity) context).findViewById(viewID);
        nav_btn_back = (ImageButton) headerView.findViewById(R.id.nav_btn_back);
        nav_txt_Title = (TextView) headerView.findViewById(R.id.nav_txt_title);
        nav_txt_op = (TextView) headerView.findViewById(R.id.nav_txt_op);

        nav_btn_back.setOnClickListener(back);
    }

    /**
     * 返回上一页面
     */
    private OnClickListener back = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Activity) currentContext).finish();
            ((Activity) currentContext).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }
    };

    /**
     * 设置标题
     * @param title 标题
     */
    public void setTitle(String title) {
        nav_txt_Title.setText(title);
    }

    /**
     * 设置操作按钮文本
     * @param concent 文本
     */
    public void setOperationText(String concent) {
        nav_txt_op.setText(concent);
    }

    /**
     * 隐藏 返回
     */
    public void hideBackView() {
        nav_btn_back.setVisibility(View.GONE);
    }

    /**
     * 显示 返回
     */
    public void showBackView() {
        nav_btn_back.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏 操作
     */
    public void hideOperationView() {
        nav_txt_op.setVisibility(View.GONE);
    }

    /**
     * 显示 操作
     */
    public void showOperationView() {
        nav_txt_op.setVisibility(View.VISIBLE);
    }

    /**
     * 设置操作的事件
     *
     * @param listener 事件源
     */
    public void setNavOpOnClickListener(OnClickListener listener) {
        nav_txt_op.setOnClickListener(listener);
    }

    // {{

    /**
     * 确认对话框
     *
     * @param title           标题
     * @param msg             信息
     * @param confirmListener 确认事件 为NULL则不显示确认按钮
     */
    public void showDialog(String title, String msg, OnClickListener confirmListener) {
        if (activityIsFinishing()) {
            return;
        }
        try{
            final Dialog dialog_checksdcard = DialogUtil.commonDialog(currentContext, R.layout.dialog_view_info);
            WindowManager.LayoutParams params = dialog_checksdcard.getWindow().getAttributes();
            Point point = WinDisplay.getWidthAndHeight(currentContext);
            switch (FggApplication.PageSize) {
                case 6:// 手机
                    params.width = (int) ((point.x) * (0.8));
                    params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    dialog_checksdcard.getWindow().setAttributes(params);
                    break;
                case 15:// 平板
                    params.width = (int) ((point.x) * (0.5));
                    params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    dialog_checksdcard.getWindow().setAttributes(params);
                    break;
            }
            TextView dialog_view_info_concent = (TextView) dialog_checksdcard.findViewById(R.id.dialog_view_info_concent);
            TextView dialog_view_info_title = (TextView) dialog_checksdcard.findViewById(R.id.dialog_view_info_title);
            Button dialog_view_info_confirm = (Button) dialog_checksdcard.findViewById(R.id.dialog_view_info_confirm);
            Button dialog_view_info_cancel = (Button) dialog_checksdcard.findViewById(R.id.dialog_view_info_cancel);

            dialog_view_info_title.setText(title);
            dialog_view_info_concent.setText(msg);

            if (confirmListener == null) {
                dialog_view_info_cancel.setVisibility(View.GONE);
                dialog_view_info_confirm.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog_checksdcard.dismiss();
                    }
                });
            } else {
                dialog_view_info_cancel.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        dialog_checksdcard.dismiss();
                    }
                });
                dialog_view_info_confirm.setOnClickListener(confirmListener);
            }

            if (dialog_checksdcard.isShowing()) {
                dialog_checksdcard.dismiss();
            } else {
                dialog_checksdcard.show();
            }
        }
        catch (Exception ex){
            YFLog.d(ex.getMessage());
        }

    }

    // }}

    /**
     * 判断当前currentContext是否正在运行
     *
     * @return
     */
    private boolean activityIsFinishing() {
        boolean unFinishing = true;
        try {
            if (currentContext != null) {
                //判断当前活动是否退出了。
                unFinishing = ((Activity) currentContext).isFinishing();
            } else {
                unFinishing = true;
            }
        } catch (Exception e) {
            unFinishing = true;
        }
        return unFinishing;
    }
    // }}
}
