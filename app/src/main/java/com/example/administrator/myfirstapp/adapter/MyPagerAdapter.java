package com.example.administrator.myfirstapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class MyPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<View> arrList = new ArrayList<View>();//存储类型为view

    public MyPagerAdapter(Context context) {
        super();
        this.context = context;
    }

    //添加页面
    public void addToMyadapterView(View view) {
        arrList.add(view);
    }



    @Override
    public int getCount() {
        return arrList.size();//返回页面的数量
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(arrList.get(position));//销毁超出viewpager的缓存的页面
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(arrList.get(position));
        return arrList.get(position);
    }
}
