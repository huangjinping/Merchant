package com.hdfex.merchantqrshow.activity;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.net.NetConst;

import java.net.URLDecoder;

/**
 * Created by harrishuang on 2017/1/18.
 */

public class WebActivity extends BaseActivity {

    private String url;
    private String title;
    private ImageView img_back;
    private TextView txt_left_name;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private WebView hd_webview;
    private ProgressBar prov_rogressBar;
    private FrameLayout hd_customViewContainer;
    private View mCustomView;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private ProgressWebViewClient mProgressWebViewClient;
    private ProgressWebChromeClient mProgressWebChromeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        url = bundle.getString("url");
        title = bundle.getString("title");
        if (!TextUtils.isEmpty(title)) {
            tb_tv_titile.setText(title);
        } else {
            tb_tv_titile.setText("互动金融");
        }
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
            hd_webview.loadUrl("http://www.baidu.com/");
        }

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.this.onBackPressed();
            }
        });
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setText("关闭");
        tv_home.setVisibility(View.VISIBLE);
        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        hd_webview = (WebView) findViewById(R.id.hd_webview);
        prov_rogressBar = (ProgressBar) findViewById(R.id.prov_rogressBar);
        hd_customViewContainer = (FrameLayout) findViewById(R.id.hd_customViewContainer);
    }


    public static void start(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        intent.putExtras(bundle);
        context.startActivity(intent);

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
                tb_tv_titile.setText(tempTitle);
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
            ProgressBar mVideoProgressBar = new ProgressBar(WebActivity.this);
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


    @Override
    public void onBackPressed() {

        if (hd_webview.canGoBack()) {
            hd_webview.goBack();
        } else {
            super.onBackPressed();
            finish();
        }
    }
}