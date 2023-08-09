package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.salesman.bsy.ProductItem;
import com.hdfex.merchantqrshow.bean.salesman.bsy.ProductListResponse;
import com.hdfex.merchantqrshow.bean.salesman.bsy.ProductListResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageListResponse;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageResult;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.view.BSYSelectView;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.UserManager;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 碧水源的选择
 * Created by harrishuang on 2016/11/24.
 */

public class BSYSelectPresenter extends BasePresenter<BSYSelectView> {
    /**
     *当前位置
     */
    private int pageIndex;

    public void loadData(final LoadMode loadMode, User user) {
        if (loadMode != LoadMode.UP_REFRESH) {
            pageIndex = 0;
        }
        OkHttpUtils
                .post()
                .url(NetConst.BSY_PRODUCT_LIST)
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("bussinessId", user.getBussinessId())
                .addParams("pageIndex", pageIndex+"")
                .addParams("pageSize", "10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        BSYSelectPresenter.this.getmMvpView().dismissProgress();
                        BSYSelectPresenter.this.getmMvpView().onAfter();


                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        BSYSelectPresenter.this.getmMvpView().showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        BSYSelectPresenter.this.getmMvpView().onEirr();
                        BSYSelectPresenter.this.getmMvpView().showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            ProductListResponse res = GsonUtil.changeGsonToBean(response, ProductListResponse.class);
                            ProductListResult result = res.getResult();
                            List<ProductItem> list = result.getList();
                            if (loadMode==LoadMode.UP_REFRESH){
                                pageIndex++;
                            }
                            BSYSelectPresenter.this.getmMvpView().getData(list,loadMode);
                        }catch (Exception e){

                            e.printStackTrace();
                        }
                    }
                });
    }

    public void loadRedPackage(final Context context) {
        if (!UserManager.isLogin(context)) {
            return;
        }
        User user = UserManager.getUser(context);
        OkHttpUtils
                .post()
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("openFlag", "0")
                .addParams("pageIndex",  0+ "")
                .addParams("pageSize", "15")
                .tag(context)
                .url(NetConst.RED_PACKAGE_LIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                    }
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                RedPackageListResponse house = GsonUtil.changeGsonToBean(response, RedPackageListResponse.class);
                                if (house.getResult() != null) {
                                    RedPackageResult result = house.getResult();
                                    List<RedPackage> redPacketList = result.getRedPacketList();
                                    if (redPacketList != null && redPacketList.size() != 0) {
                                        RedPackage redPackage = redPacketList.get(0);
                                        if (redPackage!=null){

                                            getmMvpView().showProgress();
                                            getmMvpView().showRedPackageDialog( redPackage);
                                        }
//                                        for (RedPackage redPackage:redPacketList) {
//                                            RedPackageDialog.getInstance(context,redPackage ).show();
//                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onAfter() {
                        super.onAfter();
                    }
                });
    }

}
