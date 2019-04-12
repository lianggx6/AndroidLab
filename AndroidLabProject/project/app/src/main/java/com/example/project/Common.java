package com.example.project;

import android.util.Log;

import com.example.modelClass.goods_publish_all;
import com.example.netInterface.DataInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Common {
    static public List<goods_publish_all> gpa;

    public Common(){
        gpa = new ArrayList<>();
    }

    static public void gpa_update(){
        String BASE_URL = "http://ss.sen7u.win:8080/Test2/api/";
        Retrofit retrofit;
        DataInterface dataInterface;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//添加转换器Converter(将json 转为JavaBean)
                .build();

        dataInterface = retrofit.create(DataInterface.class);

        dataInterface.getgoods_publish_all()
                .subscribeOn(Schedulers.io())//新建线程进行网络访问
                .observeOn(AndroidSchedulers.mainThread())//在主线程处理请求结果
                .subscribe(new Subscriber<List<goods_publish_all>>() {
                    @Override
                    public void onCompleted() {
                        System.out.print("Complete the transfer");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("d", "onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<goods_publish_all> g) {
                            Log.d("d", "onNext: ");
                        gpa = g;
                    }
                });

    }
}
