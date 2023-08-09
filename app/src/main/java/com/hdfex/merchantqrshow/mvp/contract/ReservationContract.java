package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.salesman.appointment.SubscribeItem;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/9/13.
 * email : huangjinping@hdfex.com
 */

public interface ReservationContract {

    interface View extends MvpView {


        void updateList();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void requestupdateBussSubscribeStatus(User user, SubscribeItem item, String status);

        public abstract void getAppointmentDetails(Context context, User user, SubscribeItem item);

        public abstract void accpetOrder(Context context, User user, SubscribeItem item);

        public abstract void refuseOrder(Context context, User user, SubscribeItem item);

        public abstract void pushOrder(Context context, User user, SubscribeItem item);
    }
}
