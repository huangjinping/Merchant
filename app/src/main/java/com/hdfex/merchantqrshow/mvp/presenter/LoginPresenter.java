package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.view.LoginView;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.log.LogUtil;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by harrishuang on 2016/11/15.
 */

public class LoginPresenter extends BasePresenter<LoginView> {


    public void login(final String username, String password) {
        OkHttpUtils
                .post()
                .url(NetConst.LOGIN)
                .addParams("account", username)
                .addParams("password", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        LoginPresenter.this.getmMvpView().dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        LoginPresenter.this.getmMvpView().showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        LogUtil.d("okhttp",e.getMessage());
                        getmMvpView().showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        LoginPresenter.this.getmMvpView().login(response,username);
                    }
                });
    }

}
