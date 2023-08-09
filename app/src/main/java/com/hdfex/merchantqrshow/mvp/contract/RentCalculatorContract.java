package com.hdfex.merchantqrshow.mvp.contract;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hdfex.merchantqrshow.salesman.add.activity.RentCalculatorActivity;
import com.hdfex.merchantqrshow.bean.salesman.home.CalculateResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * Created by harrishuang on 2017/4/20.
 * 房租计算器
 */

public interface RentCalculatorContract {

    interface View extends MvpView {
        void returnCalculateResult(CalculateResult result);

        void returnTestCalculateResult(CalculateResult result);
    }

    interface Modle {

    }

    abstract class Persenter extends BasePresenter<View> {
        public abstract void selectData(Activity context, TextView targetView);

        public abstract void selectMonth(RentCalculatorActivity rentCalculatorActivity, EditText et_entdata, FragmentManager fragmentManager);

        public abstract void requestResult(User user, String startDate, String duration, String terminationDate, String monthRent);

        public abstract void addEvents(User user, EditText et_startdata, EditText et_entdata, EditText et_unbinddata);
    }

}
