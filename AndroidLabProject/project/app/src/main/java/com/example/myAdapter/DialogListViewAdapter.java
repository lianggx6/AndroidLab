package com.example.myAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.modelClass.messages;
import com.example.project.GlideLoader;
import com.example.project.MyApplication;
import com.example.project.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Sen7u on 2018/1/21.
 */

public class DialogListViewAdapter extends BaseAdapter {
    private List<messages> mDatas;
    private Context mContext;
    private GlideLoader glideLoader;
    public DialogListViewAdapter (Context context,List<messages> Datas)
    {
        this.mContext = context;
        this.mDatas = Datas;
        glideLoader = new GlideLoader();
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_item,null);
        if(mDatas.get(i).direction.equals("left")) {
            TextView content = (TextView) view.findViewById(R.id.msgright);
            TextView content1 = (TextView) view.findViewById(R.id.msgleft);
            RoundedImageView avat = (RoundedImageView) view.findViewById(R.id.imgleft);
            content1.setText(mDatas.get(i).content);
            content.setVisibility(view.GONE);
            glideLoader.load(mContext, avat, mDatas.get(i).sender_avatar);
        }
        else {
            TextView content = (TextView) view.findViewById(R.id.msgright);
            TextView content1 = (TextView) view.findViewById(R.id.msgleft);
            RoundedImageView avat = (RoundedImageView) view.findViewById(R.id.imgright);
            content.setText(mDatas.get(i).content);
            content1.setVisibility(view.GONE);
            glideLoader.load(mContext, avat, mDatas.get(i).sender_avatar);
        }
        return view;
    }
}
