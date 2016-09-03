package com.junjianliu.library.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junjianliu
 * on 16/9/1
 * email:spyhanfeng@qq.com
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views = new ArrayList<>();

    @Override
    public int getCount() {
        return views.size();
    }

    public void addView(View view){
        this.views.add(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }
}
