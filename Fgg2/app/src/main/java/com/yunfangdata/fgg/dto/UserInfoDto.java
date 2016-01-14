package com.yunfangdata.fgg.dto;

import com.yunfangdata.fgg.model.UserInfo;

/**
 * Created by Kevin on 2015/12/21.
 *  用户信息DTO
 */
public class UserInfoDto {

    private String birthday="";

    private String phone="";

    private String occupation="";

    private String email="";

    private String name="";

    private String gender="男";

    private boolean success;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserInfo getUser(){
        UserInfo userInfo=new UserInfo();
        userInfo.setGender(this.getGender());
        userInfo.setOccupation(this.getOccupation());
        userInfo.setPhone(this.getPhone());
        userInfo.setRealName(this.getName());
        userInfo.setBirthday(this.getBirthday());
        userInfo.setEmail(this.getEmail());

        return userInfo;
    }

}
