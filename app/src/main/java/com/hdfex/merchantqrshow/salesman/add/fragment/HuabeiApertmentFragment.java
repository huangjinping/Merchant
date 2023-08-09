package com.hdfex.merchantqrshow.salesman.add.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.add.adapter.InstallmentAdapter;
import com.hdfex.merchantqrshow.utils.EventRxBus;

import java.util.ArrayList;
import java.util.List;


/**
 * author Created by harrishuang on 2017/8/3.
 * email : huangjinping@hdfex.com
 */

public class HuabeiApertmentFragment extends BaseFragment implements View.OnClickListener {
    private ListView lisv_periods;
    private InstallmentAdapter adapter;
    private List<Installment> dataList;
    private int index = -1;
    private ImageView img_back;
    private View v_background;
    private ImageView iv_back;
    private LinearLayout ll_root;
    private Order order;

    public static BaseFragment getInstance() {
        HuabeiApertmentFragment fragment = new HuabeiApertmentFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.frgament_huabeiapert, container, false);
        initView(view);

        dataList = new ArrayList<>();
        adapter = new InstallmentAdapter(dataList, getActivity());
        if (getArguments().getSerializable(IntentFlag.INTENT_NAME) != null) {
            order= (Order) getArguments().getSerializable(IntentFlag.INTENT_NAME);
            index=getArguments().getInt(IntentFlag.INDEX,-1);
        }
        if (order != null && order.getList() != null) {
            dataList.addAll(order.getList());
        }
        lisv_periods.setAdapter(adapter);
        adapter.setIndex(index);
        adapter.notifyDataSetChanged();
        lisv_periods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventRxBus.getInstance().post(IntentFlag.CURRENT_DURATION, i);
                onBack();
            }
        });
        return view;
    }

    private void initView(View view) {
        v_background = (View) view.findViewById(R.id.v_background);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        lisv_periods = (ListView) view.findViewById(R.id.lisv_periods);
        ll_root = (LinearLayout) view.findViewById(R.id.ll_root);
        ll_root.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                    onBack();
                break;
        }
    }

    /**
     * 后退方法
     */
    private void onBack(){
        getActivity().getSupportFragmentManager().popBackStack();

    }


}
