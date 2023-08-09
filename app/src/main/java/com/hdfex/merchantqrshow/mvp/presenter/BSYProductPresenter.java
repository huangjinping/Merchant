package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.salesman.order.activity.BSYOrderDetailsActivity;
import com.hdfex.merchantqrshow.salesman.add.activity.BSYProductDetailsActivity;
import com.hdfex.merchantqrshow.bean.salesman.bsy.ProductDetailResponse;
import com.hdfex.merchantqrshow.bean.salesman.bsy.ProductItem;
import com.hdfex.merchantqrshow.bean.salesman.bsy.SubmitResponse;
import com.hdfex.merchantqrshow.bean.salesman.bsy.SubmitResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.view.BSYProductView;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.widget.picker.AddressInitTask;
import com.hdfex.merchantqrshow.widget.picker.AddressPicker;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 *
 *
 * Created by harrishuang on 2016/11/24.
 */

public class BSYProductPresenter  extends BasePresenter<BSYProductView>{
    /**
     * 下载用户
     * @param user
     */
    public  void  loadData(User user,String commodityId){
        OkHttpUtils
                .post()
                .url(NetConst.BSY_DETAIL)
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("commodityId",commodityId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        BSYProductPresenter.this.getmMvpView().dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        BSYProductPresenter.this.getmMvpView().showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            ProductDetailResponse res = GsonUtil.changeGsonToBean(response, ProductDetailResponse.class);
                            ProductItem result = res.getResult();
                            BSYProductPresenter.this.getmMvpView().onResult(result);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 打开地址
     */
    public void    openAddrProvince(){
        new AddressInitTask((BSYProductDetailsActivity)BSYProductPresenter.this.getmMvpView(), new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(String province, String city, String county) {
                BSYProductPresenter.this.getmMvpView().onLocationResult( province,  city,  county);
            }

            @Override
            public void onAddressPickedCode(String provinceCode, String cityCode, String countyCode) {
                BSYProductPresenter.this.getmMvpView().onlocationCode(provinceCode,cityCode,countyCode);
            }
        }).execute("北京市", "北京市", "东城区");
    }

    /**
     * 提交数据
     * @param user
     * @param params
     */
    public void  onSubmit(User user, Map<String,String> params){
        OkHttpUtils
                .post()
                .url(NetConst.BSY_LIST_ORDER)
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("commodityId",params.get("commodityId"))
                .addParams("commodityCount",params.get("commodityCount"))
                .addParams("totalAmount",params.get("totalAmount"))
                .addParams("bussinessId",params.get("bussinessId"))
                .addParams("custName",params.get("custName"))
                .addParams("phoneNo",params.get("phoneNo"))
                .addParams("provinceCode",params.get("provinceCode"))
                .addParams("cityCode",params.get("provinceCode"))
                .addParams("areaCode",params.get("areaCode"))
                .addParams("address",params.get("address"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        BSYProductPresenter.this.getmMvpView().dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        BSYProductPresenter.this.getmMvpView().showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            BSYProductDetailsActivity activity=(BSYProductDetailsActivity)BSYProductPresenter.this.getmMvpView();
                            boolean b = activity.checkResonse(response);
                            if (b){

                                SubmitResponse res = GsonUtil.changeGsonToBean(response, SubmitResponse.class);
                                SubmitResult result = res.getResult();
                                BSYOrderDetailsActivity.startSubmit((BSYProductDetailsActivity)BSYProductPresenter.this.getmMvpView(),result.getOrderId(),true);
                                activity.finish();
                            }
                            /**
                             * 提交订单
                             *
                             */
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }
}
