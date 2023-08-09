package com.hdfex.merchantqrshow.admin.business.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.business.fragment.AdminBusinessFragment;
import com.hdfex.merchantqrshow.admin.business.fragment.AdminOrderListFragment;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/12/26.
 * email : huangjinping@hdfex.com
 */

public class AdminOrderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private SegmentTabLayout layout_segment;
    private ViewPager vip_container;
    private List<BaseFragment> fragmentList;
    private final String[] mTitles = {
            "已完成", "未完成"
    };

    private SlidingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminorder);
        initView();
        fragmentList = new ArrayList<>();
        fragmentList.add(AdminOrderListFragment.getInstance("01"));
        fragmentList.add(AdminOrderListFragment.getInstance("00"));
        layout_segment.setTabData(mTitles);
        adapter=new SlidingAdapter(getSupportFragmentManager(),fragmentList,mTitles);
        vip_container.setAdapter(adapter);
        layout_segment.setCurrentTab(0);
        layout_segment.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vip_container.setCurrentItem(position);

            }
            @Override
            public void onTabReselect(int position) {
            }
        });
        vip_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                layout_segment.setCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        layout_segment = (SegmentTabLayout) findViewById(R.id.layout_segment);
        vip_container = (ViewPager) findViewById(R.id.vip_container);
        img_back.setOnClickListener(this);
    }

    public static void startAction(Context context) {
        Intent intent=new Intent(context,AdminOrderActivity.class);
        context.startActivity(intent);
    }
}
