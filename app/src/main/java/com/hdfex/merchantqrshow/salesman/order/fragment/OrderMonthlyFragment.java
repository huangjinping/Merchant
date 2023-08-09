package com.hdfex.merchantqrshow.salesman.order.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2018/7/26.
 * email : huangjinping@hdfex.com
 */
public class OrderMonthlyFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager vip_order;
    private SlidingTabLayout tab_layout_header;
    private List<BaseFragment> fragmentList;
    private OrderPagerAdapter mAdapter;
    private final String[] mTitles = {
            "未生效", "已生效", "已失效",
    };
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private TextView txt_left_name;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_monthly, container, false);

        initView(view);
        fragmentList = new ArrayList<>();
        fragmentList.add(OrderMonthlyListFragment.getInstance("00"));
        fragmentList.add(OrderMonthlyListFragment.getInstance("01"));
        fragmentList.add(OrderMonthlyListFragment.getInstance("02"));
        mAdapter = new OrderPagerAdapter(getChildFragmentManager());
        vip_order.setAdapter(mAdapter);
        tab_layout_header.setViewPager(vip_order, mTitles);
//        tab_layout_header.showDot(0);
        Bundle arguments = getArguments();
        if (arguments != null) {
            int index = arguments.getInt("index", 0);
            setCurrent(index);
            img_back.setVisibility(View.VISIBLE);
            tb_tv_titile.setTextColor(getResources().getColor(R.color.white));
            layout_toolbar.setBackgroundResource(R.color.blue_light);
        } else {
            img_back.setVisibility(View.GONE);
        }
        return view;
    }

    private void initView(View view) {
        vip_order = (ViewPager) view.findViewById(R.id.vip_order);
        tab_layout_header = (SlidingTabLayout) view.findViewById(R.id.tab_layout_header);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) view.findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) view.findViewById(R.id.iv_setting);
        tv_home = (TextView) view.findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) view.findViewById(R.id.layout_toolbar);
        tb_tv_titile.setText("订单");
        tb_tv_titile.setTextColor(getResources().getColor(R.color.black));
        layout_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        txt_left_name = (TextView) view.findViewById(R.id.txt_left_name);

    }

    /**
     * 设置当前东西
     *
     * @param index
     */
    public void setCurrent(int index) {
        if (tab_layout_header != null && vip_order != null) {
            tab_layout_header.setCurrentTab(index);
            vip_order.setCurrentItem(index);
        }
    }

    /**
     * 处理适配器
     */
    private class OrderPagerAdapter extends FragmentPagerAdapter {

        public OrderPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
        }
    }
}
