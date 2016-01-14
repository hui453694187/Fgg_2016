package com.yunfangdata.fgg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.adapter.PersonMenuAdapter;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.dto.PersonMenuDto;
import com.yunfangdata.fgg.logic.AppHeader;
import com.yunfangdata.fgg.utils.SPUtil;
import com.yunfangdata.fgg.view.RoundImage;

import java.util.ArrayList;

/**
 * Created by 贺隽 on 2015/12/14.
 * 个人中心
 */
public class PersonActivity extends NewBaseActivity {

    /**
     * 主菜单的广播
     */
    public AppHeader appHeader;

    /**
     * 获取菜单
     */
    private final int HANDLE_GET_MENU = 100;

    /**
     *  打开对应的界面
     */
    private final int HANDLE_OPEN = 110;

    /**
     * 用户头像
     */
    RoundImage person_txt_user_logo;

    /**
     * 用户名
     */
    TextView person_txt_user;

    /**
     * 菜单
     */
    ListView person_lv_menu;

    /**
     * 退出按钮
     */
    Button person_btn_menu_logout;


    /**
     * 菜单信息
     */
    ArrayList<PersonMenuDto> personMenus;

    /**
     * 菜单数据源
     */
    private PersonMenuAdapter personMenuAdapter;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        assignViews();
        doWork("", HANDLE_GET_MENU, "");
    }

    /**
     * 初始化UI
     */
    private void assignViews() {
        appHeader = new AppHeader(this, R.id.nav_title);
        person_txt_user_logo = (RoundImage) findViewById(R.id.person_txt_user_logo);
        person_txt_user = (TextView) findViewById(R.id.person_txt_user);
        person_lv_menu = (ListView) findViewById(R.id.person_lv_menu);
        person_btn_menu_logout = (Button) findViewById(R.id.person_btn_menu_logout);

        //设置显示信息
        appHeader.setTitle("个人中心");
        person_txt_user.setText("用户：" + FggApplication.getUserInfo().getUserName() + "，欢迎您！");

        //设置点击事件
        person_btn_menu_logout.setOnClickListener(clickListener);
        person_lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 打开对应的菜单
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                doWork("", HANDLE_OPEN, personMenus.get(position));
            }
        });
    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_MENU:
                personMenus = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    personMenus.add(new PersonMenuDto(i));
                }
                break;
            case HANDLE_OPEN:
                resultMsg.obj = msg.obj;
        }
        mUiHandler.sendMessage(resultMsg);
    }

    @Override
    protected void handleUiMessage(Message msg) {
        String message = "";
        super.handleUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_MENU: {
                personMenuAdapter = new PersonMenuAdapter(getApplicationContext(),personMenus);
                person_lv_menu.setAdapter(personMenuAdapter);
                break;
            }
            case HANDLE_OPEN: {
                PersonMenuDto personMenu = (PersonMenuDto)msg.obj;
                if(personMenu != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("title",personMenu.Concent);
                    Intent intent = null;
                    switch (personMenu.Position){
                        case 0://个人资料
                            intent = new Intent(PersonActivity.this,PersonDataActivity.class);
                            break;
                        case 1://消息中心
                            intent = new Intent(PersonActivity.this,PersonMessageActivity.class);
                            break;
                        case 2://看房联系人
                            bundle.putBoolean("otherGetData",false);//从这里进去时不是选择模式
                            intent = new Intent(PersonActivity.this,PersonContactsActivity.class);
                            break;
                        case 3://收件人信息
                            bundle.putBoolean("otherGetData",false);//从这里进去时不是选择模式
                            intent = new Intent(PersonActivity.this,PersonRecipientsActivity.class);
                            break;
                        case 4://意见反馈
                            intent = new Intent(PersonActivity.this,PersonFeedbackActivity.class);
                            break;
                        case 5://修改密码
                            intent = new Intent(PersonActivity.this,PersonPasswordActivity.class);
                            break;
                        case 6://解绑手机
                            intent = new Intent(PersonActivity.this,PersonUnbindingMobileActivity.class);
                            break;
                        case 7://绑定邮箱
                            intent = new Intent(PersonActivity.this,PersonBundlingEmailActivity.class);
                            break;
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
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
                case R.id.person_btn_menu_logout:
                    appHeader.showDialog("提示","确认退出登录吗",new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            //登录状态清空
                            SPUtil.saveUserName(FggApplication.getInstance(),"");
                            FggApplication.setUserInfo(null);
                            finish();
                        }
                    });
                default:
                    break;
            }
        }
    };

}
