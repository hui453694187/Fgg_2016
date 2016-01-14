package com.yunfangdata.fgg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunfang.framework.base.BaseWorkerActivity;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.http.task.ScheduleReportTask;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.ScheduleReportBeen;
import com.yunfangdata.fgg.utils.MyPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends NewBaseActivity {
    /**
     * 报告编号的查询
     */
    private final int HANDLE_GET_QUERY_NUMBER = 120;
    /**
     * 报告编号的查询传递编号
     */
    private final String HANDLE_GET_QUERY_NUMBER_VALUE = "HANDLE_GET_QUERY_NUMBER_VALUE";

    /**
     * 默认的城市名字
     */
    private String cityName = "beijing";

    /**
     * 放置结果7个步骤的List
     */
    private List<LinearLayout> mLinearLayout = new ArrayList<LinearLayout>();

    /**
     * 结果的文本
     */
    private int[] resutStringsint = {R.string.schedu_item_1_value, R.string.schedu_item_2_value,
            R.string.schedu_item_3_value, R.string.schedu_item_4_value,
            R.string.schedu_item_5_value, R.string.schedu_item_6_value,};

    /**
     * 当前是否在结果页面
     * 在 = true
     */
    private boolean isShowResult = false;
    /**
     * 警告的PopupWindow
     */
    private MyPopupWindow myPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        assignViews();

//        getIntentData();

        getDefaultData();
    }

    /**
     * 获取默认的数据
     */
    private void getDefaultData() {
        //获取城市名称
        cityName = FggApplication.getInstance().city.getCityPinYin();
    }

    /**
     * 获取传递过来的数据
     */
    private void getIntentData() {
        //获取传过来数据
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            //获取传递过来的城市
            String cityn = extras.getString(Constant.KEY_INTENT_TO_HOUSE_DETAIL_CITYNAME);

            //城市名称单独判断
            if (cityn != null && !cityn.isEmpty()){
                cityName = cityn;
            }

        }
    }
    /**
     * 覆盖返回键
     */
    @Override
    public void onBackPressed() {
        //当是结果页面的时候,,先变成查询页面
        if (isShowResult){
            isShowResult = false;
            changeView();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        Bundle bundle = msg.getData();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_QUERY_NUMBER:// 进行进度查询
                //获取传递过来的名字
                String inputNumber = bundle.getString(HANDLE_GET_QUERY_NUMBER_VALUE);
                ScheduleReportTask scheduleReportTask = new ScheduleReportTask();
                ResultInfo<ScheduleReportBeen> resultInfo = scheduleReportTask.request(cityName, inputNumber);
                resultMsg.obj = resultInfo;
                mUiHandler.sendMessage(resultMsg);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_QUERY_NUMBER:// 获取模糊搜索
                //不管成功失败,,还原按钮的点击事件
                scdButtonQuery.setClickable(true);
                scdButtonQuery.setText(getString(R.string.scd_button_query));

                ResultInfo<ScheduleReportBeen> result = (ResultInfo<ScheduleReportBeen>) msg.obj;
                if (result.Success) {
                    if (result.Data != null) {
                        ScheduleReportBeen scheduleReportBeen = result.Data;
                        showResultView(scheduleReportBeen);

                    } else {
                        showToast(result.Message);
                    }
                } else {
                    showToast(result.Message);
                }
                break;
        }
    }

    /**
     * 展示进度查询结果页面
     *
     * @param scheduleReportBeen
     */
    private void showResultView(ScheduleReportBeen scheduleReportBeen) {
        if (scheduleReportBeen != null) {
            List<ScheduleReportBeen.DataEntity> data = scheduleReportBeen.getData();
            if (data != null && data.size() > 0) {

                //更改当前页面为结果页面
                isShowResult = true;
                //改变View
                changeView();

                ScheduleReportBeen.DataEntity dataEntity = data.get(0);
                //设置前面的灰色文字
                scheduNumber.setText(getString(R.string.schedu_number1) + dataEntity.getBaoGaoBiaoHao() + getString(R.string.schedu_number2));

                //取出状态
                String stateString = dataEntity.getState();
                Integer state = Integer.valueOf(stateString);
                //设置文字
                scheduNumberResult.setText((state + 1) + " - " + getString(resutStringsint[state]));

                //判断当前的步骤
                for (int i = 0; i < mLinearLayout.size(); i++) {
                    if (i < state) { //已经完成
                        mLinearLayout.get(i).setBackgroundColor(ScheduleActivity.this.getResources().getColor(R.color.schedule_green));
                    } else if (i == state) {  //正在完成
                        mLinearLayout.get(i).setBackgroundColor(ScheduleActivity.this.getResources().getColor(R.color.schedule_yellow));
                    } else {  //还没有完成
                        mLinearLayout.get(i).setBackgroundColor(ScheduleActivity.this.getResources().getColor(R.color.schedule_gay));
                    }
                }
            }
        }
    }


//    private ImageView buttonBack;
//    private TextView scdTitle;
    private LinearLayout scdLlQuery;
    private EditText scdNumber;
    private ImageView scdNumberWarning;
    private Button scdButtonQuery;

    private TextView scheduNumber;
    private TextView scheduNumberResult;
    private LinearLayout scheduItem1;
    private LinearLayout scheduItem2;
    private LinearLayout scheduItem3;
    private LinearLayout scheduItem4;
    private LinearLayout scheduItem5;
    private LinearLayout scheduItem6;
    private LinearLayout scheduItem7;
    private View scdIncludeResult;


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

