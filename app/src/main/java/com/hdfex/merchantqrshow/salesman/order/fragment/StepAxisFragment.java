package com.hdfex.merchantqrshow.salesman.order.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.UserStep;
import com.hdfex.merchantqrshow.bean.salesman.order.UserStepResponse;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.order.adapter.StepAxisAdapter;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2019/9/5.
 * email : huangjinping1000@163.com
 */
public class StepAxisFragment extends BaseFragment {

    private RecyclerView rec_step_axis;
    private StepAxisAdapter adapter;
    private List<UserStep> dataList;
    private User user;
    public String packageId;

    public static BaseFragment getInstance(String packageId) {
        StepAxisFragment fragment = new StepAxisFragment();
        fragment.packageId = packageId;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_step_axis, container, false);
        initView(view);
        user = UserManager.getUser(getActivity());
        dataList = new ArrayList<>();
        adapter = new StepAxisAdapter(dataList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rec_step_axis.setLayoutManager(layoutManager);
        rec_step_axis.setAdapter(adapter);
        onStepAxis();
        return view;
    }


    private void initView(View view) {
        rec_step_axis = (RecyclerView) view.findViewById(R.id.rec_step_axis);
    }


    /**
     * 下载数据
     */
    private void onStepAxis() {
        showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("packageId", packageId)
                .url(NetConst.GET_ORDER_UNSUBMIT_ORDER_DETAIL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {

                try {
//                    if (checkResonse(response)) {
//
//                    }
                    UserStepResponse userStepResponse = GsonUtil.changeGsonToBean(response, UserStepResponse.class);
                    if (userStepResponse.getCode() == 0) {
                        List<UserStep> result = userStepResponse.getResult();
                        dataList.clear();
                        dataList.addAll(result);
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissProgress();
            }
        });
    }
}
