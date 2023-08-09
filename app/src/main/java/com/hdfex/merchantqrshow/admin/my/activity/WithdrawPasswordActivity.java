package com.hdfex.merchantqrshow.admin.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.widget.GridEditText;

/**
 * author Created by harrishuang on 2018/1/30.
 * email : huangjinping@hdfex.com
 */

public class WithdrawPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_desc;
    private GridEditText gt_pay_password;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_password);
        initView();
        intent = getIntent();
        gt_pay_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    Intent intent = getIntent();
                    intent.putExtra(IntentFlag.WITH_DRAW_PASSWORD, s.toString());
                    WithdrawConformPasswordActivity.startAction(WithdrawPasswordActivity.this, intent);

                }
            }
        });
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        txt_desc = (TextView) findViewById(R.id.txt_desc);
        gt_pay_password = (GridEditText) findViewById(R.id.gt_pay_password);
        tb_tv_titile.setText("验证身份");
        img_back.setOnClickListener(this);
    }

    public static void startAction(Context context, Intent intent) {
        intent.setClass(context, WithdrawPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }


}
