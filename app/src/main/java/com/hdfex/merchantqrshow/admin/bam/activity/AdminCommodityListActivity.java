package com.hdfex.merchantqrshow.admin.bam.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.bam.fragment.AdminCommodityItemFragment;
import com.hdfex.merchantqrshow.admin.bam.fragment.SearchCommodityFragment;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/12/1.
 * email : huangjinping@hdfex.com
 */

public class AdminCommodityListActivity extends BaseActivity implements View.OnClickListener {
    private final String[] mTitles = {
            "已上架", "已下架",
    };
    private List<BaseFragment> fragmentList;
    private SlidingTabLayout tab_layout_header;
    private SlidingAdapter adapter;
    private ImageView img_back;
    private ViewPager vip_container;
    private LinearLayout layout_search_view;
    private EditText edt_order_search;
    private Button btn_open_search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_commoditylist);
        initView();
        fragmentList = new ArrayList<>();
        fragmentList.add(AdminCommodityItemFragment.getInstance("1"));
        fragmentList.add(AdminCommodityItemFragment.getInstance("2"));
        adapter = new SlidingAdapter(getSupportFragmentManager(), fragmentList, mTitles);
        vip_container.setAdapter(adapter);
        tab_layout_header.setViewPager(vip_container, mTitles);
    }

    private void initView() {
        vip_container = (ViewPager) findViewById(R.id.vip_container);
        tab_layout_header = (SlidingTabLayout) findViewById(R.id.tab_layout_header);
        layout_search_view = (LinearLayout) findViewById(R.id.layout_search_view);
        img_back = (ImageView) findViewById(R.id.img_back);
        edt_order_search=(EditText)findViewById(R.id.edt_order_search);
        btn_open_search=(Button)findViewById(R.id.btn_open_search);
        edt_order_search.setOnClickListener(this);
        btn_open_search.setOnClickListener(this);
        img_back.setOnClickListener(this);
        layout_search_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.edt_order_search:
            case R.id.btn_open_search:
            case R.id.layout_search_view:
                showSearchView();
                break;
        }
    }

    /**
     * 搜索页面展示
     */
    private void showSearchView() {
        int currentItem = vip_container.getCurrentItem();
        SearchCommodityFragment fragment=null;
        if (currentItem==0){
            fragment =  SearchCommodityFragment.getInstance("1");
        }else {
            fragment = SearchCommodityFragment.getInstance("2");
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer,
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer);
        transaction.replace(R.id.layout_other_content, fragment).
                addToBackStack(SearchCommodityFragment.class.getSimpleName())
                .commit();

    }


    public static void startAction(Context context) {
        Intent intent = new Intent(context, AdminCommodityListActivity.class);
        context.startActivity(intent);
    }


}
