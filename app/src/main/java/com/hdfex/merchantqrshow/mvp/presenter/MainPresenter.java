package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;

import com.hdfex.merchantqrshow.mvp.contract.MainContract;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.EventRxBus;

/**
 * author Created by harrishuang on 2017/7/10.
 * email : huangjinping@hdfex.com
 */

public class MainPresenter extends MainContract.Presenter {
    @Override
    public void loadRedPackage(final Context context) {
        EventRxBus.getInstance().post(IntentFlag.RED_LOAD,IntentFlag.RED_LOAD);
    }

}
