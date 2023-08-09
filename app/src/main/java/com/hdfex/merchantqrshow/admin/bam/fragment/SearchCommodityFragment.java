package com.hdfex.merchantqrshow.admin.bam.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
 * author Created by harrishuang on 2017/12/8.
 * email : huangjinping@hdfex.com
 */

public class SearchCommodityFragment extends BaseFragment implements View.OnClickListener {


    private AdminCommodityAdapter mAdapter;
    private RecyclerView rec_commodity_list;
    private List<FindCommodityItem> dataList;
    private MultiStateView multiStateView;
    private SmartRefreshLayout xr_freshview;
    private User user;
    private int page = 0;
    public String counterFlag;
    private ImageView img_back;
    private EditText edt_order_search;
    private TextView txt_search;


    public static SearchCommodityFragment getInstance(String counterFlag) {
        SearchCommodityFragment fragment = new SearchCommodityFragment();
        fragment.counterFlag = counterFlag;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_commodity, container, false);
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
                    updateCommodityStatus(tag, item.getCommodityId());
                } else if ("2".equals(tag)) {
                    /**
                     * 下架
                     */
                    updateCommodityStatus(tag, item.getCommodityId());
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
        setLiteners();
        return view;

    }


    private void initView(View view) {
        rec_commodity_list = (RecyclerView) view.findViewById(R.id.rec_commodity_list);
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
        xr_freshview = (SmartRefreshLayout) view.findViewById(R.id.xr_freshview);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        edt_order_search = (EditText) view.findViewById(R.id.edt_order_search);
        edt_order_search.setOnClickListener(this);
        txt_search = (TextView) view.findViewById(R.id.txt_search);
        txt_search.setOnClickListener(this);
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
                .addParams("commodityName", edt_order_search.getText().toString().trim())
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
    private void updateCommodityStatus(String counterFlag, String commodityId) {
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
                        loadData(LoadMode.NOMAL);
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

    private void submit() {
        // validate


        // TODO validate success, do something
        String search = edt_order_search.getText().toString().trim();
        if (TextUtils.isEmpty(search)) {
            return;
        }

        loadData(LoadMode.NOMAL);

    }


    /**
     *
     */
    private void setLiteners() {

        edt_order_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = edt_order_search.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        return true;
                    }
                    ((InputMethodManager) edt_order_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getActivity().getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
//                    layout_search.setVisibility(View.GONE);
//                    layout_content_view.setVisibility(View.VISIBLE);
                    submit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_search:
                submit();
                break;
            case R.id.img_back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
