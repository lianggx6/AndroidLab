package com.example.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.modelClass.goods_publish_all;
import com.example.myAdapter.SearchListViewAdapter;
import com.example.myAdapter.SquareListViewAdapter;
import com.example.netInterface.DataInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    ImageView back;
    TextView search;
    EditText input;
    ListView listView;
    List<goods_publish_all> list;
    String edit;
    String BASE_URL;
    Retrofit retrofit;
    DataInterface dataInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initial();
        edit = getIntent().getStringExtra("searchType");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//添加转换器Converter(将json 转为JavaBean)
                .build();
        if(!edit.equals(""))
        {
            input.setText(edit);

            dataInterface = retrofit.create(DataInterface.class);
            dataInterface.getlikebypara1(edit)
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
                            list.clear();
                            for(int i = 0; i < l; i++)
                            {
                                list.add(g.get(i));
                            }
                            SearchListViewAdapter searchListViewAdapter = new SearchListViewAdapter(SearchActivity.this,list);
                            listView.setAdapter(searchListViewAdapter);
                            searchListViewAdapter.notifyDataSetChanged();
                        }
                    });
            input.clearFocus();
            input.setSelected(false);
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit = input.getText().toString();
                dataInterface = retrofit.create(DataInterface.class);
                dataInterface.getlikebypara1(edit)
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
                                list.clear();
                                for(int i = 0; i < l; i++)
                                {
                                    list.add(g.get(i));
                                }
                                SearchListViewAdapter searchListViewAdapter = new SearchListViewAdapter(SearchActivity.this,list);
                                listView.setAdapter(searchListViewAdapter);
                                searchListViewAdapter.notifyDataSetChanged();
                            }
                        });
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, InfoActivity.class);
                intent.putExtra("type",true);    //0为发布，1为求购
                intent.putExtra("info",list.get(position));
                startActivity(intent);
            }
        });

    }


    void initial()
    {
        back = (ImageView) findViewById(R.id.search_back_image);
        search = (TextView) findViewById(R.id.search_sousuo_text);
        input = (EditText) findViewById(R.id.search_edit);
        listView = (ListView) findViewById(R.id.search_listview);
        list = new ArrayList<>();
        edit = "";
        BASE_URL = "http://ss.sen7u.win:8080/Test2/api/";
    }
}
