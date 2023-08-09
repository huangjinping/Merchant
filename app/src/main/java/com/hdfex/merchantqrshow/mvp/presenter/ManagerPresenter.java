package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.bean.manager.main.StoresListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.ManagerContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/6/6.
 * email : huangjinping@hdfex.com
 */
public class ManagerPresenter extends ManagerContract.Presenter {

    @Override
    public void loadStoresList(User user) {
        OkHttpUtils.post()
                .addParams("id", user.getBussinessId())
                .addParams("token", user.getToken())
                .url(NetConst.STORESLIST).build().execute(new StringCallback() {

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
                    StoresListResponse res = GsonUtil.changeGsonToBean(response, StoresListResponse.class);
                    getmMvpView().returnStroes(res);
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


