package com.yunfangdata.fgg.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.yunfang.framework.utils.ListUtil;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.helper.IntentHelper;
import com.yunfangdata.fgg.http.task.EntrustEvaluateTask;
import com.yunfangdata.fgg.http.task.ParseCityTask;
import com.yunfangdata.fgg.logic.PersonOperator;
import com.yunfangdata.fgg.model.EntrustEvaluateBeen;
import com.yunfangdata.fgg.model.EntrustEvaluateReturnBeen;
import com.yunfangdata.fgg.model.PersonContacts;
import com.yunfangdata.fgg.model.PersonRecipients;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.SPUtil;
import com.yunfangdata.fgg.utils.ZLog;
import com.yunfangdata.fgg.viewmodel.EntrustEvaluateViewModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 委托评估
 * 2015年12月18日 10:59:48 zjt
 */
public class EntrustEvaluateActivity extends NewBaseActivity {
    private static final String TAG = "EntrustEvaluateActivity";
    private static final int LOGLEVEL = 1;

    /**
     * 估价目的选择器
     */
    private OptionsPickerView ObjectivePickView;
    /**
     * 贷款成数--选择器
     */
    private OptionsPickerView PercentPickView;
    /**
     * 贷款成数--资料提供
     */
    private OptionsPickerView InformationPickView;
    /**
     * 贷款成数--银行
     */
    private OptionsPickerView BankPickView;
    /**
     * 约看时段
     */
    private OptionsPickerView TimeFramesPickView;
    /**
     * 约看时间
     */
    private TimePickerView TimePickView;

    /**
     * 城市选择
     */
    private OptionsPickerView CityPickView;

    /**
     * 填写信息封装
     */
    private EntrustEvaluateBeen eEBeen = new EntrustEvaluateBeen();

    /**
     * 展示错误信息s
     */
    private PopupWindow popupWindow;
    /**
     * popupWindow的文本
     */
    private TextView pp_textview;
    /**
     * 获取城市选择列表
     */
    private final int HANDLE_GET_CITY_VIEW = 120;
    /**
     * 获取城市选择列表
     */
    private final int HANDLE_GET_EVALUATION_NOW = 121;
    /**
     * 获取收件人信息
     */
    private final int HANDLE_GET_REPORT_DATA = 122;

    /**
     * 看房联系人
     */
    private final int HANDLE_GET_DATA = 123;
    /**
     * 城市列表是否初始化成功
     */
    private boolean isCityInitSeccess = false;
    /**
     * 1级列表 --- 广东省
     */
    private ArrayList<String> options1Items = new ArrayList<String>();
    /**
     * 2级列表 --- 广州市
     */
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    /**
     * 3级列表 --- 天河区
     */
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

