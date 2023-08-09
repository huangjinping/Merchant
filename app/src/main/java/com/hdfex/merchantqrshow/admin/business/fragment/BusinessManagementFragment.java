package com.hdfex.merchantqrshow.admin.business.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cameroon.banner.Banner;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.business.activity.AdminBusinessActivity;
import com.hdfex.merchantqrshow.admin.business.activity.AdminOrderActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.EventRxBus;

/**
 * author Created by harrishuang on 2017/12/19.
 * email : huangjinping@hdfex.com
 */

public class BusinessManagementFragment extends BaseFragment implements View.OnClickListener {


    private Banner ban_username;
    private TextView tv_payment;
    private LinearLayout ll_bill;
    private LinearLayout layout_order_manager;
    private LinearLayout layout_merchant_manager;

    public static BusinessManagementFragment getIntance() {
        BusinessManagementFragment fragment = new BusinessManagementFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_management, container, false);
        initView(view);
//        //设置图片加载器
//        ban_username.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        ban_username.setImages(images);
//        //banner设置方法全部调用完毕时最后调用
//        ban_username.start();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        EventRxBus.getInstance().post(IntentFlag.RED_LOAD, IntentFlag.RED_LOAD);
    }

    private void initView(View view) {
        ban_username = (Banner) view.findViewById(R.id.ban_username);
        tv_payment = (TextView) view.findViewById(R.id.tv_payment);
        ll_bill = (LinearLayout) view.findViewById(R.id.ll_bill);
        layout_order_manager = (LinearLayout) view.findViewById(R.id.layout_order_manager);
        layout_order_manager.setOnClickListener(this);
        layout_merchant_manager = (LinearLayout) view.findViewById(R.id.layout_merchant_manager);
        layout_merchant_manager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_order_manager:
                AdminOrderActivity.startAction(getActivity());
                break;
            case R.id.layout_merchant_manager:
//                showToast("暂未开通");
                AdminBusinessActivity.startAction(getActivity());
                break;
        }

    }
}
