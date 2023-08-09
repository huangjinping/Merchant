package com.hdfex.merchantqrshow.admin.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.widget.GridEditText;

/**
 * author Created by harrishuang on 2018/1/15.
 * email : huangjinping@hdfex.com
 */

public class PayPasswordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private GridEditText gt_pay_password;
    private TextView txt_desc;
    public  static final String  STATUS_CREATE="STATUS_CREATE";
    public  static final String  STATUS_COMPLATE="STATUS_COMPLATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypassword);
        initView();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        gt_pay_password = (GridEditText) findViewById(R.id.gt_pay_password);
        tb_tv_titile.setText("设置提现密码");
        txt_desc = (TextView) findViewById(R.id.txt_desc);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    public static void startAction(Context context,String status) {
        Intent intent = new Intent(context, PayPasswordActivity.class);
        intent.putExtra("status",status);
        context.startActivity(intent);
    }

}
