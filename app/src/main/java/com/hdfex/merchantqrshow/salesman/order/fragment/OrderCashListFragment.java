package com.hdfex.merchantqrshow.salesman.order.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.MultiOrder;
import com.hdfex.merchantqrshow.bean.salesman.order.MultiOrderResponse;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.activity.PayMonthlyQRCodeActivity;
import com.hdfex.merchantqrshow.salesman.add.activity.QRCodeProduceActivity;
import com.hdfex.merchantqrshow.salesman.add.activity.ZuFangQRCodeProduceActivity;
import com.hdfex.merchantqrshow.salesman.order.activity.MutliOrderDetailsActivity;
import com.hdfex.merchantqrshow.salesman.order.adapter.CashOrderAdapter;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.MyItemClickListener;
import com.hdfex.merchantqrshow.widget.MultiStateView;
import com.hdfex.merchantqrshow.widget.OrderTypePopWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2019/9/5.
 * email : huangjinping1000@163.com
 */
public class OrderCashListFragment extends BaseFragment implements View.OnClickListener {


    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView tv_home;
    private MultiStateView multiStateView;
    private RecyclerView rec_cash_order;
    private SmartRefreshLayout xr_freshview;
    private CashOrderAdapter orderAdapter;
    private List<MultiOrder> dataList;
    private String search;
    private String status = "";
    private User user;
    private List statusTitleList;
    private List statusList;
    private EditText edt_order_search;
    private TextView txt_search;


