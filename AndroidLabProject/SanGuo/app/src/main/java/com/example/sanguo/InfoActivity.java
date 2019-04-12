package com.example.sanguo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    Humans humans;
    String table;
    ImageView back;
    ImageView humanImage;
    ImageView nationImage;
    ImageView collect;
    ImageView xiugai;
    TextView name;
    TextView shengzu;
    TextView jiguan;
    TextView shengping;
    RelativeLayout middle;
    RelativeLayout buttom;
    MyDatabaseManager myDatabaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initial();
        humanImage.setImageResource(humans.imageId);
        name.setText(humans.name);
        shengzu.setText(humans.years);
        shengping.setText(humans.introduction);
        shengping.setMovementMethod(ScrollingMovementMethod.getInstance()) ;
        jiguan.setText(humans.nativePlace);
        if(table.equals("collect"))
        {
            xiugai.setVisibility(View.VISIBLE);
            xiugai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(InfoActivity.this,NewActivity.class);
                    intent.putExtra("human",new Humans(humans));
                    intent.putExtra("come","Info");
                    startActivity(intent);
                    finish();
                }
            });
        }
        else xiugai.setVisibility(View.GONE);
        switch (humans.nation) {
            case "魏":
                middle.setBackgroundResource(R.drawable.weiback);
                buttom.setBackgroundResource(R.drawable.infoweiback);
                nationImage.setImageResource(R.drawable.wei);
                break;
            case "蜀":
                middle.setBackgroundResource(R.drawable.shuback);
                buttom.setBackgroundResource(R.drawable.infoshuback);
                nationImage.setImageResource(R.drawable.shu);
                break;
            case "吴":
                middle.setBackgroundResource(R.drawable.wuback);
                buttom.setBackgroundResource(R.drawable.infowuback);
                nationImage.setImageResource(R.drawable.wu);
                break;
            default:
                middle.setBackgroundResource(R.drawable.qunback);
                buttom.setBackgroundResource(R.drawable.infoqunback);
                nationImage.setImageResource(R.drawable.qun);
                break;
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myDatabaseManager.add(new Humans(humans),"collect"))
                    Toast.makeText(InfoActivity.this,humans.name + "已添加到名将录",Toast.LENGTH_LONG).show();
                else Toast.makeText(InfoActivity.this,humans.name + "已存在于名将录中",Toast.LENGTH_LONG).show();
            }
        });
    }

    void initial()
    {
        humans = (Humans) getIntent().getSerializableExtra("human");
        table = getIntent().getExtras().getString("table");
        back = findViewById(R.id.info_back_button);
        humanImage = findViewById(R.id.info_human_image);
        nationImage = findViewById(R.id.info_nation_image);
        collect = findViewById(R.id.info_collect);
        xiugai = findViewById(R.id.xiugai);
        name = findViewById(R.id.info_name);
        shengzu = findViewById(R.id.info_sheengzu);
        jiguan = findViewById(R.id.info_jiguan);
        shengping = findViewById(R.id.info_shengping);
        middle = findViewById(R.id.info_middle);
        buttom = findViewById(R.id.info_buttom);
        myDatabaseManager = new MyDatabaseManager(InfoActivity.this);
    }

}
