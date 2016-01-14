package com.yunfangdata.fgg.viewmodel;

/**
 * 注册登录类的
 *
 * Created by zjt on 2015-12-16.
 */
public class RegisterAndLoginViewModel {

    /**
     * 这是判断手机号码的正则表达式
     * ^((13[0-9])|(147)|(15[^4,\\D])|(18[0-9]))\\d{8}$
     */
    public static  final String regular_expression = "^((13[0-9])|(147)|(15[^4,\\D])|(18[0-9]))\\d{8}$";

    /**
     * 这是判断密码的正则表达式
     *  "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
     */
    public static final String passward_expression = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

    /**
     * 需要生成多少位的随机数
     */
    public static  final int random_digit = 4;

    /**
     * 1登陆码,2注册码；
     */
    public static  final int VerificationCode_type_login = 1;

    /**
     * 1登陆码,2注册码；
     */
    public static  final int VerificationCode_type_register = 2;


}
