package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;

import com.hdfex.merchantqrshow.admin.bam.activity.AddCommodityActivity;
import com.hdfex.merchantqrshow.admin.bam.activity.AddEmployeeActivity;
import com.hdfex.merchantqrshow.bean.manager.team.PersonDetail;
import com.hdfex.merchantqrshow.bean.manager.team.PersonDetailResponse;
import com.hdfex.merchantqrshow.bean.manager.team.RegionListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.manager.team.activity.SalesmanActivity;
import com.hdfex.merchantqrshow.mvp.contract.PersonnelManagerContract;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;

import okhttp3.Call;
import okhttp3.Request;
import rx.Observable;
import rx.Subscriber;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class EmployeeManagerManagerPresenter extends PersonnelManagerContract.Presenter {


    private int page = 0;


    @Override
    public void getRegionList(User user, final LoadMode loadMode) {
        if (loadMode != LoadMode.UP_REFRESH) {
            page = 0;
        }
        OkHttpUtils.post().addParams("id", user.getBussinessId())
                .addParams("token", user.getToken())
                .addParams("id", user.getBussinessId())
                .addParams("pageIndex", page + "")
                .addParams("pageSize", "30")
                .addParams("type", "0")
                .url(NetConst.REGIONLIST).build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                if (loadMode == LoadMode.NOMAL) {
                    getmMvpView().showProgress();
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    RegionListResponse house = GsonUtil.changeGsonToBean(response, RegionListResponse.class);
                    page++;
                    getmMvpView().returnListResponse(house.getResult(), loadMode);


                }
            }

            @Override
            public void onAfter() {
                super.onAfter();

                getmMvpView().returnRefresh();
                getmMvpView().dismissProgress();
            }
        });
    }

    @Override
    public void initData(final Context context, final User user) {

        Observable<Object> register = EventRxBus.getInstance().register(IntentFlag.SALES_DETAILS);
        register.subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                loadPersonDetail(context, user, (String) o);
            }
        });
    }


    private void loadPersonDetail(final Context context, User user, final String id) {
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", id)
                .url(NetConst.PERSONDETAIL).build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                getmMvpView().showProgress();

            }

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    PersonDetailResponse house = GsonUtil.changeGsonToBean(response, PersonDetailResponse.class);
                    PersonDetail result = house.getResult();

                    AddEmployeeActivity.startAction(context, result, id);




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
