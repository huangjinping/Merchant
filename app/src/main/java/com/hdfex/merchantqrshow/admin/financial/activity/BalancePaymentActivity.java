package com.hdfex.merchantqrshow.admin.financial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.financial.adapter.BalanceItemAdapter;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageListResponse;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageResult;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2017/12/12.
 * email : huangjinping@hdfex.com
 */

public class BalancePaymentActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private MultiStateView multiStateView;
    private RecyclerView rec_balance_list;
    private SmartRefreshLayout xr_freshview;
    private TextView tv_home;
    private User user;
    private List<RedPackage> dataList;
    private BalanceItemAdapter mAdapter;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, BalancePaymentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balancepayment);
        initView();
        user = UserManager.getUser(this);
        dataList = new ArrayList<>();

        mAdapter = new BalanceItemAdapter(dataList);
        rec_balance_list.setLayoutManager(new LinearLayoutManager(this));
        rec_balance_list.setAdapter(mAdapter);


        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);


        xr_freshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadRedPackageList(LoadMode.PULL_REFRSH);
            }
        });
        xr_freshview.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadRedPackageList(LoadMode.UP_REFRESH);
            }
        });
        loadRedPackageList(LoadMode.NOMAL);

    }


    private int page = 0;

    public void loadRedPackageList(final LoadMode loadMode) {

        if (loadMode != LoadMode.UP_REFRESH) {
            page = 0;
        }
        OkHttpUtils
                .post()
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("pageIndex", page + "")
                .addParams("pageSize", "15")
                .tag(this)
                .url(NetConst.RED_PACKAGE_LIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        if (loadMode != LoadMode.UP_REFRESH) {
                            dataList.clear();
                        }
                        try {
                            if (checkResonse(response)) {

                                RedPackageListResponse house = GsonUtil.changeGsonToBean(response, RedPackageListResponse.class);
                                if (house.getResult() != null) {
                                    RedPackageResult result = house.getResult();
                                    List<RedPackage> redPacketList = result.getRedPacketList();
                                    dataList.addAll(redPacketList);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        mAdapter.notifyDataSetChanged();
                        dismissProgress();

                    }
                });
    }


    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        multiStateView = (MultiStateView) findViewById(R.id.multiStateView);
        rec_balance_list = (RecyclerView) findViewById(R.id.rec_balance_list);
        xr_freshview = (SmartRefreshLayout) findViewById(R.id.xr_freshview);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tb_tv_titile.setText("收支明细");
        img_back.setOnClickListener(this);
        tv_home.setOnClickListener(this);
        tv_home.setText("筛选");

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
