package com.hdfex.merchantqrshow.salesman.my.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;
import com.hdfex.merchantqrshow.salesman.my.fragment.SignResultFragment;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * author Created by harrishuang on 2019/5/31.
 * email : huangjinping1000@163.com
 */
public class SignResultActivity extends BaseActivity implements View.OnClickListener {
    private final String[] mTitles = {
            "已签约", "未签约"
    };
    private SlidingTabLayout tab_layout_header;
    private ViewPager vip_container;
    private List<BaseFragment> fragmentList;
    private SlidingAdapter mAdapter;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private RxPermissions rxPermissions;
    private int mIndex = 0;


    public static final void startAction(Context context) {
        Intent intent = new Intent(context, SignResultActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ActionSheetStyle);

        setContentView(R.layout.activity_signresult);
        initView();
        fragmentList = new ArrayList<>();
        fragmentList.add(SignResultFragment.getInstance("1"));
        fragmentList.add(SignResultFragment.getInstance("0"));
        mAdapter = new SlidingAdapter(getSupportFragmentManager(), fragmentList, mTitles);
        vip_container.setAdapter(mAdapter);
        tab_layout_header.setViewPager(vip_container, mTitles);
        tb_tv_titile.setText("签约结果");
        rxPermissions = RxPermissions.getInstance(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (!aBoolean) {
                    showPermissDeniedAlert();
                }
            }
        });

//        tab_layout_header.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                tab_layout_header.setCurrentTab(position);
//            }
//            @Override
//            public void onTabReselect(int position) {
//
//            }
//        });

    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        vip_container = (ViewPager) findViewById(R.id.vip_container);
        tab_layout_header = (SlidingTabLayout) findViewById(R.id.tab_layout_header);
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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("index", mIndex);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        try {
            mIndex = savedInstanceState.getInt("index");
            tab_layout_header.setCurrentTab(mIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
