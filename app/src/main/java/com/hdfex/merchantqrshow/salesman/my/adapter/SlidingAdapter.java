package com.hdfex.merchantqrshow.salesman.my.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hdfex.merchantqrshow.base.BaseFragment;

import java.util.List;

/**
 * Created by harrishuang on 2017/2/9.
 *
 */

public class SlidingAdapter extends FragmentPagerAdapter {

    private List<BaseFragment>  fragmentList;

    private String[] titils;

    public SlidingAdapter(FragmentManager fm, List<BaseFragment> fragmentList, String[] titils) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titils = titils;
    }

    public SlidingAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titils[position];
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
