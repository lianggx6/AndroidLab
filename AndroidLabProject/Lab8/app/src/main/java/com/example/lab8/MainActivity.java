package com.example.lab8;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button addItem;
    ListView listView;
    MyListAdapter myListAdapter;
    List<String[]> birthList;
    MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddItem.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                @SuppressLint("InflateParams") View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog,null);
                TextView dialogNumber = dialogView.findViewById(R.id.dialog_number);
                final TextView dialogName = dialogView.findViewById(R.id.dialog_add_name);
                final EditText dialogBirth = dialogView.findViewById(R.id.dialog_add_birth);
                final EditText dialogGift = dialogView.findViewById(R.id.dialog_add_gift);
                final String name = birthList.get(pos)[0];
                String number = "";
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?",new String[]{name},null);
                assert cursor != null;
                while (cursor.moveToNext())
                {
                    number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                cursor.close();
                if(number.isEmpty()) number = "暂无";
                dialogName.setText(birthList.get(pos)[0]);
                dialogBirth.setText(birthList.get(pos)[1]);
                dialogGift.setText(birthList.get(pos)[2]);
                dialogNumber.setText(number);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView)
                        .setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String birthday = dialogBirth.getText().toString();
                        String gift = dialogGift.getText().toString();
                        birthList.get(pos)[1] = birthday;
                        birthList.get(pos)[2] = gift;
                        myDatabaseHelper.update(name,birthday,gift);
                        myListAdapter.notifyDataSetChanged();
                    }
                })
                        .setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                        })
                        .create().show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("是否删除？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myDatabaseHelper.delete(birthList.get(pos)[0]);
                                birthList.remove(pos);
                                myListAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {}
                        })
                        .create().show();
                return true;
            }
        });
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        birthList = myDatabaseHelper.getALL();
        myListAdapter = new MyListAdapter(this,birthList);
        listView.setAdapter(myListAdapter);
        myListAdapter.notifyDataSetChanged();
    }

    void initial()
    {
        addItem = findViewById(R.id.add_item);
        listView = findViewById(R.id.listView);
        birthList = new ArrayList<>();
        myDatabaseHelper = new MyDatabaseHelper(this);
        birthList = myDatabaseHelper.getALL();
        myListAdapter = new MyListAdapter(this,birthList);
        listView.setAdapter(myListAdapter);
    }
}
