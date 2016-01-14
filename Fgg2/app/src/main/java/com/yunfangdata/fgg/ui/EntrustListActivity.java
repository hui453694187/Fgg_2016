package com.yunfangdata.fgg.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.adapter.EntrustListAdapter;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.http.task.EntrustListActivityTask;
import com.yunfangdata.fgg.http.task.EntrustListReminderTask;
import com.yunfangdata.fgg.model.EElistBeen;
import com.yunfangdata.fgg.model.EntrustListReminderBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.SPUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntrustListActivity extends NewBaseActivity{
    /**
     * 获取委托列表
     */
    private final int HANDLE_GET_EE_LIST = 121;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 查询时间段,开始时间
     */
    private String startTime;
    /**
     * 查询时间段,结束时间
     */
    private String endTime;
    /**
     * 每页条数
     */
    private String pageSize = "100";
    /**
     * 当前页数
     */
    private String curPage = "1";
    /**
     * 传空为获取所有
     * 委托状态:0.待支付,1.未受理,2.受理中,3.已完成,4.已撤单
     */
    private String state = "";


    /**
     * 展示修改
     */
    private PopupWindow popupWindow;

    /**
     * 随便搞一个View 用来显示popupWindow
     */
    private View view;


    /**
     * 点击修改
     */
    public static final int HANDLER_SHOW_POPUPWINDOW = 110;
    /**
     * 点击催单
     */
    public static final int HANDLER_REMINDER = 111;
    /**
     * 点击撤单
     */
    public static final int HANDLER_REVOKE = 112;
    /**
     * 请求催单或者撤单
     */
    public static final int HANDLER_REQUERT_REVOKE = 113;
    /**
     * 催单或者撤单 的类型
     */
    public String HANDLER_REQUERT_REVOKE_TYPE = "催单";
    /**
     * 单号Id
     */
    public static final String HANDLER_REMINDER_AND_REVOKE_ID = "handler_Reminder_and_Revoke_id";
    /**
     * List 的 Id
     */
    public static final String HANDLER_LIST_ID = "HANDLER_LIST_ID";

    /**
     * ListView 适配器
     */
    private EntrustListAdapter entrustListAdapter;

    /**
     * 城市名称
     */
    private String cityname = "beijing";
    /**
     * 列表的ID
     */
    private int listID = -1;
    /**
     * 委托列表
     */
    private EElistBeen eElistBeen;

    /**
     * 开始时间选择器
     */
    private TimePickerView startTimePickView;

    /**
     * 保存开始的时间
     * 用来比较大小
     */
    private Date starData;
    /**
     * 结束时间选择器
     */
    private TimePickerView endTimePickView;
    /**
     * 保存结束的时间
     * 用来比较大小
     */
    private Date endData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrust_list);


        assignViews();

        //获取默认的数据
        getDefaultData();

        initPopupWindow();

        //开始请求全部的数据
        actionClickTopView("", 0);


    }

    /**
     * 获取默认的数据
     */
    private void getDefaultData() {
        //获取用户名
        String userName = SPUtil.getUserName(EntrustListActivity.this);
        if (userName != null) {
            phone = userName;
        } else {
            showToast("你还未登录!不能查看委托列表!");
            finish();
        }

        //获取城市名称
        cityname= FggApplication.getInstance().city.getCityPinYin();
    }


    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        Bundle bundle = msg.getData();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_EE_LIST:// 获取委托列表
                EntrustListActivityTask entrustListActivityTask = new EntrustListActivityTask();
                ResultInfo<EElistBeen> request = entrustListActivityTask.request(phone, startTime, endTime, pageSize, curPage, state);
                resultMsg.obj = request;
                mUiHandler.sendMessage(resultMsg);
                break;
            case HANDLER_REQUERT_REVOKE:// 请求催单或者撤单  HANDLER_REQUERT_REVOKE_TYPE
                if (eElistBeen != null && eElistBeen.getData() != null && eElistBeen.getData().size() > 0) {
                    EntrustListReminderTask entrustListReminderTask = new EntrustListReminderTask();
                    String weiTuoPingGuId = eElistBeen.getData().get(listID).getWeiTuoPingGuId();
                    ResultInfo<EntrustListReminderBeen> requestremin = entrustListReminderTask.request(weiTuoPingGuId, HANDLER_REQUERT_REVOKE_TYPE, cityname);
                    resultMsg.obj = requestremin;
                    mUiHandler.sendMessage(resultMsg);
                }
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        //关闭加载动画
        EntrustListActivity.this.loadingWorker.closeLoading();

        switch (msg.what) {
            case HANDLE_GET_EE_LIST://  获取委托列表
                ResultInfo<EElistBeen> result = (ResultInfo<EElistBeen>) msg.obj;
                if (result.Success) {
                    if (result.Data != null) {
                        eElistBeen = result.Data;

                        setListData(eElistBeen);
                    } else {
                        showToast(result.Message);
                    }
                }
                break;
            case HANDLER_REQUERT_REVOKE://  请求催单或者撤单  HANDLER_REQUERT_REVOKE_TYPE
                ResultInfo<EntrustListReminderBeen> requestremin = (ResultInfo<EntrustListReminderBeen>) msg.obj;
                if (requestremin.Success) {
                    if (requestremin.Data != null) {
                        EntrustListReminderBeen entrustListReminderBeen = requestremin.Data;
                        showToast(HANDLER_REQUERT_REVOKE_TYPE + " 成功");

                        if (eElistBeen != null && eElistBeen.getData() != null && eElistBeen.getData().size() > 0 && entrustListAdapter != null) {
                            if ((listID + 1) < eElistBeen.getData().size()) {
                                if (HANDLER_REQUERT_REVOKE_TYPE.equals("催单")) {
                                    eElistBeen.getData().get(listID).setIsCuidan(1);
                                    entrustListAdapter.notifyDataSetChanged();
                                } else {
                                    eElistBeen.getData().get(listID).setIsChedan(1);
                                    entrustListAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    } else {
                        showToast(requestremin.Message);
                    }
                }
                break;
        }
    }

    /**
     * 设置List的数据
     *
     * @param eElistBeen
     */
    private void setListData(EElistBeen eElistBeen) {
        //设置数据源
        entrustListAdapter.seteElistBeen(eElistBeen);

        entrustListAdapter.notifyDataSetChanged();
    }

    /**
     * 获取委托列表
     */
    private void getEEList() {
        Message get_detail = new Message();
        get_detail.what = HANDLE_GET_EE_LIST;
        mBackgroundHandler.sendMessage(get_detail);
        this.loadingWorker.showLoading("获取记录中");
    }

    /**
     * 请求催单或者撤单
     */
    private void requertRevoke() {
        Message get_detail = new Message();
        get_detail.what = HANDLER_REQUERT_REVOKE;
        mBackgroundHandler.sendMessage(get_detail);
    }


    @Override
    public void showToast(String msg) {
        /**
         * 覆盖掉父类的toast 太长了
         * 使用短toast
         */
        Toast.makeText(EntrustListActivity.this, msg, Toast.LENGTH_SHORT).show();
//        super.showToast(msg);
    }

    /**
     * 初次化 PopupWindow
     */
    private void initPopupWindow() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(EntrustListActivity.this).inflate(R.layout.view_el_show_edit, null);

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

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
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.juxing_zhijiao_white));
    }

    private void showPopuWindow() {
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * 顶部List
     */
    private List<TextView> tvView = new ArrayList<TextView>();
    /**
     * 顶部List线
     */
    private List<View> lineView = new ArrayList<View>();


    private ImageButton navBtnBack;
    private TextView navTxtTitle;
    private TextView navTxtOp;

    private RelativeLayout elTop1;
    private TextView elTop1Value;
    private View elTop1Line;
    private RelativeLayout elTop2;
    private TextView elTop2Value;
    private View elTop2Line;
    private RelativeLayout elTop3;
    private TextView elTop3Value;
    private View elTop3Line;
    private RelativeLayout elTop4;
    private TextView elTop4Value;
    private View elTop4Line;
    private RelativeLayout elTop5;
    private TextView elTop5Value;
    private View elTop5Line;
    private RelativeLayout elTimeRelativeLayoutStart;
    private TextView elTimeValueStart;
    private RelativeLayout elTimeRelativeLayoutEnd;
    private TextView elTimeValueEnd;
    private Button elBtSearch;
    private ListView elListview;

    /**
     * 初始化界面
     */
    private void assignViews() {

        //头部
        navBtnBack = (ImageButton) findViewById(R.id.nav_btn_back);
        navTxtTitle = (TextView) findViewById(R.id.nav_txt_title);
        navTxtOp = (TextView) findViewById(R.id.nav_txt_op);

        elTop1 = (RelativeLayout) findViewById(R.id.el_top_1);
        elTop1Value = (TextView) findViewById(R.id.el_top_1_value);
        elTop1Line = findViewById(R.id.el_top_1_line);
        elTop2 = (RelativeLayout) findViewById(R.id.el_top_2);
        elTop2Value = (TextView) findViewById(R.id.el_top_2_value);
        elTop2Line = findViewById(R.id.el_top_2_line);
        elTop3 = (RelativeLayout) findViewById(R.id.el_top_3);
        elTop3Value = (TextView) findViewById(R.id.el_top_3_value);
        elTop3Line = findViewById(R.id.el_top_3_line);
        elTop4 = (RelativeLayout) findViewById(R.id.el_top_4);
        elTop4Value = (TextView) findViewById(R.id.el_top_4_value);
        elTop4Line = findViewById(R.id.el_top_4_line);
        elTop5 = (RelativeLayout) findViewById(R.id.el_top_5);
        elTop5Value = (TextView) findViewById(R.id.el_top_5_value);
        elTop5Line = findViewById(R.id.el_top_5_line);
        elTimeRelativeLayoutStart = (RelativeLayout) findViewById(R.id.el_time_relativeLayout_start);
        elTimeValueStart = (TextView) findViewById(R.id.el_time_value_start);
        elTimeRelativeLayoutEnd = (RelativeLayout) findViewById(R.id.el_time_relativeLayout_end);
        elTimeValueEnd = (TextView) findViewById(R.id.el_time_value_end);
        elBtSearch = (Button) findViewById(R.id.el_bt_search);
        elListview = (ListView) findViewById(R.id.el_listview);

        //设置标题
        navTxtTitle.setText("委托记录");

        //给一个View  展示popuWindow
        view = elTop1;

        //返回
        navBtnBack.setOnClickListener(myBaseOnClickListener);
        //搜索
        elBtSearch.setOnClickListener(myBaseOnClickListener);

        //适配器
        entrustListAdapter = new EntrustListAdapter(EntrustListActivity.this, myHandler);
        elListview.setAdapter(entrustListAdapter);

        //填充数据
        tvView.add(elTop1Value);
        tvView.add(elTop2Value);
        tvView.add(elTop3Value);
        tvView.add(elTop4Value);
        tvView.add(elTop5Value);
        //填充数据
        lineView.add(elTop1Line);
        lineView.add(elTop2Line);
        lineView.add(elTop3Line);
        lineView.add(elTop4Line);
        lineView.add(elTop5Line);

        //点击事件
        elTop1.setOnClickListener(myBaseOnClickListener);
        elTop2.setOnClickListener(myBaseOnClickListener);
        elTop3.setOnClickListener(myBaseOnClickListener);
        elTop4.setOnClickListener(myBaseOnClickListener);
        elTop5.setOnClickListener(myBaseOnClickListener);


        //开始时间选择器
        intiStartTimePick();
        elTimeRelativeLayoutStart.setOnClickListener(myBaseOnClickListener);

        //结束时间选择器
        intiEndTimePick();
        elTimeRelativeLayoutEnd.setOnClickListener(myBaseOnClickListener);
    }


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
                case R.id.el_bt_search: //条件检索
                    actionCLickSearch();
                    break;
                //切换状态 //委托状态:0.待支付,1.未受理,2.受理中,3.已完成,4.已撤单
                case R.id.el_top_1: //全部
                    actionClickTopView("", 0);
                    break;
                case R.id.el_top_2: //已经完成
                    actionClickTopView("3", 1);
                    break;
                case R.id.el_top_3: //未受理
                    actionClickTopView("1", 2);
                    break;
                case R.id.el_top_4: //受理中
                    actionClickTopView("2", 3);
                    break;
                case R.id.el_top_5: //已撤单
                    actionClickTopView("4", 4);
                    break;
                case R.id.el_time_relativeLayout_start: //开始时间选择器
                    startTimePickView.show();
                    break;
                case R.id.el_time_relativeLayout_end: //结束时间选择器
                    endTimePickView.show();
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 点击搜索时间内的订单
     */
    private void actionCLickSearch() {
        //开始时间不能为空
        if (elTimeValueStart.getText() == null || starData == null) {
            showToast("请先设置开始时间");
            return;
        }
        //结束时间不能为空
        if (elTimeValueEnd.getText() == null || endData == null) {
            showToast("请设置结束时间");
            return;
        }
        //开始时间在结束时间的后面
        if (starData.after(endData)) {
            showToast("结束时间必须大于开始时间");
            return;
        }

        //设置时间
        startTime = elTimeValueStart.getText().toString().trim();
        endTime = elTimeValueEnd.getText().toString().trim();

        //请求数据
        getEEList();

        //清空数据
        eElistBeen = null;
        entrustListAdapter.notifyDataSetChanged();
    }

    /**
     * 点击切换状态
     *
     * @param types //状态
     * @param index //点击的的下标
     */
    private void actionClickTopView(String types, int index) {
        //改变选择文字的状态
        for (int i = 0; i < tvView.size(); i++) {
            if (i == index) {
                tvView.get(i).setTextColor(getResources().getColor(R.color.title_blue)); //改变选中的文字
                lineView.get(i).setVisibility(View.VISIBLE); //改变文字下面的线
            } else {
                lineView.get(i).setVisibility(View.INVISIBLE);
                tvView.get(i).setTextColor(getResources().getColor(R.color.black));
            }
        }

        //清空时间限制
        startTime = "";
        endTime = "";

        //切换状态
        state = types;
        //请求网络
        getEEList();

        //清空数据
        eElistBeen = null;
        entrustListAdapter.notifyDataSetChanged();
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case HANDLER_SHOW_POPUPWINDOW:// 点击修改
                    showPopuWindow();
                    break;
                case HANDLER_REMINDER:// 催单
                    HANDLER_REQUERT_REVOKE_TYPE = "催单";
                    listID = bundle.getInt(HANDLER_LIST_ID);
                    requertRevoke();

                    //设置为正在催单状态
                    eElistBeen.getData().get(listID).setIsCuidan(3);
                    entrustListAdapter.notifyDataSetChanged();
                    break;
                case HANDLER_REVOKE:// 撤单
                    HANDLER_REQUERT_REVOKE_TYPE = "撤单";
                    listID = bundle.getInt(HANDLER_LIST_ID);
                    requertRevoke();

                    //设置为正在撤单状态
                    eElistBeen.getData().get(listID).setIsChedan(3);
                    entrustListAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


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
     * 开始时间
     */
    private void intiStartTimePick() {
        startTimePickView = new TimePickerView(EntrustListActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
        startTimePickView.setTime(new Date());
        startTimePickView.setCancelable(true);
        //时间选择后回调
        startTimePickView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                elTimeValueStart.setText(getTime(date));
                starData = date;
            }
        });
    }

    /**
     * 结束时间
     */
    private void intiEndTimePick() {
        endTimePickView = new TimePickerView(EntrustListActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
        endTimePickView.setTime(new Date());
        endTimePickView.setCancelable(true);
        //时间选择后回调
        endTimePickView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                elTimeValueEnd.setText(getTime(date));
                endData = date;
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
}
