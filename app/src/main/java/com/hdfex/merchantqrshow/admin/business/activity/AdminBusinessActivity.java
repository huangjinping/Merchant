package com.hdfex.merchantqrshow.admin.business.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.business.fragment.AdminBusinessFragment;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/12/19.
 * email : huangjinping@hdfex.com
 */

public class AdminBusinessActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private ImageView img_add;
    private SegmentTabLayout layout_segment;
    private ViewPager vip_container;
    private SlidingAdapter adapter;
    private List<BaseFragment> fragmentList;
    private final String[] mTitles = {
            "未提交", "准入中", "已准入"
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminbusiness);
        initView();
        fragmentList = new ArrayList<>();
        fragmentList.add(AdminBusinessFragment.getInstance("0"));
        fragmentList.add(AdminBusinessFragment.getInstance("1"));
        fragmentList.add(AdminBusinessFragment.getInstance("2"));
        adapter = new SlidingAdapter(getSupportFragmentManager(), fragmentList, mTitles);
        vip_container.setAdapter(adapter);
        layout_segment.setTabData(mTitles);
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
                vip_container.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        layout_segment.setCurrentTab(0);
    }




    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_add = (ImageView) findViewById(R.id.img_add);
        layout_segment = (SegmentTabLayout) findViewById(R.id.layout_segment);
        vip_container = (ViewPager) findViewById(R.id.vip_container);
        img_back.setOnClickListener(this);
        img_add.setOnClickListener(this);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AdminBusinessActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_add:
                ExpandBusinessActivity.startAction(this);
                break;
        }

    }
}
