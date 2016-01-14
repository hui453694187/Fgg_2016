package com.yunfangdata.fgg.model;

/**
 * Created by Kecin on 2015/12/19.
 * 小区模糊查询结果
 */
public class SearchResult {

    private String ResidentialAreaName;

    private String shoucang;

    private int ResidentialAreaID;

    private String Address;

    private String MatchName;

    private String SearchFlag;

    private String DistrictFullName;

    public String getResidentialAreaName() {
        return ResidentialAreaName;
    }

    public void setResidentialAreaName(String residentialAreaName) {
        ResidentialAreaName = residentialAreaName;
    }

    public String getShoucang() {
        return shoucang;
    }

    public void setShoucang(String shoucang) {
        this.shoucang = shoucang;
    }

    public int getResidentialAreaID() {
        return ResidentialAreaID;
    }

    public void setResidentialAreaID(int residentialAreaID) {
        ResidentialAreaID = residentialAreaID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMatchName() {
        return MatchName;
    }

    public void setMatchName(String matchName) {
        MatchName = matchName;
    }

    public String getSearchFlag() {
        return SearchFlag;
    }

    public void setSearchFlag(String searchFlag) {
        SearchFlag = searchFlag;
    }

    public String getDistrictFullName() {
        return DistrictFullName;
    }

    public void setDistrictFullName(String districtFullName) {
        DistrictFullName = districtFullName;
    }
}
