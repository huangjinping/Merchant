package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.CreateHouseResponse;
import com.hdfex.merchantqrshow.bean.salesman.resource.CreateHouseResult;
import com.hdfex.merchantqrshow.mvp.contract.SalesmanContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class SalesmanPresenter extends SalesmanContract.Presenter {
    @Override
    public void requestPersonForbid(User user, String id,final String status) {
        OkHttpUtils.post()
                .addParams("flag", status)
                .addParams("token", user.getToken())
                .addParams("id", id)
                .url(NetConst.PERSON_FORBID).build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                getmMvpView().showProgress();
            }

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    Response result = GsonUtil.changeGsonToBean(response, Response.class);
                    getmMvpView().returnResult(status);
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });


    }
}
