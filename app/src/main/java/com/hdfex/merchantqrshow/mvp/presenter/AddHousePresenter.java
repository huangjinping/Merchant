package com.hdfex.merchantqrshow.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.Apartment;
import com.hdfex.merchantqrshow.bean.salesman.resource.ApartmentResponse;
import com.hdfex.merchantqrshow.bean.salesman.resource.CreateHouseResponse;
import com.hdfex.merchantqrshow.bean.salesman.resource.CreateHouseResult;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.view.AddHouseView;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.widget.ActionSheet;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by harrishuang on 2017/2/17.
 */

public class AddHousePresenter extends BasePresenter<AddHouseView> {
    /**
     * @param param
     */
    public void addHouse(Map<String, String> param) {
        getmMvpView().showProgress();
        Log.d("okhttp", param.toString());
        OkHttpUtils.post().params(getParseMap(param)).url(NetConst.CREATEHOUSE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    CreateHouseResponse house = GsonUtil.changeGsonToBean(response, CreateHouseResponse.class);
                    CreateHouseResult result = house.getResult();
                    getmMvpView().returnAddCreateHouse(result);
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
     * @param user
     * @param commodityId
     */
    public void deleteHouse(User user, String commodityId) {

        OkHttpUtils.post().addParams("id", user.getId()).addParams("token", user.getToken()).addParams("commodityId", commodityId).url(NetConst.DELEHOUSE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    CreateHouseResponse house = GsonUtil.changeGsonToBean(response, CreateHouseResponse.class);
                    CreateHouseResult result = house.getResult();
                    getmMvpView().returnDeleteHouse(result);
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
     * @param param
     */
    public void editHouse(Map<String, String> param) {
        Log.d("okhttp", getParseMap(param).toString());
        getmMvpView().showProgress();
        OkHttpUtils.post().params(getParseMap(param)).url(NetConst.MODHOUSE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    CreateHouseResponse house = GsonUtil.changeGsonToBean(response, CreateHouseResponse.class);
                    CreateHouseResult result = house.getResult();
                    getmMvpView().returnAddCreateHouse(result);
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
     * @param param
     * @return
     */
    public Map<String, String> getParseMap(Map<String, String> param) {
        for (Map.Entry<String, String> entry : param.entrySet()) {
            if (entry.getValue() == null) {
                param.put(entry.getKey(), "");
            }
        }
        return param;

    }

    public void showDeleteAlert(Activity context,FragmentManager fragmentManager) {

        final String[] dataArr = {"删除房间信息"};
        ActionSheet.createBuilder(context, fragmentManager)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {

                getmMvpView().deleteHouse();

            }
        }).show();
//        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
//        dialogBuilder
//                .withTitle("信息提示")
//                .withTitleColor("#FFFFFF")
//                .withMessage("确定删除当前房间信息吗?")
//                .withMessageColor("#000000")
//                .withDialogColor("#FFFFFF")
//                .withIcon(context.getResources().getDrawable(R.mipmap.ic_merchant_logo))
//                .isCancelableOnTouchOutside(true)
//                .withDuration(100)
//                .withButton1Text("否")
//                .withButton2Text("是")
//                .isCancelable(false)
//                .isCancelableOnTouchOutside(false)
//                .setButton1Click(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogBuilder.dismiss();
//                    }
//                })
//                .setButton2Click(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogBuilder.dismiss();
//                        getmMvpView().deleteHouse();
//                    }
//                })
//                .show();

    }

    /**
     * @param context
     * @param fragmentManager
     */
    public void showActionSheet(Context context, FragmentManager fragmentManager) {
        final String[] dataArr = {"集中式", "分散式"};
        ActionSheet.createBuilder(context, fragmentManager)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                getmMvpView().houseType(dataArr[index], index);
            }
        }).show();
    }


    private List<Apartment> result;

    /**
     * @param context
     * @param user
     */
    public void selectCentraliseHouse(Context context, User user, final int type) {
        if (result != null) {
            getmMvpView().returnApartment(result,type);
            return;
        }

        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("bussinessId", user.getBussinessId())
                .url(NetConst.GET_APARTMENT_LIST).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    ApartmentResponse house = GsonUtil.changeGsonToBean(response, ApartmentResponse.class);
                    result=house.getResult();
                    if (result!=null){
                        getmMvpView().returnApartment(house.getResult(),type);
                    }
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
     * @param context
     * @param fragmentManager
     */
    public void showLeaseMode(Context context, FragmentManager fragmentManager) {
        final String[] dataArr = {"合租", "整租"};
        ActionSheet.createBuilder(context, fragmentManager)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
//                getmMvpView().houseType(dataArr[index], index);
                getmMvpView().returnShowLeaseMode(index,dataArr[index]);
            }
        }).show();
    }

}
