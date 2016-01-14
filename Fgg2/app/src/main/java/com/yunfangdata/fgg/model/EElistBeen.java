package com.yunfangdata.fgg.model;

import java.util.List;

/**
 * 委托列表
 *
 * Created by zjt on 2015-12-19.
 */
public class EElistBeen {


    /**
     * data : [{"weiTuoPingGuNo":"462097220332","weiTuoPingGuId":"8a2c58515190af4301519e26d0600002","weiTuoXiangMuMingCheng":"西摸摸","isChedan":1,"isCuidan":1,"weiTuoPingGuZhuangTai":4,"weiTuoPingGuTime":"2015-12-14 09:42:02","weiTuoXiangMuDiZhi":"北京 海淀 咯噢耶我用"}]
     * success : true
     */

    private String success;
    /**
     * weiTuoPingGuNo : 462097220332
     * weiTuoPingGuId : 8a2c58515190af4301519e26d0600002
     * weiTuoXiangMuMingCheng : 西摸摸
     * isChedan : 1
     * isCuidan : 1
     * weiTuoPingGuZhuangTai : 4
     * weiTuoPingGuTime : 2015-12-14 09:42:02
     * weiTuoXiangMuDiZhi : 北京 海淀 咯噢耶我用
     */

    private List<DataEntity> data;

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String weiTuoPingGuNo;
        private String weiTuoPingGuId;
        private String weiTuoXiangMuMingCheng;
        private int isChedan;
        private int isCuidan;
        private int weiTuoPingGuZhuangTai;
        private String weiTuoPingGuTime;
        private String weiTuoXiangMuDiZhi;

        public void setWeiTuoPingGuNo(String weiTuoPingGuNo) {
            this.weiTuoPingGuNo = weiTuoPingGuNo;
        }

        public void setWeiTuoPingGuId(String weiTuoPingGuId) {
            this.weiTuoPingGuId = weiTuoPingGuId;
        }

        public void setWeiTuoXiangMuMingCheng(String weiTuoXiangMuMingCheng) {
            this.weiTuoXiangMuMingCheng = weiTuoXiangMuMingCheng;
        }

        public void setIsChedan(int isChedan) {
            this.isChedan = isChedan;
        }

        public void setIsCuidan(int isCuidan) {
            this.isCuidan = isCuidan;
        }

        public void setWeiTuoPingGuZhuangTai(int weiTuoPingGuZhuangTai) {
            this.weiTuoPingGuZhuangTai = weiTuoPingGuZhuangTai;
        }

        public void setWeiTuoPingGuTime(String weiTuoPingGuTime) {
            this.weiTuoPingGuTime = weiTuoPingGuTime;
        }

        public void setWeiTuoXiangMuDiZhi(String weiTuoXiangMuDiZhi) {
            this.weiTuoXiangMuDiZhi = weiTuoXiangMuDiZhi;
        }

        public String getWeiTuoPingGuNo() {
            return weiTuoPingGuNo;
        }

        public String getWeiTuoPingGuId() {
            return weiTuoPingGuId;
        }

        public String getWeiTuoXiangMuMingCheng() {
            return weiTuoXiangMuMingCheng;
        }

        public int getIsChedan() {
            return isChedan;
        }

        public int getIsCuidan() {
            return isCuidan;
        }

        public int getWeiTuoPingGuZhuangTai() {
            return weiTuoPingGuZhuangTai;
        }

        public String getWeiTuoPingGuTime() {
            return weiTuoPingGuTime;
        }

        public String getWeiTuoXiangMuDiZhi() {
            return weiTuoXiangMuDiZhi;
        }
    }
}
