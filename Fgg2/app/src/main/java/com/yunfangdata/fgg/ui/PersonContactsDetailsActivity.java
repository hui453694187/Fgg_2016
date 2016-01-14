package com.yunfangdata.fgg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.yunfang.framework.base.BaseWorkerActivity;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.adapter.PersonMenuAdapter;
import com.yunfangdata.fgg.base.BroadRecordType;
import com.yunfangdata.fgg.base.LoadingConstant;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.logic.AppHeader;
import com.yunfangdata.fgg.logic.PersonOperator;
import com.yunfangdata.fgg.model.PersonContacts;
import com.yunfangdata.fgg.model.ResultInfo;

/**
 * Created by 贺隽 on 2015/12/16
 * 个人中心-看房联系人-详情
 */
public class PersonContactsDetailsActivity extends NewBaseActivity {

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
     * 保存按钮
     */
    Button person_contacts_details_btn_save;

    /**
     * 勾选默认看房人
     */
    CheckBox person_contacts_details_cbx_default;

    /**
     * 看房联系人
     */
    EditText person_contacts_details_txt_name;

    /**
     * 看房联系人电话
     */
    EditText person_contacts_details_txt_phone;

    /**
     * 显示数据
     */
    PersonContacts data;

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
        setContentView(R.layout.activity_person_contacts_details);
        assignViews();
        if (id.length() > 0) {
            doWork("", HANDLE_GET_DATA, id);
        }
    }

    /**
     * 初始化UI
     */
    private void assignViews() {

        appHeader = new AppHeader(this, R.id.nav_title);
        person_contacts_details_btn_save = (Button) findViewById(R.id.person_contacts_details_btn_save);
        person_contacts_details_cbx_default = (CheckBox) findViewById(R.id.person_contacts_details_cbx_default);
        person_contacts_details_txt_name = (EditText) findViewById(R.id.person_contacts_details_txt_name);
        person_contacts_details_txt_phone = (EditText) findViewById(R.id.person_contacts_details_txt_phone);

        Bundle bundle = this.getIntent().getExtras();
        add = bundle.getBoolean("add");
        id = bundle.getString("id");

        //设置显示信息
        if (add) {
            appHeader.setTitle("添加联系人");
            person_contacts_details_btn_save.setText("保存");
        } else {
            appHeader.setTitle("修改联系人");
            person_contacts_details_btn_save.setText("修改");
        }

        //设置事件
        person_contacts_details_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (person_contacts_details_txt_name.length() <= 0) {
                    appHeader.showDialog("提示", "请输入看房联系人", null);
                    return;
                }
                if (person_contacts_details_txt_phone.length() <= 0) {
                    appHeader.showDialog("提示", "请输入看房人电话", null);
                    return;
                }
                if (!isMobileNo(person_contacts_details_txt_phone.getText().toString())) {
                    appHeader.showDialog("提示", "看房人电话输入有误", null);
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
                resultMsg.obj = PersonOperator.contactsQueryById(id);
                break;
            case HANDLE_ADD:
                resultMsg.obj = PersonOperator.contactsSave(
                        person_contacts_details_txt_name.getText().toString(),
                        person_contacts_details_txt_phone.getText().toString(),
                        person_contacts_details_cbx_default.isChecked() ? 1 : 0
                );
                break;
            case HANDLE_UPDATE:
                resultMsg.obj = PersonOperator.contactsUpdate(
                        id,
                        person_contacts_details_txt_name.getText().toString(),
                        person_contacts_details_txt_phone.getText().toString(),
                        person_contacts_details_cbx_default.isChecked() ? 1 : 0
                );
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
                ResultInfo<PersonContacts> resultInfo = (ResultInfo<PersonContacts>) msg.obj;
                if (resultInfo.Success) {
                    data = resultInfo.Data;
                    person_contacts_details_txt_name.setText(data.getLianXiRenName());
                    person_contacts_details_txt_phone.setText(data.getShouJi());
                    person_contacts_details_cbx_default.setChecked(data.getMoRenLianXiRen() == 1);
                } else {
                    message = resultInfo.Message;
                }
                break;
            }
            case HANDLE_ADD:
            case HANDLE_UPDATE: {
                ResultInfo<Boolean> resultInfo = (ResultInfo<Boolean>) msg.obj;
                if (resultInfo.Success) {
                    Intent intent = new Intent();
                    intent.setAction(BroadRecordType.PERSON_CONTACTS_SAVE_FINISH);
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

}
