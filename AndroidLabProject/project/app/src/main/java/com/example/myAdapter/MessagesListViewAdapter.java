package com.example.myAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelClass.messages;
import com.example.project.GlideLoader;
import com.example.project.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MessagesListViewAdapter extends BaseAdapter {


    private List<messages> mDatas;
    private Context mContext;
    private GlideLoader glideLoader;
    public MessagesListViewAdapter (Context context,List<messages> Datas)
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
        view = LayoutInflater.from(mContext).inflate(R.layout.message_item,null);
        TextView name = (TextView) view.findViewById(R.id.name_item);
        TextView text = (TextView) view.findViewById(R.id.text_item);
        RoundedImageView avata = (RoundedImageView) view.findViewById(R.id.user_adaper_item);
        name.setText(mDatas.get(i).sender_name);
        text.setText(mDatas.get(i).content);
        glideLoader.load(mContext,avata,mDatas.get(i).sender_avatar);
        return view;
    }
}


