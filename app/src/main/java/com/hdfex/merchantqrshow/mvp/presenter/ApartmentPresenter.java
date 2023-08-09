package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.ApartmentResponse;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.view.ApartmentView;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;

/**
 * Created by harrishuang on 2017/3/20.
 */

public class ApartmentPresenter extends BasePresenter<ApartmentView> {

    public void requestApartmentList(User user) {
        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("bussinessId", user.getBussinessId())
                .url(NetConst.GET_APARTMENT_LIST).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }
            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    ApartmentResponse house = GsonUtil.changeGsonToBean(response, ApartmentResponse.class);
                    getmMvpView().returnAparmentList(house.getResult());
                    ;
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
