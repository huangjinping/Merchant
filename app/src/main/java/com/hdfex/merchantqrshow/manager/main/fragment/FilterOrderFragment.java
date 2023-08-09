package com.hdfex.merchantqrshow.manager.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.manager.business.RegionForOrder;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.manager.main.adapter.FilterOrderAdapter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/6/7.
 * email : huangjinping@hdfex.com
 */

public class FilterOrderFragment extends BaseFragment {

    private ListView lisv_filter;
    private List<RegionForOrder> dataList;
    private FilterOrderAdapter filterOrderAdapter;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filterorder, container, false);
        initView(view);
        user = UserManager.getUser(getActivity());
        dataList = new ArrayList<>();
        filterOrderAdapter = new FilterOrderAdapter(getActivity(), dataList);
        lisv_filter.setAdapter(filterOrderAdapter);
        lisv_filter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RegionForOrder order = dataList.get(position);
                order.setRegion(order.getRegionName());
                if (position == 0) {
                    order.setRegion(null);
                }
                EventRxBus.getInstance().post(IntentFlag.REGION, dataList.get(position));
            }
        });
        return view;
    }

    private void initView(View view) {
        lisv_filter = (ListView) view.findViewById(R.id.lisv_filter);
    }

    /**
     * 联系数据
     *
     * @param result
     */
    public void setDataList(List<RegionForOrder> result) {
        dataList.clear();
        dataList.addAll(result);
        filterOrderAdapter.notifyDataSetChanged();
    }
}
