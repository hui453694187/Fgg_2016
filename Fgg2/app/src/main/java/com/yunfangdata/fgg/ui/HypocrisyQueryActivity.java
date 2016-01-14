package com.yunfangdata.fgg.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.http.task.HypocrisyQueryTask;
import com.yunfangdata.fgg.model.HyporisyQueryBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.KeyBoardUtil;
import com.yunfangdata.fgg.utils.MyPopupWindow;

/**
 * 真伪查询页面
 * <p/>
 * 2015年12月14日 15:50:05  -- zjt
 */
public class HypocrisyQueryActivity extends NewBaseActivity {
    /**
     * 报告编号的查询
     */
    private final int HANDLE_GET_QUERY_NUMBER = 120;
    /**
     * 报告编号的查询传递编号
     */
    private final String HANDLE_GET_QUERY_NUMBER_VALUE = "HANDLE_GET_QUERY_NUMBER_VALUE";
    /**
     * 报告编号的查询委托方名称
     */
    private final String HANDLE_GET_QUERY_NUMBER_NAME = "HANDLE_GET_QUERY_NUMBER_NAME";

    /**
     * 默认的城市名字
     */
    private String cityName = "beijing";

    /**
     * 判断当前是那个view在显示
     * 0---查询
     * 1---有结果
     * 2---感叹号!!
     */
    private int whoViewShowInThis = 0;
    /**
     * 编号的长度限制
     */
    private static final int numberLean = 15;

    /**
     * 警告的PopupWindow
     */
    private MyPopupWindow myPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hypocrisy_query);

        //初始化数据
        initData();

        assignViews();

        changeView();

//        getIntentData();
        getDefaultData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        /**
         * 初始化popupWindow
         */
        myPopupWindow = new MyPopupWindow(HypocrisyQueryActivity.this);
        myPopupWindow.initPopupWindow();

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
            if (cityn != null && !cityn.isEmpty()) {
                cityName = cityn;
            }

        }
    }


    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        Bundle bundle = msg.getData();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_QUERY_NUMBER:// 进行真虚伪查询
                //获取传递过来的名字
                String inputNumber = bundle.getString(HANDLE_GET_QUERY_NUMBER_VALUE);
                String inputName = bundle.getString(HANDLE_GET_QUERY_NUMBER_NAME);
                HypocrisyQueryTask hypocrisyQueryTask = new HypocrisyQueryTask();
                ResultInfo<HyporisyQueryBeen> resultInfo = hypocrisyQueryTask.request(cityName, inputNumber, inputName);
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
                hqButtonQuery.setClickable(true);
                hqButtonQuery.setText(getString(R.string.scd_button_query));

                ResultInfo<HyporisyQueryBeen> result = (ResultInfo<HyporisyQueryBeen>) msg.obj;
                if (result.Success) {
                    if (result.Data != null) {
                        HyporisyQueryBeen hyporisyQueryBeen = result.Data;
                        showResultView(hyporisyQueryBeen);

                    } else {
                        showToast(result.Message);
                    }
                } else {
                    showWarningView();
                }
                break;
        }
    }


    /**
     * 展示进度查询结果页面
     *
     * @param hyporisyQueryBeen
     */
    private void showResultView(HyporisyQueryBeen hyporisyQueryBeen) {
        if (hyporisyQueryBeen != null) {
            HyporisyQueryBeen.ResultDataEntity resultData = hyporisyQueryBeen.getResultData();
            if (resultData != null && resultData.getData() != null) {
                HyporisyQueryBeen.ResultDataEntity.DataEntity data = resultData.getData();
                String status = data.getStatus();
                if (status.equals("完成")) {
                    showCompleteView(data);
                } else {
                    showWaitingDialog();
                }

            }
        }
    }


    /**
     * 展示警告页面
     */
    private void showWarningView() {
        //页面修改
        whoViewShowInThis = 2;
        changeView();
    }

    /**
     * 展示等待中对话框
     */
    private void showWaitingDialog() {

        AlertDialog dialog = new AlertDialog.Builder(HypocrisyQueryActivity.this).create();
        dialog.setTitle("提示");
        dialog.setMessage("查询成功，您的委托尚未出结果报告，请您耐心等待。");
        // 确定
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // 显示对话框
        dialog.show();

    }

    /**
     * 展示成功的页面
     *
     * @param data
     */
    private void showCompleteView(HyporisyQueryBeen.ResultDataEntity.DataEntity data) {
        //页面修改
        whoViewShowInThis = 1;
        changeView();

        //报告编号
        if (data.getReportNum() != null && !data.getReportNum().isEmpty()) {
            hqResultReportNum.setText(data.getReportNum());
        }

        //项目位置
        if (data.getAddress() != null && !data.getAddress().isEmpty()) {
            hqResultAddress.setText(data.getAddress());
        }

        //总价
        if (data.getExpectPrice() != null && !data.getExpectPrice().isEmpty()) {
            hqResultTotalPrices.setText(data.getExpectPrice());
        }

        //单价
        if (data.getPrice() != null && !data.getPrice().isEmpty()) {
            hqResultPrices.setText(data.getPrice());
        }

        //建成时间
        if (data.getBuildedYear() != null && !data.getBuildedYear().isEmpty()) {
            hqResultBuildedYear.setText(data.getBuildedYear());
        }

        //产权性质
        if (data.getProperty() != null && !data.getProperty().isEmpty()) {
            hqResultProperty.setText(data.getProperty());
        }

        //利用状态
        if (data.getPresentStatus() != null && !data.getPresentStatus().isEmpty()) {
            hqResultUseState.setText(data.getPresentStatus());
        }

        //物理结构
        if (data.getPhysicalChange() != null && !data.getPhysicalChange().isEmpty()) {
            hqResultPhyicalStructure.setText(data.getPhysicalChange());
        }


    }


    //    private ImageView buttonBack;