    public static OrderCashListFragment getInstance(String status, String search) {
        OrderCashListFragment sf = new OrderCashListFragment();
        sf.status = status;
        sf.search = search;
        return sf;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadData(LoadMode.NOMAL);
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cashorderlist, container, false);
        user = UserManager.getUser(getActivity());
        initView(view);
        dataList = new ArrayList<>();
        orderAdapter = new CashOrderAdapter(dataList);
        rec_cash_order.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec_cash_order.setAdapter(orderAdapter);
        xr_freshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadData(LoadMode.PULL_REFRSH);
            }
        });

        handler.sendMessageDelayed(handler.obtainMessage(), 1 * 1000);

        xr_freshview.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadData(LoadMode.UP_REFRESH);
            }
        });
        orderAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MultiOrder orderItem = dataList.get(position);
                if (!"00".equals(orderItem.getStatus())) {
                    MutliOrderDetailsActivity.startAction(getActivity(), "" + orderItem.getId(), view);
                } else {
                    String packageType = orderItem.getPackageType();
                    Intent intent = new Intent();
                    if ("1".equals(packageType)) {
                        intent.setClass(getActivity(), ZuFangQRCodeProduceActivity.class);
                    } else if ("2".equals(packageType)) {
                        intent.setClass(getActivity(), QRCodeProduceActivity.class);
                    } else {
                        intent.setClass(getActivity(), PayMonthlyQRCodeActivity.class);
                    }
                    intent.putExtra(IntentFlag.INTENT_NAME, IntentFlag.CONFORM);
                    intent.putExtra("packageId", orderItem.getId());
                    getActivity().startActivity(intent);
                }
            }
        });


        edt_order_search.setHint("请输入客户姓名");

        edt_order_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = edt_order_search.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        return true;
                    }
                    // TODO: 16/4/7  搜索现象
                    ((InputMethodManager) edt_order_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getActivity().getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    search = edt_order_search.getText().toString().trim();
                    onSearch();
                    return true;
                }
                return false;
            }
        });


        statusTitleList = new ArrayList<String>();
        statusTitleList.add("全部");
        statusTitleList.add("扫码未提交");
        statusTitleList.add("待开户");
        statusTitleList.add("待签约");
        statusTitleList.add("已提交");
        statusTitleList.add("已打回");
        statusTitleList.add("已取消");
        statusTitleList.add("已拒绝");
        statusTitleList.add("审核中");
        statusTitleList.add("审核通过");
        statusTitleList.add("还款中");
        statusTitleList.add("已结清");
        statusList = new ArrayList<String>();

        statusList.add("");
        statusList.add("00");
        statusList.add("01");
        statusList.add("02");
        statusList.add("03");
        statusList.add("04");
        statusList.add("05");
        statusList.add("06");
        statusList.add("07");
        statusList.add("08");
        statusList.add("09");
        statusList.add("10");
        return view;
    }

    private void initMenu(View view) {
        OrderTypePopWindow typePopWindow = new OrderTypePopWindow(getActivity(), statusTitleList);
        typePopWindow.showAsDropDown(view, 10, 10);
        typePopWindow.setOnOrderTypeCallBack(new OrderTypePopWindow.onOrderTypeCallBack() {
            @Override
            public void callBack(String title, int position) {
                tv_home.setText(title);
                status = (String) statusList.get(position);
                loadData(LoadMode.NOMAL);
            }
        });
    }


    private void endLoadView() {
        xr_freshview.finishRefresh();//结束刷新
        xr_freshview.finishLoadmore();//结束加载
    }

    private void initView(View view) {
        img_back = (ImageView) view.findViewById(R.id.img_back);
        tb_tv_titile = (TextView) view.findViewById(R.id.tb_tv_titile);
        tv_home = (TextView) view.findViewById(R.id.tv_home);
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
        rec_cash_order = (RecyclerView) view.findViewById(R.id.rec_cash_order);
        xr_freshview = (SmartRefreshLayout) view.findViewById(R.id.xr_freshview);
        tb_tv_titile.setText("订单");
        tv_home.setVisibility(View.VISIBLE);
        tv_home.setText("筛选");
        tv_home.setOnClickListener(this);
        img_back.setVisibility(View.GONE);
        edt_order_search = (EditText) view.findViewById(R.id.edt_order_search);
        edt_order_search.setOnClickListener(this);
        txt_search = (TextView) view.findViewById(R.id.txt_search);
        txt_search.setOnClickListener(this);
    }


    /**
     * 下载数据
     *
     * @param loadMode
     */
    private int page = 0;

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
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(NetConst.GET_ORDER_LIST_V3)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("status", "0")
                .addParams("pageIndex", page + "")
                .addParams("pageSize", "15")
                .addParams("custName", edt_order_search.getText().toString().trim())
                .addParams("status", status);
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                        endLoadView();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);

                        if (!TextUtils.isEmpty(search)) {

                        }

                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        onErrr();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                MultiOrderResponse orderListResponse = GsonUtil.changeGsonToBean(response, MultiOrderResponse.class);
                                if (orderListResponse == null) {
                                    if (page == 0) {
                                        ToastUtils.makeText(getActivity(), "无此记录").show();
                                        return;
                                    }
                                }

                                if (loadMode != LoadMode.UP_REFRESH) {
                                    dataList.clear();
                                }


                                if (orderListResponse.getResult() != null) {
                                    ArrayList<MultiOrder> orderItemslist = (ArrayList<MultiOrder>) orderListResponse.getResult();
                                    if (orderItemslist == null || orderItemslist.size() == 0) {
                                        onEmpty();

                                        if (orderItemslist == null) {
                                            return;
                                        }

                                        if (page == 0) {
                                            ToastUtils.makeText(getActivity(), "无此记录").show();
                                            return;
                                        }
                                        ToastUtils.makeText(getActivity(), "没有更多数据").show();
                                        return;
                                    }
                                    page++;

                                    dataList.addAll(orderItemslist);

//                                    if (dataList.size() < 14) {
//                                        xr_freshview.setEnableLoadmore(false);
//                                    } else {
//                                        xr_freshview.setEnableLoadmore(true);
//                                    }

                                }
                                if (dataList.size() == 0) {
                                    onEmpty();
                                } else {
                                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                                }

                                orderAdapter.notifyDataSetChanged();

                            } else {
                                onErrr();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void onEmpty() {

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);

            }
        });
    }

    public void onErrr() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home:
                initMenu(v);
                break;
            case R.id.txt_search:
                onSearch();
                break;
        }
    }

    /**
     * 点击搜索
     */
    private void onSearch() {
        search = edt_order_search.getText().toString().trim();


        loadData(LoadMode.NOMAL);
    }

}
