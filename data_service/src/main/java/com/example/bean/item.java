package com.example.bean;

import org.springframework.data.annotation.Id;

public class item {

    @Id
    public String id;

    public String itemName;
    public Integer price;
    public item(){}

    public item(String itemName, Integer price){
        this.itemName = itemName;
        this.price = price;
    }

    @Override
    public String toString(){
        return String.format("item[id=%s, item_name='%s', price=%d]", id, itemName, price);
    }

}
