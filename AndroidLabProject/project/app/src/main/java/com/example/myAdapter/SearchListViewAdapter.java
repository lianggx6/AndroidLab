package com.example.myAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.modelClass.goods_publish_all;
import com.example.project.GlideLoader;
import com.example.project.R;

import java.util.List;

public class SearchListViewAdapter extends BaseAdapter {

    private List<goods_publish_all> mDatas;
    private Context mContext;
    private GlideLoader glideLoader;
    public SearchListViewAdapter(Context context,List<goods_publish_all> Datas)
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
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.search_item,parent,false);
        ImageView image = (ImageView) convertView.findViewById(R.id.search_item_image);
        TextView info = (TextView) convertView.findViewById(R.id.search_item_inform);
        TextView price = (TextView) convertView.findViewById(R.id.search_item_rmb);
        info.setText(mDatas.get(position).mygoods.information);
        price.setText("RMB:" + mDatas.get(position).mygoods.price);
        String[] sArray=mDatas.get(position).mygoods.image.split(";");
        glideLoader.load(mContext,image,sArray[0]);
        return convertView;
    }
}
