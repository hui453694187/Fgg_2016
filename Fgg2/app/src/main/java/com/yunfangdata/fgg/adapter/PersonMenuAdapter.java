package com.yunfangdata.fgg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.dto.PersonMenuDto;
import com.yunfangdata.fgg.model.BuyHouseBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 贺隽 on 2015/12/14.
 * 个人中心 数据适配源
 */
public class PersonMenuAdapter extends BaseAdapter {
    /**
     * 标记信息
     */
    private static final String TAG = "PersonMenuAdapter";

    /**
     * 实例化界面
     */
    private Context context;

    /**
     * 菜单信息
     */
    private ArrayList<PersonMenuDto> personMenus = new ArrayList<>();

    /**
     * 个人中心 数据适配源
     * @param context 实例化界面
     * @param mPersonMenus 数据源
     */
    public PersonMenuAdapter(Context context, ArrayList<PersonMenuDto> mPersonMenus) {
        super();
        this.context = context;
        this.personMenus = mPersonMenus;
    }

    /**
     * 获取总个数
     * @return 返回总数
     */
    @Override
    public int getCount() {
        if (personMenus != null && personMenus.size() > 0) {
            return personMenus.size();
        } else {
            return 0;
        }
    }

    /**
     * 获取当前实例
     * @param position 索引
     * @return 返回当前对象
     */
    @Override
    public Object getItem(int position) {
        if(position > -1 && personMenus != null && personMenus.size() > 0){
            return personMenus.get(position);
        }else{
            return  null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 返回每一行的数据
     * @param position 当前索引
     * @param convertView 当前操作视图
     * @param parent 当前操作视图组
     * @return 返回新绘制的视图
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_person_menu, null);
            holder = new ViewHolder();
            holder.person_item_txt_concent = (TextView) convertView.findViewById(R.id.person_item_txt_concent);
            holder.person_item_img_menu = (ImageView) convertView.findViewById(R.id.person_item_img_menu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PersonMenuDto personMenu = personMenus.get(position);
        if (personMenu != null){
            //设置菜单
            holder.person_item_txt_concent.setText(personMenu.Concent);
            //设置菜单图标
            holder.person_item_img_menu.setImageBitmap(personMenu.MenuIocn);
        }
        return convertView;
    }

    /**
     * 缓存视图控件
     */
    private class ViewHolder {

        /**
         * 菜单名称
         */
        private TextView person_item_txt_concent;

        /**
         * 菜单图标
         */
        private ImageView person_item_img_menu;

    }

}
