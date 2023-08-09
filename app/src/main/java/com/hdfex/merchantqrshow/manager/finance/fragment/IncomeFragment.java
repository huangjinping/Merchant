package com.hdfex.merchantqrshow.manager.finance.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.manager.finance.activity.ExpenditureActivity;
import com.hdfex.merchantqrshow.manager.finance.activity.IncomeActivity;
import com.hdfex.merchantqrshow.manager.finance.adapter.ExpenditureAdapter;
import com.hdfex.merchantqrshow.manager.finance.adapter.IncomeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/6/1.
 * email : huangjinping@hdfex.com
 */

public class IncomeFragment extends BaseFragment {

    private ListView lisv_payments;
    private IncomeAdapter adapter;
    private List<String> dataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        initView(view);
        dataList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("i");
        }
        adapter=new IncomeAdapter(dataList,getActivity());
        lisv_payments.setAdapter(adapter);
        lisv_payments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IncomeActivity.startAction(getActivity());
            }
        });
        return view;
    }

    public static BaseFragment getInstance() {
        IncomeFragment payment = new IncomeFragment();
        return payment;
    }
    private void initView(View view) {
        lisv_payments = (ListView) view.findViewById(R.id.lisv_payments);
    }
}
