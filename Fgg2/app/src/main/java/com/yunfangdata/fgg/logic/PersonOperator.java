package com.yunfangdata.fgg.logic;

import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.http.task.BindingEmailTask;
import com.yunfangdata.fgg.http.task.ContactsDefaultTask;
import com.yunfangdata.fgg.http.task.ContactsDeleteTask;
import com.yunfangdata.fgg.http.task.ContactsQueryByIdTask;
import com.yunfangdata.fgg.http.task.ContactsQueryByUserIdTask;
import com.yunfangdata.fgg.http.task.ContactsSaveTask;
import com.yunfangdata.fgg.http.task.ContactsUpdateTask;
import com.yunfangdata.fgg.http.task.DeleteMsgTask;
import com.yunfangdata.fgg.http.task.EditUserPasswordTask;
import com.yunfangdata.fgg.http.task.GetMessageListTask;
import com.yunfangdata.fgg.http.task.GetUserInfo;
import com.yunfangdata.fgg.http.task.RecipientsDefaultTask;
import com.yunfangdata.fgg.http.task.RecipientsDeleteTask;
import com.yunfangdata.fgg.http.task.RecipientsQueryByIdTask;
import com.yunfangdata.fgg.http.task.RecipientsQueryByUserIdTask;
import com.yunfangdata.fgg.http.task.RecipientsSaveTask;
import com.yunfangdata.fgg.http.task.RecipientsUpdateTask;
import com.yunfangdata.fgg.http.task.ResetUserNameTask;
import com.yunfangdata.fgg.http.task.SaveUserInfoTask;
import com.yunfangdata.fgg.http.task.SendRetroactionTask;
import com.yunfangdata.fgg.http.task.SetMsgIsReadTask;
import com.yunfangdata.fgg.model.BindingEmailBeen;
import com.yunfangdata.fgg.model.DataResult;
import com.yunfangdata.fgg.model.PersonContacts;
import com.yunfangdata.fgg.model.PersonMessage;
import com.yunfangdata.fgg.model.PersonRecipients;
import com.yunfangdata.fgg.model.ResetUserNameBeen;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 贺隽 on 2015/12/16.
 * 个人中心逻辑类
 */
public class PersonOperator {

    //region 个人资料

    //endregion

    //region 消息中心

    //endregion

    //region 看房联系人


