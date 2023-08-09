package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;

import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/7/10.
 * email : huangjinping@hdfex.com
 */

public interface MainContract {

    interface View extends MvpView {
    }

    abstract class Presenter extends BasePresenter<MainContract.View> {
        public abstract void loadRedPackage(Context context);
    }
}
