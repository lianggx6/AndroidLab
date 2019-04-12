package com.example.myAdapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.project.GlideLoader;
import com.example.project.R;

import java.util.List;

public class InfoListViewAdapter extends BaseAdapter {

    private List<String> mDatas;
    private Context mContext;
    private GlideLoader glideLoader;

    public InfoListViewAdapter(Context context,List<String> Datas)
    {
        mContext = context;
        mDatas = Datas;
        glideLoader = new GlideLoader();
        Log.i("adapter",String.valueOf(mDatas.size()));
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.info_image_item,parent,false);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.inform_image);
        glideLoader.load(mContext,imageView,mDatas.get(position));
        return convertView;
    }
}
