package com.hdfex.merchantqrshow.apliySale.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.ReceiptMoneyContract;
import com.hdfex.merchantqrshow.mvp.presenter.ReceiptMoneyPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.HuabeiDialog;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

/**
 * author Created by harrishuang on 2018/1/3.
 * email : huangjinping@hdfex.com
 * 收款界面
 */

public class ReceiptMoneyActivity extends BaseActivity implements ReceiptMoneyContract.View, View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private User user;
    private ReceiptMoneyContract.Presenter presenter;
    /**
     * 花呗支付
     */
    public final String PAY_FLAG_HUABEI = "00";
    /**
     * 支付宝支付
     */
    public final String PAY_FLAG_TAOBAO = "01";
    private EditText edt_payAmount;
    private EditText edt_amount;
    private Button btn_easy_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_money);
        initView();
        presenter = new ReceiptMoneyPresenter();
        presenter.attachView(this);
        user = UserManager.getUser(this);
        tb_tv_titile.setText("在线收款");
        EventRxBus.getInstance().register(IntentFlag.TAOBAO_SCAN).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {
                if (o != null) {
                    submit(o.toString(), PAY_FLAG_TAOBAO);
                }
            }
        });



    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        edt_payAmount = (EditText) findViewById(R.id.edt_payAmount);
        edt_payAmount.setOnClickListener(this);
        edt_amount = (EditText) findViewById(R.id.edt_amount);
        edt_amount.setOnClickListener(this);
        btn_easy_pay = (Button) findViewById(R.id.btn_easy_pay);
        btn_easy_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_easy_pay:
                onScanCode();
                break;
        }
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, ReceiptMoneyActivity.class);
        context.startActivity(intent);
    }


    private void onScanCode() {
        String amount = edt_amount.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            showToast("请输入收银金额");
            return;
        }
        // validate
        String payAmount = edt_payAmount.getText().toString().trim();
        if (TextUtils.isEmpty(payAmount)) {
            showToast("请输入实收金额");
            return;
        }
        CaptureActivity.startAction(this, IntentFlag.TAOBAO_SCAN);

    }


    /**
     * 提交花呗订单
     *
     * @param authCode
     * @param payFlag
     * @param
     */
    private void submit(String authCode, String payFlag) {
        // validate


        String amount = edt_amount.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            showToast("请输入收银金额");
            return;
        }

        // validate
        String payAmount = edt_payAmount.getText().toString().trim();
        if (TextUtils.isEmpty(payAmount)) {
            showToast("请输入实收金额");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("authCode", authCode);
        params.put("payFlag", payFlag);
        params.put("token", user.getToken());
        params.put("id", user.getId());
        params.put("payAmount", payAmount);

//        params.put("custPhoneNo", custPhoneNo);
//        params.put("custName", custName);
//        params.put("custIdNo", custIdNo);
//        params.put("commodityName", edt_commodity_name.getText().toString());
//        params.put("commodityBarCode", commodityBarCode);
//        params.put("custPhoneNo", custPhoneNo);

//        params.put("imei", ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId());
        params.put("bussinessId", user.getBussinessId());
        params.put("bizUserName", user.getName());
        params.put("totalAmount", amount);

        if ("0".equals(amount)) {
            params.put("totalAmount", payAmount);
        }


        presenter.submitData(this, params);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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
        if (!TextUtils.isEmpty(res.getMessage())) {
            showToast(res.getMessage());
        }
    }

    @Override
    public void returnSanpay(Response res) {

        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("收款成功请注意查收")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(this.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
                .withButton2Text("知道了")
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)

                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        finish();
                    }
                })
                .show();
    }


}
