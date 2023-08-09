package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.HouseListResponse;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.view.ResoutcesListView;
import com.hdfex.merchantqrshow.mvp.view.ResoutcesPayListView;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;

import okhttp3.Call;

/**
 * Created by harrishuang on 2017/2/20.
 */

public class ResourcesPayListPresenter extends BasePresenter<ResoutcesPayListView> {
    private int page = 0;

    public void requestResourcesByStatus(String status, User user, final LoadMode loadMode) {
        if (loadMode != LoadMode.UP_REFRESH) {
            page = 0;
        }
        OkHttpUtils.post().url(NetConst.HOUSE_NEW_LIST)
                .addParams("id", user.getId())
                .addParams("status", status)
                .addParams("bussinessId", user.getBussinessId())
                .addParams("token", user.getToken())
                .addParams("pageSize", "20")
                .addParams("pageIndex", page + "")
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        try {
                            getmMvpView().onAfter();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        try {
                            getmMvpView().showWebEirr(e);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                HouseListResponse rep = GsonUtil.changeGsonToBean(response, HouseListResponse.class);
                                page++;
                                getmMvpView().returnItemHouseList(rep.getResult(), loadMode);
                            }
                        } catch (Exception e) {
                            onError(null, e);
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void requestResourcesByStatusAndSearch(String status, String search, User user, final LoadMode loadMode) {
        if (loadMode != LoadMode.UP_REFRESH) {
            page = 0;
        }

        if (loadMode==LoadMode.NOMAL){
            getmMvpView().showProgress();
        }

        OkHttpUtils.post().url(NetConst.HOUSE_LIST)
                .addParams("id", user.getId())
                .addParams("status", status)
                .addParams("bussinessId", user.getBussinessId())
                .addParams("token", user.getToken())
                .addParams("pageSize", "20")
                .addParams("pageIndex", page + "")
                .addParams("search", search)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        try {
                            getmMvpView().onAfter();
                            getmMvpView().dismissProgress();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        try {
                            getmMvpView().showWebEirr(e);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                HouseListResponse rep = GsonUtil.changeGsonToBean(response, HouseListResponse.class);
                                page++;
                                getmMvpView().returnItemHouseList(rep.getResult(), loadMode);
                            }
                        } catch (Exception e) {
                            onError(null, e);
                            e.printStackTrace();
                        }
                    }
                });
    }
}
