package com.yunfangdata.fgg.http.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.EntrustEvaluateBeen;
import com.yunfangdata.fgg.model.EntrustEvaluateReturnBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.utils.ZLog;

import java.util.Hashtable;

/**
 * 进行委托评估
 * <p/>
 * Created by zjt on 2015-12-10.
 */
public class EntrustEvaluateTask implements IRequestTask {

    private static final String TAG = "EntrustEvaluateTask";
    private static final int LOGLEVEL = 1;
    /**
     * 响应数据
     */
    private byte[] mData;

    @Override
    public void setContext(byte[] data) {
        this.mData = data;
    }

    @Override
    public ResultInfo<EntrustEvaluateReturnBeen> getResponseData() {
        ResultInfo<EntrustEvaluateReturnBeen> result = new ResultInfo<EntrustEvaluateReturnBeen>();
        if (mData != null) {
            String resultStr = new String(mData);
            try {
                if (!TextUtils.isEmpty(resultStr)) {
                    ZLog.Zlogi("数据返回 = " + resultStr, TAG, LOGLEVEL);
                    EntrustEvaluateReturnBeen entrustEvaluateReturnBeen = JSON.parseObject(resultStr, EntrustEvaluateReturnBeen.class);
                    ZLog.Zlogi("entrustEvaluateReturnBeen = " + entrustEvaluateReturnBeen.toString(), TAG, LOGLEVEL);
                    if (!entrustEvaluateReturnBeen.getSuccess().equals("false")) {
                        result.Data = entrustEvaluateReturnBeen;
                    } else {
                        result.Success = false;
                        result.Message = entrustEvaluateReturnBeen.getMsg();
                    }
                }
            } catch (Exception e) {
                result.Success = false;
                result.Message = "服务器异常! 请重试。";
                e.printStackTrace();
            }
        } else {
            result.Success = false;
            result.Message = "请求服务器数据失败！";
        }
        return result;
    }


    public ResultInfo<EntrustEvaluateReturnBeen> request(EntrustEvaluateBeen eeB) {
        ResultInfo<EntrustEvaluateReturnBeen> result = null;

        String url = Constant.HTTP_ALL + Constant.HTTP_GET_ENTRUST;
//        String url = "http://123.56.104.193:10082/yunhenewApp/reportController/getIosVersion";
        Hashtable<String, Object> params = new Hashtable<String, Object>(20);
        //估价目的
        params.put("gjmd", eeB.getObjective());
        //贷款银行
        params.put("dkyh", eeB.getBank());
        //详细地址
        params.put("xxdz", eeB.getDetailAddress());
        //建筑面积 浮点数
        params.put("jzmj", eeB.getArea());
        //物业类型
        params.put("wylx", eeB.getTypeHouse());
        //看房时间
        String s9 = (eeB.getTimeAll() != null) ? eeB.getTimeAll() : "";
        params.put("kfsj", s9);
        //看房联系人姓名
        String s10 = (eeB.getLookHousePeople() != null) ? eeB.getLookHousePeople() : "";
        params.put("lxrxm", s10);
        //看房联系人电话
        String s11 = (eeB.getLookHousePeoplePhone() != null) ? eeB.getLookHousePeoplePhone() : "";
        params.put("lxrdh", s11);

//        //接受报告邮箱
//        String s1 = (eeB.getReportPeople() != null) ? eeB.getReportPeople() : "";
//        params.put("bgjsEmail", s1);
//        //纸质报告邮寄地址
//        String s2 = (eeB.getReportPeopleAddress() != null) ? eeB.getReportPeopleAddress() : "";
//        params.put("bgjsEmail", s2);

        String s3 = (eeB.getReportPeople() != null) ? eeB.getReportPeople() : "";
        //资料收取方式
        params.put("zlsqfs", s3);

        //备注信息
        String s4 = (eeB.getMark() != null) ? eeB.getMark() : "";
        params.put("bgjsEmail", s4);

        //客户手机
        params.put("phone", eeB.getUserPhone());
        //城市名称
        params.put("city", eeB.getCityName());
        //小区,名称
        params.put("community", eeB.getResidentialName());
        //楼栋名称
        params.put("building", "");
        //houseNum户号
        params.put("houseNum", "");
        //评估入口
        params.put("pinggurukouId","402886fb4d705112014d705121280000");
        //期待信息
        params.put("qdxx",eeB.getPercentresult());


        CommonRequestPackage commReauest = new CommonRequestPackage(url, RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(commReauest, this);
            result = getResponseData();
        } catch (Exception e) {
            if (result == null) {
                result = new ResultInfo<EntrustEvaluateReturnBeen>();
            }
            result.Success = false;
            result.Message = e.getMessage();
        }

        return result;
    }


}
