package com.hdfex.merchantqrshow.salesman.add.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrishuang on 2017/2/9.
 * <p>
 * 房源碎片列表
 */

public class ResourcesPayAllFragment extends BaseFragment {


    private final String[] mTitles = {
            "需清退", "已出租 ", "闲置"
    };
    private List<BaseFragment> fragmentList;
    private SlidingAdapter adapter;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private User user;
    private SlidingTabLayout tab_layout_header;
    private ViewPager vip_resoures;

    public static Fragment getInstance() {
        ResourcesPayAllFragment fragment = new ResourcesPayAllFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_resources, container, false);
        fragmentList = new ArrayList<>();
        initView(view);
        showZufang();
        return view;
    }


    /**
     * 分期租房
     */
    private void showZufang() {
        fragmentList.add(ResourcesPayFragment.getInstance("0"));
        fragmentList.add(ResourcesPayFragment.getInstance("2"));
        fragmentList.add(ResourcesPayFragment.getInstance("1"));
        adapter = new SlidingAdapter(getChildFragmentManager(), fragmentList, mTitles);
        vip_resoures.setAdapter(adapter);
        tab_layout_header.setViewPager(vip_resoures, mTitles);
    }

    private void initView(View view) {
        tab_layout_header = (SlidingTabLayout) view.findViewById(R.id.tab_layout_header);
        vip_resoures = (ViewPager) view.findViewById(R.id.vip_resoures);
    }

}
