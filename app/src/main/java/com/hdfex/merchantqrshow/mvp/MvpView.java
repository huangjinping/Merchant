package com.hdfex.merchantqrshow.mvp;

/**
 * Created by harrishuang on 2016/11/15.
 */

public interface MvpView {

    void  showProgress();

    void dismissProgress();

    void showWebEirr(Exception e);

     void showToast(String msg);
     boolean checkResonse(String res);


}
