package com.yunfangdata.fgg.ui;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yunfang.framework.utils.ToastUtil;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.logic.AppHeader;
import com.yunfangdata.fgg.logic.PersonOperator;
import com.yunfangdata.fgg.model.DataResult;

/**
 * Created by 贺隽 on 2015/12/14.
 * 个人中心-修改密码
 */
public class PersonPasswordActivity extends NewBaseActivity implements View.OnClickListener {

    /**
     * 主菜单的广播
     */
    public AppHeader appHeader;

    /**
     * 获取菜单
     */
    private final int RESET_PASS_WORD = 100;

    private EditText pass_edt;

    private EditText new_pass_edt;

    private EditText new_pass_edt2;

    private Button submit_bt;

    private ImageView rl_pass_name_clean1;

    private ImageView rl_pass_name_clean2;

    private ImageView rl_pass_name_clean3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_password);
        assignViews();
    }

    /**
     * 初始化UI
     */
    private void assignViews() {
        appHeader = new AppHeader(this, R.id.nav_title);
        pass_edt = $(R.id.pass_edt);
        new_pass_edt = $(R.id.new_pass_edt);
        new_pass_edt2 = $(R.id.new_pass_edt2);
        submit_bt = $(R.id.submit_bt);
        rl_pass_name_clean1 = $(R.id.rl_pass_name_clean1);
        rl_pass_name_clean2 = $(R.id.rl_pass_name_clean2);
        rl_pass_name_clean3 = $(R.id.rl_pass_name_clean3);

        Bundle bundle = this.getIntent().getExtras();
        //设置显示信息
        appHeader.setTitle(bundle.getString("title"));

        submit_bt.setOnClickListener(this);
        pass_edt.addTextChangedListener(new EdtTextWatcher(rl_pass_name_clean1));
        new_pass_edt.addTextChangedListener(new EdtTextWatcher(rl_pass_name_clean2));
        new_pass_edt2.addTextChangedListener(new EdtTextWatcher(rl_pass_name_clean3));

        rl_pass_name_clean1.setOnClickListener(this);
        rl_pass_name_clean2.setOnClickListener(this);
        rl_pass_name_clean3.setOnClickListener(this);

    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case RESET_PASS_WORD:
                String newPwd = new_pass_edt.getText().toString();
                String oldPwd = pass_edt.getText().toString();
                resultMsg.obj = PersonOperator.resetPwd(newPwd, oldPwd);
                break;
        }
        mUiHandler.sendMessage(resultMsg);
    }

    @Override
    protected void handleUiMessage(Message msg) {
        switch (msg.what) {
            case RESET_PASS_WORD:
                DataResult<Boolean> result = (DataResult) msg.obj;
                if (result.getSuccess()) {
                    ToastUtil.shortShow(this,"密码修改成功");
                    this.finish();
                    this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                } else {
                    ToastUtil.shortShow(this, result.getMessage());
                }
                this.loadingWorker.closeLoading();
                break;
        }
        this.loadingWorker.closeLoading();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_pass_name_clean1:
                pass_edt.setText("");
                break;
            case R.id.rl_pass_name_clean2:
                new_pass_edt.setText("");
                break;
            case R.id.rl_pass_name_clean3:
                new_pass_edt2.setText("");
                break;
            case R.id.submit_bt:
                resetPwd();
                break;
        }
    }

    private void resetPwd(){
        String pass = pass_edt.getText().toString();
        String pass1 = new_pass_edt.getText().toString();
        String pass2 = new_pass_edt2.getText().toString();
        if (verifyPass(pass) && verifyPass(pass1) && verifyPass(pass2)) {
            if (pass1.equals(pass2)) {
                doWork("正在修改密码.....", RESET_PASS_WORD, null);
            } else {
                ToastUtil.shortShow(this, "新密码输入不一致");
            }
        } else {
            ToastUtil.shortShow(this, "密码必须是8~16位");
        }
    }

    private boolean verifyPass(String pass) {
        boolean isVerify = false;
        if (pass.trim().length() >= 8 && pass.trim().length() <= 16) {
            isVerify = true;
        }
        return isVerify;
    }

    private class EdtTextWatcher implements TextWatcher{

        ImageView img;

        private EdtTextWatcher(ImageView edt){
            this.img=edt;

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() > 0) {
                img.setVisibility(View.VISIBLE);
            } else {
                img.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}
