package com.hdfex.merchantqrshow.mvp.contract;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.message.MessageItem;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

import java.util.List;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public interface MessageContract {

    interface View extends MvpView {

        void returnMessageList(List<MessageItem> result);
    }

    abstract class Presenter extends BasePresenter<MessageContract.View> {

        public abstract void requestMessageList(Activity activity, User user, String type);

        public abstract void loadMessageDetails(FragmentActivity activity, User user, MessageItem messageItem);
    }
}
