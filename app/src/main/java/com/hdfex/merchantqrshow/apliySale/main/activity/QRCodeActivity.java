package com.hdfex.merchantqrshow.apliySale.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.apliysale.QRCodeResult;

/**
 * author Created by harrishuang on 2018/1/3.
 * email : huangjinping@hdfex.com
 */

public class QRCodeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private LinearLayout layout_opened;
    private LinearLayout layout_closed;
    private ImageView img_qrcode;

    /**
     * 开始界面
     *
     * @param context
     * @param result
     */
    public static void startAction(Context context, QRCodeResult result) {
        Intent intent = new Intent(context, QRCodeActivity.class);
        intent.putExtra(QRCodeResult.class.getSimpleName(), result);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        initView();
        Intent intent = getIntent();
        parseCode(intent);
    }

    /**
     * @param intent
     */
    private void parseCode(Intent intent) {

        layout_closed.setVisibility(View.GONE);
        layout_opened.setVisibility(View.GONE);

        if (intent.getSerializableExtra(QRCodeResult.class.getSimpleName()) != null) {
            QRCodeResult qrcode = (QRCodeResult) intent.getSerializableExtra(QRCodeResult.class.getSimpleName());
            if (!TextUtils.isEmpty(qrcode.getQrcodeUrl())) {
                Glide.with(getApplicationContext())
                        .load(qrcode.getQrcodeUrl())
                        .placeholder(R.mipmap.ic_defoult)
                        .error(R.mipmap.ic_defoult)
                        .into(img_qrcode);
                layout_opened.setVisibility(View.VISIBLE);
                return;
            }
        }
        layout_closed.setVisibility(View.VISIBLE);
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        layout_opened = (LinearLayout) findViewById(R.id.layout_opened);
        layout_closed = (LinearLayout) findViewById(R.id.layout_closed);
        layout_opened.setOnClickListener(this);
        layout_closed.setOnClickListener(this);
        tb_tv_titile.setText("商户收款码");
        img_qrcode = (ImageView) findViewById(R.id.img_qrcode);
        img_qrcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
