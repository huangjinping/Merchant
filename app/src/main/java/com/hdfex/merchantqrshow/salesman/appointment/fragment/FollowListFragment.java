package com.hdfex.merchantqrshow.salesman.appointment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpDetailQueryResponse;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpItem;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpListQueryResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.appointment.activity.FollowActivity;
import com.hdfex.merchantqrshow.salesman.appointment.adapter.FollowListAdapter;
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
 * author Created by harrishuang on 2017/9/8.
 * email : huangjinping@hdfex.com
 */

public class FollowListFragment extends BaseFragment {

    public String status;
    private RecyclerView rec_appointment_list;
    private FollowListAdapter mAdapter;
    private List<FollowUpItem> dataList;
    private SmartRefreshLayout xr_freshview;
    private MultiStateView multiStateView;
    private User user;
    /**
     * 下载数据
     *
     * @param loadMode
     */
    private int page = 0;

    public static BaseFragment getInstance(String status) {
        FollowListFragment fragment = new FollowListFragment();
        fragment.status = status;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followlist, container, false);
        user = UserManager.getUser(getContext());

        initView(view);
        dataList = new ArrayList<>();

        mAdapter = new FollowListAdapter(dataList);
        rec_appointment_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec_appointment_list.setAdapter(mAdapter);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mAdapter.setItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                FollowUpItem followUpItem = dataList.get(postion);
                loadDetails(followUpItem.getApplyId(), followUpItem.getExamineId());
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
        if (FollowUpItem.UN_FOLLOW.equals(status)) {
            textEmpty.setText("暂无未跟进订单\n" +
                    "目前展示客户的电核接听情况");


        } else if (FollowUpItem.FOLLOWED.equals(status)) {
            textEmpty.setText("暂无跟进订单");
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(LoadMode.PULL_REFRSH);
    }

    /**
     * 问题列表
     */
    private void addDetailsUpdateLitener() {
        EventRxBus.getInstance().unregister(IntentFlag.FOLLOW_LIST_UPDATE);
        EventRxBus.getInstance().register(IntentFlag.FOLLOW_LIST_UPDATE).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                showToast("dddd>>>>");


            }
        });
    }

    private void endLoadView() {
        xr_freshview.finishRefresh();//结束刷新
        xr_freshview.finishLoadmore();//结束加载
    }

    private void initView(View view) {
        rec_appointment_list = (RecyclerView) view.findViewById(R.id.rec_appointment_list);
        xr_freshview = (SmartRefreshLayout) view.findViewById(R.id.xr_freshview);

        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);

    }

    /**
     * 下载详情
     *
     * @param applyId
     */
    private void loadDetails(String applyId, String examineId) {

        showProgress();
        OkHttpUtils.post().addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("applyId", applyId)
                .addParams("examineId", examineId)
                .url(NetConst.FOLLOW_UP_DETAIL_QUERY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }


            @Override
            public void onResponse(String response) {
                try {
                    if (checkResonse(response)) {
                        FollowUpDetailQueryResponse queryResponse = GsonUtil.changeGsonToBean(response, FollowUpDetailQueryResponse.class);
                        queryResponse.getResult().setStatus(status);
                        FollowActivity.startAction(getActivity(), queryResponse.getResult());

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
        String url = NetConst.FOLLOW_UP_LIST_QUERY;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("status", "0")
                .addParams("pageIndex", page + "")
                .addParams("pageSize", "10")
                .addParams("status", status);
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
                                FollowUpListQueryResponse subscribeListResponse = GsonUtil.changeGsonToBean(response, FollowUpListQueryResponse.class);
                                if (loadMode != LoadMode.UP_REFRESH) {
                                    dataList.clear();
                                }
                                if (subscribeListResponse.getResult() != null) {
                                    ArrayList<FollowUpItem> orderItemslist = (ArrayList<FollowUpItem>) subscribeListResponse.getResult();


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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
