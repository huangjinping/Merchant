package com.hdfex.merchantqrshow.admin.business.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.admin.business.AlipayOrderDetail;

/**
 * author Created by harrishuang on 2017/12/26.
 * email : huangjinping@hdfex.com
 */

public class AdminOrderDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_commodityName;
    private TextView txt_applyAmount;
    private TextView txt_applyStatus;
    private TextView txt_applyDate;
    private TextView txt_loanAmount;
    private TextView txt_applyDuration;
    private TextView txt_redpacketAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminorderdetails);
        initView();
        tb_tv_titile.setText("订单详情");
        Intent intent = getIntent();
        if (intent.getSerializableExtra("result") != null) {
            AlipayOrderDetail result = (AlipayOrderDetail) intent.getSerializableExtra("result");
            setViewByData(result);
        }

    }

    /**
     * 设置View
     *
     * @param result
     */
    private void setViewByData(AlipayOrderDetail result) {
        txt_applyAmount.setText(result.getApplyAmount());
        txt_applyDate.setText(result.getApplyDate());
        txt_applyDuration.setText(result.getApplyDuration());
        txt_applyStatus.setText(result.getApplyStatus());
        txt_commodityName.setText(result.getCommodityName());
        txt_redpacketAmt.setText(result.getRedpacketAmt());
        txt_loanAmount.setText(result.getLoanAmount());
    }


    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        img_back.setOnClickListener(this);

        txt_commodityName = (TextView) findViewById(R.id.txt_commodityName);
        txt_commodityName.setOnClickListener(this);
        txt_applyAmount = (TextView) findViewById(R.id.txt_applyAmount);
        txt_applyAmount.setOnClickListener(this);
        txt_applyStatus = (TextView) findViewById(R.id.txt_applyStatus);
        txt_applyStatus.setOnClickListener(this);
        txt_applyDate = (TextView) findViewById(R.id.txt_applyDate);
        txt_applyDate.setOnClickListener(this);
        txt_loanAmount = (TextView) findViewById(R.id.txt_loanAmount);
        txt_loanAmount.setOnClickListener(this);
        txt_applyDuration = (TextView) findViewById(R.id.txt_applyDuration);
        txt_applyDuration.setOnClickListener(this);
        txt_redpacketAmt = (TextView) findViewById(R.id.txt_redpacketAmt);
        txt_redpacketAmt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    public static void startAction(Context context, AlipayOrderDetail result) {
        Intent intent = new Intent(context, AdminOrderDetailsActivity.class);
        intent.putExtra("result", result);
        context.startActivity(intent);
    }
}
