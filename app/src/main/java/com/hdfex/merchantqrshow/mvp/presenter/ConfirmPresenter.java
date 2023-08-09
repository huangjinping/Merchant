package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.PeriodAmountResponese;
import com.hdfex.merchantqrshow.mvp.contract.ConfirmorderContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by harrishuang on 2017/3/2.
 */

public class ConfirmPresenter extends ConfirmorderContract.Presenter {

    @Override
    public void calculateUpdate(User user, String applyAmount, String caseId) {
//        OkHttpUtils.post()
//                .url(NetConst.GETPERIODAMOUNT)
//                .addParams("token", user.getToken())
//                .addParams("id", user.getId())
//                .addParams("applyAmount", applyAmount)
//                .addParams("caseId", caseId)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e) {
//                        getmMvpView().showWebEirr(e);
//                    }
//
//                    @Override
//                    public void onBefore(Request request) {
//                        super.onBefore(request);
//                        getmMvpView().showProgress();
//                    }
//
//                    @Override
//                    public void onAfter() {
//                        super.onAfter();
//                        getmMvpView().dismissProgress();
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//
//
//                        try {
//                            boolean b = getmMvpView().checkResonse(response);
//                            if (b) {
//                                PeriodAmountResponese responese = GsonUtil.changeGsonToBean(response, PeriodAmountResponese.class);
//                                getmMvpView().returnPeriodAmount(responese.getResult().getPeriodAmount());
//                            }
//                        } catch (Exception e) {
//                            getmMvpView().showWebEirr(e);
//                            e.printStackTrace();
//                        }
//                    }
//                });

        OkHttpUtils.post()
                .url(NetConst.APP_QUERY_PERIODAMOUNT)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("applyAmount", applyAmount)
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
                        getmMvpView().showProgress();
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        getmMvpView().dismissProgress();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = getmMvpView().checkResonse(response);
                            if (b) {
                                PeriodAmountResponese responese = GsonUtil.changeGsonToBean(response, PeriodAmountResponese.class);
                                getmMvpView().returnPeriodAmount(responese.getResult().getPeriodAmount());
                                getmMvpView().returnPeriodAmountResult(responese.getResult());
                            }
                        } catch (Exception e) {
                            getmMvpView().showWebEirr(e);
                            e.printStackTrace();
                        }
                    }
                });
    }
}
