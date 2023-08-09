package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.AdditionalInfo;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.AdditionalResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CaluateDueDateResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.TypeModel;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageListResponse;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageResult;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.view.CreateCommodityView;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.activity.CreateCommodityActivity;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by harrishuang on 2017/2/20.
 */

public class CreateCommodityPresenter extends BasePresenter<CreateCommodityView> {
    public List<TypeModel> downpaymentLists;
    public List<TypeModel> durationLists;
    public final  static int NORMAL_TYPE = 0;

    public final  static int DOWNPAYMENT_TYPE = 1;

    public final  static int DURATION_TYPE = 2;

    /**
     * @param
     * @param startDate
     * @param payTypeCode
     * @param duration
     */
    public void requestCalculateDueDate(User user, String startDate, String payTypeCode, String duration) {
        getmMvpView().showProgress();
        OkHttpUtils.post().url(NetConst.CALCULATE_DUEDATE).addParams("id", user.getId()).addParams("token", user.getToken()).addParams("startDate", startDate).addParams("payTypeCode", payTypeCode).addParams("duration", duration).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {

                    if (getmMvpView().checkResonse(response)) {
                        CaluateDueDateResponse resp = GsonUtil.changeGsonToBean(response, CaluateDueDateResponse.class);
                        if (resp.getResult() == null) {
                            getmMvpView().showWebEirr(null);
                            return;
                        }
                        getmMvpView().returnEndDate(resp.getResult().getEndDate());

                    }
                } catch (Exception e) {
                    getmMvpView().showWebEirr(e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });

    }

    /**
     * 下载专案
     * @param user
     * @param code
     */
    public void requestAdditional(User user, int code) {
        if (code == DOWNPAYMENT_TYPE&&downpaymentLists!=null) {
            getmMvpView().returnDownpaymentLists(downpaymentLists);
            return;
        } else if (code == DURATION_TYPE&&durationLists!=null) {
            getmMvpView().returnDurationLists(durationLists);
            return;
        }

        OkHttpUtils.post()
                .url(NetConst.QUERY_BASEDATA)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("bussinessId", user.getBussinessId())
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showWebEirr(e);
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                AdditionalResponse additionalResponse = GsonUtil.changeGsonToBean(response, AdditionalResponse.class);
                                AdditionalInfo info = additionalResponse.getResult();
                                if (info.getPayTypeList() != null) {
                                    downpaymentLists = info.getPayTypeList();
                                }
                                if (info.getDurationList() != null) {
                                    durationLists = info.getDurationList();
                                }
                            }
                        } catch (Exception e) {
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

    /**
     * 弹出提示框
     * @param context
     */
    public void showAlert(Context context) {

        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("核对租房地址，确保与纸质合同一致\n核对租期，确保在纸质合同租期范围内\n合同照片清晰、完整\n客户签字清晰可辨认")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(context.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
                .withButton1Text("我知道了")
                .withMessageGravity(Gravity.LEFT)
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                }).show();

    }
}