//        buttonBack = (ImageView) findViewById(R.id.button_back);
//        scdTitle = (TextView) findViewById(R.id.scd_title);
        scdLlQuery = (LinearLayout) findViewById(R.id.scd_ll_query);
        scdNumber = (EditText) findViewById(R.id.scd_number);
        scdNumberWarning = (ImageView) findViewById(R.id.scd_number_warning);
        scdButtonQuery = (Button) findViewById(R.id.scd_button_query);

        scheduNumber = (TextView) findViewById(R.id.schedu_number);
        scheduNumberResult = (TextView) findViewById(R.id.schedu_number_result);
        scheduItem1 = (LinearLayout) findViewById(R.id.schedu_item_1);
        scheduItem2 = (LinearLayout) findViewById(R.id.schedu_item_2);
        scheduItem3 = (LinearLayout) findViewById(R.id.schedu_item_3);
        scheduItem4 = (LinearLayout) findViewById(R.id.schedu_item_4);
        scheduItem5 = (LinearLayout) findViewById(R.id.schedu_item_5);
        scheduItem6 = (LinearLayout) findViewById(R.id.schedu_item_6);
        scheduItem7 = (LinearLayout) findViewById(R.id.schedu_item_7);

        //设置标题
        navTxtTitle.setText(R.string.scd_title);


        //结果展示的整个VIew
        scdIncludeResult = (View) findViewById(R.id.scd_include_result);
        scdIncludeResult.setVisibility(View.GONE);

        //将7个结果放入List集合中
        mLinearLayout.add(scheduItem1);
        mLinearLayout.add(scheduItem2);
        mLinearLayout.add(scheduItem3);
        mLinearLayout.add(scheduItem4);
        mLinearLayout.add(scheduItem5);
        mLinearLayout.add(scheduItem6);
//        mLinearLayout.add(scheduItem7);


        //返回事件
        navBtnBack.setOnClickListener(myOnClickListener);

        //查询编号的点击事件
        scdButtonQuery.setOnClickListener(myOnClickListener);

        //警告图标的点击事件
        scdNumberWarning.setOnClickListener(myOnClickListener);

        //报告编号的监听
        scdNumber.addTextChangedListener(myscdNumberListener);

        /**
         * 初始化popupWindow
         */
        myPopupWindow = new MyPopupWindow(ScheduleActivity.this);
        myPopupWindow.initPopupWindow();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        myPopupWindow.destroyPopoWindow();
    }

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.scd_button_query: //查询编号的点击事件

                    actionClickQuery();
                    break;
                case R.id.scd_number_warning: //警告图标的点击事件
                    myPopupWindow.showPopup(v,getString(R.string.scd_number_warning));
                    break;
                case R.id.nav_btn_back: //点击返回

                    if (isShowResult){
                        isShowResult = false;
                        changeView();
                    }else {
                        finish();
                    }

                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 改变页面的View显示状态
     */
    private void changeView(){
        if (!isShowResult){
            navTxtTitle.setText(getString(R.string.scd_title)); //设置标题
            scdLlQuery.setVisibility(View.VISIBLE);  //查询页面
            scdIncludeResult.setVisibility(View.GONE); //结果页面
        }else {
            navTxtTitle.setText(getString(R.string.schedule_title_resurt));
            scdLlQuery.setVisibility(View.GONE);
            scdIncludeResult.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 判断输入框是否为空
     *
     * @param editText //输入框
     * @return
     */
    private boolean isEdittextNotNull(EditText editText) {
        return (editText.getText() != null && !editText.getText().toString().isEmpty());
    }

    /**
     * 点击查询编号
     */
    private void actionClickQuery() {

        //判断编号不为空且长度为12
        if (isEdittextNotNull(scdNumber) && scdNumber.getText().toString().length() == 12) {
            getQueryResult(scdNumber.getText().toString());
        } else {
            Toast.makeText(ScheduleActivity.this, "编号错误!", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 搜索编号详情
     */
    private void getQueryResult(String number) {
        //去掉按钮的点击事件
        scdButtonQuery.setClickable(false);
        //改变按钮的文字
        scdButtonQuery.setText(getString(R.string.scd_button_query1));

        Message get_detail = new Message();
        get_detail.what = HANDLE_GET_QUERY_NUMBER;
        Bundle data = new Bundle();
        data.putString(HANDLE_GET_QUERY_NUMBER_VALUE, number);
        get_detail.setData(data);
        mBackgroundHandler.sendMessage(get_detail);
    }

    /**
     * 监听报告编号
     * 以显示警告图标
     */
    private TextWatcher myscdNumberListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String numbetCount = s.toString();
            //如果为空,不处理
            if (numbetCount != null && numbetCount.length() > 0) {
                //如果小于12位
                if (numbetCount.length() < 12) {
                    //显示警告图标
                    scdNumberWarning.setVisibility(View.VISIBLE);
                } else {
                    //不显示警告图标
                    scdNumberWarning.setVisibility(View.INVISIBLE);
                }
            } else {
                //不显示警告图标
                scdNumberWarning.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void showToast(String msg) {
        /**
         * 覆盖掉父类的toast 太长了
         * 使用短toast
         */
        Toast.makeText(ScheduleActivity.this, msg, Toast.LENGTH_SHORT).show();
//        super.showToast(msg);
    }
}
