package com.yunfangdata.fgg.logic;

import com.yunfangdata.fgg.http.task.UpdataTask;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.UpdataBeen;

/**
 * Created by Administrator on 2015-12-30.
 */
public class SettingOperator {

    /***
     * 版本更新
     * @return
     */
    public static ResultInfo<UpdataBeen> getUpdate() {
        ResultInfo<UpdataBeen> request = null;
        try {
            UpdataTask updataTask = new UpdataTask();
            request = updataTask.request();
        } catch (Exception e) {
            request = new ResultInfo<>(false);
            e.printStackTrace();
        }
        return request;
    }
}
