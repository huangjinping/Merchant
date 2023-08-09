package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageListResponse;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageResult;
import com.hdfex.merchantqrshow.mvp.contract.AdminIndexContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import java.util.List;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2017/12/20.
 * email : huangjinping@hdfex.com
 */

public class AdminIndexPresenter extends AdminIndexContract.Presenter {
    @Override
    public void loadRedPackage(Context context) {
        if (!UserManager.isLogin(context)) {
            return;
        }
        User user = UserManager.getUser(context);
        OkHttpUtils
                .post()
                .addParams("id", user.getId())
                .addParams("bussinessId", user.getBussinessId())

                .addParams("token", user.getToken())
                .addParams("openFlag", "0")
                .addParams("pageIndex", 0 + "")
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
                                        if (redPackage != null) {
                                            getmMvpView().showRedPackageDialog(redPackage);
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
