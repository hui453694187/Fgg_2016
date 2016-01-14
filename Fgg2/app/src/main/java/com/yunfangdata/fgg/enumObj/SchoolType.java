package com.yunfangdata.fgg.enumObj;

/**
 * Created by Kevin on 2015/12/16.
 *  学校类型枚举
 */
public enum SchoolType {

   /* 初中,
    高中,*/
    小学(0,"小学");

    SchoolType(int position,String name){
        this.position=position;
        this.name=name;
    }

    private int position;
    private String name;

    public int getPosition() {
        return position;
    }
    public String getName() {
        return name;
    }

    public static SchoolType getEnumByPostion(int position){
        for(SchoolType h:SchoolType.values()){
            if(h.getPosition()==position){
                return h;
            }
        }
        return null;
    }

    public static SchoolType getEnumByName(String name){
        for(SchoolType h:SchoolType.values()){
            if(h.getName().equals(name)){
                return h;
            }
        }
        return null;
    }

    public static String[] getEnumToString(){
        SchoolType [] schoolTypes=SchoolType.values();
        String [] schoolTypeStr=new String[schoolTypes.length];
        for(int i=0;i<schoolTypeStr.length;i++){
            schoolTypeStr[i]=schoolTypes[i].getName();
        }
        return schoolTypeStr;
    }

}
