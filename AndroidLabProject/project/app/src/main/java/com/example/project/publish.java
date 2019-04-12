package com.example.project;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import com.example.project.R;

public class publish extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.publish,container,false);
        LinearLayout left = (LinearLayout) view.findViewById(R.id.publish_woyou);
        LinearLayout right = (LinearLayout) view.findViewById(R.id.publish_qiugou);
        Animation animationLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        Animation animationRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        animationLeft.setDuration(800);
        animationRight.setDuration(800);
        animationRight.setStartOffset(500);
        left.startAnimation(animationLeft);
        right.startAnimation(animationRight);
        return view;
    }
}
