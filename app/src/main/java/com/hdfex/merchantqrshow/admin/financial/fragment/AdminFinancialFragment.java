package com.hdfex.merchantqrshow.admin.financial.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.financial.activity.BalancePaymentActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.admin.bam.QueryFinanceInfo;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.AdminFinancialContract;
import com.hdfex.merchantqrshow.mvp.presenter.AdminFinancialPresenter;
import com.hdfex.merchantqrshow.utils.UserManager;

/**
 * 财务服务页面
 * author Created by harrishuang on 2017/12/12.
 * email : huangjinping@hdfex.com
 */

public class AdminFinancialFragment extends BaseFragment implements View.OnClickListener, AdminFinancialContract.View {

    private AdminFinancialContract.Presenter presenter;
    private LinearLayout layout_balance;
    private TextView txt_withdrawals;
    private User user;
    private TextView txt_totalRedPacketAmt;
    private TextView txt_totalAmount;
    private TextView txt_curRedPacketAmt;
    private TextView txt_alipayNo;
    private LinearLayout layout_packetAmt;

    public static AdminFinancialFragment getInstance() {
        AdminFinancialFragment fragment = new AdminFinancialFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgament_adminfinancial, container, false);
        presenter = new AdminFinancialPresenter();
        presenter.attachView(this);
        user = UserManager.getUser(getActivity());
        initView(view);
        resetData();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_packetAmt:
                BalancePaymentActivity.startAction(getActivity());
                break;
            case R.id.txt_withdrawals:
                presenter.loadWithdrawDeposit(user, getActivity());
                break;
        }

    }

    private void initView(View view) {
        layout_balance = (LinearLayout) view.findViewById(R.id.layout_balance);
        txt_withdrawals = (TextView) view.findViewById(R.id.txt_withdrawals);
        txt_withdrawals.setOnClickListener(this);
        txt_totalRedPacketAmt = (TextView) view.findViewById(R.id.txt_totalRedPacketAmt);
        txt_totalAmount = (TextView) view.findViewById(R.id.txt_totalAmount);
        txt_curRedPacketAmt = (TextView) view.findViewById(R.id.txt_curRedPacketAmt);
        txt_alipayNo = (TextView) view.findViewById(R.id.txt_alipayNo);
        layout_packetAmt = (LinearLayout) view.findViewById(R.id.layout_packetAmt);
        layout_packetAmt.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadQueryFinanceInfo();
    }

    /**
     * 4.37.22.	财务管理（管理员）
     */
    private void loadQueryFinanceInfo() {
        if (presenter!=null){
            presenter.loadQueryFinanceInfo(getActivity());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden && getActivity() != null) {
            loadQueryFinanceInfo();
        }
    }

    /**
     * 重置数据
     */
    private void resetData() {
        txt_totalRedPacketAmt.setText("0.00");
        txt_totalAmount.setText("0.00");
        txt_curRedPacketAmt.setText("0.00");

    }


    @Override
    public void returnQueryFinanceInfo(QueryFinanceInfo result) {
        if (result != null) {
            if (!TextUtils.isEmpty(result.getTotalRedPacketAmt())) {
                txt_totalRedPacketAmt.setText(result.getTotalRedPacketAmt());
            }
            if (!TextUtils.isEmpty(result.getTotalAmount())) {
                txt_totalAmount.setText(result.getTotalAmount());
            }
            if (!TextUtils.isEmpty(result.getCurRedPacketAmt())) {
                txt_curRedPacketAmt.setText(result.getCurRedPacketAmt());
            }
            if (!TextUtils.isEmpty(result.getAlipayNo())) {
                txt_alipayNo.setText("支付宝收款账号："+result.getAlipayNo());
            }
        }
    }
}
