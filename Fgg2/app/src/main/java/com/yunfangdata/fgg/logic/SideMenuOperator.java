package com.yunfangdata.fgg.logic;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.helper.IntentHelper;
import com.yunfangdata.fgg.model.DeviceInfo;
import com.yunfangdata.fgg.model.SideMenuItemModel;
import com.yunfangdata.fgg.model.UserInfo;
import com.yunfangdata.fgg.ui.HomeActivity;

import java.util.List;

/**
 * Created by Kevin on 2015/12/10.
 */
public class SideMenuOperator implements AdapterView.OnItemClickListener {

    private View view;

    private ListView menuLv;

    private MenuListAdapter menuListAdapter;

    private HomeActivity homeActivity;

    private LayoutInflater layoutInflater;

    private List<SideMenuItemModel> sideItemList;

    private boolean isLogin = false;

    private UserInfo user;


    public SideMenuOperator(View view, HomeActivity context) {
        homeActivity = context;
        layoutInflater = LayoutInflater.from(homeActivity);
        initItemList();
        this.view = view;
        initView();

    }

    private void initView() {
        menuLv = (ListView) view.findViewById(R.id.menu_lv);
        menuListAdapter = new MenuListAdapter(sideItemList);
        menuLv.setAdapter(menuListAdapter);
        menuLv.setOnItemClickListener(this);
    }

    /**
     * 初始化列表Item
     */
    private void initItemList() {
        String[] itemNames = FggApplication.getInstance().getResources().getStringArray(R.array.side_item_name);
        sideItemList = homeActivity.viewModel.getSideItemList(itemNames);
    }

    /***
     * 菜单列表点击事件
     *
     * @param adapterView
     * @param view
     * @param position
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        isLogin();
        switch (position) {
            case 0://用户登录
                if (isLogin) {
                    IntentHelper.intentToPerson(homeActivity);
                } else {
                    IntentHelper.intentToRegisterAndLogin(homeActivity);
                }
                break;
            case 2://买房估值
                IntentHelper.intentToBuyHouse(homeActivity, "beijing");
                break;
            case 3://卖房估值
                IntentHelper.intentToSaleHouse(homeActivity, "beijing");
                break;
            case 4://进度查询
                IntentHelper.intentToScheduleQuery(homeActivity, "beijing");
                break;
            case 5://真伪查询
                IntentHelper.intentToHypocrisyQuery(homeActivity, "beijing");
                break;
            case 6://设置
                IntentHelper.intentToSettingActivity(homeActivity);
                break;
            case 8://委托评估
                if (isLogin) {
                    IntentHelper.intentToEntrustBookWebView(homeActivity);
                } else {
                    IntentHelper.intentToRegisterAndLogin(homeActivity);
                }
                break;
            case 9://委托记录
                if (isLogin) {
                    IntentHelper.intentToEntrustList(homeActivity);
                } else {
                    IntentHelper.intentToRegisterAndLogin(homeActivity);
                }
                break;
            case 10:// 个人中心
                if (isLogin) {
                    IntentHelper.intentToPerson(homeActivity);
                } else {
                    IntentHelper.intentToRegisterAndLogin(homeActivity);
                }
                break;
            default:
                return;
        }
        homeActivity.sideMenuPopWin.dismiss();
    }

    public void isLogin() {
        user = FggApplication.getUserInfo();
        if (!TextUtils.isEmpty(user.getUserName())) {
            isLogin = true;
            menuListAdapter.sideItemList.get(0).setName(user.getUserName());
            menuListAdapter.notifyDataSetChanged();
        } else {
            isLogin = false;
            menuListAdapter.sideItemList.get(0).setName("会员登录");
        }
    }

    public class MenuListAdapter extends BaseAdapter {

        public List<SideMenuItemModel> sideItemList;

        MenuListAdapter(List sideItemList) {
            this.sideItemList = sideItemList;

        }

        @Override
        public int getCount() {
            return this.sideItemList.size();
        }

        @Override
        public Object getItem(int i) {
            return this.sideItemList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            SideMenuItemModel menuItem = this.sideItemList.get(position);
            if (menuItem.getItemType() != SideMenuItemModel.ItemType.Label) {// 不是lebel 类型的布局
                ViewHolder viewHolder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.list_item_side_menu, null, false);
                viewHolder.ionImg = (ImageView) view.findViewById(R.id.icon_menu_img);
                viewHolder.nameTv = (TextView) view.findViewById(R.id.item_name_tv);
                setDrawable(viewHolder.ionImg, menuItem.getIconId());
                viewHolder.nameTv.setText(menuItem.getName());
                DeviceInfo deviceInfo = FggApplication.getInstance().deviceInfo;
                int height=deviceInfo.ScreenHeight/11;
//                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,height));
            } else {
                view = layoutInflater.inflate(R.layout.list_item_side_menu_label, null, false);
                TextView label = (TextView) view.findViewById(R.id.label_tv);
                label.setText(menuItem.getName());
            }
            return view;
        }


        private void setDrawable(ImageView img, int drawableId) {
            img.setImageDrawable(homeActivity.getResources().getDrawable(drawableId));
        }

        class ViewHolder {
            public TextView nameTv;
            public ImageView ionImg;

            ViewHolder() {
            }
        }
    }


}
