package com.hdfex.merchantqrshow.mvp.contract;

import android.app.Activity;
import android.widget.TextView;

import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * author Created by harrishuang on 2017/9/22.
 * email : huangjinping@hdfex.com
 */

public interface SettingContract {

    interface View extends MvpView {

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void pushSetting(Activity activity, TextView tv_we_push);
    }
}
