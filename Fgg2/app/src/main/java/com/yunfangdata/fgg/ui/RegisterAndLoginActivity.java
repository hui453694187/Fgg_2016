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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunfang.framework.base.BaseWorkerActivity;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.http.task.ForgetPasswordTask;
import com.yunfangdata.fgg.http.task.ForgetPasswordVerificationCodeTask;
import com.yunfangdata.fgg.http.task.PasswordLoginTask;
import com.yunfangdata.fgg.http.task.SmsLoginTask;
import com.yunfangdata.fgg.http.task.SmsRegisterTask;
import com.yunfangdata.fgg.http.task.VerificationCodeTask;
import com.yunfangdata.fgg.model.ForgetPasswordBeen;
import com.yunfangdata.fgg.model.ForgetPasswordVerificationCodeBeen;
import com.yunfangdata.fgg.model.PasswordLoginBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.SMSRegisterBeen;
import com.yunfangdata.fgg.model.SmsLoginBeen;
import com.yunfangdata.fgg.model.VerificationCodeBeen;
import com.yunfangdata.fgg.utils.KeyBoardUtil;
import com.yunfangdata.fgg.utils.SPUtil;
import com.yunfangdata.fgg.viewmodel.RegisterAndLoginViewModel;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterAndLoginActivity extends NewBaseActivity {
    private static final String TAG = "RegisterAndLoginActivity";
    private static final int LOGLEVEL = 1;
    /**
     * 当前显示在Activity的是哪个View
     * 默认密码登录页面
     */
    private int whoShowInView = Login_password;
    /**
     * 密码登录页面
     */
    private static final int Login_password = 0;
    /**
     * 短信登录页面
     */
    private static final int Login_sms = 1;
    /**
     * 短信注册页面
     */
    private static final int register_sms = 2;
    /**
     * 忘记密码页面
     */
    private static final int forget_password = 3;

    /**
     * 密码登录的HANDLE
     */
    private final int HANDLE_GET_PASSWORD_LOGIN = 110;

    /**
     * 获取验证码的HANDLE
     */
    private final int HANDLE_GET_VERIFICATION_CODE = 111;
    /**
     * 短信登录的的HANDLE
     */
    private final int HANDLE_SMS_LOGIN = 112;
    /**
     * 定时器改变UI的HANDLE
     */
    private final int HANDLE_COUNT_DOWN_TIMER = 113;
    /**
     * 短信注册的HANDLE
     */
    private final int HANDLE_SMS_REGISTER = 114;

    /**
     * 忘记密码的HANDLE
     */
    private final int HANDLE_FORGET_PASSWORD = 115;
    /**
     * 忘记密码获取验证码的HANDLE
     */
    private final int HANDLE_FORGET_PASSWORD_VERIFICATION_CODE = 116;

    /**
     * 获取验证码的HANDLE
     * 的Type值
     */
    private final String HANDLE_GET_VERIFICATION_CODE_TYPE = "HANDLE_GET_VERIFICATION_CODE_TYPE";
    /**
     * 获取验证码的HANDLE
     * 的号码
     */
    private final String HANDLE_GET_VERIFICATION_CODE_PHONE = "HANDLE_GET_VERIFICATION_CODE_PHONE";

    /**
     * 本机生成的验证码
     */
    private String verification_code_local;

    /**
     * 短信登录
     * 是否获取到了云端返回的验证码
     */
    private boolean is_verification_code_cloud_return = false;
    /**
     * 短信注册
     * 是否获取到了云端返回的验证码
     */
    private boolean is_Register_verification_code_cloud_return = false;
    /**
     * 短信登录
     * 是否获取到了云端返回的验证码
     */
    private boolean is_forget_psw_verification_code_cloud_return = false;

    /**
     * 输入的登录手机号码是否正确
     */
    private boolean isphoneNumberRight = false;

    /**
     * 输入的注册手机号码是否正确
     */
    private boolean isRegistephoneNumberRight = false;
    /**
     * 输入的忘记密码手机号码是否正确
     */
    private boolean isForgetPhoneNumberRight = false;

    /**
     * 输入的密码是否符合规范
     */
    private boolean isRegistePasswordRight = false;

    /**
     * 验证码倒数60秒的发送
     */
    private Timer count_down_timer;

    /**
     * 发送验证码剩余秒数
     */
    private int count_down_timer_int = 60;

    /**
     * 当前的用户名
     */
    private String userName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_and_login);

        assignViews();

        changeView();
    }

    /**
     * 保存用户信息
     * @param userName
     */
    private void saveUserInfo(String userName){
        SPUtil.saveUserName(RegisterAndLoginActivity.this,userName);

        finish();
    }


    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        Bundle bundle = msg.getData();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_PASSWORD_LOGIN:// 密码登录
                PasswordLoginTask passwordLoginTask = new PasswordLoginTask();
                String pswName = rlPassName.getText().toString().trim();
                userName = pswName;//保存用户名
                String pswPsw = rlPassPassword.getText().toString().trim();
                ResultInfo<PasswordLoginBeen> resultInfo = passwordLoginTask.request(pswName, pswPsw);
                resultMsg.obj = resultInfo;
                mUiHandler.sendMessage(resultMsg);
                break;
            case HANDLE_GET_VERIFICATION_CODE:// 获取验证码
                //获取传递过来的名字
                int type = bundle.getInt(HANDLE_GET_VERIFICATION_CODE_TYPE);
                String phonenumber = bundle.getString(HANDLE_GET_VERIFICATION_CODE_PHONE);
                VerificationCodeTask verificationCodeTask = new VerificationCodeTask();
                ResultInfo<VerificationCodeBeen> requestsn = verificationCodeTask.request(phonenumber, type);
                resultMsg.obj = requestsn;
                mUiHandler.sendMessage(resultMsg);
                break;
            case HANDLE_FORGET_PASSWORD_VERIFICATION_CODE:// 获取找回密码验证码
                //获取手机号码
                String forgetPswName = rlForgetPswName.getText().toString().trim();
                ForgetPasswordVerificationCodeTask forgetPasswordVerificationCodeTask = new ForgetPasswordVerificationCodeTask();
                ResultInfo<ForgetPasswordVerificationCodeBeen> requestFPV = forgetPasswordVerificationCodeTask.request(forgetPswName);
                resultMsg.obj = requestFPV;
                mUiHandler.sendMessage(resultMsg);
                break;
            case HANDLE_SMS_LOGIN:// 短信登录
                String smsName = rlSmsLoginName.getText().toString().trim();
                userName = smsName;//保存用户名
                String smsCode = rlSmsLoginVerificationPhone.getText().toString().trim();
                SmsLoginTask smsLoginTask = new SmsLoginTask();
                ResultInfo<SmsLoginBeen> requestSMS = smsLoginTask.request(smsName, smsCode);
                resultMsg.obj = requestSMS;
                mUiHandler.sendMessage(resultMsg);
                break;
            case HANDLE_SMS_REGISTER:// 短信注册
                String smsRName = rlSmsRegisterName.getText().toString().trim();
                String smsRCode = rlSmsRegisterVerificationPhone.getText().toString().trim();
                String smsRpsw = rlSmsRegisterSecondPsw.getText().toString().trim();
                SmsRegisterTask smsRegisterTask = new SmsRegisterTask();
                ResultInfo<SMSRegisterBeen> requestR = smsRegisterTask.request(smsRName, smsRpsw, smsRCode);
                resultMsg.obj = requestR;
                mUiHandler.sendMessage(resultMsg);
                break;
            case HANDLE_FORGET_PASSWORD:// 找回密码
                String fpName = rlForgetPswName.getText().toString().trim();
                String fpCode = rlForgetPswVerificationPhone.getText().toString().trim();
                String fppsw = rlForgetPswSecondPsw.getText().toString().trim();
                ForgetPasswordTask forgetPasswordTask = new ForgetPasswordTask();
                ResultInfo<ForgetPasswordBeen> requestFP = forgetPasswordTask.request(fpName, fppsw, fpCode);
                resultMsg.obj = requestFP;
                mUiHandler.sendMessage(resultMsg);
                break;
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_PASSWORD_LOGIN: // 密码登录
                //放开登录按钮
                rlBtLogin.setClickable(true);
                rlBtLogin.setText("登录");

                ResultInfo<PasswordLoginBeen> result = (ResultInfo<PasswordLoginBeen>) msg.obj;
                if (result.Success) {
                    if (result.Data != null) {
                        PasswordLoginBeen passwordLoginBeen = result.Data;
                        if (passwordLoginBeen.getSuccess().equals("true")) {
                            showToast(getString(R.string.rl_login_success));
                            //登录并且退出
                            saveUserInfo(userName);
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

                            switch (whoShowInView) {
                                case Login_sms: //短信登录页面
                                    is_verification_code_cloud_return = true; //登录 == 获取到了云端验证码
                                    break;
                                case register_sms: //短信注册页面
                                    is_Register_verification_code_cloud_return = true; //注册 == 获取到了云端验证码
                                    break;
                                case forget_password: //忘记密码页面
                                    break;
                            }
                            showToast("验证码发送成功!");
                        }
                    } else {
                        showToast(resultcode.Message);
                    }
                } else {
                    showToast(resultcode.Message);
                }
                break;
            case HANDLE_FORGET_PASSWORD_VERIFICATION_CODE: // 获取找回密码验证码
                ResultInfo<ForgetPasswordVerificationCodeBeen> fpresultcode = (ResultInfo<ForgetPasswordVerificationCodeBeen>) msg.obj;
                if (fpresultcode.Success) {
                    if (fpresultcode.Data != null) {
                        ForgetPasswordVerificationCodeBeen verificationCodeBeen = fpresultcode.Data;
                        if (verificationCodeBeen.getSuccess().equals("true")) {

                            showToast("验证码发送成功!");
                        }
                    } else {
                        showToast(fpresultcode.Message);
                    }
                } else {
                    showToast(fpresultcode.Message);
                }
                break;
            case HANDLE_SMS_LOGIN: // 短信登录
                //放开登录按钮
                rlBtLogin.setClickable(true);
                rlBtLogin.setText("登录");

                ResultInfo<SmsLoginBeen> resultSMS = (ResultInfo<SmsLoginBeen>) msg.obj;
                if (resultSMS.Success) {
                    if (resultSMS.Data != null) {
                        SmsLoginBeen smsLoginBeen = resultSMS.Data;
                        if (smsLoginBeen.getSuccess().equals("true")) {
                            showToast("登录成功!");
                            //登录并且退出
                            saveUserInfo(userName);
                        } else {
                            showToast(resultSMS.Message);
                        }
                    } else {
                        showToast(resultSMS.Message);
                    }
                } else {
                    showToast(resultSMS.Message);
                }
                break;
            case HANDLE_SMS_REGISTER: // 短信注册
                //放开短信注册按钮
                rlSmsRegisterButton.setClickable(true);
                rlSmsRegisterButton.setText("注册");

                ResultInfo<SMSRegisterBeen> resultR = (ResultInfo<SMSRegisterBeen>) msg.obj;
                if (resultR.Success) {
                    if (resultR.Data != null) {
                        SMSRegisterBeen smsRegisterBeen = resultR.Data;
                        if (smsRegisterBeen.getSuccess().equals("true")) {
                            showToast("注册成功!");
                        } else {
                            showToast(resultR.Message);
                        }
                    } else {
                        showToast(resultR.Message);
                    }
                } else {
                    showToast(resultR.Message);
                }
                break;
            case HANDLE_FORGET_PASSWORD: // 忘记密码
                //放开忘记密码按钮
                rlForgetPswButton.setClickable(true);
                rlForgetPswButton.setText("保存");
                ResultInfo<ForgetPasswordBeen> resultFP = (ResultInfo<ForgetPasswordBeen>) msg.obj;
                if (resultFP.Success) {
                    if (resultFP.Data != null) {
                        ForgetPasswordBeen smsRegisterBeen = resultFP.Data;
                        if (smsRegisterBeen.getSuccess().equals("true")) {
                            showToast("修改密码成功");
                        } else {
                            showToast(resultFP.Message);
                        }
                    } else {
                        showToast(resultFP.Message);
                    }
                } else {
                    showToast(resultFP.Message);
                }
                break;
            case HANDLE_COUNT_DOWN_TIMER: // 倒计时
                switch (whoShowInView) {
                    case Login_sms: //短信登录页面
                        changeTimer(rlSmsLoginVerificationPhoneGet);
                        break;
                    case register_sms: //短信注册页面
                        changeTimer(rlSmsRegisterVerificationPhoneGet);
                        break;
                    case forget_password: //忘记密码页面
                        changeTimer(rlForgetPswVerificationPhoneGet);
                        break;
                }
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
     * 改变当前显示的view
     */
    private void changeView() {
        //隐藏键盘
        KeyBoardUtil.hideKeyboard(this);

        //开始先隐藏所有页面
        activityRegisterAndLoginAllLogin.setVisibility(View.GONE); //登录页面总页面
        activityRegisterAndLoginPasswordLogin.setVisibility(View.GONE); //密码登录页面
        activityRegisterAndLoginSmsLogin.setVisibility(View.GONE); //短信登录页面

        activityRegisterAndLoginSmsRegister.setVisibility(View.GONE); //短信注册页面
        activityRegisterAndLoginForgetPsw.setVisibility(View.GONE); //忘记密码页面

        rlPassLogin.setVisibility(View.GONE); //密码登录字下面的横线
        rlSmsLogin.setVisibility(View.GONE); //短信登录字下面的横线

        rlTitle.setText(getString(R.string.rl_title_login)); //设置标题为登录

        //根据不同的情况显示不同的View
        switch (whoShowInView) {
            case Login_password: //密码登录页面
                activityRegisterAndLoginAllLogin.setVisibility(View.VISIBLE); //登录页面总页面
                activityRegisterAndLoginPasswordLogin.setVisibility(View.VISIBLE); //密码登录页面
                rlPassLogin.setVisibility(View.VISIBLE); //密码登录字下面的横线
                break;
            case Login_sms: //短信登录页面
                activityRegisterAndLoginAllLogin.setVisibility(View.VISIBLE); //登录页面总页面
                activityRegisterAndLoginSmsLogin.setVisibility(View.VISIBLE); //短信登录页面
                rlSmsLogin.setVisibility(View.VISIBLE); //短信登录字下面的横线

                rlSmsLoginRelativeLayoutPhonenumber.setVisibility(View.GONE);  //短信登录--获取手机验证码
                createRandomNumber(rlSmsLoginVerificationShow); //短信登录 == 生成本地验证码

                rlSmsLoginVerificationPhone.setText(""); //短信登录 -- 清除 -- 本机验证码
                break;
            case register_sms: //短信注册页面
                activityRegisterAndLoginSmsRegister.setVisibility(View.VISIBLE); //短信注册页面
                rlTitle.setText(getString(R.string.rl_title_register)); //设置标题为注册

                rlSmsRegisterVerificationPhone.setText("");//清除验证码
                rlSmsRegisterFirstPsw.setText(""); // 清除密码
                rlSmsRegisterSecondPsw.setText(""); //清除密码2
                createRandomNumber(rlSmsRegisterRegisterShow); //短信注册页面 == 生成本地验证码
                rlSmsRegisterLinearLayout.setVisibility(View.GONE); //短信注册页面 -- 验证手机号码通过才展示
                break;
            case forget_password: //忘记密码页面
                activityRegisterAndLoginForgetPsw.setVisibility(View.VISIBLE); //忘记密码页面
                rlTitle.setText(getString(R.string.rl_title_find_psw)); //设置标题为找回密码

                createRandomNumber(rlForgetPswVerificationCode); //短信注册页面 == 生成本地验证码

                rlForgetPswVerificationPhone.setText("");  //清除验证码
                rlSmsRegisterFirstPsw.setText("");  // 清除密码
                rlSmsRegisterSecondPsw.setText("");  //清除密码2
                rlForgetPswsLinearLayout.setVisibility(View.GONE);  //忘记密码页面 -- 验证手机号码通过才展示
                break;

            default:
                break;
        }
    }


    //基础页面
    private ImageView buttonBack;
    private TextView rlTitle;
    private LinearLayout activityRegisterAndLoginAllLogin;
    private RelativeLayout rlPassLoginRelativeLayout;
    private View rlPassLogin;
    private RelativeLayout rlSmsLoginRelativeLayout;
    private View rlSmsLogin;
    private View activityRegisterAndLoginPasswordLogin;
    private View activityRegisterAndLoginSmsLogin;
    private Button rlBtLogin;
    private TextView rlNewRegister;
    private TextView rlForgetPassword;
    private View activityRegisterAndLoginSmsRegister;
    private View activityRegisterAndLoginForgetPsw;

    //忘记密码
    private EditText rlForgetPswName;
    private ImageView rlSmsForgetPswNameClean;
    private EditText rlForgetPswVerification;
    private TextView rlForgetPswVerificationCode;
    private ImageView rlForgetPswVerificationClean;
    private LinearLayout rlForgetPswsLinearLayout;
    private EditText rlForgetPswVerificationPhone;
    private TextView rlForgetPswVerificationPhoneGet;
    private ImageView rlForgetPswVerificationPhoneClean;
    private EditText rlForgetPswFirstPsw;
    private ImageView rlForgetPswFirstPswClean;
    private EditText rlForgetPswSecondPsw;
    private ImageView rlForgetPswSecondPswClean;
    private Button rlForgetPswButton;

    //密码登录
    private EditText rlPassName;
    private ImageView rlPassNameClean;
    private EditText rlPassPassword;
    private ImageView rlPassPasswordClean;

    //短信登录
    private EditText rlSmsLoginName;
    private ImageView rlSmsLoginNameClean;
    private EditText rlSmsLoginVerification;
    private TextView rlSmsLoginVerificationShow;
    private ImageView rlSmsLoginVerificationClean;
    private RelativeLayout rlSmsLoginRelativeLayoutPhonenumber;
    private EditText rlSmsLoginVerificationPhone;
    private TextView rlSmsLoginVerificationPhoneGet;
    private ImageView rlSmsLoginVerificationPhoneClean;

    //短信注册
    private EditText rlSmsRegisterName;
    private ImageView rlSmsRegisterNameClean;
    private EditText rlSmsRegisterVerification;
    private TextView rlSmsRegisterRegisterShow;
    private ImageView rlSmsRegisterVerificationClean;
    private LinearLayout rlSmsRegisterLinearLayout;
    private EditText rlSmsRegisterVerificationPhone;
    private TextView rlSmsRegisterVerificationPhoneGet;
    private ImageView rlSmsRegisterVerificationPhoneClean;
    private EditText rlSmsRegisterFirstPsw;
    private ImageView rlSmsRegisterFirstPswClean;
    private EditText rlSmsRegisterSecondPsw;
    private ImageView rlSmsRegisterSecondPswClean;
    private Button rlSmsRegisterButton;

    private ImageButton navBtnBack;
    private TextView navTxtTitle;
    private TextView navTxtOp;

    /**
     * 初始化界面
     */
    private void assignViews() {

        //头部
        navBtnBack = (ImageButton) findViewById(R.id.nav_btn_back);
        rlTitle = (TextView) findViewById(R.id.nav_txt_title);
        navTxtOp = (TextView) findViewById(R.id.nav_txt_op);

        //基础页面
        activityRegisterAndLoginAllLogin = (LinearLayout) findViewById(R.id.activity_register_and_login_all_login);
        rlPassLoginRelativeLayout = (RelativeLayout) findViewById(R.id.rl_pass_login_relativeLayout);
        rlPassLogin = findViewById(R.id.rl_pass_login);
        rlSmsLoginRelativeLayout = (RelativeLayout) findViewById(R.id.rl_sms_login_relativeLayout);
        rlSmsLogin = findViewById(R.id.rl_sms_login);
        activityRegisterAndLoginPasswordLogin = (View) findViewById(R.id.activity_register_and_login_password_login);
        activityRegisterAndLoginSmsLogin = (View) findViewById(R.id.activity_register_and_login_sms_login);
        rlBtLogin = (Button) findViewById(R.id.rl_bt_login);
        rlNewRegister = (TextView) findViewById(R.id.rl_new_register);
        rlForgetPassword = (TextView) findViewById(R.id.rl_forget_password);
        activityRegisterAndLoginSmsRegister = (View) findViewById(R.id.activity_register_and_login_sms_register);
        activityRegisterAndLoginForgetPsw = (View) findViewById(R.id.activity_register_and_login_forget_psw);

        //忘记密码
        rlForgetPswName = (EditText) findViewById(R.id.rl_forget_psw_name);
        rlSmsForgetPswNameClean = (ImageView) findViewById(R.id.rl_sms_forget_psw_name_clean);
        rlForgetPswVerification = (EditText) findViewById(R.id.rl_forget_psw_verification);
        rlForgetPswVerificationCode = (TextView) findViewById(R.id.rl_forget_psw_verification_code);
        rlForgetPswVerificationClean = (ImageView) findViewById(R.id.rl_forget_psw_verification_clean);
        rlForgetPswsLinearLayout = (LinearLayout) findViewById(R.id.rl_forget_psws_linearLayout);
        rlForgetPswVerificationPhone = (EditText) findViewById(R.id.rl_forget_psw_verification_phone);
        rlForgetPswVerificationPhoneGet = (TextView) findViewById(R.id.rl_forget_psw_verification_phone_get);
        rlForgetPswVerificationPhoneClean = (ImageView) findViewById(R.id.rl_forget_psw_verification_phone_clean);
        rlForgetPswFirstPsw = (EditText) findViewById(R.id.rl_forget_psw_first_psw);
        rlForgetPswFirstPswClean = (ImageView) findViewById(R.id.rl_forget_psw_first_psw_clean);
        rlForgetPswSecondPsw = (EditText) findViewById(R.id.rl_forget_psw_second_psw);
        rlForgetPswSecondPswClean = (ImageView) findViewById(R.id.rl_forget_psw_second_psw_clean);
        rlForgetPswButton = (Button) findViewById(R.id.rl_forget_psw_button);

        //密码登录
        rlPassName = (EditText) findViewById(R.id.rl_pass_name);
        rlPassNameClean = (ImageView) findViewById(R.id.rl_pass_name_clean);
        rlPassPassword = (EditText) findViewById(R.id.rl_pass_password);
        rlPassPasswordClean = (ImageView) findViewById(R.id.rl_pass_password_clean);

        //短信登录
        rlSmsLoginName = (EditText) findViewById(R.id.rl_sms_login_name);
        rlSmsLoginNameClean = (ImageView) findViewById(R.id.rl_sms_login_name_clean);
        rlSmsLoginVerification = (EditText) findViewById(R.id.rl_sms_login_verification);
        rlSmsLoginVerificationShow = (TextView) findViewById(R.id.rl_sms_login_verification_show);
        rlSmsLoginVerificationClean = (ImageView) findViewById(R.id.rl_sms_login_verification_clean);
        rlSmsLoginRelativeLayoutPhonenumber = (RelativeLayout) findViewById(R.id.rl_sms_login_relativeLayout_phonenumber);
        rlSmsLoginVerificationPhone = (EditText) findViewById(R.id.rl_sms_login_verification_phone);
        rlSmsLoginVerificationPhoneGet = (TextView) findViewById(R.id.rl_sms_login_verification_phone_get);
        rlSmsLoginVerificationPhoneClean = (ImageView) findViewById(R.id.rl_sms_login_verification_phone_clean);

        //短信注册
        rlSmsRegisterName = (EditText) findViewById(R.id.rl_sms_register_name);
        rlSmsRegisterNameClean = (ImageView) findViewById(R.id.rl_sms_register_name_clean);
        rlSmsRegisterVerification = (EditText) findViewById(R.id.rl_sms_register_verification);
        rlSmsRegisterRegisterShow = (TextView) findViewById(R.id.rl_sms_register_register_show);
        rlSmsRegisterVerificationClean = (ImageView) findViewById(R.id.rl_sms_register_verification_clean);
        rlSmsRegisterLinearLayout = (LinearLayout) findViewById(R.id.rl_sms_register_linearLayout);
        rlSmsRegisterVerificationPhone = (EditText) findViewById(R.id.rl_sms_register_verification_phone);
        rlSmsRegisterVerificationPhoneGet = (TextView) findViewById(R.id.rl_sms_register_verification_phone_get);
        rlSmsRegisterVerificationPhoneClean = (ImageView) findViewById(R.id.rl_sms_register_verification_phone_clean);
        rlSmsRegisterFirstPsw = (EditText) findViewById(R.id.rl_sms_register_first_psw);
        rlSmsRegisterFirstPswClean = (ImageView) findViewById(R.id.rl_sms_register_first_psw_clean);
        rlSmsRegisterSecondPsw = (EditText) findViewById(R.id.rl_sms_register_second_psw);
        rlSmsRegisterSecondPswClean = (ImageView) findViewById(R.id.rl_sms_register_second_psw_clean);
        rlSmsRegisterButton = (Button) findViewById(R.id.rl_sms_register_button);

        //基础页面监听
        onClickListenerBase();

        //密码登录监听
        onClickListenerPasswordLogin();

        //SMS登录监听
        onClickListenerSMSLogin();

        //SMS注册监听
        onClickListenerSMSRegister();

        //忘记密码的监听
        onClickListenerForgetPassword();

    }

    /**
     * 忘记密码的监听
     */
    private void onClickListenerForgetPassword() {
        //忘记密码的用户名监听
        rlForgetPswName.addTextChangedListener(myForgetPswTextListener);
        //忘记密码的用户名焦点监听
        rlForgetPswName.setOnFocusChangeListener(myForgetPswFocusChangeListener);

        //清除--忘记密码 -- 名字
        rlSmsForgetPswNameClean.setOnClickListener(myOnClickListener);
        //清除--忘记密码 -- 本地验证码
        rlForgetPswVerificationClean.setOnClickListener(myOnClickListener);
        //清除--忘记密码 -- 验证码
        rlForgetPswVerificationPhoneClean.setOnClickListener(myOnClickListener);
        //清除--忘记密码 -- 第一次密码
        rlForgetPswFirstPswClean.setOnClickListener(myOnClickListener);
        //清除--忘记密码 -- 第二次密码
        rlForgetPswSecondPswClean.setOnClickListener(myOnClickListener);

        //生成新的本机验证码
        rlForgetPswVerificationCode.setOnClickListener(myOnClickListener);
        //验证本地生成的验证码
        rlForgetPswVerification.addTextChangedListener(myForgetPswVerificationTextListener);

        //点击获取云端验证码
        rlForgetPswVerificationPhoneGet.setOnClickListener(myOnClickListener);

        //点击修改密码
        rlForgetPswButton.setOnClickListener(myOnClickListener);

        //密码是否符合规范
        rlForgetPswFirstPsw.addTextChangedListener(myPasswordTextListener);
        //密码是否符合规范焦点监听
        rlForgetPswFirstPsw.setOnFocusChangeListener(myPasswordFocusChangeListener);

        //忘记密码 -- 云端验证码 -- 是否显示清除按钮
        rlForgetPswVerificationPhone.addTextChangedListener(myForgetPswVerificationTextListener);
        //忘记密码 -- 第二次密码 -- 是否显示清除按钮
        rlForgetPswSecondPsw.addTextChangedListener(myForgetPswSecondPswTextListener);
    }

    /**
     * //SMS注册监听
     */
    private void onClickListenerSMSRegister() {
        //短信注册的用户名监听
        rlSmsRegisterName.addTextChangedListener(mySmsRegisterTextListener);
        //短信注册的用户名焦点监听
        rlSmsRegisterName.setOnFocusChangeListener(mySmsRegisterFocusChangeListener);

        //清除--短信注册 -- 名字
        rlSmsRegisterNameClean.setOnClickListener(myOnClickListener);
        //清除--短信注册 -- 本地验证码
        rlSmsRegisterVerificationClean.setOnClickListener(myOnClickListener);
        //清除--短信注册 -- 验证码
        rlSmsRegisterVerificationPhoneClean.setOnClickListener(myOnClickListener);
        //清除--短信注册 -- 第一次密码
        rlSmsRegisterFirstPswClean.setOnClickListener(myOnClickListener);
        //清除--短信注册 -- 第二次密码
        rlSmsRegisterSecondPswClean.setOnClickListener(myOnClickListener);

        //生成新的本机验证码
        rlSmsRegisterRegisterShow.setOnClickListener(myOnClickListener);
        //验证本地生成的验证码
        rlSmsRegisterVerification.addTextChangedListener(myRegiesterTextListener);

        //点击获取云端验证码
        rlSmsRegisterVerificationPhoneGet.setOnClickListener(myOnClickListener);

        //点击SMS注册
        rlSmsRegisterButton.setOnClickListener(myOnClickListener);

        //密码是否符合规范
        rlSmsRegisterFirstPsw.addTextChangedListener(myPasswordTextListener);
        //密码是否符合规范焦点监听
        rlSmsRegisterFirstPsw.setOnFocusChangeListener(myPasswordFocusChangeListener);

        //短信注册 -- 云端验证码 -- 是否显示清除按钮
        rlSmsRegisterVerificationPhone.addTextChangedListener(mySmsRegisterVerificationTextListener);
        //短信注册 -- 第二次密码 -- 是否显示清除按钮
        rlSmsRegisterSecondPsw.addTextChangedListener(mySmsRegisterSecondPswTextListener);
    }

    /**
     * //SMS登录监听
     */
    private void onClickListenerSMSLogin() {
        //短信登录的用户名监听
        rlSmsLoginName.setOnFocusChangeListener(myOnFocusChangeListener);

        //生成新的本机验证码
        rlSmsLoginVerificationShow.setOnClickListener(myOnClickListener);

        //验证本地生成的验证码
        rlSmsLoginVerification.addTextChangedListener(myLocalTextListener);

        //点击获取云端验证码
        rlSmsLoginVerificationPhoneGet.setOnClickListener(myOnClickListener);

        //-------------------
        //清除--短信登录 -- 名字
        rlSmsLoginNameClean.setOnClickListener(myOnClickListener);
        //清除--短信登录 -- 本地验证码
        rlSmsLoginVerificationClean.setOnClickListener(myOnClickListener);
        //清除--短信登录 -- 验证码
        rlSmsLoginVerificationPhoneClean.setOnClickListener(myOnClickListener);
        //短信登录 -- 名字 -- 是否显示清除按钮
        rlSmsLoginName.addTextChangedListener(mySmsLoginNameTextListener);
        //短信登录 -- 验证码 -- 是否显示清除按钮
        rlSmsLoginVerificationPhone.addTextChangedListener(mySmsLoginVerificationPhoneTextListener);
    }

    /**
     * 密码登录监听
     */
    private void onClickListenerPasswordLogin() {

        //-------------------
        //清除--密码登录 -- 名字
        rlPassNameClean.setOnClickListener(myOnClickListener);
        //清除--密码登录 -- 密码
        rlPassPasswordClean.setOnClickListener(myOnClickListener);
        //密码登录 -- 名字 -- 是否显示清除按钮
        rlPassName.addTextChangedListener(myPassNameTextListener);
        //密码登录 -- 密码 -- 是否显示清除按钮
        rlPassPassword.addTextChangedListener(myPassNameTextListener2);
    }

    /**
     * 监听基础页面
     */
    private void onClickListenerBase() {

        //设置标题
        rlTitle.setText(R.string.rl_title);

        //登录按钮
        rlBtLogin.setOnClickListener(myOnClickListener);

        //新用户注册
        rlNewRegister.setOnClickListener(myOnClickListener);
        //忘记密码
        rlForgetPassword.setOnClickListener(myOnClickListener);

        //返回按钮
        navBtnBack.setOnClickListener(myOnClickListener);

        //密码登录页面
        rlPassLoginRelativeLayout.setOnClickListener(myOnClickListener);

        //短信登录页面
        rlSmsLoginRelativeLayout.setOnClickListener(myOnClickListener);
    }

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_new_register: //新用户注册
                    whoShowInView = register_sms; //改变当前显示的下标
                    changeView();//改变页面
                    break;
                case R.id.rl_forget_password: //忘记密码
                    whoShowInView = forget_password; //改变当前显示的下标
                    changeView();//改变页面
                    break;
                case R.id.rl_bt_login: //登录按钮--密码登录和短信登录页面
                    actionClickLogin();
                    break;
                case R.id.rl_pass_login_relativeLayout: //切换到 -- 密码登录页面
                    whoShowInView = Login_password; //改变当前显示的下标
                    changeView();//改变页面
                    break;

                case R.id.rl_sms_login_relativeLayout: //切换到 -- 短信登录页面
                    whoShowInView = Login_sms;//改变当前显示的下标
                    changeView();//改变页面
                    break;

                case R.id.nav_btn_back: //点击返回
                    //如果在忘记密码 和 注册页面 则返回登录页面
                    if (whoShowInView == forget_password || whoShowInView == register_sms){
                        whoShowInView = Login_password;
                        changeView();//改变页面
                    }else {
                        finish();
                    }
                    break;

                case R.id.rl_sms_login_verification_show: //短信登录 == 生成本地验证码
                    createRandomNumber(rlSmsLoginVerificationShow); //短信登录 == 生成本地验证码
                    rlSmsLoginVerification.setText("");//清除--短信登录 -- 本地验证码
                    break;
                case R.id.rl_sms_login_verification_phone_get: //短信登录 == 生成云端验证码
                    is_verification_code_cloud_return = false; //重置云端验证码获取状态
                    getCloudVerificationCode(rlSmsLoginName, RegisterAndLoginViewModel.VerificationCode_type_login);
                    break;
                case R.id.rl_pass_name_clean:  //清除--密码登录 -- 名字
                    rlPassName.setText("");
                    break;
                case R.id.rl_pass_password_clean:  //清除--密码登录 -- 密码
                    rlPassPassword.setText("");
                    break;
                case R.id.rl_sms_login_name_clean:   //清除--短信登录 -- 名字
                    rlSmsLoginName.setText("");
                    break;
                case R.id.rl_sms_login_verification_clean:   //清除--短信登录 -- 本地验证码
                    rlSmsLoginVerification.setText("");
                    break;
                case R.id.rl_sms_login_verification_phone_clean:  //短信登录 -- 验证码 -- 是否显示清除按钮
                    rlSmsLoginVerificationPhone.setText("");
                    break;

                //-----------------c-----------------------
                case R.id.rl_sms_register_name_clean:  //短信注册 -- 名字 -- 是否显示清除按钮
                    rlSmsRegisterName.setText("");
                    break;
                case R.id.rl_sms_register_verification_clean:  //短信注册 -- 验证码 -- 是否显示清除按钮
                    rlSmsRegisterVerification.setText("");
                    break;
                case R.id.rl_sms_register_verification_phone_clean:  //短信注册 -- 云端验证码 -- 是否显示清除按钮
                    rlSmsRegisterVerificationPhone.setText("");
                    break;
                case R.id.rl_sms_register_first_psw_clean:  //短信注册 -- 1密码 -- 是否显示清除按钮
                    rlSmsRegisterFirstPsw.setText("");
                    break;
                case R.id.rl_sms_register_second_psw_clean:  //短信注册 -- 2密码 -- 是否显示清除按钮
                    rlSmsRegisterSecondPsw.setText("");
                    break;
                case R.id.rl_sms_register_register_show:  //短信注册 == 生成本地验证码
                    createRandomNumber(rlSmsRegisterRegisterShow); //短信注册 == 生成本地验证码
                    rlSmsRegisterVerification.setText("");//清除--短信注册 -- 本地验证码
                    break;
                case R.id.rl_sms_register_verification_phone_get:  //短信注册 == 生成云端验证码
                    is_Register_verification_code_cloud_return = false; //重置云端验证码获取状态
                    getCloudVerificationCode(rlSmsRegisterName, RegisterAndLoginViewModel.VerificationCode_type_register);
                    break;
                case R.id.rl_sms_register_button:  //短信注册 == 点击注册
                    actionClickRegisterSMS();
                    break;

                //----------------忘记密码--------------------
                case R.id.rl_sms_forget_psw_name_clean:  //忘记密码 -- 名字 -- 是否显示清除按钮
                    rlForgetPswName.setText("");
                    break;
                case R.id.rl_forget_psw_verification_clean:  //忘记密码 -- 验证码 -- 是否显示清除按钮
                    rlForgetPswVerification.setText("");
                    break;
                case R.id.rl_forget_psw_verification_phone_clean:  //忘记密码 -- 云端验证码 -- 是否显示清除按钮
                    rlForgetPswVerificationPhone.setText("");
                    break;
                case R.id.rl_forget_psw_first_psw_clean:  //忘记密码 -- 1密码 -- 是否显示清除按钮
                    rlForgetPswFirstPsw.setText("");
                    break;
                case R.id.rl_forget_psw_second_psw_clean:  //忘记密码 -- 2密码 -- 是否显示清除按钮
                    rlForgetPswSecondPsw.setText("");
                    break;
                case R.id.rl_forget_psw_verification_code:  //忘记密码 == 生成本地验证码
                    createRandomNumber(rlForgetPswVerificationCode); //忘记密码 == 生成本地验证码
                    rlForgetPswVerification.setText("");//清除--忘记密码 -- 本地验证码
                    break;
                case R.id.rl_forget_psw_verification_phone_get:  //忘记密码 == 生成云端验证码
                    is_forget_psw_verification_code_cloud_return = false; //重置云端验证码获取状态
                    getCloudVerificationCode(rlForgetPswName, RegisterAndLoginViewModel.VerificationCode_type_login);
                    break;
                case R.id.rl_forget_psw_button:  //忘记密码 == 点击修改
                    actionClickForgetPassword();
                    break;

                default:
                    break;
            }
        }

    };

    /**
     * 点击进行注册请求
     */
    private void actionClickRegisterSMS() {

        if (isEdittextNotNull(rlSmsRegisterName)) {
            if (isEdittextNotNull(rlSmsRegisterVerificationPhone)) {
                if (isEdittextNotNull(rlSmsRegisterFirstPsw)) {
                    if (isRegistePasswordRight) {
                        if (isEdittextNotNull(rlSmsRegisterSecondPsw)) {
                            if (rlSmsRegisterFirstPsw.getText().toString().trim().equals(rlSmsRegisterSecondPsw.getText().toString().trim())) {
                                //屏蔽登录按钮
                                rlSmsRegisterButton.setClickable(false);
                                rlSmsRegisterButton.setText("注册中...");

                                //请求登录
                                Message get_detail = new Message();
                                get_detail.what = HANDLE_SMS_REGISTER;
                                mBackgroundHandler.sendMessage(get_detail);
                            } else {
                                showToast("两次输入的密码不一致");
                            }
                        } else {
                            showToast("请再次输入密码");
                        }
                    } else {
                        showToast(getString(R.string.rl_password_is_right));
                    }
                } else {
                    showToast("请输入密码");
                }
            } else {
                showToast("请输入手机验证码");
            }
        } else {
            showToast(getString(R.string.rl_username_no_null));
        }

    }

    /**
     * 监听短信登录电话号码
     */
    private TextWatcher mySmsRegisterVerificationTextListener = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            setVisibility(rlSmsRegisterVerificationPhoneClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    /**
     * 监听是否显示2次密码的清除按钮
     */
    private TextWatcher mySmsRegisterSecondPswTextListener = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            setVisibility(rlSmsRegisterSecondPswClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * 监听密码是否符合规范
     */
    private TextWatcher myPasswordTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            if (floors != null) {
                if (floors.length() >= 8) {
                    isRegistePasswordRight = CheckPassword(floors);
                }
            }
            setVisibility(rlForgetPswFirstPswClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    /**
     * 监听本机验证码
     */
    private TextWatcher myRegiesterTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            if (floors != null && floors.length() >= 4) {
                if (verification_code_local != null && !verification_code_local.isEmpty()) {
                    //如果验证码正确 显示发送手机验证码的
                    if (verification_code_local.equals(floors) && isRegistephoneNumberRight) {
                        rlSmsRegisterLinearLayout.setVisibility(View.VISIBLE);  //短信登录--获取手机验证码
                    } else {
                        rlSmsRegisterVerification.setText("");
                        if (verification_code_local.equals(floors) &&!isRegistephoneNumberRight){
                            showToast("请检查手机号码");
                        }
                    }
                }
            }
            setVisibility(rlSmsRegisterVerificationClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    /**
     * 监听密码焦点获取情况,失去焦点就判断手机号码是否正确
     */
    private View.OnFocusChangeListener myPasswordFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //当失去焦点时
                if (isEdittextNotNull((EditText) v) && !isRegistePasswordRight) {
                    showToastTop(getString(R.string.rl_password_is_right));
                }
            }
        }
    };

    /**
     * 监听手机号码输入框焦点获取情况,失去焦点就判断手机号码是否正确
     */
    private View.OnFocusChangeListener mySmsRegisterFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //当失去焦点时
                if (isEdittextNotNull((EditText) v) && !isRegistephoneNumberRight) {
                    showToastTop(getString(R.string.rl_pleaes_enter_right_number));
                }
            }
        }
    };


    /**
     * 监听短信注册电话号码
     */
    private TextWatcher mySmsRegisterTextListener = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            if (floors != null && floors.length() >= 0) {
                isRegistephoneNumberRight = CheckMobilePhoneNumber(floors);
            }
            setVisibility(rlSmsRegisterNameClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    /**
     * 获取云端的验证码
     *
     * @param editText //验证手机号码 .. 由于服务器不靠谱
     * @param Type
     */
    private void getCloudVerificationCode(EditText editText, int Type) {
        if (isEdittextNotNull(editText)) {
            if (CheckMobilePhoneNumber(editText.getText().toString().trim())) {
                //开启倒计时
                countDownTimer();
                //请求验证码
                if (whoShowInView == forget_password) { //找回密码的接口不一样
                    Message get_detail = new Message();
                    get_detail.what = HANDLE_FORGET_PASSWORD_VERIFICATION_CODE;
                    mBackgroundHandler.sendMessage(get_detail);
                } else {
                    Message get_detail = new Message();
                    get_detail.what = HANDLE_GET_VERIFICATION_CODE;
                    Bundle data = new Bundle();
                    data.putInt(HANDLE_GET_VERIFICATION_CODE_TYPE, Type);
                    data.putString(HANDLE_GET_VERIFICATION_CODE_PHONE, editText.getText().toString().trim());
                    get_detail.setData(data);
                    mBackgroundHandler.sendMessage(get_detail);
                }
            }
        }
    }

    /**
     * 监听短信登录手机验证码
     */
    private TextWatcher mySmsLoginVerificationPhoneTextListener = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            setVisibility(rlSmsLoginVerificationPhoneClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * 监听短信登录电话号码
     */
    private TextWatcher mySmsLoginNameTextListener = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            if (floors != null && floors.length() >= 0) {
                isphoneNumberRight = CheckMobilePhoneNumber(floors);
            }
            setVisibility(rlSmsLoginNameClean, (floors != null && floors.length() > 0));
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
            setVisibility(rlPassPasswordClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    /**
     * 监听密码登录的名字
     */
    private TextWatcher myPassNameTextListener = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            setVisibility(rlPassNameClean, (floors != null && floors.length() > 0));
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

    /**
     * 监听本机验证码
     */
    private TextWatcher myLocalTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            if (floors != null && floors.length() >= 4) {
                if (verification_code_local != null && !verification_code_local.isEmpty()) {
                    //如果验证码正确 显示发送手机验证码的
                    if (verification_code_local.equals(floors) && isphoneNumberRight) {
                        rlSmsLoginRelativeLayoutPhonenumber.setVisibility(View.VISIBLE);  //短信登录--获取手机验证码
                    } else {
                        rlSmsLoginVerification.setText("");
                        if (verification_code_local.equals(floors) &&!isphoneNumberRight){
                            showToast("请检查手机号码");
                        }
                    }
                }
            }
            setVisibility(rlSmsLoginVerificationClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


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
     * 检查密码是否符合规矩
     *
     * @param number
     * @return
     */
    private boolean CheckPassword(String number) {
        //正则表达式
        Pattern p = Pattern.compile(RegisterAndLoginViewModel.passward_expression);
        Matcher m = p.matcher(number);
        return m.find();
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

    //点击登录按钮
    private void actionClickLogin() {

        //根据不同的情况做不同的处理
        switch (whoShowInView) {
            case Login_password: //密码登录页面
                passwordLogin();

                break;
            case Login_sms: //短信登录页面
                smsLogin(rlSmsLoginName, rlSmsLoginVerificationPhone);

                break;
            default:
                break;
        }

    }

    /**
     * 短信登录
     *
     * @param phone //号码
     * @param vcode //验证码框
     */
    private void smsLogin(EditText phone, EditText vcode) {
        //不能为空
        if (isEdittextNotNull(phone) && isEdittextNotNull(vcode)) {
            //号码符合规则
            if (CheckMobilePhoneNumber(phone.getText().toString().trim())) {

                //屏蔽登录按钮
                rlBtLogin.setClickable(false);
                rlBtLogin.setText("登录中...");

                //请求登录
                Message get_detail = new Message();
                get_detail.what = HANDLE_SMS_LOGIN;
                mBackgroundHandler.sendMessage(get_detail);

            }
        }
    }

    /**
     * 密码登录
     */
    private void passwordLogin() {
        if (isEdittextNotNull(rlPassName)) {
            if (isEdittextNotNull(rlPassPassword)) {

                //屏蔽登录按钮
                rlBtLogin.setClickable(false);
                rlBtLogin.setText("登录中...");

                //请求登录
                Message get_detail = new Message();
                get_detail.what = HANDLE_GET_PASSWORD_LOGIN;
                mBackgroundHandler.sendMessage(get_detail);

            } else {
                showToast(getString(R.string.rl_psw_no_null));
            }
        } else {
            showToast(getString(R.string.rl_username_no_null));
        }

    }

    /**
     * 判断输入框是否为空
     *
     * @param editText //输入框
     * @return
     */
    private boolean isEdittextNotNull(EditText editText) {
        return (editText.getText() != null && !editText.getText().toString().trim().isEmpty());
    }

    @Override
    public void showToast(String msg) {
        /**
         * 覆盖掉父类的toast 太长了
         * 使用短toast
         */
        Toast.makeText(RegisterAndLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
//        super.showToast(msg);
    }

    /**
     * 显示在顶部的Toast
     *
     * @param msg
     */
    public void showToastTop(String msg) {
        Toast toast = Toast.makeText(RegisterAndLoginActivity.this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    /**
     * 创建一个随机验证码
     *
     * @param textview //验证码显示的textview
     */
    private void createRandomNumber(TextView textview) {
        String number = "";
        for (int i = 0; i < RegisterAndLoginViewModel.random_digit; i++) {
            int item = (int) (Math.random() * 10);
            number = number + item;
        }
        verification_code_local = number;
        textview.setText(number);
    }

    /**
     * 定时器
     * 验证码倒数60秒
     */
    private void countDownTimer() {

        cancelTimer();
        count_down_timer_int = 60;
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
     * 监听忘记密码电话号码
     */
    private TextWatcher myForgetPswTextListener = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            if (floors != null && floors.length() >= 0) {
                isForgetPhoneNumberRight = CheckMobilePhoneNumber(floors);
            }
            setVisibility(rlSmsForgetPswNameClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * 监听手机号码输入框焦点获取情况,失去焦点就判断手机号码是否正确
     */
    private View.OnFocusChangeListener myForgetPswFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //当失去焦点时
                if (isEdittextNotNull((EditText) v) && !isForgetPhoneNumberRight) {
                    showToastTop(getString(R.string.rl_pleaes_enter_right_number));
                }
            }
        }
    };


    /**
     * 忘记密码
     * 监听本机验证码
     */
    private TextWatcher myForgetPswVerificationTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            if (floors != null && floors.length() >= 4) {
                if (verification_code_local != null && !verification_code_local.isEmpty()) {
                    //如果验证码正确 显示发送手机验证码的
                    if (verification_code_local.equals(floors) && isForgetPhoneNumberRight) {
                        rlForgetPswsLinearLayout.setVisibility(View.VISIBLE);  //忘记密码的获取手机验证码页面
                    } else {
                        rlForgetPswVerification.setText("");
                        if (verification_code_local.equals(floors) &&!isForgetPhoneNumberRight){
                            showToast("请检查手机号码");
                        }
                    }
                }
            }
            setVisibility(rlForgetPswVerificationClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * 忘记密码
     * 监听是否显示2次密码的清除按钮
     */
    private TextWatcher myForgetPswSecondPswTextListener = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            setVisibility(rlForgetPswSecondPswClean, (floors != null && floors.length() > 0));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * 点击进行忘记密码
     */
    private void actionClickForgetPassword() {

        if (isEdittextNotNull(rlForgetPswName)) {
            if (isEdittextNotNull(rlForgetPswVerificationPhone)) {
                if (isEdittextNotNull(rlForgetPswFirstPsw)) {
                    if (isRegistePasswordRight) {
                        if (isEdittextNotNull(rlForgetPswSecondPsw)) {
                            if (rlForgetPswFirstPsw.getText().toString().trim().equals(rlForgetPswSecondPsw.getText().toString().trim())) {
                                //屏蔽登录按钮
                                rlForgetPswButton.setClickable(false);
                                rlForgetPswButton.setText("修改中...");

                                //请求登录
                                Message get_detail = new Message();
                                get_detail.what = HANDLE_FORGET_PASSWORD;
                                mBackgroundHandler.sendMessage(get_detail);
                            } else {
                                showToast("两次输入的密码不一致");
                            }
                        } else {
                            showToast("请再次输入密码");
                        }
                    } else {
                        showToast(getString(R.string.rl_password_is_right));
                    }
                } else {
                    showToast("请输入密码");
                }
            } else {
                showToast("请输入手机验证码");
            }
        } else {
            showToast(getString(R.string.rl_username_no_null));
        }

    }


    /**
     * 覆盖返回键
     */
    @Override
    public void onBackPressed() {
        //如果在忘记密码 和 注册页面 则返回登录页面
        if (whoShowInView == forget_password || whoShowInView == register_sms){
            whoShowInView = Login_password;
            changeView();//改变页面
        }else {
            super.onBackPressed();
        }
    }
}
