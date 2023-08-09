package com.hdfex.merchantqrshow.admin.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfo;
import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.my.activity.AuthenticationActivity;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2018/1/15.
 * email : huangjinping@hdfex.com
 */

public class LoginPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private LinearLayout layout_forgot_password;
    private LinearLayout layout_change_password;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);
        user = UserManager.getUser(this);
        initView();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_change_password:
                loadIdCard(user, this);
                break;
            case R.id.layout_forgot_password:
                ForgotPasswordActivity.startAction(this);
                break;

        }

    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        layout_forgot_password = (LinearLayout) findViewById(R.id.layout_forgot_password);
        layout_change_password = (LinearLayout) findViewById(R.id.layout_change_password);
        img_back.setOnClickListener(this);
        layout_change_password.setOnClickListener(this);
        layout_forgot_password.setOnClickListener(this);
        tb_tv_titile.setText("登录密码设置");
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, LoginPasswordActivity.class);
        context.startActivity(intent);

    }


    /**
     * 资金额问题
     *
     * @param user
     * @param context
     */
    public void loadIdCard(User user, final Context context) {
        showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .url(NetConst.ID_CARD_QUERY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast("服务异常");
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (checkResonse(response)) {
                        AdvancedInfoResponse house = GsonUtil.changeGsonToBean(response, AdvancedInfoResponse.class);
                        /**
                         *
                         *
                         * 1.未认证
                         *
                         * 2.已认证
                         *
                         */
                        if (house.getResult() != null) {
                            AdvancedInfo result = house.getResult();
                            if (AdvancedInfo.AUTH_STATUS_NO.equals(result.getAuthStatus())) {
                                Intent intent = getIntent();
                                intent.putExtra(IntentFlag.INTENT_NAME, IntentFlag.UPDATE_LOGIN_PASSWORD);
                                AuthenticationActivity.startAction(context, intent);

                            } else {
                                Intent intent = new Intent();
                                intent.putExtra(IntentFlag.ID_CARD, result.getIdNo());
                                intent.putExtra(IntentFlag.INTENT_NAME, IntentFlag.UPDATE_LOGIN_PASSWORD);
                                AdminAuthenticationActivity.startAction(LoginPasswordActivity.this, intent);
                            }
                        }


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
