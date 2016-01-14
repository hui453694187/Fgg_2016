package com.yunfangdata.fgg.viewmodel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 买房和卖房的
 *
 * Created by zjt on 2015-12-15.
 */
public class BuyAndSaleViewModel {

    /**
     * 朝向类型数据源
     */
    public static final String toward[] = {"请选择", "东", "南", "西", "北", "东南", "东北", "西南", "西北", "东西", "南北"};

    /**
     * 居室列表数据源
     */
    public static final  String houseTypes[] = {"一居室", "二居室", "三居室", "四居室", "五居室", "五居室以上"};
    /**
     * 居室列表数据源对应的int值
     */
    public static final  int houseTypesINT[] = {1, 2, 3, 4, 5, 9};

    /**
     * 所在楼层的取值区间
     * 第一最小--第二最大
     */
    public static final int floorminAndMax[] = {0, 100};

    /**
     * 总楼层数的取值区间
     * 第一最小--第二最大
     */
    public static final int totalfloorminAndMax[] = {0, 100};
    /**
     * 建成年代的取值区间
     * 第一最小--第二最大
     */
    public static final int builtTimeminAndMax[] = {1900, Integer.valueOf(getTime())};
    /**
     * 格式化时间
     * @return
     */
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(new Date());
    }


    /**
     * 预购总价的取值区间
     * 第一最小--第二最大
     */
    public static final int purchasePriceminAndMax[] = {1, 9999};
    /**
     * 建筑面积的取值区间
     * 第一最小--第二最大
     */
    public static final float areaminAndMax[] = {1.00f, 9999.00f};
}
