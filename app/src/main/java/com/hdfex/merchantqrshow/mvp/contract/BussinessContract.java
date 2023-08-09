package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.manager.business.BusinessForCurOrderResult;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessForHouseResult;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessForTotalOrderResult;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessForWarnResult;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public interface BussinessContract {

    interface View extends MvpView {

        void returnBusinessForWarn(BusinessForWarnResult result);

        void returnCurOrder(BusinessForCurOrderResult result);

        void returnTotalOrder(BusinessForTotalOrderResult result);

        void returnforHouse(BusinessForHouseResult result);

        void returnBusinessName(String bussinessName);
    }

    abstract class Presenter extends BasePresenter<BussinessContract.View> {

        public abstract void loadData(Context context);
    }
}