//    private TextView sqTitle;
    private View activityHypocrisyQueryBase;
    private View activityHypocrisyQueryWarning;
    private View activityHypocrisyQueryResult;

    private LinearLayout scdLlQuery;
    private EditText hqNumber;
    private ImageView hqNumberWarning;
    private EditText hqName;
    private ImageView hqNameWarning;
    private Button hqButtonQuery;
    private TextView hqInstruction1;
    private TextView hqInstruction2;

    private TextView hqResultReportNum;
    private TextView hqResultAddress;
    private TextView hqResultTotalPrices;
    private TextView hqResultPrices;
    private TextView hqResultBuildedYear;
    private TextView hqResultProperty;
    private TextView hqResultUseState;
    private TextView hqResultPhyicalStructure;
    private Button hqButtonResultBack;

    private Button hqButtonWarning;

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
        //基础页面
//        buttonBack = (ImageView) findViewById(R.id.button_back);
//        sqTitle = (TextView) findViewById(R.id.sq_title);
        activityHypocrisyQueryBase = (View) findViewById(R.id.activity_hypocrisy_query_base);
        activityHypocrisyQueryWarning = (View) findViewById(R.id.activity_hypocrisy_query_warning);
        activityHypocrisyQueryResult = (View) findViewById(R.id.activity_hypocrisy_query_result);

        //查询页面
        scdLlQuery = (LinearLayout) findViewById(R.id.scd_ll_query);
        hqNumber = (EditText) findViewById(R.id.hq_number);
        hqNumberWarning = (ImageView) findViewById(R.id.hq_number_warning);
        hqName = (EditText) findViewById(R.id.hq_name);
        hqNameWarning = (ImageView) findViewById(R.id.hq_name_warning);
        hqButtonQuery = (Button) findViewById(R.id.hq_button_query);
        hqInstruction1 = (TextView) findViewById(R.id.hq_instruction1);
        hqInstruction2 = (TextView) findViewById(R.id.hq_instruction2);

        //结果页面
        hqResultReportNum = (TextView) findViewById(R.id.hq_result_reportNum);
        hqResultAddress = (TextView) findViewById(R.id.hq_result_address);
        hqResultTotalPrices = (TextView) findViewById(R.id.hq_result_total_prices);
        hqResultPrices = (TextView) findViewById(R.id.hq_result_prices);
        hqResultBuildedYear = (TextView) findViewById(R.id.hq_result_builded_year);
        hqResultProperty = (TextView) findViewById(R.id.hq_result_property);
        hqResultUseState = (TextView) findViewById(R.id.hq_result_use_state);
        hqResultPhyicalStructure = (TextView) findViewById(R.id.hq_result_phyical_structure);
        hqButtonResultBack = (Button) findViewById(R.id.hq_button_result_back);

        //设置标题
        navTxtTitle.setText(R.string.sq_title);

        //警告页面
        hqButtonWarning = (Button) findViewById(R.id.hq_button_warning);


        //高级文本  设置说明文字部分红色
        SpannableString spanText1 = new SpannableString(getString(R.string.hq_instruction1));
        spanText1.setSpan(new ForegroundColorSpan(Color.RED), 0, 6, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        hqInstruction1.setText(spanText1);

        //高级文本  设置说明文字部分红色
        SpannableString spanText2 = new SpannableString(getString(R.string.hq_instruction2));
        spanText2.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        hqInstruction2.setText(spanText2);

        //报告编号的监听
        hqNumber.addTextChangedListener(myhqNumberListener);
        //报告编号姓名的监听
        hqName.addTextChangedListener(myhqNameListener);

        //查询编号的点击事件
        hqButtonQuery.setOnClickListener(myOnClickListener);

        //返回事件
        navBtnBack.setOnClickListener(myOnClickListener);

        //结果页面的返回按钮
        hqButtonResultBack.setOnClickListener(myOnClickListener);

        //警告页面的返回按钮
        hqButtonWarning.setOnClickListener(myOnClickListener);
        //编号警告图标
        hqNumberWarning.setOnClickListener(myOnClickListener);
        //名字警告图标
        hqNameWarning.setOnClickListener(myOnClickListener);
    }


    /**
     * 改变页面的View显示状态
     */
    private void changeView() {
        //隐藏键盘
        KeyBoardUtil.hideKeyboard(this);

        activityHypocrisyQueryBase.setVisibility(View.GONE);//基础的查询页面
        activityHypocrisyQueryResult.setVisibility(View.GONE);//查询结果展示页面
        activityHypocrisyQueryWarning.setVisibility(View.GONE);//查询出错感叹号
        if (whoViewShowInThis == 1) {
            activityHypocrisyQueryResult.setVisibility(View.VISIBLE);//查询结果展示页面
        } else if (whoViewShowInThis == 2) {
            activityHypocrisyQueryWarning.setVisibility(View.VISIBLE);//查询出错感叹号
        } else {
            activityHypocrisyQueryBase.setVisibility(View.VISIBLE);//基础的查询页面
        }
    }

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hq_button_query: //查询编号的点击事件
                    actionClickQuery();
                    break;
                case R.id.nav_btn_back: //点击返回
                    if (whoViewShowInThis != 0) {
                        //页面修改
                        whoViewShowInThis = 0;
                        changeView();
                    } else {
                        finish();
                    }

                    break;

                case R.id.hq_button_result_back: //结果页面的返回按钮
                    //页面修改
                    whoViewShowInThis = 0;
                    changeView();
                    break;
                case R.id.hq_button_warning: //警告页面的返回按钮
                    //页面修改
                    whoViewShowInThis = 0;
                    changeView();
                    break;
                case R.id.hq_number_warning: // 编号警告图标
                    myPopupWindow.showPopup(v, getString(R.string.hq_number_warning));
                    break;
                case R.id.hq_name_warning: // 名字警告图标
                    myPopupWindow.showPopup(v, getString(R.string.hq_name_warning));
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 覆盖返回键
     */
    @Override
    public void onBackPressed() {
        //当是结果页面的时候,,先变成查询页面
        if (whoViewShowInThis != 0) {
            //页面修改
            whoViewShowInThis = 0;
            changeView();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 监听报告编号
     * 以显示警告图标
     */
    private TextWatcher myhqNumberListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String numbetCount = s.toString();
            //如果为空,不处理
            if (numbetCount != null && numbetCount.length() > 0) {
                //如果小于15位
                if (numbetCount.length() < numberLean) {
                    //显示警告图标
                    hqNumberWarning.setVisibility(View.VISIBLE);
                } else {
                    //不显示警告图标
                    hqNumberWarning.setVisibility(View.INVISIBLE);
                }
            } else {
                //不显示警告图标
                hqNumberWarning.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    /**
     * 监听报告名字
     * 以显示警告图标
     */
    private TextWatcher myhqNameListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String numbetCount = s.toString();
            if (numbetCount != null && numbetCount.length() > 0) {
                hqNameWarning.setVisibility(View.INVISIBLE);
            } else {
                //显示警告图标
                hqNumberWarning.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

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
     * 点击查询编号
     */
    private void actionClickQuery() {

        //判断编号不为空且长度为15
        if (isEdittextNotNull(hqNumber) && hqNumber.getText().toString().length() == numberLean) {
            if (isEdittextNotNull(hqName)) {
                getQueryResult(hqNumber.getText().toString().trim(), hqName.getText().toString().trim());
            } else {
                showToast(getString(R.string.hq_name_warning));
                hqNameWarning.setVisibility(View.VISIBLE);
            }
        } else {
            showToast(getString(R.string.hq_number_warning));
            hqNumberWarning.setVisibility(View.VISIBLE);
            return;
        }
    }

    /**
     * 搜索编号详情
     */
    private void getQueryResult(String number, String name) {
        //去掉按钮的点击事件
        hqButtonQuery.setClickable(false);
        //改变按钮的文字
        hqButtonQuery.setText(getString(R.string.scd_button_query1));

        Message get_detail = new Message();
        get_detail.what = HANDLE_GET_QUERY_NUMBER;
        Bundle data = new Bundle();
        data.putString(HANDLE_GET_QUERY_NUMBER_VALUE, number);
        data.putString(HANDLE_GET_QUERY_NUMBER_NAME, name);
        get_detail.setData(data);
        mBackgroundHandler.sendMessage(get_detail);
    }

    @Override
    public void showToast(String msg) {
        /**
         * 覆盖掉父类的toast 太长了
         * 使用短toast
         */
        Toast.makeText(HypocrisyQueryActivity.this, msg, Toast.LENGTH_SHORT).show();
//        super.showToast(msg);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


        myPopupWindow.destroyPopoWindow();
    }
}
