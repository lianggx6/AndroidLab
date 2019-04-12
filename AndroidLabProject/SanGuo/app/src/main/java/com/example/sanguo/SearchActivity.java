package com.example.sanguo;

import android.content.Intent;
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

public class SearchActivity extends AppCompatActivity {

    ListView allListView;
    MyListAdapter allListAdapter;
    MyListAdapter selectListAdapter;
    MyDatabaseManager myDatabaseManager;
    List<Humans> allHumansList;
    EditText searchEdit;
    ImageView searchBack;
    ImageView searchFind;
    InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initial();
        allListView.setAdapter(allListAdapter);
        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                finish();
            }
        });
        allListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this,InfoActivity.class);
                intent.putExtra("human",allHumansList.get(i));
                intent.putExtra("table","human");
                startActivity(intent);
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
        searchFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = searchEdit.getText().toString();
//                List<Humans> selectHumansList;
                if(input.length() > 0)
                {
                    allHumansList = myDatabaseManager.query(input,"human");
                    Toast.makeText(SearchActivity.this,input,Toast.LENGTH_LONG).show();
                }
                else
                {
                    allHumansList = myDatabaseManager.getALL("human");
                    int  size =  myDatabaseManager.getALL("human").size();
                    Toast.makeText(SearchActivity.this,String.valueOf(size),Toast.LENGTH_LONG).show();
                }
                selectListAdapter = new MyListAdapter(SearchActivity.this,allHumansList);
                allListView.setAdapter(selectListAdapter);
                selectListAdapter.notifyDataSetChanged();
            }
        });
    }

    void initial()
    {
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        myDatabaseManager = new MyDatabaseManager(SearchActivity.this);
        myDatabaseManager.initial("human");
        allHumansList = new ArrayList<>();
        allHumansList = myDatabaseManager.getALL("human");
        allListView = findViewById(R.id.all_list);
        searchEdit = findViewById(R.id.search_edit);
        searchBack = findViewById(R.id.search_back);
        searchFind = findViewById(R.id.search_find);
        allListAdapter = new MyListAdapter(SearchActivity.this,allHumansList);
    }
}
