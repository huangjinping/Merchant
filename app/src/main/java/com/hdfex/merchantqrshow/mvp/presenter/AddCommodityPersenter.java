package com.hdfex.merchantqrshow.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.hdfex.merchantqrshow.bean.admin.bam.BrandListBean;
import com.hdfex.merchantqrshow.bean.admin.bam.CommodityCategoryListBean;
import com.hdfex.merchantqrshow.bean.admin.bam.FindCommodityDetail;
import com.hdfex.merchantqrshow.bean.admin.bam.FindCommodityDetailResponse;
import com.hdfex.merchantqrshow.bean.admin.bam.QueryBussinessCaseInfo;
import com.hdfex.merchantqrshow.bean.admin.bam.QueryBussinessCaseInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.AddCommodityContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.ActionSheet;
import com.hdfex.merchantqrshow.widget.picker.CategoryPicker;
import com.hdfex.merchantqrshow.widget.picker.FlowlayoutPicker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2017/12/7.
 * email : huangjinping@hdfex.com
 */

public class AddCommodityPersenter extends AddCommodityContract.Presenter {
    private List<CommodityCategoryListBean> commodityCategoryList;
    private List<BrandListBean> brandList;
    private User user;

    @Override
    public void loadDetails(Context context, String commodityId) {
        /**
         * 获取商品详情
         */
        user = UserManager.getUser(context);
        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("commodityId", commodityId)
                .url(NetConst.FIND_COMMODITY_DETAIL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (getmMvpView().checkResonse(response)) {
                        FindCommodityDetailResponse response1 = GsonUtil.changeGsonToBean(response, FindCommodityDetailResponse.class);
                        FindCommodityDetail result = response1.getResult();
                        commodityCategoryList = result.getCommodityCategoryList();
                        brandList = result.getBrandList();
                        getmMvpView().returnFindCommodityDetail(result);
                    }
                } catch (Exception e) {
                    getmMvpView().showToast("数据异常");
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

    @Override
    public void showActionCatrgory(Context context, String commodityId, FragmentManager supportFragmentManager) {
        if (commodityCategoryList == null || commodityCategoryList.size() == 0) {
            loadCommodityCategory(context, commodityId, supportFragmentManager);
            return;
        }
        selectCatrgory(context, commodityId, supportFragmentManager);
    }

    @Override
    public void showBrand(Activity context, final List<BrandListBean> brandList) {

        List<String> dataArr = new ArrayList<>();
        for (BrandListBean bean : brandList) {
            dataArr.add(bean.getBrand());
        }

        CategoryPicker picker = new CategoryPicker(context, dataArr);
        picker.setOnItemClickListener(new CategoryPicker.OnItemClickLitener() {
            @Override
            public void onSelected(int selectedIndex) {
                getmMvpView().returnBrand(brandList.get(selectedIndex));
            }
        });
        picker.show();

    }

    private List<QueryBussinessCaseInfo> bussinessCaseInfos;

    @Override
    public void showCasePicker(final Activity context, String commodityPrice) {


//        if (bussinessCaseInfos != null && bussinessCaseInfos.size() >= 0) {
//            showFlowPicker(context);
//            return;
//        }
        user = UserManager.getUser(context);
        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("id", user.getBussinessId())
                .addParams("token", user.getToken())
                .addParams("commodityPrice", commodityPrice)
                .url(NetConst.BUSSINESS_CASE_INFO).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (getmMvpView().checkResonse(response)) {
                        QueryBussinessCaseInfoResponse response1 = GsonUtil.changeGsonToBean(response, QueryBussinessCaseInfoResponse.class);
                        bussinessCaseInfos = response1.getResult();
                        showFlowPicker(context);
                    }
                } catch (Exception e) {
                    getmMvpView().showToast("数据异常");
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
     * @param context
     */
    private void showFlowPicker(Activity context) {
        List<String> caseIds = new ArrayList<>();
        if (bussinessCaseInfos == null && bussinessCaseInfos.size() == 0) {
            return;
        }
        for (int i = 0; i < bussinessCaseInfos.size(); i++) {
            QueryBussinessCaseInfo info = bussinessCaseInfos.get(i);
            caseIds.add(info.getPerAmount() + "X" + info.getCaseDuration() + "期，服务费" + info.getPerFee() + "/期");
        }

        FlowlayoutPicker picker = new FlowlayoutPicker(context, caseIds);
        picker.setOnResultListener(new FlowlayoutPicker.OnResultListener() {
            @Override
            public void onResult(Set<Integer> set) {
                Iterator<Integer> it = set.iterator();
                StringBuffer buffer = new StringBuffer();
                StringBuffer caseIds = new StringBuffer();
                while (it.hasNext()) {
                    Integer position = it.next();
                    QueryBussinessCaseInfo queryBussinessCaseInfo = bussinessCaseInfos.get(position);
                    buffer.append(queryBussinessCaseInfo.getCaseDuration()+"");
                    buffer.append("/");
                    caseIds.append(queryBussinessCaseInfo.getCaseId()+"").append(",");
                }

                if (buffer.length() > 1) {
                    buffer.deleteCharAt(buffer.length() - 1);
                }

                if (caseIds.length() > 1) {
                    caseIds.deleteCharAt(caseIds.length() - 1);
                }

                getmMvpView().returnFlowPicker(buffer.toString(), caseIds.toString());
            }
        });
        picker.show();

    }


    private void selectCatrgory(Context context, String commodityId, FragmentManager supportFragmentManager) {
        if (commodityCategoryList == null || commodityCategoryList.size() == 0) {
            return;
        }
        final String[] dataArr = new String[commodityCategoryList.size()];
        for (int i = 0; i < commodityCategoryList.size(); i++) {
            CommodityCategoryListBean commodityCategoryListBean = commodityCategoryList.get(i);
            dataArr[i] = commodityCategoryListBean.getCategoryName();
        }
        ActionSheet.createBuilder(context, supportFragmentManager)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                getmMvpView().reutrnHouseType(dataArr[index], index);
            }
        }).show();
    }


    private void loadCommodityCategory(final Context context, final String commodityId, final FragmentManager supportFragmentManager) {
        /**
         * 获取商品详情
         */
        user = UserManager.getUser(context);
        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("commodityId", commodityId)
                .url(NetConst.FIND_COMMODITY_DETAIL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (getmMvpView().checkResonse(response)) {
                        FindCommodityDetailResponse response1 = GsonUtil.changeGsonToBean(response, FindCommodityDetailResponse.class);
                        FindCommodityDetail result = response1.getResult();
                        commodityCategoryList = result.getCommodityCategoryList();
                        brandList = result.getBrandList();
                        selectCatrgory(context, commodityId, supportFragmentManager);
                        getmMvpView().returnFindCommodityDetail(result);

                    }
                } catch (Exception e) {
                    getmMvpView().showToast("数据异常");
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
}
