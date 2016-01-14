package com.yunfangdata.fgg.http.task;

import com.alibaba.fastjson.JSON;
import com.yunfang.framework.httpClient.CommonRequestPackage;
import com.yunfang.framework.httpClient.IRequestTask;
import com.yunfang.framework.httpClient.RequestTypeEnum;
import com.yunfang.framework.httpClient.YFHttpClient;
import com.yunfang.framework.utils.ListUtil;
import com.yunfangdata.fgg.base.Constant;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.SearchResult;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Kevin on 2015/12/19.
 * 小区信息模糊查询
 */
public class FuzzySearchTask implements IRequestTask {

    private byte[] mData;

    @Override
    public void setContext(byte[] bytes) {
        this.mData = bytes;
    }

    public DataResult<List<SearchResult>> request(String searchStr) {
        DataResult<List<SearchResult>> result;
        try {
            String url = Constant.HTTP_ALL + Constant.HTTP_FUZZY_SEARCH;
            // 填充参数，key-value。key是接口要求传的变量名称
            Hashtable<String, Object> params = new Hashtable<>(3);
            params.put("cityName", FggApplication.getInstance().city.getCityPinYin());
            params.put("content", searchStr);
            params.put("count", 20);
            CommonRequestPackage requestPackage = new CommonRequestPackage(url,
                    RequestTypeEnum.GET, params);
            YFHttpClient.request(requestPackage, this);
            result = getResponseData();
        } catch (Exception e) {
            e.printStackTrace();
            result = new DataResult<>(false, "");
        }
        return result;
    }

    @Override
    public DataResult<List<SearchResult>> getResponseData() {
        DataResult<List<SearchResult>> result = new DataResult<>();
        try {
            if (this.mData != null) {
                String str = new String(this.mData);
                result = JSON.parseObject(str, result.getClass());
                String dataStr = result.getResultData().toString();
                List<SearchResult> searchResults = JSON.parseArray(dataStr, SearchResult.class);
                if(!ListUtil.hasData(searchResults)){
                    searchResults=new ArrayList<>();
                }
                result.setResultData(searchResults);

            } else {
                result.setSuccess(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = new DataResult<>(false, "");
        }
        return result;
    }
}
