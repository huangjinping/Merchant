package com.hdfex.merchantqrshow.mvp.contract;

import android.support.v4.app.FragmentActivity;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public interface MyContract {

    interface View extends MvpView {

    }

    abstract class Presenter extends BasePresenter<MyContract.View> {

        public abstract void loadRedPackage(User user, FragmentActivity activity);
    }
}
