package com.yunfangdata.fgg.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.base.NewBaseActivity;
import com.yunfangdata.fgg.helper.IntentHelper;
import com.yunfangdata.fgg.http.task.GetAroundResidentialTask;
import com.yunfangdata.fgg.http.task.GetHouseDetailsTask;
import com.yunfangdata.fgg.model.AroundResidentialBeen;
import com.yunfangdata.fgg.model.HouseDetailBeen;
import com.yunfangdata.fgg.model.ResultInfo;

import java.util.List;

public class HouseDetailActivity extends NewBaseActivity {
    private static final String TAG = "HouseDetailActivity";
    private static final int LOGLEVEL = 1;
    /**
     * 获取房子的详情
     */
    private final int HANDLE_GET_HOUSE_DETAIL = 110;

    /**
     * 获取房子的周边的详情
     */
    private final int HANDLE_GET_AROUND_DETAIL = 111;

    /**
     * 默认的城市名字
     */
    private String cityName = "beijing";
    /**
     * 默认的小区id
     */
    private int residentialAreaID = 12944;
    /**
     * 小区名字
     */
    private String residentialAreaName = null;

    //imgLoad的配置
    private DisplayImageOptions displayImageOptions;
    private ImageSize mImageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        cityName = FggApplication.getInstance().city.getCityPinYin();
        initImageView();

        assignViews();

        getIntentData();

        //只有传过来有数据才进行网络请求
        getHouseDetail();

