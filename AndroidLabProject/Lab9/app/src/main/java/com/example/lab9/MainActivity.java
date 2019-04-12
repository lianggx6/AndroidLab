package com.example.lab9;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    String BASEURL = "https://api.github.com";
    Button fetch;
    Button clear;
    EditText name;
    ProgressBar bar;
    RecyclerView recyclerView;
    MyRecyclerAdapter myRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecyclerAdapter);
        bar.setVisibility(View.GONE);
        name.setText(R.string.hellokenlee);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(null);
            }
        });

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setVisibility(View.VISIBLE);
                GithubService service = retrofit(BASEURL).create(GithubService.class);
                service.getUser(name.getText().toString())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<GitHub>() {
                            @Override
                            public void onCompleted() {
                                bar.setVisibility(View.GONE);
                                Log.i("网络传输日志：","传输成功");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this,"出现错误",Toast.LENGTH_SHORT).show();
                                bar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onNext(GitHub gitHub) {
                                myRecyclerAdapter.add(gitHub);
                            }
                        });
            }
        });

        myRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this,ReposActivity.class);
                intent.putExtra("name",myRecyclerAdapter.getGithub(position).login);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {
                myRecyclerAdapter.remove(position);
                Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initial()
    {
        fetch = findViewById(R.id.fetch);
        clear = findViewById(R.id.clear);
        name = findViewById(R.id.name_input);
        bar = findViewById(R.id.mainBar);
        recyclerView = findViewById(R.id.recyclerview);
        myRecyclerAdapter = new MyRecyclerAdapter(MainActivity.this,R.layout.item);
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
