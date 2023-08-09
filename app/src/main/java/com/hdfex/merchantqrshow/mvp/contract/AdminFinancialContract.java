package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.hdfex.merchantqrshow.admin.financial.fragment.AdminFinancialFragment;
import com.hdfex.merchantqrshow.bean.admin.bam.QueryFinanceInfo;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/12/12.
 * email : huangjinping@hdfex.com
 */

public interface AdminFinancialContract {

    interface View extends MvpView {


        void returnQueryFinanceInfo(QueryFinanceInfo result);
    }

    abstract class Presenter extends BasePresenter<View> {


        public abstract void loadRedPackage(User user, FragmentActivity activity);

        public abstract void loadWithdrawDeposit(User user,Context context);

        public abstract void loadQueryFinanceInfo(Context context);
    }
}
