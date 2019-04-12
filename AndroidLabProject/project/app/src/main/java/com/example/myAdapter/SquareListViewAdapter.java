package com.example.myAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.modelClass.demands_publish_all;
import com.example.project.GlideLoader;
import com.example.project.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SquareListViewAdapter extends BaseAdapter {


    private List<demands_publish_all> mDatas;
    private Context mContext;
    private GlideLoader glideLoader;
    public SquareListViewAdapter(Context context, List<demands_publish_all> Datas)
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
        view = LayoutInflater.from(mContext).inflate(R.layout.square_item,null);
        TextView nicheng = (TextView) view.findViewById(R.id.squre_item_nicheng);
        TextView miaoshu = (TextView) view.findViewById(R.id.square_item_miaoshu);
        RoundedImageView roundedImageView = (RoundedImageView) view.findViewById(R.id.square_item_touxiang);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.square_item_recyclerView);
        String[] sArray=mDatas.get(i).mydemands.image.split(";");
        List<String> imagelist = new ArrayList<>();
        imagelist.addAll(Arrays.asList(sArray));
        RecyclerViewLatestAdapter recyclerViewLatestAdapter = new RecyclerViewLatestAdapter(mContext,imagelist,R.layout.recycler_image_item);
        nicheng.setText(mDatas.get(i).mydemander.name);
        miaoshu.setText(mDatas.get(i).mydemands.information);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewLatestAdapter);
        glideLoader.load(mContext,roundedImageView,mDatas.get(i).mydemander.avatar);
        return view;
    }
}


