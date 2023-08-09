package com.hdfex.merchantqrshow.mvp.view;

import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.TypeModel;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.mvp.MvpView;

import java.util.List;

/**
 * Created by harrishuang on 2017/2/20.
 */

public interface CreateCommodityView extends MvpView {

    void returnEndDate(String endDate);

    void returnDurationLists(List<TypeModel> durationLists);

    void returnDownpaymentLists(List<TypeModel> downpaymentLists);

    void showRedPackageDialog(RedPackage redPackage);
}
