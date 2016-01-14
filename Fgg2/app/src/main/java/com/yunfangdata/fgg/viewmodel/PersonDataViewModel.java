package com.yunfangdata.fgg.viewmodel;

import java.util.ArrayList;

/**
 * Created by Kevin on 2015/12/21.
 *
 */
public class PersonDataViewModel {

    private String[] profession={"银行","中介","金融","其他"};

    private ArrayList<String> professionLs;


    public ArrayList<String> getProfession() {
        if(professionLs==null){
            professionLs=new ArrayList<>();
        }
        for(String str:profession){
            professionLs.add(str);
        }


        return professionLs;
    }
}
