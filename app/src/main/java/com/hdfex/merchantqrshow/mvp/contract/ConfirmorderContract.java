package com.hdfex.merchantqrshow.mvp.contract;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.PeriodAmountResult;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * Created by harrishuang on 2017/3/2.
 * 商品计算Contrct
 */

public interface ConfirmorderContract {

    interface View extends MvpView {

        void returnPeriodAmount(String periodAmount);

        void returnPeriodAmountResult(PeriodAmountResult result);
    }

    abstract class Presenter extends BasePresenter<View> {
        /**
         * 试算接口
         * @param user
         * @param applyAmount
         * @param caseId
         */
        public abstract void calculateUpdate(User user, String applyAmount, String caseId);
    }
}
