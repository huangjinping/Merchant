package com.hdfex.merchantqrshow.mvp.presenter;

import android.app.Activity;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.mvp.contract.SettingContract;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;

/**
 * author Created by harrishuang on 2017/9/22.
 * email : huangjinping@hdfex.com
 */

public class SettingPresenter extends SettingContract.Presenter {
    @Override
    public void pushSetting(final Activity context, final TextView tv_we_push) {
        PushAgent pushAgent = PushAgent.getInstance(context);
        boolean enable = UserManager.isPushEnable(context);
        getmMvpView().showProgress();
        if (!enable) {
            pushAgent.enable(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    System.out.println("enable" + "true" + Thread.currentThread().getName());
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UserManager.setPushEnable(context, true);
                            getmMvpView().dismissProgress();
                            tv_we_push.setText(R.string.opened_push);

                        }
                    });
                }

                @Override
                public void onFailure(String s, String s1) {
                    getmMvpView().dismissProgress();
                    tv_we_push.setText(s + s1);
                    getmMvpView().showToast(s + s1);
                }
            });
        } else {

            pushAgent.disable(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    System.out.println("enable" + "false" + Thread.currentThread().getName());
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_we_push.setText(R.string.closed_push);
                            UserManager.setPushEnable(context, false);
                            getmMvpView().dismissProgress();


                        }
                    });

                }

                @Override
                public void onFailure(String s, String s1) {
                    tv_we_push.setText(s + s1);

                    getmMvpView().dismissProgress();
                    getmMvpView().showToast(s + s1);
                }
            });
        }

    }
}
