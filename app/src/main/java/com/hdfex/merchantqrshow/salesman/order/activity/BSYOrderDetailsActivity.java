package com.hdfex.merchantqrshow.salesman.order.activity;

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
import com.hdfex.merchantqrshow.bean.salesman.bsy.BSYOrderItem;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.presenter.BSYOrderDetailsPresenter;
import com.hdfex.merchantqrshow.mvp.view.BSYOrderView;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;

/**
 * 碧水源订单详情
 * Created by harrishuang on 2016/11/24.
 */

public class BSYOrderDetailsActivity extends BaseActivity implements BSYOrderView, View.OnClickListener {
    private BSYOrderDetailsPresenter presenter;
    private ImageView img_back;
    private TextView txt_left_name;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private TextView txt_commodity_status;
    private TextView txt_order_updateTime;
    private ImageView img_order_logo;
    private ImageView img_order_pic;
    private TextView txt_commodity_name;
    private TextView txt_product_id;
    private TextView txt_product_status;
    private TextView txt_amount;
    private LinearLayout layout_item_orderlist;
    private TextView tv_order_title;
    private TextView tv_order_number_title;
    private TextView tv_order_number;
    private TextView tv_time_title;
    private TextView tv_time;
    private TextView tv_send_type;
    private TextView txt_customer_name;
    private TextView txt_customer_phone;
    private TextView txt_address;
    private static final String APPLE_ID = "orderId";
    private static final String SHOW_ALERT = "SHOW_ALERT";

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsyorderdetails);
        initView();
        initData();
    }


    /**
     * 启动界面
     *
     * @param context
     * @param appleId
     */
    public static void startSubmit(Context context, String appleId, boolean showAlert) {
        Intent intent = new Intent(context, BSYOrderDetailsActivity.class);
        intent.putExtra(APPLE_ID, appleId);
        intent.putExtra(SHOW_ALERT, showAlert);
        context.startActivity(intent);
    }


    /**
     * 启动界面
     *
     * @param context
     * @param appleId
     */
    public static void start(Context context, String appleId) {

        Intent intent = new Intent(context, BSYOrderDetailsActivity.class);
        intent.putExtra(APPLE_ID, appleId);
        context.startActivity(intent);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        presenter = new BSYOrderDetailsPresenter();
        user = UserManager.getUser(this);
        presenter.attachView(this);
        String appleId = getIntent().getStringExtra(APPLE_ID);
        boolean alert = getIntent().getBooleanExtra(SHOW_ALERT, false);
        if (alert) {
            ToastUtils.makeTextOrder(this).show();
        }

        presenter.loadDetails(user, appleId);
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        txt_commodity_status = (TextView) findViewById(R.id.txt_commodity_status);
        txt_order_updateTime = (TextView) findViewById(R.id.txt_order_updateTime);
        img_order_logo = (ImageView) findViewById(R.id.img_order_logo);
        img_order_pic = (ImageView) findViewById(R.id.img_order_pic);
        txt_commodity_name = (TextView) findViewById(R.id.txt_commodity_name);
        txt_product_id = (TextView) findViewById(R.id.txt_product_id);
        txt_product_status = (TextView) findViewById(R.id.txt_product_status);
        txt_amount = (TextView) findViewById(R.id.txt_amount);
        layout_item_orderlist = (LinearLayout) findViewById(R.id.layout_item_orderlist);
        tv_order_title = (TextView) findViewById(R.id.tv_order_title);
        tv_order_number_title = (TextView) findViewById(R.id.tv_order_number_title);
        tv_order_number = (TextView) findViewById(R.id.tv_order_number);
        tv_time_title = (TextView) findViewById(R.id.tv_time_title);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_send_type = (TextView) findViewById(R.id.tv_send_type);
        tb_tv_titile.setText("订单详情");
        txt_customer_name = (TextView) findViewById(R.id.txt_customer_name);
        txt_customer_name.setOnClickListener(this);
        txt_customer_phone = (TextView) findViewById(R.id.txt_customer_phone);
        txt_customer_phone.setOnClickListener(this);
        txt_address = (TextView) findViewById(R.id.txt_address);
        txt_address.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void onDetailsResult(BSYOrderItem orderDetails) {
        if (!TextUtils.isEmpty(orderDetails.getStatus())) {
            txt_commodity_status.setText(orderDetails.getStatus());
        }
        if (!TextUtils.isEmpty(orderDetails.getUpdateTime())) {
            txt_order_updateTime.setText(orderDetails.getUpdateTime());
        }
        if (!TextUtils.isEmpty(orderDetails.getCustName())) {
            txt_customer_name.setText(orderDetails.getCustName());
        }
        if (!TextUtils.isEmpty(orderDetails.getPhoneNo())) {
            txt_customer_phone.setText(orderDetails.getPhoneNo());
        }


        /**
         * 地址
         */
        if (!TextUtils.isEmpty(orderDetails.getAddress())) {
            txt_address.setText(orderDetails.getAddress());
        }


        if (!TextUtils.isEmpty(orderDetails.getCommodityName())) {
            txt_commodity_name.setText(orderDetails.getCommodityName());
        }


        if (!TextUtils.isEmpty(orderDetails.getParameter())) {
            txt_product_id.setText(orderDetails.getParameter());
        }


        if (!TextUtils.isEmpty(orderDetails.getCountLeft())) {
            txt_product_status.setText("库存" + orderDetails.getCountLeft() + "件");
        }
        if (!TextUtils.isEmpty(orderDetails.getOrderId())) {
            tv_order_number.setText(orderDetails.getOrderId());
        }
        if (!TextUtils.isEmpty(orderDetails.getImportTime())) {
            tv_time.setText(orderDetails.getImportTime());
        }


        if (orderDetails.getCommodityPrice() != null) {
            txt_amount.setText(orderDetails.getCommodityPrice().toString());
        }
        Glide.with(getApplicationContext())
                .load(orderDetails.getCommodityUrl())
                .placeholder(R.mipmap.ic_defoult)
                .error(R.mipmap.ic_defoult)
                .into(img_order_pic);

    }
}
