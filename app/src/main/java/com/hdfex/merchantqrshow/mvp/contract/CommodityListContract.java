package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;
import com.hdfex.merchantqrshow.salesman.add.activity.CommodityListActivity;

/**
 * author Created by harrishuang on 2017/7/11.
 * email : huangjinping@hdfex.com
 */

public interface CommodityListContract {

    interface View extends MvpView {

        void showRedPackageDialog(RedPackage redPackage);
    }

    abstract class Presenter extends BasePresenter<CommodityListContract.View> {
        public abstract void loadRedPackage(final Context context);

        public abstract void preSubmitOrder(Context context, Order order,User user);
    }


}
