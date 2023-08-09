package com.hdfex.merchantqrshow.salesman.add.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.salesman.add.adapter.ApartmentAdapter;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.Apartment;
import com.hdfex.merchantqrshow.bean.salesman.resource.ApartmentResponse;
import com.hdfex.merchantqrshow.mvp.presenter.ApartmentPresenter;
import com.hdfex.merchantqrshow.mvp.view.ApartmentView;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrishuang on 2017/3/20.
 */
public class ApartmentFragment extends BaseFragment implements ApartmentView, View.OnClickListener {

    private ApartmentPresenter persenter;
    private List<Apartment> dataList;
    private ListView lisv_apertment;
    private ApartmentAdapter apartmentAdapter;
    private Button btn_cancel;
    private User user;
    private LinearLayout layout_view_top;
    private LinearLayout layout_rootview;

    public static ApartmentFragment getInstance() {
        ApartmentFragment fragement = new ApartmentFragment();
        return fragement;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_apartment, container, false);
        initView(view);
        user = UserManager.getUser(getActivity());
        persenter = new ApartmentPresenter();
        persenter.attachView(this);
        dataList = new ArrayList<>();
        ApartmentResponse info = (ApartmentResponse) getArguments().getSerializable(IntentFlag.INTENT_HOUSE);
        dataList.addAll(info.getResult());
        apartmentAdapter = new ApartmentAdapter(getActivity(), dataList);
        lisv_apertment.setAdapter(apartmentAdapter);
        lisv_apertment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventRxBus.getInstance().post(IntentFlag.HOUSE_APARTMENT_EVENT,dataList.get(i));
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }

    @Override
    public void returnAparmentList(List<Apartment> result) {
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            layout_rootview.setBackgroundColor(getResources().getColor(R.color.transparent_quarter));
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        handler.sendMessageDelayed(handler.obtainMessage(),300);
    }

    @Override
    public void onPause() {
        super.onPause();
        layout_rootview.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    private void initView(View view) {
        lisv_apertment = (ListView) view.findViewById(R.id.lisv_apertment);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        layout_view_top = (LinearLayout) view.findViewById(R.id.layout_view_top);
        layout_view_top.setOnClickListener(this);
        layout_rootview = (LinearLayout) view.findViewById(R.id.layout_rootview);
        layout_rootview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.layout_view_top:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
