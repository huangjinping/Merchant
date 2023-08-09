package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.bean.manager.team.PersonRecordListResponse;
import com.hdfex.merchantqrshow.bean.manager.team.TeamResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.TeamContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class TeamPresenter extends TeamContract.Presenter {
    @Override
    public void requestTeam(User user) {
        OkHttpUtils.post().addParams("id", user.getCurrentBussinessId())
                .addParams("token", user.getToken())
                .url(NetConst.GET_TEAM)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    TeamResponse house = GsonUtil.changeGsonToBean(response, TeamResponse.class);
                    getmMvpView().returnTeamResult(house.getResult());
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }

    @Override
    public void requestPersonRecordList(User user) {
        OkHttpUtils.post().addParams("id", user.getCurrentBussinessId())
                .addParams("token", user.getToken())
                .url(NetConst.PERSONRECORDLIST)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    PersonRecordListResponse data = GsonUtil.changeGsonToBean(response, PersonRecordListResponse.class);
                    getmMvpView().returnPersonRecordList(data.getResult());
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }
}
