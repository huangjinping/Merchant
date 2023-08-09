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
import com.hdfex.merchantqrshow.bean.salesman.appointment.SubscribeItem;
import com.hdfex.merchantqrshow.bean.salesman.appointment.SubscribeListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.ReservationContract;
import com.hdfex.merchantqrshow.mvp.presenter.ReservationPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.appointment.adapter.ReservationListAdapter;
import com.hdfex.merchantqrshow.utils.EventRxBus;
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
import okhttp3.Request;
import rx.Subscriber;

/**
 * author Created by harrishuang on 2017/9/8.
 * email : huangjinping@hdfex.com
 */

/**
 * 抢单 拒绝   公共两个
 * 接受  拒绝  转单   私有的时候
 * 接受  拒绝    接受放
 * <p>
 * 发送的时候  毛线没有
 */

public class ReservationListFragment extends BaseFragment implements ReservationContract.View, ReservationListAdapter.ReservationCallBack {


    public String status;
    private SmartRefreshLayout xr_freshview;
    private MultiStateView multiStateView;
    private User user;
    private ReservationContract.Presenter presenter;
    private RecyclerView rec_appointment_list;
    private ReservationListAdapter mAdapter;
    private List<SubscribeItem> dataList;
    /**
     * 下载数据
     *
     * @param loadMode
     */
    private int page = 0;

    public static ReservationListFragment getInstance(String status) {
        ReservationListFragment fragment = new ReservationListFragment();
        fragment.status = status;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followlist, container, false);
        initView(view);
        user = UserManager.getUser(getActivity());
        presenter = new ReservationPresenter();
        presenter.attachView(this);
        dataList = new ArrayList<>();
        mAdapter = new ReservationListAdapter(dataList, status);
        rec_appointment_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec_appointment_list.setAdapter(mAdapter);
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
        mAdapter.setCallBack(this);
        loadData(LoadMode.NOMAL);
        TextView textEmpty = (TextView) multiStateView.getView(MultiStateView.VIEW_STATE_EMPTY).findViewById(R.id.txt_empty);

        if (SubscribeItem.STATUS_NOT_FOLLOW_UP.equals(status)) {
            textEmpty.setText("暂无未跟进预约");

        } else if (SubscribeItem.STATUS_FOLLOWED.equals(status)) {
            textEmpty.setText("暂无已跟进预约");

        } else if (SubscribeItem.STATUS_COMPLATE.equals(status)) {
            textEmpty.setText("暂无已完成预约");

        } else {
            textEmpty.setText("暂无预约");
        }

        return view;
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
        String url = null;
        if (SubscribeItem.public_LIST.equals(status)) {
            url = NetConst.BUS_PUBLIC_CLUE_LIST;
        } else {
            url = NetConst.BUS_SUBS_CRIBE_LIST;
        }


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
                        endLoadView();
                        dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);

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
                                SubscribeListResponse subscribeListResponse = GsonUtil.changeGsonToBean(response, SubscribeListResponse.class);
                                if (loadMode != LoadMode.UP_REFRESH) {
                                    dataList.clear();
                                }
                                if (subscribeListResponse.getResult() != null) {
                                    ArrayList<SubscribeItem> orderItemslist = (ArrayList<SubscribeItem>) subscribeListResponse.getResult();

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
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);

                }
            });
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    @Override
    public void onTurn(SubscribeItem item, int index) {
        EventRxBus.getInstance().post(IntentFlag.TURN_DESC_KEY, item);
        EventRxBus.getInstance().unregister(IntentFlag.TURN_DESC_KEY_UPDATE);
        EventRxBus.getInstance().register(IntentFlag.TURN_DESC_KEY_UPDATE).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                loadData(LoadMode.PULL_REFRSH);

            }
        });


    }

    @Override
    public void onRefuse(SubscribeItem item, int index) {
        presenter.refuseOrder(getActivity(), user, item);
    }

    @Override
    public void onDetails(SubscribeItem item, int index) {

        if (SubscribeItem.STATUS_FOLLOWED.equals(status)) {
            presenter.getAppointmentDetails(getActivity(), user, item);
            addDetailsUpdateLitener();


        } else if (SubscribeItem.STATUS_COMPLATE.equals(status)) {
            presenter.getAppointmentDetails(getActivity(), user, item);
            addDetailsUpdateLitener();
        }

    }

    /**
     * 问题列表
     */
    private void addDetailsUpdateLitener() {
        EventRxBus.getInstance().unregister(IntentFlag.DETAILS_COMPLATE_UPDATE);
        EventRxBus.getInstance().register(IntentFlag.DETAILS_COMPLATE_UPDATE).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                loadData(LoadMode.PULL_REFRSH);

            }
        });
    }

    @Override
    public void onRushOrder(SubscribeItem item, int index) {
        presenter.pushOrder(getActivity(), user, item);
    }

    @Override
    public void onAccpet(SubscribeItem item, int index) {
        presenter.accpetOrder(getActivity(), user, item);

    }

    @Override
    public void updateList() {
        loadData(LoadMode.NOMAL);

    }


}