        getAroundDetail();

    }

    /**
     * 获取传递过来的数据
     */
    private void getIntentData() {
        //获取传过来数据
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            //获取传过来的小区id
            int id = extras.getInt(Constant.KEY_INTENT_TO_HOUSE_DETAIL_RESIDENTIALAREAID);
            //获取传过来的城市名称
            String name = extras.getString(Constant.KEY_INTENT_TO_HOUSE_DETAIL_CITYNAME);
            if (name != null && !name.isEmpty()) {
//                cityName = name;
            }
            residentialAreaID = id;
        }
    }

    /**
     * 初次化ImageLoader配置参数
     */
    private void initImageView() {
        // 创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).writeDebugLogs() // 打印log信息
                .build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);


        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.hd_defoult_fgg) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.hd_defoult_fgg)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.hd_defoult_fgg) //设置图片加载/解码过程中错误时候显示的图片
                .build();
        //设置图片的大小
        mImageSize = new ImageSize(200, 200);
    }

    /**
     * 获取房子的详情
     */
    private void getHouseDetail() {
        Message get_detail = new Message();
        get_detail.what = HANDLE_GET_AROUND_DETAIL;
        mBackgroundHandler.sendMessage(get_detail);
    }

    /**
     * 获取房子的周边的详情
     */
    private void getAroundDetail() {
        Message get_detail = new Message();
        get_detail.what = HANDLE_GET_HOUSE_DETAIL;
        mBackgroundHandler.sendMessage(get_detail);
    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        Message resultMsg = new Message();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_HOUSE_DETAIL:// 获取房子详情F
                GetHouseDetailsTask getHouseDetailsTask = new GetHouseDetailsTask();
                ResultInfo<HouseDetailBeen> resultInfo = getHouseDetailsTask.request(cityName, residentialAreaID);
                resultMsg.obj = resultInfo;
                mUiHandler.sendMessage(resultMsg);
                break;
            case HANDLE_GET_AROUND_DETAIL:// 获取房子周边详情
                GetAroundResidentialTask getAroundResidentialTask = new GetAroundResidentialTask();
                ResultInfo<AroundResidentialBeen> request = getAroundResidentialTask.request(cityName, residentialAreaID);
                resultMsg.obj = request;
                mUiHandler.sendMessage(resultMsg);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_HOUSE_DETAIL://  获取房子详情
                ResultInfo<HouseDetailBeen> result = (ResultInfo<HouseDetailBeen>) msg.obj;
                if (result.Success) {
                    if (result.Data != null) {
                        HouseDetailBeen houseDetailBeen = result.Data;
                        upDataView(houseDetailBeen);
                    } else {
                        showToast(result.Message);
                    }
                } else {
                    if (result.Message != null) {
                        showNetWorkError(result.Message);
                    }
                }
                break;
            case HANDLE_GET_AROUND_DETAIL:// 获取房子周边详情
                ResultInfo<AroundResidentialBeen> resultAround = (ResultInfo<AroundResidentialBeen>) msg.obj;
                if (resultAround.Success) {
                    if (resultAround.Data != null) {
                        AroundResidentialBeen aroundDetailBeen = resultAround.Data;
                        updataViewAround(aroundDetailBeen);
                    } else {
                        showToast(resultAround.Message);
                    }
                } else {
                    if (resultAround.Message != null) {
                        showNetWorkError(resultAround.Message);
                    }
                }
                break;

        }
    }


    /**
     * 根据返回的信息,显示不同的Toast
     *
     * @param massage // result.Message
     */
    private void showNetWorkError(String massage) {
        if (massage.contains("Value Connection")) {
            showToast("获取信息失败,无法连接服务器");
        } else if (massage.contains("Value Connect")) {
            showToast("获取信息失败,连接服务器超时");
        } else if (massage.contains("lang")) {
            showToast("获取信息失败");
        } else {
            showToast("获取信息失败，" + massage);
        }
    }


    /**
     * 根据返回的小区详情数据更新页面
     *
     * @param houseDetailBeen
     */
    private void upDataView(HouseDetailBeen houseDetailBeen) {

        HouseDetailBeen.ResultDataEntity resultData = houseDetailBeen.getResultData();
        if (resultData != null) {

            //首先显示图片
            List<HouseDetailBeen.ResultDataEntity.ImagesListEntity> imagesList = resultData.getImagesList();
            if (imagesList != null && imagesList.size() > 0) {
                HouseDetailBeen.ResultDataEntity.ImagesListEntity imagesListEntity = imagesList.get(2);
                String imageUrl = imagesListEntity.getUrl();

                ImageLoader.getInstance().loadImage(imageUrl, mImageSize, displayImageOptions, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        hdTopImageView.setImageBitmap(loadedImage);
                    }
                });
            }

            //顶部的名字
            hdHouseName.setText(resultData.getResidentialAreaName());
            //给传递到估值页面的小区名字赋值
            residentialAreaName = resultData.getResidentialAreaName();
            //区域
            hdHouseDistrictName.setText(resultData.getDistrictName());

            //区域名字
            hdHouseResidentialAreaName.setText(resultData.getResidentialAreaName());

            //小区均价
            hdUnitPriceValue.setText(resultData.getUnitPrice() + getString(R.string.hd_m_pingfang));

            java.text.DecimalFormat df = new java.text.DecimalFormat("######0.00");
            double value = 0; //保存取出的值
            int ImageResource;//选择显示的图片
            //环比上月
            value = resultData.getRatioByLastMonthForPrice();
            hdMonthForRentValue.setText(df.format(value * 100) + "%");
            ImageResource = (value <= 0) ? R.mipmap.hd_rent_down : R.mipmap.hd_rent_up;
            hdMonthForRentImageView.setImageResource(ImageResource);
            int color = (value <= 0) ? R.color.title_blue : R.color.red;
            hdMonthForRentValue.setTextColor(getResources().getColor(color));

            //同比去年
            value = resultData.getRatioByLastYearForPrice();
            hdYearForPriceValue.setText(df.format(value * 100) + "%");
            ImageResource = (value <= 0) ? R.mipmap.hd_rent_down : R.mipmap.hd_rent_up;
            hdYearForPriceImageView.setImageResource(ImageResource);
            int color2 = (value <= 0) ? R.color.title_blue : R.color.red;
            hdYearForPriceValue.setTextColor(getResources().getColor(color2));

            //租金信息
            hdRentPriceValue.setText(resultData.getRentPrice() + getString(R.string.hd_yuen_month));

        }
    }

    /**
     * 根据返回的小区 周边 数据更新页面
     *
     * @param aroundDetailBeen
     */
    private void updataViewAround(AroundResidentialBeen aroundDetailBeen) {
        if (aroundDetailBeen != null) {

            AroundResidentialBeen.ResultDataEntity resultData = aroundDetailBeen.getResultData();
            //周边--医院
            List<AroundResidentialBeen.ResultDataEntity.HospitalMapEntity> hospitalMap = resultData.getHospitalMap();
            if (hospitalMap != null && hospitalMap.size() > 0) {
                String value = "";
                for (int i = 0; i < hospitalMap.size(); i++) {
                    ///、/
                    value = value + hospitalMap.get(i).getName();
                    if (i != (hospitalMap.size() - 1)) {
                        value = value + "、";
                    }
                }
                hdAroundHospitalValue.setText(value);
            }

            //周边--超市
            List<AroundResidentialBeen.ResultDataEntity.SupermarketMapEntity> supermarketMap = resultData.getSupermarketMap();
            if (supermarketMap != null && supermarketMap.size() > 0) {
                String value = "";
                for (int i = 0; i < supermarketMap.size(); i++) {
                    ///、/
                    value = value + supermarketMap.get(i).getName();
                    if (i != (supermarketMap.size() - 1)) {
                        value = value + "、";
                    }
                }
                hdAroundSupermarketValue.setText(value);
            }

            //周边--银行
            List<AroundResidentialBeen.ResultDataEntity.BankMapEntity> bankMap = resultData.getBankMap();
            if (bankMap != null && bankMap.size() > 0) {
                String value = "";
                for (int i = 0; i < bankMap.size(); i++) {
                    ///、/
                    value = value + bankMap.get(i).getName();
                    if (i != (bankMap.size() - 1)) {
                        value = value + "、";
                    }
                }
                hdAroundBankValue.setText(value);
            }

            //周边--公交
            List<AroundResidentialBeen.ResultDataEntity.BusStationMapEntity> busStationMap = resultData.getBusStationMap();
            if (busStationMap != null && busStationMap.size() > 0) {
                String value = "";
                for (int i = 0; i < busStationMap.size(); i++) {
                    ///、/
                    value = value + busStationMap.get(i).getName();
                    if (i != (busStationMap.size() - 1)) {
                        value = value + "、";
                    }
                }
                hdAroundBusValue.setText(value);
            }

            //周边--地铁
            List<AroundResidentialBeen.ResultDataEntity.SubwayStationMapEntity> subwayStationMap = resultData.getSubwayStationMap();
            if (subwayStationMap != null && subwayStationMap.size() > 0) {
                String value = "";
                for (int i = 0; i < subwayStationMap.size(); i++) {
                    ///、/
                    value = value + subwayStationMap.get(i).getName();
                    if (i != (subwayStationMap.size() - 1)) {
                        value = value + "、";
                    }
                }
                hdAroundMetroValue.setText(value);
            }
        }
    }


    private ImageView hdTopImageView;
    private TextView hdHouseName;
    private TextView hdHouseDistrictName;
    private TextView hdHouseResidentialAreaName;
    private ImageView hdStarImageView1;
    private ImageView hdStarImageView2;
    private ImageView hdStarImageView3;
    private ImageView hdStarImageView4;
    private ImageView hdStarImageView5;
    private TextView hdUnitPrice;
    private TextView hdUnitPriceValue;
    private TextView hdMonthForRent;
    private TextView hdMonthForRentValue;
    private ImageView hdMonthForRentImageView;
    private TextView hdYearForPrice;
    private TextView hdYearForPriceValue;
    private ImageView hdYearForPriceImageView;
    private TextView hdRentPrice;
    private TextView hdRentPriceValue;
    private TextView hdAroundName;
    private TextView hdAroundSchoolName;
    private TextView hdAroundSchoolValue;
    private TextView hdAroundHospitalName;
    private TextView hdAroundHospitalValue;
    private TextView hdAroundSupermarketName;
    private TextView hdAroundSupermarketValue;
    private TextView hdAroundBankName;
    private TextView hdAroundBankValue;
    private TextView hdAroundBusName;
    private TextView hdAroundBusValue;
    private TextView hdAroundMetroName;
    private TextView hdAroundMetroValue;
    private TextView hdTipsTv;
    private Button hdBtBuyValues;
    private Button hdBtSellValues;
    private ImageView hdButtonBack;


    /**
     * 初次化界面UI
     */
    private void assignViews() {
        hdTopImageView = (ImageView) findViewById(R.id.hd_top_imageView);
        hdHouseName = (TextView) findViewById(R.id.hd_house_name);
        hdHouseDistrictName = (TextView) findViewById(R.id.hd_house_districtName);
        hdHouseResidentialAreaName = (TextView) findViewById(R.id.hd_house_ResidentialAreaName);
        hdStarImageView1 = (ImageView) findViewById(R.id.hd_star_imageView1);
        hdStarImageView2 = (ImageView) findViewById(R.id.hd_star_imageView2);
        hdStarImageView3 = (ImageView) findViewById(R.id.hd_star_imageView3);
        hdStarImageView4 = (ImageView) findViewById(R.id.hd_star_imageView4);
        hdStarImageView5 = (ImageView) findViewById(R.id.hd_star_imageView5);
        hdUnitPrice = (TextView) findViewById(R.id.hd_UnitPrice);
        hdUnitPriceValue = (TextView) findViewById(R.id.hd_UnitPrice_value);
        hdMonthForRent = (TextView) findViewById(R.id.hd_MonthForRent);
        hdMonthForRentValue = (TextView) findViewById(R.id.hd_MonthForRent_value);
        hdMonthForRentImageView = (ImageView) findViewById(R.id.hd_MonthForRent_imageView);
        hdYearForPrice = (TextView) findViewById(R.id.hd_YearForPrice);
        hdYearForPriceValue = (TextView) findViewById(R.id.hd_YearForPrice_value);
        hdYearForPriceImageView = (ImageView) findViewById(R.id.hd_YearForPrice_imageView);
        hdRentPrice = (TextView) findViewById(R.id.hd_RentPrice);
        hdRentPriceValue = (TextView) findViewById(R.id.hd_RentPrice_value);
        hdAroundName = (TextView) findViewById(R.id.hd_around_name);
        hdAroundSchoolName = (TextView) findViewById(R.id.hd_around_school_name);
        hdAroundSchoolValue = (TextView) findViewById(R.id.hd_around_school_value);
        hdAroundHospitalName = (TextView) findViewById(R.id.hd_around_hospital_name);
        hdAroundHospitalValue = (TextView) findViewById(R.id.hd_around_hospital_value);
        hdAroundSupermarketName = (TextView) findViewById(R.id.hd_around_Supermarket_name);
        hdAroundSupermarketValue = (TextView) findViewById(R.id.hd_around_Supermarket_value);
        hdAroundBankName = (TextView) findViewById(R.id.hd_around_bank_name);
        hdAroundBankValue = (TextView) findViewById(R.id.hd_around_bank_value);
        hdAroundBusName = (TextView) findViewById(R.id.hd_around_bus_name);
        hdAroundBusValue = (TextView) findViewById(R.id.hd_around_bus_value);
        hdAroundMetroName = (TextView) findViewById(R.id.hd_around_metro_name);
        hdAroundMetroValue = (TextView) findViewById(R.id.hd_around_metro_value);
        hdTipsTv = (TextView) findViewById(R.id.hd_tips_tv);
        hdBtBuyValues = (Button) findViewById(R.id.hd_bt_buy_values);
        hdBtSellValues = (Button) findViewById(R.id.hd_bt_sell_values);
        hdButtonBack = (ImageView) findViewById(R.id.hd_button_back);

        hdBtBuyValues.setOnClickListener(myOnClickListener);
        hdBtSellValues.setOnClickListener(myOnClickListener);
        hdButtonBack.setOnClickListener(myOnClickListener);
    }


    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hd_bt_buy_values: //买房估值

                    //跳转到买房页面
                    IntentHelper.intentToBuyHouse(HouseDetailActivity.this, cityName, residentialAreaName, residentialAreaID);
                    break;

                case R.id.hd_bt_sell_values: //卖饭估值
                    //跳转到卖房页面
                    IntentHelper.intentToSaleHouse(HouseDetailActivity.this, cityName, residentialAreaName, residentialAreaID);

                    break;

                case R.id.hd_button_back: //点击返回\
                    finish();
                    break;

                default:
                    break;
            }
        }

    };
}
