package com.hdfex.merchantqrshow.admin.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.DateUtils;

import okhttp3.Call;

/**
 * 忘记密码页面
 * author Created by harrishuang on 2018/1/16.
 * email : huangjinping@hdfex.com
 */

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private EditText edt_login_name;
    private Button btn_submit;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        user = UserManager.getUser(this);
        initView();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        edt_login_name = (EditText) findViewById(R.id.edt_login_name);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        img_back.setOnClickListener(this);
        tb_tv_titile.setText("忘记登录密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void submit() {
        // validate
        String name = edt_login_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入登录账号");
            return;
        }

        // TODO 判断是不是身份证上传认证了
        loadIdCard(user, this);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        context.startActivity(intent);
    }

    public static void startAction(Context context, Intent intent) {
        intent.setClass(context, ForgotPasswordActivity.class);
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

                                showUnAuthAlert(context, result);

                            } else {
                                Intent intent = new Intent();
                                intent.putExtra(IntentFlag.INTENT_NAME, IntentFlag.FORGOT_LOGIN_PASSWORD);
                                intent.putExtra(IntentFlag.ID_CARD, result.getIdNo());
                                AdminAuthenticationActivity.startAction(ForgotPasswordActivity.this, intent);
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


    /**
     * @param context
     * @param result
     */
    private void showUnAuthAlert(final Context context, final AdvancedInfo result) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("应《中华人民共和国网络安全法》要求，使用互联网服务需进行账号实名认证，截止日期" + result.getEndDate() + "，到期未完成实名认证的管家将无法继续使用下单功能。")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(context.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
                .withButton1Text("暂不认证")
                .withButton2Text("去认证")
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        boolean compareNowDate = DateUtils.compareNowDate(result.getEndDate());
                        if (compareNowDate) {

                        }
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        Intent intent = getIntent();
                        intent.putExtra(IntentFlag.INTENT_NAME, IntentFlag.FORGOT_LOGIN_PASSWORD);
                        AuthenticationActivity.startAction(context, intent);
                    }
                })
                .show();
    }

}

