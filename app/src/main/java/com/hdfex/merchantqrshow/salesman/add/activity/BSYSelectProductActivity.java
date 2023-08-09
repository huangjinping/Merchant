package com.hdfex.merchantqrshow.salesman.add.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.salesman.add.adapter.BSYSelectAdapter;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.bsy.ProductItem;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.presenter.BSYSelectPresenter;
import com.hdfex.merchantqrshow.mvp.view.BSYSelectView;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.xlistView.XListView;
import com.hdfex.merchantqrshow.widget.MultiStateView;

import java.util.ArrayList;
import java.util.List;

/**
 * 碧水源选择商品
 * Created by harrishuang on 2016/11/24.
 */

public class BSYSelectProductActivity extends BaseActivity implements BSYSelectView, View.OnClickListener, AdapterView.OnItemClickListener {

    private BSYSelectAdapter adapter;
    private ImageView img_back;
    private TextView txt_left_name;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private XListView lisv_selectproduct;
    private BSYSelectPresenter presenter;
    private List<ProductItem> dataList;
    private User user;
    private MultiStateView multiStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsyselectproduct);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tb_tv_titile.setText("选择商品");
        user = UserManager.getUser(this);
        dataList = new ArrayList<>();
        adapter = new BSYSelectAdapter(dataList, this);
        lisv_selectproduct.setAdapter(adapter);
        presenter = new BSYSelectPresenter();
        presenter.attachView(this);
        presenter.loadData(LoadMode.NOMAL, user);
    }

    /**
     * 初始化数据
     */
    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        lisv_selectproduct = (XListView) findViewById(R.id.lisv_selectproduct);
        img_back.setOnClickListener(this);
        lisv_selectproduct.setOnItemClickListener(this);
        multiStateView = (MultiStateView) findViewById(R.id.multiStateView);
        multiStateView.setOnClickListener(this);
        multiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (connect()) {
                            presenter.loadData(LoadMode.NOMAL, user);
                        }
                    }
                });
//        lisv_selectproduct.setPullLoadEnable(false);
        lisv_selectproduct.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                presenter.loadData(LoadMode.NOMAL, user);

            }

            @Override
            public void onLoadMore() {
                presenter.loadData(LoadMode.PULL_REFRSH, user);
            }
        });
    }


    @Override
    public void getData(List<ProductItem> list, LoadMode mode) {
        try {
            if (list != null) {
                if (LoadMode.UP_REFRESH != mode) {
                    dataList.clear();
                }
                dataList.addAll(list);
                adapter.notifyDataSetChanged();
                if (dataList.size()==10){
                    lisv_selectproduct.setPullLoadEnable(true);
                }else {
                    lisv_selectproduct.setPullLoadEnable(false);
                }
                if (dataList.size()!=0){
                    onContentView();
                }else {
                    onEmpty();
                }
            }
        } catch (Exception e) {
            showWebEirr();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            if (dataList.size()>0){
                ProductItem productItem = dataList.get(position - 1);
                BSYProductDetailsActivity.start(productItem,this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onEmpty() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

//                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);

            }
        });
    }

    @Override
    public void onContentView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

            }
        });
    }

    @Override
    public void onEirr() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);

            }
        });
    }

    @Override
    public void onAfter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    lisv_selectproduct.stopRefresh();
                    lisv_selectproduct.stopLoadMore();
                    lisv_selectproduct.setRefreshTime("刚刚");
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadRedPackage(this);
    }
}
