package com.yunfangdata.fgg.model;

/**
 * 委托评估
 *
 * Created by zjt on 2015-12-18.
 */
public class EntrustEvaluateBeen {


    private String Objective;
    private String ResidentialName;
    private String Bank;
    private String Area;
    private String TypeHouse;  //true=住宅
    private String Location;
    private String DetailAddress;
    private String Time; //约看时间
    private String TimeFrames;  //越看时段
    private String TimeAll;  //约看时间 + 越看时段
    private String Futures;
    private String Percent; //贷款成数
    private int PercentIndex; //贷款成数的下标
    private String Percentresult; //期待信息的计算结果
    private String LookHousePeople;
    private String LookHousePeoplePhone;
    private String UserPhone;
    private String CityName;
    private String CommunityName;

    //选填
    private String ReportPeople;
    private String ReportPeopleEmail;
    private String ReportPeopleAddress;
    private String Information;
    private String Mark;

    public String getPercentresult() {
        return Percentresult;
    }

    public void setPercentresult(String percentresult) {
        Percentresult = percentresult;
    }

    public int getPercentIndex() {
        return PercentIndex;
    }

    public void setPercentIndex(int percentIndex) {
        PercentIndex = percentIndex;
    }

    public String getTimeAll() {
        return TimeAll;
    }

    public void setTimeAll(String timeAll) {
        TimeAll = timeAll;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCommunityName() {
        return CommunityName;
    }

    public void setCommunityName(String communityName) {
        CommunityName = communityName;
    }

    public String getLookHousePeople() {
        return LookHousePeople;
    }

    public String getLookHousePeoplePhone() {
        return LookHousePeoplePhone;
    }

    public void setLookHousePeoplePhone(String lookHousePeoplePhone) {
        LookHousePeoplePhone = lookHousePeoplePhone;
    }

    public String getReportPeopleEmail() {
        return ReportPeopleEmail;
    }

    public void setReportPeopleEmail(String reportPeopleEmail) {
        ReportPeopleEmail = reportPeopleEmail;
    }

    public String getReportPeopleAddress() {
        return ReportPeopleAddress;
    }

    public void setReportPeopleAddress(String reportPeopleAddress) {
        ReportPeopleAddress = reportPeopleAddress;
    }

    public void setLookHousePeople(String lookHousePeople) {
        LookHousePeople = lookHousePeople;
    }

    public String getReportPeople() {
        return ReportPeople;
    }

    public void setReportPeople(String reportPeople) {
        ReportPeople = reportPeople;
    }

    public String getObjective() {
        return Objective;
    }

    public void setObjective(String objective) {
        Objective = objective;
    }

    public String getResidentialName() {
        return ResidentialName;
    }

    public void setResidentialName(String residentialName) {
        ResidentialName = residentialName;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getTypeHouse() {
        return TypeHouse;
    }

    public void setTypeHouse(String typeHouse) {
        TypeHouse = typeHouse;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDetailAddress() {
        return DetailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        DetailAddress = detailAddress;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTimeFrames() {
        return TimeFrames;
    }

    public void setTimeFrames(String timeFrames) {
        TimeFrames = timeFrames;
    }

    public String getFutures() {
        return Futures;
    }

    public void setFutures(String futures) {
        Futures = futures;
    }

    public String getPercent() {
        return Percent;
    }

    public void setPercent(String percent) {
        Percent = percent;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public String getMark() {
        return Mark;
    }

    public void setMark(String mark) {
        Mark = mark;
    }
}
