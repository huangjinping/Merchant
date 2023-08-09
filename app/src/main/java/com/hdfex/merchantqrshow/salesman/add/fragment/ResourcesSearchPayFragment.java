package com.hdfex.merchantqrshow.salesman.add.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.ItemHouse;
import com.hdfex.merchantqrshow.mvp.presenter.ResourcesPayListPresenter;
import com.hdfex.merchantqrshow.mvp.view.ResoutcesPayListView;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.my.adapter.ResourcesAdapter;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.xlistView.XListView;
import com.hdfex.merchantqrshow.widget.MultiStateView;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2018/6/22.
 * email : huangjinping@hdfex.com
 */
public class ResourcesSearchPayFragment extends BaseFragment implements ResoutcesPayListView, View.OnClickListener {

    private ImageView img_back;
    private EditText edt_resources_search;
    private TextView txt_search;
    private MultiStateView multiStateView;
    private XListView lisv_resource_list;
    private ListView lisv_seatch_resoult;
    private LinearLayout layout_search;
    private ResourcesAdapter adapter;
    private List<ItemHouse> dataList;
    public String status = "1";
    private ResourcesPayListPresenter presenter;
    private User user;
    private LinearLayout layout_root;


    public static ResourcesSearchPayFragment getInstance() {
        ResourcesSearchPayFragment fragement = new ResourcesSearchPayFragment();
        return fragement;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resources_search, container, false);
        initView(view);
        user = UserManager.getUser(getContext());
        presenter = new ResourcesPayListPresenter();
        presenter.attachView(this);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        dataList = new ArrayList<>();

        adapter = new ResourcesAdapter(getContext(), dataList, status);
        lisv_resource_list.setPullLoadEnable(false);
        lisv_resource_list.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                presenter.requestResourcesByStatusAndSearch(status, edt_resources_search.getText().toString(), user, LoadMode.PULL_REFRSH);
            }

            @Override
            public void onLoadMore() {
                presenter.requestResourcesByStatusAndSearch(status, edt_resources_search.getText().toString(), user, LoadMode.UP_REFRESH);
            }
        });

        lisv_resource_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    ItemHouse itemHouse = dataList.get(position - 1);
                    EventRxBus.getInstance().post(IntentFlag.INTENT_HOUSE, itemHouse);
                    getActivity().finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        lisv_resource_list.setAdapter(adapter);


        edt_resources_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = edt_resources_search.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        return true;
                    }
                    ((InputMethodManager) edt_resources_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getActivity().getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
//                    layout_search.setVisibility(View.GONE);
//                    layout_content_view.setVisibility(View.VISIBLE);
                    submit();
                    return true;
                }
                return false;
            }
        });
        return view;
    }


    private void initView(View view) {
        img_back = (ImageView) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        edt_resources_search = (EditText) view.findViewById(R.id.edt_resources_search);
        txt_search = (TextView) view.findViewById(R.id.txt_search);
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
        lisv_resource_list = (XListView) view.findViewById(R.id.lisv_resource_list);
        lisv_seatch_resoult = (ListView) view.findViewById(R.id.lisv_seatch_resoult);
        layout_search = (LinearLayout) view.findViewById(R.id.layout_search);
        layout_root = (LinearLayout) view.findViewById(R.id.layout_root);
        layout_root.setOnClickListener(this);
        txt_search.setOnClickListener(this);
    }


    private void submit() {
        // validate
        String search = edt_resources_search.getText().toString().trim();
        if (TextUtils.isEmpty(search)) {
            showToast("请输入小区或公寓进行搜索，可精确到房间");
            return;
        }

        presenter.requestResourcesByStatusAndSearch(status, edt_resources_search.getText().toString(), user, LoadMode.NOMAL);

    }


    @Override
    public void onResume() {
        super.onResume();
//        presenter.requestResourcesByStatusAndSearch(status, edt_resources_search.getText().toString(), user, LoadMode.NOMAL);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }

    @Override
    public void returnItemHouseList(List<ItemHouse> result, LoadMode loadMode) {
        if (loadMode != LoadMode.UP_REFRESH) {
            dataList.clear();
        }
        dataList.addAll(result);
        if (dataList.size() > 14) {
            lisv_resource_list.setPullLoadEnable(true);
        } else {
            lisv_resource_list.setPullLoadEnable(false);
        }

        if (dataList.size() == 0) {
            onEmpty();

        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onAfter() {
        onLoad();
    }


    private void onLoad() {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    lisv_resource_list.stopRefresh();
                    lisv_resource_list.stopLoadMore();
                    lisv_resource_list.setRefreshTime("刚刚");
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });
    }

    public void onEmpty() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_search:
                submit();
                break;
            case R.id.img_back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
