package com.example.sanguo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity {

    ImageView setting;
    ImageView search;
    ImageView xinjian;
    ListView collectListView;
    EditText searchEdit;
    List<Humans> collectHumansList;
    MyListAdapter collectListAdapter;
    MyDatabaseManager myDatabaseManager;
    InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initial();
        collectListView.setAdapter(collectListAdapter);
        search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("PrivateResource")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectActivity.this,StartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                finish();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("PrivateResource")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectActivity.this,SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                finish();
            }
        });
        searchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEdit.setFocusable(true);
                searchEdit.setFocusableInTouchMode(true);
                searchEdit.requestFocus();
                searchEdit.findFocus();
                imm.showSoftInput(searchEdit, InputMethodManager.SHOW_FORCED);
            }
        });
        collectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CollectActivity.this,InfoActivity.class);
                intent.putExtra("human",collectHumansList.get(i));
                intent.putExtra("table","collect");
                startActivity(intent);
            }
        });

        collectListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CollectActivity.this);
                final String tempName = collectHumansList.get(i).name;
                mBuilder.setTitle("删除人物")
                        .setMessage("从名将录中删除" + tempName)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int pos) {
                                myDatabaseManager.delete(collectHumansList.get(i));
                                collectHumansList.remove(i);
                                collectListAdapter.notifyDataSetChanged();
                                Toast.makeText(CollectActivity.this,tempName + "已从名将录中删除",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .create().show();
                return true;
            }
        });

        xinjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectActivity.this,NewActivity.class);
                intent.putExtra("come","collect");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
        if(intent.getExtras().getBoolean("shoppingVisiablity"))
        {

        }
    }
    void initial()
    {
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        search = findViewById(R.id.search_image);
        setting = findViewById(R.id.setting_image);
        xinjian = findViewById(R.id.search_xinjian);
        collectListView = findViewById(R.id.collect_list);
        searchEdit = findViewById(R.id.search_edit);
        collectHumansList = new ArrayList<>();
        myDatabaseManager = new MyDatabaseManager(CollectActivity.this);
        myDatabaseManager.initial("collect");
        collectHumansList = myDatabaseManager.getALL("collect");
        collectListAdapter = new MyListAdapter(CollectActivity.this,collectHumansList);
    }
}
