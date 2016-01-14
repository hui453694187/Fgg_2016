package com.yunfangdata.fgg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.yunfang.framework.base.BaseWorkerActivity;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.adapter.PersonMenuAdapter;
import com.yunfangdata.fgg.base.BroadRecordType;
import com.yunfangdata.fgg.base.LoadingConstant;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.http.task.ParseCityTask;
import com.yunfangdata.fgg.logic.AppHeader;
import com.yunfangdata.fgg.logic.PersonOperator;
import com.yunfangdata.fgg.model.Area;
import com.yunfangdata.fgg.model.CityNameBeen;
import com.yunfangdata.fgg.model.PersonRecipients;
import com.yunfangdata.fgg.model.PersonMessage;
import com.yunfangdata.fgg.model.ResultInfo;

import java.util.ArrayList;

/**
 * Created by 贺隽 on 2015/12/16
 * 个人中心-收件收件人-详情
 */
public class PersonRecipientsDetailsActivity extends NewBaseActivity {
    /**
     * 主菜单的广播
     */
    public AppHeader appHeader;

    /**
     * 获取数据
     */
    private final int HANDLE_GET_DATA = 100;

    /**
     * 添加
     */
    private final int HANDLE_ADD = 120;

    /**
     * 修改
     */
    private final int HANDLE_UPDATE = 130;


    /**
     * 区域选择
     */
    RelativeLayout person_recipients_details_relay_area;

    /**
     * 保存按钮
     */
    Button person_recipients_details_btn_save;

    /**
     * 勾选默认看房人
     */
    CheckBox person_recipients_details_cbx_default;

    /**
     * 看房收件人
     */
    EditText person_recipients_details_txt_name;

    /**
     * 省、市、区
     */
    TextView person_recipients_details_txt_area;

    /**
     * 地址
     */
    EditText person_recipients_details_txt_address;

    /**
     * 邮政编码
     */
    EditText person_recipients_details_txt_postcode;

    /**
     * 手机
     */
    EditText person_recipients_details_txt_phone;

    /**
     * 城市选择
     */
    private OptionsPickerView CityPickView;

    /**
     * 获取城市选择列表
     */
    private final int HANDLE_GET_CITY_VIEW = 140;
    /**
     * 获取城市选择列表
     */
    private final int HANDLE_GET_EVALUATION_NOW = 121;
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
     * 省份选择
     */
    OptionsPickerView areaPicker = null;

    /**
     * 显示数据
     */
    PersonRecipients data;

    /**
     * 是否为添加模式
     */
    Boolean add;

    /**
     * 编号
     */
    String id;

