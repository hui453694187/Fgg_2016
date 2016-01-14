package com.yunfangdata.fgg.ui;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.http.task.VerificationCodeTask;
import com.yunfangdata.fgg.logic.PersonOperator;
import com.yunfangdata.fgg.model.ResetUserNameBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.VerificationCodeBeen;
import com.yunfangdata.fgg.viewmodel.RegisterAndLoginViewModel;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 贺隽 on 2015/12/14.
 * 个人中心-解绑手机
 */
public class PersonUnbindingMobileActivity extends NewBaseActivity {
    /**
     * 输入的手机号码是否正确
     */
    private boolean isphoneNumberRight = false;
    /**
     * 解绑手机
     */
    private final int HANDLE_GET_RESET_USERNAME = 100;
    /**
     * 获取验证码的HANDLE
     */
    private final int HANDLE_GET_VERIFICATION_CODE = 111;
    /**
     * 定时器改变UI的HANDLE
     */
    private final int HANDLE_COUNT_DOWN_TIMER = 113;
    /**
     * 原用户名
     */
    private String userName;

    /**
     * 新用户名
     */
    private String newUserName;
    /**
     * 验证码
     */
    private String vCode;
    /**
     * 验证码倒数60秒的发送
     */
    private Timer count_down_timer;

    /**
     * 发送验证码计算时间用
     */
    private int count_down_timer_int = 0;
    /**
     * 发送验证码剩余秒数
     */
    private static final int count_down_timer_int_cound = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_unbinding_mobile);
        assignViews();

        getDefaultData();
    }

    /**
     * 获取默认数据
     */
    private void getDefaultData() {

        String userName1 = FggApplication.getUserInfo().getUserName();
        if (userName1 == null){
            showToast("你还未登录!");
            finish();
        }
        puOldNumber.setText(userName1);
        //用户名
        userName = userName1;
    }

    private ImageButton navBtnBack;
    private TextView navTxtTitle;
    private TextView navTxtOp;
    private TextView puOldNumber;
    private EditText puNewNumber;
    private ImageView puNewNumberClean;
    private RelativeLayout rlSmsLoginRelativeLayoutPhonenumber;
    private EditText puVerificationPhone;
    private TextView puVerificationPhoneGet;
    private ImageView puVerificationPhoneGetClean;
    private Button puBtEnter;

    private void assignViews() {
        //头部
        navBtnBack = (ImageButton) findViewById(R.id.nav_btn_back);
        navTxtTitle = (TextView) findViewById(R.id.nav_txt_title);
        navTxtOp = (TextView) findViewById(R.id.nav_txt_op);

        puOldNumber = (TextView) findViewById(R.id.pu_old_number);
        puNewNumber = (EditText) findViewById(R.id.pu_new_number);
        puNewNumberClean = (ImageView) findViewById(R.id.pu_new_number_clean);
        rlSmsLoginRelativeLayoutPhonenumber = (RelativeLayout) findViewById(R.id.rl_sms_login_relativeLayout_phonenumber);
        puVerificationPhone = (EditText) findViewById(R.id.pu_verification_phone);
        puVerificationPhoneGet = (TextView) findViewById(R.id.pu_verification_phone_get);
        puVerificationPhoneGetClean = (ImageView) findViewById(R.id.pu_verification_phone_get_clean);
        puBtEnter = (Button) findViewById(R.id.pu_bt_enter);

        //设置标题
        navTxtTitle.setText(R.string.pum_title);

        //设置点击事件
        navBtnBack.setOnClickListener(clickListener);

        //解绑手机
        puBtEnter.setOnClickListener(clickListener);
        //获取验证码
        puVerificationPhoneGet.setOnClickListener(clickListener);

        //-------------------
        //清除-- -- 名字
        puNewNumberClean.setOnClickListener(clickListener);
        //清除--- 验证码
        puVerificationPhoneGetClean.setOnClickListener(clickListener);
        //用户名监听
        puNewNumber.setOnFocusChangeListener(myOnFocusChangeListener);

        // -- 名字 -- 是否显示清除按钮
        puNewNumber.addTextChangedListener(myPassNameTextListener);
        //-- 验证码 -- 是否显示清除按钮
        puVerificationPhone.addTextChangedListener(myPassNameTextListener2);
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
                case R.id.pu_bt_enter: //解绑手机
                    actionResetName();
                    break;
                case R.id.pu_new_number_clean: ///清除-- -- 名字
                    puNewNumber.setText("");
                    break;
                case R.id.pu_verification_phone_get_clean: //清除--- 验证码
                    puVerificationPhone.setText("");
                    break;
                case R.id.pu_verification_phone_get: //获取验证码
                    getCloudVerificationCode();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 重新设置密码
     */
    private void actionResetName() {
        //请输入新号码
        if (!isEdittextNotNull(puNewNumber)){
            showToast("请输入新号码");
            return;
        }
        //手机号码不正确,请重新输入
        if(!isphoneNumberRight){
            showToast("手机号码不正确,请重新输入");
            return;
        }
        //请输入新号码
        if (!isEdittextNotNull(puVerificationPhone)){
            showToast("请输入验证码");
            return;
        }
        //新号码
        newUserName = puNewNumber.getText().toString().trim();
        //验证码
        vCode = puVerificationPhone.getText().toString().trim();

        //点击事件
        puBtEnter.setClickable(false);
        puBtEnter.setText("正在解绑");
        doWork("", HANDLE_GET_RESET_USERNAME, "");
    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_RESET_USERNAME:
                resultMsg.obj = PersonOperator.resetUserName(userName, newUserName, vCode);
                break;

            case HANDLE_GET_VERIFICATION_CODE:// 获取验证码
                VerificationCodeTask verificationCodeTask = new VerificationCodeTask();
                ResultInfo<VerificationCodeBeen> requestsn = verificationCodeTask.request(userName, 2);
                resultMsg.obj = requestsn;
                break;
        }
        mUiHandler.sendMessage(resultMsg);
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_RESET_USERNAME:
                //点击事件
                puBtEnter.setClickable(true);
                puBtEnter.setText("确定");

                ResultInfo<ResetUserNameBeen> result = (ResultInfo<ResetUserNameBeen>) msg.obj;
                if (result.Success) {
                    if (result.Data != null) {
                        ResetUserNameBeen entrustEvaluateReturnBeen = result.Data;
                        if (entrustEvaluateReturnBeen.getSuccess().equals("true")) {
                            showToast(result.Message);

                            finish();
                        } else {
                            showToast(result.Message);
                        }
                    } else {
                        showToast(result.Message);
                    }
                } else {
                    showToast(result.Message);
                }
                break;

            case HANDLE_GET_VERIFICATION_CODE: // 获取验证码
                ResultInfo<VerificationCodeBeen> resultcode = (ResultInfo<VerificationCodeBeen>) msg.obj;
                if (resultcode.Success) {
                    if (resultcode.Data != null) {
                        VerificationCodeBeen verificationCodeBeen = resultcode.Data;
                        if (verificationCodeBeen.getSuccess().equals("true")) {

                            showToast("验证码发送成功!");
                        }
                    } else {
                        showToast(resultcode.Message);
                    }
                } else {
                    showToast(resultcode.Message);
                }
                break;
            case HANDLE_COUNT_DOWN_TIMER: // 倒计时
                changeTimer(puVerificationPhoneGet);
                break;
        }
    }
    /**
     * 改变倒计时显示
     *
     * @param textView //要改变的view
     */
    private void changeTimer(TextView textView) {
        if (count_down_timer_int > 0) {
            count_down_timer_int--;
            textView.setBackgroundResource(R.drawable.juxing_yuanjiao_gay);
            textView.setClickable(false);
            textView.setText("重新获取(" + count_down_timer_int + ")");
        } else {
            cancelTimer();
            textView.setBackgroundResource(R.drawable.button_selector_bule);
            textView.setClickable(true);
            textView.setText("获取验证码");
        }
    }
    /**
     * 定时器
     * 验证码倒数60秒
     */
    private void countDownTimer() {

        cancelTimer();
        count_down_timer_int = count_down_timer_int_cound;
        count_down_timer = new Timer();
        count_down_timer.schedule(new TimerTask() {
            @Override
            public void run() {

                Message resultMsg = new Message();
                resultMsg.what = HANDLE_COUNT_DOWN_TIMER;
                mUiHandler.sendMessage(resultMsg);
            }
        }, 0, 1000);
    }

    /**
     * 获取云端的验证码
     */
    private void getCloudVerificationCode() {
        //开启倒计时
        countDownTimer();

        Message get_detail = new Message();
        get_detail.what = HANDLE_GET_VERIFICATION_CODE;
        mBackgroundHandler.sendMessage(get_detail);

    }

    /**
     * //取消定时器
     */
    private void cancelTimer() {
        if (count_down_timer != null) {
            count_down_timer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //取消定时器
        cancelTimer();
    }
    /**
     * 监听手机号码输入框焦点获取情况,失去焦点就判断手机号码是否正确
     */
    private View.OnFocusChangeListener myOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //当失去焦点时
                if (isEdittextNotNull((EditText) v)) {
                    if (CheckMobilePhoneNumber(((EditText) v).getText().toString().trim())) {
                        //可以获取的手机验证码
                        isphoneNumberRight = true;
                    } else {
                        isphoneNumberRight = false;
                        showToastTop("请输入正确的手机号码!");
                    }
                }
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
     * 检查手机号码是否符合规矩
     *
     * @param number
     * @return
     */
    private boolean CheckMobilePhoneNumber(String number) {
        //正则表达式
        Pattern p = Pattern.compile(RegisterAndLoginViewModel.regular_expression);
        Matcher m = p.matcher(number);
        return m.find();
    }

    /**
     * 显示在顶部的Toast
     *
     * @param msg
     */
    public void showToastTop(String msg) {
        Toast toast = Toast.makeText(PersonUnbindingMobileActivity.this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 监听密码登录的名字
     */
    private TextWatcher myPassNameTextListener = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            setVisibility(puNewNumberClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    /**
     * 监听密码登录的密码
     */
    private TextWatcher myPassNameTextListener2 = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            setVisibility(puVerificationPhoneGetClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    /**
     * 设置某个控件受否显示
     *
     * @param view   // 控件
     * @param isshow //是否显示
     */
    private void setVisibility(View view, boolean isshow) {
        if (isshow) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }

    }

    @Override
    public void showToast(String msg) {
        /**
         * 覆盖掉父类的toast 太长了
         * 使用短toast
         */
        Toast.makeText(PersonUnbindingMobileActivity.this, msg, Toast.LENGTH_SHORT).show();
//        super.showToast(msg);
    }
}
