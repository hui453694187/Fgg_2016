package com.yunfangdata.fgg.base;

/**
 * Created by kevin on 2015/12/3.
 * 系统用到的常量
 */
public class Constant {

    

    /**
     * sp名字
     */
    public static String SP_CONFIG = "config";
    /**
     * sp保存的用户名字
     */
    public static String SP_CONFIG_USER_NAME = "uesrname";
    /**
     * sp保存的是否第一次
     */
    public static String SP_CONFIG_IS_FIRST_USE = "isFirst";

    /***
     * 获取看房联系人返回码
     */
    public static final int GET_PERSION_CONTACTS_4_RESULT=0X0f;

    /***
     * 获取收件人返回码
     */
    public static final int GET_RECIPIENTS_4_RESULT=0x1f;

    /**
     * intent key
     * 跳转到房子详情
     *
     * 城市名称
     */
    public static final String KEY_INTENT_TO_HOUSE_DETAIL_CITYNAME = "KEY_INTENT_TO_HOUSE_DETAIL_CITYNAME";
    /**
     * intent key
     * 跳转到房子详情
     *
     * 小区ID
     */
    public static final String KEY_INTENT_TO_HOUSE_DETAIL_RESIDENTIALAREAID = "KEY_INTENT_TO_HOUSE_DETAIL_RESIDENTIALAREAID";
    /**
     * intent key
     * 跳转到估值页面
     *
     * 小区名字
     */
    public static final String KEY_INTENT_TO_BUY_RESIDENTIALAREANAME = "KEY_INTENT_TO_BUY_RESIDENTIALAREANAME";

    /**
     * intent key
     * 跳转到估值的web页面
     */
    public static final String KEY_INTENT_TO_VALUATION_WEB_VIEW = "KEY_INTENT_TO_VALUATION_WEB_VIEW";

    /**
     * intent key
     * 跳转到估值的web页面
     * 是买房还是卖房
     */
    public static final String KEY_INTENT_TO_VALUATION_WEB_VIEW_TYPE = "KEY_INTENT_TO_VALUATION_WEB_VIEW_TYPE";

    /**
     * 跳转到估值的web页面
     * 买房
     */
    public static final int TYPE_BUY_HOUSE = 1;
    /**
     * 跳转到估值的web页面
     * 卖房
     */
    public static final int TYPE_SALE_HOUSE = 2;
    /**
     * intent key
     * 请求
     * 收到到就关闭
     */
    public static final int KEY_INTENT_TO_CLOSE_YOURSELF_REQUESTCODE = 110110110;
    /**
     * intent key
     * 收到到就关闭
     */
    public static final int KEY_INTENT_TO_CLOSE_YOURSELF = 110110111;

    /**
     * 网址
     */
//    public static final String HTTP_ADRESS = "http://123.56.104.193";
    public static final String HTTP_ADRESS = "http://fggapp.yunfangdata.com";
    /**
     * 端口
     */
    public static final String HTTP_POINT = "10082";
    /**
     * 应用
     */
    public static final String HTTP_SOFTWARE = "/yunhenewApp/";


    /**
     * 端口加应用
     */
    public static final String HTTP_ALL = HTTP_ADRESS + ":" + HTTP_POINT + HTTP_SOFTWARE;

    /**
     * 端口加应用
     */
    public static final String HTTP_ALL2 = HTTP_ADRESS + ":" + HTTP_POINT + "/";


    /**
     * 接口
     * 四、获取单个小区详情
     * 说明：
     * 根据ID获取
     */
    public static final String HTTP_GETRESIDENTIALAREADETAIL = "residentialAreaController/getResidentialAreaDetail";


    /**
     * 获取小区周边信息
     */
    public static final String HTTP_GETAROUNDRESIDENTIALAREAINFO = "residentialAreaController/getAroundResidentialAreaInfo";
    /**
     * 小区名字的模糊搜索
     * residentialAreaController/getResidentialAreaByFuzzySearch
     */
    public static final String HTTP_GETRESIDENTIALAREABYFUZZYSEARCH = "residentialAreaController/getResidentialAreaByFuzzySearch";

    /***
     * 获取一个城市下的行政区
     * METHOD:GET
     * RESULT: 区域名 ， 区域拼音 ， 坐标， 均价
     */
    public static final String HTTP_GET_DISTRICT = "subWayController/getDistrict";

    /***
     * 获取地址站信息
     */
    public static final String HTTP_GET_SUBWAY = "subWayController/getSubWay";
    /***
     * 获取片区信息
     */
    public static final String HTTP_GET_AEAR = "subWayController/getPlate";

    /**
     * 买房估值
     * appraiseController/getAppraisement
     */
    public static final String HTTP_GETAPPRAISEMENT = "appraiseController/getAppraisement";

    /**
     * 进度查询
     * reportController/getReportRate
     */
    public static final String HTTP_GETREPORTRATE = "reportController/getReportRate";

    /**
     * 真伪查询
     * reportController/getReportDifference
     */
    public static final String HTTP_GETREPORTDIFFERENCE = "reportController/getReportDifference";
    /***
     * ResidentialArea
     * 根据矩形区域对角坐标获取区域内的坐标
     */
    public static final String HTTP_GET_RESIDENTIAL_AREA = "residentialAreaController/getResidentialAreaByXY";

    /**
     * 请求估值页面
     * AppHtml/app/financialValuation/financialValuationResult_content.html
     */
    public static final String HTTP_FINANCIALVALUATION = "AppHtml/app/financialValuation/financialValuationResult_content.html?";
    /**
     * 请求小区是否包含特殊因素
     * residentialAreaController/specialFactors
     */
    public static final String HTTP_SPECIALFACTORS = "residentialAreaController/specialFactors";

