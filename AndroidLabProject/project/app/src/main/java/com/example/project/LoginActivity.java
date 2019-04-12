package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    ImageButton back;
    EditText userEdit;
    EditText passwordEdit;
    Button login;
    Button regist;
    String BASE_URL;
    Retrofit retrofit;
    DataInterface dataInterface;
    MyApplication myApplication;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initial();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = userEdit.getText().toString();
                final String password = passwordEdit.getText().toString();
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())//添加转换器Converter(将json 转为JavaBean)
                        .build();

                dataInterface = retrofit.create(DataInterface.class);
                dataInterface.getuser("name",username)
                        .subscribeOn(Schedulers.io())//新建线程进行网络访问
                        .observeOn(AndroidSchedulers.mainThread())//在主线程处理请求结果
                        .subscribe(new Subscriber<List<user>>() {
                            @Override
                            public void onCompleted() {
                                System.out.print("Complete the transfer");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("d", "check-onError:" + e.getMessage());
                                myApplication.setState(1);
                                myApplication.setUser_id(-100);
                            }

                            @Override
                            public void onNext(List<user> g) {
                                Log.d("d", "check-onNext: ");
                                if(password.equals(g.get(0).password))
                                {
                                    myApplication.setState(2);
                                    myApplication.setUser(g.get(0).name);
                                    myApplication.setUser_id(g.get(0).id);
                                    myApplication.user_name = g.get(0).name;
                                    myApplication.user_avatar = g.get(0).avatar;
                                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                    sharedPreferences.edit().putString("password",password).apply();
                                    sharedPreferences.edit().putString("username",username).apply();
                                    EventBus.getDefault().post(myApplication);
                                    finish();
                                }
                                else
                                {
                                    myApplication.setState(1);
                                    myApplication.setUser_id(-100);
                                    Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        });

    }

    void initial()
    {
        back = (ImageButton) findViewById(R.id.login_back);
        userEdit = (EditText) findViewById(R.id.login_user);
        passwordEdit = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login_login);
        regist = (Button) findViewById(R.id.login_regist);
        BASE_URL = "http://ss.sen7u.win:8080/Test2/api/";
        sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
        myApplication = (MyApplication) LoginActivity.this.getApplication();
    }
}
