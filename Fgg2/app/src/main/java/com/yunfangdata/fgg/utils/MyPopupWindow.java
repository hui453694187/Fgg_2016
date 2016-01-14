package com.yunfangdata.fgg.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunfangdata.fgg.R;

/**
 * Created by zjt on 2015-12-24.
 */
public class MyPopupWindow {

    private Context context;
    private TextView textView;
    private PopupWindow popupWindow;

    public MyPopupWindow(Context context) {
        this.context = context;
    }

    public PopupWindow initPopupWindow() {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_show_error, null);

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        textView = (TextView) contentView.findViewById(R.id.pp_textview);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.my_popu_orange2));

        return popupWindow;
    }

    /**
     * 显示popuwindow
     * @param view
     * @param text
     */
    public void showPopup(View view, String text) {
        textView.setText(text);
        popupWindow.showAsDropDown(view);
    }


    /**
     * 隐藏popupWindow
     */
    public void destroyPopoWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}
