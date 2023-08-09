package com.hdfex.merchantqrshow.mvp.contract;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.message.MessageItem;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/19.
 * email : huangjinping@hdfex.com
 */

public interface HuabeiContract {


    interface View extends MvpView {

        void returnSanpay(Response res);

        void showLoading();

        void dismissLoading();

        void returnTimeout();

        void returnPayEirr(Response res);
    }

    abstract class Presenter extends BasePresenter<HuabeiContract.View> {
        public abstract void startAnimation(android.view.View view);

        public abstract void scanPay(User user, String orderId, String paycode);
    }
}
