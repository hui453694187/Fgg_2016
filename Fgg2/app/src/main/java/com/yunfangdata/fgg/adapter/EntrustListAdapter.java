package com.yunfangdata.fgg.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.model.EElistBeen;
import com.yunfangdata.fgg.ui.EntrustListActivity;



/**
 * 委托列表
 */
public class EntrustListAdapter extends BaseAdapter {
    private static final String TAG = "EntrustListAdapter";
    private static final int LOGLEVEL = 1;

    private Context context;
    private EElistBeen eElistBeen; //数据源
    private Handler myHander; //消息分发

    public void seteElistBeen(EElistBeen eElistBeen) {
        this.eElistBeen = eElistBeen;
    }

    public EntrustListAdapter(Context context, Handler myHander) {
        super();
        this.context = context;
        this.myHander = myHander;
    }

    @Override
    public int getCount() {
        if (eElistBeen != null && eElistBeen.getData() != null && eElistBeen.getData().size() > 0) {
            return eElistBeen.getData().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_entrust_list, null);
            holder = new ViewHolder();
            holder.ielNumber = (TextView) convertView.findViewById(R.id.iel_number);
            holder.ielItem = (TextView) convertView.findViewById(R.id.iel_item);
            holder.ielAddress = (TextView) convertView.findViewById(R.id.iel_address);
            holder.ielData = (TextView) convertView.findViewById(R.id.iel_data);
            holder.ielState = (TextView) convertView.findViewById(R.id.iel_state);
            holder.ielBtReminder = (TextView) convertView.findViewById(R.id.iel_bt_reminder);
            holder.ielBtRevoke = (TextView) convertView.findViewById(R.id.iel_bt_revoke);
            holder.ielBtModification = (TextView) convertView.findViewById(R.id.iel_bt_modification);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (eElistBeen != null && eElistBeen.getData() != null && eElistBeen.getData().size() > 0) {
            EElistBeen.DataEntity dataEntity = eElistBeen.getData().get(position);

            //评估编号
            holder.ielNumber.setText(dataEntity.getWeiTuoPingGuNo());
            //评估对象
            holder.ielItem.setText(dataEntity.getWeiTuoXiangMuMingCheng());
            //详细地址
            holder.ielAddress.setText(dataEntity.getWeiTuoXiangMuDiZhi());
            //评估时间
            holder.ielData.setText(dataEntity.getWeiTuoPingGuTime());
            //状态
            holder.ielState.setText(getStateName(dataEntity.getWeiTuoPingGuZhuangTai()));

            holder.ielBtReminder.setClickable(false);
            //催单
            if (dataEntity.getIsCuidan() == 0){
                holder.ielBtReminder.setClickable(true);
                holder.ielBtReminder.setText("催单");
                holder.ielBtReminder.setTextColor(context.getResources().getColor(R.color.title_blue));

                holder.ielBtReminder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message get_detail = new Message();
                        get_detail.what = EntrustListActivity.HANDLER_REMINDER;
                        Bundle data = new Bundle();
                        data.putInt(EntrustListActivity.HANDLER_LIST_ID, position);
                        get_detail.setData(data);
                        myHander.sendMessage(get_detail);
                    }
                });
            }else if (dataEntity.getIsCuidan() == 1){
                holder.ielBtReminder.setText("已催单");
                holder.ielBtReminder.setTextColor(context.getResources().getColor(R.color.gay));
            }else {
                holder.ielBtRevoke.setText("正在催单");
                holder.ielBtRevoke.setTextColor(context.getResources().getColor(R.color.gay));
            }

            //撤单
            holder.ielBtRevoke.setClickable(false);
            if (dataEntity.getIsChedan() == 0){
                holder.ielBtRevoke.setClickable(true);
                holder.ielBtRevoke.setText("撤单");
                holder.ielBtRevoke.setTextColor(context.getResources().getColor(R.color.title_blue));
                holder.ielBtRevoke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message get_detail = new Message();
                        get_detail.what = EntrustListActivity.HANDLER_REVOKE;
                        Bundle data = new Bundle();
                        data.putInt(EntrustListActivity.HANDLER_LIST_ID, position);
                        get_detail.setData(data);
                        myHander.sendMessage(get_detail);
                    }
                });

            }else if(dataEntity.getIsChedan() == 1){
                holder.ielBtRevoke.setText("已撤单");
                holder.ielBtRevoke.setTextColor(context.getResources().getColor(R.color.gay));
            }else {
                holder.ielBtRevoke.setText("正在撤单");
                holder.ielBtRevoke.setTextColor(context.getResources().getColor(R.color.gay));
            }

            //修改
            holder.ielBtModification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myHander.sendEmptyMessage(EntrustListActivity.HANDLER_SHOW_POPUPWINDOW);
                }
            });

        }
        return convertView;

    }


    private class ViewHolder {
        private TextView ielNumber;
        private TextView ielItem;
        private TextView ielAddress;
        private TextView ielData;
        private TextView ielState;
        private TextView ielBtReminder;
        private TextView ielBtRevoke;
        private TextView ielBtModification;
    }

    /**
     * 获取状态的文字
     * 委托状态:0.待支付,1.未受理,2.受理中,3.已完成,4.已撤单
     * @param state    //状态
     * @return  文字
     */
    public String getStateName(int state){
        String  retune = "未知";
        switch (state) {
            case 0: //待支付
                retune = "待支付";
                break;
            case 1: //未受理
                retune = "未受理";
                break;
            case 2: //受理中
                retune = "受理中";
                break;
            case 3: //已完成
                retune = "已完成";
                break;
            case 4: //已撤单
                retune = "已撤单";
                break;

            default:
                break;
        }
        return retune;
    }
}
