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
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.RegexUtils;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;

/**
 * 重置登录密码
 * author Created by harrishuang on 2018/1/16.
 * email : huangjinping@hdfex.com
 */

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private EditText edt_new_password;
    private EditText edt_conform_password;
    private Button btn_submit;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        user = UserManager.getUser(this);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }

    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        edt_new_password = (EditText) findViewById(R.id.edt_new_password);
        edt_conform_password = (EditText) findViewById(R.id.edt_conform_password);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        img_back.setOnClickListener(this);
        tb_tv_titile.setText("重置登录密码");
    }

    private void submit() {
        // validate
        String newPassword = edt_new_password.getText().toString().trim();
        if (TextUtils.isEmpty(newPassword)) {
            showToast("请输入6-32位数字和字母的组合");
            return;
        }

        String conformPassword = edt_conform_password.getText().toString().trim();
        if (TextUtils.isEmpty(conformPassword)) {
            showToast("请确认输入的新密码");
            return;
        }

        if (!RegexUtils.user_password(newPassword)) {
            showToast("请输入6-32位数字字母");
            return;
        }

        if (!newPassword.equals(conformPassword)) {
            showToast("确认密码与设定密码不相同");
            return;
        }
        // TODO validate success, do something
        showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("modifyType","1")
                .addParams("newPassWord",conformPassword)
                .url(NetConst.MODIFY_PASSWORD).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast("服务异常");
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (checkResonse(response)) {
//                        AdvancedInfoResponse house = GsonUtil.changeGsonToBean(response, AdvancedInfoResponse.class);
                        logout();
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

    public static void startAction(Context context) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        context.startActivity(intent);
    }
}
