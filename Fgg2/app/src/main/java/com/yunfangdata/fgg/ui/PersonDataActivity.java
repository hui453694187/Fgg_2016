package com.yunfangdata.fgg.ui;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.view.WheelTime;
import com.yunfang.framework.utils.ToastUtil;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.logic.AppHeader;
import com.yunfangdata.fgg.logic.PersonOperator;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.UserInfo;
import com.yunfangdata.fgg.viewmodel.PersonDataViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 贺隽 on 2015/12/14.
 * 个人中心-个人资源
 */
public class PersonDataActivity extends NewBaseActivity {

    private PersonDataViewModel viewModel=new PersonDataViewModel();

    /**
     * 主菜单的广播
     */
    public AppHeader appHeader;

    /**
     * 获取数据
     */
    private final int HANDLE_GET_DATA = 100;

    /**
     *  保存数据
     */
    private final int HANDLE_SAVE = 110;

    /**
     * 保存
     */
    private Button person_data_btn_confirm;

    /**
     * 用户名
     */
    private EditText person_data_txt_user_name;

    /**
     *姓名
     */
    private EditText person_data_txt_real_name;

    /**
     * 性别-男 按钮
     */
    private TextView person_data_btn_men;

    /**
     * 性别-女 按钮
     */
    private TextView person_data_btn_women;
    /***
     * 用户生日
     */
    private EditText birthday_edt;
    /***
     * 用户职业
     */
    private EditText profession_edt;
    /***
     * 日期选择器
     */
    TimePickerView birthdayPicker = null;

    /***
     * 职业选择器
     */
    OptionsPickerView professionPicker = null;

    /**
     * 用户信息
     */
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_data);
        assignViews();
        doWork("加载中....", HANDLE_GET_DATA, "");
        initPicker();
    }

    private void initPicker() {
        birthdayPicker = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        WheelTime.setSTART_YEAR(1920);
        birthdayPicker.setTime(new Date());
        birthdayPicker.setCancelable(true);
        //时间选择后回调
        birthdayPicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                birthday_edt.setText(getTime(date));
            }
        });

        professionPicker=new OptionsPickerView(this);

        //设置数据
        professionPicker.setPicker(viewModel.getProfession());
        //设置是否循环滚动
        professionPicker.setCyclic(false);
        //设置默认选中的项目
        professionPicker.setSelectOptions(0);

        professionPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = viewModel.getProfession().get(options1);
                //设置数据 供提交
                profession_edt.setText(tx);
            }
        });

    }


    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    /**
     * 初始化UI
     */
    private void assignViews() {
        appHeader = new AppHeader(this, R.id.nav_title);

        person_data_btn_confirm =$(R.id.person_data_btn_confirm);
        person_data_txt_user_name =$(R.id.person_data_txt_user_name);
        person_data_txt_real_name =$(R.id.person_data_txt_real_name);
        person_data_btn_men = $(R.id.person_data_btn_men);
        person_data_btn_women =$(R.id.person_data_btn_women);
        birthday_edt =$(R.id.birthday_edt);
        profession_edt =$(R.id.profession_edt);

        Bundle bundle = this.getIntent().getExtras();
        //设置显示信息
        appHeader.setTitle(bundle.getString("title"));

        //设置点击事件
        person_data_btn_confirm.setOnClickListener(clickListener);
        person_data_btn_men.setOnClickListener(clickListener);
        person_data_btn_women.setOnClickListener(clickListener);
        birthday_edt.setOnClickListener(clickListener);
        profession_edt.setOnClickListener(clickListener);

        if(userInfo==null){
            userInfo = new UserInfo();
        }
        userInfo.setUserName(FggApplication.getUserInfo().getUserName());
        //用户账号
        person_data_txt_user_name.setText(userInfo.getUserName());
    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_DATA:
                resultMsg.obj=PersonOperator.getUserInfo();
                break;
            case HANDLE_SAVE:
                resultMsg.obj=PersonOperator.saveUserInfo(userInfo);
                break;
        }
        mUiHandler.sendMessage(resultMsg);
    }

    private void setGenderClick(boolean isMan){
        if(isMan){
            person_data_btn_men.setBackgroundResource(R.color.title_blue);
            person_data_btn_women.setBackgroundResource(R.drawable.juxing_yuanjiao_blue_ee);
            userInfo.setGender("男");
        }else{
            person_data_btn_men.setBackgroundResource(R.drawable.juxing_yuanjiao_blue_ee);
            person_data_btn_women.setBackgroundResource(R.color.title_blue);
            userInfo.setGender("女");
        }
    }

    @Override
    protected void handleUiMessage(Message msg) {
        String message = "";
        super.handleUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_DATA: {
                DataResult<UserInfo> userResult=(DataResult<UserInfo>)msg.obj;
                UserInfo user;
                if(userResult.getSuccess()){
                    user=userResult.getResultData();
                    userInfo.setGender(user.getGender());
                    userInfo.setBirthday(user.getBirthday());
                    userInfo.setEmail(user.getEmail());
                    userInfo.setRealName(user.getRealName());
                    userInfo.setOccupation(user.getOccupation());
                }
                person_data_txt_real_name.setText(userInfo.getRealName());
                boolean isMan="男".equals(userInfo.getGender());
                setGenderClick(isMan);
                birthday_edt.setText(userInfo.getBirthday());
                profession_edt.setText(userInfo.getOccupation());

                loadingWorker.closeLoading();
                break;
            }
            case HANDLE_SAVE:{
                DataResult<Boolean> updateResult=(DataResult<Boolean>)msg.obj;
                if(updateResult.getSuccess()&&updateResult.getResultData()){
                    ToastUtil.longShow(this,"修改成功");
                    finish();
                }else{
                    ToastUtil.longShow(this,updateResult.getMessage());
                }
                loadingWorker.closeLoading();
                break;
            }
        }
        if(message.length() > 0){
            showToast(message);
        }
        this.loadingWorker.closeLoading();
    }

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.person_data_btn_confirm:
                    userInfo.setRealName(person_data_txt_real_name.getText().toString());
                    userInfo.setBirthday(birthday_edt.getText().toString());
                    userInfo.setOccupation(profession_edt.getText().toString());
                    doWork("修改中....", HANDLE_SAVE,null);
                    break;
                case R.id.person_data_btn_men:
                    setGenderClick(true);
                    break;
                case R.id.person_data_btn_women:
                    setGenderClick(false);
                    break;
                case R.id.birthday_edt:
                    birthdayPicker.show();
                    break;
                case R.id.profession_edt:
                    professionPicker.show();
                    break;
                default:
                    break;
            }
        }
    };
}
