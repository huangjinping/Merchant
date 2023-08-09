package com.hdfex.merchantqrshow.admin.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.widget.GridEditText;

/**
 * author Created by harrishuang on 2018/1/15.
 * email : huangjinping@hdfex.com
 */

public class AdminAuthenticationActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_desc;
    private GridEditText gt_pay_password;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_authentication);
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
                if (s.length() == 4){
                    String id_card = intent.getStringExtra(IntentFlag.ID_CARD);
                        if (!TextUtils.isEmpty(id_card)){
                            if (id_card.toUpperCase().endsWith(s.toString().toUpperCase())){
                                onSubmit(s.toString());
                            }else {
                                showToast("与认证信息不匹配，请重新输入");
                            }
                        }
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
        intent.setClass(context, AdminAuthenticationActivity.class);
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

    /**
     * 提交数据
     */
    private void onSubmit(String param) {
        String intentName = intent.getStringExtra(IntentFlag.INTENT_NAME);
        if (IntentFlag.FORGOT_LOGIN_PASSWORD.equals(intentName)) {
            ResetPasswordActivity.startAction(this);
        } else if (IntentFlag.UPDATE_LOGIN_PASSWORD.equals(intentName)) {
            UpdatePasswordActivity.startAction(this);
        }else  if (IntentFlag.SETTING_WITHDRAW_PASSWORD.equals(intentName)){
            WithdrawPasswordActivity.startAction(this,intent);

        }
    }
}
