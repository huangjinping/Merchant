package com.hdfex.merchantqrshow.salesman.add.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.Apartment;
import com.hdfex.merchantqrshow.mvp.presenter.ApartmentPresenter;
import com.hdfex.merchantqrshow.mvp.view.ApartmentView;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.xlistView.XListView;
import com.hdfex.merchantqrshow.widget.MultiStateView;

import java.util.List;

/**
 * Created by harrishuang on 2017/3/20.
 */
public class ApartmentActivity extends BaseActivity implements ApartmentView {

    private ImageView img_back;
    private TextView txt_left_name;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private MultiStateView multiStateView;
    private XListView lisv_resource_list;
    private ApartmentPresenter persenter;
    private User user;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_apartment);
        initView();
        user = UserManager.getUser(this);
        persenter = new ApartmentPresenter();
        persenter.attachView(this);
        persenter.requestApartmentList(user);
    }






    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        multiStateView = (MultiStateView) findViewById(R.id.multiStateView);
        lisv_resource_list = (XListView) findViewById(R.id.lisv_resource_list);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        persenter.detachView();
    }



    @Override
    public void returnAparmentList(List<Apartment> result) {

    }
}
