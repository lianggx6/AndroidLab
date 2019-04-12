package com.example.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project.LoginActivity;
import com.example.project.MainActivity;
import com.example.project.MyApplication;
import com.example.project.PublishActivity;
import com.example.project.R;
import com.example.project.SettingActivity;

public class publish extends Fragment {
    MyApplication myApplication;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.publish,container,false);
        myApplication = (MyApplication) getActivity().getApplication();
        LinearLayout left = (LinearLayout) view.findViewById(R.id.publish_woyou);
        LinearLayout right = (LinearLayout) view.findViewById(R.id.publish_qiugou);
        Animation animationLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        Animation animationRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        animationLeft.setDuration(600);
        animationRight.setDuration(600);
        animationRight.setStartOffset(300);
        left.startAnimation(animationLeft);
        right.startAnimation(animationRight);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.getState() == 1) {
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getContext(), PublishActivity.class);
                    intent.putExtra("publishtype",true);
                    startActivity(intent);
                }

            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.getState() == 1) {
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getContext(), PublishActivity.class);
                    intent.putExtra("publishtype",false);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}
