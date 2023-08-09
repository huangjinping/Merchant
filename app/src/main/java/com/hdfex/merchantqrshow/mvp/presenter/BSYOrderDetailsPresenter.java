package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.salesman.order.activity.BSYOrderDetailsActivity;
import com.hdfex.merchantqrshow.bean.salesman.bsy.BSYOrderDetailsResponse;
import com.hdfex.merchantqrshow.bean.salesman.bsy.BSYOrderItem;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.view.BSYOrderView;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by harrishuang on 2016/11/24.
 */

public class BSYOrderDetailsPresenter extends BasePresenter<BSYOrderView> {
    /**
     * 获取订单详情
     * @param apple_id
     */
    public  void  loadDetails(User user, String apple_id){

        OkHttpUtils
                .post()
                .url(NetConst.BSY_ORDER_DETAILS)
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("orderId",apple_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        BSYOrderDetailsPresenter.this.getmMvpView().dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        BSYOrderDetailsPresenter.this.getmMvpView().showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {

                            BSYOrderDetailsActivity activity= (BSYOrderDetailsActivity) BSYOrderDetailsPresenter.this.getmMvpView();
                            boolean b = activity.checkResonse(response);
                            if (b) {
                                BSYOrderDetailsResponse orderDetailsResponse = GsonUtil.changeGsonToBean(response, BSYOrderDetailsResponse.class);
                                BSYOrderItem orderDetails = orderDetailsResponse.getResult();


                                BSYOrderDetailsPresenter.this.getmMvpView().onDetailsResult(orderDetails);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }
}
