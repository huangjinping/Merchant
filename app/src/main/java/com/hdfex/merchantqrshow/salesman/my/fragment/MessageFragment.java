package com.hdfex.merchantqrshow.salesman.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.message.MessageItem;
import com.hdfex.merchantqrshow.mvp.contract.MessageContract;
import com.hdfex.merchantqrshow.mvp.presenter.MessagePresenter;
import com.hdfex.merchantqrshow.salesman.my.adapter.MessageAdapter;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.xlistView.XListView;
import com.hdfex.merchantqrshow.widget.MultiStateView;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class MessageFragment extends BaseFragment implements MessageContract.View {
    private String type;
    private MultiStateView multiStateView;
    private XListView lisv_message_list;
    private MessageContract.Presenter presenter;
    private MessageAdapter adapter;
    private List<MessageItem> dataList;
    private User user;


    public static BaseFragment getInstance(String type) {
        MessageFragment messageFragment = new MessageFragment();
        messageFragment.type = type;
        return messageFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initView(view);
        presenter = new MessagePresenter();
        presenter.attachView(this);
        user = UserManager.getUser(getActivity());
        dataList = new ArrayList<>();
        adapter = new MessageAdapter(getActivity(), dataList);
        lisv_message_list.setAdapter(adapter);
        lisv_message_list.setPullLoadEnable(false);
        lisv_message_list.setPullRefreshEnable(false);
        onEmpty();
        lisv_message_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    presenter.loadMessageDetails(getActivity(), user, dataList.get(position - 1));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.requestMessageList(getActivity(), user, type);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initView(View view) {
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
        lisv_message_list = (XListView) view.findViewById(R.id.lisv_message_list);
    }

    @Override
    public void returnMessageList(List<MessageItem> result) {
        dataList.clear();
        if (result != null) {
            dataList.addAll(result);
        }
        if (dataList.size() == 0) {
            onEmpty();
        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }

        Log.d("okhttp", "======>>>" + dataList.toString());
        adapter.notifyDataSetChanged();
    }

    public void onEmpty() {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        });
    }
}
