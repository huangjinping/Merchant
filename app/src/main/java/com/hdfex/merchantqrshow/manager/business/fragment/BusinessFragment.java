package com.hdfex.merchantqrshow.manager.business.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessForCurOrderResult;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessForHouseResult;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessForTotalOrderResult;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessForWarnResult;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessOrder;
import com.hdfex.merchantqrshow.manager.business.activity.ManagerOrderActivity;
import com.hdfex.merchantqrshow.mvp.contract.BussinessContract;
import com.hdfex.merchantqrshow.mvp.presenter.BusunessPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.EventRxBus;

/**
 * author Created by harrishuang on 2017/5/27.
 * email : huangjinping@hdfex.com
 * 业务管理
 */

public class BusinessFragment extends BaseFragment implements View.OnClickListener, BussinessContract.View {

    private LinearLayout layout_commpany;
    private TextView txt_overDueCustCount;
    private TextView txt_repayCustCount;
    private TextView txt_renewalCount;
    private TextView txt_curPassCount;
    private TextView txt_curOrderCount;
    private TextView txt_backOrderCount;
    private TextView txt_loanedCount;
    private TextView txt_loaned;
    private TextView txt_cancelOrderCount;
    private TextView txt_cancelOrder;
    private TextView txt_rejectOrderCount;
    private TextView txt_rejectOrder;
    private TextView txt_emptyHouse;
    private TextView txt_emptyRate;
    private TextView txt_houseCount;
    private BussinessContract.Presenter persenter;
    private TextView txt_bussiness_name;
    private TextView txt_overDueRate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        persenter = new BusunessPresenter();
        persenter.attachView(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        layout_commpany = (LinearLayout) view.findViewById(R.id.layout_commpany);
        layout_commpany.setOnClickListener(this);
        txt_overDueCustCount = (TextView) view.findViewById(R.id.txt_overDueCustCount);
        txt_repayCustCount = (TextView) view.findViewById(R.id.txt_repayCustCount);
        txt_renewalCount = (TextView) view.findViewById(R.id.txt_renewalCount);
        txt_curPassCount = (TextView) view.findViewById(R.id.txt_curPassCount);
        txt_curOrderCount = (TextView) view.findViewById(R.id.txt_curOrderCount);
        txt_backOrderCount = (TextView) view.findViewById(R.id.txt_backOrderCount);
        txt_loanedCount = (TextView) view.findViewById(R.id.txt_loanedCount);
        txt_loaned = (TextView) view.findViewById(R.id.txt_loaned);
        txt_loaned.setOnClickListener(this);
        txt_cancelOrderCount = (TextView) view.findViewById(R.id.txt_cancelOrderCount);
        txt_cancelOrder = (TextView) view.findViewById(R.id.txt_cancelOrder);
        txt_cancelOrder.setOnClickListener(this);
        txt_rejectOrderCount = (TextView) view.findViewById(R.id.txt_rejectOrderCount);
        txt_rejectOrder = (TextView) view.findViewById(R.id.txt_rejectOrder);
        txt_rejectOrder.setOnClickListener(this);
        txt_emptyHouse = (TextView) view.findViewById(R.id.txt_emptyHouse);
        txt_emptyRate = (TextView) view.findViewById(R.id.txt_emptyRate);
        txt_houseCount = (TextView) view.findViewById(R.id.txt_houseCount);
        txt_bussiness_name = (TextView) view.findViewById(R.id.txt_bussiness_name);
        txt_overDueRate = (TextView) view.findViewById(R.id.txt_overDueRate);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_loaned:
                ManagerOrderActivity.startAction(getActivity(), BusinessOrder.STATUS_LOANED);
                break;
            case R.id.txt_cancelOrder:
                ManagerOrderActivity.startAction(getActivity(), BusinessOrder.STATUS_CANCEL);
                break;
            case R.id.txt_rejectOrder:
                ManagerOrderActivity.startAction(getActivity(), BusinessOrder.STATUS_REJECT);
                break;
            case R.id.layout_commpany:
                EventRxBus.getInstance().post(IntentFlag.INDEX_DRAWER, IntentFlag.INDEX_DRAWER);
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        persenter.loadData(getActivity());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (this.isResumed() && !hidden) {
            persenter.loadData(getActivity());
        }

    }

    /**
     * 刷新数据
     */
    public void reLoad() {
        persenter.loadData(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        persenter.detachView();
    }

    @Override
    public void returnBusinessForWarn(BusinessForWarnResult result) {
        if (!TextUtils.isEmpty(result.getOverDueCustCount())) {
            txt_overDueCustCount.setText(result.getOverDueCustCount()+"/");
        }
        if (!TextUtils.isEmpty(result.getRenewalCount())) {
            txt_renewalCount.setText(result.getRenewalCount());
        }
        if (!TextUtils.isEmpty(result.getRepayCustCount())) {
            txt_repayCustCount.setText(result.getRepayCustCount());
        }
    }

    @Override
    public void returnCurOrder(BusinessForCurOrderResult result) {
        if (!TextUtils.isEmpty(result.getCurOrderCount())) {
            txt_curOrderCount.setText(result.getCurOrderCount());
        }
        if (!TextUtils.isEmpty(result.getBackOrderCount())) {
            txt_loanedCount.setText(result.getBackOrderCount());
        }
        if (!TextUtils.isEmpty(result.getCurPassCount())) {
            txt_curPassCount.setText(result.getCurPassCount());
        }
    }

    @Override
    public void returnTotalOrder(BusinessForTotalOrderResult result) {
        if (!TextUtils.isEmpty(result.getLoanedCount())) {
            txt_backOrderCount.setText(result.getLoanedCount());
        }



        if (!TextUtils.isEmpty(result.getCancelOrderCount())) {
            txt_cancelOrderCount.setText(result.getCancelOrderCount());
        }

        if (!TextUtils.isEmpty(result.getOverDueRate())){
            txt_overDueRate.setText(result.getOverDueRate()+"%");
        }
        if (!TextUtils.isEmpty(result.getRejectOrderCount())) {
            txt_rejectOrderCount.setText(result.getRejectOrderCount());
        }
    }

    @Override
    public void returnforHouse(BusinessForHouseResult result) {
        if (!TextUtils.isEmpty(result.getEmptyHouse())) {
            txt_emptyHouse.setText(result.getEmptyHouse());
        }
        if (!TextUtils.isEmpty(result.getEmptyRate())) {
            txt_emptyRate.setText(result.getEmptyRate()+"%");
        }
        if (!TextUtils.isEmpty(result.getHouseCount())) {
            txt_houseCount.setText(result.getHouseCount());
        }
    }

    @Override
    public void returnBusinessName(String bussinessName) {
        txt_bussiness_name.setText(bussinessName);
    }
}
