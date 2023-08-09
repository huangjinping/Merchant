package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfo;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;
import com.hdfex.merchantqrshow.salesman.main.activity.IndexActivity;

/**
 * author Created by harrishuang on 2017/7/10.
 * email : huangjinping@hdfex.com
 */

public interface IndexContract {
    interface View extends MvpView {
        void showRedPackageDialog(RedPackage redPackage);

        void createCommodity();

        void returnCardInfo();
    }

    abstract class Presenter extends BasePresenter<IndexContract.View> {
        public abstract void loadRedPackage(Context context);

        public abstract void requestHuabeiOrder(Context context, User user);

        public abstract void loadIdCard(User user, Context context);
    }
}
