package com.hdfex.merchantqrshow.mvp.presenter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.bsy.BSYOrderListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.message.MessageDetails;
import com.hdfex.merchantqrshow.bean.salesman.message.MessageDetailsResponse;
import com.hdfex.merchantqrshow.bean.salesman.message.MessageItem;
import com.hdfex.merchantqrshow.bean.salesman.message.MessageResponse;
import com.hdfex.merchantqrshow.mvp.contract.MessageContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.my.activity.MessageDetailsActivity;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class MessagePresenter extends MessageContract.Presenter {
    @Override
    public void requestMessageList(Activity activity, User user, String type) {
        OkHttpUtils
                .post()
                .url(NetConst.APP_MESSAGE)
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("type", type)
                .tag(activity)
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
                            boolean b = getmMvpView().checkResonse(response);
                            if (b) {
                                MessageResponse messageResponse = GsonUtil.changeGsonToBean(response, MessageResponse.class);
                                getmMvpView().returnMessageList(messageResponse.getResult());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    @Override
    public void loadMessageDetails(final FragmentActivity activity, final User user, final MessageItem messageItem) {
        OkHttpUtils
                .post()
                .url(NetConst.APP_MESSAGE_DETAILS)
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("mid", messageItem.getMid())
                .tag(activity)
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

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = getmMvpView().checkResonse(response);
                            if (b) {
                                MessageDetailsResponse messageResponse = GsonUtil.changeGsonToBean(response, MessageDetailsResponse.class);

                                if (MessageItem.ISREAD_YES==messageItem.getIsread()){
                                    MessageDetailsActivity.startAction(activity, messageResponse.getResult());
                                }else {
                                    loadData(activity,user,messageItem,messageResponse.getResult());
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     *  @param activity
     * @param user
     * @param messageItem
     * @param result
     */
    private void loadData(final FragmentActivity activity, User user, final MessageItem messageItem,final MessageDetails result) {

        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(NetConst.APP_MESSAGE_RES)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("cumid",messageItem.getCumid())
                .addParams("mid",messageItem.getMid());
        postFormBuilder.build()
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
                            boolean b = getmMvpView().checkResonse(response);
                            if (b) {
                                MessageDetailsActivity.startAction(activity, result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }




}
