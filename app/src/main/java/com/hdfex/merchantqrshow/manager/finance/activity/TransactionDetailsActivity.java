package com.hdfex.merchantqrshow.manager.finance.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;

/**
 * author Created by harrishuang on 2017/5/31.
 * email : huangjinping@hdfex.com
 *
 * 交易详情
 */

public class TransactionDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactiondetails);
        initView();

    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setText(R.string.transaction_details);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    public static void startAction(Context context) {
        Intent intent=new Intent(context,TransactionDetailsActivity.class);
        context.startActivity(intent);
    }
}
