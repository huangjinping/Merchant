package com.hdfex.merchantqrshow.manager.finance.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.manager.finance.fragment.ExpenditureFragment;
import com.hdfex.merchantqrshow.manager.finance.fragment.IncomeFragment;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/5/31.
 * email : huangjinping@hdfex.com
 */

public class PaymentsActivity extends BaseActivity implements View.OnClickListener {
    private final String[] mTitles = {
            "月收入", "月支出",
    };
    private List<BaseFragment> fragmentList;
    private SlidingTabLayout tab_layout_header;
    private ViewPager vip_resoures;
    private SlidingAdapter adapter;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        initView();
        fragmentList = new ArrayList<>();
        fragmentList.add(ExpenditureFragment.getInstance());
        fragmentList.add(IncomeFragment.getInstance());
        adapter = new SlidingAdapter(getSupportFragmentManager(), fragmentList, mTitles);
        vip_resoures.setAdapter(adapter);
        tab_layout_header.setViewPager(vip_resoures, mTitles);
    }

    private void initView() {
        tab_layout_header = (SlidingTabLayout) findViewById(R.id.tab_layout_header);
        vip_resoures = (ViewPager) findViewById(R.id.vip_resoures);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, PaymentsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
