package com.hdfex.merchantqrshow.admin.business.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.business.activity.AdminOrderDetailsActivity;
import com.hdfex.merchantqrshow.admin.business.adapter.AdminOrderAdapter;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.admin.business.AlipayOrderDetailForAdminResponse;
import com.hdfex.merchantqrshow.bean.admin.business.AlipayOrderForAdmin;
import com.hdfex.merchantqrshow.bean.admin.business.AlipayOrderForAdminResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.MyItemClickListener;
import com.hdfex.merchantqrshow.widget.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/12/28.
 * email : huangjinping@hdfex.com
 */

public class AdminOrderListFragment extends BaseFragment {
    private MultiStateView multiStateView;
    private RecyclerView rec_admin_order;
    private SmartRefreshLayout xr_freshview;
    private List<AlipayOrderForAdmin> dataList;
    private AdminOrderAdapter mAdapter;

    /**
     * 下载数据
     * @param loadMode
     *
     */
    private int page = 0;
    private User user;
    private Context context;
    public String status;

    public  static BaseFragment getInstance(String status){
        AdminOrderListFragment fragment=new AdminOrderListFragment();
            fragment.status=status;
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgament_adminorderlist, container, false);
        initView(view);
        context = getActivity();
        user = UserManager.getUser(context);
        dataList = new ArrayList<>();
        mAdapter = new AdminOrderAdapter(dataList,status);
        rec_admin_order.setLayoutManager(new LinearLayoutManager(context));
        rec_admin_order.setAdapter(mAdapter);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mAdapter.setItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

                AlipayOrderForAdmin alipayOrderForAdmin = dataList.get(postion);
                loadDataDetails(alipayOrderForAdmin.getOrderId());
            }
        });
        xr_freshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadData(LoadMode.PULL_REFRSH);
            }


        });
        xr_freshview.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadData(LoadMode.UP_REFRESH);
            }
        });
        TextView textEmpty = (TextView) multiStateView.getView(MultiStateView.VIEW_STATE_EMPTY).findViewById(R.id.txt_empty);
        textEmpty.setText("暂无订单");

        return view;
    }

    private void initView(View view) {
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
        rec_admin_order = (RecyclerView) view.findViewById(R.id.rec_admin_order);
        xr_freshview = (SmartRefreshLayout) view.findViewById(R.id.xr_freshview);
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData(LoadMode.NOMAL);
    }

    /**
     * 下载数据
     *
     * @param loadMode
     */
    private void loadData(final LoadMode loadMode) {
        if (!connect()) {
            return;
        }
        if (loadMode == LoadMode.NOMAL) {
            showProgress();
        }
        if (loadMode != LoadMode.UP_REFRESH) {
            page = 0;
        }
        String url = NetConst.GET_ALIPAY_ORDER_FOR_ADMIN;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("id", user.getBussinessId())
                .addParams("status", "0")
                .addParams("pageIndex", page + "")
                .addParams("pageSize", "10")
                .addParams("status", status);
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();

                        endLoadView();
                        dismissProgress();

                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);

                    }

                    @Override
                    public void onError(Call call, Exception e) {

                        updateView();
                    }

                    @Override
                    public void onResponse(String response) {

                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                AlipayOrderForAdminResponse subscribeListResponse = GsonUtil.changeGsonToBean(response, AlipayOrderForAdminResponse.class);
                                if (loadMode != LoadMode.UP_REFRESH) {
                                    dataList.clear();
                                }
                                if (subscribeListResponse.getResult() != null) {
                                    ArrayList<AlipayOrderForAdmin> orderItemslist = (ArrayList<AlipayOrderForAdmin>) subscribeListResponse.getResult();
                                    if (orderItemslist != null && orderItemslist.size() != 0) {
                                        page++;
                                        dataList.addAll(orderItemslist);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        updateView();

                    }
                });
    }

    /**
     * 刷新数据
     */
    private void updateView() {
        if (dataList.size() == 0) {
            onEmpty();
        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 停止加载动画
     */
    private void endLoadView() {
        xr_freshview.finishRefresh();//结束刷新
        xr_freshview.finishLoadmore();//结束加载
    }


    /**
     * 为空
     */
    public void onEmpty() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        });
    }




    /**
     * 下载数据
     *
     * @param orderId
     */
    private void loadDataDetails(final String  orderId) {
        if (!connect()) {
            return;
        }

        String url = NetConst.GET_ALIPAY_ORDER_DETAIL_FOR_ADMIN;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("orderId",orderId);
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();

                        endLoadView();
                        dismissProgress();

                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);

                    }

                    @Override
                    public void onError(Call call, Exception e) {

                        updateView();
                    }

                    @Override
                    public void onResponse(String response) {

                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                AlipayOrderDetailForAdminResponse subscribeListResponse = GsonUtil.changeGsonToBean(response, AlipayOrderDetailForAdminResponse.class);
                                AdminOrderDetailsActivity.startAction(getActivity(),subscribeListResponse.getResult());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        updateView();

                    }
                });
    }
}
