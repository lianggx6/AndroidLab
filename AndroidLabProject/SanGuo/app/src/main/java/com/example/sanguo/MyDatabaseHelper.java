package com.example.sanguo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDatabaseHelper  extends SQLiteOpenHelper {
    //数据库版本号
    private static final int DATABASE_VERSION=4;
    //数据库名称
    private static final String DATABASE_NAME="humans.db";

    MyDatabaseHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String CREATE_TABLE_HUMAN="CREATE TABLE human(name TEXT PRIMARY KEY,nation TEXT,gender TEXT,nativePlace TEXT,introduction TEXT, birth TEXT,dead TEXT,imageId INTEGER)";
        String CREATE_TABLE_COLLECT="CREATE TABLE collect(name TEXT PRIMARY KEY,nation TEXT,gender TEXT,nativePlace TEXT,introduction TEXT, birth TEXT,dead TEXT,imageId INTEGER)";
        db.execSQL(CREATE_TABLE_HUMAN);
        db.execSQL(CREATE_TABLE_COLLECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS human");
        db.execSQL("DROP TABLE IF EXISTS collect");
        //再次创建表
        onCreate(db);
    }
}

