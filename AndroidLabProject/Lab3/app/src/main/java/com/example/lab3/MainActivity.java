package com.example.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.recyclerview.itemanimator.SlideInOutRightItemAnimator;


public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyRecyclerAdapter mRecyclerAdapter;
    ListView mListView;
    MyListAdapter mListAdapter;
    List<Goods> goodsList;
    List<Goods> cartList;
    FloatingActionButton FAButton;
    int addTime;
    int browsingPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mListView.setAdapter(mListAdapter);
        mListView.setVisibility(View.GONE);

        FAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRecyclerView.getVisibility() == View.GONE)
                {
                    mListView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                     FAButton.setImageResource(R.mipmap.shoplist);
                }
                else
                {
                    mRecyclerView.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                    FAButton.setImageResource(R.mipmap.mainpage);
                }
            }
        });

        mRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                browsingPosition = position;
                intent.putExtra("goods",goodsList.get(position));
                startActivityForResult(intent,2);
            }

            @Override
            public void onLongClick(int position) {
                mRecyclerAdapter.remove(position);
                mRecyclerView.setItemAnimator(new SlideInOutRightItemAnimator(mRecyclerView));
                String temp = "移除第";
                Toast.makeText(MainActivity.this,
                        temp.concat(String.valueOf(position+1)).concat("个商品"),Toast.LENGTH_SHORT).show();
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                if(position!=0)
                {
                    browsingPosition = position;
                    intent.putExtra("goods",cartList.get(position));
                    startActivityForResult(intent,3);
                }
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if(position != 0)
                {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                    mBuilder.setTitle("移除商品")
                            .setMessage("从购物车删除" + cartList.get(position).name)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    cartList.remove(position);
                                    mListAdapter.notifyDataSetChanged();
                                    Toast.makeText(MainActivity.this,"商品已删除",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .create().show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"购物车共"+String.valueOf(cartList.size()-1)+"件商品",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        addTime = intent.getExtras().getInt("addTime");
        if (requestCode == 2)
        {
            goodsList.get(browsingPosition).isCollect = intent.getExtras().getBoolean("isCollect");
            for(int i = 0; i < addTime; i++)
                cartList.add(goodsList.get(browsingPosition));
        }
        else
        {
            cartList.get(browsingPosition).isCollect = intent.getExtras().getBoolean("isCollect");
            for(int i = 0; i < addTime; i++)
                cartList.add(cartList.get(browsingPosition));
        }
            mListAdapter.notifyDataSetChanged();
    }

    void initial()
    {
        goodsList = new ArrayList<>();
        cartList = new ArrayList<>();
        cartList.add(new Goods("购物车","价格   ","类型","产品信息",R.mipmap.shoplist));
        goodsList.add(new Goods("Enchated Forest",   "¥ 5.00",    "作者",  "Johanna Basford",R.mipmap.enchatedforest));
        goodsList.add(new Goods("Arla Milk",          "¥ 59.00",   "产地",  "德国",R.mipmap.arla));
        goodsList.add(new Goods("Devondale Milk",    "¥ 79.00",   "产地",  "澳大利亚",R.mipmap.devondale));
        goodsList.add(new Goods("Kindle Oasis",      "¥ 2399.00", "版本",  "8GB",R.mipmap.kindle));
        goodsList.add(new Goods("waitrose 早餐麦片", "¥ 179.00",  "重量",  "2kg",R.mipmap.waitrose));
        goodsList.add(new Goods("Mcvitie's 饼干",    "¥ 14.90",   "产地",  "英国",R.mipmap.mcvitie));
        goodsList.add(new Goods("Ferrero Rocher",    "¥ 132.59",  "重量",  "300g",R.mipmap.ferrero));
        goodsList.add(new Goods("Maltesers",          "¥ 141.43",  "重量",  "118g",R.mipmap.maltesers));
        goodsList.add(new Goods("Lindt",               "¥ 5.00",    "重量",  "249g",R.mipmap.lindt));
        goodsList.add(new Goods("Borggreve",          "¥ 28.90",   "重量",  "640g",R.mipmap.borggreve));
        mRecyclerView = (RecyclerView)findViewById(R.id.goods_list);
        mListView = (ListView) findViewById(R.id.cart_list);
        FAButton = (FloatingActionButton)findViewById(R.id.cart);
        mRecyclerAdapter = new MyRecyclerAdapter(MainActivity.this,R.layout.item,goodsList);
        mListAdapter = new MyListAdapter(MainActivity.this,cartList);
    }
}


