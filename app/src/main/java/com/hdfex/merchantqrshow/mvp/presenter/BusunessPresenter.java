package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.manager.business.BusinessForCurOrderResponse;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessForHouseResponse;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessForTotalOrderResponse;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessForWarnResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.BussinessContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class BusunessPresenter extends BussinessContract.Presenter {


    @Override
    public void loadData(Context context) {
        User user = UserManager.getUser(context);
        getBusinessForWarn(user,context);
        getBusinessForCurOrder(user,context);
        getBusinessForTotalOrder(user,context);
        getBusinessForHouse(user,context);
        getmMvpView().returnBusinessName(user.getCurrentBussinessName());

    }

    /**
     * @param
     * @param user
     * @param context
     */
    public void getBusinessForWarn(User user, Context context) {

        OkHttpUtils.post().addParams("id", user.getCurrentBussinessId()).addParams("token", user.getToken()).url(NetConst.BUSINESS_FORWARN).tag(context).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                try {
                    getmMvpView().showToast(e.getMessage());

                }catch (Exception e1){
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    BusinessForWarnResponse forWarnResponse = GsonUtil.changeGsonToBean(response, BusinessForWarnResponse.class);
                    getmMvpView().returnBusinessForWarn(forWarnResponse.getResult());
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }

    /**
     * @param
     * @param user
     * @param context
     */
    public void getBusinessForCurOrder(User user, Context context) {

        OkHttpUtils.post().addParams("id", user.getCurrentBussinessId()).addParams("token", user.getToken()).url(NetConst.BUSINESS_FORCURORDER).tag(context).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                try {
                    getmMvpView().showToast(e.getMessage());

                }catch (Exception e1){
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    BusinessForCurOrderResponse orderResponse = GsonUtil.changeGsonToBean(response, BusinessForCurOrderResponse.class);
                    getmMvpView().returnCurOrder(orderResponse.getResult());

                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }

    /**
     * @param
     * @param user
     * @param context
     */
    public void getBusinessForTotalOrder(User user, Context context) {

        OkHttpUtils.post().addParams("id", user.getCurrentBussinessId()).addParams("token", user.getToken()).url(NetConst.BUSINESS_FORTOTALORDER).tag(context).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                try {
                    getmMvpView().showToast(e.getMessage());

                }catch (Exception e1){
                    e.printStackTrace();
                }            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    BusinessForTotalOrderResponse totalOrderResponse = GsonUtil.changeGsonToBean(response, BusinessForTotalOrderResponse.class);
                    getmMvpView().returnTotalOrder(totalOrderResponse.getResult());
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }

    /**
     * @param
     * @param user
     * @param context
     */
    public void getBusinessForHouse(User user, Context context) {

        OkHttpUtils.post().addParams("id", user.getCurrentBussinessId()).addParams("token", user.getToken()).url(NetConst.BUSINESS_FORHOUSE).tag(context).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                try {
                    getmMvpView().showToast(e.getMessage());

                }catch (Exception e1){
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    BusinessForHouseResponse forHouseResponse = GsonUtil.changeGsonToBean(response, BusinessForHouseResponse.class);
                    getmMvpView().returnforHouse(forHouseResponse.getResult());
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