    //region 看房联系人

    /***
     * 获取联系人信息
     */
    public static final String HTTP_CONTACTSQUERYBYUSERID = "linkPersonController/getLinkPersonByUserid";

    /***
     * 修改看房联系人
     */
    public static final String HTTP_CONTACTSUPDATE = "linkPersonController/updateLinkPerson";

    /***
     * 新增看房联系人
     */
    public static final String HTTP_CONTACTSSAVE = "linkPersonController/saveLinkPerson";

    /***
     * 查看看房联系人
     */
    public static final String HTTP_CONTACTSQUERYBYID = "linkPersonController/getLinkPersonById";

    /***
     * 删除看房联系人
     */
    public static final String HTTP_CONTACTSDELETE = "linkPersonController/delLinkPerson";

    /***
     * 设置默认看房联系人
     */
    public static final String HTTP_CONTACTSDEFAULT = "linkPersonController/setDefaultLinkPerson";

    //endregion

    //region 收件人

    /***
     * 获取收件人信息
     */
    public static final String HTTP_RECIPIENTSQUERYBYUSERID = "mailAdderssController/getMailAddressList";

    /***
     * 修改收件人
     */
    public static final String HTTP_RECIPIENTSUPDATE = "mailAdderssController/updateMailAddress";

    /***
     * 新增收件人
     */
    public static final String HTTP_RECIPIENTSSAVE = "mailAdderssController/saveMailAddress";

    /***
     * 查看收件人
     */
    public static final String HTTP_RECIPIENTSQUERYBYID = "mailAdderssController/getMailAddressDetail";

    /***
     * 删除收件人
     */
    public static final String HTTP_RECIPIENTSDELETE = "mailAdderssController/deleteMialAddress";

    /***
     * 设置默认收件人
     */
    public static final String HTTP_RECIPIENTSDEFAULT = "mailAdderssController/setDefalutAddress";

    //endregion

    /***
     * residentialAreaController/getResidentialAreaByCondition
     */
    public static final String HTTP_GET_RESIDENTIAL_BY_CONDITION="residentialAreaController/getResidentialAreaByCondition";
    /**
     * 密码登录
     * userController/loginByUserName
     */
    public static final String HTTP_LOGIN_BY_USERNAME = "userController/loginByUserName";

    /**
     * 获取手机验证码
     * userController/getSecurityCode
     */
    public static final String HTTP_GET_SECURITY_CODE = "userController/getSecurityCode";

    /**
     * 用户手机验证码登录接口
     * userController/loginNoPwd
     */
    public static final String HTTP_GET_LOGIN_NO_PWD = "userController/loginNoPwd";


    /**
     * 用户手机注册接口
     * userController/register
     */
    public static final String HTTP_GET_REGISTER = "userController/register";
    /**
     * 用户找回密码的验证码
     * findPwdController/sendMessage
     */
    public static final String HTTP_GET_SEND_MESSAGE = "findPwdController/sendMessage";
    /**
     * 用户找回密码的 保存接口
     * findPwdController/resetPwd
     */
    public static final String HTTP_GET_RESET_PWD = "findPwdController/resetPwd";

    /***
     * 模糊查询小区数据
     */
    public static final String HTTP_FUZZY_SEARCH ="residentialAreaController/getResidentialAreaByFuzzySearch";

    /***
     * 获取单个小区信息
     * 参数：小区ID
     */
    public static final String HTTP_GET_SINGLE_GARDEN="residentialAreaController/getResidentialAreaDetail";


    /**
     * 进行委托评估
     * entrustController/entrust
     */
    public static final String HTTP_GET_ENTRUST = "entrustController/entrust";

    /***
     * 获取用户信息
     * 参数：用户账号
     */
    public static final String HTTP_GET_USER_INFO="userController/getUserInfo";

    /***
     * 修改用户信息
     *userController/editUserInfo
     */
    public static final String HTTP_EDIT_USER_INFO="userController/editUserInfo";

    /***
     * 获取消息列表
     */
    public static final String HTTP_GET_MESSAGE_INFO="messageController/loadMySelfMsg";
    /**
     * 二、	获取委托列表
     * entrustController/loadWeituojilu
     */
    public static final String HTTP_GET_LOAD_WEITUOJILU = "entrustController/loadWeituojilu";
    /**
     * 四、	委托催单,撤单
     * entrustController/doWeituopinggu_cd
     */
    public static final String HTTP_GET_DO_WEITUO_PINGGU_CD = "entrustController/doWeituopinggu_cd";
    /**
     * 获取版本号
     * reportController/getVersion
     */
    public static final String HTTP_GET_GET_VERSION = "reportController/getVersion";
    /**
     * 绑定邮箱
     * emailController/bandEmail
     */
    public static final String HTTP_GET_BAND_EMAIL = "emailController/bandEmail";
    /**
     * 解绑手机
     * userController/confirmEidtPhone
     */
    public static final String HTTP_GET_CONFIRM_EIDT_PHONE = "userController/confirmEidtPhone";

    /***
     * 设置消息已读
     */
    public static final String HTTP_SET_MSG_IS_READ="messageController/setMyReadMsg";

    /***
     * 批量设置消息已读
     */
    public static final String HTTP_SET_ALL_MSG_IS_READ="messageController/readAllMsg";

    /***
     * 删除消息
     */
    public static final String HTTP_DELETE_MSG="messageController/deleteMySelfMsg";

    /***
     * 批量删除消息
     */
    public static final String HTTP_DELETE_ALL_MSG="messageController/deleteMsgByList";

    /***
     * 意见反馈
     */
    public static final String HTTP_SEND_RETROACTIONTASK="retroactionController/addRetroaction";

    public static final String HTTP_EDT_PWD="userController/editUserPwd";
}