    /**
     * 创建界面
     *
     * @param savedInstanceState 保存实例状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_recipients_details);
        newintiCityPick();
        assignViews();
        initAreaChoaese();
        if (id.length() > 0) {
            doWork("", HANDLE_GET_DATA, id);
        }
    }

    /**
     * 初始化UI
     */
    private void assignViews() {
        appHeader = new AppHeader(this, R.id.nav_title);
        person_recipients_details_relay_area = (RelativeLayout) findViewById(R.id.person_recipients_details_relay_area);
        person_recipients_details_btn_save = (Button) findViewById(R.id.person_recipients_details_btn_save);
        person_recipients_details_cbx_default = (CheckBox) findViewById(R.id.person_recipients_details_cbx_default);
        person_recipients_details_txt_name = (EditText) findViewById(R.id.person_recipients_details_txt_name);
        person_recipients_details_txt_area = (TextView) findViewById(R.id.person_recipients_details_txt_area);
        person_recipients_details_txt_address = (EditText) findViewById(R.id.person_recipients_details_txt_address);
        person_recipients_details_txt_postcode = (EditText) findViewById(R.id.person_recipients_details_txt_postcode);
        person_recipients_details_txt_phone = (EditText) findViewById(R.id.person_recipients_details_txt_phone);


        Bundle bundle = this.getIntent().getExtras();
        add = bundle.getBoolean("add");
        id = bundle.getString("id");
        data = (PersonRecipients) bundle.getSerializable("data");

        //设置显示信息
        if (add) {
            appHeader.setTitle("添加收件人");
            person_recipients_details_btn_save.setText("保存");
        } else {
            appHeader.setTitle("修改收件人");
            person_recipients_details_btn_save.setText("修改");
        }

        //设置事件
        person_recipients_details_relay_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCityInitSeccess) {
                    CityPickView.show();
                } else {
                    showToast("城市列表正在初始化,请稍候再试");
                }
//                areaPicker.show();
            }
        });
        person_recipients_details_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (person_recipients_details_txt_name.length() <= 0) {
                    appHeader.showDialog("提示", "请输入收件人", null);
                    return;
                }
                if (person_recipients_details_txt_area.length() <= 0) {
                    appHeader.showDialog("提示", "请选择省、市、区", null);
                    return;
                }
                if (person_recipients_details_txt_address.length() <= 0) {
                    appHeader.showDialog("提示", "请输入详细地址", null);
                    return;
                }
                if (person_recipients_details_txt_postcode.length() <= 0) {
                    appHeader.showDialog("提示", "请输入邮政编码", null);
                    return;
                }
                if (person_recipients_details_txt_phone.length() <= 0) {
                    appHeader.showDialog("提示", "请输入手机号码", null);
                    return;
                }
                if (!isMobileNo(person_recipients_details_txt_phone.getText().toString())) {
                    appHeader.showDialog("提示", "手机号码输入有误", null);
                    return;
                }
                doWork(LoadingConstant.KEY_LOADING, add ? HANDLE_ADD : HANDLE_UPDATE, null);
            }
        });
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNo(String mobileNo) {
        /*
		移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
        String telRegex = "[1][1-9]\\d{9}";//"[1]"代表第1位为数字1，"[1-9]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return mobileNo.matches(telRegex);
    }

    /**
     * 后台处理
     *
     * @param msg
     */
    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_DATA:
                resultMsg.obj = data;
                break;
            case HANDLE_ADD:
                resultMsg.obj = PersonOperator.recipientsSave(
                        person_recipients_details_txt_name.getText().toString(),
                        person_recipients_details_txt_area.getText().toString(),
                        person_recipients_details_txt_address.getText().toString(),
                        person_recipients_details_txt_postcode.getText().toString(),
                        person_recipients_details_txt_phone.getText().toString(),
                        person_recipients_details_cbx_default.isChecked() ? 1 : 0
                );
                break;
            case HANDLE_UPDATE:
                resultMsg.obj = PersonOperator.recipientsUpdate(
                        id,
                        person_recipients_details_txt_name.getText().toString(),
                        person_recipients_details_txt_area.getText().toString(),
                        person_recipients_details_txt_address.getText().toString(),
                        person_recipients_details_txt_postcode.getText().toString(),
                        person_recipients_details_txt_phone.getText().toString(),
                        person_recipients_details_cbx_default.isChecked() ? 1 : 0
                );
                break;
            case HANDLE_GET_CITY_VIEW://获取城市列表
                ParseCityTask parseCityTask = new ParseCityTask(PersonRecipientsDetailsActivity.this);
                boolean request = parseCityTask.request(CityPickView, options1Items, options2Items, options3Items);
                resultMsg.obj = request;
                break;
        }
        mUiHandler.sendMessage(resultMsg);
    }

    /**
     * UI处理
     *
     * @param msg 处理信息
     */
    @Override
    protected void handleUiMessage(Message msg) {
        String message = "";
        super.handleUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_DATA: {
                PersonRecipients resultInfo = (PersonRecipients) msg.obj;
                if (resultInfo != null) {
                    person_recipients_details_txt_name.setText(data.getShouJianRen());
                    person_recipients_details_txt_area.setText(data.getYouJifangwei());
                    person_recipients_details_txt_address.setText(data.getYouJiDiZhi());
                    person_recipients_details_txt_postcode.setText(data.getYouZhengBianMa());
                    person_recipients_details_txt_phone.setText(data.getShouJianRenDianHua());
                    person_recipients_details_cbx_default.setChecked(data.getMoRenShouJianDiZhi() == 1);
                } else {
                    message = "服务器异常";
                }
                break;
            }
            case HANDLE_GET_CITY_VIEW://获取城市列表
                isCityInitSeccess = (boolean) msg.obj;
                if (isCityInitSeccess) {
                    newintiCityPick2();
                }
                break;
            case HANDLE_ADD:
            case HANDLE_UPDATE: {
                ResultInfo<Boolean> resultInfo = (ResultInfo<Boolean>) msg.obj;
                if (resultInfo.Success) {
                    Intent intent = new Intent();
                    intent.setAction(BroadRecordType.PERSON_RECIPIENTS_SAVE_FINISH);
                    sendBroadcast(intent);
                    finish();
                } else {
                    message = resultInfo.Message;
                }
                break;
            }
        }
        if (message.length() > 0) {
            showToast(message);
        }
        this.loadingWorker.closeLoading();
    }

    ArrayList<String> provinceItem = new ArrayList<String>();
    ArrayList<String> cityItem = new ArrayList<String>();
    ArrayList<String> districtItem = new ArrayList<String>();

    /**
     * 初次化居室列表选择器
     */
    private void initAreaChoaese() {
        areaPicker = new OptionsPickerView(PersonRecipientsDetailsActivity.this);
        provinceItem.add("北京");
        provinceItem.add("上海");
        cityItem.add("北京");
        cityItem.add("上海");
        districtItem.add("东城区");
        districtItem.add("西城区");

        areaPicker.setPicker(provinceItem, null, null, true);
        //设置是否循环滚动
        areaPicker.setCyclic(false);
        //设置默认选中的项目
        areaPicker.setSelectOptions(0);
        //监听确定选择按钮
        areaPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {

                person_recipients_details_txt_area.setText(provinceItem.get(options1));
            }
        });

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

                String tx = options1Items.get(options1) + " "
                        + options2Items.get(options1).get(option2) + " "
                        + options3Items.get(options1).get(option2).get(options3);

//                eeLocationValue.setText(tx);
                person_recipients_details_txt_area.setText(tx);
            }
        });
    }

}
