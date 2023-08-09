package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hdfex.merchantqrshow.apliySale.main.activity.QRCodeActivity;
import com.hdfex.merchantqrshow.bean.apliysale.QRCodeResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodity.PreSubmitOrder;
import com.hdfex.merchantqrshow.bean.salesman.commodity.PreSubmitOrderResponse;
import com.hdfex.merchantqrshow.bean.salesman.huabei.BussinessCaseResponse;
import com.hdfex.merchantqrshow.bean.salesman.huabei.HuabeiRequest;
import com.hdfex.merchantqrshow.bean.salesman.huabei.PayOrderQueryResponse;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.ApliyMainContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.activity.ConfirmCommodityActivity;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import java.util.List;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2018/1/3.
 * email : huangjinping@hdfex.com
 */

public class ApliyMainPresenter extends ApliyMainContract.Presenter {


    /**
     * 分期专案查询(商户专案)
     */
    @Override
    public void bussinessCase(final Context context, final User user) {
        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("bussinessId", user.getBussinessId())
                .url(NetConst.BUSSINESS_CASE).build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast("服务异常");
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (getmMvpView().checkResonse(response)) {
                        BussinessCaseResponse response1 = GsonUtil.changeGsonToBean(response, BussinessCaseResponse.class);
                        if (response1.getResult() != null) {
                            List<Installment> resultList = response1.getResult();
                            if (resultList.size() > 0) {
                                HuabeiRequest request = new HuabeiRequest();
                                request.setCommodityCaseList(resultList);
                                preSubmitOrderV2(context, user, request);
                            } else {
                                getmMvpView().showToast("数据异常");
                            }
                        } else {
                            getmMvpView().showToast("数据异常");
                        }
                    }
                } catch (Exception e) {
                    getmMvpView().showToast("数据异常");
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


    /**
     * 预生成订单V2（花呗分期）
     */
    private void preSubmitOrderV2(final Context context, final User user, final HuabeiRequest huabeiRequest) {
        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .url(NetConst.PRE_SUBMIT_ORDERV2).build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast("服务异常");
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (getmMvpView().checkResonse(response)) {
                        PreSubmitOrderResponse house = GsonUtil.changeGsonToBean(response, PreSubmitOrderResponse.class);

                        Log.d("okhttp", "===" + house.toString());
                        if (house.getResult() != null && !TextUtils.isEmpty(house.getResult().getOrderId())) {
                            PreSubmitOrder result = house.getResult();
                            huabeiRequest.setOrderId(result.getOrderId());
                            payOrderQuery(context, user, huabeiRequest);

                        } else {
                            getmMvpView().showToast("数据异常！");
                        }
                    }
                } catch (Exception e) {
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


    /**
     * 查询基础配置的
     *
     * @param context
     * @param user
     * @param huabeiRequest
     */
    public void payOrderQuery(final Context context, User user, final HuabeiRequest huabeiRequest) {
        getmMvpView().showProgress();

        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("orderId", huabeiRequest.getOrderId())
                .url(NetConst.PAY_ORDER_QUERY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (getmMvpView().checkResonse(response)) {
                        PayOrderQueryResponse payOrderQuery = GsonUtil.changeGsonToBean(response, PayOrderQueryResponse.class);
                        ConfirmCommodityActivity.startAction(context, huabeiRequest, payOrderQuery.getResult());
                    }

                } catch (Exception e) {
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
    public void requestQrCode(final Context context, User user) {
        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .url(NetConst.APLIY_GET_QR_CODE).build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast("服务异常");
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (getmMvpView().checkResonse(response)) {
                        QRCodeResponse qrCodeResponse = GsonUtil.changeGsonToBean(response, QRCodeResponse.class);
                        QRCodeActivity.startAction(context,qrCodeResponse.getResult());
                    }
                } catch (Exception e) {
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
}
