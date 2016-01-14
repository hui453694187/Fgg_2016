package com.yunfangdata.fgg.enumObj;

/**
 * Created by Kevin on 2015/12/16.
 * 户型枚举
 */
public enum HouseType {

    不限(0,"不限"),
    一居室(1,"一居室"),
    二居室(2,"二居室"),
    三居室(3,"三居室"),
    四居室(4,"四居室"),
    五居室(5,"五居室"),
    五居室以上(6,"五居室以上");

    private int position;
    private String name;
    HouseType(int position,String name){
        this.position=position;
        this.name=name;
    }

    public String getName(){
       return this.name;
    }
    public int getPosition(){
        return this.position;
    }

    public static HouseType getEnumByPostion(int position){
        for(HouseType h:HouseType.values()){
            if(h.getPosition()==position){
                return h;
            }
        }
        return null;
    }

    public static HouseType getEnumByName(String name){
        for(HouseType h:HouseType.values()){
            if(h.getName().equals(name)){
                return h;
            }
        }
        return null;
    }

    public static String[] getEnumToString(){
        HouseType [] houseTypes=HouseType.values();
        String [] hoseTypeStr=new String[houseTypes.length];
        for(int i=0;i<hoseTypeStr.length;i++){
            hoseTypeStr[i]=houseTypes[i].getName();
        }
        return hoseTypeStr;
    }
}
