package com.hdfex.merchantqrshow.mvp.contract;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageResult;
import com.hdfex.merchantqrshow.bean.salesman.taobao.AliPayUserAuth;
import com.hdfex.merchantqrshow.bean.salesman.taobao.RedPackageAccountInfo;
import com.hdfex.merchantqrshow.bean.salesman.taobao.WithdrawDeposit;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;
import com.hdfex.merchantqrshow.utils.LoadMode;

/**
 * author Created by harrishuang on 2017/6/26.
 * email : huangjinping@hdfex.com
 */

public interface TaobaoAuthContract {
    interface View extends MvpView {

        void returnAliPayUserAuth(AliPayUserAuth result);

        void returnWithdrawDeposit(WithdrawDeposit result);

        void returnRedPackageResult(RedPackageResult result, LoadMode loadMode);

        void returnRedPackage(RedPackageAccountInfo result);

        void loadAfter();
    }

    abstract class Presenter extends BasePresenter<TaobaoAuthContract.View> {

        private  User user;

        public void setUser(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public abstract void toTaobaoAuth(Activity context);

        public abstract void getWithdrawDeposit(User user, String balAmt, Activity activity);
        public abstract void loadRedPackageList(User user, final Activity activity,LoadMode loadMode);

        public abstract void showActionSheet(Activity context,User user, FragmentManager supportFragmentManager);
        public abstract void loadRedPackage(User user, final Activity activity);

    }
}
