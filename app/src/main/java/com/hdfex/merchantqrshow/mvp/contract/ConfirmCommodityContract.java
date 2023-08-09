package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.huabei.PayOrderQueryResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;
import com.hdfex.merchantqrshow.salesman.add.activity.ConfirmCommodityActivity;

import java.util.Map;

/**
 * author Created by harrishuang on 2017/6/21.
 * email : huangjinping@hdfex.com
 */

public interface ConfirmCommodityContract {






    interface View extends MvpView {

        void returnProceduresType(String s, int index);

        void returnPayOrderQuery(PayOrderQueryResult result, String type);

        void returnSubmitSuccess();

        void returnOrder();

        void returnSanpay(Response res);


        void showLoading();

        void dismissLoading();

        void returnTimeout();

        void returnPayEirr(Response res);
    }

    abstract class Presenter extends BasePresenter<ConfirmCommodityContract.View> {

        public abstract void selectProcedures(Context context, FragmentManager supportFragmentManager);

        public abstract void loadOrderList(Context context, User user);

        public abstract void payOrderQuery(Context context, User user, String id, String orderId);

        public abstract void finishOrder(Context context, User user, String orderId);

        public abstract void showUnComplate(Context context);
        /**
         * 花呗提交查询
         */
        public final   String  ACTION_SUBMIT_HUABEI="ACTION_SUBMIT_HUABEI";
        /**
         * 淘宝提交查询
         */
        public final    String  ACTION_SUBMIT_TAOBAO="ACTION_SUBMIT_TAOBAO";
        /**
         * 单纯的提交查询
         */
        public final   String  ACTION_QUERY_SHOW="ACTION_QUERY_SHOW";

        /**
         * 花呗支付
         */
        public final String PAY_FLAG_HUABEI = "00";
        /**
         * 支付宝支付
         */
        public final String PAY_FLAG_TAOBAO = "01";


        /**
         *V1花呗支付
         * @param context
         * @param params
         */
        public abstract void submitData(Context context, Map<String, String> params);

        /**
         * V1支付宝二维码生成
         * @param context
         * @param params
         */
        public abstract void submitOnPrecreate(Context context, Map<String, String> params);
        public abstract void payUpDateOrderQuery(Context context, User user, Order order);

        /**
         * 花呗支付V2
         * @param context
         * @param params
         */
        public abstract void scanPayV3(Context context, Map<String, String> params);

        /**
         * 4.38.13.	预生成订单V2（花呗分期）
         * @param context
         * @param params
         */
        public abstract void preSubmitOrderV2(Context context, Map<String, String> params);
    }
}
