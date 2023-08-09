package com.hdfex.merchantqrshow.mvp.contract;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public interface SalesmanContract {

    interface View extends MvpView {

        void returnResult(String status);
    }

    abstract class Presenter extends BasePresenter<SalesmanContract.View> {

        public abstract void requestPersonForbid(User user, String id, String status);
    }
}
