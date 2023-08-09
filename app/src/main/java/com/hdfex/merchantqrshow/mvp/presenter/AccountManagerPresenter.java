package com.hdfex.merchantqrshow.mvp.presenter;

import android.widget.TextView;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.AccountManagerContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/9/15.
 * email : huangjinping@hdfex.com
 */

public class AccountManagerPresenter extends AccountManagerContract.Presenter {

    @Override
    public void avatarUpload(User user, final File file) {

        OkHttpUtils
                .post()
                .url(NetConst.AVATAR_UPLOAD)
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addFile("userAvatarImg", "userAvatarImg.jpg", file)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                getmMvpView().returnAvatarUpload(file);
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getmMvpView().showToast("上传失败");
                    }
                });
    }

    @Override
    public void saveEntryDate(User user, final String entryDate, final TextView edittext) {
        OkHttpUtils
                .post()
                .url(NetConst.AVATAR_UPLOAD)
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("entryDate", entryDate)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        getmMvpView().dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        getmMvpView().showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showToast("操作异常");
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                edittext.setText(entryDate);
                                getmMvpView().returnEntryDate(entryDate);
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getmMvpView().showToast("上传失败");
                    }
                });
    }
}
