package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CommodityCreateInfo;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CommodityCreateResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.PayMonthly;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2018/7/11.
 * email : huangjinping@hdfex.com
 */
public class MonthlyPreviewActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_commodity_name;
    private TextView txt_commodity_address;
    private TextView txt_commodity_price;
    private TextView txt_coustomer_name;
    private TextView txt_coustomer_phone;
    private TextView txt_start_time;
    private TextView txt_end_time;
    private TextView txt_downpayment_type;
    private Button btn_submit;
    private PayMonthly payMonthly;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_preview);
        initView();
        initData();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        txt_commodity_name = (TextView) findViewById(R.id.txt_commodity_name);
        txt_commodity_address = (TextView) findViewById(R.id.txt_commodity_address);
        txt_commodity_price = (TextView) findViewById(R.id.txt_commodity_price);
        txt_coustomer_name = (TextView) findViewById(R.id.txt_coustomer_name);
        txt_coustomer_phone = (TextView) findViewById(R.id.txt_coustomer_phone);
        txt_start_time = (TextView) findViewById(R.id.txt_start_time);
        txt_end_time = (TextView) findViewById(R.id.txt_end_time);
        txt_downpayment_type = (TextView) findViewById(R.id.txt_downpayment_type);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        img_back.setOnClickListener(this);
        tb_tv_titile.setText("预览");
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        user = UserManager.getUser(this);
        payMonthly = (PayMonthly) intent.getSerializableExtra(PayMonthly.class.getSimpleName());
        if (!TextUtils.isEmpty(payMonthly.getBussinessCustName())) {
            txt_commodity_name.setText(payMonthly.getBussinessCustName());
        }
        if (!TextUtils.isEmpty(payMonthly.getDownpaymentTypeStr())) {
            txt_downpayment_type.setText(payMonthly.getDownpaymentTypeStr());
        }

        if (!TextUtils.isEmpty(payMonthly.getPhone())) {
            txt_coustomer_phone.setText(payMonthly.getPhone());
        }

        if (!TextUtils.isEmpty(payMonthly.getStartDate())) {
            txt_start_time.setText(payMonthly.getStartDate());
        }
        if (!TextUtils.isEmpty(payMonthly.getEndDate())) {
            txt_end_time.setText(payMonthly.getEndDate());
        }
        if (!TextUtils.isEmpty(payMonthly.getMonthRent())) {
            txt_commodity_price.setText(payMonthly.getMonthRent());
        }

        if (!TextUtils.isEmpty(payMonthly.getCommodityName())) {
            txt_commodity_address.setText(payMonthly.getCommodityName());
        }
    }

    /**
     * 提交订单
     *
     * @param bean
     * @param context
     */
    public static void startAction(PayMonthly bean, Context context) {
        Intent intent = new Intent(context, MonthlyPreviewActivity.class);
        intent.putExtra(PayMonthly.class.getSimpleName(), bean);
        context.startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submitCreateOrder();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    /**
     * 提交生成订单
     */
    private void submitCreateOrder() {
        showProgress();
        OkHttpUtils.post().url(NetConst.CREATE_QRCODE_FORHR)
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("bussinessId", user.getBussinessId())
                .addParams("commodityId", payMonthly.getCommodityId())
                .addParams("phone", payMonthly.getPhone())
                .addParams("startDate", payMonthly.getStartDate())
                .addParams("endDate", payMonthly.getEndDate())
                .addParams("downpaymentType", payMonthly.getDownpaymentType())
                .addParams("otherMonth", TextUtils.isEmpty(payMonthly.getOtherMonth()) ? "" : payMonthly.getOtherMonth())
                .addParams("monthRent", payMonthly.getMonthRent())
                .addParams("deposit", payMonthly.getDeposit())
                .addParams("bussinessCustName", user.getBussinessName())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        try {

                            if (checkResonse(response)) {
                                CommodityCreateResponse commodityCreateResponse = GsonUtil.changeGsonToBean(response, CommodityCreateResponse.class);
                                CommodityCreateInfo info = commodityCreateResponse.getResult();
                                Intent intent = new Intent(MonthlyPreviewActivity.this, PayMonthlyQRCodeActivity.class);
                                Bundle bundle = new Bundle();
                                payMonthly.setPackageId(info.getPackageId());
                                bundle.putSerializable(IntentFlag.SERIALIZABLE, payMonthly);
                                intent.putExtras(bundle);
                                intent.putExtra(IntentFlag.CONFORM, IntentFlag.INTENT_CONFORM_ZUFANG);
                                startActivity(intent);
                                finish();
                            }
                        } catch (Exception e) {
                            showToast("服务异常");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }
                });
    }

    public void onRefox(View view) {
        finish();
    }
}
