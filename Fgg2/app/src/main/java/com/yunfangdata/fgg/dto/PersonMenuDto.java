package com.yunfangdata.fgg.dto;

import android.graphics.Bitmap;

import com.yunfang.framework.utils.BitmapHelperUtil;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.FggApplication;

/**
 * Created by 贺隽 on 2015/12/15.
 */
public class PersonMenuDto {

    /**
     * 默认构造函数
     * @param menuIndex 菜单索引
     */
    public PersonMenuDto(int menuIndex) {
        int resourceId;
        switch (menuIndex){
            case 0:
                Concent = "个人资料";
                resourceId = R.drawable.menu_geren;
                break;
            case 1:
                Concent = "消息中心";
                resourceId = R.drawable.menu_xiaoxi;
                break;
            case 2:
                Concent = "看房联系人";
                resourceId = R.drawable.menu_kanfang;
                break;
            case 3:
                Concent = "收件人信息";
                resourceId = R.drawable.menu_kanfang;
                break;
            case 4:
                Concent = "意见反馈";
                resourceId = R.drawable.menu_xiugai;
                break;
            case 5:
                Concent = "修改密码";
                resourceId = R.drawable.menu_geren;
                break;
            case 6:
                Concent = "解绑手机";
                resourceId = R.drawable.menu_kanfang;
                break;
            case 7:
                Concent = "绑定邮箱";
                resourceId = R.drawable.menu_youxiang;
                break;
            default:
                Concent = "测试菜单";
                resourceId = R.drawable.menu_kanfang;
                break;
        }
        Position = menuIndex;
        MenuIocn = BitmapHelperUtil.decodeSampledBitmapFromResource(FggApplication.getInstance().getResources(), resourceId, 28, 30);
    }

    /**
     * 姓名
     */
    public String Concent;

    /**
     * 性别
     */
    public Bitmap MenuIocn;

    /**
     * 索引
     */
    public int Position;
}
