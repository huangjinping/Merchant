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
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.RoleUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.GridEditText;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2018/1/30.
 * email : huangjinping@hdfex.com
 */

public class WithdrawConformPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_desc;
    private GridEditText gt_pay_password;
    private Intent intent;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_password);
        initView();
        user= UserManager.getUser(this);
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
                    String password = intent.getStringExtra(IntentFlag.WITH_DRAW_PASSWORD);
                    if (password.equals(s.toString())) {
                        submitWithdrawPassword(s.toString());
                        return;
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
        intent.setClass(context, WithdrawConformPasswordActivity.class);
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
     * @param withdrawalPwd
     */
    private void submitWithdrawPassword(String withdrawalPwd) {
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("withdrawalPwd", withdrawalPwd)
                .url(NetConst.SET_WITH_DRAWAL_PWD).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast("服务异常");
            }

            @Override
            public void onResponse(String response) {

                try {
                    if (checkResonse(response)) {
                        showToast("设置成功");
                        Intent intent=new Intent(WithdrawConformPasswordActivity.this,AccountSecurityActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    showToast("数据异常");
                    e.printStackTrace();
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
