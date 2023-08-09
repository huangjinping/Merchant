package com.hdfex.merchantqrshow.salesman.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.bsy.BSYOrderListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.message.MessageDetails;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class MessageDetailsActivity extends BaseActivity implements View.OnClickListener {


    private TextView txt_message_content;
    private ScrollView scl_root;
    private WebView hd_webview;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private User user;

    protected void init() {
        WebSettings settings = hd_webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);
//        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagedetails_actiity);
        initView();
        init();
        Intent intent = getIntent();
        try {
            MessageDetails result = (MessageDetails) intent.getSerializableExtra("result");
            if (!TextUtils.isEmpty(result.getTitle())) {
                tb_tv_titile.setText(result.getTitle());
            }
            if (!TextUtils.isEmpty(result.getContent())) {
                scl_root.setVisibility(View.VISIBLE);
                hd_webview.setVisibility(View.GONE);
                txt_message_content.setVisibility(View.VISIBLE);
                txt_message_content.setText(result.getContent());
            }

            if (!TextUtils.isEmpty(result.getContentHtml())){
                scl_root.setVisibility(View.GONE);
                hd_webview.setVisibility(View.VISIBLE);
                hd_webview.loadDataWithBaseURL(null, result.getContentHtml(), "text/html", "utf-8", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initView() {
        txt_message_content = (TextView) findViewById(R.id.txt_message_content);
        scl_root = (ScrollView) findViewById(R.id.scl_root);
        hd_webview = (WebView) findViewById(R.id.hd_webview);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setOnClickListener(this);
        tb_tv_titile.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    public static void startAction(Context context, MessageDetails result) {
        Intent intent = new Intent(context, MessageDetailsActivity.class);
        intent.putExtra("result", result);
        context.startActivity(intent);

    }



}
