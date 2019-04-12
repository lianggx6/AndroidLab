package com.example.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.modelClass.user;
import com.example.netInterface.DataInterface;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegistActivity extends AppCompatActivity {

    EditText name;
    EditText password;
    EditText number;
    EditText email;
    Button regist;
    ImageButton back;
    String BASE_URL;
    Retrofit retrofit;
    DataInterface dataInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initial();
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namestr = name.getText().toString();
                String passwordstr = password.getText().toString();
                String numberstr = number.getText().toString();
                String emailstr = email.getText().toString();
                if(namestr.isEmpty() || passwordstr.isEmpty() || numberstr.isEmpty() || emailstr.isEmpty())
                    Toast.makeText(RegistActivity.this,"请填入全部信息",Toast.LENGTH_SHORT).show();
                else
                {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())//添加转换器Converter(将json 转为JavaBean)
                            .build();

                    dataInterface = retrofit.create(DataInterface.class);
                    dataInterface.adduser(namestr,"1",emailstr,numberstr,passwordstr)
                            .subscribeOn(Schedulers.io())//新建线程进行网络访问
                            .observeOn(AndroidSchedulers.mainThread())//在主线程处理请求结果
                            .subscribe(new Subscriber<user>() {
                                @Override
                                public void onCompleted() {
                                    System.out.print("Complete the transfer");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("d", "onError:" + e.getMessage());
                                }

                                @Override
                                public void onNext(user g) {
                                    if(g.id < 0) Toast.makeText(RegistActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                                    else
                                    {
                                        Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                }
                            });
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void initial()
    {
        name = (EditText) findViewById(R.id.regist_username);
        password = (EditText) findViewById(R.id.regist_password);
        number = (EditText) findViewById(R.id.regist_phone);
        email = (EditText) findViewById(R.id.regist_email);
        regist = (Button) findViewById(R.id.regist_regist);
        back = (ImageButton) findViewById(R.id.regist_back);
        BASE_URL = "http://ss.sen7u.win:8080/Test2/api/";
    }
}
