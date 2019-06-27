package com.example.bean;
import com.google.gson.Gson;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class order {
    @Id
    public String id;

    public String user;
    public ArrayList<orderItem> itemList;

    public order(){}

    public order(String user, ArrayList<orderItem> item_list){
        this.user = user;
        this.itemList = item_list;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}


