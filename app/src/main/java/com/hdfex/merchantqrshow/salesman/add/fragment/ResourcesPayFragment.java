package com.hdfex.merchantqrshow.salesman.add.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.ItemHouse;
import com.hdfex.merchantqrshow.mvp.presenter.ResourcesListPresenter;
import com.hdfex.merchantqrshow.mvp.presenter.ResourcesPayListPresenter;
import com.hdfex.merchantqrshow.mvp.view.ResoutcesPayListView;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.add.activity.AddHouseActivity;
import com.hdfex.merchantqrshow.salesman.my.adapter.ResourcesAdapter;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.xlistView.XListView;
import com.hdfex.merchantqrshow.widget.MultiStateView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrishuang on 2017/2/9.
 * <p>
 * 房源碎片列表
 */

public class ResourcesPayFragment extends BaseFragment implements ResoutcesPayListView {

    private ResourcesAdapter adapter;
    private List<ItemHouse> dataList;
    public String status;
    private MultiStateView multiStateView;
    private ResourcesPayListPresenter presenter;
    private User user;
    private XListView lisv_resource_list;
    public boolean canSelect = false;

    public boolean canSearch = false;
    private EditText edt_order_search;
    private TextView txt_search;
    private LinearLayout layout_delete;
    private LinearLayout layout_my_search;


    public void setCanSelect(boolean canSelect) {
        this.canSelect = canSelect;
        if (adapter != null) {
            adapter.setShowArrow(!canSelect);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * @param status
     * @return
     */
    public static ResourcesPayFragment getInstance(String status) {
        ResourcesPayFragment fragment = new ResourcesPayFragment();
        fragment.status = status;
        return fragment;
    }


    /**
     * @param status
     * @return
     */
    public static ResourcesPayFragment getInstance(String status, boolean canSelect, boolean canSearch) {
        ResourcesPayFragment fragment = new ResourcesPayFragment();
        fragment.canSelect = canSelect;
        fragment.status = status;
        fragment.canSearch = canSearch;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resources, container, false);
        initView(view);
        user = UserManager.getUser(getContext());
        presenter = new ResourcesPayListPresenter();
        presenter.attachView(this);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        dataList = new ArrayList<>();
        if (canSelect) {
            layout_my_search.setVisibility(View.VISIBLE);
        } else {
            layout_my_search.setVisibility(View.GONE);
        }
        adapter = new ResourcesAdapter(getContext(), dataList, status);
        lisv_resource_list.setPullLoadEnable(false);
        lisv_resource_list.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                presenter.requestResourcesByStatus(status, user, LoadMode.PULL_REFRSH);
            }

            @Override
            public void onLoadMore() {
                presenter.requestResourcesByStatus(status, user, LoadMode.UP_REFRESH);
            }
        });

        lisv_resource_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    ItemHouse itemHouse = dataList.get(position - 1);


                    if (canSelect) {
                        EventRxBus.getInstance().post(IntentFlag.INTENT_HOUSE, itemHouse);
                        getActivity().finish();
                    } else {
//                        if (IntentFlag.HOSUE_TYPE_FREE.equals(status)) {
//                            itemHouse.setStatus(status);
//                            AddHouseActivity.start(getActivity(), itemHouse);
//                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        lisv_resource_list.setAdapter(adapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.requestResourcesByStatus(status, user, LoadMode.NOMAL);
    }

    private void initView(View view) {
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
        lisv_resource_list = (XListView) view.findViewById(R.id.lisv_resource_list);


        edt_order_search = (EditText) view.findViewById(R.id.edt_order_search);
        txt_search = (TextView) view.findViewById(R.id.txt_search);
        layout_delete = (LinearLayout) view.findViewById(R.id.layout_delete);
        layout_my_search = (LinearLayout) view.findViewById(R.id.layout_my_search);

        edt_order_search.setFocusable(false);
        edt_order_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventRxBus.getInstance().post(IntentFlag.INTENT_OPEN_SEARCH, 1);

            }
        });
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


}
