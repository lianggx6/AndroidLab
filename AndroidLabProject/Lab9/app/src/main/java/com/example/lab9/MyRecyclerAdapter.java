package com.example.lab9;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>
{
    private Context mContext;
    private int mLayoutId;
    private List<GitHub> mDatas;
    private OnItemClickListener mOnItemClickListener;
    MyRecyclerAdapter(Context context,int layoutId)
    {
        mContext = context;
        mLayoutId = layoutId;
        mOnItemClickListener = null;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        holder.id.setText("id:" + mDatas.get(position).id);
        holder.blog.setText("blog:" + mDatas.get(position).blog);
        holder.name.setText(mDatas.get(position).login);
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mOnItemClickListener.onClick(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                return true;
            }
        });
    }

    interface OnItemClickListener
    {
        void onClick(int position);
        void onLongClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }

    GitHub getGithub(int pos)
    {
        return mDatas.get(pos);
    }

    void add(GitHub gitHub)
    {
        mDatas.add(gitHub);
        notifyDataSetChanged();
    }

    void remove(int position)
    {
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDatas.size() - position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        TextView blog;
        MyViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.id);
            name = view.findViewById(R.id.name);
            blog = view.findViewById(R.id.blog);
        }
    }
}