package com.youli.zbetuch.jingan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHengBin on 2017/8/14.
 */

public class MyFpAdapter extends FragmentPagerAdapter{

    private List<Fragment> mFragmentList;

    public MyFpAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList!=null?mFragmentList.get(position):null;
    }

    @Override
    public int getCount() {
        return mFragmentList!=null?mFragmentList.size():0;
    }
}
