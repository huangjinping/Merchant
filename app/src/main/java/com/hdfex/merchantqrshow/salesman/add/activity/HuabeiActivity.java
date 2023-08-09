package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.huabei.AlipayOrder;
import com.hdfex.merchantqrshow.bean.salesman.huabei.HubeiParam;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.HuabeiContract;
import com.hdfex.merchantqrshow.mvp.presenter.HuabeiPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.HuabeiDialog;

import java.util.Map;

import rx.Subscriber;
import rx.functions.Func1;

/**
 * author Created by harrishuang on 2017/6/16.
 * email : huangjinping@hdfex.com
 */

public class HuabeiActivity extends BaseActivity implements HuabeiContract.View, View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private Button btn_scan_huabei;
    private TextView txt_taobao_paycode;
    private ScrollView scrl_root;
    private HuabeiContract.Presenter presenter;
    private User user;
    private String orderId;

    public static final String CANEDITE = "CANEDITE";
    private Button btn_back_edit;
    private TextView txt_commodity_name;
    private TextView txt_commodityBarCode;
    private TextView txt_custName;
    private TextView txt_custPhoneNo;
    private TextView txt_totalAmount;
    private TextView txt_duration;
    private TextView txt_procedures;
    private TextView txt_preview;
    private LinearLayout layout_scan_huabei;
    private HubeiParam param;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huabei);
        initView();
        user = UserManager.getUser(this);
        presenter = new HuabeiPresenter();
        presenter.attachView(this);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        param = (HubeiParam) intent.getSerializableExtra("param");

        if (intent.getBooleanExtra(CANEDITE, false)) {
            Map<String, String> params = param.getParams();

            if (!TextUtils.isEmpty(params.get("commodityName"))) {
                txt_commodity_name.setText(params.get("commodityName"));
            }
            if (!TextUtils.isEmpty(params.get("custName"))) {
                txt_custName.setText(params.get("custName"));
            }
            if (!TextUtils.isEmpty(params.get("commodityBarCode"))) {
                txt_commodityBarCode.setText(params.get("commodityBarCode"));
            }
            if (!TextUtils.isEmpty(params.get("custPhoneNo"))) {
                txt_custPhoneNo.setText(params.get("custPhoneNo"));
            }
            if (!TextUtils.isEmpty(params.get("totalAmount"))) {
                txt_totalAmount.setText(params.get("totalAmount"));
            }
            if (!TextUtils.isEmpty(params.get("duration"))) {
                txt_duration.setText(params.get("duration") + "期");
            }
            if (!TextUtils.isEmpty(params.get("serviceFee")) && !TextUtils.isEmpty(params.get("totalAmount")) && !TextUtils.isEmpty(params.get("feeRate"))) {

                txt_preview.setText(params.get("serviceFee") + "=" + params.get("totalAmount") + "x" + params.get("feeRate"));
            }

            layout_scan_huabei.setVisibility(View.VISIBLE);

        } else {
            AlipayOrder order = param.getOrder();

            if (order.isRespay()) {
                layout_scan_huabei.setVisibility(View.VISIBLE);
            } else {
                layout_scan_huabei.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(order.getApplyAmount())) {
                txt_totalAmount.setText(order.getApplyAmount());
            }

            if (!TextUtils.isEmpty(order.getCommodityName())) {
                txt_commodity_name.setText(order.getCommodityName());
            }
            if (!TextUtils.isEmpty(order.getCustName())) {
                txt_custName.setText(order.getCustName());
            }
            if (!TextUtils.isEmpty(order.getCommodityBarcode())) {
                txt_commodityBarCode.setText(order.getCommodityBarcode());
            }
            if (!TextUtils.isEmpty(order.getCustPhoneNo())) {
                txt_custPhoneNo.setText(order.getCustPhoneNo());
            }
            if (!TextUtils.isEmpty(order.getDuration())) {
                txt_duration.setText(order.getDuration() + "期");
            }
        }


        EventRxBus.getInstance().register(HuabeiActivity.class.getSimpleName()).filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return o != null;
            }
        }).map(new Func1<Object, String>() {
            @Override
            public String call(Object o) {
                return o.toString();
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                showToast("扫码出现问题");
            }

            @Override
            public void onNext(String s) {
                scrl_root.fullScroll(ScrollView.FOCUS_DOWN);
                txt_taobao_paycode.setText(s);
                presenter.scanPay(user, orderId, txt_taobao_paycode.getText().toString());
            }
        });


    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        btn_scan_huabei = (Button) findViewById(R.id.btn_scan_huabei);
        btn_scan_huabei.setOnClickListener(this);
        tb_tv_titile.setText("确认商品");
        txt_taobao_paycode = (TextView) findViewById(R.id.txt_taobao_paycode);
        txt_taobao_paycode.setOnClickListener(this);
        scrl_root = (ScrollView) findViewById(R.id.scrl_root);
        btn_back_edit = (Button) findViewById(R.id.btn_back_edit);
        btn_back_edit.setOnClickListener(this);
        txt_commodity_name = (TextView) findViewById(R.id.txt_commodity_name);
        txt_commodity_name.setOnClickListener(this);
        txt_commodityBarCode = (TextView) findViewById(R.id.txt_commodityBarCode);
        txt_commodityBarCode.setOnClickListener(this);
        txt_custName = (TextView) findViewById(R.id.txt_custName);
        txt_custName.setOnClickListener(this);
        txt_custPhoneNo = (TextView) findViewById(R.id.txt_custPhoneNo);
        txt_custPhoneNo.setOnClickListener(this);
        txt_totalAmount = (TextView) findViewById(R.id.txt_totalAmount);
        txt_totalAmount.setOnClickListener(this);
        txt_duration = (TextView) findViewById(R.id.txt_duration);
        txt_duration.setOnClickListener(this);
        txt_procedures = (TextView) findViewById(R.id.txt_procedures);
        txt_procedures.setOnClickListener(this);
        txt_preview = (TextView) findViewById(R.id.txt_preview);
        txt_preview.setOnClickListener(this);
        layout_scan_huabei = (LinearLayout) findViewById(R.id.layout_scan_huabei);
        layout_scan_huabei.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan_huabei:
                CaptureActivity.startAction(this, HuabeiActivity.class.getSimpleName());
                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.btn_back_edit:
                onBackPressed();
