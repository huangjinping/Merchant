package com.hdfex.merchantqrshow.admin.business.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.business.adapter.AdminBusinessAdapter;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.view.MyItemClickListener;
import com.hdfex.merchantqrshow.widget.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/12/26.
 * email : huangjinping@hdfex.com
 */

public class AdminBusinessFragment extends BaseFragment {

    private MultiStateView multiStateView;
    private RecyclerView rec_admin_business;
    private SmartRefreshLayout xr_freshview;
    private List<String> dataList;
    private AdminBusinessAdapter mAdapter;
    public String type;

    public static BaseFragment getInstance(String type){
        AdminBusinessFragment  fragment=new AdminBusinessFragment();
        fragment.type=type;
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_business, container, false);
        initView(view);
        dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("");
        }

        mAdapter = new AdminBusinessAdapter(dataList);
        rec_admin_business.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec_admin_business.setAdapter(mAdapter);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mAdapter.setItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

            }
        });

        xr_freshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                loadData(LoadMode.PULL_REFRSH);
            }
        });
        xr_freshview.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                loadData(LoadMode.UP_REFRESH);
            }
        });
        return view;
    }

    private void initView(View view) {
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
        rec_admin_business = (RecyclerView) view.findViewById(R.id.rec_admin_business);
        xr_freshview = (SmartRefreshLayout) view.findViewById(R.id.xr_freshview);
    }
}
