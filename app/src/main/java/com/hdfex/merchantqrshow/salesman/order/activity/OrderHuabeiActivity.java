package com.hdfex.merchantqrshow.salesman.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.huabei.AlipayOrder;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.StringUtils;

/**
 * 花呗的订单
 * author Created by harrishuang on 2017/8/2.
 * email : huangjinping@hdfex.com
 */

public class OrderHuabeiActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt_custPhoneNo;
    private TextView txt_status;
    private TextView txt_duration;
    private TextView txt_totalAmount;
    private TextView txt_commodityName;
    private TextView txt_failReason;
    private LinearLayout layout_failReason;
    private TextView txt_orderId;
    private TextView txt_orderDate;
    private TextView txt_payWay;
    private TextView tb_tv_titile;
    private ImageView img_back;
    private TextView txt_payAmout;
    private TextView txt_custName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhuabei);
        initView();
        Intent intent = getIntent();
        parseOrder(intent);
    }

    public static void startAction(Context context, AlipayOrder alipayOrder) {
        Intent intent = new Intent(context, OrderHuabeiActivity.class);
        intent.putExtra(IntentFlag.HUABEI_DETAILS, alipayOrder);
        context.startActivity(intent);
    }

    /**
     * 解析数据问题
     *
     * @param intent
     */
    private void parseOrder(Intent intent) {
        if (intent.getSerializableExtra(IntentFlag.HUABEI_DETAILS) != null) {
            AlipayOrder alipayOrder = (AlipayOrder) intent.getSerializableExtra(IntentFlag.HUABEI_DETAILS);
            if (!TextUtils.isEmpty(alipayOrder.getCustPhoneNo())) {
                txt_custPhoneNo.setText(StringUtils.getPhoneFormat(alipayOrder.getCustPhoneNo()));
            }
            if (!TextUtils.isEmpty(alipayOrder.getStatus())) {
                txt_status.setText(alipayOrder.getStatus());
            }
            if (!TextUtils.isEmpty(alipayOrder.getDuration())) {
                txt_duration.setText("(" + alipayOrder.getDuration() + "期," + alipayOrder.getProfitSource() + ")");
            }
            if (!TextUtils.isEmpty(alipayOrder.getTotalAmount())) {
                txt_totalAmount.setText(alipayOrder.getTotalAmount() + "元");
            }
            if (!TextUtils.isEmpty(alipayOrder.getCommodityName())) {
                txt_commodityName.setText(alipayOrder.getCommodityName());
            }
            layout_failReason.setVisibility(View.GONE);

            if ("已取消".equals(alipayOrder.getStatus())) {
                if (!TextUtils.isEmpty(alipayOrder.getFailReason())) {
                    layout_failReason.setVisibility(View.VISIBLE);
                    txt_failReason.setText(alipayOrder.getFailReason());
                }
            }

            if (!TextUtils.isEmpty(alipayOrder.getOrderId())) {
                txt_orderId.setText("订单编号：" + alipayOrder.getOrderId());
            }

            if (!TextUtils.isEmpty(alipayOrder.getOrderTime())) {
                txt_orderDate.setText("下单时间：" + alipayOrder.getOrderTime());
            }


            if (!TextUtils.isEmpty(alipayOrder.getPayAmout())) {
                txt_payAmout.setText(alipayOrder.getPayAmout());
            }

            if (!TextUtils.isEmpty(alipayOrder.getCustName())) {
                txt_custName.setText(alipayOrder.getCustName());
            }

            if ("0".equals(alipayOrder.getPayWay())) {
                txt_payWay.setText("交易方式：" + "花呗分期");
            } else if ("1".equals(alipayOrder.getPayWay())) {
                txt_payWay.setText("交易方式：" + "支付宝");
            } else if ("2".equals(alipayOrder.getPayWay())) {
                txt_payWay.setText("交易方式：" + "支付宝+花呗分期");
            }
        }
    }

    private void initView() {
        txt_custPhoneNo = (TextView) findViewById(R.id.txt_custPhoneNo);
        txt_status = (TextView) findViewById(R.id.txt_status);
        txt_duration = (TextView) findViewById(R.id.txt_duration);
        txt_totalAmount = (TextView) findViewById(R.id.txt_totalAmount);
        txt_commodityName = (TextView) findViewById(R.id.txt_commodityName);
        txt_failReason = (TextView) findViewById(R.id.txt_failReason);
        layout_failReason = (LinearLayout) findViewById(R.id.layout_failReason);
        txt_orderId = (TextView) findViewById(R.id.txt_orderId);
        txt_orderDate = (TextView) findViewById(R.id.txt_orderDate);
        txt_payWay = (TextView) findViewById(R.id.txt_payWay);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setText("订单详情");
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txt_payAmout = (TextView) findViewById(R.id.txt_payAmout);
        txt_payAmout.setOnClickListener(this);
        txt_custName = (TextView) findViewById(R.id.txt_custName);
        txt_custName.setOnClickListener(this);
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
