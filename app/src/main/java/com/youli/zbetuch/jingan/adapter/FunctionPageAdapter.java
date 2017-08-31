package com.youli.zbetuch.jingan.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liutao on 2017/8/8.
 */

public class FunctionPageAdapter extends PagerAdapter{

    private List<View> data;

    public FunctionPageAdapter(List<View> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(data.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(data.get(position));

        return data.get(position);
    }
}
