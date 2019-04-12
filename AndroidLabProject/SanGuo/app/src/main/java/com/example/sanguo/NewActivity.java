package com.example.sanguo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewActivity extends AppCompatActivity {

    Humans humans;
    ImageView cancle;
    ImageView humanImage;
    ImageView save;
    EditText name;
    EditText jiguan;
    EditText shengping;
    EditText nation;
    EditText birth;
    EditText dead;
    TextView middle;
    MyDatabaseManager myDatabaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        initial();
        final String come = getIntent().getExtras().getString("come");
        assert come != null;
        if(come.equals("Info"))
        {

            humans = (Humans) getIntent().getSerializableExtra("human");
            humanImage.setImageResource(humans.imageId);
            name.setText(humans.name);
            nation.setText(humans.nation);
            birth.setText(humans.birth);
            dead.setText(humans.dead);
            jiguan.setText(humans.nativePlace);
            shengping.setText(humans.introduction);
        }
        else humans = new Humans();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Humans newHuman = new Humans(name.getText().toString(),
                        nation.getText().toString(),
                        "未知",
                        birth.getText().toString(),
                        dead.getText().toString(),
                        jiguan.getText().toString(),
                        humanImage.getId(),
                        shengping.getText().toString());

                if(come.equals("Info")) myDatabaseManager.delete(humans);
                if(myDatabaseManager.add(newHuman,"collect"))
                {
                    Toast.makeText(NewActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NewActivity.this,CollectActivity.class);
                    startActivity(intent);
                    finish();
                }
                else Toast.makeText(NewActivity.this,"姓名冲突",Toast.LENGTH_LONG).show();

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void initial()
    {
        humanImage = findViewById(R.id.new_human_image);
        name = findViewById(R.id.new_name_edit);
        nation = findViewById(R.id.new_nation_edit);
        birth = findViewById(R.id.new_birth_edit);
        dead = findViewById(R.id.new_dead_edit);
        jiguan = findViewById(R.id.new_jiguan_edit);
        shengping = findViewById(R.id.new_shengping);
        middle = findViewById(R.id.new_middle);
        save = findViewById(R.id.new_save);
        cancle = findViewById(R.id.new_cancle);
        myDatabaseManager = new MyDatabaseManager(NewActivity.this);
    }
}
