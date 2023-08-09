package com.hdfex.merchantqrshow.mvp.view;

import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * Created by harrishuang on 2016/11/15.
 */

public interface LoginView extends MvpView {



    void login(String response,String username);
}
