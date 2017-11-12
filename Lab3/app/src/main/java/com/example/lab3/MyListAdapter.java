package com.example.lab3;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

class MyListAdapter extends BaseAdapter
{
    private List<Goods> mDatas;
    private Context mContext;

    MyListAdapter(Context context,List<Goods> Datas)
    {
        this.mContext = context;
        this.mDatas = Datas;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i)
    {
        return mDatas.get(i);
    }

    @Override
    public  long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        View convertView;
        MyViewHolder mViewHolder;
        if(view == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }
        else
        {
            convertView = view;
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        if(mDatas.get(position).name.equals("购物车")) mViewHolder.letter.setText("*");
        else mViewHolder.letter.setText(mDatas.get(position).name.substring(0,1));
        mViewHolder.name.setText(mDatas.get(position).name);
        mViewHolder.price.setText(mDatas.get(position).price);
        return convertView;
    }

    private class MyViewHolder
    {
        TextView letter;
        TextView name;
        TextView price;
        MyViewHolder(View view)
        {
            letter=(TextView) view.findViewById(R.id.first_letter);
            name = (TextView) view.findViewById(R.id.good_name);
            price = (TextView) view.findViewById(R.id.good_price);
        }
    }
}
