package com.hdfex.merchantqrshow.salesman.appointment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.appointment.DataErrorItem;
import com.hdfex.merchantqrshow.bean.salesman.appointment.DataErrorResponse;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpDetailItem;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpDetailResponse;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpMaterialDetailQueryResponse;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpMaterialDetailQueryResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.appointment.adapter.DataErrorAdapter;
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

public class DataErrorActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private RecyclerView rec_follow_details;
    private DataErrorAdapter adapter;
    private List<DataErrorItem> dataList;
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
        Intent intent = new Intent(context, DataErrorActivity.class);
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
        user = UserManager.getUser(this);
        dataList = new ArrayList<>();
        adapter = new DataErrorAdapter(dataList, type);
        rec_follow_details.setLayoutManager(new LinearLayoutManager(this));
        rec_follow_details.setAdapter(adapter);
        loadData(LoadMode.NOMAL);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        adapter.setItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
//                00-	电核异常
//                01-	资料异常

                DataErrorItem backOrder = dataList.get(postion);
                if ("00".equals(type)) {
                    /**
                     *
                     */
                    loadFollowUpDetailQuery(backOrder.getApplyId(), backOrder.getExamineId());
                } else if ("01".equals(type)) {
                    /**
                     *
                     */
                    loadFollowUpMaterialDetailQuery(backOrder.getApplyId(), backOrder.getExamineId());
                }
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

    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        rec_follow_details = (RecyclerView) findViewById(R.id.rec_follow_details);
        tb_tv_titile.setText("资料电核异常列表");
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
     * 下载数据
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
        String url = NetConst.DATA_ERROR_LIST;
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
                                DataErrorResponse dataErrorResponse = GsonUtil.changeGsonToBean(response, DataErrorResponse.class);
                                if (loadMode != LoadMode.UP_REFRESH) {
                                    dataList.clear();
                                }
                                if (dataErrorResponse.getResult() != null) {
                                    ArrayList<DataErrorItem> orderItemslist = (ArrayList<DataErrorItem>) dataErrorResponse.getResult();
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
        adapter.notifyDataSetChanged();
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


    /**
     * 4.70.11.	客户跟进详情（电核）
     */
    private void loadFollowUpDetailQuery(final String applyId, String examineId) {
        String url = NetConst.FOLLOW_UPDATE_DETAIL_QUERY;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("applyId", applyId)
                .addParams("examineId", examineId);
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
                                FollowUpDetailResponse followUpDetailResponse = GsonUtil.changeGsonToBean(response, FollowUpDetailResponse.class);
                                FollowUpDetailItem result = followUpDetailResponse.getResult();
                                result.setApplyId(applyId);
                                FollowActivity.startAction(DataErrorActivity.this,followUpDetailResponse.getResult());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 4.70.12.	客户跟进详情（其他资料）
     */
    private void loadFollowUpMaterialDetailQuery(final String applyId, String examineId) {
        String url = NetConst.FOLLOW_UP_MATERICAL_DETAIL_QUERY;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("applyId", applyId)
                .addParams("examineId", examineId);
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
                                FollowUpMaterialDetailQueryResponse detailQueryResponse = GsonUtil.changeGsonToBean(response, FollowUpMaterialDetailQueryResponse.class);
                                FollowUpMaterialDetailQueryResult result = detailQueryResponse.getResult();
                                result.setApplyId(applyId);
                                FollowActivity.startAction(DataErrorActivity.this,detailQueryResponse.getResult());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        loadData(LoadMode.NOMAL);
    }
}
