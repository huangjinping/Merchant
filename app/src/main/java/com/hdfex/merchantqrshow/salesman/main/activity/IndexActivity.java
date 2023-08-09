package com.hdfex.merchantqrshow.salesman.main.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.activity.WebActivity;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.SystemConfigResponse;
import com.hdfex.merchantqrshow.bean.salesman.home.Notice;
import com.hdfex.merchantqrshow.bean.salesman.home.NoticeResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.AuthHt;
import com.hdfex.merchantqrshow.bean.salesman.login.AuthHtResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.IndexContract;
import com.hdfex.merchantqrshow.mvp.presenter.IndexPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.activity.BSYSelectProductActivity;
import com.hdfex.merchantqrshow.salesman.add.activity.CommodityListActivity;
import com.hdfex.merchantqrshow.salesman.add.activity.CreateCommodityActivity;
import com.hdfex.merchantqrshow.salesman.add.activity.CreateContractActivity;
import com.hdfex.merchantqrshow.salesman.appointment.fragment.ClueFragment;
import com.hdfex.merchantqrshow.salesman.customer.fragment.CustomerFragment;
import com.hdfex.merchantqrshow.salesman.main.fragment.MainFragment;
import com.hdfex.merchantqrshow.salesman.my.fragment.MyFragment;
import com.hdfex.merchantqrshow.salesman.order.activity.OrderListNewActvitity;
import com.hdfex.merchantqrshow.salesman.order.fragment.OrderCashListFragment;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.TabEntity;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.contract.ContactAddrUtil;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.utils.update.UpdataUtil;
import com.hdfex.merchantqrshow.view.RewardView;
import com.hdfex.merchantqrshow.widget.PermissionDialog;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yuyh.library.EasyGuide;
import com.yuyh.library.support.HShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * 首页
 * Created by harrishuang on 16/9/19.
 */

public class IndexActivity extends BaseActivity implements View.OnClickListener, MainFragment.OrderDelegate, IndexContract.View {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private LinearLayout layout_header;
    private CommonTabLayout layout_tab_com;
    private LinearLayout layout_container;
    private ImageView img_add;
    private ArrayList<Fragment> fragmentsList;
    private FragmentManager supportFragmentManager;
    private int mIndex = 0;
    private TextView txt_left_name;
    private LinearLayout layout_add;
    private OrderCashListFragment orderFragment;
    private long firstClick = 0;
    private LinearLayout layout_jiaoyu;
    private LinearLayout layout_zufang;
    private LinearLayout layout_jingshuiqi;
    private LinearLayout layout_content_view;
    private ImageView btn_close_menu;
    private boolean showMenu;
    private String[] sourceTypeArr;
    private DisplayMetrics displayMetrics;
    private IndexContract.Presenter presenter;
    private LinearLayout layout_pay_zufang;
    private RewardView rew_adview;
    private Notice notice;
    private UpdataUtil mUpdataUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        String upateUrl = NetConst.BASEURL + getString(R.string.upateUrl);
        mUpdataUtil = new UpdataUtil(IndexActivity.this, upateUrl);
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        presenter = new IndexPresenter();
        presenter.attachView(this);
        //检测版本和更新
        initView();
        initFragment();
        initTab();
        setTabSelection(0);
        setOnListenrs();
        EventRxBus.getInstance().register(IntentFlag.LOGOUT).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {
                finish();
            }
        });

        EventRxBus.getInstance().register(IntentFlag.RED_LOAD).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {
                presenter.loadRedPackage(IndexActivity.this);
            }
        });
        RxPermissions rxPermissions = RxPermissions.getInstance(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (!aBoolean) {
                    showPermissDeniedAlert();
                } else {
                    ContactAddrUtil addrUtil = new ContactAddrUtil();
                    addrUtil.getContractAuto(IndexActivity.this);
                }
            }
        });


