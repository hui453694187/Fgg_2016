package com.yunfangdata.fgg.http.task;

import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.PersonContacts;
import com.yunfangdata.fgg.model.ResultInfo;

import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by 贺隽 on 2015/12/16.
 * 添加看房联系人
 */
public class ContactsSaveTask implements IRequestTask {

    /**
     * 返回数据
     */
    private byte[] mData;

    /**
     * 设置返回数据
     *
     * @param bytes 二进制数据
     */
    @Override
    public void setContext(byte[] bytes) {
        this.mData = bytes;
    }

    /**
     * 获取返回会数据
     *
     * @return 执行结果
     */
    @Override
    public ResultInfo<Boolean> getResponseData() {
        ResultInfo<Boolean> result = new ResultInfo<>();
        try {
            if (this.mData != null) {
                String dataString = new String(this.mData);
                JSONObject json = new JSONObject(dataString);
                if (json.getString("success").equals("true")) {
                    result.Success = true;
                    result.Message = json.getString("msg");
                }else {
                    result.Success = false;
                    result.Message = json.getString("msg");
                }
            }
        } catch (Exception e) {
            result.Success = false;
            result.Message = "服务器异常！";
        }
        return result;
    }

    /**
     * 执行接口
     * @param personContacts 保存对象
     * @return 结果对象
     */
    public ResultInfo<Boolean> request(String userName, PersonContacts personContacts) {
        ResultInfo<Boolean> result = new ResultInfo<>();
        String url = Constant.HTTP_ALL + Constant.HTTP_CONTACTSSAVE;
        // 填充参数，key-value。key是接口要求传的变量名称
        Hashtable<String, Object> params = new Hashtable<>(7);
        params.put("phone", userName);
        params.put("linkPersonName", personContacts.getLianXiRenName());
        params.put("linkPersonPhone", personContacts.getShouJi());
        params.put("areaCode", "");
        params.put("telPhone", "");
        params.put("ext", "");
        params.put("isDefault", personContacts.getMoRenLianXiRen());
        CommonRequestPackage requestPackage = new CommonRequestPackage(url,
                RequestTypeEnum.GET, params);
        try {
            YFHttpClient.request(requestPackage, this);
            result = getResponseData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
