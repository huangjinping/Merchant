package com.hdfex.merchantqrshow.mvp.contract;

import com.hdfex.merchantqrshow.bean.manager.main.StoresListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/6/6.
 * email : huangjinping@hdfex.com
 */

public interface ManagerContract {


    interface View extends MvpView {

        void returnStroes(StoresListResponse res);
    }

    abstract class Presenter extends BasePresenter<ManagerContract.View> {


        public abstract void loadStoresList(User user);
    }
}
