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
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpItem;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 追踪订单
 * author Created by harrishuang on 2017/9/8.
 * email : huangjinping@hdfex.com
 */

public class FollowOrderFragment extends BaseFragment {


    private final String[] mTitles = {
            "未跟进", "已跟进"
    };
    private SlidingTabLayout tab_layout_header;
    private ViewPager vip_container;
    private List<BaseFragment> fragmentList;
    private SlidingAdapter mAdapter;

    public static FollowOrderFragment getInstance() {
        FollowOrderFragment fragment = new FollowOrderFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followorder, container, false);
        initView(view);
        fragmentList = new ArrayList<>();
        fragmentList.add(FollowUnListFragment.getInstance(FollowUpItem.UN_FOLLOW));
        fragmentList.add(FollowListFragment.getInstance(FollowUpItem.FOLLOWED));
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
