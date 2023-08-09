package com.hdfex.merchantqrshow.manager.finance.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.manager.finance.Finance;
import com.hdfex.merchantqrshow.utils.NumberToCN;

import java.math.BigDecimal;
import java.util.List;

/**
 * author Created by harrishuang on 2017/6/8.
 * email : huangjinping@hdfex.com
 */

public class FinanceBottomFragment extends BaseFragment {


    private TextView txt_title;
    private TextView txt_amt;
    private TextView txt_hanamt;
    public String type;

    public static FinanceBottomFragment getInstance(String type) {
        FinanceBottomFragment fragment = new FinanceBottomFragment();
        fragment.type = type;
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_financebottom, container, false);
        initView(view);
        if ("0".equals(type)) {
            txt_title.setText("总成交金额");

        } else if ("1".equals(type)) {
            txt_title.setText("件均");
        } else if ("2".equals(type)) {
            txt_title.setText("贷款余额");
        }
        setParam();
        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            setParam();
        }

    }

    private void initView(View view) {
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_amt = (TextView) view.findViewById(R.id.txt_amt);
        txt_hanamt = (TextView) view.findViewById(R.id.txt_hanamt);
    }


    public void setParam() {
        Finance result = FinanceFragment.result;
        if (result == null) {
            return;
        }
        if ("0".equals(type)) {

            if (!TextUtils.isEmpty(result.getTotalAmt())) {
                txt_amt.setText((result.getTotalAmt()));
                String number = NumberToCN.number2CNMontrayUnit(new BigDecimal(result.getTotalAmt()));
                txt_hanamt.setText(number);
            }
        } else if ("1".equals(type)) {

            if (!TextUtils.isEmpty(result.getPerAmt())) {
                Log.d("OKHTTP","===========>"+result.getPerAmt());
                txt_amt.setText((result.getPerAmt()));
                String number = NumberToCN.number2CNMontrayUnit(new BigDecimal(result.getPerAmt()));
                txt_hanamt.setText(number);
            }

        } else if ("2".equals(type)) {


            if (!TextUtils.isEmpty(result.getLoanBal())) {
                txt_amt.setText((result.getLoanBal()));
                String number = NumberToCN.number2CNMontrayUnit(new BigDecimal(result.getLoanBal()));
                txt_hanamt.setText(number);
            }
        }
    }
}
