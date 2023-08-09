package com.hdfex.merchantqrshow.mvp.presenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessOrderResponse;
import com.hdfex.merchantqrshow.bean.manager.business.RegionForOrder;
import com.hdfex.merchantqrshow.bean.manager.business.RegionForOrderResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.manager.main.fragment.CommFragment;
import com.hdfex.merchantqrshow.mvp.contract.ManagerOrderContract;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class ManagerOrderPresenter extends ManagerOrderContract.Presenter {

    private int page = 0;


    private List<RegionForOrder> regionForOrderList;

    @Override
    public void loadOrderList(User user, String status, final LoadMode loadMode, String region) {
        if (loadMode != LoadMode.UP_REFRESH) {
            page = 0;
        }
        OkHttpUtils.post()
                .addParams("id", user.getCurrentBussinessId())
                .addParams("token", user.getToken())
                .addParams("pageSize", "10")
                .addParams("status", status)
                .addParams("pageIndex", page + "")
                .addParams("region", region)
                .url(NetConst.ORDER_LISTFORADMIN).build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                if (loadMode == LoadMode.NOMAL) {
                    getmMvpView().showProgress();
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                if (getmMvpView() != null) {
                    getmMvpView().showToast(e.getMessage());
                }
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    BusinessOrderResponse orderResponse = GsonUtil.changeGsonToBean(response, BusinessOrderResponse.class);
                    page++;
                    getmMvpView().returnOrderResponse(orderResponse.getResult(), loadMode);
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }


    @Override
    public void loadRegionForOrderList(User user, String status) {


        OkHttpUtils.post()
                .addParams("id", user.getBussinessId())
                .addParams("token", user.getToken())
                .addParams("status", status)
                .url(NetConst.REGION_FOR_ORDERLIST).build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onError(Call call, Exception e) {
                if (getmMvpView() != null) {
                    getmMvpView().showToast(e.getMessage());
                }
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    RegionForOrderResponse orderResponse = GsonUtil.changeGsonToBean(response, RegionForOrderResponse.class);
                    regionForOrderList = orderResponse.getResult();
                    getmMvpView().returnRegionForOrder(orderResponse.getResult());

                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
            }
        });
    }

    @Override
    public void reLoadRegionForOrderList(User user, String status) {


        if (regionForOrderList != null) {
            getmMvpView().returnReloadRegionForOrder(regionForOrderList);
return;
        }

        OkHttpUtils.post()
                .addParams("id", user.getBussinessId())
                .addParams("token", user.getToken())
                .addParams("status", status)
                .url(NetConst.REGION_FOR_ORDERLIST).build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onError(Call call, Exception e) {
                if (getmMvpView() != null) {
                    getmMvpView().showToast(e.getMessage());
                }
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    RegionForOrderResponse orderResponse = GsonUtil.changeGsonToBean(response, RegionForOrderResponse.class);
                    regionForOrderList = orderResponse.getResult();
                    getmMvpView().returnReloadRegionForOrder(orderResponse.getResult());

                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
            }
        });


    }

    @Override
    public void openMenu(final List<RegionForOrder> result, int id, FragmentManager supportFragmentManager) {

        if (result == null || result.size() == 0) {
            return;
        }

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            arrayList.add(result.get(i).getRegionName());
        }
        CommFragment fragment = CommFragment.getInstance();
        fragment.setCallBack(new CommFragment.CallBack() {
            @Override
            public void onCall(int position, String name) {
                RegionForOrder order = result.get(position);
                order.setRegion(order.getRegionName());
                if (position == 0) {
                    order.setRegion(null);
                }
                EventRxBus.getInstance().post(IntentFlag.REGION, result.get(position));
            }
        });
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(IntentFlag.COMM_LIST, arrayList);
        bundle.putString("title","请选择");
        fragment.setArguments(bundle);
        transaction.setCustomAnimations(
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer,
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer);
        transaction.replace(id, fragment);
        transaction.addToBackStack("CommFragment");
        transaction.commit();
    }


}
