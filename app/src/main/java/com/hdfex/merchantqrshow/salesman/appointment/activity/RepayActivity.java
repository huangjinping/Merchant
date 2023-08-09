package com.hdfex.merchantqrshow.salesman.appointment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.appointment.RepayListItem;
import com.hdfex.merchantqrshow.bean.salesman.appointment.RepayListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.appointment.adapter.RepayDetailsAdapter;
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
 * 跟进详情
 * author Created by harrishuang on 2017/10/24.
 * email : huangjinping@hdfex.com
 */

public class RepayActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private RecyclerView rec_follow_details;
    private RepayDetailsAdapter adapter;
    private List<RepayListItem> dataList;
    private MultiStateView multiStateView;
    private SmartRefreshLayout xr_freshview;
    private User user;
    private Intent intent;
    /**
     * 下载数据
     *
     * @param loadMode
     */
    private int page = 0;
    private String type;

    public static void startAction(Context context, String type) {
        Intent intent = new Intent(context, RepayActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followdetails);
        initView();
        intent = getIntent();
        type = intent.getStringExtra("type");

        if ("00".equals(type)){
            tb_tv_titile.setText("待还款跟进详情");
        }else {
            tb_tv_titile.setText("逾期跟进详情");
        }

        user = UserManager.getUser(this);
        dataList = new ArrayList<>();
        adapter = new RepayDetailsAdapter(dataList, type);
        rec_follow_details.setLayoutManager(new LinearLayoutManager(this));
        rec_follow_details.setAdapter(adapter);
        loadData(LoadMode.NOMAL);
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

        adapter.setOnCallSendClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                RepayListItem repayListItem = dataList.get(postion);
                if (TextUtils.isEmpty(repayListItem.getPhoneNo())){
                    return;
                }
                showAlertForPhone(repayListItem.getPhoneNo());
                updatePhoneCount(repayListItem.getPlanId(),"");
            }
        });

        adapter.setOnSendMessageClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                RepayListItem repayListItem = dataList.get(postion);
                if (TextUtils.isEmpty(repayListItem.getPhoneNo())){
                    return;
                }
                showAlertForMessage(repayListItem.getPhoneNo(), "提醒你一下");
                updateSMSCount(repayListItem.getPlanId());
            }
        });
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        rec_follow_details = (RecyclerView) findViewById(R.id.rec_follow_details);

        multiStateView = (MultiStateView) findViewById(R.id.multiStateView);
        multiStateView.setOnClickListener(this);
        xr_freshview = (SmartRefreshLayout) findViewById(R.id.xr_freshview);
        xr_freshview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    /**
     * 更新电话信息
     */
    private void updatePhoneCount(String planId,String examineId) {
        String url = NetConst.UPDATE_PHONE_COUNT;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("planId", planId)
                .addParams("examineId",examineId)
                .addParams("type", "00");

        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 更新短信次数
     */
    private void updateSMSCount(String planId) {
        String url = NetConst.UPDATE_SMS_COUNT;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("planId", planId);
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
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
        String url = NetConst.DISCOVERY_REPAYLIST;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("status", "0")
                .addParams("type", type)
                .addParams("pageIndex", page + "")
                .addParams("pageSize", "10");
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
                                RepayListResponse subscribeListResponse = GsonUtil.changeGsonToBean(response, RepayListResponse.class);
                                if (loadMode != LoadMode.UP_REFRESH) {
                                    dataList.clear();
                                }
                                if (subscribeListResponse.getResult() != null) {
                                    List<RepayListItem> result = subscribeListResponse.getResult();
                                    if (result != null && result.size() != 0) {
                                        page++;
                                        dataList.addAll(result);
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
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData(LoadMode.NOMAL);
    }

    /**
     * 为空
     */
    public void onEmpty() {
        runOnUiThread(new Runnable() {
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
}
