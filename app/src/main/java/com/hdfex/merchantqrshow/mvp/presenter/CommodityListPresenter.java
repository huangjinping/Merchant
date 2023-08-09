package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.hdfex.merchantqrshow.bean.salesman.commodity.PreSubmitOrder;
import com.hdfex.merchantqrshow.bean.salesman.commodity.PreSubmitOrderResponse;
import com.hdfex.merchantqrshow.bean.salesman.huabei.PayOrderQueryResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageListResponse;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageResult;
import com.hdfex.merchantqrshow.mvp.contract.CommodityListContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.activity.ConfirmCommodityActivity;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import java.util.List;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2017/7/11.
 * email : huangjinping@hdfex.com
 */

public class CommodityListPresenter extends CommodityListContract.Presenter {

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

                                            getmMvpView().showProgress();
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
    public void preSubmitOrder(final Context context, final Order order, final User user) {

        OkHttpUtils
                .post()
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("commodityId", order.getCommoditysList().get(0).getCommodityId())
                .addParams("commodityCount", order.getCommoditysList().size() + "")
                .addParams("totalAmount", order.getTotalPrice())
                .tag(context)
                .url(NetConst.PRE_SUBMIT_ORDER)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                PreSubmitOrderResponse house = GsonUtil.changeGsonToBean(response, PreSubmitOrderResponse.class);
                                if (house.getResult() != null && !TextUtils.isEmpty(house.getResult().getOrderId())) {
                                    PreSubmitOrder result = house.getResult();
                                    order.setOrderId(result.getOrderId());

                                    payOrderQuery(context, user, order);

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
                    }
                });

    }

    /**
     * 获取查询
     * @param context
     * @param user
     * @param order
     */
    public void payOrderQuery(final Context context, User user, final Order order) {
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
                        ConfirmCommodityActivity.startAction(context, order, payOrderQuery.getResult());
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
}