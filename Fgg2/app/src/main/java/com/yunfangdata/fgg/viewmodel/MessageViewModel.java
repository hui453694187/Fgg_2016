package com.yunfangdata.fgg.viewmodel;

import com.yunfangdata.fgg.model.PersonMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2015/12/21.
 *  消息中心界面数据模型
 */
public class MessageViewModel {

    public static final int GET_PERSON_MESSAGE =101;

    public static final int SET_MSG_IS_READ=102;

    public static final int DELETE_MSG=103;

    public static final int SET_MSG_IS_READ_ON_CLICK=104;

    public PersonMessage checkoutPm;

    /***
     * 是否是编辑状态
     */
    private boolean isEdited=false;



    private List<PersonMessage> messages;

    public List<PersonMessage> getMessages() {
        if(messages==null){
            messages=new ArrayList<>();
        }
        return messages;
    }

    public void deleteMsgById(PersonMessage msgId) throws Exception{
        messages.remove(msgId);
    }

    /***
     * 设置指定消息已读
     * @param msgId
     */
    public void setMsgIsRead(PersonMessage msgId){
        msgId.setXiaoXiZhuangTai(true);
        /*for(PersonMessage pm:this.messages){
            if(msgId.equals(pm.getXiaoXiId())){
                pm.setXiaoXiZhuangTai(true);
            }
        }*/
    }

    public void setMessages(List<PersonMessage> messages) {
        this.messages = messages;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setIsEdited(boolean isEdited) {
        this.isEdited = isEdited;
    }
}
