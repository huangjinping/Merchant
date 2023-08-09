package com.hdfex.merchantqrshow.mvp.contract;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.plan.PlanDetail;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/5/26.
 * email : huangjinping@hdfex.com
 */

public interface PlanDetailsContract {

    interface View extends MvpView {

        void returnPlanDetail(PlanDetail planDetail);
    }


    abstract class Persenter extends BasePresenter<PlanDetailsContract.View> {


        public abstract void loadRepayDetail( User user, String applyId);
    }
}
