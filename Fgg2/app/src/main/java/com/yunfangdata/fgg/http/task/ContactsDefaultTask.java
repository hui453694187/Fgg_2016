package com.yunfangdata.fgg.http.task;

import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.model.ResultInfo;

import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by 贺隽 on 2015/12/16.
 * 删除勘察联系人
 */
public class ContactsDefaultTask implements IRequestTask {

    /**
     * 返回数据
     */
    private byte[] mData;

    /**
     * 设置数据
     * @param bytes
     */
    @Override
    public void setContext(byte[] bytes) {
        this.mData = bytes;
    }

    /**
     *获取返回数据
     * @return
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
            result.Message = "服务器异常";
        }
        return result;
    }

    /**
     * 执行接口
     * @param id 联系人编号
     * @return 结果对象
     */
    public ResultInfo<Boolean> request(String userName,String id) {
        ResultInfo<Boolean> result = new ResultInfo<>();
        String url = Constant.HTTP_ALL + Constant.HTTP_CONTACTSDEFAULT;
        // 填充参数，key-value。key是接口要求传的变量名称
        Hashtable<String, Object> params = new Hashtable<String, Object>(2);
        params.put("phone", userName);
        params.put("linkPersonID", id);
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