    /***
     * 设置默认联系人
     *
     * @param id 联系人编号
     * @return 执行结果
     */
    public static ResultInfo<Boolean> contactsDefault(String id) {
        ResultInfo<Boolean> result = new ResultInfo<>();
        try {
            String userName = FggApplication.getUserInfo().getUserName();
            ContactsDefaultTask task = new ContactsDefaultTask();
            result = task.request(userName,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 删除联系人
     *
     * @param id 联系人编号
     * @return 执行结果
     */
    public static ResultInfo<Boolean> contactsDelete(String id) {
        ResultInfo<Boolean> result = null;
        try {
            ContactsDeleteTask task = new ContactsDeleteTask();
            result = task.request(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 获取某一个看房联系人
     *
     * @param id 联系人编号
     * @return 执行结果
     */
    public static ResultInfo<PersonContacts> contactsQueryById(String id) {
        ResultInfo<PersonContacts> result = new ResultInfo<>();
        try {
            ContactsQueryByIdTask task = new ContactsQueryByIdTask();
            result = task.request(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /***
     * 查询当前用户所有联系人
     *
     * @return 执行结果
     */
    public static ResultInfo<ArrayList<PersonContacts>> contactsQuery() {
        ResultInfo<ArrayList<PersonContacts>> result = new ResultInfo<>();
        try {
            String userName = FggApplication.getUserInfo().getUserName();
            ContactsQueryByUserIdTask task = new ContactsQueryByUserIdTask();
            result = task.request(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 添加看房联系人
     *
     * @param name 名称
     * @param phone 电话号码
     * @param iDefault 1 为默认 0为其他
     * @return 执行结果
     */
    public static ResultInfo<Boolean> contactsSave(String name,String phone,int iDefault) {
        ResultInfo<Boolean> result = new ResultInfo<>();
        try {
            PersonContacts personContacts = new PersonContacts();
            personContacts.setLianXiRenName(name);
            personContacts.setShouJi(phone);
            personContacts.setMoRenLianXiRen(iDefault);
            String userName = FggApplication.getUserInfo().getUserName();
            ContactsSaveTask task = new ContactsSaveTask();
            result = task.request(userName, personContacts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 修改看房联系人
     *
     * @param id 看房联系人编号
     * @param name 名称
     * @param phone 电话号码
     * @param iDefault 1 为默认 0为其他
     * @return 执行结果
     */
    public static ResultInfo<Boolean> contactsUpdate(String id,String name,String phone,int iDefault) {
        ResultInfo<Boolean> result = new ResultInfo<>();
        try {
            PersonContacts personContacts = new PersonContacts();
            personContacts.setLianXiRenId(id);
            personContacts.setLianXiRenName(name);
            personContacts.setShouJi(phone);
            personContacts.setMoRenLianXiRen(iDefault);
            ContactsUpdateTask task = new ContactsUpdateTask();
            result = task.request(personContacts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //endregion

    //region 收件人信息

    /***
     * 设置默认联系人
     *
     * @param id 联系人编号
     * @return 执行结果
     */
    public static ResultInfo<Boolean> recipientsDefault(String id) {
        ResultInfo<Boolean> result = new ResultInfo<>();
        try {
            String userName = FggApplication.getUserInfo().getUserName();
            RecipientsDefaultTask task = new RecipientsDefaultTask();
            result = task.request(userName,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 删除联系人
     *
     * @param id 联系人编号
     * @return 执行结果
     */
    public static ResultInfo<Boolean> recipientsDelete(String id) {
        ResultInfo<Boolean> result = null;
        try {
            RecipientsDeleteTask task = new RecipientsDeleteTask();
            result = task.request(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 获取某一个收件人
     *
     * @param id 联系人编号
     * @return 执行结果
     */
    public static ResultInfo<PersonRecipients> recipientsQueryById(String id) {
        ResultInfo<PersonRecipients> result = new ResultInfo<>();
        try {
            RecipientsQueryByIdTask task = new RecipientsQueryByIdTask();
            result = task.request(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /***
     * 查询当前用户所有联系人
     *
     * @return 执行结果
     */
    public static ResultInfo<ArrayList<PersonRecipients>> recipientsQuery() {
        ResultInfo<ArrayList<PersonRecipients>> result = new ResultInfo<>();
        try {
            String userName = FggApplication.getUserInfo().getUserName();
            RecipientsQueryByUserIdTask task = new RecipientsQueryByUserIdTask();
            result = task.request(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 添加收件人
     * @param name 收件人名称
     * @param area 区域
     * @param detailAddress 地址
     * @param postCode 邮政编码
     * @param phoneNumber 手机号码
     * @param iDefault 是否为默认收件人
     * @return
     */
    public static ResultInfo<Boolean> recipientsSave(String name,String area,String detailAddress,String postCode,String phoneNumber,int iDefault) {
        ResultInfo<Boolean> result = new ResultInfo<>();
        try {
            String userName = FggApplication.getUserInfo().getUserName();
            PersonRecipients personRecipients = new PersonRecipients();
            personRecipients.setYouJifangwei(area);
            personRecipients.setYouJiDiZhi(detailAddress);
            personRecipients.setYouZhengBianMa(postCode);
            personRecipients.setShouJianRenDianHua(phoneNumber);
            personRecipients.setShouJianRen(name);
            personRecipients.setMoRenShouJianDiZhi(iDefault);

            RecipientsSaveTask task = new RecipientsSaveTask();
            result = task.request(userName, personRecipients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 修改收件人
     * @param id 收件人编号
     * @param name 收件人名称
     * @param area 区域
     * @param detailAddress 地址
     * @param postCode 邮政编码
     * @param phoneNumber 手机号码
     * @param iDefault 是否为默认收件人
     * @return 执行结果
     */
    public static ResultInfo<Boolean> recipientsUpdate(String id,String name,String area,String detailAddress,String postCode,String phoneNumber,int iDefault) {
        ResultInfo<Boolean> result = new ResultInfo<>();
        try {
            String userName = FggApplication.getUserInfo().getUserName();
            PersonRecipients personRecipients = new PersonRecipients();
            personRecipients.setYouJiDiZhiId(id);
            personRecipients.setYouJifangwei(area);
            personRecipients.setYouJiDiZhi(detailAddress);
            personRecipients.setYouZhengBianMa(postCode);
            personRecipients.setShouJianRenDianHua(phoneNumber);
            personRecipients.setShouJianRen(name);
            personRecipients.setMoRenShouJianDiZhi(iDefault);

            RecipientsUpdateTask task = new RecipientsUpdateTask();
            result = task.request(userName, personRecipients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 获取当前用户信息
     * @return
     */
    public static DataResult<UserInfo> getUserInfo(){
        DataResult<UserInfo> result;
        GetUserInfo task=new GetUserInfo();
        try{
            result=task.request();
        }catch (Exception e){
            result=new DataResult<>(false,"网络异常");
        }
        return result;
    }

    /***
     * 保存用户信息
     * @param userInfo
     */
    public static DataResult<Boolean> saveUserInfo(UserInfo userInfo) {
        DataResult<Boolean> result;
        SaveUserInfoTask task=new SaveUserInfoTask();
        try {
            result=task.request(userInfo);
        }catch (Exception E){
            E.printStackTrace();
            result=new DataResult<>(false,"保存失败");
        }
        return result;

    }

    public static DataResult<List<PersonMessage>> getMessageList(){
        DataResult<List<PersonMessage>> result;
        try{
            GetMessageListTask task=new GetMessageListTask();
            result=task.request();
        }catch (Exception e){
            e.printStackTrace();
            result=new DataResult<>(false,"");
        }
        return result;
    }

    /***
     * 设置消息已读
     * @param msgIds
     * @return
     */
    public static DataResult<Boolean> setMsgIsRead(List<PersonMessage> msgIds){
        DataResult<Boolean> result;
        SetMsgIsReadTask task=new SetMsgIsReadTask();
        try{
            result= task.request(msgIds);
        }catch(Exception e){
            e.printStackTrace();
            result=new DataResult<>(false,"检查网络");
        }
        return result;
    }

    /***
     * 删除消息
     * @param msgIds
     * @return
     */
    public static DataResult<Boolean> deleteMsgIsRead(List<PersonMessage> msgIds){
        DataResult<Boolean> result;
        DeleteMsgTask task=new DeleteMsgTask();
        try{
            result= task.request(msgIds);
        }catch(Exception e){
            e.printStackTrace();
            result=new DataResult<>(false,"检查网络");
        }
        return result;
    }

    public static DataResult<Boolean> sendMsg(String msgStr){
        DataResult<Boolean> result;
        SendRetroactionTask task=new SendRetroactionTask();
        try{
            result= task.request(msgStr);
        }catch(Exception e){
            e.printStackTrace();
            result=new DataResult<>(false,"检查网络");
        }
        return result;
    }

    //endregion

    //region 意见反馈

    //endregion

    //region 修改密码

    //endregion

    //region 解绑手机（改绑手机）

    //endregion

    //region 绑定邮箱

    //endregion


    /***
     * 绑定邮箱
     *
     * @return 执行结果
     * @param emailAddress
     */
    public static ResultInfo<BindingEmailBeen> requertBindingEmail(String emailAddress) {
        ResultInfo<BindingEmailBeen> result = new ResultInfo<>();
        try {
            String userName = FggApplication.getUserInfo().getUserName();
            BindingEmailTask bindingEmailTask = new BindingEmailTask();
            ResultInfo<BindingEmailBeen> request = bindingEmailTask.request(userName, emailAddress);
            result = request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     *解绑手机
     *
     * @return 执行结果
     */
    public static ResultInfo<ResetUserNameBeen> resetUserName(String phone,String newphone,String vcode) {
        ResultInfo<ResetUserNameBeen> result = new ResultInfo<>();
        try {
            ResetUserNameTask resetUserNameTask = new ResetUserNameTask();
            ResultInfo<ResetUserNameBeen> request = resetUserNameTask.request(phone, newphone, vcode);
            result = request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static DataResult<Boolean> resetPwd(String newPwd,String oldPwd){
        DataResult<Boolean> result;
        EditUserPasswordTask task=new EditUserPasswordTask();
        try{
            result= task.request(newPwd,oldPwd);
        }catch(Exception e){
            e.printStackTrace();
            result=new DataResult<>(false,"检查网络");
        }
        return result;
    }

}
