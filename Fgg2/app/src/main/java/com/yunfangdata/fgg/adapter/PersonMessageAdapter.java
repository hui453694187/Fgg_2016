package com.yunfangdata.fgg.adapter;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunfang.framework.utils.BitmapHelperUtil;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.model.PersonMessage;
import com.yunfangdata.fgg.ui.PersonMessageActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 贺隽 on 2015/12/14.
 * 个人中心 数据适配源
 */
public class PersonMessageAdapter extends BaseAdapter {
    /**
     * 标记信息
     */
    private static final String TAG = "PersonMessageAdapter";

    private HashMap<Integer,Boolean> isChecks = new HashMap<Integer,Boolean>();

    /**
     * 显示信息
     */
    private ArrayList<PersonMessage> personMessages = new ArrayList<>();

    private LayoutInflater mInflater;

    private PersonMessageActivity context;

    /**
     * 个人中心 数据适配源
     *
     * @param context 实例化界面
     */
    public PersonMessageAdapter(PersonMessageActivity context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;

    }

    public void putCheckout(int position) {
        boolean currentCheck=this.isChecks.get(position)==null?false:this.isChecks.get(position);
        this.isChecks.put(position, !currentCheck);
    }

    public void cleanCheckout() {
        isChecks.clear();
    }

    public List<PersonMessage> getCheckMsgId(){
        List<PersonMessage> ids=new ArrayList<>();
        for(Integer i:this.isChecks.keySet()){
            PersonMessage ps=personMessages.get(i);
            ids.add(ps);
        }

        return ids;
    }

    public void deleteMsgById(String msgId) throws Exception{
        for(PersonMessage pm:this.personMessages){
            if(msgId.equals(pm.getXiaoXiId())){
                this.personMessages.remove(pm);
            }
        }
    }

    /***
     * 设置指定消息已读
     * @param msgId
     */
    public void setMsgIsRead(String msgId){
        for(PersonMessage pm:this.personMessages){
            if(msgId.equals(pm.getXiaoXiId())){
                pm.setXiaoXiZhuangTai(true);
            }
        }
    }

    /***
     * 刷新列表
     *
     * @param mPersonMessages
     */
    public void refaceData(List<PersonMessage> mPersonMessages) {
        if (this.personMessages == null) {
            this.personMessages = new ArrayList<>();
        }
        this.personMessages.clear();
        this.personMessages.addAll(mPersonMessages);
        this.notifyDataSetChanged();
    }

    /**
     * 获取总个数
     *
     * @return 返回总数
     */
    @Override
    public int getCount() {
        if (personMessages != null && personMessages.size() > 0) {
            return personMessages.size();
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
    public PersonMessage getItem(int position) {
        if (position > -1 && personMessages != null && personMessages.size() > 0) {
            return personMessages.get(position);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_person_message_data, null, false);
            holder = new ViewHolder();
            holder.person_msg_item_txt_title = (TextView) convertView.findViewById(R.id.person_msg_item_txt_title);
            holder.person_msg_item_txt_concent = (TextView) convertView.findViewById(R.id.person_msg_item_txt_concent);
            holder.person_msg_item_img_read = (ImageView) convertView.findViewById(R.id.person_msg_item_img_read);
            holder.select_cb = (CheckBox) convertView.findViewById(R.id.select_cb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PersonMessage personMessage = personMessages.get(position);
        if (personMessage != null) {
            int resourceId;
            holder.person_msg_item_txt_title.setText(personMessage.getXiaoXiTitle());
            holder.person_msg_item_txt_concent.setText(personMessage.getXiaoXiNeiRong());
            if (personMessage.getXiaoXiZhuangTai()) {
                resourceId = R.drawable.person_readed;
            } else {
                resourceId = R.drawable.person_unread;
            }
            Bitmap bitmap = BitmapHelperUtil.decodeSampledBitmapFromResource(FggApplication.getInstance().getResources(), resourceId, 15, 15);
            holder.person_msg_item_img_read.setImageBitmap(bitmap);
            if (context.viewModel.isEdited()) {
                holder.select_cb.setVisibility(View.VISIBLE);
                boolean isChecks = this.isChecks.get(position) == null ? false : this.isChecks.get(position);
                holder.select_cb.setChecked(isChecks);
            } else {
                holder.select_cb.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    /**
     * 缓存视图控件
     */
    private class ViewHolder {
        /***
         * 复选按钮
         */
        private CheckBox select_cb;
        /**
         * 标题
         */
        private TextView person_msg_item_txt_title;

        /**
         * 内容
         */
        private TextView person_msg_item_txt_concent;

        /**
         * 读取状态
         */
        private ImageView person_msg_item_img_read;

    }

}
