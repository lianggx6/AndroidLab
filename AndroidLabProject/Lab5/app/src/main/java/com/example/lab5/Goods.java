package com.example.lab5;

import java.io.Serializable;

class Goods implements Serializable {
     String price;
     String name;
     String type;
     String type_value;
     int imageId;
     boolean isCollect;
    Goods(String name,String price,String type,String type_value,int ID)
    {
        this.price = price;
        this.name =name;
        this.type = type;
        this.type_value = type_value;
        imageId = ID;
        isCollect = false;

    }
}
