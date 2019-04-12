package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.GlideLoader;
import com.example.project.LoginActivity;
import com.example.project.MyApplication;
import com.example.project.OrderActivity;
import com.example.project.R;
import com.example.project.SettingActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class me extends Fragment {

    LinearLayout linearLayout;
    RoundedImageView adaper;
    TextView username;
    GlideLoader glideLoader;
    MyApplication myApplication;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(myApplication.getState() == 1) linearLayout.setVisibility(View.GONE);
        else
        {
            linearLayout.setVisibility(View.VISIBLE);
            username.setText(myApplication.user_name);
            glideLoader.load(getContext(),adaper,myApplication.user_avatar);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.me,container,false);
        ConstraintLayout setting = (ConstraintLayout) view.findViewById(R.id.me_shezhi);
        ConstraintLayout fabu = (ConstraintLayout) view.findViewById(R.id.wofabude);
        ConstraintLayout shoucang = (ConstraintLayout) view.findViewById(R.id.woshoucangde);
        Button login = (Button) view.findViewById(R.id.login);
        linearLayout = (LinearLayout) view.findViewById(R.id.head1);
        adaper = (RoundedImageView) view.findViewById(R.id.user_adaper);
        username = (TextView) view.findViewById(R.id.user_name);
        glideLoader = new GlideLoader();
        myApplication = ((MyApplication) getActivity().getApplication());
        EventBus.getDefault().register(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.getState() == 1) {
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getContext(), SettingActivity.class);
                    startActivity(intent);
                }
            }
        });
        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.getState() == 1) {
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getContext(), OrderActivity.class);
                    intent.putExtra("title",0);
                    startActivity(intent);
                }

            }
        });
        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.getState() == 1) {
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getContext(), OrderActivity.class);
                    intent.putExtra("title",1);
                    startActivity(intent);
                }

            }
        });
        return view;
    }

    @Subscribe
    public void onEventMainThread(MyApplication myApplication) {
        if(myApplication.getState() == 1) linearLayout.setVisibility(View.GONE);
        else
        {
            linearLayout.setVisibility(View.VISIBLE);
            username.setText(myApplication.user_name);
            glideLoader.load(getContext(),adaper,myApplication.user_avatar);
        }
    }
}
