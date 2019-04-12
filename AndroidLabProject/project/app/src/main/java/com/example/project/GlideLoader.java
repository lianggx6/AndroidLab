package com.example.project;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.lightsky.infiniteindicator.ImageLoader;

public class GlideLoader implements ImageLoader {

    public void initLoader(Context context) {

    }

    @Override
    public void load(Context context, ImageView targetView, Object res) {

        if (res instanceof String){
            Glide.with(context)
                    .load((String) res)
                    .centerCrop()
//                .placeholder(R.drawable.a)
                    .crossFade()
                    .into(targetView);
        }
    }
}