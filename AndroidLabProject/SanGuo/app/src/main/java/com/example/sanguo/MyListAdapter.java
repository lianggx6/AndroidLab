package com.example.sanguo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends BaseAdapter{

    private List<Humans> mDatas;
    private Context mContext;

    MyListAdapter(Context context,List<Humans> Datas)
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        MyViewHolder mViewHolder;
        if(view == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.human_item,viewGroup,false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }
        else
        {
            convertView = view;
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        mViewHolder.setViewHolder(mDatas.get(i));
        return convertView;
    }

    private class MyViewHolder
    {
        TextView name;
        TextView year;
        ImageView image;
        ImageView nation;
        ImageView back;
        MyViewHolder(View view)
        {
            name = view.findViewById(R.id.item_name);
            year = view.findViewById(R.id.item_year);
            image = view.findViewById(R.id.item_image);
            nation = view.findViewById(R.id.item_nation);
            back = view.findViewById(R.id.item_back);
        }

        void setViewHolder(Humans humans)
        {
            name.setText(humans.name);
            year.setText(humans.years);
            image.setImageResource(humans.imageId);
            switch (humans.nation) {
                case "魏":
                    back.setImageResource(R.drawable.weiback);
                    nation.setImageResource(R.drawable.wei);
                    break;
                case "蜀":
                    back.setImageResource(R.drawable.shuback);
                    nation.setImageResource(R.drawable.shu);
                    break;
                case "吴":
                    back.setImageResource(R.drawable.wuback);
                    nation.setImageResource(R.drawable.wu);
                    break;
                default:
                    back.setImageResource(R.drawable.qunback);
                    nation.setImageResource(R.drawable.qun);
                    break;
            }
        }
    }
}
