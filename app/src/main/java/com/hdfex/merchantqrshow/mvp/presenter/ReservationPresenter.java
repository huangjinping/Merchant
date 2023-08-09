package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.appointment.BussinessSubscribeDetailResponse;
import com.hdfex.merchantqrshow.bean.salesman.appointment.SubscribeItem;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.ReservationContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.appointment.activity.AppointmentActivity;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2017/9/13.
 * email : huangjinping@hdfex.com
 */

public class ReservationPresenter extends ReservationContract.Presenter {

    @Override
    public void requestupdateBussSubscribeStatus(User user, SubscribeItem item, String status) {
        getmMvpView().showProgress();
        OkHttpUtils.post().addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("subscribeId", item.getSubscribeId())
                .addParams("type", status)
                .url(NetConst.UPDATE_BUSS_SUBSCRIBE_STATUS).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    Response response1 = GsonUtil.changeGsonToBean(response, Response.class);

                    getmMvpView().updateList();

                }
            }


            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }

    @Override
    public void getAppointmentDetails(final Context context, User user,final SubscribeItem item) {

        getmMvpView().showProgress();
        OkHttpUtils.post().addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("subscribeId", item.getSubscribeId())
                .url(NetConst.BUSSINESS_SUBSCRIBE_DETAIL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (getmMvpView().checkResonse(response)) {
                        BussinessSubscribeDetailResponse subscribeDetailResponse = GsonUtil.changeGsonToBean(response, BussinessSubscribeDetailResponse.class);
                        subscribeDetailResponse.getResult().setSubscribeId(item.getSubscribeId());
                        AppointmentActivity.startAction(context, subscribeDetailResponse.getResult());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }

    @Override
    public void accpetOrder(final Context context, final User user,final SubscribeItem item) {
        getmMvpView().showProgress();
        OkHttpUtils.post().addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("subscribeId", item.getSubscribeId())
                .addParams("type", "00")
                .url(NetConst.UPDATE_BUSS_SUBSCRIBE_STATUS).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
//                    Response house = GsonUtil.changeGsonToBean(response, Response.class);
                    getmMvpView().updateList();
                    getAppointmentDetails(context,user,item);
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }

    @Override
    public void refuseOrder(Context context, User user, SubscribeItem item) {
        getmMvpView().showProgress();
        OkHttpUtils.post().addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("subscribeId", item.getSubscribeId())
                .addParams("type", item.getRefuseType())
                .url(NetConst.UPDATE_BUSS_SUBSCRIBE_STATUS).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
//                    Response house = GsonUtil.changeGsonToBean(response, Response.class);
                    getmMvpView().updateList();

                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }

    @Override
    public void pushOrder(final Context context,final User user,final SubscribeItem item) {
        getmMvpView().showProgress();
        OkHttpUtils.post().addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("subscribeId", item.getSubscribeId())
                .addParams("type","00")
                .url(NetConst.APP_DISCOVERY_RESHORDER).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
//                    Response house = GsonUtil.changeGsonToBean(response, Response.class);
                    getmMvpView().updateList();
                    getAppointmentDetails(context,user,item);
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
