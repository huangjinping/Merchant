package com.hdfex.merchantqrshow.admin.bam.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.bam.activity.AddCommodityActivity;
import com.hdfex.merchantqrshow.admin.bam.adapter.AdminCommodityAdapter;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.admin.bam.FindCommodityItem;
import com.hdfex.merchantqrshow.bean.admin.bam.FindCommodityListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.EventRxBus;
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
import rx.Subscriber;

/**
 * author Created by harrishuang on 2017/12/1.
 * email : huangjinping@hdfex.com
 */

public class AdminCommodityItemFragment extends BaseFragment {

    private AdminCommodityAdapter mAdapter;
    private RecyclerView rec_commodity_list;
    private List<FindCommodityItem> dataList;
    private MultiStateView multiStateView;
    private SmartRefreshLayout xr_freshview;
    private User user;
    private int page = 0;
    public String counterFlag;

    public static BaseFragment getInstance(String counterFlag) {
        AdminCommodityItemFragment fragment = new AdminCommodityItemFragment();
        fragment.counterFlag = counterFlag;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commodity_list, container, false);
        initView(view);
        user = UserManager.getUser(getActivity());
        dataList = new ArrayList<>();
        mAdapter = new AdminCommodityAdapter(dataList, counterFlag);
        rec_commodity_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec_commodity_list.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                String tag = view.getTag().toString();
                FindCommodityItem item = dataList.get(postion);
                if ("1".equals(tag)) {
                    /**
                     * 上架
                     */
                    updateCommodityStatus(tag,item.getCommodityId());
                } else if ("2".equals(tag)) {
                    /**
                     * 下架
                     */
                    updateCommodityStatus(tag,item.getCommodityId());
                } else if ("3".equals(tag)) {
                    AddCommodityActivity.startAction(getContext());
                }
            }
        });

        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);


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
        EventRxBus.getInstance().register(AdminCommodityItemFragment.class.getSimpleName()).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                loadData(LoadMode.NOMAL);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(LoadMode.NOMAL);
    }

    private void initView(View view) {
        rec_commodity_list = (RecyclerView) view.findViewById(R.id.rec_commodity_list);
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
        xr_freshview = (SmartRefreshLayout) view.findViewById(R.id.xr_freshview);
    }

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
        String url = NetConst.FIND_COMMODITY_LIST_BY_COUNTER_FLG;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("id", user.getBussinessId())
                .addParams("status", "0")
                .addParams("pageIndex", page + "")
                .addParams("counterFlag", counterFlag)
                .addParams("pageSize", "10");
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        if (getActivity() == null) {
                            return;
                        }
                        endLoadView();
                        dismissProgress();

                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        if (getActivity() == null) {
                            return;
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        if (getActivity() == null) {
                            return;
                        }
                        updateView();
                    }

                    @Override
                    public void onResponse(String response) {
                        if (getActivity() == null) {
                            return;
                        }
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                FindCommodityListResponse subscribeListResponse = GsonUtil.changeGsonToBean(response, FindCommodityListResponse.class);
                                if (loadMode != LoadMode.UP_REFRESH) {
                                    dataList.clear();
                                }
                                if (subscribeListResponse.getResult() != null) {
                                    ArrayList<FindCommodityItem> orderItemslist = (ArrayList<FindCommodityItem>) subscribeListResponse.getResult();


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


    private void endLoadView() {
        xr_freshview.finishRefresh();//结束刷新
        xr_freshview.finishLoadmore();//结束加载
    }

    /**
     * 下载详情
     *
     * @param
     */
    private void updateCommodityStatus(String counterFlag,String commodityId) {
        showProgress();
        OkHttpUtils.post().addParams("id", user.getBussinessId())
                .addParams("token", user.getToken())
                .addParams("counterFlag", counterFlag)
                .addParams("commodityId", commodityId)
                .url(NetConst.UPDATE_COMMODITY_STATUS).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (checkResonse(response)) {
                        EventRxBus.getInstance().post(AdminCommodityItemFragment.class.getSimpleName(),response);
                    }
                } catch (Exception e) {
                    showToast("数据异常");
                    e.printStackTrace();
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissProgress();
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventRxBus.getInstance().unregister(AdminCommodityItemFragment.class.getSimpleName());
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

}
