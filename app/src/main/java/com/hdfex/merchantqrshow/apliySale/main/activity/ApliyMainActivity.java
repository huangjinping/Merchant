package com.hdfex.merchantqrshow.apliySale.main.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.apliySale.main.fragment.ApliyMainFragment;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.manager.business.fragment.BusinessFragment;
import com.hdfex.merchantqrshow.manager.finance.fragment.FinanceFragment;
import com.hdfex.merchantqrshow.manager.team.fragment.TeamFragment;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.salesman.customer.fragment.CustomerFragment;
import com.hdfex.merchantqrshow.salesman.main.fragment.MainFragment;
import com.hdfex.merchantqrshow.salesman.my.fragment.MyFragment;
import com.hdfex.merchantqrshow.salesman.order.fragment.OrderAllFragment;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.TabEntity;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.update.UpdataUtil;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * author Created by harrishuang on 2018/1/3.
 * email : huangjinping@hdfex.com
 * 花呗业务员首页
 */

public class ApliyMainActivity extends BaseActivity {

    private LinearLayout layout_container;
    private CommonTabLayout layout_tab_com;
    private ArrayList<Fragment> fragmentsList;
    private FragmentManager supportFragmentManager;
    private int mIndex = 0;
    private DrawerLayout layout_drawer;
    private UpdataUtil mUpdataUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managerindex);
        initHomeRxPermissions();
        String upateUrl = NetConst.BASEURL + getString(R.string.upateUrl);
        mUpdataUtil = new UpdataUtil(this, upateUrl);
        initView();
        initFragment();
        initTab();
        setTabSelection(0);
        EventRxBus.getInstance().register(IntentFlag.INDEX_DRAWER).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {
                if (layout_drawer.isDrawerOpen(Gravity.LEFT)) {
                    layout_drawer.closeDrawers();
                } else {
                    layout_drawer.openDrawer(Gravity.LEFT);
                }
            }
        });
        Observable<Object> register = EventRxBus.getInstance().register(IntentFlag.BUSSINESS_ID);
        register.subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {
                /**
                 * 触发了信息刷新
                 */
                for (Fragment mFragment : fragmentsList) {
                    if (mFragment.isResumed()) {
                        if (mFragment instanceof BusinessFragment) {
                            BusinessFragment businessFragment = (BusinessFragment) mFragment;
                            businessFragment.reLoad();
                        }
                        if (mFragment instanceof TeamFragment) {
                            TeamFragment teamFragment = (TeamFragment) mFragment;
                            teamFragment.reLoad();
                        }
                        if (mFragment instanceof FinanceFragment) {
                            TeamFragment teamFragment = (TeamFragment) mFragment;
                            teamFragment.reLoad();
                        }
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        String upateUrl = NetConst.BASEURL + getString(R.string.upateUrl);
//        UpdataUtil mUpdataUtil = new UpdataUtil(ApliyMainActivity.this, upateUrl);
//        mUpdataUtil.getVersion();
        mUpdataUtil.getVersionAfter26(new UpdataUtil.CallBack() {
            @Override
            public void onCall() {
                checkInstall();
            }
        });
    }


    /**
     * 提交是不是安装
     */
    private void checkInstall() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean hasInstallPerssion = getPackageManager().canRequestPackageInstalls();
            if (hasInstallPerssion) {
                //安装应用的逻辑
                // 检查版本
                mUpdataUtil.checkUpdate();
            } else {
                //跳转至“安装未知应用”权限界面，引导用户开启权限，可以在onActivityResult中接收权限的开启结果
                mUpdataUtil.installAlert(this, Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, REQUEST_CODE_UNKNOWN_APP);
            }
        } else {
            // 检查版本
            mUpdataUtil.checkUpdate();
        }
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        supportFragmentManager = getSupportFragmentManager();
        fragmentsList = new ArrayList<>();
        ApliyMainFragment apliyMainFragment = new ApliyMainFragment();
        OrderAllFragment orderFragment = new OrderAllFragment();
        CustomerFragment customerFragment = new CustomerFragment();
        MyFragment myFragment = new MyFragment();
        MainFragment mainFragment = new MainFragment();


        fragmentsList.add(apliyMainFragment);
        fragmentsList.add(orderFragment);
        fragmentsList.add(mainFragment);
        fragmentsList.add(myFragment);
    }


    /**
     * 设置tab页面
     */
    private void initTab() {
        ArrayList<CustomTabEntity> tabDataList = new ArrayList<CustomTabEntity>();
        tabDataList.add(new TabEntity("业务管理", R.mipmap.ic_add_selected, R.mipmap.ic_add_normal));
        tabDataList.add(new TabEntity("订单", R.mipmap.ic_order_selected, R.mipmap.ic_order_normal));
        tabDataList.add(new TabEntity("统计", R.mipmap.ic_customer_selected, R.mipmap.ic_customer_normal));
        tabDataList.add(new TabEntity("我的", R.mipmap.ic_my_selected, R.mipmap.ic_my_noramal));
        layout_tab_com.setTabData(tabDataList);
        layout_tab_com.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        setTabSelection(0);
                        break;
                    case 1:

                        setTabSelection(1);
                        break;
                    case 2:
                        setTabSelection(2);

                        break;
                    case 3:
                        setTabSelection(3);

                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        layout_tab_com.setCurrentTab(0);
//        layout_tab_com.showDot(0);
    }


    /**
     * @param index
     */
    private void setTabSelection(int index) {
        mIndex = index;
        FragmentTransaction beginTransaction = supportFragmentManager
                .beginTransaction();
        hideFragments(beginTransaction);
        Fragment fragment = fragmentsList.get(index);
        if (fragment.isAdded()) {
            beginTransaction.show(fragment);
        } else {
            beginTransaction.add(R.id.layout_container, fragment);
            beginTransaction.show(fragment);
        }
        beginTransaction.commit();
    }


    private void hideFragments(FragmentTransaction transaction) {
        for (Fragment fragment : fragmentsList) {
            transaction.hide(fragment);
        }
    }


    private void initView() {
        layout_container = (LinearLayout) findViewById(R.id.layout_container);
        layout_tab_com = (CommonTabLayout) findViewById(R.id.layout_tab_com);
        layout_drawer = (DrawerLayout) findViewById(R.id.layout_drawer);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("index", mIndex);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        mIndex = savedInstanceState.getInt("index");
        setTabSelection(mIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventRxBus.getInstance().unregister(IntentFlag.BUSSINESS_ID);
    }


    private long firstClick = 0;

    @Override
    public void onBackPressed() {
        long secondClick = System.currentTimeMillis();
        if (secondClick - firstClick > 2000) {
            ToastUtils.d(ApliyMainActivity.this,
                    getResources().getString(R.string.exit)).show();
            firstClick = secondClick;
        } else { // 此处添加退出后台
//            super.onBackPressed(); android.os.Process.killProcess(android.os.Process.myPid());
//            finish();
//            moveTaskToBack(true);
            finish();
//            System.exit(0);

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UNKNOWN_APP) {
            checkInstall();
            return;
        }
    }
}
