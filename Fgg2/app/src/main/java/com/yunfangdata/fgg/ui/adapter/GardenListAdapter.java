package com.yunfangdata.fgg.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.model.GardenDetail;
import com.yunfangdata.fgg.model.GardenImage;
import com.yunfangdata.fgg.ui.HomeActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2015/12/18.
 * 小区列表适配器
 */
public class GardenListAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;

    private List<GardenDetail> gardenDetailList;

    /**
     * imageLoader 参数配置
     */
    private DisplayImageOptions option;
    /**
     * 图片加载工具类
     */
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public GardenListAdapter(HomeActivity context,List<GardenDetail> gardenDetailList){
        this.context=context;
        inflater=LayoutInflater.from(this.context);
        if(this.gardenDetailList==null){
            this.gardenDetailList=new ArrayList<>();
        }
        this.gardenDetailList.addAll(gardenDetailList);
    }

    public void refreshData(List<GardenDetail> gardenDetailList){
        if(this.gardenDetailList==null){
            this.gardenDetailList=new ArrayList<>();
        }
        this.gardenDetailList.clear();
        this.gardenDetailList.addAll(gardenDetailList);
        this.notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return gardenDetailList.size();
    }

    @Override
    public Object getItem(int i) {
        return gardenDetailList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder childView;
        if(view==null){
            childView=new ViewHolder();
            view=inflater.inflate(R.layout.list_item_garden_info,null,false);
            childView.address_tv=(TextView)view.findViewById(R.id.address_tv);
            childView.garden_name_tv=(TextView)view.findViewById(R.id.garden_name_tv);
            childView.rent_price_tv=(TextView)view.findViewById(R.id.rent_price_tv);
            childView.qoq_price_tv=(TextView)view.findViewById(R.id.qoq_price_tv);
            childView.year_on_year_price_tv=(TextView)view.findViewById(R.id.year_on_year_price_tv);
            childView.title_img=(ImageView)view.findViewById(R.id.title_img);
            childView.year_on_year_img=(ImageView)view.findViewById(R.id.year_on_year_img);
            childView.qoq_price_img=(ImageView)view.findViewById(R.id.qoq_price_img);
            view.setTag(childView);
        }else{
            childView=(ViewHolder)view.getTag();
        }

        GardenDetail gardenDetail=(GardenDetail)getItem(i);
        setViewData(childView,gardenDetail);

        return view;
    }

    public void setViewData(ViewHolder childView,GardenDetail gardenDetail) {
        childView.garden_name_tv.setText(gardenDetail.getResidentialAreaName());
        childView.address_tv.setText(gardenDetail.getAddress());
        childView.rent_price_tv.setText(gardenDetail.getUnitPrice() + "");
        DecimalFormat    df   = new DecimalFormat("######0.00");
        //同比
        double yyp = gardenDetail.getRatioByLastYearForPrice();
        if (yyp <= 0) {
            childView.year_on_year_price_tv.setTextColor(context.getResources().getColor(R.color.title_blue));
            childView.year_on_year_img.setImageResource(R.drawable.sj_2);
        } else {
            childView.year_on_year_price_tv.setTextColor(context.getResources().getColor(R.color.red));
            childView.year_on_year_img.setImageResource(R.drawable.sj_1);
        }
        childView.year_on_year_price_tv.setText(df.format(yyp*100)+"%");
        //环比
        double monthPrice = gardenDetail.getRatioByLastMonthForPrice();
        if (monthPrice <= 0) {
            childView.qoq_price_tv.setTextColor(context.getResources().getColor(R.color.title_blue));
            childView.qoq_price_img.setImageResource(R.drawable.sj_2);
        } else {
            childView.qoq_price_tv.setTextColor(context.getResources().getColor(R.color.red));
            childView.qoq_price_img.setImageResource(R.drawable.sj_1);
        }
        childView.qoq_price_tv.setText(df.format(monthPrice*100)+"%");
        String titlePath="";
        for (GardenImage gi : gardenDetail.getImagesList()) {
            if ("原图".equals(gi.getType()) && "小区内环境".equals(gi.getTitle())) {
                titlePath = gi.getUrl();
            }
        }
        loaderImage(childView.title_img,titlePath);

    }

    private void loaderImage(ImageView title_img,String titlePath) {
        if (option == null) {
            // 配置ImageLoader
            option = new DisplayImageOptions.Builder()//
                    .showImageOnLoading(R.drawable.untitled_big)//
                    .showImageForEmptyUri(R.drawable.untitled_big)//
                    .showImageOnFail(R.drawable.untitled_big)//
                    .cacheInMemory(true).cacheOnDisk(true)//
                    .considerExifParams(true)//
                    .imageScaleType(ImageScaleType.EXACTLY)//
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }
        imageLoader.displayImage(titlePath, title_img, option);// 加载图片
    }

    private class ViewHolder{
        /*小区图片**/
        private ImageView title_img;
        /*小区名**/
        private TextView garden_name_tv;
        /*小区地址均价**/
        private TextView address_tv;
        /*小区均价**/
        private TextView rent_price_tv;
        /*同比**/
        private TextView year_on_year_price_tv;
        /*环比**/
        private TextView qoq_price_tv;
        /*同比图标**/
        private ImageView year_on_year_img;
        /*环图标**/
        private ImageView qoq_price_img;



    }
}
