package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

import java.util.Map;

/**
 * author Created by harrishuang on 2018/1/4.
 * email : huangjinping@hdfex.com
 */

public interface ReceiptMoneyContract {

    interface View extends MvpView {
        void showLoading();

        void dismissLoading();

        void returnTimeout();

        void returnPayEirr(Response res);

        void returnSanpay(Response res);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void submitData(Context context, Map<String, String> params);
    }
}
