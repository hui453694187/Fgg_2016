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
 * 查询某一个看房联系人信息
 */
public class ContactsQueryByIdTask implements IRequestTask {

    /**
     * 返回数据
     */
    private byte[] mData;

    /**
     * 设置返回数据
     *
     * @param bytes
     */
    @Override
    public void setContext(byte[] bytes) {
        this.mData = bytes;
    }

    /**
     * 获取返回数据
     *
     * @return
     */
    @Override
    public ResultInfo<PersonContacts> getResponseData() {
        ResultInfo<PersonContacts> result = new ResultInfo<>();
        try {
            if (this.mData != null) {
                String dataString = new String(this.mData);
                JSONObject json = new JSONObject(dataString);
                if (json.getString("success").equals("true")) {
                    dataString = json.getString("data");
                    JSONObject temp= new JSONObject(dataString);
                    result.Data = new PersonContacts(temp);
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
     *
     * @param id 联系人编号
     * @return 结果对象
     */
    public ResultInfo<PersonContacts> request(String id) {
        ResultInfo<PersonContacts> result = new ResultInfo<>();
        String url = Constant.HTTP_ALL + Constant.HTTP_CONTACTSQUERYBYID;
        // 填充参数，key-value。key是接口要求传的变量名称
        Hashtable<String, Object> params = new Hashtable<String, Object>(1);
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
