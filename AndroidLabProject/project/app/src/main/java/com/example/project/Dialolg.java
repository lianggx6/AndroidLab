package com.example.project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelClass.messages;
import com.example.myAdapter.DialogListViewAdapter;
import com.example.netInterface.DataInterface;
import com.nostra13.universalimageloader.utils.L;

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

/**
 * Created by Sen7u on 2018/1/21.
 */

public class Dialolg extends AppCompatActivity {

    int other;
    int me;
    String name;
    List<messages> data;
    DialogListViewAdapter listViewAdapter;
    ListView listView;
    TextView title;

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
                service.getdialog(Integer.toString(me),Integer.toString(other))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<messages>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("完成传输");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(Dialolg.this,Integer.toString(me)+Integer.toString(other)+e.getMessage()+"请确认你搜索的用户存在2",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<messages> mess) {
                        int flag = 1;
                        if(data.size()!=mess.size()){
                            flag  = 2;
                        }
                        if(flag == 2) {
                            data = mess;
                            for(int i=0;i<data.size();i++){
                                messages one = data.get(i);
                                if(one.sender_id==me) one.direction = "right";
                                else one.direction = "left";
                                data.set(i,one);
                            }
                            final DialogListViewAdapter listViewAdapter = new DialogListViewAdapter(Dialolg.this,data);
                            listView.setAdapter(listViewAdapter);
                        }
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        listView = (ListView) findViewById(R.id.dialog_list);
        final EditText editText = (EditText) findViewById(R.id.input);
        Button enter = (Button) findViewById(R.id.enter);
        ImageButton back = (ImageButton) findViewById(R.id.dialog_back);
        title = (TextView) findViewById(R.id.dialog_title);
        other = getIntent().getExtras().getInt("other");
        name = getIntent().getExtras().getString("name");
        MyApplication myApplication = (MyApplication) this.getApplication();
        me = myApplication.getUser_id();
        data = new ArrayList<messages>();
        getdata();
        final DialogListViewAdapter listViewAdapter = new DialogListViewAdapter(Dialolg.this,data);
        listView.setAdapter(listViewAdapter);
        title.setText(name);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                editText.setText("");
                if(content.equals("")){
                    Toast.makeText(Dialolg.this,"输入为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ss.sen7u.win:8080/Test2/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(createOkHttp())
                            .build();
                    DataInterface service = retrofit.create(DataInterface.class);
                    service.addchat(Integer.toString(me),Integer.toString(other),content)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<messages>() {
                                @Override
                                public void onCompleted() {
                                    System.out.println("完成传输");
                                }

                                @Override
                                public void onError(Throwable e) {
//                                    Toast.makeText(Dialolg.this,Integer.toString(me)+Integer.toString(other)+e.getMessage()+"请确认你搜索的用户存在2",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNext(messages mess) {
                                    getdata();
                                    DialogListViewAdapter listViewAdapter = new DialogListViewAdapter(Dialolg.this,data);
                                    listView.setAdapter(listViewAdapter);
                                }
                            });
                }
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
    }
}
