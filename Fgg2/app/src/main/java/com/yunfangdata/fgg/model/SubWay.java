package com.yunfangdata.fgg.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2015/12/14.
 */
public class SubWay {

    private String subWayNo;

    private List<SubStation> children;

    public String getSubWayNo() {
        return subWayNo;
    }

    public void setSubWayNo(String subWayNo) {
        this.subWayNo = subWayNo;
    }

    public List<SubStation> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<SubStation> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder("subStation:");
        for (SubStation ss : children) {
            strBuilder.append(ss.toString());
        }

        return "subWayNo:" + subWayNo +
                "---->" + strBuilder.toString();
    }
}
