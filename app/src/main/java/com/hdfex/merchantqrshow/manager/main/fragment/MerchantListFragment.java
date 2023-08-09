package com.hdfex.merchantqrshow.manager.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.manager.main.Stores;
import com.hdfex.merchantqrshow.bean.manager.main.StoresListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.manager.main.adapter.MerchantListAdapter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/6/1.
 * email : huangjinping@hdfex.com
 */

public class MerchantListFragment extends BaseFragment implements View.OnClickListener {
    private MerchantListAdapter adapter;
    private ListView layout_merchant_list;
    private List<Stores> dataList;
    private ViewFooterHolder holder;
    public StoresListResponse res;
    public User user;
    private OnSelectedCallBack callBack;

    public void setCallBack(OnSelectedCallBack callBack) {
        this.callBack = callBack;
    }

    public static MerchantListFragment getInstance(StoresListResponse res) {
        MerchantListFragment fragment = new MerchantListFragment();
        fragment.res = res;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchantlist, container, false);
        initView(view);
        user = UserManager.getUser(getActivity());
        dataList = new ArrayList<>();
        dataList.add(new Stores(user.getBussinessId(), user.getBussinessName()));
        dataList.addAll(res.getResult());
        adapter = new MerchantListAdapter(getActivity(), dataList);
        layout_merchant_list.setAdapter(adapter);
        layout_merchant_list.addFooterView(initFooterView());
        layout_merchant_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Stores stores = dataList.get(position);
                user.setCurrentBussinessName(stores.getBussinessName());
                user.setCurrentBussinessId(stores.getBussinessId());
                UserManager.saveUser(getActivity(), user);
                onCancel();
                EventRxBus.getInstance().post(IntentFlag.BUSSINESS_ID, stores);
                if (callBack != null) {
                    callBack.onCall(stores);
                }
            }
        });
        return view;
    }

    private View initFooterView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_merchantlistfooter, null);
        holder = new ViewFooterHolder(view);
        return view;
    }

    private void initView(View view) {
        layout_merchant_list = (ListView) view.findViewById(R.id.layout_merchant_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                onCancel();

                break;
        }
    }

    /**
     * 退出当前界面
     */
    private void onCancel() {
        getActivity().getSupportFragmentManager().popBackStack();

    }

    public class ViewFooterHolder {
        public View rootView;
        public Button btn_cancel;

        public ViewFooterHolder(View rootView) {
            this.rootView = rootView;
            this.btn_cancel = (Button) rootView.findViewById(R.id.btn_cancel);
            this.btn_cancel.setOnClickListener(MerchantListFragment.this);
        }
    }

    /**
     * 调用回调
     */
    public interface OnSelectedCallBack {
        void onCall(Stores stores);
    }
}
