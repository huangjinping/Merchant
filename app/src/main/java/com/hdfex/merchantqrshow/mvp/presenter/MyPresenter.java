package com.hdfex.merchantqrshow.mvp.presenter;

import android.support.v4.app.FragmentActivity;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageListResponse;
import com.hdfex.merchantqrshow.bean.salesman.taobao.RedPackageAccountInfoResponse;
import com.hdfex.merchantqrshow.comm.taobao.TaobaoAuthActivity;
import com.hdfex.merchantqrshow.mvp.contract.MyContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class MyPresenter extends MyContract.Presenter {

    @Override
    public void loadRedPackage(User user, final FragmentActivity activity) {
//        OkHttpUtils
//                .post()
//                .addParams("id", user.getId())
//                .addParams("token", user.getToken())
//                .tag(activity)
//                .url(NetConst.RED_PACKAGE_LIST)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e) {
//                        getmMvpView().showToast("数据异常！");
//                    }
//                    @Override
//                    public void onResponse(String response) {
//                        if (getmMvpView().checkResonse(response)) {
//                            RedPackageListResponse house = GsonUtil.changeGsonToBean(response, RedPackageListResponse.class);
//                            if (house.getResult() != null) {
//                                TaobaoAuthActivity.startAction(activity, house.getResult());
//                            } else {
//                                getmMvpView().showToast("数据异常！");
//                            }
//                        }
//                    }
//                    @Override
//                    public void onAfter() {
//                        super.onAfter();
//                        getmMvpView().dismissProgress();
//                    }
//                });

        OkHttpUtils
                .post()
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .tag(activity)
                .url(NetConst.RED_PACKAGEACCOUNT_INFO)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        getmMvpView().showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showToast("数据异常！");
                    }
                    @Override
                    public void onResponse(String response) {
                        if (getmMvpView().checkResonse(response)) {
                            RedPackageAccountInfoResponse house = GsonUtil.changeGsonToBean(response, RedPackageAccountInfoResponse.class);
                            if (house.getResult() != null) {
                                TaobaoAuthActivity.startAction(activity, house.getResult());
                            } else {
                                getmMvpView().showToast("数据异常！");
                            }
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
