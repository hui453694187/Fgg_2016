package com.yunfangdata.fgg.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.yunfang.framework.base.BaseBroadcastReceiver;
import com.yunfang.framework.base.BaseWorkerActivity;
import com.yunfang.framework.utils.ListUtil;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.adapter.PersonRecipientsAdapter;
import com.yunfangdata.fgg.base.BroadRecordType;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.LoadingConstant;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.logic.AppHeader;
import com.yunfangdata.fgg.logic.PersonOperator;
import com.yunfangdata.fgg.model.PersonRecipients;
import com.yunfangdata.fgg.model.ResultInfo;

import java.util.ArrayList;

/**
 * Created by 贺隽 on 2015/12/14.
 * 个人中心-收件人信息
 */
public class PersonRecipientsActivity extends NewBaseActivity {

    /**
     * 主菜单的广播
     */
    public AppHeader appHeader;

    /**
     * 获取数据
     */
    private final int HANDLE_GET_DATA = 100;

    /**
     * 删除
     */
    private final int HANDLE_DELETE = 110;

    /**
     * 设置默认
     */
    private final int HANDLE_DEFAULT = 120;

    /**
     * 选择
     */
    private final int HANDLE_SELECT = 130;

    /**
     * 数据
     */
    ListView person_recipients_lv_data;

    /**
     * 删除按钮
     */
    Button person_recipients_btn_delete;

    /**
     * 设置默认按钮
     */
    Button person_recipients_btn_default;

    /**
     * 编辑按钮
     */
    Button person_recipients_btn_edit;

    /**
     * 添加按钮
     */
    Button person_recipients_btn_add;

    /**
     * 菜单信息
     */
    public ArrayList<PersonRecipients> data;

    /**
     * 菜单数据源
     */
    private PersonRecipientsAdapter adapter;

    /**
     * 是否为从其他界面调用
     */
    private Boolean otherGetData = false;

    /**
     * 操作模式是否为默认
     */
    private Boolean operationNormal = true;

    /***
     * 广播通知
     */
    BaseBroadcastReceiver receiver;

