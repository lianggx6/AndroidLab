package com.example.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.modelClass.demands_publish_all;
import com.example.myAdapter.SquareListViewAdapter;
import com.example.netInterface.DataInterface;
import com.example.project.InfoActivity;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class square extends Fragment {
    List<demands_publish_all> list;
    ListView listView;
    String BASE_URL;
    Retrofit retrofit;
    DataInterface dataInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.square,container,false);
        listView = (ListView) view.findViewById(R.id.square_listview);
        list = new ArrayList<>();
        BASE_URL = "http://ss.sen7u.win:8080/Test2/api/";
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//添加转换器Converter(将json 转为JavaBean)
                .build();
        dataInterface = retrofit.create(DataInterface.class);

        dataInterface.getdemands_publish_all()
                .subscribeOn(Schedulers.io())//新建线程进行网络访问
                .observeOn(AndroidSchedulers.mainThread())//在主线程处理请求结果
                .subscribe(new Subscriber<List<demands_publish_all>>() {
                    @Override
                    public void onCompleted() {
                        System.out.print("Complete the transfer");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("d", "square-onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<demands_publish_all> d) {
                        Log.d("d", "sauare-onNext: ");
                        int l = d.size();
                        list.clear();
                        for(int i = 0; i < l; i++)
                        {
                            list.add(d.get(i));
                        }
                        SquareListViewAdapter squareListViewAdapter = new SquareListViewAdapter(getContext(),list);
                        listView.setAdapter(squareListViewAdapter);
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), InfoActivity.class);
                intent.putExtra("type",false);    //0为发布，1为求购
                intent.putExtra("info",list.get(position));
                startActivity(intent);
            }
        });
        @SuppressLint("HandlerLeak") final Handler mHandler = new Handler(){
            @Override
            public  void handleMessage(Message msg){
                super.handleMessage(msg);
                switch(msg.what){
                    case 123:
                        retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())//添加转换器Converter(将json 转为JavaBean)
                                .build();
                        dataInterface = retrofit.create(DataInterface.class);

                        dataInterface.getdemands_publish_all()
                                .subscribeOn(Schedulers.io())//新建线程进行网络访问
                                .observeOn(AndroidSchedulers.mainThread())//在主线程处理请求结果
                                .subscribe(new Subscriber<List<demands_publish_all>>() {
                                    @Override
                                    public void onCompleted() {
                                        System.out.print("Complete the transfer");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("d", "square-onError:" + e.getMessage());
                                    }

                                    @Override
                                    public void onNext(List<demands_publish_all> d) {
                                        Log.d("d", "sauare-onNext: ");
                                        int l = d.size();
                                        if(l!=list.size()){
                                            list.clear();
                                            for(int i = 0; i < l; i++)
                                            {
                                                list.add(d.get(i));
                                            }
                                            SquareListViewAdapter squareListViewAdapter = new SquareListViewAdapter(getContext(),list);
                                            listView.setAdapter(squareListViewAdapter);
                                        }
                                    }
                                });
                        break;
                }
            }
        };
        Thread mtherad = new Thread(){
            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(10000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    mHandler.obtainMessage(123).sendToTarget();
                }
            }
        };
        mtherad.start();
        return view;
    }
}
