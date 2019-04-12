package com.example.lab7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class EditActivity extends AppCompatActivity {

    EditText fileName;
    EditText fielContent;
    Button save;
    Button load;
    Button clear;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initial();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fileName.getText().toString();
                String content = fielContent.getText().toString();
                if(name.isEmpty())
                {
                    Toast.makeText(EditActivity.this,"File name cannot be empty",Toast.LENGTH_LONG).show();
                }
                else
                {
                    try {
                        FileOutputStream outputStream = openFileOutput(name,MODE_PRIVATE);
                        outputStream.write(content.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        Toast.makeText(EditActivity.this,"Save successfully",Toast.LENGTH_LONG).show();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fileName.getText().toString();
                try {
                    FileInputStream inputStream = openFileInput(name);
                    byte[] content = new byte[inputStream.available()];
                    StringBuilder stringBuilder = new StringBuilder("");
                    int length;
                    while((length = inputStream.read(content)) > 0)
                    {
                        stringBuilder.append(new String(content,0,length));
                    }
                    inputStream.close();
                    fielContent.setText(stringBuilder.toString());
                    Toast.makeText(EditActivity.this,"Load successfully",Toast.LENGTH_LONG).show();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditActivity.this,"Failed to load file",Toast.LENGTH_LONG).show();
                    fielContent.setText("");
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fielContent.setText("");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fileName.getText().toString();
                deleteFile(name);
                Toast.makeText(EditActivity.this,"Delete successfully",Toast.LENGTH_LONG).show();
            }
        });
    }

    void initial()
    {
        fileName = findViewById(R.id.file_name);
        fielContent = findViewById(R.id.file_content);
        save = findViewById(R.id.save);
        load = findViewById(R.id.load);
        clear = findViewById(R.id.clear_content);
        delete = findViewById(R.id.delete);
    }
}
