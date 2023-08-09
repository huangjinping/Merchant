package com.hdfex.merchantqrshow.mvp.contract;

import android.widget.TextView;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

import java.io.File;

/**
 * author Created by harrishuang on 2017/9/15.
 * email : huangjinping@hdfex.com
 */

public interface AccountManagerContract {

    interface View extends MvpView {

        void returnAvatarUpload(File file);

        void returnEntryDate(String entryDate);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void avatarUpload(User user, File file);

        public abstract void saveEntryDate(User user, String s, TextView edittext);
    }
}