    /**
     * 是否能使用返回键
     */
    private boolean isCanBack = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrust_evaluate);

        assignViews();

        initPickerData();

        initPopupWindow();

        //初始化城市选择
        newintiCityPick();

        //获取默认的数据
        getDefoultData();

        //获取收件人信息
        doWork("", HANDLE_GET_REPORT_DATA, "");

        //看房联系人
        doWork("", HANDLE_GET_DATA, "");
    }

    /**
     * 获取默认的数据
     */
    private void getDefoultData() {
        //获取用户名
        String userName = SPUtil.getUserName(EntrustEvaluateActivity.this);
        if (userName != null) {
            eEBeen.setUserPhone(userName);
        } else {
            showToast("你还未登录!不能进行委托!");
            finish();
        }

        //获取城市名称
        String cityname = FggApplication.getInstance().city.getCityPinYin();
        eEBeen.setCityName(cityname);
    }

    /**
     * 进行委托评估
     */
    private void evaluationNow() {
        Message get_detail = new Message();
        get_detail.what = HANDLE_GET_EVALUATION_NOW;
        mBackgroundHandler.sendMessage(get_detail);
    }

    /**
     * 初始化城市选择
     */
    private void newintiCityPick() {
        //选项选择器
        CityPickView = new OptionsPickerView(this);
        //后台加载分析城市列表
        Message get_detail = new Message();
        get_detail.what = HANDLE_GET_CITY_VIEW;
        mBackgroundHandler.sendMessage(get_detail);
    }

    /**
     * 收到所有列表数据
     * 初始化城市选择
     */
    private void newintiCityPick2() {
        //监听确定选择按钮
        CityPickView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                //防止超出大小  --滑动太快的话
                option2 = ((options2Items.get(options1).size() - 1) >= option2) ? option2 : (options2Items.get(options1).size() - 1);
                //防止超出大小  --滑动太快的话
                options3 = ((options3Items.get(options1).get(option2).size() - 1) >= options3) ? options3 : (options3Items.get(options1).get(option2).size() - 1);

                String tx = options1Items.get(options1) + ""
                        + options2Items.get(options1).get(option2) + ""
                        + options3Items.get(options1).get(option2).get(options3) + "";

                eeLocationValue.setText(tx);
                eEBeen.setLocation(tx);
                eeLocationWarning.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        Bundle bundle = msg.getData();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_CITY_VIEW://获取城市列表
                ParseCityTask parseCityTask = new ParseCityTask(EntrustEvaluateActivity.this);
                boolean request = parseCityTask.request(CityPickView, options1Items, options2Items, options3Items);
                resultMsg.obj = request;

                break;
            case HANDLE_GET_EVALUATION_NOW://请求委托
                EntrustEvaluateTask entrustEvaluateTask = new EntrustEvaluateTask();
                ResultInfo<EntrustEvaluateReturnBeen> resultInfo = entrustEvaluateTask.request(eEBeen);
                resultMsg.obj = resultInfo;

                break;
            case HANDLE_GET_REPORT_DATA: //请求收件人
                resultMsg.obj = PersonOperator.recipientsQuery();
                break;
            case HANDLE_GET_DATA: //请求看房联系人
                resultMsg.obj = PersonOperator.contactsQuery();
                break;
        }
        mUiHandler.sendMessage(resultMsg);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_CITY_VIEW://获取城市列表
                isCityInitSeccess = (boolean) msg.obj;
                if (isCityInitSeccess) {
                    newintiCityPick2();
                }
                break;
            case HANDLE_GET_EVALUATION_NOW://请求委托
                ResultInfo<EntrustEvaluateReturnBeen> result = (ResultInfo<EntrustEvaluateReturnBeen>) msg.obj;
                if (result.Success) {
                    if (result.Data != null) {
                        EntrustEvaluateReturnBeen entrustEvaluateReturnBeen = result.Data;
                        if (entrustEvaluateReturnBeen.getSuccess().equals("true")) {
                            showToast(result.Message);
                            showDialogNotBack();
                        }
                    } else {
                        showToast(result.Message);
                    }
                } else {
                    showToast(result.Message);
                }
                break;
            case HANDLE_GET_REPORT_DATA: //报告人
                ResultInfo<ArrayList<PersonRecipients>> resultInfo = (ResultInfo<ArrayList<PersonRecipients>>) msg.obj;
                if (resultInfo.Success && resultInfo != null && ListUtil.hasData(resultInfo.Data)) {
                    ArrayList<PersonRecipients> data = resultInfo.Data;
                    for (int i = 0; i < data.size(); i++) {
                        PersonRecipients personRecipients = data.get(i);
                        //如果有默认报告人
                        if (personRecipients.getMoRenShouJianDiZhi() == 1) {
                            setReportValue(personRecipients);
                        }
                    }
                }
                break;
            case HANDLE_GET_DATA:  //看房联系人
                ResultInfo<ArrayList<PersonContacts>> resultInfo2 = (ResultInfo<ArrayList<PersonContacts>>) msg.obj;
                if (resultInfo2.Success && resultInfo2 != null && ListUtil.hasData(resultInfo2.Data)) {
                    ArrayList<PersonContacts> data = resultInfo2.Data;
                    for (int i = 0; i < data.size(); i++) {
                        PersonContacts personContacts = data.get(i);
                        //如果有看房联系人
                        if (personContacts.getMoRenLianXiRen() == 1) {
                            setLookHouseValue(personContacts);
                        }
                    }
                }
                break;

        }
    }

    /**
     * 设置看房联系人
     *
     * @param personContacts
     */
    private void setLookHouseValue(PersonContacts personContacts) {
        if (personContacts == null) {
            return;
        }
        //是不是默认报告人
        int vis = (personContacts.getMoRenLianXiRen() == 1) ? View.VISIBLE : View.GONE;
        //默认2字是否可见
        eeLookHouseTextview1.setVisibility(vis);

        //设置名称
        String name = (personContacts.getLianXiRenName() != null) ? personContacts.getLianXiRenName() : "";
        eeLookHouseTextviewName.setText(name);

        //设置提交名称
        eEBeen.setLookHousePeople(name);
        //设置电话
        String phone = (personContacts.getShouJi() != null) ? personContacts.getShouJi() : "";
        eeLookHouseTextviewPhone.setText(phone);
        //设置提交电话
        eEBeen.setLookHousePeople(phone);
    }

    /**
     * 设置报告联系人
     *
     * @param personRecipients
     */
    private void setReportValue(PersonRecipients personRecipients) {
        if (personRecipients == null) {
            return;
        }
        //是不是默认报告人
        int vis = (personRecipients.getMoRenShouJianDiZhi() == 1) ? View.VISIBLE : View.GONE;
        //默认2字是否可见
        eeReportTextview1.setVisibility(vis);

        //设置名称
        String name = (personRecipients.getShouJianRen() != null) ? personRecipients.getShouJianRen() : "";
        eeReportTextviewName.setText(name);

        //设置电话
        String phone = (personRecipients.getShouJianRenDianHua() != null) ? personRecipients.getShouJianRenDianHua() : "";
        eeReportTextviewPhone.setText(phone);
        //设置报告人
        //设置报告人电话
        eEBeen.setReportPeople(name + phone);
    }


    private ScrollView buyScrollView;
    private RelativeLayout eeObjectiveRelativeLayout;
    private TextView eeObjectiveValue;
    private ImageView eeObjectiveWarning;
    private EditText eeResidentialName;
    private ImageView eeResidentialNameWarning;
    private RelativeLayout eeBankRelativeLayout;
    private TextView eeBankValue;
    private ImageView eeBankWarning;
    private TextView eeArea;
    private EditText eeAreaValue;
    private ImageView eeAreaWarning;
    private TextView eeTypeTrueHouse;
    private TextView eeTypeFalseHouse;
    private RelativeLayout eeLocationRelativeLayout;
    private TextView eeLocationValue;
    private ImageView eeLocationWarning;
    private EditText eeDetailAddressValue;
    private ImageView eeDetailAdressWarning;
    private RelativeLayout eeTimeRelativeLayout;
    private TextView eeTimeValue;
    private ImageView eeTimeWarning;
    private RelativeLayout eeTimeFramesRelativeLayout;
    private TextView eeTimeFrameValue;
    private ImageView eeTimeFrameWarning;
    private EditText eeFuturesValue;
    private ImageView eeFuturesWarning;
    private RelativeLayout eePercentRelativeLayout;
    private TextView eePercentValue;
    private ImageView eePercentWarning;
    private RelativeLayout eeLookHouseRelativeLayout;
    private TextView eeLookHouseTextview;
    private RelativeLayout eeReportRelativeLayout;
    private TextView eeReportTextview;
    private RelativeLayout eeInformationRelativeLayout;
    private TextView eeInformationValue;
    private ImageView eeInformationWarning;
    private EditText eeMarkValue;
    private ImageView eeMarkWarning;
    private CheckBox eeCheckBox;
    private Button eeBtEnter;

    private RelativeLayout eeLookHouseRelativeLayout2;
    private TextView eeLookHouseTextview1;
    private TextView eeLookHouseTextviewName;
    private TextView eeLookHouseTextviewPhone;
    private RelativeLayout eeReportRelativeLayout2;
    private TextView eeReportTextview1;
    private TextView eeReportTextviewName;
    private TextView eeReportTextviewPhone;

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

        //本体
        buyScrollView = (ScrollView) findViewById(R.id.buy_scroll_view);
        eeObjectiveRelativeLayout = (RelativeLayout) findViewById(R.id.ee_objective_relativeLayout);
        eeObjectiveValue = (TextView) findViewById(R.id.ee_objective_value);
        eeObjectiveWarning = (ImageView) findViewById(R.id.ee_objective_warning);
        eeResidentialName = (EditText) findViewById(R.id.ee_residential_name);
        eeResidentialNameWarning = (ImageView) findViewById(R.id.ee_residential_name_warning);
        eeBankRelativeLayout = (RelativeLayout) findViewById(R.id.ee_bank_relativeLayout);
        eeBankValue = (TextView) findViewById(R.id.ee_bank_value);
        eeBankWarning = (ImageView) findViewById(R.id.ee_bank_warning);
        eeArea = (TextView) findViewById(R.id.ee_area);
        eeAreaValue = (EditText) findViewById(R.id.ee_area_value);
        eeAreaWarning = (ImageView) findViewById(R.id.ee_area_warning);
        eeTypeTrueHouse = (TextView) findViewById(R.id.ee_type_true_house);
        eeTypeFalseHouse = (TextView) findViewById(R.id.ee_type_false_house);
        eeLocationRelativeLayout = (RelativeLayout) findViewById(R.id.ee_location_relativeLayout);
        eeLocationValue = (TextView) findViewById(R.id.ee_location_value);
        eeLocationWarning = (ImageView) findViewById(R.id.ee_location_warning);
        eeDetailAddressValue = (EditText) findViewById(R.id.ee_detail_address_value);
        eeDetailAdressWarning = (ImageView) findViewById(R.id.ee_detail_adress_warning);
        eeTimeRelativeLayout = (RelativeLayout) findViewById(R.id.ee_time_relativeLayout);
        eeTimeValue = (TextView) findViewById(R.id.ee_time_value);
        eeTimeWarning = (ImageView) findViewById(R.id.ee_time_warning);
        eeTimeFramesRelativeLayout = (RelativeLayout) findViewById(R.id.ee_time_frames_relativeLayout);
        eeTimeFrameValue = (TextView) findViewById(R.id.ee_time_frame_value);
        eeTimeFrameWarning = (ImageView) findViewById(R.id.ee_time_frame_warning);
        eeFuturesValue = (EditText) findViewById(R.id.ee_futures_value);
        eeFuturesWarning = (ImageView) findViewById(R.id.ee_futures_warning);
        eePercentRelativeLayout = (RelativeLayout) findViewById(R.id.ee_percent_relativeLayout);
        eePercentValue = (TextView) findViewById(R.id.ee_percent_value);
        eePercentWarning = (ImageView) findViewById(R.id.ee_percent_warning);
        eeLookHouseRelativeLayout = (RelativeLayout) findViewById(R.id.ee_look_house_relativeLayout);
        eeReportRelativeLayout = (RelativeLayout) findViewById(R.id.ee_report_relativeLayout);
        eeInformationRelativeLayout = (RelativeLayout) findViewById(R.id.ee_information_relativeLayout);
        eeInformationValue = (TextView) findViewById(R.id.ee_information_value);
        eeInformationWarning = (ImageView) findViewById(R.id.ee_information_warning);
        eeMarkValue = (EditText) findViewById(R.id.ee_mark_value);
        eeMarkWarning = (ImageView) findViewById(R.id.ee_mark_warning);
        eeCheckBox = (CheckBox) findViewById(R.id.ee_checkBox);
        eeBtEnter = (Button) findViewById(R.id.ee_bt_enter);

        eeLookHouseRelativeLayout2 = (RelativeLayout) findViewById(R.id.ee_look_house_relativeLayout2);
        eeLookHouseTextview1 = (TextView) findViewById(R.id.ee_look_house_textview1);
        eeLookHouseTextviewName = (TextView) findViewById(R.id.ee_look_house_textview_name);
        eeLookHouseTextviewPhone = (TextView) findViewById(R.id.ee_look_house_textview_phone);
        eeReportRelativeLayout2 = (RelativeLayout) findViewById(R.id.ee_report_relativeLayout2);
        eeReportTextview1 = (TextView) findViewById(R.id.ee_report_textview1);
        eeReportTextviewName = (TextView) findViewById(R.id.ee_report_textview_name);
        eeReportTextviewPhone = (TextView) findViewById(R.id.ee_report_textview_phone);

        //监听选择器
        setPickOnClickListener();

        //基础监听器
        setBaseOnClickListener();

        //警告监听器
        setWarningOnClickListener();

        //添加文本监听器
        addTextListener();
    }

    /**
     * 添加文本监听器
     */
    private void addTextListener() {
        //小区名字
        eeResidentialName.addTextChangedListener(myResidentialNameTextListener);

        //建筑面积
        eeAreaValue.addTextChangedListener(myBuildAreaTextListener);

        //期待金额
        eeFuturesValue.addTextChangedListener(myFuturesTextListener);

        //小区名字
        eeDetailAddressValue.addTextChangedListener(myDetailAddressTextListener);

    }

    /**
     * 警告监听器
     */
    private void setWarningOnClickListener() {
        //目的错误
        eeObjectiveWarning.setOnClickListener(myWarningOnClickListener);
        //小区名称
        eeResidentialNameWarning.setOnClickListener(myWarningOnClickListener);
        //建筑面积
        eeAreaWarning.setOnClickListener(myWarningOnClickListener);
        //所在区域
        eeLocationWarning.setOnClickListener(myWarningOnClickListener);
        //详细地址
        eeDetailAdressWarning.setOnClickListener(myWarningOnClickListener);
        //期待金额
        eeFuturesWarning.setOnClickListener(myWarningOnClickListener);
    }

    /**
     * 基础监听器
     */
    private void setBaseOnClickListener() {
        //设置标题
        navTxtTitle.setText(R.string.ee_title);

        //默认的类型
        eEBeen.setTypeHouse("住宅");

        //返回按钮
        navBtnBack.setOnClickListener(myBaseOnClickListener);

        //提交监听
        eeBtEnter.setOnClickListener(myBaseOnClickListener);

        //住宅
        eeTypeTrueHouse.setOnClickListener(myBaseOnClickListener);
        //非住宅
        eeTypeFalseHouse.setOnClickListener(myBaseOnClickListener);
        //看房联系人
        eeLookHouseRelativeLayout.setOnClickListener(myBaseOnClickListener);
        eeLookHouseRelativeLayout2.setOnClickListener(myBaseOnClickListener);

        //报告联系人
        eeReportRelativeLayout.setOnClickListener(myBaseOnClickListener);
        eeReportRelativeLayout2.setOnClickListener(myBaseOnClickListener);
    }

    /**
     * 监听选择器
     */
    private void setPickOnClickListener() {

        //初始化估价目的
        eeObjectiveRelativeLayout.setOnClickListener(myPickerOnClickListener);
        //初始化--银行
        eeBankRelativeLayout.setOnClickListener(myPickerOnClickListener);
        //初始化贷款成数
        eePercentRelativeLayout.setOnClickListener(myPickerOnClickListener);
        //初始化--资料提供
        eeInformationRelativeLayout.setOnClickListener(myPickerOnClickListener);
        //时间
        eeTimeRelativeLayout.setOnClickListener(myPickerOnClickListener);
        //时段
        eeTimeFramesRelativeLayout.setOnClickListener(myPickerOnClickListener);
        //城市选择器
        eeLocationRelativeLayout.setOnClickListener(myPickerOnClickListener);

    }

    /**
     * 初始化数据
     */
    private void initPickerData() {
        //初始化估价目的
        intiObjectivePick();

        //初始化贷款成数
        intiPercentPick();

        //初始化--资料提供
        intiInformationPick();

        //初始化--银行
        intiBankPick();

        //初始化--时段
        intiTimeFramesPick();

        //约看时间
        intiTimePick();
    }


    /**
     * 约看时间
     */
    private void intiTimePick() {
        TimePickView = new TimePickerView(EntrustEvaluateActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
        TimePickView.setTime(new Date());
        TimePickView.setCancelable(true);
        //时间选择后回调
        TimePickView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {

                //当前一天减一天再做判断
                if (date.after(subtractOneDay()) && date.before(addThirtyDay())) {
                    eEBeen.setTime(getTime(date));
                    eeTimeValue.setText(getTime(date));
                } else {
                    showToast("约看时间只能在30天以内");
                    TimePickView.setTime(new Date());
                }
            }
        });
        eeTimeValue.setText(getTime(new Date()));
        eEBeen.setTime(getTime(new Date()));
    }


    /**
     * 当前时间加30天
     *
     * @return 30天后的日期
     */
    private Date addThirtyDay() {
//        Calendar nowTime = Calendar.getInstance();
        Calendar afterTime = Calendar.getInstance();
        afterTime.add(Calendar.DAY_OF_MONTH, 30); //当前日期加30天
        Date addDate = (Date) afterTime.getTime();
//
//        System.out.println("今天时间"+nowDate);
//        System.out.println("修改后的 时间"+addDate);
        return addDate;
    }


    /**
     * 当前时间减一天
     *
     * @return 减一天后的日期
     */
    private Date subtractOneDay() {
        Calendar afterTime = Calendar.getInstance();
        afterTime.add(Calendar.DAY_OF_MONTH, -1); //当前日期加30天
        Date subtractDate = (Date) afterTime.getTime();
        return subtractDate;
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
     * 初始化贷款成数
     */
    private void intiPercentPick() {
        PercentPickView = new OptionsPickerView(EntrustEvaluateActivity.this);
        final ArrayList<String> strings = initAllOptionsPicker(PercentPickView, EntrustEvaluateViewModel.percents);
        //监听确定选择按钮
        PercentPickView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = strings.get(options1);
                eePercentValue.setText(tx);

                //设置数据 供提交
                eEBeen.setPercent(tx);
                eEBeen.setPercentIndex(options1);
//                //设置警告图标是否可见
//                int vis = (options1 == 0) ? View.VISIBLE : View.INVISIBLE;
//                eePercentWarning.setVisibility(vis);
            }
        });
        //设置默认选择
        eePercentValue.setText(EntrustEvaluateViewModel.percents[0]);
        //设置默认选择
        eEBeen.setPercent(EntrustEvaluateViewModel.percents[0]);
        eEBeen.setPercentIndex(0);
    }

    /**
     * 初始化资料提供
     */
    private void intiInformationPick() {
        InformationPickView = new OptionsPickerView(EntrustEvaluateActivity.this);
        final ArrayList<String> strings = initAllOptionsPicker(InformationPickView, EntrustEvaluateViewModel.informations);
        //监听确定选择按钮
        InformationPickView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = strings.get(options1);
                eeInformationValue.setText(tx);

                //设置数据 供提交
                eEBeen.setInformation(tx);
//                //设置警告图标是否可见
//                int vis = (options1 == 0) ? View.VISIBLE : View.INVISIBLE;
//                eeInformationWarning.setVisibility(vis);

                //显示对话框
                if (options1 != 0) {
                    showDialog(EntrustEvaluateViewModel.informationsTips[options1]);
                }
            }
        });
        //设置默认选择
        eeInformationValue.setText(EntrustEvaluateViewModel.informations[0]);
        //设置默认选择
        eEBeen.setInformation(EntrustEvaluateViewModel.informations[0]);
    }

    /**
     * 初始化时段
     */
    private void intiTimeFramesPick() {
        TimeFramesPickView = new OptionsPickerView(EntrustEvaluateActivity.this);
        final ArrayList<String> strings = initAllOptionsPicker(TimeFramesPickView, EntrustEvaluateViewModel.timeframes);
        //监听确定选择按钮
        TimeFramesPickView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = strings.get(options1);
                eeTimeFrameValue.setText(tx);

                //设置数据 供提交
                eEBeen.setTimeFrames(tx);
//                //设置警告图标是否可见
//                int vis = (options1 == 0) ? View.VISIBLE : View.INVISIBLE;
//                eeTimeFrameWarning.setVisibility(vis);
            }
        });
        //设置默认选择
        eeTimeFrameValue.setText(EntrustEvaluateViewModel.timeframes[0]);
        //设置默认选择
        eEBeen.setTimeFrames(EntrustEvaluateViewModel.timeframes[0]);
    }

    /**
     * 初始化银行
     */
    private void intiBankPick() {
        BankPickView = new OptionsPickerView(EntrustEvaluateActivity.this);
        final ArrayList<String> strings = initAllOptionsPicker(BankPickView, EntrustEvaluateViewModel.banks);
        //监听确定选择按钮
        BankPickView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = strings.get(options1);
                eeBankValue.setText(tx);

                //设置数据 供提交
                eEBeen.setBank(tx);
//                //设置警告图标是否可见
//                int vis = (options1 == 0) ? View.VISIBLE : View.INVISIBLE;
//                eeBankWarning.setVisibility(vis);
            }
        });
        //设置默认选择
        eeBankValue.setText(EntrustEvaluateViewModel.banks[0]);
        //设置默认数据 供提交
        eEBeen.setBank(EntrustEvaluateViewModel.banks[0]);
    }

    /**
     * 初始化估价目的
     */
    private void intiObjectivePick() {
        ObjectivePickView = new OptionsPickerView(EntrustEvaluateActivity.this);
        final ArrayList<String> strings = initAllOptionsPicker(ObjectivePickView, EntrustEvaluateViewModel.objective);
        //监听确定选择按钮
        ObjectivePickView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = strings.get(options1);
                eeObjectiveValue.setText(tx);

                //设置数据 供提交
                eEBeen.setObjective(tx);
                //设置警告图标是否可见
                int vis = (options1 == 0) ? View.VISIBLE : View.INVISIBLE;
                eeObjectiveWarning.setVisibility(vis);
                if (options1 != 0) {
                    showDialog(EntrustEvaluateViewModel.objectiveTipes[options1]);
                }
            }
        });

        //设置默认选择
        eeObjectiveValue.setText(EntrustEvaluateViewModel.objective[0]);
    }


    /**
     * 初次化选择器
     */
    private ArrayList<String> initAllOptionsPicker(OptionsPickerView optionsPickerView, String[] data) {
        ArrayList<String> optionsItems = new ArrayList<String>();
        for (int i = 0; i < data.length; i++) {
            optionsItems.add(data[i]);
        }
        //设置数据
        optionsPickerView.setPicker(optionsItems);
        //设置是否循环滚动
        optionsPickerView.setCyclic(false);
        //设置默认选中的项目
        optionsPickerView.setSelectOptions(0);

        return optionsItems;
    }

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myPickerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ee_objective_relativeLayout: //估价目的
                    ObjectivePickView.show();
                    break;
                case R.id.ee_bank_relativeLayout: //银行
                    BankPickView.show();
                    break;
                case R.id.ee_information_relativeLayout: //资料提供
                    InformationPickView.show();
                    break;
                case R.id.ee_percent_relativeLayout: //贷款成数
                    PercentPickView.show();
                    break;
                case R.id.ee_time_frames_relativeLayout: //时段
                    TimeFramesPickView.show();
                    break;
                case R.id.ee_time_relativeLayout: //时间
                    TimePickView.show();
                    break;
                case R.id.ee_location_relativeLayout: //城市选择
                    if (isCityInitSeccess) {
                        CityPickView.show();
                    } else {
                        showToast("城市列表正在初始化,请稍候再试");
                    }
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myBaseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nav_btn_back: //返回
                    finish();
                    break;
                case R.id.ee_bt_enter: //提交
                    //验证数据并提交
                    actionClickEnter();
                    break;
                case R.id.ee_type_true_house: //住宅
                    eEBeen.setTypeHouse("住宅");
                    eeTypeTrueHouse.setBackgroundColor(getResources().getColor(R.color.title_blue));
                    eeTypeFalseHouse.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case R.id.ee_type_false_house: //非住宅
                    eEBeen.setTypeHouse("非住宅");
                    eeTypeTrueHouse.setBackgroundColor(getResources().getColor(R.color.white));
                    eeTypeFalseHouse.setBackgroundColor(getResources().getColor(R.color.title_blue));
                    break;
                case R.id.ee_look_house_relativeLayout: //看房
                case R.id.ee_look_house_relativeLayout2: //看房2
                    actionClickLookhouse();
                    break;
                case R.id.ee_report_relativeLayout://报告
                case R.id.ee_report_relativeLayout2://报告2
                    actionClickrReport();
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 报告联系人
     */
    private void actionClickrReport() {
        IntentHelper.toPersonRecipients4Result(EntrustEvaluateActivity.this);
    }

    /**
     * 获取看房联系人
     */
    private void actionClickLookhouse() {
        IntentHelper.toPersonContacts4Result(EntrustEvaluateActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取看房联系人返回
        if (requestCode == Constant.GET_PERSION_CONTACTS_4_RESULT && requestCode == Constant.GET_PERSION_CONTACTS_4_RESULT) {
            if (data != null) {

                Bundle bundleExtra = data.getExtras();
                if (bundleExtra != null) {
                    PersonContacts personContacts = (PersonContacts) bundleExtra.getSerializable(Constant.GET_PERSION_CONTACTS_4_RESULT + "");
                    setLookHouseValue(personContacts);
                }
            }
        }
        //获取报告联系人返回
        if (requestCode == Constant.GET_RECIPIENTS_4_RESULT && requestCode == Constant.GET_RECIPIENTS_4_RESULT) {
            if (data != null) {
                Bundle bundleExtra = data.getExtras();
                if (bundleExtra != null) {
                    PersonRecipients personRecipients = (PersonRecipients) bundleExtra.getSerializable(Constant.GET_RECIPIENTS_4_RESULT + "");
                    setReportValue(personRecipients);
                }
            }
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

    /**
     * 验证数据并提交
     */
    private void actionClickEnter() {
        if (!eeCheckBox.isChecked()) {
            showToast("你必须同意委托协议书才能委托");
            return;
        }
        //判断估价目的
        if (eEBeen.getObjective() != null
                && !eEBeen.getObjective().equals(EntrustEvaluateViewModel.objective[0])) {
        }else {
            showToast(getString(R.string.ee_objective_error));
            //警告图标可见
            eeObjectiveWarning.setVisibility(View.VISIBLE);
            return;
        }
        //小区名称
        if (!isEdittextNotNull(eeResidentialName)) {
            showToast(getString(R.string.ee_residential_name_erroe));
            //警告图标可见
            eeResidentialNameWarning.setVisibility(View.VISIBLE);
            return;
        }
        //设置小区名字
        eEBeen.setResidentialName(eeResidentialName.getText().toString().trim());

        //设置小区面积
        if (isEdittextNotNull(eeAreaValue) && ifCanBuildAreaText(eeAreaValue.getText().toString().trim())) {
            eEBeen.setArea(eeAreaValue.getText().toString().trim());
        } else {
            showToast(getString(R.string.ee_area_warning_ee));
            //警告图标可见
            eeAreaWarning.setVisibility(View.VISIBLE);
            return;
        }
        //所在区域
        if (eEBeen.getLocation() == null) {
            showToast(getString(R.string.ee_location_warning_ee));
            //警告图标可见
            eeLocationWarning.setVisibility(View.VISIBLE);
            return;
        }

        //详细地址
        if (!isEdittextNotNull(eeDetailAddressValue)) {
            showToast(getString(R.string.ee_detail_adress_warning_ee));
            //警告图标可见
            eeDetailAdressWarning.setVisibility(View.VISIBLE);
            return;
        }
        //拼接详细地址
        eEBeen.setDetailAddress(eeLocationValue.getText().toString() + eeDetailAddressValue.getText().toString().trim());

        //设置期待金额
        String futures = null;
        if (isEdittextNotNull(eeFuturesValue) && ifCanBuildAreaText(eeFuturesValue.getText().toString().trim())) {
            futures = eeFuturesValue.getText().toString().trim();
            eEBeen.setFutures(futures);
        } else {
            showToast(getString(R.string.ee_futures_warning_ee));
            //警告图标可见
            eeFuturesWarning.setVisibility(View.VISIBLE);
            return;
        }
        //备注信息
        if (isEdittextNotNull(eeMarkValue)) {
            eEBeen.setMark(eeMarkValue.getText().toString().trim());
        }

        //约看总时间
        eEBeen.setTimeAll(eEBeen.getTime() + eEBeen.getTimeFrames());

        //期待信息
        Double aDoublefutures = Double.valueOf(futures);
        //计算期待信息
        Double result = aDoublefutures * (eEBeen.getPercentIndex() + 1) / 10.0;
        DecimalFormat df = new DecimalFormat("#.0000");
        //格式化结果
        String format = df.format(result);
        eEBeen.setPercentresult(format);

        //请求委托
        evaluationNow();

    }


    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myWarningOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ee_objective_warning: //
                    showPopupWindow(getString(R.string.ee_objective_error), v);
                    break;
                case R.id.ee_residential_name_warning: //
                    showPopupWindow(getString(R.string.ee_residential_name_erroe), v);
                    break;
                case R.id.ee_area_warning:
                    showPopupWindow(getString(R.string.ee_area_warning_ee), v);
                    break;
                case R.id.ee_location_warning:
                    showPopupWindow(getString(R.string.ee_location_warning_ee), v);
                    break;
                case R.id.ee_detail_adress_warning:
                    showPopupWindow(getString(R.string.ee_detail_adress_warning_ee), v);
                    break;
                case R.id.ee_futures_warning:
                    showPopupWindow(getString(R.string.ee_futures_warning_ee), v);
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 展示popoWindow
     *
     * @param string
     * @param v
     */
    private void showPopupWindow(String string, View v) {
        destroyPopoWindow();
        pp_textview.setText(string);
        popupWindow.showAsDropDown(v);
    }

    /**
     * 初次化 PopupWindow
     */
    private void initPopupWindow() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(EntrustEvaluateActivity.this).inflate(R.layout.view_show_error, null);

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        pp_textview = (TextView) contentView.findViewById(R.id.pp_textview);
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
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.my_popu_orange2));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyPopoWindow();
    }

    /**
     * 隐藏popupWindow
     */
    private void destroyPopoWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 监听输入框
     */
    private TextWatcher myResidentialNameTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String name = s.toString();
            if (name != null && name.length() > 0) {
                //是否显示警告图标
                eeResidentialNameWarning.setVisibility(View.INVISIBLE);
            } else {
                //是否显示警告图标
                eeResidentialNameWarning.setVisibility(View.VISIBLE);
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
     * 监听输入框
     */
    private TextWatcher myDetailAddressTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String name = s.toString();
            if (name != null && name.length() > 0) {
                //是否显示警告图标
                eeDetailAdressWarning.setVisibility(View.INVISIBLE);
            } else {
                //是否显示警告图标
                eeDetailAdressWarning.setVisibility(View.VISIBLE);
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
     * 监听期待金额输入框
     */
    private TextWatcher myFuturesTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //不显示警告图标
            eeFuturesWarning.setVisibility(View.VISIBLE);
            String area = s.toString();
            if (ifCanBuildAreaText(area)) {
                //显示警告图标
                eeFuturesWarning.setVisibility(View.INVISIBLE);
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
     * 监听建筑面积输入框
     */
    private TextWatcher myBuildAreaTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //不显示警告图标
            eeAreaWarning.setVisibility(View.VISIBLE);

            String area = s.toString();
            if (ifCanBuildAreaText(area)) {
                //显示警告图标
                eeAreaWarning.setVisibility(View.INVISIBLE);
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
     * 判断建筑面积是否合格
     *
     * @param area
     * @return
     */
    private boolean ifCanBuildAreaText(String area) {
        boolean isCanAdd = false;
        if (area != null && area.length() > 0) {
            //判断是否超出取值范围
            Float areaf = Float.valueOf(area);
            if (areaf >= 1.00 && areaf <= 9999.99) {
                isCanAdd = true;
            } else {
                isCanAdd = false;
            }

            //判断小数点是否超出取值范围
            String temp[] = area.split("\\.");
            ZLog.Zlogi("temp.length = " + temp.length + "name = " + area, TAG, LOGLEVEL);
            if (temp.length == 2) {
                //取出小数点后2位
                Double area2 = Double.valueOf(temp[1]);
                if (area2 >= 100d) {
                    isCanAdd = false;
                }
            }
        } else {
            isCanAdd = false;
        }

        return isCanAdd;
    }

    @Override
    public void showToast(String msg) {
        /**
         * 覆盖掉父类的toast 太长了
         * 使用短toast
         */
        Toast.makeText(EntrustEvaluateActivity.this, msg, Toast.LENGTH_SHORT).show();
//        super.showToast(msg);
    }

    /**
     * 展示对话框
     */
    private void showDialog(String massage) {

        AlertDialog dialog = new AlertDialog.Builder(EntrustEvaluateActivity.this).create();
        dialog.setTitle("提示");
        dialog.setMessage(massage);
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
     * 展示对话框
     */
    private void showDialogNotBack() {

        isCanBack = false;
        AlertDialog dialog = new AlertDialog.Builder(EntrustEvaluateActivity.this).create();
        dialog.setTitle("委托成功");
        dialog.setMessage("接下来你希望去哪里?");
        dialog.setCancelable(false);
        // 确定
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "去首页", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                setResult(Constant.KEY_INTENT_TO_CLOSE_YOURSELF);
                finish();
            }
        });

        // 确定
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "去委托详情", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                IntentHelper.intentToEntrustList(EntrustEvaluateActivity.this);
                finish();
            }
        });
        // 显示对话框
        dialog.show();
    }

    /**
     * 覆盖返回键
     */
    @Override
    public void onBackPressed() {
        if (isCanBack) {
            super.onBackPressed();
        }
    }
}
