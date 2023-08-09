package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.plan.PlanDetail;
import com.hdfex.merchantqrshow.bean.salesman.plan.PlanResponse;
import com.hdfex.merchantqrshow.mvp.contract.PlanDetailsContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/5/26.
 * email : huangjinping@hdfex.com
 */

public class PlanDetailsPresenter extends PlanDetailsContract.Persenter {


    @Override
    public void loadRepayDetail( User user, final String applyId) {
        OkHttpUtils
                .post()
                .url(NetConst.REPAY_DETAIL)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("applyId", applyId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        getmMvpView().dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        getmMvpView().showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showToast("服务异常");
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                PlanResponse planResponse = GsonUtil.changeGsonToBean(response, PlanResponse.class);
                                PlanDetail planDetail = planResponse.getResult();
                                getmMvpView().returnPlanDetail(planDetail);


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getmMvpView().showToast("服务异常");
                        }
                    }
                });

    }
}
