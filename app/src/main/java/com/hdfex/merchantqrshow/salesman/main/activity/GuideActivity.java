package com.hdfex.merchantqrshow.salesman.main.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.salesman.main.fragment.GuideFragment;

import java.util.ArrayList;

/**
 * 引导页
 * Created by harrishuang on 16/9/28.
 */

public class GuideActivity extends BaseActivity {

    private ViewPager vip_guide;
    private ArrayList<Fragment> fragmentsList;
    private OrderPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        fragmentsList = new ArrayList<>();
        fragmentsList.add(GuideFragment.getInstance(0));
        fragmentsList.add(GuideFragment.getInstance(1));
        fragmentsList.add(GuideFragment.getInstance(2));
        adapter = new OrderPagerAdapter(getSupportFragmentManager());
        vip_guide.setAdapter(adapter);

    }


    private void initView() {
        vip_guide = (ViewPager) findViewById(R.id.vip_guide);
    }

    /**
     * 引导页适配器
     */
    private class OrderPagerAdapter extends FragmentPagerAdapter {
        public OrderPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return position + "";
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentsList.get(position);
        }
    }

}
