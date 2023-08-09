package com.hdfex.merchantqrshow.salesman.add.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.AdditionalInfo;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.TypeModel;
import com.hdfex.merchantqrshow.mvp.presenter.ModifyPresenter;
import com.hdfex.merchantqrshow.mvp.view.ModifyView;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.widget.DeleteEditText;

import java.util.List;

/**
 * View 的弹出窗口
 * Created by harrishuang on 2017/2/14.
 */

public class ModifyInfomationFragment extends BaseFragment implements View.OnClickListener, ModifyView {

    public TextView txt_modify_cancel;
    public EditText et_from;
    public EditText et_pay_mode;
    public EditText et_pay_duration;
    public DeleteEditText et_down_payment;
    public Button btn_submit;
    private LinearLayout layout_poptop;
    private List<TypeModel> downpaymentLists;
    private List<TypeModel> durationLists;
    private ModifyPresenter presenter;

    public static ModifyInfomationFragment getInstance() {
        ModifyInfomationFragment fragement = new ModifyInfomationFragment();
        return fragement;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new ModifyPresenter();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modifyinfomation, container, false);
        initView(view);
        AdditionalInfo info = (AdditionalInfo) getArguments().getSerializable(IntentFlag.INTENT_ADDITIONAL);
        if (info.getPayTypeList() != null) {
            downpaymentLists = info.getPayTypeList();
        }
        if (info.getDurationList() != null) {
            durationLists = info.getDurationList();
        }
        et_from.setOnClickListener(this);
        return view;
    }

    private void initView(View view) {
        this.txt_modify_cancel = (TextView) view.findViewById(R.id.txt_modify_cancel);
        this.et_from = (EditText) view.findViewById(R.id.et_from);
        this.et_pay_mode = (EditText) view.findViewById(R.id.et_pay_mode);
        this.et_pay_duration = (EditText) view.findViewById(R.id.et_pay_duration);
        this.et_down_payment = (DeleteEditText) view.findViewById(R.id.et_down_payment);
        this.btn_submit = (Button) view.findViewById(R.id.btn_submit);
        layout_poptop = (LinearLayout) view.findViewById(R.id.layout_poptop);
        layout_poptop.setOnClickListener(this);
        txt_modify_cancel.setOnClickListener(this);
        et_pay_duration.setOnClickListener(this);
        et_pay_mode.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.et_from:
                presenter.getTime(et_from, getActivity());
                break;
            case R.id.layout_poptop:
            case R.id.txt_modify_cancel:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.et_pay_duration:
                presenter.dataPicker(durationLists, et_pay_duration, getActivity());
                break;
            case R.id.et_pay_mode:
                presenter.dataPicker(downpaymentLists, et_pay_mode, getActivity());
                break;
            case R.id.btn_submit:
                showToast("重新提交订单");
                break;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }
}
