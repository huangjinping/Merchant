package com.hdfex.merchantqrshow.salesman.order.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.salesman.order.adapter.OrderDetailRecyclerAdapter;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderDetails;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderDetailsResponse;
import com.hdfex.merchantqrshow.bean.salesman.order.QueryUncompelete;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 订单详情界面
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private RecyclerView lv_commodity;
    private User user;
    private String applyId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_detail);
        initView();
        initDate();
    }

    private void initDate() {
        user = UserManager.getUser(this);
        applyId = getIntent().getStringExtra("applyId");
        Serializable serializable = getIntent().getExtras().getSerializable(IntentFlag.SCAN_DETAILS);
        if (serializable == null) {
            tb_tv_titile.setText("订单详情");
            loadData(applyId);
        } else {
            tb_tv_titile.setText("申请详情");
            QueryUncompelete queryUncompelete = (QueryUncompelete) serializable;
            ChangeToOrderDetails(queryUncompelete);
        }

    }
    private void ChangeToOrderDetails(QueryUncompelete queryUncompelete) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setBusinessId("");
        orderDetails.setBusinessName("");
        orderDetails.setPic("");
        orderDetails.setOrderNo(queryUncompelete.getPackageId());
        orderDetails.setTime(queryUncompelete.getScanDate());
        orderDetails.setTotalPrice(queryUncompelete.getTotalPrice());
        orderDetails.setPeriodAmount(queryUncompelete.getPeriodAmount());
        orderDetails.setDuration(queryUncompelete.getDuration());
        orderDetails.setDownpayment(queryUncompelete.getDownpayment());
        orderDetails.setApplyAmount(queryUncompelete.getApplyAmount());
        orderDetails.setStatus("未提交");
        orderDetails.setFeedback("");
        orderDetails.setBankName("");
        orderDetails.setCardNo("");
        orderDetails.setPhone(queryUncompelete.getPhone());
        orderDetails.setIdName(queryUncompelete.getIdName());
        orderDetails.setOrderCommoditys(queryUncompelete.getCommoditys());
        lv_commodity.setAdapter(new OrderDetailRecyclerAdapter(OrderDetailActivity.this, orderDetails));
    }

    private void loadData(String applyId) {
        if (TextUtils.isEmpty(applyId) || !connect()) {
            return;
        }
        OkHttpUtils
                .post()
                .url(NetConst.ORDER_DETAIL)
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
                        showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
//                            LogUtil.e("zbt1",response);
                            boolean b = checkResonse(response);
                            if (b) {
                                OrderDetailsResponse orderDetailsResponse = GsonUtil.changeGsonToBean(response, OrderDetailsResponse.class);
                                OrderDetails orderDetails = orderDetailsResponse.getResult();
                                lv_commodity.setAdapter(new OrderDetailRecyclerAdapter(OrderDetailActivity.this, orderDetails));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr();
                        }
                    }
                });
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        lv_commodity = (RecyclerView) findViewById(R.id.lv_commodity);
        lv_commodity.setLayoutManager(new LinearLayoutManager(this));
        img_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}
