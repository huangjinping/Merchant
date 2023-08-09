package com.hdfex.merchantqrshow.salesman.appointment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpListQueryV2Response;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpListQueryV2Result;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.appointment.activity.BackOrderActivity;
import com.hdfex.merchantqrshow.salesman.appointment.activity.DataErrorActivity;
import com.hdfex.merchantqrshow.salesman.appointment.activity.RepayActivity;
import com.hdfex.merchantqrshow.salesman.appointment.adapter.FollowUnAdapter;
import com.hdfex.merchantqrshow.salesman.order.activity.ChangeFundSourceActivity;
import com.hdfex.merchantqrshow.salesman.order.activity.WoFuActivity;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.MyItemClickListener;
import com.hdfex.merchantqrshow.widget.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2017/10/25.
 * email : huangjinping@hdfex.com
 */

public class FollowUnListFragment extends BaseFragment {

    public String status;
    private MultiStateView multiStateView;
    private RecyclerView rec_follow_details;
    private SmartRefreshLayout xr_freshview;
    private List<FollowUpListQueryV2Result> dataList;
    private FollowUnAdapter mAdapter;
    private User user;

    public static BaseFragment getInstance(String status) {
        FollowUnListFragment fragment = new FollowUnListFragment();
        fragment.status = status;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followun, container, false);
        initView(view);
        user = UserManager.getUser(getActivity());
        dataList = new ArrayList<>();
        mAdapter = new FollowUnAdapter(dataList);
        rec_follow_details.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec_follow_details.setAdapter(mAdapter);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mAdapter.setMyItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                FollowUpListQueryV2Result followUpListQueryV2Result = dataList.get(postion);
//                00-	电核异常
//                01-	资料异常
//                02-	待还款列表
//                03-	逾期列表
//                04-	未提交列表

                if (("00".equals(followUpListQueryV2Result.getType()))) {
                    DataErrorActivity.startAction(getActivity(), "00");
                } else if ("01".equals(followUpListQueryV2Result.getType())) {
                    DataErrorActivity.startAction(getActivity(), "01");
                } else if ("02".equals(followUpListQueryV2Result.getType())) {
                    RepayActivity.startAction(getActivity(), "00");
                } else if ("03".equals(followUpListQueryV2Result.getType())) {
                    RepayActivity.startAction(getActivity(), "01");
                } else if (("04".equals(followUpListQueryV2Result.getType()))) {
                    BackOrderActivity.startAction(getActivity(), "00");
                } else if (("05".equals(followUpListQueryV2Result.getType()))) {
                    ChangeFundSourceActivity.startAction(getActivity(), "01");
                } else if (("06".equals(followUpListQueryV2Result.getType()))) {
                    WoFuActivity.startAction(getActivity(), "01");
                }
            }
        });

        xr_freshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadData();
            }
        });
//        xr_freshview.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                loadData();
//            }
//        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 下载数据
     */
    private void loadData() {
        showProgress();
        OkHttpUtils.post().addParams("id", user.getId())
                .addParams("token", user.getToken())
                .url(NetConst.FOLLOW_UP_LIST_QUERY_V2).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                dismissProgress();
                if (checkResonse(response)) {
                    FollowUpListQueryV2Response upListQueryV2Response = GsonUtil.changeGsonToBean(response, FollowUpListQueryV2Response.class);
                    List<FollowUpListQueryV2Result> result = upListQueryV2Response.getResult();
                    dataList.clear();
                    dataList.addAll(result);
                    Iterator<FollowUpListQueryV2Result> it = dataList.iterator();
                    while (it.hasNext()) {
                        FollowUpListQueryV2Result x = it.next();
                        if (x.getCustCount() < 1) {
                            it.remove();
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
                updateView();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                endLoadView();
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


    private void endLoadView() {
        xr_freshview.finishRefresh();//结束刷新
        xr_freshview.finishLoadmore();//结束加载
    }

    private void initView(View view) {
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
        rec_follow_details = (RecyclerView) view.findViewById(R.id.rec_follow_details);
        xr_freshview = (SmartRefreshLayout) view.findViewById(R.id.xr_freshview);
        xr_freshview.setEnableLoadmore(false);

    }
}
