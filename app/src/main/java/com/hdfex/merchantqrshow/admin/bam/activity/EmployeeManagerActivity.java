package com.hdfex.merchantqrshow.admin.bam.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.manager.team.RegionResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.manager.team.adapter.PersonnelAdapter;
import com.hdfex.merchantqrshow.mvp.contract.PersonnelManagerContract;
import com.hdfex.merchantqrshow.mvp.presenter.EmployeeManagerManagerPresenter;
import com.hdfex.merchantqrshow.mvp.presenter.PersonnelManagerPresenter;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.MultiStateView;
import com.hdfex.sdk.pullrefreshlayout.BGANormalRefreshViewHolder;
import com.hdfex.sdk.pullrefreshlayout.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class EmployeeManagerActivity extends BaseActivity implements View.OnClickListener, PersonnelManagerContract.View {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private ExpandableListView exp_manager;
    private PersonnelAdapter adapter;
    private List<RegionResult> dataList;
    private PersonnelManagerContract.Presenter presenter;
    private BGARefreshLayout layout_refresh;
    private MultiStateView multiStateView;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnelmanager);
        initView();
        presenter = new EmployeeManagerManagerPresenter();
        presenter.attachView(this);
        user = UserManager.getUser(this);
        presenter.initData(this, user);
        dataList = new ArrayList<>();
        adapter = new PersonnelAdapter(this, dataList);
        exp_manager.setAdapter(adapter);
        presenter.getRegionList(user, LoadMode.NOMAL);
        BGANormalRefreshViewHolder holder = new BGANormalRefreshViewHolder(this, true);
        layout_refresh.setRefreshViewHolder(holder);
        layout_refresh.setIsShowLoadingMoreView(true);
        layout_refresh.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                presenter.getRegionList(user, LoadMode.PULL_REFRSH);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

                presenter.getRegionList(user, LoadMode.UP_REFRESH);

                return false;
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
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        exp_manager = (ExpandableListView) findViewById(R.id.exp_manager);
        exp_manager.setGroupIndicator(null);
        tb_tv_titile.setText("人员管理");
        layout_refresh = (BGARefreshLayout) findViewById(R.id.layout_refresh);
        multiStateView = (MultiStateView) findViewById(R.id.multiStateView);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, EmployeeManagerActivity.class);
        context.startActivity(intent);

    }


    public void onEmpty() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.getRegionList(user, LoadMode.NOMAL);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void returnListResponse(List<RegionResult> result, LoadMode loadMode) {
        if (loadMode != LoadMode.UP_REFRESH) {
            dataList.clear();
        }
        dataList.addAll(result);
        if (dataList.size() == 0) {
            onEmpty();
        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void returnRefresh() {
        layout_refresh.endLoadingMore();
        layout_refresh.endRefreshing();
    }
}
