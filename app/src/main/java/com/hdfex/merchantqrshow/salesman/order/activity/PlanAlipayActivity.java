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
import com.hdfex.merchantqrshow.bean.salesman.huabei.AlipayPlan;
import com.hdfex.merchantqrshow.bean.salesman.huabei.DurationPayment;
import com.hdfex.merchantqrshow.salesman.order.adapter.PlanAlipayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/8/14.
 * email : huangjinping@hdfex.com
 */

public class PlanAlipayActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private RecyclerView rec_plandetails;
    private PlanAlipayAdapter mAapter;
    private List<AlipayPlan> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planalipay);
        initView();
        dataList = new ArrayList<>();

        mAapter = new PlanAlipayAdapter(dataList);
        rec_plandetails.setLayoutManager(new LinearLayoutManager(this));
        rec_plandetails.setAdapter(mAapter);
        Intent intent = getIntent();
        if (intent.getSerializableExtra(DurationPayment.class.getSimpleName()) != null) {
            DurationPayment result = (DurationPayment) intent.getSerializableExtra(DurationPayment.class.getSimpleName());
            if (result.getDurationList() != null) {
                dataList.addAll(result.getDurationList());
                mAapter.notifyDataSetChanged();
            }
        }
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        rec_plandetails = (RecyclerView) findViewById(R.id.rec_plandetails);
        img_back.setOnClickListener(this);
        tb_tv_titile.setText("还款计划");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }


    public static void startAction(Context context, DurationPayment result) {
        Intent intent = new Intent(context, PlanAlipayActivity.class);
        intent.putExtra(DurationPayment.class.getSimpleName(), result);
        context.startActivity(intent);

    }
}
