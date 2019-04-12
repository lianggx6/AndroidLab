package com.example.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import com.example.project.R;
import static android.view.Gravity.START;


public class home extends Fragment implements ViewPager.OnPageChangeListener,OnPageClickListener
{

    private ArrayList<Page> pageViews;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home,container,false);
        initData();
        InfiniteIndicator mAnimCircleIndicator = (InfiniteIndicator) view.findViewById(R.id.indicator_default_circle);
        IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                .imageLoader(new GlideLoader())
                .isStopWhileTouch(true)
                .onPageChangeListener(this)
                .onPageClickListener(this)
                .direction(START)
                .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                .build();
        mAnimCircleIndicator.init(configuration);
        mAnimCircleIndicator.notifyDataChange(pageViews);
        return view;
    }

    private void initData() {
        pageViews = new ArrayList<>();
        pageViews.add(new Page("A", "https://img.alicdn.com/bao/uploaded/i1/0/TB22I8Gd4PI8KJjSspfXXcCFXXa_!!0-rate.jpg_400x400.jpg",this));
        pageViews.add(new Page("B", "https://img.alicdn.com/bao/uploaded/i3/0/TB2iw0Ad4rI8KJjy0FpXXb5hVXa_!!0-rate.jpg_400x400.jpg",this));
        pageViews.add(new Page("C", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg",this));
        pageViews.add(new Page("D", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg",this));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageClick(int position, Page page) {

    }
}
