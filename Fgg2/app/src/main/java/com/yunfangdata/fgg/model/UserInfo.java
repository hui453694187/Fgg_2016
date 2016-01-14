package com.yunfangdata.fgg.model;

/**
 * Created by 贺隽 on 2015-12-15.
 */
public class UserInfo {

    /**
     * 构造方法
     */
    public UserInfo() {

    }

    /**
     * 生日
     */
    private String birthday;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 手机号码
     */
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 职业
     */
    private String occupation;

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * 邮箱
     */
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 姓名
     */
    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 用户名(登录用)
     */
    private String userName;

    public String getUserName() {return userName;}

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 性别
     */
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
