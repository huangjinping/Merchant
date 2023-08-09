package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * author Created by harrishuang on 2017/11/13.
 * email : huangjinping@hdfex.com
 */

public class ScanPicActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView img_scan_pic;
    private TextView txt_totalAmount;
    private TextView txt_loanAmt;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanpic);
        initView();
        init();
    }

    /**
     * 初始化基础数据
     */
    private void init() {
        intent = getIntent();
        Map<String, String> params = (Map<String, String>) intent.getSerializableExtra("params");
        Log.d("okhttp", GsonUtil.createGsonString(params));
        String qrcode = params.get("qrcode");
        String payAmount = params.get("payAmount");
        String callBackMessage = params.get("callBackMessage");
        String payFlag = params.get("payFlag");
        if (qrcode != null) {
            Glide.with(getApplicationContext())
                    .load(qrcode)
                    .placeholder(R.mipmap.ic_defoult)
                    .error(R.mipmap.ic_defoult)
                    .into(img_scan_pic);
        }
        txt_totalAmount.setText("" + payAmount);
        /**
         *支付宝推送
         */
        if ("01".equals(payFlag)) {
            tb_tv_titile.setText("支付宝收款码");
            txt_loanAmt.setText("商品金额：" + payAmount);
        } else {
            /**
             * 花呗推送
             */
            txt_loanAmt.setText("" + callBackMessage);

        }
    }

    /**
     * 初始化数据
     */
    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        img_scan_pic = (ImageView) findViewById(R.id.img_scan_pic);
        txt_totalAmount = (TextView) findViewById(R.id.txt_totalAmount);
        txt_loanAmt = (TextView) findViewById(R.id.txt_loanAmt);
        tb_tv_titile.setText("花呗分期二维码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    public static void startAction(Context context, Map<String, String> params) {
        Intent intent = new Intent(context, ScanPicActivity.class);
        intent.putExtra("params", (Serializable) params);
        context.startActivity(intent);
    }
}
