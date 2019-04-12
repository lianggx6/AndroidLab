package com.example.myAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.modelClass.my_collect;
import com.example.modelClass.my_publish;
import com.example.project.GlideLoader;
import com.example.project.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class PublishOrderAdapter extends BaseAdapter {


    private List<my_publish> mDatas;
    private Context mContext;
    private GlideLoader glideLoader_avatar,glideLoader_img;
    public PublishOrderAdapter(Context context, List<my_publish> Datas)
    {
        this.mContext = context;
        this.mDatas = Datas;
        glideLoader_avatar = new GlideLoader();
        glideLoader_img = new GlideLoader();
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
        view = LayoutInflater.from(mContext).inflate(R.layout.order_item,null);
        TextView nicheng = (TextView) view.findViewById(R.id.order_person_name);
        TextView detail = (TextView) view.findViewById(R.id.order_content);
        RoundedImageView roundedImageView_avatar = (RoundedImageView) view.findViewById(R.id.order_person_avatar);
        RoundedImageView roundedImageView_img = (RoundedImageView) view.findViewById(R.id.order_img);
        TextView price = (TextView) view.findViewById(R.id.order_price);


        nicheng.setText(mDatas.get(i).announcer_name);
        detail.setText(mDatas.get(i).publish_information);
        String[] sArray=mDatas.get(i).publish_image.split(";");
        glideLoader_avatar.load(mContext,roundedImageView_avatar,mDatas.get(i).announcer_avatar);
        glideLoader_img.load(mContext,roundedImageView_img,sArray[0]);
        if (mDatas.get(i).judge_id == 0){//商品
            price.setText(String.valueOf(mDatas.get(i).publish_price));
        }
        else{
            price.setText("求购");
        }
        return view;
    }
}


