package com.hdfex.merchantqrshow.mvp;

/**
 *
 * Created by harrishuang on 2016/11/15.
 */

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

}

