package com.hdfex.merchantqrshow.salesman.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.salesman.main.activity.ScanRecordActivity;
import com.hdfex.merchantqrshow.base.BaseActivity;

/**
 * 订单管理界面
 */
public class OrderListManagerActivity extends BaseActivity implements View.OnClickListener {
    private TextView tb_tv_titile;
    private LinearLayout layout_toolbar;
    private RelativeLayout Layout_un_scan;
    private RelativeLayout Layout_scan_ed;
    private ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist_manager);
        initView();
        initDate();
    }

    private void initDate() {
        tb_tv_titile.setText("点单管理");
    }

    private void initView() {
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        Layout_un_scan = (RelativeLayout) findViewById(R.id.Layout_un_scan);
        Layout_scan_ed = (RelativeLayout) findViewById(R.id.Layout_scan_ed);
        Layout_un_scan.setOnClickListener(this);
        Layout_scan_ed.setOnClickListener(this);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        Intent mIntent;
        switch (view.getId()) {
            case R.id.Layout_un_scan:
                mIntent = new Intent(this, ScanRecordActivity.class);
                mIntent.putExtra("conform", "unscan");
                startActivity(mIntent);
                break;
            case R.id.Layout_scan_ed:
                mIntent = new Intent(this, OrderListActivity.class);
                mIntent.putExtra("conform", "scaned");
                startActivity(mIntent);
                break;

            case R.id.img_back:
                finish();
                break;
        }
    }
}
