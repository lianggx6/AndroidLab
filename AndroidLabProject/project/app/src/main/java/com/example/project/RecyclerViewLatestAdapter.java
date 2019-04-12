package com.example.project;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewLatestAdapter extends RecyclerView.Adapter<RecyclerViewLatestAdapter.MyViewHolder>{
    private Context mContext;
    private int mLayoutId;
    private List<String> mDatas;
    RecyclerViewLatestAdapter(Context context,int layoutId)
    {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = new ArrayList<>();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        MyViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image_item_imageview);
        }
    }
}