//                if (getIntent().getBooleanExtra(CANEDITE, false)) {
//                    onBackPressed();
//                } else {
//                    ConfirmCommodityActivity.startAction(this, param.getOrder());
//                }
                break;
        }
    }

    /**
     * @param context
     * @param orderId
     * @param params
     */
    public static void startAction(Context context, String orderId, Map<String, String> params) {
        Intent intent = new Intent(context, HuabeiActivity.class);
        intent.putExtra("orderId", orderId);
        HubeiParam param = new HubeiParam();
        param.setParams(params);
        intent.putExtra("param", param);
        intent.putExtra(HuabeiActivity.CANEDITE, true);
        context.startActivity(intent);
    }


    /**
     * @param context
     * @param orderId
     * @param
     */
    public static void startAction(Context context, String orderId, AlipayOrder order) {
        Intent intent = new Intent(context, HuabeiActivity.class);
        intent.putExtra("orderId", orderId);
        HubeiParam param = new HubeiParam();
        param.setOrder(order);
        intent.putExtra("param", param);
        intent.putExtra(HuabeiActivity.CANEDITE, false);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void returnSanpay(Response res) {
        showToast("交易成功");
        HuaBeiSuccessActivity.startAction(this);
    }

    private HuabeiDialog huabeiDialog;

    @Override
    public void showLoading() {
        huabeiDialog = HuabeiDialog.getInstance(this);
        huabeiDialog.show();
    }

    @Override
    public void dismissLoading() {
        huabeiDialog.dismiss();
    }

    @Override
    public void returnTimeout() {
        showToast("支付超时！请重新支付！");
    }

    @Override
    public void returnPayEirr(Response res) {
        HuaBeiFailsActivity.startAction(this, res.getMessage());
    }

    @Override
    public void onBackPressed() {
        EventRxBus.getInstance().post(IntentFlag.ORDER_ID, orderId);
        finish();
    }

}
