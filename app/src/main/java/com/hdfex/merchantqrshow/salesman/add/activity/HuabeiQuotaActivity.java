package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;

/**
 * author Created by harrishuang on 2017/7/7.
 * email : huangjinping@hdfex.com
 */

public class HuabeiQuotaActivity extends BaseActivity implements View.OnClickListener {


    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView img_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huabeiquota);
        initView();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        img_pic = (ImageView) findViewById(R.id.img_pic);
        img_back.setOnClickListener(this);
        tb_tv_titile.setText("查看花呗额度");
        tb_tv_titile.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = img_pic.getLayoutParams();
                layoutParams.height = img_pic.getWidth();
                img_pic.setLayoutParams(layoutParams);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_back:
                finish();
                break;
        }
    }

    public static void startAction( Context context) {
        Intent intent=new Intent(context,HuabeiQuotaActivity.class);
        context.startActivity(intent);
    }
}
