package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.commodity.PreSubmitOrder;
import com.hdfex.merchantqrshow.bean.salesman.commodity.PreSubmitOrderResponse;
import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfo;
import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.huabei.AlipayOrder;
import com.hdfex.merchantqrshow.bean.salesman.huabei.AlipayOrderResponse;
import com.hdfex.merchantqrshow.bean.salesman.huabei.BussinessCaseResponse;
import com.hdfex.merchantqrshow.bean.salesman.huabei.HuabeiRequest;
import com.hdfex.merchantqrshow.bean.salesman.huabei.PayInfoItem;
import com.hdfex.merchantqrshow.bean.salesman.huabei.PayOrderQueryResponse;
import com.hdfex.merchantqrshow.bean.salesman.huabei.PayOrderQueryResult;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.AlipayOrderDetailsResponse;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageListResponse;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageResult;
import com.hdfex.merchantqrshow.mvp.contract.IndexContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.activity.ConfirmCommodityActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.AuthenticationActivity;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.DateUtils;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/7/10.
 * email : huangjinping@hdfex.com
 */

public class IndexPresenter extends IndexContract.Presenter {


    @Override
    public void loadRedPackage(final Context context) {
        if (!UserManager.isLogin(context)) {
            return;
        }
        User user = UserManager.getUser(context);
        OkHttpUtils
                .post()
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("openFlag", "0")
                .addParams("pageIndex", 0 + "")
                .addParams("pageSize", "15")
                .tag(context)
                .url(NetConst.RED_PACKAGE_LIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                RedPackageListResponse house = GsonUtil.changeGsonToBean(response, RedPackageListResponse.class);
                                if (house.getResult() != null) {
                                    RedPackageResult result = house.getResult();
                                    List<RedPackage> redPacketList = result.getRedPacketList();
                                    if (redPacketList != null && redPacketList.size() != 0) {
                                        RedPackage redPackage = redPacketList.get(0);
                                        if (redPackage != null) {
                                            getmMvpView().showRedPackageDialog(redPackage);
                                        }
//                                        for (RedPackage redPackage:redPacketList) {
//                                            RedPackageDialog.getInstance(context,redPackage ).show();
//                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                    }
                });
    }

    @Override
    public void requestHuabeiOrder(final Context context, final User user) {
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(NetConst.ALIPAY_ORDER)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("status", "0")
                .addParams("pageIndex", "0")
                .addParams("pageSize", "15");
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().createCommodity();

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = getmMvpView().checkResonse(response);
                            if (b) {
                                AlipayOrderResponse orderListResponse = GsonUtil.changeGsonToBean(response, AlipayOrderResponse.class);
                                if (orderListResponse != null && orderListResponse.getResult() != null && orderListResponse.getResult().size() > 0) {
                                    AlipayOrder alipayOrder = orderListResponse.getResult().get(0);
                                    loadAlipayOrder(context, alipayOrder.getOrderNo(), user);
                                    return;
                                }
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                        bussinessCase(context, user);
//                        getmMvpView().createCommodity();

                    }
                });
    }


    @Override
    public void loadIdCard(User user, final Context context) {
        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .url(NetConst.ID_CARD_QUERY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast("服务异常");
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (getmMvpView().checkResonse(response)) {
                        AdvancedInfoResponse house = GsonUtil.changeGsonToBean(response, AdvancedInfoResponse.class);

                        if (house.getResult() != null) {
                            AdvancedInfo result = house.getResult();
                            if (AdvancedInfo.AUTH_STATUS_NO.equals(result.getAuthStatus())) {
                                showUnAuthAlert(context, result);
                            } else {
                                getmMvpView().returnCardInfo();
                            }
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
     * @param context
     * @param result
     */
    private void showUnAuthAlert(final Context context, final AdvancedInfo result) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("应《中华人民共和国网络安全法》要求，使用互联网服务需进行账号实名认证，截止日期" + result.getEndDate() + "，到期未完成实名认证的管家将无法继续使用下单功能。")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(context.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
                .withButton1Text("暂不认证")
                .withButton2Text("去认证")
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        boolean compareNowDate = DateUtils.compareNowDate(result.getEndDate());
                        if (compareNowDate) {
                            getmMvpView().returnCardInfo();
                        }
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        AuthenticationActivity.startAction(context);

                    }
                })
                .show();
    }

    /**
     * 显示数据问题
     *
     * @param context
     * @param alipayOrder
     * @param result
     */
    private void showUnComplateOrder(final Context context, final AlipayOrder alipayOrder, final PayOrderQueryResult result) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("当前订单没有完成，请您点击【支付完成】或继续进行支付")
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
                        ConfirmCommodityActivity.startAction(context, alipayOrder, result);
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

    /**
     * 获取订单详情接口
     *
     * @param orderId
     */
    private void loadAlipayOrder(final Context context, String orderId, final User user) {


        PostFormBuilder postFormBuilder = OkHttpUtils
                .post().tag(this)
                .url(NetConst.ALIPAY_ORDER_DETAILS)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("orderId", orderId);
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        getmMvpView().dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        getmMvpView().showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().createCommodity();

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = getmMvpView().checkResonse(response);
                            if (b) {
                                AlipayOrderDetailsResponse orderListResponse = GsonUtil.changeGsonToBean(response, AlipayOrderDetailsResponse.class);
                                if (orderListResponse.getResult() != null) {
                                    AlipayOrder alipayOrder = orderListResponse.getResult();
                                    payOrderQuery(context, user, alipayOrder);
                                    return;
                                }
                            }
                        } catch (Exception e) {


                            e.printStackTrace();
                        }
                        bussinessCase(context, user);

//                        getmMvpView().createCommodity();

                    }
                });
    }

    /**
     * 查询基础配置的
     *
     * @param context
     * @param user
     * @param alipayOrder
     */
    public void payOrderQuery(final Context context, final User user, final AlipayOrder alipayOrder) {
        getmMvpView().showProgress();

        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("orderId", alipayOrder.getOrderNo())
                .url(NetConst.PAY_ORDER_QUERY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
                getmMvpView().createCommodity();
            }

            @Override
            public void onResponse(String response) {
                try {
                    PayOrderQueryResponse payOrderQuery = GsonUtil.changeGsonToBean(response, PayOrderQueryResponse.class);
                    if (payOrderQuery != null) {
                        if (0 == payOrderQuery.getCode()) {
                            PayOrderQueryResult result = payOrderQuery.getResult();
                            if (result != null && result.getPayInfoList() != null) {
                                for (int i = 0; i < result.getPayInfoList().size(); i++) {
                                    PayInfoItem payInfoItem = result.getPayInfoList().get(i);
                                    if (PayInfoItem.PAYSTATUS_YES.equals(payInfoItem.getPayStatus())) {
                                        showUnComplateOrder(context, alipayOrder, result);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bussinessCase(context, user);

//                getmMvpView().createCommodity();

            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }


    /**
     * 分期专案查询(商户专案)
     */
    private void bussinessCase(final Context context, final User user) {
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
                getmMvpView().createCommodity();
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

}
