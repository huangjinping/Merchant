package com.hdfex.merchantqrshow.mvp.contract;

import com.hdfex.merchantqrshow.bean.manager.finance.Finance;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/6/7.
 * email : huangjinping@hdfex.com
 */

public interface FinanceContract {

    interface View extends MvpView {

        void returnFinance(Finance result);
    }

    abstract class Presenter extends BasePresenter<FinanceContract.View> {


        public abstract void loadFinanceManager(User user);
    }
}
