package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.hdfex.merchantqrshow.bean.manager.business.BusinessOrder;
import com.hdfex.merchantqrshow.bean.manager.business.RegionForOrder;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.manager.business.activity.ManagerOrderActivity;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;
import com.hdfex.merchantqrshow.utils.LoadMode;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public interface ManagerOrderContract {

    interface View extends MvpView {

        void returnOrderResponse(List<BusinessOrder> result, LoadMode loadMode);

        void onAfter();

        void returnRegionForOrder(List<RegionForOrder> result);

        void returnReloadRegionForOrder(List<RegionForOrder> result);
    }

    abstract class Presenter extends BasePresenter<ManagerOrderContract.View> {

        public abstract void loadOrderList(User user, String status, LoadMode nomal, String region);

        public abstract void loadRegionForOrderList(User user, String status);

        public abstract void reLoadRegionForOrderList(User user, String status);


        public abstract void openMenu(List<RegionForOrder> result, int id,  FragmentManager supportFragmentManager);
    }
}
