package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.hdfex.merchantqrshow.bean.salesman.huabei.DurationPayment;
import com.hdfex.merchantqrshow.bean.salesman.huabei.DurationPaymentResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.InputHuabeiContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.order.activity.PlanAlipayActivity;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/8/15.
 * email : huangjinping@hdfex.com
 */

public class InputHuabeiPresenter extends InputHuabeiContract.Presenter {

    @Override
    public void requestDurationPayment(User user, String payAmount, String caseId, final int type) {
        OkHttpUtils
                .post()
                .url(NetConst.DURATION_PAYMENT)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("payAmount", payAmount)
                .addParams("caseId", caseId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showWebEirr(e);
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        if (getmMvpView() != null) {
                            getmMvpView().showProgress();
                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        if (getmMvpView() != null) {
                            getmMvpView().dismissProgress();
                        }
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                DurationPaymentResponse res = GsonUtil.changeGsonToBean(response, DurationPaymentResponse.class);
                                getmMvpView().returnDurationPayment(res.getResult());
                                if (SUBMIT == type) {
                                    DurationPayment result = res.getResult();
                                    if (!TextUtils.isEmpty(result.getRealLoanAmount())) {
                                        getmMvpView().submitScan();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onPlan(final Context context, final User user, String payAmount, String caseId) {
        OkHttpUtils
                .post()
                .url(NetConst.DURATION_PAYMENT)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("payAmount", payAmount)
                .addParams("caseId", caseId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showWebEirr(e);
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        if (getmMvpView() != null) {
                            getmMvpView().showProgress();
                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        if (getmMvpView() != null) {
                            getmMvpView().dismissProgress();
                        }
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                DurationPaymentResponse res = GsonUtil.changeGsonToBean(response, DurationPaymentResponse.class);
                                getmMvpView().returnDurationPayment(res.getResult());

                                PlanAlipayActivity.startAction(context, res.getResult());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
