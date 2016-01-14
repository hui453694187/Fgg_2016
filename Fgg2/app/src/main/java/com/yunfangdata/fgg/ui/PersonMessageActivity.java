package com.yunfangdata.fgg.ui;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yunfang.framework.utils.ToastUtil;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.adapter.PersonMessageAdapter;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.helper.IntentHelper;
import com.yunfangdata.fgg.logic.AppHeader;
import com.yunfangdata.fgg.logic.PersonOperator;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.PersonMessage;
import com.yunfangdata.fgg.viewmodel.MessageViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 贺隽 on 2015/12/14.
 * 个人中心-消息中心
 */
public class PersonMessageActivity extends NewBaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    /**
     * 主菜单的广播
     */
    public AppHeader appHeader;

    public MessageViewModel viewModel = new MessageViewModel();

    /**
     * 菜单
     */
    private ListView person_message_lv_data;

    private View edt_linea;

    private TextView is_read_tv;

    private TextView delete_tv;


    /***
     * 消息数据适配器
     */
    private PersonMessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_message);

        assignViews();

    }

    /**
     * 初始化UI
     */
    private void assignViews() {
        appHeader = new AppHeader(this, R.id.nav_title);
        appHeader.setTitle(this.getIntent().getExtras().getString("title"));
        is_read_tv = $(R.id.is_read_tv);
        delete_tv = $(R.id.delete_tv);
        person_message_lv_data = $(R.id.person_message_lv_data);
        edt_linea = $(R.id.edt_linea);

        messageAdapter = new PersonMessageAdapter(this);
        person_message_lv_data.setAdapter(messageAdapter);
        person_message_lv_data.setOnItemClickListener(this);
        delete_tv.setOnClickListener(this);
        is_read_tv.setOnClickListener(this);

        appHeader.showOperationView();
        appHeader.setNavOpOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isEdited = viewModel.isEdited();
                setListEdt(isEdited);
            }

        });
        doWork("消息加载中....", MessageViewModel.GET_PERSON_MESSAGE, null);
    }

    private void setListEdt(boolean isEdited){
        //是否处于编辑状态
        appHeader.setOperationText(isEdited ? "编辑" : "取消");
        viewModel.setIsEdited(!viewModel.isEdited());
        messageAdapter.notifyDataSetChanged();
        int isShow = isEdited ? View.GONE : View.VISIBLE;
        edt_linea.setVisibility(isShow);
        messageAdapter.cleanCheckout();
    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case MessageViewModel.GET_PERSON_MESSAGE:
                resultMsg.obj = PersonOperator.getMessageList();
                break;
            case MessageViewModel.SET_MSG_IS_READ:
                List<PersonMessage> msgIds = (List<PersonMessage>) msg.obj;
                resultMsg.obj=PersonOperator.setMsgIsRead(msgIds);
                break;
            case MessageViewModel.DELETE_MSG:
                List<PersonMessage> msgIds2 = (List<PersonMessage>) msg.obj;
                resultMsg.obj=PersonOperator.deleteMsgIsRead(msgIds2);
                break;
            case MessageViewModel.SET_MSG_IS_READ_ON_CLICK:
                List<PersonMessage> msgIds3 = (List<PersonMessage>) msg.obj;
                resultMsg.obj=PersonOperator.setMsgIsRead(msgIds3);
                break;

        }
        mUiHandler.sendMessage(resultMsg);
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MessageViewModel.GET_PERSON_MESSAGE:
                DataResult<List<PersonMessage>> msgList = (DataResult<List<PersonMessage>>) msg.obj;
                if (msgList.getSuccess()) {
                    viewModel.setMessages(msgList.getResultData());
                    messageAdapter.refaceData(viewModel.getMessages());
                } else {
                    ToastUtil.shortShow(this, msgList.getMessage());
                }
                this.loadingWorker.closeLoading();
                break;
            case MessageViewModel.SET_MSG_IS_READ:
                DataResult<Boolean> msgIds = (DataResult<Boolean>) msg.obj;
                if(msgIds.getSuccess()){
                    List<PersonMessage> list = messageAdapter.getCheckMsgId();
                    for(PersonMessage msgId:list){
                        viewModel.setMsgIsRead(msgId);
//                        messageAdapter.setMsgIsRead(msgId);
                    }
                    messageAdapter.notifyDataSetChanged();
                }else{
                    ToastUtil.shortShow(this,msgIds.getMessage());
                }
                this.loadingWorker.closeLoading();
                setListEdt(viewModel.isEdited());
                break;
            case MessageViewModel.DELETE_MSG:
                DataResult<Boolean> msgIds2 = (DataResult<Boolean>) msg.obj;
                if(msgIds2.getSuccess()){
                    List<PersonMessage> list = messageAdapter.getCheckMsgId();
                    try {
                        for (PersonMessage msgId : list) {
                            viewModel.deleteMsgById(msgId);
                        }
                        messageAdapter.refaceData(viewModel.getMessages());
                    }catch (Exception e){ e.printStackTrace(); }
                }else{
                    ToastUtil.shortShow(this,msgIds2.getMessage());
                }
                this.loadingWorker.closeLoading();
                setListEdt(viewModel.isEdited());
                break;
            case MessageViewModel.SET_MSG_IS_READ_ON_CLICK:
                DataResult<Boolean> msgIds3 = (DataResult<Boolean>) msg.obj;
                if(msgIds3.getSuccess()){
                    try {
                        viewModel.setMsgIsRead(viewModel.checkoutPm);
                        messageAdapter.notifyDataSetChanged();
                    }catch (Exception e){ e.printStackTrace(); }
                }else{
                    ToastUtil.shortShow(this, msgIds3.getMessage());
                }
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        boolean isEdited = viewModel.isEdited();
        if (isEdited) {
            messageAdapter.putCheckout(i);
            messageAdapter.notifyDataSetChanged();
        } else {
            List<PersonMessage> list =new ArrayList<>();
            viewModel.checkoutPm=messageAdapter.getItem(i);
            list.add(viewModel.checkoutPm);
            doWork("", MessageViewModel.SET_MSG_IS_READ_ON_CLICK,list);
            PersonMessage msg = messageAdapter.getItem(i);
            IntentHelper.toMessageDetails(this, msg);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.is_read_tv:
                List<PersonMessage> list = messageAdapter.getCheckMsgId();
                viewModel.setIsEdited(true);
                doWork("正在标记....",MessageViewModel.SET_MSG_IS_READ,list);
                break;
            case R.id.delete_tv:
                List<PersonMessage> msgIds = messageAdapter.getCheckMsgId();
                doWork("删除消息中....",MessageViewModel.DELETE_MSG,msgIds);
                break;
        }
    }
}
