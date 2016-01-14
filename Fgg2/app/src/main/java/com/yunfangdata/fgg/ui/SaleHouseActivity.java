package com.yunfangdata.fgg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.adapter.BuyHouseSearchAdapter;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.helper.IntentHelper;
import com.yunfangdata.fgg.http.task.BuyHouseReturnTask;
import com.yunfangdata.fgg.http.task.BuyHouseSearchTask;
import com.yunfangdata.fgg.http.task.BuyHouseSpecialFactorsTask;
import com.yunfangdata.fgg.model.BuyHouseBeen;
import com.yunfangdata.fgg.model.BuyHouseReturnBeen;
import com.yunfangdata.fgg.model.GetAppraisementBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.SpecialFactorsBeen;
import com.yunfangdata.fgg.utils.MyPopupWindow;
import com.yunfangdata.fgg.utils.ZLog;
import com.yunfangdata.fgg.view.MyScrollView;
import com.yunfangdata.fgg.view.ScrollViewListener;
import com.yunfangdata.fgg.viewmodel.BuyAndSaleViewModel;

import java.util.ArrayList;

public class SaleHouseActivity extends NewBaseActivity {
    private static final String TAG = "BuyHouseActivity";
    private static final int LOGLEVEL = 1;
    /**
     * /**
     * 小区名字
     */
    private String residentialAreaName = null;
    /**
     * 默认的城市名字
     */
    private String cityName = "beijing";
    /**
     * 默认的城市名字中文
     */
    private String cityNameCH = "北京";

    /**
     * 默认的小区id
     */
    private int residentialAreaID = 12944;

    /**
     * 默认的检索条目
     */
    private final int ItemCount = 10;

    /**
     * 小区名字的模糊搜索
     */
    private final int HANDLE_GET_SEARCH_NAME = 120;

    /**
     * 获取我要估值的返回值
     */
    private final int HANDLE_GET_BUY_HOUSE_RETURN = 121;

    /**
     * 传递输入框中输入的文字
     */
    private final String HANDLE_GET_INPUT_STRING_NAME = "HANDLE_GET_INPUT_STRING_NAME";
    /**
     * 传递用户输入的买房信息
     */
    private final String HANDLE_GET_BUY_HOUSE_RETURN_VALUE = "HANDLE_GET_BUY_HOUSE_RETURN_VALUE";

    /**
     * 查询特殊因素的买房名字
     */
    private final String HANDLE_GET_SPECIAL_FACTORY_VALUE = "HANDLE_GET_SPECIAL_FACTORY_VALUE";

    /**
     * 获取特殊因素的返回值
     */
    private final int HANDLE_GET_SPECIAL_FACTORY = 122;

    /**
     * popupWindow的适配器
     */
    private BuyHouseSearchAdapter buyHouseSearchAdapter;
    /**
     * 展示模糊搜索结果的PopupWindow
     */
    private PopupWindow popupWindow;

    /**
     * 用于给popuwindow点击的LIst
     */
    private BuyHouseBeen mbuyHouseBeen;

    /**
     * 是否搜索并且显示PopoWindow
     */
    private boolean isShowPopoWindow = false;

    /**
     * 输入框上一次的文字
     * 用于判断输入框是否改变了文字
     */
    private String lastInputName;
    /**
     * 居室类型选择器
     */
    private OptionsPickerView houseTypePickr;
    /**
     * 房屋朝向选择器
     */
    private OptionsPickerView towardTypePickr;

    /**
     * 朝向类型数据源
     */
    private String toward[] = BuyAndSaleViewModel.toward;
    /**
     * 居室列表数据源
     */
    private String houseTypes[] = BuyAndSaleViewModel.houseTypes;
    /**
     * 居室列表数据源对应的int值
     */
    private int houseTypesINT[] = BuyAndSaleViewModel.houseTypesINT;

    /**
     * 当前选中的对应的int值下标值
     */
    private int nowChoesehouseTypesINT = 0;

    /**
     * 所在楼层的取值区间
     * 第一最小--第二最大
     */
    private int floorminAndMax[] = BuyAndSaleViewModel.floorminAndMax;

    /**
     * 总楼层数的取值区间
     * 第一最小--第二最大
     */
    private int totalfloorminAndMax[] = BuyAndSaleViewModel.totalfloorminAndMax;
    /**
     * 建成年代的取值区间
     * 第一最小--第二最大
     */
    private int builtTimeminAndMax[] = BuyAndSaleViewModel.builtTimeminAndMax;

