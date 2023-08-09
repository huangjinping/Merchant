package com.hdfex.merchantqrshow.salesman.order.activity;

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
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderMonthlyDetail;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderMonthlyDetailResponse;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;
import okhttp3.Request;

/**
 *
 * author Created by harrishuang on 2018/7/26.
 * email : huangjinping@hdfex.com
 * 月付的订单详情
 *
 */
public class MonthlyOrderDetailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_commodity_name;
    private TextView txt_commodity_address;
    private TextView txt_commodity_price;
    private TextView txt_coustomer_name;
    private TextView txt_order_status;
    private TextView txt_start_time;
    private TextView txt_end_time;
    private TextView txt_downpayment_type;
    private Button btn_operation;
    private Button btn_submit;
    private String applyId;
    private User user;
    private OrderMonthlyDetail currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_orderdetails);
        initView();
        user = UserManager.getUser(this);
        applyId = getIntent().getStringExtra("applyId");
        getData();
    }


    public static void startAction(Context context, String applyId, View view) {
        Intent intent = new Intent(context, MonthlyOrderDetailsActivity.class);
        intent.putExtra("applyId", applyId);
        context.startActivity(intent);
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        txt_commodity_name = (TextView) findViewById(R.id.txt_commodity_name);
        txt_commodity_address = (TextView) findViewById(R.id.txt_commodity_address);
        txt_commodity_price = (TextView) findViewById(R.id.txt_commodity_price);
        txt_coustomer_name = (TextView) findViewById(R.id.txt_coustomer_name);
        txt_order_status = (TextView) findViewById(R.id.txt_order_status);
        txt_start_time = (TextView) findViewById(R.id.txt_start_time);
        txt_end_time = (TextView) findViewById(R.id.txt_end_time);
        txt_downpayment_type = (TextView) findViewById(R.id.txt_downpayment_type);
        btn_operation = (Button) findViewById(R.id.btn_operation);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_operation.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        img_back.setOnClickListener(this);
        tb_tv_titile.setText("订单详情");
    }


    /**
     * 获取数据
     */
    private void getData() {
        if (TextUtils.isEmpty(applyId)) {
            showToast("数据异常");
            return;
        }
        OkHttpUtils.getInstance()
                .post()
                .tag(this)
                .url(NetConst.CONTRACT_GET_CONTRACT_DETAIL)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("applyId", applyId)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
//                        LogUtil.i(OrderDetailActivity.class.getSimpleName(), e.toString() + "onError");
                    }

                    @Override
                    public void onResponse(String response) {

                        if (checkResonse(response)) {
                            OrderMonthlyDetailResponse mOrderDetail = GsonUtil.changeGsonToBean(response, OrderMonthlyDetailResponse.class);
                            OrderMonthlyDetail result = mOrderDetail.getResult();
                            setViewByCode(result);
                        } else {
                            showToast("数据加载异常稍后重试！");
                        }
                    }
                });
    }

    /**
     * 设置ViewByCode
     */
    private void setViewByCode(OrderMonthlyDetail order) {
        if (order == null) {
            return;
        }
        currentOrder = order;
        if (!TextUtils.isEmpty(order.getCommodityName())) {
            txt_commodity_address.setText(order.getCommodityName());
        }

        txt_commodity_price.setText("月付金额：" + order.getRentAmt() + "押金：" + order.getDeposit());

        if (!TextUtils.isEmpty(order.getPayTypeName())) {
            txt_downpayment_type.setText(order.getPayTypeName());
        }

//        if (!TextUtils.isEmpty(order.getRenterPhone())) {
//            txt_coustomer_phone.setText(order.getRenterPhone());
//        }

        if (!TextUtils.isEmpty(order.getStartDate())) {
            txt_start_time.setText(order.getStartDate());
        }
        if (!TextUtils.isEmpty(order.getEndDate())) {
            txt_end_time.setText(order.getEndDate());
        }
        if (!TextUtils.isEmpty(order.getStatusDesc())) {
            txt_order_status.setText(order.getStatusDesc());
        }

//        if ("2000".equals(order.getExamineStatus())) {
//            btn_operation.setVisibility(View.VISIBLE);
//            btn_operation.setText(order.getExamineStatusDesc() + "");
//        } else {
//            btn_operation.setVisibility(View.GONE);
//        }

//        if (!TextUtils.isEmpty(order.getButtonDescTitle()) && !TextUtils.isEmpty(order.getUrl())) {
//            btn_submit.setVisibility(View.VISIBLE);
//        } else {
//            btn_submit.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_operation:

                break;
            case R.id.btn_submit:

                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
