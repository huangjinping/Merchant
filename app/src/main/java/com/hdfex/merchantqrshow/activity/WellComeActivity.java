package com.hdfex.merchantqrshow.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.home.BussnessBanner;
import com.hdfex.merchantqrshow.bean.salesman.home.BussnessBannerQueryResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.main.activity.GuideActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.LoginActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.PermissionDisplayActivity;
import com.hdfex.merchantqrshow.utils.DeviceUtils;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.RoleUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.dexter.OnPermissionListener;
import com.hdfex.merchantqrshow.utils.dexter.SampleMultipleBackgroundPermissionListener;
import com.hdfex.merchantqrshow.view.LoadingView;
import com.karumi.dexter.PermissionToken;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.umeng.analytics.MobclickAgent;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 展示页面
 * Created by harrishuang on 16/7/4.
 */
public class WellComeActivity extends BaseActivity {
    private RelativeLayout layout_root;
    private ImageView img_well_top;
    private DisplayMetrics displayMetrics;
    private LoadingView load_view;
    private TextView txt_well_blink;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        displayMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //友盟
        MobclickAgent.enableEncrypt(true);
        MobclickAgent.setDebugMode(true);

        initView();
        initStatusBarColor(R.color.transparent);
        startAnimation();
        settopViewHeight();
        load_view.setTimeLenth(3);
        load_view.setLoadingListener(new LoadingView.OnLoadingListener() {
            @Override
            public void onFinish() {
                initPermission();
            }
        });
        load_view.start();
        txt_well_blink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_view.endLoad();
                initPermission();
            }
        });

        user = UserManager.getUser(this);
        if (user != null && !TextUtils.isEmpty(user.getToken())) {
            String bussnessBannder = UserManager.getBussnessBannder(user.getId());
            if (!TextUtils.isEmpty(bussnessBannder)) {
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_wellcome_top);
                Glide.with(this).load(bussnessBannder).placeholder(drawable).error(drawable).into(img_well_top);
            }
            loaBussnessBannder();
            return;
        }

    }

    /**
     * 下载数据
     */
    public void loaBussnessBannder() {
        if (!connect()) {
            return;
        }
        final User user = UserManager.getUser(this);

        OkHttpUtils
                .post()
                .url(NetConst.BUSSNESS_BANNER_QUERY)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();

                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            BussnessBannerQueryResponse bannderResponse = GsonUtil.changeGsonToBean(response, BussnessBannerQueryResponse.class);
                            BussnessBanner result = bannderResponse.getResult();
                            String bannerUrl = result.getBannerUrl();
                            UserManager.saveBussnessBanner(user.getId(), bannerUrl);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                });

    }


    private void initView() {
        layout_root = (RelativeLayout) findViewById(R.id.layout_root);
        img_well_top = (ImageView) findViewById(R.id.img_well_top);
        load_view = (LoadingView) findViewById(R.id.load_view);
        txt_well_blink = (TextView) findViewById(R.id.txt_well_blink);
    }


    private void settopViewHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) img_well_top.getLayoutParams();
        layoutParams.height = displayMetrics.widthPixels * 1596 / 1125;
        img_well_top.setLayoutParams(layoutParams);
        img_well_top.invalidate();
    }

    boolean isJump = false;

    /**
     * 跳转到首页
     */
    private void toMain() {
        if (!isJump) {
            isJump = true;
        } else {
            return;
        }
        boolean login = UserManager.isLogin(this);
        boolean first = UserManager.isFirst(this);
//        boolean permission = UserManager.isPermission(this);
        if (!first) {
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            this.finish();
            return;
        }
//        if (!permission) {
//            PermissionDisplayActivity.startAction(this);
//            return;
//        }

        if (login) {
            User user = UserManager.getUser(this);
            RoleUtils.startAction(WellComeActivity.this, user, RoleUtils.ROLE_FLAG_LOGIN);
            return;
        }
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();

    }

    /**
     *
     */

    private void startAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout_root, "alpha", 0f, 1);
        objectAnimator.setDuration(1000);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                txt_well_blink.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    /**
     * 权限监听器
     */
    SampleMultipleBackgroundPermissionListener multiplePermissionListener = new SampleMultipleBackgroundPermissionListener(new OnPermissionListener() {
        @Override
        public void showPermissionGranted(String permission) {
            if (DeviceUtils.isPermission(WellComeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) && DeviceUtils.isPermission(WellComeActivity.this, Manifest.permission.CAMERA) && DeviceUtils.isPermission(WellComeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                toMain();
                return;
            }
        }

        @Override
        public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {

            showPermissDeniedAlert();
        }

        @Override
        public void showPermissionRationale(PermissionToken token) {

            token.continuePermissionRequest();
        }
    });

    /**
     * 初始化权限
     */
    private void initPermission() {


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (DeviceUtils.isPermission(this, Manifest.permission.WRITE_SETTINGS) && DeviceUtils.isPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) && DeviceUtils.isPermission(this, Manifest.permission.CAMERA) && DeviceUtils.isPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                toMain();
//                return;
//            }
//            if (Dexter.isRequestOngoing()) {
//                return;
//            }
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//                    Dexter.checkPermissionsOnSameThread(multiplePermissionListener, Manifest.permission.WRITE_SETTINGS, Manifest.permission.WRITE_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
//                }
//            }.start();
//        } else {
//            toMain();
//        }

        toMain();

    }


}
