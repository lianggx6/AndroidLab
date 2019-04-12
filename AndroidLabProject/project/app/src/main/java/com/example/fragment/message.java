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

import com.example.modelClass.messages;
import com.example.myAdapter.MessagesListViewAdapter;
import com.example.netInterface.DataInterface;
import com.example.project.Dialolg;
import com.example.project.MyApplication;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class message extends Fragment {
    List<messages> data;
    int user_id;
    ListView listView;
    private  static OkHttpClient createOkHttp(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }
    public void getdata(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ss.sen7u.win:8080/Test2/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createOkHttp())
                .build();
        DataInterface service = retrofit.create(DataInterface.class);
        service.getmessage (Integer.toString(user_id))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<messages>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("完成传输" + user_id);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("d", "wrong1 ");
                    }

                    @Override
                    public void onNext(List<messages> mess) {
                        int flag = 1;
                        if(data.size()!=mess.size()) flag = 2;

                        if(flag==2){
                            data = mess;
                            MessagesListViewAdapter listViewAdapter = new MessagesListViewAdapter(getContext(),data);
                            listView.setAdapter(listViewAdapter);
                        }
                    }
                });
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user_id = ((MyApplication) getActivity().getApplication()).getUser_id();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        data = new ArrayList<messages>();
        View view = inflater.inflate(R.layout.message,container,false);
        listView = (ListView) view.findViewById(R.id.message_listview);
        getdata();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int other = data.get(position).sender_id;
                Intent intent = new Intent(getContext(), Dialolg.class);
                intent.putExtra("other",other);
                intent.putExtra("name",data.get(position).sender_name);
                startActivity(intent);
            }
        });
        @SuppressLint("HandlerLeak") final Handler mHandler = new Handler(){
            @Override
            public  void handleMessage(Message msg){
                super.handleMessage(msg);
                switch(msg.what){
                    case 123:
                        getdata();
                        break;
                }
            }
        };
        Thread mtherad = new Thread(){
            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(1000);
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
