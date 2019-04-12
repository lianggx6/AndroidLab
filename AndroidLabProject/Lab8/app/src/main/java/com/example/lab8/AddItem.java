package com.example.lab8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItem extends AppCompatActivity {

    EditText addName;
    EditText addBirth;
    EditText addGift;
    Button add;
    MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        initial();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = addName.getText().toString();
                String birthday = addBirth.getText().toString();
                String gift = addGift.getText().toString();
                if(name.isEmpty()) Toast.makeText(AddItem.this,"名字为空，请完善",Toast.LENGTH_SHORT).show();
                else if(myDatabaseHelper.add(name,birthday,gift)) finish();
                else Toast.makeText(AddItem.this,"名字重复啦，请检查",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initial()
    {
        addName = findViewById(R.id.add_name);
        addBirth = findViewById(R.id.add_birth);
        addGift = findViewById(R.id.add_gift);
        add = findViewById(R.id.add);
        myDatabaseHelper = new MyDatabaseHelper(this);
    }
}
