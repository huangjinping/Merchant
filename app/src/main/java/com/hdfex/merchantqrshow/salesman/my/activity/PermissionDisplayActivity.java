package com.hdfex.merchantqrshow.salesman.my.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.SystemConfigResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.RoleUtils;
import com.hdfex.merchantqrshow.utils.UserManager;

import java.net.URLDecoder;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2019-12-31.
 * email : huangjinping1000@163.com
 */
public class PermissionDisplayActivity extends BaseActivity implements View.OnClickListener {
    private CheckBox checkbox;
    private Button btn_cancel;
    private Button btn_submit;

    private String url;
    private String title;

    private WebChromeClient.CustomViewCallback customViewCallback;
    private WebView hd_webview;
    private ProgressBar prov_rogressBar;
    private FrameLayout hd_customViewContainer;
    private View mCustomView;
    private ProgressWebViewClient mProgressWebViewClient;
    private ProgressWebChromeClient mProgressWebChromeClient;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, PermissionDisplayActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_display);
        initView();
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            url = bundle.getString("url");
            title = bundle.getString("title");
        }

//        if (!TextUtils.isEmpty(title)) {
//            tb_tv_titile.setText(title);
//        } else {
//            tb_tv_titile.setText("互动金融");
//        }
        mProgressWebChromeClient = new ProgressWebChromeClient(prov_rogressBar);
        mProgressWebViewClient = new ProgressWebViewClient(prov_rogressBar);
        hd_webview.setWebChromeClient(mProgressWebChromeClient);
        hd_webview.setWebViewClient(mProgressWebViewClient);
        WebSettings settings = hd_webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        if (url != null) {
            hd_webview.loadUrl(url);
        } else {
            hd_webview.loadUrl(NetConst.PERMISSION_URL);
        }
    }


    class ProgressWebViewClient extends WebViewClient {

        public ProgressWebViewClient(ProgressBar mRogressBar) {
            this.mRogressBar = mRogressBar;
        }

        private ProgressBar mRogressBar;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 如果是卡片id 跳到英雄详情_技能加点
            url = URLDecoder.decode(url);

            if (url.contains("card=")) {
                Log.i("url1", url);
                //  url = StringUtils.substringBetween(url + "/", "card=", "/");
                return true;
            } else {
                view.loadUrl(url);
                return true;
            }


        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mRogressBar.setVisibility(View.VISIBLE);
            if (url.contains(NetConst.BAST_NEW_SIGN)) {
                finish();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // 加载完成
            super.onPageFinished(view, url);
            mRogressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    /**
     * Created by longtaoge on 2016/2/1.
     */
    public class ProgressWebChromeClient extends WebChromeClient {
        private ProgressBar mProgresBar;

        public ProgressWebChromeClient(ProgressBar mProgresBar) {
            this.mProgresBar = mProgresBar;
        }


        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            String tempTitle = title;
            if (!TextUtils.isEmpty(tempTitle)) {
                if (tempTitle.length() > 8) {
                    tempTitle = tempTitle.substring(0, 7) + "...";
                }
                Log.d("okhttp", tempTitle);
            }

        }

        @Override
        public void onShowCustomView(View view, int requestedOrientation,
                                     CustomViewCallback callback) {
            onShowCustomView(view, callback); // To change body of overridden
            // methods use File | Settings |
            // File Templates.
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            hd_webview.setVisibility(View.GONE);
            hd_customViewContainer.setVisibility(View.VISIBLE);

            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            hd_customViewContainer.addView(view);
            customViewCallback = callback;
        }

        /**
         * When the user starts to playback a video element, it may take time for enough
         * data to be buffered before the first frames can be rendered. While this buffering
         * is taking place, the ChromeClient can use this function to provide a View to be
         * displayed. For example, the ChromeClient could show a spinner animation.
         *
         * @return View The View to be displayed whilst the video is loading.
         */
        @Override
        public View getVideoLoadingProgressView() {
            ProgressBar mVideoProgressBar = new ProgressBar(PermissionDisplayActivity.this);
            return mVideoProgressBar;
        }


        @Override
        public void onHideCustomView() {
            super.onHideCustomView(); // To change body of overridden methods
            // use File | Settings | File Templates.
            if (mCustomView == null)
                return;

            hd_webview.setVisibility(View.VISIBLE);
            hd_customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            hd_customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }

        /**
         * Tell the host application the current progress of loading a page.
         *
         * @param view        The WebView that initiated the callback.
         * @param newProgress Current page loading progress, represented by
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgresBar.setProgress(newProgress);
        }
    }


    private void initView() {
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        hd_webview = (WebView) findViewById(R.id.hd_webview);
        hd_webview.setOnClickListener(this);
        prov_rogressBar = (ProgressBar) findViewById(R.id.prov_rogressBar);
        prov_rogressBar.setOnClickListener(this);
        hd_customViewContainer = (FrameLayout) findViewById(R.id.hd_customViewContainer);
        hd_customViewContainer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                showCancelAlert();
                break;
            case R.id.btn_submit:
                onSubmit();
                break;
        }
    }

    private void showCancelAlert() {


        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("您需同意《优选加用户隐私保护政策方可试用本软件中的产品和服务》")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(this.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
                .withButton1Text("确认")
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                }).show();
    }


    private void onSubmit() {
//        if (!checkbox.isChecked()) {
//            showToast("阅读并同意相关协议");
//            return;
//        }
        getAppermissionDisplayVersion();
    }


    private void getAppermissionDisplayVersion() {

        if (!checkbox.isChecked()) {
            showToast("阅读并同意相关协议");
            return;
        }
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
                        onNext();
                    }

                    @Override
                    public void onResponse(String response) {

                        try {
                            if (checkResonse(response)) {
                                SystemConfigResponse mSmsCode = GsonUtil.changeGsonToBean(response, SystemConfigResponse.class);
                                String appermissionDisplayVersion = mSmsCode.getResult().getConfigValue();
                                UserManager.setPermission(PermissionDisplayActivity.this, appermissionDisplayVersion);
                                onNext();
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        onNext();
                    }
                });
    }

    private void onNext() {
        if (UserManager.isLogin(this)) {
            User user = UserManager.getUser(this);
            RoleUtils.startAction(this, user, RoleUtils.ROLE_FLAG_LOGIN);
            finish();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
