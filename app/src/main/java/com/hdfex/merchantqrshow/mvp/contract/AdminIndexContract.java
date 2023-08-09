package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/12/20.
 * email : huangjinping@hdfex.com
 */

public interface AdminIndexContract {

    interface View extends MvpView {


        void showRedPackageDialog(RedPackage redPackage);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void loadRedPackage(Context context);
    }
}
