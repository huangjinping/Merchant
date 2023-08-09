package com.hdfex.merchantqrshow.manager.business.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessOrder;
import com.hdfex.merchantqrshow.bean.manager.business.RegionForOrder;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.manager.business.adapter.ListOrderAdapter;
import com.hdfex.merchantqrshow.mvp.contract.ManagerOrderContract;
import com.hdfex.merchantqrshow.mvp.presenter.ManagerOrderPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.xlistView.XListView;
import com.hdfex.merchantqrshow.widget.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * author Created by harrishuang on 2017/5/27.
 * email : huangjinping@hdfex.com
 */

public class ManagerOrderActivity extends BaseActivity implements ManagerOrderContract.View, View.OnClickListener {
    private List<BusinessOrder> dataList;
    private ManagerOrderContract.Presenter presenter;
    private User user;
    private String status;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private MultiStateView multiStateView;
    private XListView lisv_order_list;
    private ListOrderAdapter listOrderAdapter;
    private ImageView iv_setting;
    private String region;
    private TextView txt_regionName;
    private TextView txt_order_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managerorder);
        initView();
        user = UserManager.getUser(this);
        status = getIntent().getStringExtra("status");
        if (BusinessOrder.STATUS_LOANED.equals(status)) {
            tb_tv_titile.setText("已打回订单");
        } else if (BusinessOrder.STATUS_CANCEL.equals(status)) {
            tb_tv_titile.setText("已取消订单");
        } else {
            tb_tv_titile.setText("已拒绝订单");
        }
        presenter = new ManagerOrderPresenter();
        presenter.attachView(this);
        presenter.loadOrderList(user, this.status, LoadMode.NOMAL, region);
        presenter.loadRegionForOrderList(user, status);
        dataList = new ArrayList<>();
        listOrderAdapter = new ListOrderAdapter(this, dataList);
        lisv_order_list.setAdapter(listOrderAdapter);
        lisv_order_list.setPullLoadEnable(false);
        lisv_order_list.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                presenter.loadOrderList(user, ManagerOrderActivity.this.status, LoadMode.PULL_REFRSH, region);
            }

            @Override
            public void onLoadMore() {
                presenter.loadOrderList(user, ManagerOrderActivity.this.status, LoadMode.UP_REFRESH, region);
            }
        });
        EventRxBus.getInstance().register(IntentFlag.REGION).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {

                RegionForOrder order = (RegionForOrder) o;
                region = order.getRegion();
                presenter.loadOrderList(user, status, LoadMode.NOMAL, region);
                txt_regionName.setText(order.getRegionName() + "(" + order.getCustCount() + ")人");
                txt_order_counter.setText("订单数量" + order.getOrderCount());
            }
        });
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setOnClickListener(this);
        multiStateView = (MultiStateView) findViewById(R.id.multiStateView);
        multiStateView.setOnClickListener(this);


        lisv_order_list = (XListView) findViewById(R.id.lisv_order_list);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_setting.setImageResource(R.mipmap.ic_menu);
        iv_setting.setVisibility(View.VISIBLE);
        iv_setting.setOnClickListener(this);
        txt_regionName = (TextView) findViewById(R.id.txt_regionName);
        txt_order_counter = (TextView) findViewById(R.id.txt_order_counter);
    }

    public static void startAction(Context context, String status) {
        Intent intent = new Intent(context, ManagerOrderActivity.class);
        intent.putExtra("status", status);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        EventRxBus.getInstance().unregister(IntentFlag.REGION);
    }

    @Override
    public void returnOrderResponse(List<BusinessOrder> result, LoadMode loadMode) {
        if (loadMode != LoadMode.UP_REFRESH) {
            dataList.clear();
        }
        dataList.addAll(result);
        if (dataList.size() == 0) {
            onEmpty();
        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
        if (dataList.size() >= 10) {
            lisv_order_list.setPullLoadEnable(true);
        } else {
            lisv_order_list.setPullLoadEnable(false);
        }
        listOrderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAfter() {
        onLoad();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.iv_setting:
                presenter.reLoadRegionForOrderList(user, status);

                break;
        }
    }

    public void onEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        });
    }

    public void onErrr() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        });
    }

    /**
     * 更新数据
     */
    private void onLoad() {
        if (this == null) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    lisv_order_list.stopRefresh();
                    lisv_order_list.stopLoadMore();
                    lisv_order_list.setRefreshTime("刚刚");
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });
    }

    @Override
    public void returnRegionForOrder(List<RegionForOrder> result) {
        if (result != null) {
            RegionForOrder order = new RegionForOrder();
            order.setRegionName(user.getBussinessName());
            int custCount = 0;
            int orderCount = 0;
            for (RegionForOrder region : result) {
                custCount += region.getCustCount();
                orderCount += region.getOrderCount();
            }
            order.setCustCount(custCount);
            order.setOrderCount(orderCount);
            txt_regionName.setText(order.getRegionName() + "(" + order.getCustCount() + ")人");
            txt_order_counter.setText("订单数量" + order.getOrderCount());
        }


    }

    @Override
    public void returnReloadRegionForOrder(List<RegionForOrder> result) {

        if (result != null) {
            List<RegionForOrder> dataList = new ArrayList<>();
            dataList.addAll(result);
            RegionForOrder order = new RegionForOrder();
            order.setRegionName(user.getBussinessName());
            int custCount = 0;
            int orderCount = 0;
            for (RegionForOrder region : dataList) {
                custCount += region.getCustCount();
                orderCount += region.getOrderCount();
            }
            order.setCustCount(custCount);
            order.setOrderCount(orderCount);
            dataList.add(0, order);
            presenter.openMenu(dataList, R.id.layout_fragment_content, getSupportFragmentManager());
        }
        Log.d("okhttp", "======>>returnReloadRegionForOrder>>>>>>>>>");
//        layout_drawer.openDrawer(Gravity.RIGHT);

    }
}
