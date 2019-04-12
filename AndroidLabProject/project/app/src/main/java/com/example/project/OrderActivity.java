package com.example.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.example.modelClass.*;
import com.example.myAdapter.CollectOrderAdapter;
import com.example.myAdapter.PublishOrderAdapter;
import com.example.myAdapter.SquareListViewAdapter;
import com.example.netInterface.DataInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        TextView title = (TextView) findViewById(R.id.order_title_text);

        MyApplication myApplication = (MyApplication) OrderActivity.this.getApplication() ;

        String BASE_URL= "http://ss.sen7u.win:8080/Test2/api/";
        Retrofit retrofit;
        DataInterface dataInterface;
        ImageButton back = (ImageButton) findViewById(R.id.order_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final ListView listView ;
        Intent intent = getIntent();
        int i = intent.getExtras().getInt("title");
        switch (i)
        {
            case 0:
                title.setText("我发布的");
                final List<my_publish> list0 = new ArrayList<>();

                final PublishOrderAdapter publishOrderAdapter = new PublishOrderAdapter(OrderActivity.this,list0);

                listView = (ListView) findViewById(R.id.order_listview);

                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())//添加转换器Converter(将json 转为JavaBean)
                        .build();
                dataInterface = retrofit.create(DataInterface.class);

                dataInterface.getmypublish(String.valueOf(myApplication.getUser_id()))
                        .subscribeOn(Schedulers.io())//新建线程进行网络访问
                        .observeOn(AndroidSchedulers.mainThread())//在主线程处理请求结果
                        .subscribe(new Subscriber<List<my_publish>>() {
                            @Override
                            public void onCompleted() {
                                System.out.print("Complete the transfer");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("d", "order-collect-onError:" + e.getMessage());
                            }

                            @Override
                            public void onNext(List<my_publish> d) {
                                Log.d("d", "order-collect-onNext: ");
                                int l = d.size();
                                list0.clear();
                                for(int i = 0; i < l; i++)
                                {
                                    list0.add(d.get(i));
                                }
                                listView.setAdapter(publishOrderAdapter);
                                publishOrderAdapter.notifyDataSetChanged();
                            }
                        });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(OrderActivity.this, InfoActivity.class);

                        if (list0.get(position).judge_id == 0 ) {//0为商品，1为需求
                            intent.putExtra("type",true);    //false为需求，true为商品
                            goods_publish_all goods = publishToGoods(list0.get(position));
                            intent.putExtra("info", goods);
                        }
                        else{

                            intent.putExtra("type",false);    //false为需求，true为商品
                            demands_publish_all demands = publishToDemands(list0.get(position));
                            intent.putExtra("info", demands);
                        }
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                title.setText("我收藏的");
                final List<my_collect> list = new ArrayList<>();

                final CollectOrderAdapter collectOrderAdapter = new CollectOrderAdapter(OrderActivity.this,list);

                listView = (ListView) findViewById(R.id.order_listview);

                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())//添加转换器Converter(将json 转为JavaBean)
                        .build();
                dataInterface = retrofit.create(DataInterface.class);

                dataInterface.getmycollect(String.valueOf(myApplication.getUser_id()))
                        .subscribeOn(Schedulers.io())//新建线程进行网络访问
                        .observeOn(AndroidSchedulers.mainThread())//在主线程处理请求结果
                        .subscribe(new Subscriber<List<my_collect>>() {
                            @Override
                            public void onCompleted() {
                                System.out.print("Complete the transfer");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("d", "order-collect-onError:" + e.getMessage());
                            }

                            @Override
                            public void onNext(List<my_collect> d) {
                                Log.d("d", "order-collect-onNext: ");
                                int l = d.size();
                                list.clear();
                                for(int i = 0; i < l; i++)
                                {
                                    list.add(d.get(i));
                                }
                                listView.setAdapter(collectOrderAdapter);
                                collectOrderAdapter.notifyDataSetChanged();
                            }
                        });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(OrderActivity.this, InfoActivity.class);

                        if (list.get(position).judge_id == 0 ) {//0为商品，1为需求
                            intent.putExtra("type",true);    //false为需求，true为商品
                            goods_publish_all goods = collectToGoods(list.get(position));
                            intent.putExtra("info", goods);
                        }
                        else{

                            intent.putExtra("type",false);    //false为需求，true为商品
                            demands_publish_all demands = collectToDemands(list.get(position));
                            intent.putExtra("info", demands);
                        }
                        startActivity(intent);
                    }
                });

                break;
            case 2:
                title.setText("我卖出的");



                break;
            case 3:
                title.setText("我买到的");
                break;
        }


    }

    private goods_publish_all collectToGoods(my_collect c){
        goods_publish_all g= new goods_publish_all();
        g.myseller = new user();
        g.mygoods = new goods();
        g.mygoods_publish = new goods_publish();

        g.myseller.name = c.announcer_name;
        g.myseller.avatar = c.announcer_avatar;
        g.mygoods.information = c.collect_information;
        g.mygoods.price = c.collect_price;
        g.mygoods.image = c.collect_image;
        g.myseller.id = c.announcer_id;
        return g;
    }
    private demands_publish_all collectToDemands(my_collect c){
        demands_publish_all g= new demands_publish_all();


        g.mydemander = new user();
        g.mydemands = new goods();
        g.mydemands_publish = new demands_publish();

        g.mydemander.name = c.announcer_name;
        g.mydemander.avatar = c.announcer_avatar;
        g.mydemands.information = c.collect_information;
        g.mydemands.price = c.collect_price;
        g.mydemands.image = c.collect_image;
        g.mydemander.id = c.announcer_id;
        return g;
    }
    private goods_publish_all publishToGoods(my_publish c){
        goods_publish_all g= new goods_publish_all();
        g.myseller = new user();
        g.mygoods = new goods();
        g.mygoods_publish = new goods_publish();

        g.myseller.name = c.announcer_name;
        g.myseller.avatar = c.announcer_avatar;
        g.mygoods.information = c.publish_information;
        g.mygoods.price = c.publish_price;
        g.mygoods.image = c.publish_image;
        g.myseller.id = c.announcer_id;
        return g;
    }
    private demands_publish_all publishToDemands(my_publish c){
        demands_publish_all g= new demands_publish_all();


        g.mydemander = new user();
        g.mydemands = new goods();
        g.mydemands_publish = new demands_publish();

        g.mydemander.name = c.announcer_name;
        g.mydemander.avatar = c.announcer_avatar;
        g.mydemands.information = c.publish_information;
        g.mydemands.price = c.publish_price;
        g.mydemands.image = c.publish_image;
        g.mydemander.id = c.announcer_id;
        return g;
    }
}
