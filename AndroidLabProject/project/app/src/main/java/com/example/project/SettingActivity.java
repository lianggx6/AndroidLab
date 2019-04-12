package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class SettingActivity extends AppCompatActivity {

    ImageView back;
    ConstraintLayout zhuxiao;
    SharedPreferences sharedPreferences;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        back = (ImageView) findViewById(R.id.setting_back);
        zhuxiao = (ConstraintLayout) findViewById(R.id.zhuxiao);
        sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
        myApplication = (MyApplication) SettingActivity.this.getApplication();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        zhuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingActivity.this);
                mBuilder.setMessage("确认退出吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sharedPreferences.edit().putString("password","").apply();
                                sharedPreferences.edit().putString("username","").apply();
                                myApplication.setState(1);
                                myApplication.setUser_id(-100);
                                EventBus.getDefault().post(myApplication);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .create().show();
            }
        });
    }
}
