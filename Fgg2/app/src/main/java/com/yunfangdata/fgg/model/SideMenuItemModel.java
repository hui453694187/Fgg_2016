package com.yunfangdata.fgg.model;

/**
 * Created by Administrator on 2015/12/10.
 */
public class SideMenuItemModel {

    /** item 名 */
    private String name;
    /** 图标ID  */
    private int iconId;
    /** 子项类型 */
    private ItemType itemType;

    public SideMenuItemModel(String name,int iconId,ItemType itemType){
        this.name=name;
        this.iconId=iconId;
        this.itemType=itemType;
    }

    /***
     * item 类型
     *  头部
     *  普通item
     *  label
     */
    public enum ItemType{
        Head,
        Item,
        Label
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
