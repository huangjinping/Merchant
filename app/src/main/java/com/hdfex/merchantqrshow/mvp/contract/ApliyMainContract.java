package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2018/1/3.
 * email : huangjinping@hdfex.com
 */

public interface ApliyMainContract {

    interface View extends MvpView {

    }

    abstract class Presenter extends BasePresenter<View> {


        public abstract void bussinessCase(Context context, User user);

        public abstract void requestQrCode(Context context, User user);
    }
}
