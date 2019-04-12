package com.example.project;

import android.app.Application;


public class MyApplication extends Application {
    private int state;
    private String user;
    private int user_id;
    public String user_name;
    public String user_avatar;
    @Override
    public void onCreate(){
        super.onCreate();
        setState(1);
        setUser("");
        setUser_id(2);
        user_avatar = "";
        user_name = "";
    }

    public int getState(){
        return state;
    }

    public String getUser(){
        return user;
    }
    public int getUser_id(){
        return user_id;
    }

    public void setState(int state){
        this.state = state;
    }

    public void setUser(String user){
        this.user = user;
    }

    public void setUser_id(int user_id){
        this.user_id = user_id;
    }
}
