package com.example.lab2;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView mImage;
    AlertDialog.Builder mBuild;
    RadioButton studentButton;
    RadioButton teacherButton;
    RadioButton theChoose;
    Button login;
    Button registID;
    RadioGroup mRadioGroup;
    TextInputLayout idTextInputLayout;
    TextInputLayout wordTextInputLayout;
    EditText idEditText;
    EditText wordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();

        mImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mBuild.setTitle("上传头像");
                mBuild.setItems(new String[] {"拍摄","从相册选择"}, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(which == 0)
                            Toast.makeText(MainActivity.this,"您选择了[拍摄]",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this,"您选择了[从相册选择]",Toast.LENGTH_SHORT).show();
                    }
                });
                mBuild.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(MainActivity.this,"您选择了[取消]",Toast.LENGTH_SHORT).show();
                    }
                });
                mBuild.show();
            }
        });

        idEditText.setOnClickListener(new View.OnClickListener()
            {
                @Override
            public void onClick(View view)
            {
                idEditText.setFocusable(true);
                idEditText.setFocusableInTouchMode(true);
                idEditText.requestFocus();
                idEditText.findFocus();
            }
        });

        wordEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                wordEditText.setFocusable(true);
                wordEditText.setFocusableInTouchMode(true);
                wordEditText.requestFocus();
                wordEditText.findFocus();
            }
        });


        studentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view,"您选择了学生",Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                Toast.makeText(MainActivity.this,"Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(Color.BLUE)
                        .show();
            }
        });

        teacherButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view,"您选择了教职工",Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                Toast.makeText(MainActivity.this,"Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(Color.BLUE)
                        .show();
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                theChoose = (RadioButton)findViewById(mRadioGroup.getCheckedRadioButtonId());
            }
        });

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String netid = idEditText.getText().toString();
                String passord = wordEditText.getText().toString();
                if(netid.isEmpty())
                {
                    idTextInputLayout.setError("学号不能为空");
                    wordTextInputLayout.setError(null);
                }
                else
                {
                    idTextInputLayout.setError(null);
                    if(passord.isEmpty()) wordTextInputLayout.setError("密码不能为空");
                    else
                    {
                        wordTextInputLayout.setError(null);
                        if(netid.equals("123456") && passord.equals("6666"))
                        {
                            Snackbar.make(view,"登录成功",Snackbar.LENGTH_LONG)
                                    .setAction("确定", new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View view) {}
                                    })
                                    .setActionTextColor(Color.BLUE)
                                    .show();
                        }
                        else
                        {
                            Snackbar.make(view,"学号或密码错误",Snackbar.LENGTH_LONG)
                                    .setAction("确定", new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View view) {}
                                    })
                                    .setActionTextColor(Color.BLUE)
                                    .show();
                        }
                    }
                }
            }
        });


        registID.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(studentButton.getId() == theChoose.getId())
                {
                    Snackbar.make(view,"学生注册功能尚未启用",Snackbar.LENGTH_LONG)
                            .setAction("确定", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view) {}
                            })
                            .setActionTextColor(Color.BLUE)
                            .show();
                }
                else
                {
                    Snackbar.make(view,"教职工注册功能尚未启用",Snackbar.LENGTH_LONG)
                            .setAction("确定", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view) {}
                            })
                            .setActionTextColor(Color.BLUE)
                            .show();
                }
            }
        });



    }



    void initial()
    {
        mImage = (ImageView) findViewById(R.id.imageView);
        mBuild = new AlertDialog.Builder(MainActivity.this);
        studentButton = (RadioButton) findViewById(R.id.student);
        teacherButton = (RadioButton) findViewById(R.id.teacher);
        login = (Button) findViewById(R.id.login);
        registID = (Button) findViewById(R.id.registID);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        idTextInputLayout = (TextInputLayout) findViewById(R.id.studentID);
        wordTextInputLayout = (TextInputLayout) findViewById(R.id.password);
        idEditText = (EditText) findViewById(R.id.idEdit);
        wordEditText = (EditText) findViewById(R.id.passwordEdit);
        theChoose = (RadioButton)findViewById(mRadioGroup.getCheckedRadioButtonId());
    }
}
