package com.example.lab7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText newPassword;
    EditText confirmPassword;
    EditText passeord;
    Button ok;
    Button clear;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        if(sharedPreferences.contains("password"))
        {
            newPassword.setVisibility(View.GONE);
            confirmPassword.setVisibility(View.GONE);
            passeord.setVisibility(View.VISIBLE);
        }
        else
        {
            newPassword.setVisibility(View.VISIBLE);
            confirmPassword.setVisibility(View.VISIBLE);
            passeord.setVisibility(View.GONE);
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {
                if(sharedPreferences.contains("password"))
                {
                    String store = sharedPreferences.getString("password","");
                    String input = passeord.getText().toString();
                    if(input.isEmpty())
                    {
                        Toast.makeText(MainActivity.this,"Password cannot be empty",Toast.LENGTH_LONG).show();
                    }
                    else  if(store.equals(input))
                    {
                        Intent intent = new Intent(MainActivity.this,EditActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else Toast.makeText(MainActivity.this,"Password Mismatch",Toast.LENGTH_LONG).show();
                }
                else
                {
                    String newInput = newPassword.getText().toString();
                    String confirmInput = confirmPassword.getText().toString();
                    if(newInput.isEmpty() || confirmInput.isEmpty())
                    {
                        Toast.makeText(MainActivity.this,"Password cannot be empty",Toast.LENGTH_LONG).show();
                    }
                    else if(newInput.equals(confirmInput))
                    {
                        sharedPreferences.edit().putString("password",newInput).apply();
                        Toast.makeText(MainActivity.this,"Save successfully",Toast.LENGTH_LONG).show();
                        newPassword.setVisibility(View.GONE);
                        confirmPassword.setVisibility(View.GONE);
                        passeord.setVisibility(View.VISIBLE);
                    }
                    else Toast.makeText(MainActivity.this,"Password Mismatch",Toast.LENGTH_LONG).show();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passeord.setText(null);
                newPassword.setText(null);
                confirmPassword.setText(null);
            }
        });
    }

    void initial()
    {
        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_pass_word);
        passeord = findViewById(R.id.password);
        ok = findViewById(R.id.ok);
        clear = findViewById(R.id.clear_password);
        sharedPreferences = getSharedPreferences("Passwords", MODE_PRIVATE);
    }

}
