package com.example.lab9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReposActivity extends AppCompatActivity {

    String BASEURL = "https://api.github.com";
    ListView listView;
    ProgressBar bar;
    SimpleAdapter simpleAdapter;
    List<Map<String,String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        initial();

        GithubService service = retrofit(BASEURL).create(GithubService.class);
        final String name = getIntent().getStringExtra("name");
        service.getRepos(name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Repos>>() {
                    @Override
                    public void onCompleted() {
                        bar.setVisibility(View.GONE);
                        Log.i("网络传输日志：","传输成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ReposActivity.this,"出现错误",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Repos> repos) {
                        for (Repos repo:repos) {
                            Map<String,String> map = new HashMap<>();
                            map.put("name",repo.name);
                            map.put("language",repo.language);
                            map.put("description",repo.description);
                            list.add(map);
                        }
                        simpleAdapter.notifyDataSetChanged();
                    }
                });

    }

    void initial()
    {
        listView = findViewById(R.id.listview);
        list = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(ReposActivity.this,list,R.layout.item,
                                new String[]{"name","language","description"}, new int[]{R.id.name,R.id.id,R.id.blog});
        listView.setAdapter(simpleAdapter);
        bar = findViewById(R.id.reposBar);
    }

    private static OkHttpClient createOkHttp()
    {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
    }

    private static Retrofit retrofit(String baseUrl)
    {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createOkHttp())
                .build();
    }
}
