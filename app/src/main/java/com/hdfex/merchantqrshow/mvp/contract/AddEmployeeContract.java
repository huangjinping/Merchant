package com.hdfex.merchantqrshow.mvp.contract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;
import com.hdfex.merchantqrshow.utils.LoadMode;

/**
 * author Created by harrishuang on 2017/12/5.
 * email : huangjinping@hdfex.com
 */

public interface AddEmployeeContract {

    interface View extends MvpView {

        void setPhoneMessage(int requestCode, String name, String phoneNum);

        void onRegionReturn(int index, String item);

        void returnResult(String status);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data);
        public abstract void requestPersonForbid(User user, String id,final String status) ;

        public abstract void selectContacts(Activity context, int requestCode);

        public abstract void openCategoryPicker(Activity context);

        public abstract void getRegionList(User user, Activity context);
    }
}
