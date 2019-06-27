package com.example.bean;

import com.google.gson.Gson;

public class orderItem {
    public String itemId;
    public Integer itemNum;

    public orderItem(String itemId, Integer itemNum){
        this.itemId = itemId;
        this.itemNum = itemNum;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
