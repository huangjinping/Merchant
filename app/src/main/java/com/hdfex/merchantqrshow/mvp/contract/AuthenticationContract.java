package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfo;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;
import com.hdfex.merchantqrshow.salesman.my.activity.AuthenticationActivity;

import java.io.File;

/**
 * author Created by harrishuang on 2017/9/11.
 * email : huangjinping@hdfex.com
 */

public interface AuthenticationContract {

    interface View extends MvpView {
        /**
         * 身份证正面照片
         */
        String IMAGE_TYPE_BACK="IMAGE_BACK";
        /**
         * 身份证反面照片
         */
        String IMAGE_TYPE_FONT="IMAGE_FONT";
        void startParse();
        void stopParse();
        void returnUpLoadSuccess(File file, String type);

        void returnCardInfo(AdvancedInfo result);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void parseCardInfo(Context context, Bitmap bitmap, String type, User user);
        public abstract void upLoadHoldImag(Context context, File imageFile, String type);
        public abstract void  upLoadAdvancedinfo(User user, String name, String fileName, File file, String type);

        public abstract void loadIdCard(User user);

        public abstract void parseFaceIdCardSDKResult(Context context, User user, Intent data);
    }
}
