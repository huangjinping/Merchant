package com.hdfex.merchantqrshow.mvp.contract;

import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;
import com.hdfex.merchantqrshow.view.SpannerView;

/**
 * Created by harrishuang on 2017/4/24.
 */

public interface OrderListContract {


    interface View extends MvpView {

        void returnoNTabSelect(int currentTab, int position, String name, String type);
    }

    abstract class Persenter extends BasePresenter<View> {

        public abstract void setCurrentTab(int currentTab, SpannerView layout_container);

        public abstract void setCurrentTab(int position, boolean open, SpannerView layout_container);


    }


}
