package com.hdfex.merchantqrshow.salesman.order.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderCommodityDetail;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderDetails;
import com.hdfex.merchantqrshow.mvp.contract.OrderDetailsContract;
import com.hdfex.merchantqrshow.mvp.presenter.OrderDetailsPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.add.activity.UpLoadSendActivity;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.jakewharton.rxbinding.view.RxView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;

/**
 * author Created by harrishuang on 2017/5/25.
 * email : huangjinping@hdfex.com
 */

public class MutliOrderDetailsActivity extends BaseActivity implements OrderDetailsContract.View, View.OnClickListener {

    private OrderDetailsContract.Persenter persenter;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView tv_home;
    private User user;
    private ScrollView sc_root;
    private String applyId;
    private LinearLayout layout_root;
    private ImageView img_header_icon;
    private TextView txt_order_status;
    private TextView txt_order_status_desc;
    private TextView txt_order_feedback_name;
    private TextView txt_order_message;
    private LinearLayout layout_feedback;
    private TextView txt_order_num;
    private TextView txt_idName;
    private TextView txt_phone;
    private TextView txt_commodityName;
    private TextView txt_applyAmount;
    private TextView txt_duration;
    private LinearLayout layout_order_phone;

    private Button btn_order_toplan;
    private Button btn_order_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutliorderdetails);
        persenter = new OrderDetailsPresenter();
        persenter.attachView(this);
        user = UserManager.getUser(this);
        initView();
        applyId = getIntent().getStringExtra(IntentFlag.APPLYID);
        RxView.clicks(tv_home).throttleFirst(2 * 1000, TimeUnit.MILLISECONDS).subscribe(new Subscriber<Void>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                saveCurrentImage();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        persenter.loadOrderDetails(applyId, user);
    }

    private void initView() {

        sc_root = (ScrollView) findViewById(R.id.sc_root);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setText("订单详情");
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);
        tv_home.setVisibility(View.VISIBLE);
        tv_home.setText("···");
        tv_home.setTextSize(30);
        layout_root = (LinearLayout) findViewById(R.id.layout_root);

        img_header_icon = (ImageView) findViewById(R.id.img_header_icon);
        txt_order_status = (TextView) findViewById(R.id.txt_order_status);
        txt_order_status_desc = (TextView) findViewById(R.id.txt_order_status_desc);
        txt_order_feedback_name = (TextView) findViewById(R.id.txt_order_feedback_name);
        txt_order_message = (TextView) findViewById(R.id.txt_order_message);
        layout_feedback = (LinearLayout) findViewById(R.id.layout_feedback);
        txt_order_num = (TextView) findViewById(R.id.txt_order_num);
        txt_idName = (TextView) findViewById(R.id.txt_idName);
        txt_phone = (TextView) findViewById(R.id.txt_phone);
        txt_commodityName = (TextView) findViewById(R.id.txt_commodityName);
        txt_applyAmount = (TextView) findViewById(R.id.txt_applyAmount);
        txt_duration = (TextView) findViewById(R.id.txt_duration);
        layout_order_phone = (LinearLayout) findViewById(R.id.layout_order_phone);
        layout_order_phone.setOnClickListener(this);
        btn_order_toplan = (Button) findViewById(R.id.btn_order_toplan);
        btn_order_toplan.setOnClickListener(this);
        btn_order_update = (Button) findViewById(R.id.btn_order_update);
        btn_order_update.setOnClickListener(this);
    }

    private void saveCurrentImage() {
        persenter.shotAllView(this, layout_root, new View[]{sc_root});
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_order_phone:
                if (layout_order_phone.getTag() != null) {
                    showAlertForPhone(layout_order_phone.getTag().toString());
                }
                break;
            case R.id.btn_upload:
                /**
                 * 上传图片
                 */
                UpLoadSendActivity.start(this, applyId);
                break;
            case R.id.btn_order_toplan:
                persenter.loadRepayDetail(this, user, applyId);
                break;
            case R.id.btn_order_update:
                persenter.loadOrderDetails(applyId, user);

                break;
        }

    }

    public static void startAction(Context mContext, String applyId, View view) {
        Intent intent = new Intent(mContext, MutliOrderDetailsActivity.class);
        intent.putExtra(IntentFlag.APPLYID, applyId);
        if (view == null) {
            mContext.startActivity(intent);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) mContext, view, IntentFlag.APPLYID);
            mContext.startActivity(intent, options.toBundle());
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
        }
    }

    @Override
    public void returnShotBitmap(Bitmap bitmap) {

    }

    @Override
    public void returnShotFile(File file) {

    }

    @Override
    public void returnDetails(OrderDetails orderDetails) {


        if (!TextUtils.isEmpty(orderDetails.getId())) {
            txt_order_num.setText(orderDetails.getId());
        }

        layout_order_phone.setTag(orderDetails.getPhone());
        if (!TextUtils.isEmpty(orderDetails.getStatus())) {
            txt_order_status.setText(orderDetails.getStatus());
        }
//
        if (!TextUtils.isEmpty(orderDetails.getIdName())) {
            txt_idName.setText(orderDetails.getIdName());
        }
        if (!TextUtils.isEmpty(orderDetails.getPhone())) {
            txt_phone.setText(StringUtils.getPhoneFormat(orderDetails.getPhone()));
        }
//
        if (!TextUtils.isEmpty(orderDetails.getPeriodAmount())) {
            txt_duration.setText(getString(R.string.periodAmount_duration, orderDetails.getPeriodAmount() + "", orderDetails.getDuration() + ""));
        }


        if (!TextUtils.isEmpty(orderDetails.getGracePeriod()) && !TextUtils.isEmpty(orderDetails.getGracePeriodAmount())) {
            if (orderDetails.getPeriodAmount() != null) {
                txt_duration.setText("宽限期¥" + orderDetails.getGracePeriodAmount() + " x " + orderDetails.getGracePeriod() + "期\n非宽限期¥" + orderDetails.getPeriodAmount() + " x " + orderDetails.getDuration() + "期");
            }
        } else {
            if (orderDetails.getPeriodAmount() != null) {
                txt_duration.setText("¥" + orderDetails.getPeriodAmount() + " x " + orderDetails.getDuration() + "期");
            }
        }
        if (!TextUtils.isEmpty(orderDetails.getApplyAmount())) {
            txt_applyAmount.setText(orderDetails.getApplyAmount());
        }


        String status = orderDetails.getStatus();


        if (status.indexOf("取消") != -1) {
            setOrderHeader(R.mipmap.ic_order_yiquxiao, status, null);
        } else if (status.indexOf("拒绝") != -1) {
            setOrderHeader(R.mipmap.ic_order_yijujue, status, "综合评分不足，暂不支持借款");
        } else if (status.indexOf("签") != -1) {
            setOrderHeader(R.mipmap.ic_order_daiqianyue, status, "请进行签约，完成分期申请");

        } else if (status.indexOf("打回") != -1) {
            setOrderHeader(R.mipmap.ic_order_dahui, status, "申请资料存在问题，请修改后重新提交");

        } else if (status.indexOf("通过") != -1) {
            setOrderHeader(R.mipmap.ic_order_yitongguo, status, "分期审核通过，等待最新放款");

        } else if (status.indexOf("还款中") != -1) {
            setOrderHeader(R.mipmap.ic_order_yitongguo, "已通过", null);
        } else {
            setOrderHeader(R.mipmap.ic_order_shenhezhong, status, null);
        }


        String commodityName = getAddressByCommodity(orderDetails.getOrderCommoditys());
        if (!TextUtils.isEmpty(commodityName)) {
            txt_commodityName.setText(commodityName);
        }


        if (!TextUtils.isEmpty(orderDetails.getFeedback())) {

            layout_feedback.setVisibility(View.VISIBLE);
            txt_order_message.setText("" + orderDetails.getFeedback());
        } else {
            layout_feedback.setVisibility(View.GONE);

        }

        if ("还款中".equals(orderDetails.getStatus())) {
            btn_order_toplan.setVisibility(View.VISIBLE);
        } else if ("提前结清".equals(orderDetails.getStatus())) {
            btn_order_toplan.setVisibility(View.VISIBLE);
        } else if ("已完成".equals(orderDetails.getStatus())) {
            btn_order_toplan.setVisibility(View.VISIBLE);
        } else if ("逾期中".equals(orderDetails.getStatus())) {
            btn_order_toplan.setVisibility(View.VISIBLE);
        } else {
            btn_order_toplan.setVisibility(View.GONE);
        }
    }


    private void setOrderHeader(int id, String status, String desc) {
        img_header_icon.setImageResource(id);
        txt_order_status.setText(status);
        if (TextUtils.isEmpty(desc)) {
            txt_order_status_desc.setVisibility(View.GONE);
        } else {
            txt_order_status_desc.setVisibility(View.VISIBLE);
            txt_order_status_desc.setText(desc);
        }
    }


    /**
     * @param orderCommoditys
     * @return
     */
    private String getAddressByCommodity(ArrayList<OrderCommodityDetail> orderCommoditys) {
        String s = "";

        try {
            StringBuffer StringBuffer = new StringBuffer();
            if (orderCommoditys == null || orderCommoditys.size() == 0) {
                return StringBuffer.toString();
            }
            for (OrderCommodityDetail detail : orderCommoditys) {
                if (!TextUtils.isEmpty(detail.getCommodityAddress())) {
                    StringBuffer.append(detail.getCommodityAddress());
                    StringBuffer.append(",");
                } else {
                    StringBuffer.append(detail.getCommodityName());
                    StringBuffer.append(",");
                }
            }
            s = StringBuffer.toString();
            if (s.endsWith(",")) {
                s = s.substring(0, s.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        persenter.detachView();
    }

    public void onPlanDetails(View view) {

        persenter.loadRepayDetail(this, user, applyId);
    }
}
