package com.hdfex.merchantqrshow.mvp.contract;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.hdfex.merchantqrshow.admin.bam.activity.AddCommodityActivity;
import com.hdfex.merchantqrshow.bean.admin.bam.BrandListBean;
import com.hdfex.merchantqrshow.bean.admin.bam.FindCommodityDetail;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/7.
 * email : huangjinping@hdfex.com
 */

public interface AddCommodityContract {
    interface View extends MvpView {

        void reutrnHouseType(String s, int index);

        void returnFindCommodityDetail(FindCommodityDetail result);

        void returnBrand(BrandListBean brandListBean);

        void returnFlowPicker(String s,String caseIds);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void loadDetails(Context context, String commodityId);

        public abstract void showActionCatrgory(Context context, String commodityId, FragmentManager supportFragmentManager);

        public abstract void showBrand(Activity context, List<BrandListBean> brandList);

        public abstract void showCasePicker(Activity activity,String commodityPrice);
    }
}
