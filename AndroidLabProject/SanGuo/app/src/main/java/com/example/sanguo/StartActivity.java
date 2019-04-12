package com.example.sanguo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class StartActivity extends AppCompatActivity {

    ListView randomListView;
    TextView searchText;
    MyListAdapter randomListAdapter;
    MyDatabaseManager myDatabaseManager;
    List<Humans> randomHumansList;
    List<Humans> allHumansList;
    ImageView setting;
    ImageView collect;
    ImageView change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initial();
        randomListView.setAdapter(randomListAdapter);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("PrivateResource")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                finish();
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("PrivateResource")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,CollectActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                finish();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeRandom();
                randomListAdapter.notifyDataSetChanged();
            }
        });
        randomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(StartActivity.this,InfoActivity.class);
                intent.putExtra("human",randomHumansList.get(i));
                intent.putExtra("table","human");
                startActivity(intent);
            }
        });
    }

    void initial()
    {
        myDatabaseManager = new MyDatabaseManager(StartActivity.this);
        myDatabaseManager.initial("human");
        randomHumansList = new ArrayList<>();
        allHumansList = new ArrayList<>();
        allHumansList = myDatabaseManager.getALL("human");
        randomListView = findViewById(R.id.random_list);
        searchText = findViewById(R.id.search_text);
        setting = findViewById(R.id.setting_image);
        collect = findViewById(R.id.list_image);
        change = findViewById(R.id.change_image);
        changeRandom();
        randomListAdapter = new MyListAdapter(StartActivity.this,randomHumansList);
    }

    void changeRandom()
    {
        randomHumansList.clear();
        Random random = new Random();
        int size = allHumansList.size();
        randomHumansList.add(allHumansList.get(random.nextInt(size)));
        randomHumansList.add(allHumansList.get(random.nextInt(size)));
        randomHumansList.add(allHumansList.get(random.nextInt(size)));
    }
}
