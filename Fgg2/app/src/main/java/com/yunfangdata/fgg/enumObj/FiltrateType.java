package com.yunfangdata.fgg.enumObj;

import com.yunfangdata.fgg.R;

/**
 * Created by kevin on 2015/12/11.
 *  筛选类型
 */
public enum FiltrateType {

    AREA(R.id.area_rdb,"区域"),
    HOUSE(R.id.house_rdb,"户型"),
    PRICE(R.id.price_rdb,"价格"),
    SCHOOL(R.id.school_rdb,"学校");

    private int id;

    private String name;

    FiltrateType(int id,String name){
        this.id=id;
        this.name=name;
    }

    /**根据ID 获取枚举*/
    public static FiltrateType getFiltrateTypeById(int id){
        for(FiltrateType value:FiltrateType.values()){
            if(id==value.getId()){
                return value;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
