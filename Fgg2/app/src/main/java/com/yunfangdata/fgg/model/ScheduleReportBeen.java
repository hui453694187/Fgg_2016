package com.yunfangdata.fgg.model;

import java.util.List;

/**
 * 获取进度查询的返回
 *
 * Created by zjt on 2015-12-14.
 */
public class ScheduleReportBeen {

    /**
     * Success : true
     * msg : 成功
     * data : [{"BaoGaoBiaoHao":"462097220332","state":"0","BaoGaoZhuangTai":"委托确认中"}]
     * others :
     */

    private String Success;
    private String msg;
    private String others;
    /**
     * BaoGaoBiaoHao : 462097220332
     * state : 0
     * BaoGaoZhuangTai : 委托确认中
     */

    private List<DataEntity> data;

    public void setSuccess(String Success) {
        this.Success = Success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getSuccess() {
        return Success;
    }

    public String getMsg() {
        return msg;
    }

    public String getOthers() {
        return others;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String BaoGaoBiaoHao;
        private String state;
        private String BaoGaoZhuangTai;

        public void setBaoGaoBiaoHao(String BaoGaoBiaoHao) {
            this.BaoGaoBiaoHao = BaoGaoBiaoHao;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setBaoGaoZhuangTai(String BaoGaoZhuangTai) {
            this.BaoGaoZhuangTai = BaoGaoZhuangTai;
        }

        public String getBaoGaoBiaoHao() {
            return BaoGaoBiaoHao;
        }

        public String getState() {
            return state;
        }

        public String getBaoGaoZhuangTai() {
            return BaoGaoZhuangTai;
        }
    }
}
