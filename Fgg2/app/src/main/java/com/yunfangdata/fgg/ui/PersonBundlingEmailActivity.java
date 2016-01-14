package com.yunfangdata.fgg.ui;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunfang.framework.base.BaseWorkerActivity;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.logic.PersonOperator;
import com.yunfangdata.fgg.model.BindingEmailBeen;
import com.yunfangdata.fgg.model.ResultInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 贺隽 on 2015/12/14.
 * 个人中心-绑定邮箱
 */
public class PersonBundlingEmailActivity extends NewBaseActivity {


    /**
     * 绑定邮箱
     */
    private final int HANDLE_GET_MENU = 100;

    /**
     * 输入的email
     */
    private String emailAdresss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_binding_email);
        assignViews();

    }


    private ImageButton navBtnBack;
    private TextView navTxtTitle;
    private TextView navTxtOp;

    private EditText pbmEdEmail;
    private ImageView pbmEdEmailWarning;
    private Button pbmBtBinding;

    /**
     * 初始化UI
     */
    private void assignViews() {
        //头部
        navBtnBack = (ImageButton) findViewById(R.id.nav_btn_back);
        navTxtTitle = (TextView) findViewById(R.id.nav_txt_title);
        navTxtOp = (TextView) findViewById(R.id.nav_txt_op);

        pbmEdEmail = (EditText) findViewById(R.id.pbm_ed_email);
        pbmEdEmailWarning = (ImageView) findViewById(R.id.pbm_ed_email_warning);
        pbmBtBinding = (Button) findViewById(R.id.pbm_bt_binding);

        //设置标题
        navTxtTitle.setText(R.string.peb_title);


        //设置点击事件
        pbmBtBinding.setOnClickListener(clickListener);
        pbmEdEmailWarning.setOnClickListener(clickListener);
        navBtnBack.setOnClickListener(clickListener);

        String email = FggApplication.getUserInfo().getEmail();
        if (email!= null){
            pbmEdEmail.setHint("当前已经绑定:" + email);
        }
    }
    /**
     * 按钮的点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nav_btn_back: //返回
                    finish();
                    break;
                case R.id.pbm_bt_binding: //绑定
                    actionClickBinding();
                    break;
                case R.id.pbm_ed_email_warning: //警告
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * 判断输入框是否为空
     *
     * @param editText //输入框
     * @return
     */
    private boolean isEdittextNotNull(EditText editText) {
        return (editText.getText() != null && !editText.getText().toString().trim().isEmpty());
    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }
    /**
     * 点击绑定
     */
    private void actionClickBinding() {
        if (!isEdittextNotNull(pbmEdEmail)){
            showToast("请输入邮箱");
            return;
        }

        String email = pbmEdEmail.getText().toString().trim();
        if (!checkEmail(email)){
            showToast("邮箱格式错误,请重新输入");
            return;
        }

        //去掉点击事件
        pbmBtBinding.setClickable(false);
        emailAdresss = email;
        doWork("", HANDLE_GET_MENU, "");
    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_MENU:
                resultMsg.obj = PersonOperator.requertBindingEmail(emailAdresss);
                break;
        }
        mUiHandler.sendMessage(resultMsg);
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_MENU:  //看房联系人
                //点击事件
                pbmBtBinding.setClickable(true);

                ResultInfo<BindingEmailBeen> result = (ResultInfo<BindingEmailBeen>) msg.obj;
                if (result.Success) {
                    if (result.Data != null) {
                        BindingEmailBeen entrustEvaluateReturnBeen = result.Data;
                        if (entrustEvaluateReturnBeen.getSuccess().equals("true")) {
                            showToast(result.Message);

                            finish();
                        }else {
                            showToast(result.Message);
                        }
                    } else {
                        showToast(result.Message);
                    }
                } else {
                    showToast(result.Message);
                }
                break;
        }
    }

    @Override
    public void showToast(String msg) {
        /**
         * 覆盖掉父类的toast 太长了
         * 使用短toast
         */
        Toast.makeText(PersonBundlingEmailActivity.this, msg, Toast.LENGTH_SHORT).show();
//        super.showToast(msg);
    }

}
