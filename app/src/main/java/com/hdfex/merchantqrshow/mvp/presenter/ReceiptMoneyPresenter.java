package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.ReceiptMoneyContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2018/1/4.
 * email : huangjinping@hdfex.com
 */

public class ReceiptMoneyPresenter extends ReceiptMoneyContract.Presenter {


    @Override
    public void submitData(Context context,final Map<String, String> params) {


        System.out.println(params.toString());

        OkHttpUtils.post().params(params)
                .url(NetConst.SCAN_PAY_FOROL).build()
                .connTimeOut(60 * 1000)
                .readTimeOut(60 * 1000)
                .writeTimeOut(60 * 1000).execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {



                try {
                    if (getmMvpView().checkResonse(response)) {
                        try {
                            Response res = GsonUtil.changeGsonToBean(response, Response.class);
                            User user = new User();
                            user.setId(params.get("id"));
                            user.setToken(params.get("token"));

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
                        User user = new User();
                        user.setId(params.get("id"));
                        user.setToken(params.get("token"));
                    }
                } catch (Exception e) {
                    getmMvpView().showToast("支付失败！");
                    e.printStackTrace();
                }

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
        });
    }
}