    /**
     * 界面创建
     *
     * @param savedInstanceState 保存实例状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_recipients);
        assignViews();
        receiver();
        doWork("", HANDLE_GET_DATA, "");
    }

    /**
     * 初始化UI
     */
    private void assignViews() {
        appHeader = new AppHeader(this, R.id.nav_title);
        person_recipients_lv_data = (ListView) findViewById(R.id.person_recipients_lv_data);
        person_recipients_btn_add = (Button) findViewById(R.id.person_recipients_btn_add);
        person_recipients_btn_edit = (Button) findViewById(R.id.person_recipients_btn_edit);
        person_recipients_btn_default = (Button) findViewById(R.id.person_recipients_btn_default);
        person_recipients_btn_delete = (Button) findViewById(R.id.person_recipients_btn_delete);

        //获取传入的参数
        Bundle bundle = this.getIntent().getExtras();
        otherGetData = bundle.getBoolean(Constant.GET_RECIPIENTS_4_RESULT + "");
        //设置显示信息
        appHeader.setTitle(bundle.getString("title"));
        appHeader.setNavOpOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationModel();
                doWork(LoadingConstant.KEY_LOADING, HANDLE_GET_DATA, "");
            }
        });

        //设置点击事件
        person_recipients_btn_add.setOnClickListener(clickListener);
        person_recipients_btn_edit.setOnClickListener(clickListener);
        person_recipients_btn_default.setOnClickListener(clickListener);
        person_recipients_btn_delete.setOnClickListener(clickListener);
        //选择模式时用
        if (otherGetData) {
            person_recipients_lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                // 打开对应的菜单
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                    PersonRecipients item = data.get(position);
                    FggApplication.setPersonRecipients(item);
                    Intent intent= new Intent();
                    Bundle b=new Bundle();
                    b.putSerializable(Constant.GET_RECIPIENTS_4_RESULT + "", item);
                    intent.putExtras(b);
                    PersonRecipientsActivity.this.setResult(Constant.GET_RECIPIENTS_4_RESULT, intent);
                    PersonRecipientsActivity.this.finish();
                }
            });
        }
    }

    /**
     * 后台处理
     *
     * @param msg 传递信息
     */
    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_DATA:
                resultMsg.obj = PersonOperator.recipientsQuery();
                break;
            case HANDLE_DEFAULT: {
                String id = msg.obj.toString();
                resultMsg.obj = PersonOperator.recipientsDefault(id);
                break;
            }
            case HANDLE_DELETE: {
                String id = msg.obj.toString();
                String[] deleteItems = id.split(",");
                for (String item : deleteItems) {
                    resultMsg.obj = PersonOperator.recipientsDelete(item);
                }
                break;
            }
        }
        mUiHandler.sendMessage(resultMsg);
    }

    /**
     * 前台界面UI
     *
     * @param msg 参数
     */
    @Override
    protected void handleUiMessage(Message msg) {
        String message = "";
        super.handleUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_DATA: {
                ResultInfo<ArrayList<PersonRecipients>> resultInfo = (ResultInfo<ArrayList<PersonRecipients>>) msg.obj;
                if (resultInfo.Success && resultInfo != null && ListUtil.hasData(resultInfo.Data)) {
                    appHeader.showOperationView();
                    data = resultInfo.Data;
                    adapter = new PersonRecipientsAdapter(this, operationNormal);
                    person_recipients_lv_data.setAdapter(adapter);
                } else {
                    message = resultInfo.Message;
                }
                break;
            }
            case HANDLE_DEFAULT:
            case HANDLE_DELETE: {
                ResultInfo<Boolean> resultInfo = (ResultInfo<Boolean>) msg.obj;
                if (resultInfo.Success) {
                    operationModel();
                    doWork(LoadingConstant.KEY_LOADING, HANDLE_GET_DATA, "");
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

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            Bundle bundle = new Bundle();
            String id = "";
            PersonRecipients updateData = null;
            if (ListUtil.hasData(data)) {
                for (PersonRecipients item : data) {
                    if (item.IsCheck) {
                        updateData = item;
                        id += item.getYouJiDiZhiId() + ",";
                    }
                }
                if (id.length() > 0) {
                    id = id.substring(0, id.length() - 1);
                }
            }
            switch (v.getId()) {
                case R.id.person_recipients_btn_add: {
                    bundle.putBoolean("add", true);
                    bundle.putString("id", id);
                    intent = new Intent(PersonRecipientsActivity.this, PersonRecipientsDetailsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case R.id.person_recipients_btn_edit: {
                    if (updateData != null) {
                        operationModel();
                        doWork("", HANDLE_GET_DATA, "");
                        bundle.putBoolean("add", false);
                        bundle.putString("id", id);
                        bundle.putSerializable("data", updateData);
                        intent = new Intent(PersonRecipientsActivity.this, PersonRecipientsDetailsActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    break;
                }
                case R.id.person_recipients_btn_default: {
                    if (id.length() > 0 && !id.contains(",")) {
                        doWork(LoadingConstant.KEY_LOADING, HANDLE_DEFAULT, id);
                    }
                    break;
                }
                case R.id.person_recipients_btn_delete: {
                    if (id.length() > 0) {
                        doWork(LoadingConstant.KEY_LOADING, HANDLE_DELETE, id);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 控件操作方式
     */
    private void operationModel() {
        operationNormal = !operationNormal;
        if (operationNormal) {
            appHeader.setOperationText("编辑");
            person_recipients_btn_default.setVisibility(View.GONE);
            person_recipients_btn_edit.setVisibility(View.GONE);
            person_recipients_btn_delete.setVisibility(View.GONE);
            person_recipients_btn_add.setVisibility(View.VISIBLE);

            person_recipients_btn_delete.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.button_normal));
            person_recipients_btn_default.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.button_normal));
            person_recipients_btn_edit.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.button_normal));


        } else {
            appHeader.setOperationText("取消");
            person_recipients_btn_default.setVisibility(View.VISIBLE);
            person_recipients_btn_edit.setVisibility(View.VISIBLE);
            person_recipients_btn_delete.setVisibility(View.VISIBLE);
            person_recipients_btn_add.setVisibility(View.GONE);

            person_recipients_btn_delete.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.white));
            person_recipients_btn_default.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.white));
            person_recipients_btn_edit.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.white));
        }
    }

    /**
     * 编辑模式选择了多个
     */
    public void operationModelSelectMore() {
        int count = 0;
        for (PersonRecipients item : data) {
            if (item.IsCheck) {
                count++;
            }
            if (count > 1) {
                break;
            }
        }
        if (count == 0) {
            person_recipients_btn_delete.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.white));
            person_recipients_btn_default.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.white));
            person_recipients_btn_edit.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.white));
        } else if (count > 1) {
            person_recipients_btn_delete.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.button_normal));
            person_recipients_btn_default.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.white));
            person_recipients_btn_edit.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.white));
        } else {
            person_recipients_btn_delete.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.button_normal));
            person_recipients_btn_default.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.button_normal));
            person_recipients_btn_edit.setTextColor(FggApplication.getInstance().getResources().getColor(R.color.button_normal));
        }
    }

    /**
     * 接收通知
     */
    public void receiver() {
        // 声明消息接收对象
        ArrayList<String> temp = new ArrayList<>();
        temp.add(BroadRecordType.PERSON_RECIPIENTS_SAVE_FINISH);
        receiver = new BaseBroadcastReceiver(getApplicationContext(), temp);
        receiver.setAfterReceiveBroadcast(new BaseBroadcastReceiver.afterReceiveBroadcast() {
            @Override
            public void onReceive(final Context context, Intent intent) {
                String actionType = intent.getAction();
                switch (actionType) {
                    case BroadRecordType.PERSON_RECIPIENTS_SAVE_FINISH: // 恢复
                        doWork("", HANDLE_GET_DATA, "");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        receiver.unregisterReceiver();
        super.onDestroy();
    }

}
