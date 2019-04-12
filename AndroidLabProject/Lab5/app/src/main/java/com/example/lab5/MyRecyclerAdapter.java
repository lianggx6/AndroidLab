package com.example.lab5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>
{
    private Context mContext;
    private int mLayoutId;
    private List<Goods> mDatas;
    private OnItemClickListener mOnItemClickListener;
    MyRecyclerAdapter(Context context,int layoutId,List<Goods> datas)
    {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
        mOnItemClickListener = null;
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
        holder.letter.setText(mDatas.get(position).name.substring(0,1));
        holder.name.setText(mDatas.get(position).name);
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

    void remove(int position)
    {
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDatas.size() - position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView letter;
        TextView name;

         MyViewHolder(View view) {
            super(view);
            letter=(TextView) view.findViewById(R.id.first_letter);
            name = (TextView) view.findViewById(R.id.good_name);
        }

    }
}