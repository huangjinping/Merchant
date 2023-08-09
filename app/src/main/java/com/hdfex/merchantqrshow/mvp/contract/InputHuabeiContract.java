package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.hdfex.merchantqrshow.bean.salesman.huabei.DurationPayment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/8/15.
 * email : huangjinping@hdfex.com
 */

public interface InputHuabeiContract {




    interface View extends MvpView {

        void returnDurationPayment(DurationPayment result);

        void submitScan();
    }

    abstract class Presenter extends BasePresenter<InputHuabeiContract.View> {

        public static final  int SUBMIT=12;
        public static final int  UPDATE=123;

        public abstract void requestDurationPayment(User user, String payAmount, String caseId,int type);

        public abstract void onPlan(final Context context, User user, String payAmount, String caseId);
    }
}
