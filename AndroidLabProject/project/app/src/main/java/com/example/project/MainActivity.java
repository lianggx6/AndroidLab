package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.home;
import com.example.fragment.me;
import com.example.fragment.message;
import com.example.fragment.publish;
import com.example.fragment.square;
import com.example.modelClass.goods_publish_all;
import com.example.modelClass.user;
import com.example.netInterface.DataInterface;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    me mefg;
    home homefg;
    message messagefg;
    square squarefg;
    publish publishfg;
    LinearLayout mell;
    LinearLayout homell;
    LinearLayout messagell;
    LinearLayout squarell;
    LinearLayout fabu;
    ImageView meimg;
    ImageView homeimg;
    ImageView messageimg;
    ImageView squareimg;
    ImageView fabuimg;
    TextView metext;
    TextView hometext;
    TextView messagetext;
    TextView squaretext;
    TextView fabutext;
    TextView biaotilan;
    ImageView search;
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;
    String BASE_URL;
    Retrofit retrofit;
    DataInterface dataInterface;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        init();
        setChioceItem(0);
        setonclick();
        checkPassord();
    }

    public void init(){
        mell = (LinearLayout) findViewById(R.id.wo);
        homell = (LinearLayout) findViewById(R.id.shouye);
        messagell = (LinearLayout) findViewById(R.id.xiaoxi);
        squarell = (LinearLayout) findViewById(R.id.guangchang);
        fabu = (LinearLayout) findViewById(R.id.fabu);
        meimg = (ImageView) findViewById(R.id.wo_logo);
        homeimg = (ImageView) findViewById(R.id.shouye_logo);
        messageimg = (ImageView) findViewById(R.id.xiaoxi_logo);
        squareimg = (ImageView) findViewById(R.id.guangchang_logo);
        fabuimg = (ImageView) findViewById(R.id.fabu_logo);
        metext = (TextView) findViewById(R.id.wo_text);
        hometext = (TextView) findViewById(R.id.shouye_text);
        messagetext = (TextView) findViewById(R.id.xiaoxi_text);
        squaretext = (TextView) findViewById(R.id.guangchang_text);
        fabutext = (TextView) findViewById(R.id.fabu_text);
        biaotilan = (TextView) findViewById(R.id.biaotilan);
        search = (ImageView) findViewById(R.id.main_search);
        sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
        myApplication = (MyApplication) MainActivity.this.getApplication();
        BASE_URL = "http://ss.sen7u.win:8080/Test2/api/";
    }
    public void clearChoice(){
        meimg.setBackgroundResource(R.drawable.wo);
        metext.setTextColor(0xff808080);
        homeimg.setBackgroundResource(R.drawable.shouye);
        hometext.setTextColor(0xff808080);
        messageimg.setBackgroundResource(R.drawable.xiaoxi);
        messagetext.setTextColor(0xff808080);
        squareimg.setBackgroundResource(R.drawable.guangchang);
        squaretext.setTextColor(0xff808080);
    }
    public void hideFragments(FragmentTransaction fragmentTransaction){
        if(mefg!=null){
            fragmentTransaction.hide(mefg);
        }
        if(homefg!=null){
            fragmentTransaction.hide(homefg);
        }
        if(messagefg!=null){
            fragmentTransaction.hide(messagefg);
        }
        if(squarefg!=null){
            fragmentTransaction.hide(squarefg);
        }
        if(publishfg!=null){
            fragmentTransaction.hide((publishfg));
        }
    }
    @SuppressLint("SetTextI18n")
    void setChioceItem(int index){
        if(myApplication.getState() == 1 && index==2) {
            Toast.makeText(MainActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            clearChoice();
            hideFragments(fragmentTransaction);
            switch(index){
                case 0:
                    homeimg.setBackgroundResource(R.drawable.shouye_fill);
                    hometext.setTextColor(0xff1296DB);
                    biaotilan.setText("EasyTrade");
                    search.setVisibility(View.VISIBLE);
                    if(homefg==null){
                        homefg = new home();
                        fragmentTransaction.add(R.id.content,homefg);
                    }
                    else{
                        fragmentTransaction.show(homefg);
                    }
                    break;
                case 1:
                    squareimg.setBackgroundResource(R.drawable.guangchang_fill);
                    squaretext.setTextColor(0xff1296DB);
                    biaotilan.setText("广场");
                    search.setVisibility(View.GONE);
                    if(squarefg==null){
                        squarefg = new square();
                        fragmentTransaction.add(R.id.content,squarefg);
                    }
                    else {
                        fragmentTransaction.show(squarefg);
                    }
                    break;
                case 2:
                    messageimg.setBackgroundResource(R.drawable.xiaoxi_fill);
                    messagetext.setTextColor(0xff1296DB);
                    biaotilan.setText("消息");
                    search.setVisibility(View.GONE);
                    if(messagefg==null){
                        messagefg = new message();
                        fragmentTransaction.add(R.id.content,messagefg);
                    }
                    else {
                        fragmentTransaction.show(messagefg);
                    }
                    break;
                case 3:
                    meimg.setBackgroundResource(R.drawable.wo_fill);
                    metext.setTextColor(0xff1296DB);
                    biaotilan.setText("我");
                    search.setVisibility(View.GONE);
                    if(mefg==null){
                        mefg = new me();
                        fragmentTransaction.add(R.id.content,mefg);
                    }
                    else {
                        fragmentTransaction.show(mefg);
                    }
                    break;
                case 4:
                    fabutext.setTextColor(0xff1296DB);
                    biaotilan.setText("发布");
                    search.setVisibility(View.GONE);
                    publishfg = new publish();
                    fragmentTransaction.add(R.id.content,publishfg);
            }
            fragmentTransaction.commit();
        }
    }
    void setonclick(){
        homell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(0);
            }
        });
        homeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(0);
            }
        });
        squareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(1);
            }
        });
        squarell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(1);
            }
        });
        messageimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(2);
            }
        });
        messagell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(2);
            }
        });
        meimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(3);
            }
        });
        mell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(3);
            }
        });
        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(4);
            }
        });
        fabuimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(4);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                intent.putExtra("searchType","");
                startActivity(intent);
            }
        });
    }
    void checkPassord()
    {
        final String password = sharedPreferences.getString("password","");
        String username = sharedPreferences.getString("username","");
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
                        }
                        else
                        {
                            myApplication.setState(1);
                            myApplication.setUser_id(-100);
                        }
                    }
                });

    }

}
