package com.yunfangdata.fgg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.model.PersonContacts;
import com.yunfangdata.fgg.ui.PersonContactsActivity;

/**
 * Created by 贺隽 on 2015/12/16.
 * 个人中心-看房联系人 数据适配源
 */
public class PersonContactsAdapter extends BaseAdapter {

    /**
     * 实例界面
     */
    PersonContactsActivity activity;

    /**
     *当前实例
     */
    Context context;

    /**
     * normal 是否为默认模式  为true 表示不限勾选框 false 表示显示勾选框
     */
    Boolean normal;

    /**
     * 个人中心 数据适配源
     *
     * @param mContext 实例化界面
     * @parm normal 是否为默认模式  为true 表示不限勾选框 false 表示显示勾选框
     */
    public PersonContactsAdapter(Context mContext,  Boolean mNormal) {
        super();
        this.context = mContext;
        this.normal = mNormal;
        this.activity = (PersonContactsActivity)context;
    }

    /**
     * 获取总个数
     *
     * @return 返回总数
     */
    @Override
    public int getCount() {
        if (activity.data != null && activity.data.size() > 0) {
            return activity.data.size();
        } else {
            return 0;
        }
    }

    /**
     * 获取当前实例
     *
     * @param position 索引
     * @return 返回当前对象
     */
    @Override
    public Object getItem(int position) {
        if (position > -1 && activity.data != null && activity.data.size() > 0) {
            return activity.data.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 返回每一行的数据
     *
     * @param position    当前索引
     * @param convertView 当前操作视图
     * @param parent      当前操作视图组
     * @return 返回新绘制的视图
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_person_contacts_data, null);
            holder = new ViewHolder();
            holder.person_contacts_item_rd_select = (CheckBox) convertView.findViewById(R.id.person_contacts_item_rd_select);
            holder.person_contacts_item_txt_title = (TextView) convertView.findViewById(R.id.person_contacts_item_txt_title);
            holder.person_contacts_item_txt_concent = (TextView) convertView.findViewById(R.id.person_contacts_item_txt_concent);
            holder.person_contacts_item_txt_default = (TextView) convertView.findViewById(R.id.person_contacts_item_txt_default);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PersonContacts item = activity.data.get(position);
        if (item != null) {
            holder.person_contacts_item_txt_title.setText(item.getLianXiRenName());
            holder.person_contacts_item_txt_concent.setText(item.getShouJi());
            //等于1表示为默认联系人
            if (item.getMoRenLianXiRen() == 1) {
                holder.person_contacts_item_txt_default.setVisibility(View.VISIBLE);
            } else {
                holder.person_contacts_item_txt_default.setVisibility(View.INVISIBLE);
            }
            //normal 为false 显示勾选框
            if(normal){
                holder.person_contacts_item_rd_select.setVisibility(View.INVISIBLE);
            }else{
                holder.person_contacts_item_rd_select.setVisibility(View.VISIBLE);
            }

            holder.person_contacts_item_rd_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    activity.data.get(position).IsCheck = isChecked;
                    activity.operationModelSelectMore();
                }
            });
        }
        return convertView;
    }

    /**
     * 缓存视图控件
     */
    private class ViewHolder {
        /**
         * 是否选中
         */
        private CheckBox person_contacts_item_rd_select;
        /**
         * 名称
         */
        private TextView person_contacts_item_txt_title;
        /**
         * 手机号码
         */
        private TextView person_contacts_item_txt_concent;
        /**
         * 是否为默认
         */
        private TextView person_contacts_item_txt_default;
    }

}
