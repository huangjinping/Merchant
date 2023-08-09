package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.huabei.PayOrderQueryResponse;
import com.hdfex.merchantqrshow.bean.salesman.huabei.PrecreateResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.mvp.contract.ConfirmCommodityContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.activity.ScanPicActivity;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.widget.ActionSheet;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;


/**
 * author Created by harrishuang on 2017/6/21.
 * email : huangjinping@hdfex.com
 */

public class ConfirmCommodityPresenter extends ConfirmCommodityContract.Presenter {


    @Override
    public void submitData(final Context context, final Map<String, String> params) {


        System.out.println(params.toString());

        OkHttpUtils.post().params(params)
                .url(NetConst.SCAN_PAY_V2).build().connTimeOut(60 * 1000)
                .readTimeOut(60 * 1000)
                .writeTimeOut(60 * 1000).execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                String action = "";
                if (PAY_FLAG_HUABEI.equals(params.get("payFlag"))) {
                    action = ACTION_SUBMIT_HUABEI;

                } else {
                    action = ACTION_SUBMIT_TAOBAO;
                }


                try {
                    if (getmMvpView().checkResonse(response)) {
                        try {
                            Response res = GsonUtil.changeGsonToBean(response, Response.class);
                            User user = new User();
                            user.setId(params.get("id"));
                            user.setToken(params.get("token"));

                            payOrderQuery(context, user, params.get("orderId"), action);
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
                        payOrderQuery(context, user, params.get("orderId"), action);
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

    @Override
    public void selectProcedures(Context context, FragmentManager supportFragmentManager) {
        final String[] dataArr = {"承担", "不承担"};
        ActionSheet.createBuilder(context, supportFragmentManager)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                getmMvpView().returnProceduresType(dataArr[index], index);
            }
        }).show();
    }

    @Override
    public void loadOrderList(Context context, User user) {

    }

    @Override
    public void payOrderQuery(Context context, User user, String orderId, final String action) {
        getmMvpView().showProgress();

        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("orderId", orderId)
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
                        getmMvpView().returnPayOrderQuery(payOrderQuery.getResult(), action);
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
    public void finishOrder(final Context context, final User user, final String orderId) {

        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("orderId", orderId)
                .url(NetConst.FINISH_ORDER).build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (getmMvpView().checkResonse(response)) {
                        Response resp = GsonUtil.changeGsonToBean(response, Response.class);
                        getmMvpView().returnOrder();
                    }
                } catch (Exception e) {
                    getmMvpView().showToast("服务异常！");
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
    public void showUnComplate(Context context) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("当前订单没有完成，请您点击【交易完成】或继续进行支付")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(context.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
                .withButton1Text("我知道了")
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();

                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();


                    }
                })
                .show();
    }

    @Override
    public void submitOnPrecreate(final Context context, final Map<String, String> params) {
        Log.d("okhttp", GsonUtil.createGsonString(params));
        OkHttpUtils.post().params(params)
                .url(NetConst.ALIPAY_WITHDRAW_PRECREATE).build().connTimeOut(60 * 1000)
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
                            PrecreateResponse res = GsonUtil.changeGsonToBean(response, PrecreateResponse.class);
                            params.put("qrcode", res.getResult().getQrcode());
                            ScanPicActivity.startAction(context, params);
                        } catch (Exception e) {
                            getmMvpView().showToast("二维码生成失败！！");
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    getmMvpView().showToast("二维码生成失败！");
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


    /**
     * 获取查询
     *
     * @param context
     * @param user
     * @param order
     */
    public void payUpDateOrderQuery(final Context context, User user, final Order order) {
        getmMvpView().showProgress();

        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("orderId", order.getOrderId())
                .url(NetConst.PAY_ORDER_QUERY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    PayOrderQueryResponse payOrderQuery = GsonUtil.changeGsonToBean(response, PayOrderQueryResponse.class);
                    if (payOrderQuery != null) {
                        getmMvpView().returnPayOrderQuery(payOrderQuery.getResult(), ACTION_QUERY_SHOW);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    getmMvpView().showToast("数据异常");
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
    public void scanPayV3(final Context context, final Map<String, String> params) {


        System.out.println(params.toString());

        OkHttpUtils.post().params(params)
                .url(NetConst.SCAN_PAY_V3).build().connTimeOut(60 * 1000)
                .readTimeOut(60 * 1000)
                .writeTimeOut(60 * 1000).execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                String action = "";
                if (PAY_FLAG_HUABEI.equals(params.get("payFlag"))) {
                    action = ACTION_SUBMIT_HUABEI;

                } else {
                    action = ACTION_SUBMIT_TAOBAO;
                }


                try {
                    if (getmMvpView().checkResonse(response)) {
                        try {
                            Response res = GsonUtil.changeGsonToBean(response, Response.class);
                            User user = new User();
                            user.setId(params.get("id"));
                            user.setToken(params.get("token"));

                            payOrderQuery(context, user, params.get("orderId"), action);
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
                        payOrderQuery(context, user, params.get("orderId"), action);
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

    @Override
    public void preSubmitOrderV2(final Context context, final Map<String, String> params) {
        Log.d("okhttp", GsonUtil.createGsonString(params));

        OkHttpUtils.post().params(params)
                .url(NetConst.PRE_CREATE_V2).build().connTimeOut(60 * 1000)
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
                            PrecreateResponse res = GsonUtil.changeGsonToBean(response, PrecreateResponse.class);
                            params.put("qrcode", res.getResult().getQrcode());
                            ScanPicActivity.startAction(context, params);
                        } catch (Exception e) {
                            getmMvpView().showToast("二维码生成失败！！");
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    getmMvpView().showToast("二维码生成失败！");
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