    /**
     * 预购总价的取值区间
     * 第一最小--第二最大
     */
    private int purchasePriceminAndMax[] = BuyAndSaleViewModel.purchasePriceminAndMax;
    /**
     * 建筑面积的取值区间
     * 第一最小--第二最大
     */
    private float areaminAndMax[] = BuyAndSaleViewModel.areaminAndMax;
    /**
     * 拼接出来的估值网页
     */
    private String webViewURL;


    /**
     * 用来提交的表单
     */
    private GetAppraisementBeen getAppraisementBeen;
    /**
     * 警告的PopupWindow
     */
    private MyPopupWindow myPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_house);
        //初始化数据
        initData();

        assignViews();

        getIntentData();

        initPopupWindow();

        initHouseTypeChoaese();

        initTowardChoaese();
        getDefaultData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //提交表单
        getAppraisementBeen = new GetAppraisementBeen();

        /**
         * 初始化popupWindow
         */
        myPopupWindow = new MyPopupWindow(SaleHouseActivity.this);
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
        ZLog.Zlogi("(extras != null)" + (extras != null), TAG, LOGLEVEL);
        if (extras != null) {
            //获取传过来的小区id
            int id = extras.getInt(Constant.KEY_INTENT_TO_HOUSE_DETAIL_RESIDENTIALAREAID);
            //获取传过来的小区名称
            String name = extras.getString(Constant.KEY_INTENT_TO_BUY_RESIDENTIALAREANAME);
            //获取传递过来的城市
            String cityn = extras.getString(Constant.KEY_INTENT_TO_HOUSE_DETAIL_CITYNAME);
            if (name != null && !name.isEmpty()) {
                residentialAreaName = name;
                residentialAreaID = id;
                lastInputName = name;
                //顺便设置一下小区名字
                buyResidentialName.setText(residentialAreaName);
            }

            //城市名称单独判断
            if (cityn != null && !cityn.isEmpty()) {
//                cityName = cityn;
            }

        }
    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        Bundle bundle = msg.getData();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_SEARCH_NAME:// 获取模糊搜索
                //获取传递过来的名字
                String inputName = bundle.getString(HANDLE_GET_INPUT_STRING_NAME);
                BuyHouseSearchTask buyHouseSearchTask = new BuyHouseSearchTask();
                ResultInfo<BuyHouseBeen> resultInfo = buyHouseSearchTask.request(cityName, inputName, ItemCount);
                resultMsg.obj = resultInfo;
                mUiHandler.sendMessage(resultMsg);
                break;
            case HANDLE_GET_BUY_HOUSE_RETURN:// 获取我要估值的返回
                GetAppraisementBeen getAppraisementBeen = (GetAppraisementBeen) bundle.getSerializable(HANDLE_GET_BUY_HOUSE_RETURN_VALUE);
                BuyHouseReturnTask buyHouseReturnTask = new BuyHouseReturnTask();
                ResultInfo<BuyHouseReturnBeen> returnResultInfo = buyHouseReturnTask.request(getAppraisementBeen);
                resultMsg.obj = returnResultInfo;
                mUiHandler.sendMessage(resultMsg);
                break;
            case HANDLE_GET_SPECIAL_FACTORY:// 获取特殊因素
                //获取传递过来的名字
                String queryName = bundle.getString(HANDLE_GET_SPECIAL_FACTORY_VALUE);
                BuyHouseSpecialFactorsTask buyHouseSpecialFactorsTask = new BuyHouseSpecialFactorsTask();
                ResultInfo<SpecialFactorsBeen> requestsn = buyHouseSpecialFactorsTask.request(cityName, queryName);
                resultMsg.obj = requestsn;
                mUiHandler.sendMessage(resultMsg);
                break;

        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_SEARCH_NAME:// 获取模糊搜索
                ResultInfo<BuyHouseBeen> result = (ResultInfo<BuyHouseBeen>) msg.obj;
                if (result.Success) {
                    if (result.Data != null) {
                        BuyHouseBeen buyHouseBeen = result.Data;
                        //判断能否显示结果
                        if (isShowPopoWindow) {
                            showPopupWindow(buyHouseBeen);
                        }
                    } else {
                        showToast(result.Message);
                    }
                }
                break;
            case HANDLE_GET_BUY_HOUSE_RETURN:// 获取我要估值的返回
                //不管成功失败,,还原按钮的点击事件
                buyBtValuation.setClickable(true);
                buyBtValuation.setText(getString(R.string.buy_bt_valuation));

                ResultInfo<BuyHouseReturnBeen> result2 = (ResultInfo<BuyHouseReturnBeen>) msg.obj;
                if (result2.Success) {
                    if (result2.Data != null) {
                        BuyHouseReturnBeen buyHouseReturnBeen = result2.Data;
                        intentToWebView(buyHouseReturnBeen);
                    } else {
                        showToast(result2.Message);
                    }
                } else {
                    showToast(result2.Message);
                }
                break;

            case HANDLE_GET_SPECIAL_FACTORY:// 获取特殊因素
                ResultInfo<SpecialFactorsBeen> result3 = (ResultInfo<SpecialFactorsBeen>) msg.obj;
                if (result3.Success) {
                    if (result3.Data != null) {
                        SpecialFactorsBeen specialFactorsBeen = result3.Data;
                        showSpecialView(specialFactorsBeen);

                    } else {
                        showToast(result3.Message);
                    }
                } else {
                    showToast(result3.Message);
                }
                break;


        }
    }


    /**
     * 判断是否
     * 展示特殊因素
     *
     * @param specialFactorsBeen
     */
    private void showSpecialView(SpecialFactorsBeen specialFactorsBeen) {
        if (specialFactorsBeen.getData() != null && specialFactorsBeen.getData().size() > 0) {
            if (!isEdittextNotNull(saleResidentialName)) {
                //设置特殊因素
                saleResidentialName.setText(specialFactorsBeen.getData().get(0));
            }
        }
    }

    /**
     * 跳转到webView
     *
     * @param buyHouseReturnBeen
     */
    private void intentToWebView(BuyHouseReturnBeen buyHouseReturnBeen) {
        if (buyHouseReturnBeen.getData() != null && buyHouseReturnBeen.getData().getGuid() != null) {
            BuyHouseReturnBeen.DataEntity buyHouseReturnBeenData = buyHouseReturnBeen.getData();
            if (buyHouseReturnBeenData.getRent() == 0 && buyHouseReturnBeenData.getPrice() == 0) {

                showToast("无法找到该小区,请从小区下拉框中选择小区名称!");
                return;
            }


            String json = JSON.toJSONString(buyHouseReturnBeenData);
            //拼接网页
            String nullurl = "houseType=住宅&" + "specialFactor=&" + "residentialAreaId=&"
                    + "city_Ch=" + cityNameCH + "&" + "financialResultType=sell&";
            webViewURL = webViewURL + nullurl + "appraisementResultInfo=" + json;

            IntentHelper.intentToValuationWebView(SaleHouseActivity.this, webViewURL,Constant.TYPE_SALE_HOUSE);


        }
    }

    /**
     * 判断值是否在区间内
     *
     * @param floor          //要判断的值
     * @param floorminAndMax //区间数组  第一个为最小值,,第二个为最大值
     * @return //满足为true  否则false
     */
    private boolean isInInterval(Integer floor, int[] floorminAndMax) {
        if (floorminAndMax != null && floorminAndMax.length == 2) {
            return (floor >= floorminAndMax[0] && floor <= floorminAndMax[1]);
        } else {
            return false;
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
     * 点击获取
     */
    private void actionGetValue() {
        GetAppraisementBeen getAppraisementBeen = new GetAppraisementBeen();
        //拼接网页
        webViewURL = Constant.HTTP_ADRESS + ":" + Constant.HTTP_POINT + "/" + Constant.HTTP_FINANCIALVALUATION;

        //城市名称
        if (cityName != null && !cityName.isEmpty()) {
            getAppraisementBeen.setCity(cityName);

            //拼接网页
            webViewURL = webViewURL + "city=" + cityName + "&";

        } else {
            showToast(getString(R.string.buy_not_city));
            return;
        }

        //小区名称
        String residentialAreaName = null;
        if (isEdittextNotNull(buyResidentialName)) {
            residentialAreaName = buyResidentialName.getText().toString().trim();
            getAppraisementBeen.setResidentialAreaName(residentialAreaName);
        } else {
            showToast(getString(R.string.buy_residential_name_warning_2));
            buyResidentialNameWarning.setVisibility(View.VISIBLE);
            return;
        }
        //拼接网页 - 小区名称
        webViewURL = webViewURL + "residentialAreaName=" + residentialAreaName + "&";

        //建筑面积
        String area = null;
        if (isEdittextNotNull(buyAreaValue) && ifCanBuildAreaText(buyAreaValue.getText().toString())) {
            area = buyAreaValue.getText().toString().trim();
            getAppraisementBeen.setArea(Float.valueOf(area));

        } else {
            showToast("建筑面积应在"+ areaminAndMax[0] + "~" + areaminAndMax[1] + "平方米 之间");
            buyAreaWarning.setVisibility(View.VISIBLE);
            return;
        }
        //拼接网页 建筑面积
        webViewURL = webViewURL + "area=" + area + "&";


        //预售卖总价
        String buyPrice = "";
        if (isEdittextNotNull(buyPriceValue)) {
            String sepcial = buyPriceValue.getText().toString().trim();
            Integer floor = Integer.valueOf(sepcial);
            if (isInInterval(floor, purchasePriceminAndMax)) {
                getAppraisementBeen.setSpecialFactor(sepcial);
                buyPrice = sepcial;
            } else {
                showToast("预售总价应在"+ purchasePriceminAndMax[0] + "~" + purchasePriceminAndMax[1] + "万元之间");
                buyPriceWarning.setVisibility(View.VISIBLE);
                return;
            }
        } else {
            showToast("预售总价不能为空");
            buyPriceWarning.setVisibility(View.VISIBLE);
            return;
        }
        //拼接网页 预售卖总价
        webViewURL = webViewURL + "salePrice=" + buyPrice + "&";

        //居室类型
        getAppraisementBeen.setUnitType(houseTypesINT[nowChoesehouseTypesINT] + "");

        //拼接网页 居室类型
        webViewURL = webViewURL + "unitType=" + getAppraisementBeen.getUnitType() + "&";


        //楼栋
        String buildNumber = "";
        if (isEdittextNotNull(buyBuildNumberValue)) {
            buildNumber = buyBuildNumberValue.getText().toString().trim();
            getAppraisementBeen.setBuildNumber(buildNumber);

        }
        //拼接网页 楼栋
        webViewURL = webViewURL + "buildNumber=" + buildNumber + "&";

        //门牌号
        String houseNumber = "";
        if (isEdittextNotNull(buyHouseNumberValue)) {
            houseNumber = buyHouseNumberValue.getText().toString().trim();
            getAppraisementBeen.setHouseNumber(houseNumber);
        }
        //拼接网页门牌号
        webViewURL = webViewURL + "house_number=" + houseNumber + "&";

        //朝向
        String towards = "";
        if (!buyTowardValue.getText().toString().equals(toward[0])) {
            towards = buyTowardValue.getText().toString().trim();
            getAppraisementBeen.setToward(towards);
        }

        //拼接网页 朝向
        webViewURL = webViewURL + "toward=" + towards + "&";

        //所在楼层
        String floorString = "";
        if (isEdittextNotNull(buyFloorValue)) {
            floorString = buyFloorValue.getText().toString().trim();
            Integer floor = Integer.valueOf(floorString);
            if (isInInterval(floor, floorminAndMax)) {
                getAppraisementBeen.setFloor(floor);

            } else {
                showToast("所在楼层应在"+ floorminAndMax[0] + "~" + floorminAndMax[1] + "之间\n并且应该小于或等于总楼层");

                return;
            }
        }
        //拼接网页 所在楼层
        webViewURL = webViewURL + "floor=" + floorString + "&";


        //总楼层数
        String totalfloorString = "";
        if (isEdittextNotNull(buyTotalfloorValue)) {
            totalfloorString = buyTotalfloorValue.getText().toString().trim();
            Integer totalfloor = Integer.valueOf(totalfloorString);
            if (isInInterval(totalfloor, totalfloorminAndMax)) {
                if (isEdittextNotNull(buyFloorValue)) {
                    Integer floor = Integer.valueOf(buyFloorValue.getText().toString());
                    if (totalfloor < floor) {
                        showToast(getString(R.string.buy_totalfloor_e));
                        return;
                    }
                }
                getAppraisementBeen.setFloor(totalfloor);
            } else {
                showToast("总楼层应在"+ totalfloorminAndMax[0] + "~" + totalfloorminAndMax[1] + "之间");
                return;
            }
        }
        //拼接网页 总楼层数
        webViewURL = webViewURL + "totalfloor=" + totalfloorString + "&";

        //建成年代
        String builtTimeString = "";
        if (isEdittextNotNull(buyBuiltTimeValue)) {
            builtTimeString = buyBuiltTimeValue.getText().toString().trim();
            Integer builtTime = Integer.valueOf(builtTimeString);
            if (isInInterval(builtTime, builtTimeminAndMax)) {
                getAppraisementBeen.setFloor(builtTime);

            } else {
                showToast("建成年代应在"+ builtTimeminAndMax[0] + "~" + builtTimeminAndMax[1] + "之间");
                return;
            }
        }

        //拼接网页 建成年代
        webViewURL = webViewURL + "builtTime=" + builtTimeString + "&";

        //进行估值
        getBuyHouseResult(getAppraisementBeen);

    }

    private ImageView buyButtonBack;
    private TextView buyTitle;
    private MyScrollView buyScrollView;
    private AutoCompleteTextView buyResidentialName;
    private ImageView buyResidentialNameWarning;
    private TextView buyArea;
    private EditText buyAreaValue;
    private ImageView buyAreaWarning;
    private RelativeLayout buyHouseTypeRelativeLayout;
    private TextView buyHouseType;
    private TextView buyHouseTypeValue;
    private EditText buyPriceValue;
    private ImageView buyPriceWarning;
    private EditText buyBuildNumberValue;
    private ImageView buyBuildNumberWarning;
    private EditText buyHouseNumberValue;
    private ImageView buyHouseNumberWarning;
    private RelativeLayout buyTowardRelativeLayout;
    private TextView buyTowardValue;
    private EditText buyFloorValue;
    private ImageView buyFloorWarning;
    private EditText buyTotalfloorValue;
    private ImageView buyTotalfloorWarning;
    private EditText buyBuiltTimeValue;
    private ImageView buyBuiltTimeWarning;
    private TextView saleSpecialFactor;
    private EditText saleResidentialName;
    private Button buyBtValuation;

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

        buyScrollView = (MyScrollView) findViewById(R.id.buy_scroll_view);
        buyResidentialName = (AutoCompleteTextView) findViewById(R.id.buy_residential_name);
        buyResidentialNameWarning = (ImageView) findViewById(R.id.buy_residential_name_warning);
        buyArea = (TextView) findViewById(R.id.buy_area);
        buyAreaValue = (EditText) findViewById(R.id.buy_area_value);
        buyAreaWarning = (ImageView) findViewById(R.id.buy_area_warning);
        buyHouseTypeRelativeLayout = (RelativeLayout) findViewById(R.id.buy_house_type_relativeLayout);
        buyHouseType = (TextView) findViewById(R.id.buy_house_type);
        buyHouseTypeValue = (TextView) findViewById(R.id.buy_house_type_value);
        buyPriceValue = (EditText) findViewById(R.id.buy_price_value);
        buyPriceWarning = (ImageView) findViewById(R.id.buy_price_warning);
        buyBuildNumberValue = (EditText) findViewById(R.id.buy_build_number_value);
        buyBuildNumberWarning = (ImageView) findViewById(R.id.buy_build_number_warning);
        buyHouseNumberValue = (EditText) findViewById(R.id.buy_house_number_value);
        buyHouseNumberWarning = (ImageView) findViewById(R.id.buy_house_number_warning);
        buyTowardRelativeLayout = (RelativeLayout) findViewById(R.id.buy_toward_relativeLayout);
        buyTowardValue = (TextView) findViewById(R.id.buy_toward_value);
        buyFloorValue = (EditText) findViewById(R.id.buy_floor_value);
        buyFloorWarning = (ImageView) findViewById(R.id.buy_floor_warning);
        buyTotalfloorValue = (EditText) findViewById(R.id.buy_totalfloor_value);
        buyTotalfloorWarning = (ImageView) findViewById(R.id.buy_totalfloor_warning);
        buyBuiltTimeValue = (EditText) findViewById(R.id.buy_built_time_value);
        buyBuiltTimeWarning = (ImageView) findViewById(R.id.buy_built_time_warning);
        saleSpecialFactor = (TextView) findViewById(R.id.sale_special_factor);
        saleResidentialName = (EditText) findViewById(R.id.sale_residential_name);
        buyBtValuation = (Button) findViewById(R.id.buy_bt_valuation);

        //基础监听
        baseClickListener();

        //警告图标监听
        warningClickListener();

        //建成年代的时间设置
        buyBuiltTimeValue.setHint("如: " + builtTimeminAndMax[1]);
    }
    /**
     * 警告图标监听
     */
    private void warningClickListener() {

        //小区名字
        buyResidentialNameWarning.setOnClickListener(myWarningOnClickListener);
        //面积
        buyAreaWarning.setOnClickListener(myWarningOnClickListener);
        //楼栋
        buyBuildNumberWarning.setOnClickListener(myWarningOnClickListener);
        //门牌号
        buyHouseNumberWarning.setOnClickListener(myWarningOnClickListener);
        //所在楼层
        buyFloorWarning.setOnClickListener(myWarningOnClickListener);
        //总楼层
        buyTotalfloorWarning.setOnClickListener(myWarningOnClickListener);
        //建成年代
        buyBuiltTimeWarning.setOnClickListener(myWarningOnClickListener);
        //预购总价
        buyPriceWarning.setOnClickListener(myWarningOnClickListener);

    }
    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myWarningOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String string = null;
            switch (v.getId()) {
                case R.id.buy_residential_name_warning: //小区名字
                    string = getString(R.string.buy_residential_name_warning_2);
                    break;
                case R.id.buy_area_warning: //面积
                    string = "建筑面积应在"+ areaminAndMax[0] + "~" + areaminAndMax[1] + "平方米 之间";
                    break;
                case R.id.buy_build_number_warning: //楼栋
                    string = "不能为空";
                    break;
                case R.id.buy_house_number_warning: //门牌号
                    string = "不能为空";
                    break;
                case R.id.buy_floor_warning: //所在楼层
                    string = "所在楼层应在"+ floorminAndMax[0] + "~" + floorminAndMax[1] + "之间\n并且应该小于或等于总楼层";
                    break;
                case R.id.buy_totalfloor_warning: //总楼层
                    string = "总楼层应在"+ totalfloorminAndMax[0] + "~" + totalfloorminAndMax[1] + "之间";
                    break;
                case R.id.buy_built_time_warning: //建成年代
                    string = "建成年代应在"+ builtTimeminAndMax[0] + "~" + builtTimeminAndMax[1] + "之间";
                    break;
                case R.id.buy_price_warning: //预售总价
                    string = "预售总价应在"+ purchasePriceminAndMax[0] + "~" + purchasePriceminAndMax[1] + "万元之间";
                    break;
                default:
                    break;
            }
            if (string!= null){
                myPopupWindow.showPopup(v,string);
            }
        }
    };

    /**
     * 基础监听
     */
    private void baseClickListener() {

        //设置标题
        navTxtTitle.setText(R.string.sale_title);

        //返回键
        navBtnBack.setOnClickListener(myOnClickListener);

        //点击估值
        buyBtValuation.setOnClickListener(myOnClickListener);

        //居室类型
        buyHouseTypeRelativeLayout.setOnClickListener(myOnClickListener);

        //房屋朝向
        buyTowardRelativeLayout.setOnClickListener(myOnClickListener);

        //小区名字
        buyResidentialName.addTextChangedListener(mySearchTextListener);
        buyResidentialName.setOnFocusChangeListener(myOnFocusChangeListener);

        //建筑面积
        buyAreaValue.addTextChangedListener(myBuildAreaTextListener);

        //所在楼层的监听
        buyFloorValue.addTextChangedListener(myFloorTextListener);

        //总楼层数的监听
        buyTotalfloorValue.addTextChangedListener(myTotalfloorTextListener);

        //建筑时间的监听
        buyBuiltTimeValue.addTextChangedListener(myBuiltTimeTextListener);

        //预购总价的监听
        buyPriceValue.addTextChangedListener(myPriceValueTextListener);

        //监听滚动事件
        buyScrollView.setScrollViewListener(myScrollViewListener);
    }

    /**
     * 监听滚动事件
     */
    private ScrollViewListener myScrollViewListener = new ScrollViewListener() {
        @Override
        public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {

            ZLog.Zlogi("x = " + x + "y = " + y, TAG, LOGLEVEL);
            ZLog.Zlogi("oldx = " + oldx + "oldy = " + oldy, TAG, LOGLEVEL);

            //该方法调用的时候..证明滚动了,,去掉popuwindow
            destroyPopoWindow();
        }
    };
    /**
     * 监听建筑时间
     */
    private TextWatcher myPriceValueTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            if (floors != null && floors.length() > 0) {
                Integer floor = Integer.valueOf(floors);
                if (isInInterval(floor, purchasePriceminAndMax)) {
                    buyPriceWarning.setVisibility(View.INVISIBLE);
                } else {
                    buyPriceWarning.setVisibility(View.VISIBLE);
                }
            } else {
                //不显示警告图标
                buyPriceWarning.setVisibility(View.INVISIBLE);
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
     * 监听建筑时间
     */
    private TextWatcher myBuiltTimeTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            if (floors != null && floors.length() > 0) {
                Integer floor = Integer.valueOf(floors);
                if (isInInterval(floor, builtTimeminAndMax)) {
                    buyBuiltTimeWarning.setVisibility(View.INVISIBLE);
                } else {
                    buyBuiltTimeWarning.setVisibility(View.VISIBLE);
                }
            } else {
                //不显示警告图标
                buyBuiltTimeWarning.setVisibility(View.INVISIBLE);
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
     * 监听总楼层数
     */
    private TextWatcher myTotalfloorTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            if (floors != null && floors.length() > 0) {
                Integer floor = Integer.valueOf(floors);
                if (isInInterval(floor, totalfloorminAndMax)) {
                    buyTotalfloorWarning.setVisibility(View.INVISIBLE);
                } else {
                    buyTotalfloorWarning.setVisibility(View.VISIBLE);
                }
            } else {
                //不显示警告图标
                buyTotalfloorWarning.setVisibility(View.INVISIBLE);
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
     * 监听所在楼层
     */
    private TextWatcher myFloorTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String floors = s.toString();
            if (floors != null && floors.length() > 0) {
                Integer floor = Integer.valueOf(floors);
                if (isInInterval(floor, floorminAndMax)) {
                    buyFloorWarning.setVisibility(View.INVISIBLE);
                } else {
                    buyFloorWarning.setVisibility(View.VISIBLE);
                }
            } else {
                //不显示警告图标
                buyFloorWarning.setVisibility(View.INVISIBLE);
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
     * 监听小区名称的输入框焦点获取情况,以便判断是否显示popupwindow
     */
    private View.OnFocusChangeListener myOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                isShowPopoWindow = true;
            } else {
                destroyPopoWindow();
                requestSpeciialFactores();
            }
        }
    };


    /**
     * 当小区名称失去焦点的时候请求
     * 是否包含特殊因素
     */
    private void requestSpeciialFactores() {
        if (isEdittextNotNull(buyResidentialName)) {
            Message get_detail = new Message();
            get_detail.what = HANDLE_GET_SPECIAL_FACTORY;
            Bundle data = new Bundle();
            data.putSerializable(HANDLE_GET_SPECIAL_FACTORY_VALUE, buyResidentialName.getText().toString().trim());
            get_detail.setData(data);
            mBackgroundHandler.sendMessage(get_detail);
        }


    }

    /**
     * 监听输入框
     */
    private TextWatcher mySearchTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String name = s.toString();
            if (name != null && name.length() > 0) {
                //判断上一次输入的文字和现在输入的是否不同,,如果不同则给予搜索
//                ZLog.Zlogi("lastInputName = " + lastInputName + "name = " + name + "lastInputName.equals(name)" + lastInputName.equals(name), TAG, LOGLEVEL);
                if (lastInputName != null && !lastInputName.isEmpty() && !lastInputName.equals(name)) {
                    isShowPopoWindow = true;
                }

                //判断能否进行搜索
                if (isShowPopoWindow) {
                    getSearchResult(name);
                }
                lastInputName = name;
                //是否显示警告图标
                buyResidentialNameWarning.setVisibility(View.INVISIBLE);
            } else {
                //是否显示警告图标
                buyResidentialNameWarning.setVisibility(View.VISIBLE);

                destroyPopoWindow();
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
            if (areaf >= 1.00 && areaf <= 9989.99) {
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

    /**
     * 监听建筑面积输入框
     */
    private TextWatcher myBuildAreaTextListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //不显示警告图标
            buyAreaWarning.setVisibility(View.VISIBLE);

            String area = s.toString();
            if (ifCanBuildAreaText(area)) {
                //显示警告图标
                buyAreaWarning.setVisibility(View.INVISIBLE);
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
     * 搜索房子名称列表
     */
    private void getSearchResult(String name) {
        Message get_detail = new Message();
        get_detail.what = HANDLE_GET_SEARCH_NAME;
        Bundle data = new Bundle();
        data.putString(HANDLE_GET_INPUT_STRING_NAME, name);
        get_detail.setData(data);
        mBackgroundHandler.sendMessage(get_detail);
    }

    /**
     * 点击我要买房
     */
    private void getBuyHouseResult(GetAppraisementBeen getAppraisementBeen) {
        //去掉按钮的点击事件
        buyBtValuation.setClickable(false);
        //改变按钮的文字
        buyBtValuation.setText(getString(R.string.buy_loading));

        Message get_detail = new Message();
        get_detail.what = HANDLE_GET_BUY_HOUSE_RETURN;
        Bundle data = new Bundle();
        data.putSerializable(HANDLE_GET_BUY_HOUSE_RETURN_VALUE, getAppraisementBeen);
        get_detail.setData(data);
        mBackgroundHandler.sendMessage(get_detail);
    }

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buy_house_type_relativeLayout: //居室类型
                    houseTypePickr.show();
                    break;

                case R.id.buy_toward_relativeLayout: //房屋朝向
                    towardTypePickr.show();
                    break;

                case R.id.nav_btn_back: //点击返回
                    finish();
                    break;
                case R.id.buy_bt_valuation: //点击估值
                    actionGetValue();
                    break;

                default:
                    break;
            }
        }

    };


    /**
     * 初次化居室列表选择器
     */
    private void initHouseTypeChoaese() {
        houseTypePickr = new OptionsPickerView(SaleHouseActivity.this);
        final ArrayList<String> optionsItems = new ArrayList<String>();
        for (int i = 0; i < houseTypes.length; i++) {
            optionsItems.add(houseTypes[i]);
        }

        houseTypePickr.setPicker(optionsItems);
        //设置是否循环滚动
        houseTypePickr.setCyclic(false);
        //设置默认选中的项目
        houseTypePickr.setSelectOptions(0);
        //监听确定选择按钮
        houseTypePickr.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {

                //返回的分别是三个级别的选中位置
                String tx = optionsItems.get(options1);
//                ZLog.Zlogi("tx = " + tx, TAG, LOGLEVEL);
                nowChoesehouseTypesINT = options1;
                buyHouseTypeValue.setText(tx);

            }
        });

    }


    /**
     * 初次化朝向选择器
     */
    private void initTowardChoaese() {
        towardTypePickr = new OptionsPickerView(SaleHouseActivity.this);
        final ArrayList<String> optionsItems = new ArrayList<String>();
        for (int i = 0; i < toward.length; i++) {
            optionsItems.add(toward[i]);
        }

        towardTypePickr.setPicker(optionsItems);

        //设置是否循环滚动
        towardTypePickr.setCyclic(false);
        //设置默认选中的项目
        towardTypePickr.setSelectOptions(0);
        //监听确定选择按钮
        towardTypePickr.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {

                //返回的分别是三个级别的选中位置
                String tx = optionsItems.get(options1);
                ZLog.Zlogi("tx = " + tx, TAG, LOGLEVEL);
                buyTowardValue.setText(tx);

            }
        });

    }

    /**
     * 显示popwindow
     *
     * @param buyHouseBeen
     */
    private void showPopupWindow(final BuyHouseBeen buyHouseBeen) {
        if (buyHouseBeen.getResultData() != null && buyHouseBeen.getResultData().size() > 0) {
            mbuyHouseBeen = buyHouseBeen;
            buyHouseSearchAdapter.setBuyHouseBeen(mbuyHouseBeen);
            buyHouseSearchAdapter.notifyDataSetChanged();
            // 设置好参数之后再show
            popupWindow.showAsDropDown(buyResidentialName);
        } else if (buyHouseBeen.getResultData() == null) {
            destroyPopoWindow();
        }
    }

    /**
     * 初次化 PopupWindow
     */
    private void initPopupWindow() {
        buyHouseSearchAdapter = new BuyHouseSearchAdapter(SaleHouseActivity.this);
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(SaleHouseActivity.this).inflate(R.layout.view_buy_house_search_popopwindow, null);

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        ListView popop_list_view = (ListView) contentView.findViewById(R.id.popop_list_view);
        popop_list_view.setAdapter(buyHouseSearchAdapter);
        popop_list_view.setOnItemClickListener(myOnItemClickListener);

        popupWindow.setFocusable(false); //不让其获得焦点
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

    /**
     * popupWindow里面的List的点击事件
     * 填充名字
     */
    private AdapterView.OnItemClickListener myOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mbuyHouseBeen != null && mbuyHouseBeen.getResultData() != null && mbuyHouseBeen.getResultData().size() > 0) {
                isShowPopoWindow = false;
                BuyHouseBeen.ResultDataEntity resultDataEntity = mbuyHouseBeen.getResultData().get(position);
                //设置小区id
                getAppraisementBeen.setResidentialAreaId(resultDataEntity.getResidentialAreaID()+"");

                //设置小区名字
                String matchName = resultDataEntity.getResidentialAreaName();
                //将最后输入名字改成点击名字
                //注意要放在setText的前面
                lastInputName = matchName;
                buyResidentialName.setText(matchName);
                //获取当前字符串长度，并用setselection把光标移到这个长度位置（末尾）
                buyResidentialName.setSelection(matchName.length());
                popupWindow.dismiss();
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        destroyPopoWindow();

        myPopupWindow.destroyPopoWindow();
    }

    /**
     * 隐藏popupWindow
     */
    private void destroyPopoWindow() {
        isShowPopoWindow = false;
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        /**
         * 覆盖掉父类的toast 太长了
         * 使用短toast
         */
        Toast.makeText(SaleHouseActivity.this, msg, Toast.LENGTH_SHORT).show();
//        super.showToast(msg);
    }
}

