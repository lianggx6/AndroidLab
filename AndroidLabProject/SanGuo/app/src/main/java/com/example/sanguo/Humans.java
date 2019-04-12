package com.example.sanguo;


import java.io.Serializable;

class Humans implements Serializable {
    String name;
    String nation;
    String gender;
    String nativePlace;
    String introduction;
    String years;
    String birth;
    String dead;
    int imageId;
    Humans(){}
    Humans(String name,String nation,String gender,String birth,String dead,String nativePlace,int imageId,String introduction)
    {
        this.name = name;
        this.nation = nation;
        this.gender = gender;
        this.nativePlace = nativePlace;
        this.introduction = introduction;
        this.birth = birth;
        this.dead = dead;
        this.imageId = imageId;
        years = birth + "年-" + dead + "年";
    }
    Humans(Humans humans)
    {
        this.name = humans.name;
        this.nation = humans.nation;
        this.gender = humans.gender;
        this.nativePlace = humans.nativePlace;
        this.introduction = humans.introduction;
        this.birth = humans.birth;
        this.dead = humans.dead;
        this.imageId = humans.imageId;
        years = String.valueOf(birth) + "年-" + String.valueOf(dead) + "年";
    }
}

