package com.hdfex.merchantqrshow.salesman.order.activity;

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
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.plan.PlanDetail;
import com.hdfex.merchantqrshow.mvp.contract.PlanDetailsContract;
import com.hdfex.merchantqrshow.mvp.presenter.PlanDetailsPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.order.adapter.PlanDetailsAdapter;
import com.hdfex.merchantqrshow.utils.UserManager;

/**
 * author Created by harrishuang on 2017/5/25.
 * email : huangjinping@hdfex.com
 */

public class PlanDetailsActivity extends BaseActivity implements View.OnClickListener, PlanDetailsContract.View {

    private PlanDetailsAdapter adapter;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private RecyclerView rec_details;
    private PlanDetailsContract.Persenter persenter;
    private User user;
    private String applyId;
    private PlanDetail planDetail;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plandetails);
        initView();
        intent = getIntent();
        persenter = new PlanDetailsPresenter();
        persenter.attachView(this);
        user = UserManager.getUser(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rec_details.setLayoutManager(linearLayoutManager);
        planDetail = new PlanDetail();
        adapter = new PlanDetailsAdapter(planDetail);
        rec_details.setAdapter(adapter);
        applyId = intent.getStringExtra(IntentFlag.APPLYID);
        if (intent.getSerializableExtra(IntentFlag.INTENT_NAME) != null) {
            planDetail = (PlanDetail) intent.getSerializableExtra(IntentFlag.INTENT_NAME);
            adapter.setPlanDetail(planDetail);
            return;
        }
    }


    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        rec_details = (RecyclerView) findViewById(R.id.rec_details);
        rec_details.setOnClickListener(this);
        tb_tv_titile.setText(R.string.plan_details);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

        }
    }

    public static void startAction(Context context, String applyId) {
        Intent intent = new Intent(context, PlanDetailsActivity.class);
        intent.putExtra(IntentFlag.APPLYID, applyId);
        context.startActivity(intent);
    }

    public static void startActionModel(Context context, String applyId, PlanDetail planDetail) {
        Intent intent = new Intent(context, PlanDetailsActivity.class);
        intent.putExtra(IntentFlag.APPLYID, applyId);
        intent.putExtra(IntentFlag.INTENT_NAME, planDetail);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        persenter.detachView();
    }

    @Override
    public void returnPlanDetail(PlanDetail planDetail) {
        this.planDetail = planDetail;
        adapter.notifyDataSetChanged();
    }
}
