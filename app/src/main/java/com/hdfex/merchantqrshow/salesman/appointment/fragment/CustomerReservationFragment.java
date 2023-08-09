package com.hdfex.merchantqrshow.salesman.appointment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/9/8.
 * email : huangjinping@hdfex.com
 */

public class CustomerReservationFragment extends BaseFragment {
    private final String[] mTitles = {
            "未跟进", "已跟进", "已完成",
    };
    private SlidingTabLayout tab_layout_header;
    private ViewPager vip_container;
    private List<BaseFragment> fragmentList;
    private SlidingAdapter mAdapter;

    public static BaseFragment getInstance() {
        CustomerReservationFragment fragment = new CustomerReservationFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customerreservation, container, false);
        initView(view);
        fragmentList = new ArrayList<>();
        fragmentList.add(ReservationListFragment.getInstance("00"));
        fragmentList.add(ReservationListFragment.getInstance("01"));
        fragmentList.add(ReservationListFragment.getInstance("02"));
        mAdapter = new SlidingAdapter(getChildFragmentManager(), fragmentList, mTitles);
        vip_container.setAdapter(mAdapter);
        tab_layout_header.setViewPager(vip_container, mTitles);
        return view;
    }

    private void initView(View view) {
        tab_layout_header = (SlidingTabLayout) view.findViewById(R.id.tab_layout_header);
        vip_container = (ViewPager) view.findViewById(R.id.vip_container);
    }
}
