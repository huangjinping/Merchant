package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Intent;
import android.view.View;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.HuabeiContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.my.activity.LoginActivity;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/6/19.
 * email : huangjinping@hdfex.com
 */

public class HuabeiPresenter extends HuabeiContract.Presenter {


    @Override
    public void startAnimation(View view) {

    }

    @Override
    public void scanPay(User user, String orderId, String paycode) {
        OkHttpUtils
                .post()
                .url(NetConst.ALIPAORDER_SANPAY)

                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("orderId", orderId)
                .addParams("authCode", paycode)
                .build()
                .connTimeOut(60 * 1000)
                .readTimeOut(60 * 1000)
                .writeTimeOut(60 * 1000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showWebEirr(e);
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        getmMvpView().showLoading();
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        getmMvpView().dismissLoading();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            Response res = GsonUtil.changeGsonToBean(response, Response.class);
                            switch (res.getCode()) {
                                case 0:
                                    getmMvpView().returnSanpay(res);
                                    break;
                                case -101:
                                    /**
                                     * 超时
                                     */
                                    getmMvpView().returnTimeout();
                                    break;
                                case -100:
                                    /**
                                     * 错误
                                     */
                                    getmMvpView().returnPayEirr(res);
                                    break;
                                default:
                                    getmMvpView().showToast(res.getMessage());
                                    break;
                            }
                        } catch (Exception e) {
                            getmMvpView().showToast("支付失败！！");
                            e.printStackTrace();
                        }
                    }
                });
    }
}
