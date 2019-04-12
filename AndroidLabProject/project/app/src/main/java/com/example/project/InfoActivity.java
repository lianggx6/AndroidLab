package com.example.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelClass.demands_collect;
import com.example.modelClass.demands_publish_all;
import com.example.modelClass.goods_collect;
import com.example.modelClass.goods_publish_all;
import com.example.modelClass.user;
import com.example.myAdapter.InfoListViewAdapter;
import com.example.netInterface.DataInterface;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InfoActivity extends AppCompatActivity {

    TextView nicheng;
    TextView miaoshu;
    TextView price;
    ImageView back;
    ListView listView;
    List<String> list;
    GlideLoader glideLoader;
    LinearLayout collect;
    TextView chat;
    InfoListViewAdapter infoListViewAdapter;
    RoundedImageView touxiang;
    goods_publish_all modelclass;
    demands_publish_all modelclassdemands;
    MyApplication myApplication;
    String BASE_URL;
    Retrofit retrofit;
    DataInterface dataInterface;
    boolean type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        View headView = LayoutInflater.from(InfoActivity.this).inflate(R.layout.info_head,null);
        nicheng = (TextView) headView.findViewById(R.id.inform_nicheng);
        miaoshu = (TextView) headView.findViewById(R.id.inform_miaoshu);
        price = (TextView) headView.findViewById(R.id.inform_rmb);
        back = (ImageView) findViewById(R.id.inform_back);
        chat = (TextView) findViewById(R.id.inform_yao);
        touxiang = (RoundedImageView) headView.findViewById(R.id.inform_touxiang);
        listView = (ListView) findViewById(R.id.inform_listview);
        collect = (LinearLayout) findViewById(R.id.inform_collect);
        myApplication = (MyApplication) InfoActivity.this.getApplication();
        BASE_URL= "http://ss.sen7u.win:8080/Test2/api/";
        glideLoader = new GlideLoader();
        list = new ArrayList<>();
        type = getIntent().getExtras().getBoolean("type");
        final int other;
        final String name;
        if(type)
        {
            modelclass = (goods_publish_all) getIntent().getExtras().getSerializable("info");
            glideLoader.load(InfoActivity.this,touxiang,modelclass.myseller.avatar);
            nicheng.setText(modelclass.myseller.name);
            miaoshu.setText(modelclass.mygoods.information);
            price.setText("RMB:" + modelclass.mygoods.price);
            String[] sArray=modelclass.mygoods.image.split(";");
            list.addAll(Arrays.asList(sArray));
            other = modelclass.myseller.id;
            name = modelclass.myseller.name;
        }
        else
        {
            modelclassdemands = (demands_publish_all) getIntent().getExtras().getSerializable("info");
            glideLoader.load(InfoActivity.this,touxiang,modelclassdemands.mydemander.avatar);
            nicheng.setText(modelclassdemands.mydemander.name);
            miaoshu.setText(modelclassdemands.mydemands.information);
            String[] sArray=modelclassdemands.mydemands.image.split(";");
            list.addAll(Arrays.asList(sArray));
            price.setText("求购");
            other = modelclassdemands.mydemander.id;
            name = modelclassdemands.mydemander.name;
        }

        listView.addHeaderView(headView);
        infoListViewAdapter = new InfoListViewAdapter(InfoActivity.this,list);
        listView.setAdapter(infoListViewAdapter);
        infoListViewAdapter.notifyDataSetChanged();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.getState() == 1) {
                    Toast.makeText(InfoActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(InfoActivity.this,Dialolg.class);
                    intent.putExtra("other",other);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }

            }
        });

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.getState() == 1) {
                    Toast.makeText(InfoActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())//添加转换器Converter(将json 转为JavaBean)
                            .build();

                    dataInterface = retrofit.create(DataInterface.class);
                    if(type)
                    {
                        dataInterface.addgoodscollect(String.valueOf(myApplication.getUser_id()), String.valueOf(modelclass.mygoods.id))
                                .subscribeOn(Schedulers.io())//新建线程进行网络访问
                                .observeOn(AndroidSchedulers.mainThread())//在主线程处理请求结果
                                .subscribe(new Subscriber<goods_collect>() {
                                    @Override
                                    public void onCompleted() {
                                        System.out.print("Complete the transfer");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("d", "info-onError:" + e.getMessage());
                                    }

                                    @Override
                                    public void onNext(goods_collect l) {
                                        Log.d("d", "info-collect-onNext: ");
                                        Toast.makeText(InfoActivity.this,"已添加到收藏",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else
                    {
                        dataInterface.adddemandscollect(String.valueOf(myApplication.getUser_id()), String.valueOf(modelclassdemands.mydemands.id))
                                .subscribeOn(Schedulers.io())//新建线程进行网络访问
                                .observeOn(AndroidSchedulers.mainThread())//在主线程处理请求结果
                                .subscribe(new Subscriber<demands_collect>() {
                                    @Override
                                    public void onCompleted() {
                                        System.out.print("Complete the transfer");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("d", "info-onError:" + e.getMessage());
                                    }

                                    @Override
                                    public void onNext(demands_collect l) {
                                        Log.d("d", "info-collect-onNext: ");
                                        Toast.makeText(InfoActivity.this,"已添加到收藏",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
        });
    }
}