//        AnimationActivity.startAction(this);
        rew_adview.setOnTargetClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notice != null) {
                    if (!TextUtils.isEmpty(notice.getUrl())) {
                        WebActivity.start(v.getContext(), notice.getTitle(), notice.getContentUrl());
                    }
                }
            }
        });
        loadAdView();
        getAppermissionDisplayVersion();
    }

    /**
     * 设置监听器
     */
    private void setOnListenrs() {
        img_add.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {// 加载完成后回调
                // 务必取消监听，否则会多次调用
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    img_add.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    img_add.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                if (UserManager.getGuide(IndexActivity.this)) {
//                    show();
                }
            }
        });
    }

    /**
     *
     */
    private void updatePromiss() {
        if (UserManager.isLogin(this)) {
            User user = UserManager.getUser(this);
            String sourceType = user.getSourceType();
            if (TextUtils.isEmpty(sourceType)) {
                return;
            }
            String[] arr = sourceType.split("[,]+");
            sourceTypeArr = arr;
            if (arr != null) {
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("2")) {
                        layout_zufang.setVisibility(View.VISIBLE);
                    } else if (arr[i].equals("3")) {
                        layout_jingshuiqi.setVisibility(View.VISIBLE);
                    } else if (arr[i].equals("10")) {
                        layout_pay_zufang.setVisibility(View.VISIBLE);
                    } else {
                        layout_jiaoyu.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }


    /**
     * 初始化Fragment
     */
    private void initFragment() {
        supportFragmentManager = getSupportFragmentManager();
        fragmentsList = new ArrayList<>();
        MainFragment mainFragment = new MainFragment();
        orderFragment = new OrderCashListFragment();
        CustomerFragment customerFragment = new CustomerFragment();
        MyFragment myFragment = new MyFragment();
        ClueFragment clueFragment = new ClueFragment();

        fragmentsList.add(clueFragment);
        fragmentsList.add(orderFragment);
        fragmentsList.add(mainFragment);
        fragmentsList.add(myFragment);
    }

    /**
     * 设置tab页面
     */
    private void initTab() {
        ArrayList<CustomTabEntity> tabDataList = new ArrayList<CustomTabEntity>();
        tabDataList.add(new TabEntity("线索", R.mipmap.ic_add_selected, R.mipmap.ic_add_normal));
        tabDataList.add(new TabEntity("订单", R.mipmap.ic_order_selected, R.mipmap.ic_order_normal));
        tabDataList.add(new TabEntity("", R.mipmap.ic_cancel, R.mipmap.ic_cancel_select));
        tabDataList.add(new TabEntity("统计", R.mipmap.ic_customer_selected, R.mipmap.ic_customer_normal));
        tabDataList.add(new TabEntity("我的", R.mipmap.ic_my_selected, R.mipmap.ic_my_noramal));
        layout_tab_com.setTabData(tabDataList);
        layout_tab_com.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                EventRxBus.getInstance().post(IntentFlag.RED_LOAD, IntentFlag.RED_LOAD);

                switch (position) {

                    case 0:
                        setTabSelection(0);
                        break;
                    case 1:
                        setTabSelection(1);
                        break;
                    case 3:
                        setTabSelection(2);
                        break;
                    case 4:
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

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        layout_header = (LinearLayout) findViewById(R.id.layout_header);
        layout_tab_com = (CommonTabLayout) findViewById(R.id.layout_tab_com);
        layout_container = (LinearLayout) findViewById(R.id.layout_container);
        img_add = (ImageView) findViewById(R.id.img_add);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        txt_left_name.setOnClickListener(this);
        layout_add = (LinearLayout) findViewById(R.id.layout_add);
        layout_add.setOnClickListener(this);
        layout_jiaoyu = (LinearLayout) findViewById(R.id.layout_jiaoyu);
        layout_jiaoyu.setOnClickListener(this);
        layout_zufang = (LinearLayout) findViewById(R.id.layout_zufang);
        layout_zufang.setOnClickListener(this);
        layout_jingshuiqi = (LinearLayout) findViewById(R.id.layout_jingshuiqi);
        layout_jingshuiqi.setOnClickListener(this);
        layout_content_view = (LinearLayout) findViewById(R.id.layout_content_view);
        layout_content_view.setOnClickListener(this);
        btn_close_menu = (ImageView) findViewById(R.id.btn_close_menu);
        btn_close_menu.setOnClickListener(this);
        layout_pay_zufang = (LinearLayout) findViewById(R.id.layout_pay_zufang);
        layout_pay_zufang.setOnClickListener(this);
        rew_adview = (RewardView) findViewById(R.id.rew_adview);

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.layout_add:
                /**
                 * 检测认证的接口
                 */
                if (isLogin()) {
                    presenter.loadIdCard(UserManager.getUser(this), this);
                }
                break;
            case R.id.layout_content_view:
            case R.id.btn_close_menu:
                showMenu = false;
                showMenu();
                break;
            case R.id.layout_jiaoyu:
                addJiaoYu();
                break;
            case R.id.layout_zufang:
                addZuFang();
                break;
            case R.id.layout_jingshuiqi:
                addJingShuiQi();
                break;
            case R.id.layout_pay_zufang:
                addPayZuFang();
                break;
        }
    }

    /**
     * 添加按钮
     */
    private void addbutClick() {
        if (!isLogin()) {
            return;
        }
        if (sourceTypeArr != null && sourceTypeArr.length == 1) {
            if (sourceTypeArr[0].equals("2")) {
                addZuFang();
            } else if (sourceTypeArr[0].equals("3")) {
                addJingShuiQi();
            } else if (sourceTypeArr[0].equals("10")) {
                addPayZuFang();
            } else {
                addJiaoYu();
            }
            return;
        }

//        boolean only = StringUtils.isOnly(sourceTypeArr, new String[]{"1", "6"});
//        if (only) {
//            addJiaoYu();
//            return;
//        }
        showMenu();
    }

    /**
     * 显示控件
     */
    private void showMenu() {
        if (!isLogin()) {
            return;
        }
        if (showMenu) {
            layout_content_view.setVisibility(View.VISIBLE);
        } else {
            layout_content_view.setVisibility(View.GONE);
        }
    }

    /**
     * 增加教育
     */
    private void addJiaoYu() {

        User user = UserManager.getUser(this);
        Boolean createCommFlag = user.getCreateCommFlag();
        showMenu = false;
        showMenu();
        Map<String, String> sourceTypeMap = user.getSourceTypeMap();
        if (sourceTypeMap.containsKey("6")) {
            presenter.requestHuabeiOrder(this, user);
        } else {
            createCommodity();
        }
    }


    private void authHt() {
        if (!isLogin()) {
            return;
        }
        final User pUser = UserManager.getUser(this);
        final Context context = IndexActivity.this;
        OkHttpUtils
                .post()
                .url(NetConst.AUTH_HT)
                .addParams("token", pUser.getToken())
                .addParams("id", pUser.getId())
                .addParams("account", pUser.getAccount())

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        try {
                            dismissProgress();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        LogUtil.d("okhttp", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {


                        LogUtil.d("okhttp", response);
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                AuthHtResponse mUserInfo = GsonUtil.changeGsonToBean(response, AuthHtResponse.class);
                                AuthHt result = mUserInfo.getResult();
                                User user = UserManager.getUser(context);
                                user.setSign(result.isSign());
                                UserManager.saveUser(context, user);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr();
                        }
                    }
                });


    }


    @Override
    public void createCommodity() {
        Intent mIntent = new Intent(this, CommodityListActivity.class);
        startActivity(mIntent);
    }

    @Override
    public void returnCardInfo() {
        showMenu = true;
        addbutClick();
    }

    /**
     * 增加租房
     */
    private void addPayZuFang() {

        Intent mIntent = new Intent(this, CreateContractActivity.class);
        UserManager.clearUpLoad(this);
        startActivity(mIntent);
        showMenu = false;
        showMenu();
    }

    /**
     * 增加分期租房
     */
    private void addZuFang() {

        Intent mIntent = new Intent(this, CreateCommodityActivity.class);
        UserManager.clearUpLoad(this);
        startActivity(mIntent);
        showMenu = false;
        showMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mUpdataUtil.getVersion();
        mUpdataUtil.getVersionAfter26(new UpdataUtil.CallBack() {
            @Override
            public void onCall() {
                checkInstall();
            }
        });
        updatePromiss();
        EventRxBus.getInstance().post(IntentFlag.RED_LOAD, IntentFlag.RED_LOAD);
        authHt();
    }

    /**
     * 下载广告栏
     */
    private void loadAdView() {
        User user = UserManager.getUser(this);
        notice = null;
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("type", "04")
                .addParams("bussinessId", user.getBussinessId())
                .url(NetConst.APP_NOTICE_LIST).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    NoticeResponse house = GsonUtil.changeGsonToBean(response, NoticeResponse.class);
                    if (house == null) {
                        return;
                    }
                    List<Notice> result = house.getResult();
                    if (result != null && result.size() > 0) {
                        notice = result.get(0);
                        if (mIndex == 0) {
                            rew_adview.setVisibility(View.VISIBLE);
                        }
                        rew_adview.setTargetImage(notice.getUrl());
                        rew_adview.setDataList(result);
                        rew_adview.showBannerView();
//                        rew_adview.showSpirit();
//                        rew_adview.showAdView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();

            }
        });


    }


    /**
     * 增加净水器
     */

    private void addJingShuiQi() {

//      Intent mIntent = new Intent(this, BSYProductDetailsActivity.class);
        Intent mIntent = new Intent(this, BSYSelectProductActivity.class);
        startActivity(mIntent);
        showMenu = false;
        showMenu();
    }

    /**
     * @param index
     */
    private void setTabSelection(int index) {
        if (index == 0) {
            rew_adview.setVisibility(View.VISIBLE);
        } else {
            rew_adview.setVisibility(View.GONE);
        }
        if (notice == null) {
            rew_adview.setVisibility(View.GONE);
        }

        if (!isLogin()) {
            return;
        }
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
    public void onSelection(int mIndex) {
        Intent intent = new Intent(this, OrderListNewActvitity.class);
        intent.putExtra("index", mIndex);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if (showMenu) {
            showMenu = false;
            showMenu();
            return;
        }

        long secondClick = System.currentTimeMillis();
        if (secondClick - firstClick > 2000) {
            ToastUtils.d(IndexActivity.this,
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


    public void show() {
        EasyGuide easyGuide = new EasyGuide.Builder(this)
                // 增加View高亮区域，可同时显示多个
                .addHightArea(img_add, HShape.CIRCLE)
                // 添加箭头指示
//                .addIndicator(R., loc[0], loc[1] + view.getHeight())
                // 复杂的提示布局，建议通过此方法，较容易控制
                .addView(createTipsView(), 0, displayMetrics.heightPixels - (img_add.getHeight() * 4),
                        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                // 设置提示信息，默认居中。若需调整，可采用addView形式
//                .addMessage("点击菜单显示", 14)
//                // 设置确定按钮，默认居中显示在Message下面
//                .setPositiveButton("朕知道了~", 15, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                    }
//                })
                // 是否点击任意区域消失，默认true
                .dismissAnyWhere(true)
                // 若点击作用在高亮区域，是否执行高亮区域的点击事件，默认false
                .performViewClick(true)
                .build();
        easyGuide.show();
    }

    private View createTipsView() {
        View view = LayoutInflater.from(this).inflate(R.layout.tips_view, null);
        return view;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int intExtra = intent.getIntExtra(IntentFlag.INDEX, 0);

        layout_tab_com.setCurrentTab(intExtra);
        setTabSelection(intExtra);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        EventRxBus.getInstance().unregister(IntentFlag.RED_LOAD);
        EventRxBus.getInstance().unregister(IntentFlag.LOGOUT);
    }

    /**
     * 校验安装
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UNKNOWN_APP) {
            checkInstall();
            return;
        }
    }

    private void getAppermissionDisplayVersion() {
        showProgress();
        OkHttpUtils.post()
                .url(NetConst.GET_SYSTEM_CONFIG)
                .addParams("systemConfigName", "app_permission_display_version_b")
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        if (checkResonse(response)) {
                            SystemConfigResponse mSmsCode = GsonUtil.changeGsonToBean(response, SystemConfigResponse.class);
                            String appermissionDisplayVersion = mSmsCode.getResult().getConfigValue();
                            boolean permission = UserManager.isPermission(IndexActivity.this, appermissionDisplayVersion);
                            if (!permission) {
                                showPermissionDescView(appermissionDisplayVersion);
                            }
                        }
                    }
                });
    }

    public void showPermissionDescView(final String version) {
        PermissionDialog permissionDialog = new PermissionDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.setPermission(IndexActivity.this, version);
            }
        });
        permissionDialog.show();

    }
}



