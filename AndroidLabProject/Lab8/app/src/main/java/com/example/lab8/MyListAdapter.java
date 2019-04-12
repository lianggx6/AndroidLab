package com.example.lab8;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends BaseAdapter {

    private List<String[]> mDatas;
    private Context mContext;

    MyListAdapter(Context context,List<String[]> Datas)
    {
        this.mContext = context;
        this.mDatas = Datas;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.item,null);
        TextView name = view.findViewById(R.id.name);
        TextView birthday = view.findViewById(R.id.birthday);
        TextView gift = view.findViewById(R.id.gift);
        name.setText(mDatas.get(i)[0]);
        birthday.setText(mDatas.get(i)[1]);
        gift.setText(mDatas.get(i)[2]);
        return view;
    }
}
