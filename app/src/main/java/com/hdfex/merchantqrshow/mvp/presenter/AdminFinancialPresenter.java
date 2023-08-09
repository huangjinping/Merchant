package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.hdfex.merchantqrshow.bean.admin.bam.QueryFinanceInfo;
import com.hdfex.merchantqrshow.bean.admin.bam.QueryFinanceInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.taobao.RedPackageAccountInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.taobao.WithdrawDeposit;
import com.hdfex.merchantqrshow.bean.salesman.taobao.WithdrawDepositResponse;
import com.hdfex.merchantqrshow.comm.taobao.TaobaoAuthActivity;
import com.hdfex.merchantqrshow.mvp.contract.AdminFinancialContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;
import okhttp3.Request;

import static com.hdfex.merchantqrshow.R.id.info;
import static com.hdfex.merchantqrshow.R.id.txt_totalAmount;
import static com.hdfex.merchantqrshow.R.id.txt_totalRedPacketAmt;

/**
 * author Created by harrishuang on 2017/12/12.
 * email : huangjinping@hdfex.com
 */

public class AdminFinancialPresenter extends AdminFinancialContract.Presenter {


    @Override
    public void loadRedPackage(User user,final FragmentActivity activity) {
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

    @Override
    public void loadWithdrawDeposit(User user, final Context context) {

        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("remark","remark")
                .url(NetConst.WITH_DRAW_DEPOSIT).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (getmMvpView().checkResonse(response)) {
                        WithdrawDepositResponse queryFinanceInfoResponse = GsonUtil.changeGsonToBean(response, WithdrawDepositResponse.class);
                        WithdrawDeposit result = queryFinanceInfoResponse.getResult();
                        String status = result.getTransferStatus();
                        String transferDesc = result.getTransferDesc();
                        if ("00".equals(status)){
                            loadQueryFinanceInfo(context);
                            }else {
                        }
                        getmMvpView().showToast(transferDesc);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onAfter() {
                super.onAfter();
               getmMvpView(). dismissProgress();
            }
        });
    }
    @Override
    public void loadQueryFinanceInfo(Context context) {
       getmMvpView(). showProgress();
       User user = UserManager.getUser(context);

        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("userId", user.getId())
                .addParams("id",user.getBussinessId())
                .url(NetConst.QUERY_FINANCE_INFO).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
               getmMvpView(). showToast(e.getMessage());
            }
            @Override
            public void onResponse(String response) {
                try {
                    if ( getmMvpView().checkResonse(response)) {
                        QueryFinanceInfoResponse queryFinanceInfoResponse = GsonUtil.changeGsonToBean(response, QueryFinanceInfoResponse.class);
                        QueryFinanceInfo result = queryFinanceInfoResponse.getResult();
                         getmMvpView().returnQueryFinanceInfo(result);
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
