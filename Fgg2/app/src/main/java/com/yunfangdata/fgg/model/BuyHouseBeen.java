package com.yunfangdata.fgg.model;

import java.util.List;

/**
 * 小区名字的模糊搜索的业务类
 *
 * Created by zjt on 2015-12-11.
 */
public class BuyHouseBeen {


    /**
     * resultData : [{"ResidentialAreaName":"西土城路31号院","shoucang":"请输入正确的手机号","ResidentialAreaID":8319,"Address":"西土城路31号","MatchName":"西土城路31号院","SearchFlag":"名称","DistrictFullName":"海淀区"},{"ResidentialAreaName":"西湖新村小区","shoucang":"请输入正确的手机号","ResidentialAreaID":8327,"Address":"西湖新村","MatchName":"西湖新村","SearchFlag":"名称","DistrictFullName":"昌平区"},{"ResidentialAreaName":"西三环北路23号院","shoucang":"请输入正确的手机号","ResidentialAreaID":8330,"Address":"西三环北路23号","MatchName":"西三环北路23号院","SearchFlag":"名称","DistrictFullName":"海淀区"},{"ResidentialAreaName":"西山枫林","shoucang":"请输入正确的手机号","ResidentialAreaID":8479,"Address":"香山南路168号院","MatchName":"西山枫林","SearchFlag":"名称","DistrictFullName":"石景山区"},{"ResidentialAreaName":"西直门北大街甲41号院","shoucang":"请输入正确的手机号","ResidentialAreaID":8499,"Address":"西直门外北大街甲41号","MatchName":"西直门甲41号院","SearchFlag":"名称","DistrictFullName":"西城区"},{"ResidentialAreaName":"西马庄新区","shoucang":"请输入正确的手机号","ResidentialAreaID":8526,"Address":"西马庄新区","MatchName":"西马庄新区","SearchFlag":"名称","DistrictFullName":"通州区"},{"ResidentialAreaName":"西马庄园","shoucang":"请输入正确的手机号","ResidentialAreaID":8527,"Address":"西马庄","MatchName":"西马庄园","SearchFlag":"名称","DistrictFullName":"通州区"},{"ResidentialAreaName":"西辛南区","shoucang":"请输入正确的手机号","ResidentialAreaID":8531,"Address":"西辛南区","MatchName":"西辛小区","SearchFlag":"名称","DistrictFullName":"顺义区"},{"ResidentialAreaName":"西源里小区","shoucang":"请输入正确的手机号","ResidentialAreaID":8541,"Address":"西源里小区","MatchName":"西源里","SearchFlag":"名称","DistrictFullName":"密云县"},{"ResidentialAreaName":"西宁路小区","shoucang":"请输入正确的手机号","ResidentialAreaID":8547,"Address":"西宁路小区","MatchName":"西宁路","SearchFlag":"名称","DistrictFullName":"门头沟区"}]
     * startTime : 2015-03-11 18:03:24
     * message : request success
     * endTime : 2015-03-11 18:03:24
     * success : true
     */

    private String startTime;
    private String message;
    private String endTime;
    private String success;
    /**
     * ResidentialAreaName : 西土城路31号院
     * shoucang : 请输入正确的手机号
     * ResidentialAreaID : 8319
     * Address : 西土城路31号
     * MatchName : 西土城路31号院
     * SearchFlag : 名称
     * DistrictFullName : 海淀区
     */

    private List<ResultDataEntity> resultData;

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setResultData(List<ResultDataEntity> resultData) {
        this.resultData = resultData;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getMessage() {
        return message;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSuccess() {
        return success;
    }

    public List<ResultDataEntity> getResultData() {
        return resultData;
    }

    public static class ResultDataEntity {
        private String ResidentialAreaName;
        private String shoucang;
        private int ResidentialAreaID;
        private String Address;
        private String MatchName;
        private String SearchFlag;
        private String DistrictFullName;

        public void setResidentialAreaName(String ResidentialAreaName) {
            this.ResidentialAreaName = ResidentialAreaName;
        }

        public void setShoucang(String shoucang) {
            this.shoucang = shoucang;
        }

        public void setResidentialAreaID(int ResidentialAreaID) {
            this.ResidentialAreaID = ResidentialAreaID;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public void setMatchName(String MatchName) {
            this.MatchName = MatchName;
        }

        public void setSearchFlag(String SearchFlag) {
            this.SearchFlag = SearchFlag;
        }

        public void setDistrictFullName(String DistrictFullName) {
            this.DistrictFullName = DistrictFullName;
        }

        public String getResidentialAreaName() {
            return ResidentialAreaName;
        }

        public String getShoucang() {
            return shoucang;
        }

        public int getResidentialAreaID() {
            return ResidentialAreaID;
        }

        public String getAddress() {
            return Address;
        }

        public String getMatchName() {
            return MatchName;
        }

        public String getSearchFlag() {
            return SearchFlag;
        }

        public String getDistrictFullName() {
            return DistrictFullName;
        }
    }
}
