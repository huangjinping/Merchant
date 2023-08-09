package com.hdfex.merchantqrshow.salesman.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.QueryUncompelete;
import com.hdfex.merchantqrshow.bean.salesman.order.ScanDetails;
import com.hdfex.merchantqrshow.bean.salesman.order.ScanRecordResponse;
import com.hdfex.merchantqrshow.mvp.presenter.ScanRecordPresenter;
import com.hdfex.merchantqrshow.mvp.view.ScanView;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.main.adapter.ScanRecordAdapter;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.RegexUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.view.xlistView.XListView;
import com.hdfex.merchantqrshow.widget.MultiStateView;
import com.hdfex.merchantqrshow.widget.MyListViewClickListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by harrishuang
 */

public class ScanRecordFragment extends BaseFragment implements ScanView {


    private XListView lisv_scanrecord;
    private ScanRecordAdapter adapter;
    private User user;
    private int page = 0;
    private List<QueryUncompelete> dataList;
    private String commodityName;
    private String phone;

    private boolean loadstate = false;
    private String searchData;
    public String type;
    public ScanRecordPresenter presenter;
    private MultiStateView multiStateView;

    public static ScanRecordFragment getInstance(String type) {
        ScanRecordFragment fragment = new ScanRecordFragment();
        fragment.type = type;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanrecord, container, false);
        presenter = new ScanRecordPresenter();
        presenter.attachView(this);

        user = UserManager.getUser(getActivity());
        dataList = new ArrayList<>();
        initView(view);
        onEmpty();
        adapter = new ScanRecordAdapter(getActivity(), dataList, type);
        loadData(LoadMode.NOMAL);
        setOnListeners();
        lisv_scanrecord.setAdapter(adapter);
        return view;
    }

    private void setOnListeners() {
        lisv_scanrecord.setPullLoadEnable(false);
        lisv_scanrecord.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                loadData(LoadMode.PULL_REFRSH);
            }

            @Override
            public void onLoadMore() {
                loadData(LoadMode.UP_REFRESH);
            }
        });
        adapter.setListViewClickListener(new MyListViewClickListener() {
            @Override
            public void itemClick(Object obj, int position) {
                QueryUncompelete queryUncompelete = (QueryUncompelete) obj;
                presenter.onSelectScan(type, queryUncompelete, getActivity());

            }
        });
    }

    private void initView(View view) {
        lisv_scanrecord = (XListView) view.findViewById(R.id.lisv_scanrecord);
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
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
        if (!TextUtils.isEmpty(searchData)) {
            if (RegexUtils.phone(searchData)) {
                commodityName = null;
                phone = searchData;
            } else {
                commodityName = searchData;
                phone = null;
            }
        } else {
            phone = null;
            commodityName = null;
        }
        if (loadMode == LoadMode.NOMAL) {
            showProgress();
        }
        if (loadMode != LoadMode.UP_REFRESH) {
            page = 0;
        }
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(NetConst.QUERY_UNCOMPELETE)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("pageIndex", page + "")
                .addParams("pageSize", "20")
                .addParams("packageType", type);

        if (loadstate && !TextUtils.isEmpty(commodityName)) {
            postFormBuilder.addParams("commodityName", commodityName);
        }
        if (loadstate && !TextUtils.isEmpty(phone)) {
            postFormBuilder.addParams("phone", phone);
        }
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                        Log.d("hjp", "thread:" + Thread.currentThread().getName());
                        onLoad();

                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                LogUtil.e("zbt", response);
                                ScanRecordResponse scanRecordResponse = GsonUtil.changeGsonToBean(response, ScanRecordResponse.class);
                                ScanDetails scanDetails = scanRecordResponse.getResult();
                                List<QueryUncompelete> list = scanDetails.getList();
                                LogUtil.e("zbt", list.size());
                                if (list.size() == 0) {
                                    if (page == 0) {
//                                        ToastUtils.makeText(getActivity(), "无此记录").show();
                                        return;
                                    }
                                    ToastUtils.makeText(getActivity(), "没有更多数据").show();
                                    return;
                                }
                                page++;
                                if (loadMode != LoadMode.UP_REFRESH) {
                                    dataList.clear();
                                }
                                dataList.addAll(list);
                                if (dataList.size() >= 20 * page) {
                                    lisv_scanrecord.setPullLoadEnable(true);
                                } else {
                                    lisv_scanrecord.setPullLoadEnable(false);
                                }
                                adapter.notifyDataSetChanged();


                                if (dataList.size() == 0) {
                                    onEmpty();
                                } else {
                                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
//                            showWebEirr();
                        }
                    }
                });

    }

    private void onLoad() {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lisv_scanrecord.stopRefresh();
                        lisv_scanrecord.stopLoadMore();
                        lisv_scanrecord.setRefreshTime("刚刚");
                    } catch (Exception E) {
                        E.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }

    public void onEmpty() {
        try {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
