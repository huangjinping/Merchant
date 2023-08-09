package com.hdfex.merchantqrshow.salesman.customer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.manager.team.Person;
import com.hdfex.merchantqrshow.bean.manager.team.RegionResult;
import com.hdfex.merchantqrshow.bean.salesman.appointment.TurnRequest;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.BizListContract;
import com.hdfex.merchantqrshow.mvp.presenter.BizListContractPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.customer.adapter.BizAdapter;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.MultiStateView;
import com.hdfex.sdk.pullrefreshlayout.BGANormalRefreshViewHolder;
import com.hdfex.sdk.pullrefreshlayout.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import rx.Subscriber;


/**
 * author Created by harrishuang on 2017/9/11.
 * email : huangjinping@hdfex.com
 */

public class BIZListActivity extends BaseActivity implements View.OnClickListener, BizListContract.View {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ExpandableListView exp_manager;
    private BizAdapter adapter;
    private List<RegionResult> dataList;
    private BizListContract.Presenter presenter;
    private BGARefreshLayout layout_refresh;
    private MultiStateView multiStateView;
    private User user;
    private TurnRequest turnReqeust;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bizlist);
        initView();
        Intent intent = getIntent();
        if (intent.getSerializableExtra(TurnRequest.class.getSimpleName()) != null) {
            turnReqeust = (TurnRequest) intent.getSerializableExtra(TurnRequest.class.getSimpleName());
        }


        presenter = new BizListContractPresenter();
        presenter.attachView(this);
        user = UserManager.getUser(this);
        presenter.initData(this, user);
        dataList = new ArrayList<>();
        adapter = new BizAdapter(this, dataList);
        exp_manager.setAdapter(adapter);
        presenter.getRegionList(user, LoadMode.NOMAL);
        BGANormalRefreshViewHolder holder = new BGANormalRefreshViewHolder(this, true);
        layout_refresh.setRefreshViewHolder(holder);
        layout_refresh.setIsShowLoadingMoreView(true);
        layout_refresh.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                presenter.getRegionList(user, LoadMode.PULL_REFRSH);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                presenter.getRegionList(user, LoadMode.UP_REFRESH);
                return false;
            }
        });


        EventRxBus.getInstance().register(IntentFlag.TURN_ORDER).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

                if (turnReqeust != null) {
                    Person person = (Person) o;
                    showTurnOrderAlert(person);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        exp_manager = (ExpandableListView) findViewById(R.id.exp_manager);
        exp_manager.setGroupIndicator(null);
        tb_tv_titile.setText("选择接单人");
        layout_refresh = (BGARefreshLayout) findViewById(R.id.layout_refresh);
        multiStateView = (MultiStateView) findViewById(R.id.multiStateView);
    }

    public static void startAction(Context context, TurnRequest turnReqeust) {
        Intent intent = new Intent(context, BIZListActivity.class);
        intent.putExtra(TurnRequest.class.getSimpleName(), turnReqeust);
        context.startActivity(intent);
    }

    public void onEmpty() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.getRegionList(user, LoadMode.NOMAL);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void returnListResponse(List<RegionResult> result, LoadMode loadMode) {
        if (loadMode != LoadMode.UP_REFRESH) {
            dataList.clear();
        }
        dataList.addAll(result);
        if (dataList.size() == 0) {
            onEmpty();
        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void returnRefresh() {
        layout_refresh.endLoadingMore();
        layout_refresh.endRefreshing();
    }

    /**
     * 准单给某人
     *
     * @param person
     */
    private void showTurnOrderAlert(final Person person) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("转单给" + person.getName())
                .withTitleColor("#FFFFFF")
                .withMessage("注：如果对方不接受，该转发将失败")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(this.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
                .withButton1Text("取消")
                .withButton2Text("确认")
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        finish();


                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        onTurnOrder(person);
                    }
                })
                .show();
    }


    /**
     * 转单操作中
     */
    private void onTurnOrder(Person person) {
        showProgress();
        OkHttpUtils.post().addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("subscribeId", turnReqeust.getSubscribeId())
                .addParams("turnUserId", person.getId())
                .addParams("turnUserName", person.getName())
                .addParams("turnUserPhone", person.getPhoneNo())
                .addParams("turnDesc", turnReqeust.getTurnDesc())
                .url(NetConst.BUSSINESS_TURN_ORDER).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (checkResonse(response)) {
                    Response house = GsonUtil.changeGsonToBean(response, Response.class);
                    showToast("转单成功");
                    EventRxBus.getInstance().post(IntentFlag.TURN_DESC_KEY_UPDATE,response);
                    finish();
                }else {
                    showToast("转单失败");
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissProgress();
            }
        });
    }

}
