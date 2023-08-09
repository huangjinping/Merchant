package com.hdfex.merchantqrshow.manager.finance.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.manager.finance.Finance;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.manager.finance.activity.PaymentsActivity;
import com.hdfex.merchantqrshow.manager.finance.activity.TransactionRecordActivity;
import com.hdfex.merchantqrshow.mvp.contract.FinanceContract;
import com.hdfex.merchantqrshow.mvp.presenter.FinancePresenter;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;
import com.hdfex.merchantqrshow.utils.DepthPageTransformer;
import com.hdfex.merchantqrshow.utils.NumberToCN;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.ViewPagerIndicator;
import com.hdfex.merchantqrshow.utils.ZoomOutPageTransformer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/5/27.
 * email : huangjinping@hdfex.com
 */

public class FinanceFragment extends BaseFragment implements View.OnClickListener, FinanceContract.View {

    private TextView tv_home;
    private LinearLayout layout_record;
    private LinearLayout layout_payment;
    private FinanceContract.Presenter presenter;
    private User user;
    private TextView txt_curdate;
    private TextView txt_curDateLoanAmt;
    private TextView txt_curMonthLoanAmt;
    private TextView txt_curMonthRefundAmt;
    private TextView txt_hancurDateLoanAmt;
    private TextView txt_desc;
    private SlidingAdapter adapter;
    private List<BaseFragment> fragmentList;
    private final String[] mTitles = {
            "0", "1", "2"
    };

    private ViewPager vip_finance;
    private LinearLayout layout_Indicator;
    public static Finance result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finance, container, false);
        initView(view);
        presenter = new FinancePresenter();
        presenter.attachView(this);
        user = UserManager.getUser(getActivity());
        txt_hancurDateLoanAmt.setText(NumberToCN.number2CNMontrayUnit(new BigDecimal("12341234.22")));
        fragmentList = new ArrayList<>();
        result = new Finance();
        fragmentList.add(FinanceBottomFragment.getInstance("0"));
        fragmentList.add(FinanceBottomFragment.getInstance("1"));
        fragmentList.add(FinanceBottomFragment.getInstance("2"));
        adapter = new SlidingAdapter(getChildFragmentManager(), fragmentList, mTitles);
        vip_finance.setAdapter(adapter);
        vip_finance.setOnPageChangeListener(new ViewPagerIndicator(getActivity(), vip_finance, layout_Indicator, 3));
        vip_finance.setPageTransformer(true,new ZoomOutPageTransformer());
        return view;
    }

    private void initView(View view) {
        tv_home = (TextView) view.findViewById(R.id.tv_home);
        layout_record = (LinearLayout) view.findViewById(R.id.layout_record);
        layout_record.setOnClickListener(this);
        layout_payment = (LinearLayout) view.findViewById(R.id.layout_payment);
        layout_payment.setOnClickListener(this);
        txt_curdate = (TextView) view.findViewById(R.id.txt_curdate);
        txt_curdate.setOnClickListener(this);
        txt_curDateLoanAmt = (TextView) view.findViewById(R.id.txt_curDateLoanAmt);
        txt_curDateLoanAmt.setOnClickListener(this);
        txt_curMonthLoanAmt = (TextView) view.findViewById(R.id.txt_curMonthLoanAmt);
        txt_curMonthLoanAmt.setOnClickListener(this);
        txt_curMonthRefundAmt = (TextView) view.findViewById(R.id.txt_curMonthRefundAmt);
        txt_curMonthRefundAmt.setOnClickListener(this);

        txt_hancurDateLoanAmt = (TextView) view.findViewById(R.id.txt_hancurDateLoanAmt);
        txt_hancurDateLoanAmt.setOnClickListener(this);

        txt_desc = (TextView) view.findViewById(R.id.txt_desc);
        txt_desc.setOnClickListener(this);
        vip_finance = (ViewPager) view.findViewById(R.id.vip_finance);
        layout_Indicator = (LinearLayout) view.findViewById(R.id.layout_Indicator);
        layout_Indicator.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_record:
                TransactionRecordActivity.startAction(getActivity());
                break;
            case R.id.layout_payment:
                PaymentsActivity.startAction(getActivity());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (this.isResumed() && !hidden) {
            reLoad();

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadFinanceManager(user);
    }

    /***
     * 重新刷新
     */
    public void reLoad() {
        user = UserManager.getUser(getActivity());
        presenter.loadFinanceManager(user);
    }

    @Override
    public void returnFinance(Finance result) {
        this.result = result;

        if (!TextUtils.isEmpty(result.getCurdate())) {
            txt_curdate.setText(result.getCurdate());
        }
        if (!TextUtils.isEmpty(result.getCurDateLoanAmt())) {
            txt_hancurDateLoanAmt.setVisibility(View.VISIBLE);
            txt_curDateLoanAmt.setVisibility(View.VISIBLE);
            txt_desc.setVisibility(View.GONE);
            txt_curDateLoanAmt.setText(StringUtils.formatDecimal(result.getCurDateLoanAmt()));
            String number = NumberToCN.number2CNMontrayUnit(new BigDecimal(result.getCurDateLoanAmt()));
            txt_hancurDateLoanAmt.setText(number);
        } else {
            if (!TextUtils.isEmpty(result.getDesc())) {
                txt_hancurDateLoanAmt.setVisibility(View.GONE);
                txt_curDateLoanAmt.setVisibility(View.GONE);
                txt_desc.setVisibility(View.VISIBLE);
                txt_desc.setText(result.getDesc());
            }
        }
        if (!TextUtils.isEmpty(result.getCurMonthLoanAmt())) {
            txt_curMonthLoanAmt.setText(StringUtils.formatDecimal(result.getCurMonthLoanAmt()));
        }
        if (!TextUtils.isEmpty(result.getCurMonthRefundAmt())) {
            txt_curMonthRefundAmt.setText(StringUtils.formatDecimal(result.getCurMonthRefundAmt()));
        }

        for (int i = 0; i < fragmentList.size(); i++) {
            FinanceBottomFragment bottomFragment = (FinanceBottomFragment) fragmentList.get(0);
            bottomFragment.setParam();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        FinanceFragment.result = null;

    }
}
