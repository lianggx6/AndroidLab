package com.example.lab5;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class InfoActivity extends AppCompatActivity {

    ImageButton back;               //返回
    ImageButton collect;            //收藏
    ImageButton addInCart;          //加入购物车
    TextView name;
    TextView price;
    TextView type;
    TextView type_value;
    ImageView image;
    ListView operation;
    ArrayAdapter<String> arrayAdapter;
    int addTime;            //添加购物车的次数
    MyDynamicReceiver myReceiver = new MyDynamicReceiver();
    IntentFilter filter = new IntentFilter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initial();
        final Goods goods = (Goods) getIntent().getSerializableExtra("goods");
        name.setText(goods.name);
        image.setImageResource(goods.imageId);
        price.setText(goods.price);
        type.setText(goods.type);
        type_value.setText(goods.type_value);
        if(goods.isCollect) collect.setImageResource(R.mipmap.full_star);
        else collect.setImageResource(R.mipmap.empty_star);
        String [] operations = new String[]{
                "    一键下单","    分享商品","    不感兴趣","    查看更多商品促销信息"
        };
        arrayAdapter = new ArrayAdapter<>(InfoActivity.this, R.layout.oper, operations);
        operation.setAdapter(arrayAdapter);

        filter.addAction("Dynamic");
        registerReceiver(myReceiver, filter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goods.isCollect = !goods.isCollect;
                if(goods.isCollect) {
                    collect.setImageResource(R.mipmap.full_star);
                    Toast.makeText(InfoActivity.this,"商品已收藏",Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new MsgEvent(goods.isCollect,0));
                }
                else {
                    collect.setImageResource(R.mipmap.empty_star);
                    Toast.makeText(InfoActivity.this,"商品已取消收藏",Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new MsgEvent(goods.isCollect,0));
                }
            }
        });

        addInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTime++;
                Toast.makeText(InfoActivity.this,"商品已添加到购物车",Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new MsgEvent(goods.isCollect,1));
                Intent boardcastIntent = new Intent("Dynamic");
                boardcastIntent.putExtra("goods",goods);
                sendBroadcast(boardcastIntent);
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    void initial()
    {
        back = (ImageButton) findViewById(R.id.back);
        collect = (ImageButton) findViewById(R.id.collect);
        name = (TextView) findViewById(R.id.name);
        image = (ImageView) findViewById(R.id.image);
        addInCart = (ImageButton) findViewById(R.id.addInCart);
        price = (TextView) findViewById(R.id.price);
        type = (TextView) findViewById(R.id.type);
        type_value = (TextView) findViewById(R.id.type_value);
        operation = (ListView) findViewById(R.id.operation);
        addTime = 0;
    }


}
