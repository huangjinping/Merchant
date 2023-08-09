package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.bean.manager.finance.Finance;
import com.hdfex.merchantqrshow.bean.manager.finance.FinanceResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.FinanceContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2017/6/7.
 * email : huangjinping@hdfex.com
 */

public class FinancePresenter extends FinanceContract.Presenter {
    @Override
    public void loadFinanceManager(User user) {
        OkHttpUtils.post().addParams("id", user.getCurrentBussinessId())
                .addParams("token", user.getToken())
                .url(NetConst.FINANCE_MANAGER)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        if (getmMvpView().checkResonse(response)) {
                            FinanceResponse financeResponse = GsonUtil.changeGsonToBean(response, FinanceResponse.class);
                            Finance result = financeResponse.getResult();
                            getmMvpView().returnFinance(result);
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
