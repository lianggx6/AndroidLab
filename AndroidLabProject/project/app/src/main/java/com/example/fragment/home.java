package com.example.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.modelClass.goods_publish_all;
import com.example.myAdapter.HomeListViewAdapter;
import com.example.netInterface.DataInterface;
import com.example.project.GlideLoader;
import com.example.project.InfoActivity;
import com.example.project.R;
import com.example.project.SearchActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.Gravity.START;


public class home extends Fragment implements ViewPager.OnPageChangeListener,OnPageClickListener
{
    List<goods_publish_all> adapterlist;
    private ArrayList<Page> pageViews;
    HomeListViewAdapter homeListViewAdapter;
    ListView listView;
    String BASE_URL;
    Retrofit retrofit;
    DataInterface dataInterface;
    LinearLayout mancloth;
    LinearLayout womancloth;
    LinearLayout game;
    LinearLayout goods;
    LinearLayout foods;
    LinearLayout phone;
    LinearLayout make;
    LinearLayout shoes;
    LinearLayout sport;
    LinearLayout machine;
    Intent searchIntent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home,container,false);
        View headView = inflater.inflate(R.layout.home_head,null);
        initData();
        InfiniteIndicator mAnimCircleIndicator = (InfiniteIndicator) headView.findViewById(R.id.indicator_default_circle);
        listView = (ListView) view.findViewById(R.id.home_listview);

        IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                .imageLoader(new GlideLoader())
                .isStopWhileTouch(true)
                .onPageChangeListener(this)
                .onPageClickListener(this)
                .direction(START)
                .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                .build();
        mAnimCircleIndicator.init(configuration);
        mAnimCircleIndicator.notifyDataChange(pageViews);
        listView.addHeaderView(headView);
        mancloth = (LinearLayout) headView.findViewById(R.id.mancloth);
        womancloth = (LinearLayout) headView.findViewById(R.id.womancloth);
        game = (LinearLayout) headView.findViewById(R.id.game);
        goods = (LinearLayout) headView.findViewById(R.id.goods);
        foods = (LinearLayout) headView.findViewById(R.id.foods);
        phone = (LinearLayout) headView.findViewById(R.id.phone);
        make = (LinearLayout) headView.findViewById(R.id.make);
        shoes = (LinearLayout) headView.findViewById(R.id.shoes);
        sport = (LinearLayout) headView.findViewById(R.id.sport);
        machine = (LinearLayout) headView.findViewById(R.id.machine);
        searchIntent = new Intent(getContext(), SearchActivity.class);
        mancloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIntent.putExtra("searchType","男装");
                startActivity(searchIntent);
            }
        });
        womancloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIntent.putExtra("searchType","女装");
                startActivity(searchIntent);
            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIntent.putExtra("searchType","游戏装备");
                startActivity(searchIntent);
            }
        });
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIntent.putExtra("searchType","生活百货");
                startActivity(searchIntent);
            }
        });
        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIntent.putExtra("searchType","休闲零食");
                startActivity(searchIntent);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIntent.putExtra("searchType","手机数码");
                startActivity(searchIntent);
            }
        });
        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIntent.putExtra("searchType","美妆");
                startActivity(searchIntent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIntent.putExtra("searchType","鞋靴");
                startActivity(searchIntent);
            }
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIntent.putExtra("searchType","运动户外");
                startActivity(searchIntent);
            }
        });
        machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIntent.putExtra("searchType","家用电器");
                startActivity(searchIntent);
            }
        });

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
                        int l = g.size();
                        adapterlist.clear();
                        for(int i = 0; i < l; i++)
                        {
                            adapterlist.add(g.get(i));
                        }
                    }
                });
        homeListViewAdapter = new HomeListViewAdapter(getContext(),adapterlist);
        listView.setAdapter(homeListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), InfoActivity.class);
                intent.putExtra("type",true);    //0为发布，1为求购
                intent.putExtra("info",adapterlist.get(position-1));
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
                                        int l = g.size();
                                        if(l!=adapterlist.size()){
                                            adapterlist.clear();
                                            for(int i = 0; i < l; i++)
                                            {
                                                adapterlist.add(g.get(i));
                                            }
                                            homeListViewAdapter = new HomeListViewAdapter(getContext(),adapterlist);
                                            listView.setAdapter(homeListViewAdapter);
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

    private void initData() {
        pageViews = new ArrayList<>();
        adapterlist = new ArrayList<>();
        BASE_URL = "http://ss.sen7u.win:8080/Test2/api/";
        pageViews.add(new Page("A", "https://wx4.sinaimg.cn/mw690/0069T6wHly1fnc7qdts2uj30e708cdgh.jpg",this));
        pageViews.add(new Page("B", "https://wx3.sinaimg.cn/mw690/0069T6wHly1fnc7qe01ehj30dw07t0vn.jpg",this));
        pageViews.add(new Page("C", "https://wx1.sinaimg.cn/mw690/0069T6wHly1fnc7qe2o5xj30dw09bt99.jpg",this));
        pageViews.add(new Page("D", "https://wx4.sinaimg.cn/mw690/0069T6wHly1fnc7qe9p4kj30h107zmzj.jpg",this));
        pageViews.add(new Page("E", "https://wx4.sinaimg.cn/mw690/0069T6wHly1fnc7qe8fb3j30zs0khqjg.jpg",this));
        pageViews.add(new Page("F", "https://wx2.sinaimg.cn/mw690/0069T6wHly1fnc7qe9xkxj30zk0gon5k.jpg",this));
        pageViews.add(new Page("G", "https://wx1.sinaimg.cn/mw690/0069T6wHly1fnc7qecy6dj30zk0go78n.jpg",this));


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageClick(int position, Page page) {

    }
}
