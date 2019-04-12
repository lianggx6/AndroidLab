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

import com.example.modelClass.goods_publish_all;
import com.example.project.GlideLoader;
import com.example.project.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeListViewAdapter extends BaseAdapter {

    private List<goods_publish_all> mDatas;
    private Context mContext;
    private GlideLoader glideLoader;
    public HomeListViewAdapter(Context context,List<goods_publish_all> Datas)
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
        view = LayoutInflater.from(mContext).inflate(R.layout.latest_item,viewGroup,false);
        TextView nicheng = (TextView) view.findViewById(R.id.last_item_nicheng);
        TextView price = (TextView) view.findViewById(R.id.last_item_price);
        TextView miaoshu = (TextView) view.findViewById(R.id.latest_item_miaoshu);
        RoundedImageView roundedImageView = (RoundedImageView) view.findViewById(R.id.last_item_touxiang);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.latest_item_recyclerView);
        String[] sArray=mDatas.get(i).mygoods.image.split(";");//提取图片链接
        List<String> imagelist = new ArrayList<>();
        imagelist.addAll(Arrays.asList(sArray));
        RecyclerViewLatestAdapter recyclerViewLatestAdapter = new RecyclerViewLatestAdapter(mContext,imagelist,R.layout.recycler_image_item);
        nicheng.setText(mDatas.get(i).myseller.name);
        price.setText(String.valueOf(mDatas.get(i).mygoods.price));
        miaoshu.setText(mDatas.get(i).mygoods.information);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//recyclerView设置横向布局
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewLatestAdapter);
        glideLoader.load(mContext,roundedImageView,mDatas.get(i).myseller.avatar);
        return view;
    }
}


