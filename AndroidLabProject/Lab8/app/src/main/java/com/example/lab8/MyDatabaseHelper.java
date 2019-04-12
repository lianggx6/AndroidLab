package com.example.lab8;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class MyDatabaseHelper  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=4;
    private static final String DATABASE_NAME="birthList.db";

    MyDatabaseHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE allList(name TEXT PRIMARY KEY,birthday TEXT,gift TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    boolean add(String name, String birthday, String gift)
    {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM allList WHERE name = \'" + name + "\'",null);
        if(cursor.getCount() == 0)
        {
            ContentValues values=new ContentValues();
            values.put("name",name);
            values.put("birthday",birthday);
            values.put("gift",gift);
            database.insert("allList",null,values);
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    void update(String name, String birthday, String gift)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("birthday",birthday);
        values.put("gift",gift);
        database.update("allList",values,"name = ?",new String[]{name});
    }

    void delete(String name)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM allList WHERE name = \'" + name + "\'");
    }

    List<String[]> getALL()
    {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM allList",null);
        List<String[]> allList = new ArrayList<>();
        while (cursor.moveToNext()) {
            allList.add(new String[]{
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("birthday")),
                    cursor.getString(cursor.getColumnIndex("gift"))
                });
        }
        cursor.close();
        return  allList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS allList");
        onCreate(db);
    }
}


