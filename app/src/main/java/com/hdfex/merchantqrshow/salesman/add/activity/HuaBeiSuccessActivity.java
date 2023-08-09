package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.salesman.main.activity.IndexActivity;

/**
 * author Created by harrishuang on 2017/6/27.
 * email : huangjinping@hdfex.com
 */

public class HuaBeiSuccessActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView tv_home;
    private ImageView result_icon;
    private TextView tv_result_title;
    private TextView tv_result_content;
    private Button button_order_detial;
    private Button button_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huabei_success);
        initView();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tb_tv_titile.setText("成功");
        tv_home.setText("首页");
        tv_home.setOnClickListener(this);
        img_back.setOnClickListener(this);
        result_icon = (ImageView) findViewById(R.id.result_icon);
        result_icon.setOnClickListener(this);
        tv_result_title = (TextView) findViewById(R.id.tv_result_title);
        tv_result_title.setOnClickListener(this);
        tv_result_content = (TextView) findViewById(R.id.tv_result_content);
        tv_result_content.setOnClickListener(this);
        button_order_detial = (Button) findViewById(R.id.button_order_detial);
        button_order_detial.setOnClickListener(this);
        button_next = (Button) findViewById(R.id.button_next);
        button_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                toMain();
                break;
            case R.id.tv_home:
                break;
            case R.id.button_order_detial:
                break;
            case R.id.button_next:
                toMain();
                break;
        }
    }

    private void toMain(){
        Intent mIntent = new Intent(this, IndexActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        toMain();
    }

    public static void startAction(Context context) {
        Intent intent=new Intent(context,HuaBeiSuccessActivity.class);
        context.startActivity(intent);
    }
}
