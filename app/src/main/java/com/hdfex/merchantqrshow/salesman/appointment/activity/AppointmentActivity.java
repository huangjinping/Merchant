package com.hdfex.merchantqrshow.salesman.appointment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.appointment.BussinessSubscribeDetail;
import com.hdfex.merchantqrshow.bean.salesman.appointment.SubscribeItem;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2017/9/8.
 * email : huangjinping@hdfex.com
 */

public class AppointmentActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt_transfer_order;
    private BussinessSubscribeDetail subscribeDetail;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_courtName;
    private TextView txt_status;
    private TextView txt_addrDetail;
    private TextView txt_subscribeDate;
    private TextView txt_contactName;
    private TextView txt_contactPhone;
    private TextView txt_remark;
    private TextView txt_bizName;
    private TextView txt_turnDesc;
    private TextView txt_subscribeId;
    private TextView txt_submitDate;
    private TextView txt_type;
    private LinearLayout layout_turn_content;
    private Button btn_submit;
    private User user;

    public static void startAction(Context context, BussinessSubscribeDetail subscribeDetail) {
        Intent intent = new Intent(context, AppointmentActivity.class);
        intent.putExtra(BussinessSubscribeDetail.class.getSimpleName(), subscribeDetail);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        initView();
        user = UserManager.getUser(this);

        Intent intent = getIntent();
        subscribeDetail = (BussinessSubscribeDetail) intent.getSerializableExtra(BussinessSubscribeDetail.class.getSimpleName());
        setViewByData(subscribeDetail);
    }

    private void initView() {
        txt_transfer_order = (TextView) findViewById(R.id.txt_transfer_order);
        txt_transfer_order.setOnClickListener(this);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        txt_courtName = (TextView) findViewById(R.id.txt_courtName);
        txt_status = (TextView) findViewById(R.id.txt_status);
        txt_addrDetail = (TextView) findViewById(R.id.txt_addrDetail);
        txt_subscribeDate = (TextView) findViewById(R.id.txt_subscribeDate);
        txt_contactName = (TextView) findViewById(R.id.txt_contactName);
        txt_contactPhone = (TextView) findViewById(R.id.txt_contactPhone);
        txt_remark = (TextView) findViewById(R.id.txt_remark);
        txt_bizName = (TextView) findViewById(R.id.txt_bizName);
        txt_turnDesc = (TextView) findViewById(R.id.txt_turnDesc);
        txt_subscribeId = (TextView) findViewById(R.id.txt_subscribeId);
        txt_submitDate = (TextView) findViewById(R.id.txt_submitDate);
        txt_type = (TextView) findViewById(R.id.txt_type);
        tb_tv_titile.setText("预约详情");
        layout_turn_content = (LinearLayout) findViewById(R.id.layout_turn_content);
        layout_turn_content.setOnClickListener(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    /**
     * 下载更新数据
     *
     * @param subscribeDetail
     */
    private void setViewByData(BussinessSubscribeDetail subscribeDetail) {
        if (subscribeDetail == null) {
            return;
        }


        if (SubscribeItem.STATUS_NOT_FOLLOW_UP.equals(subscribeDetail.getStatus())) {
            txt_status.setText("未跟进");
        } else if (SubscribeItem.STATUS_FOLLOWED.equals(subscribeDetail.getStatus())) {
            txt_status.setText("已跟进");
            btn_submit.setVisibility(View.VISIBLE);
        } else if (SubscribeItem.STATUS_COMPLATE.equals(subscribeDetail.getStatus())) {
            txt_status.setText("已完成");
        } else if (SubscribeItem.STATUS_TURNING.equals(subscribeDetail.getStatus())) {
            txt_status.setText("转单中");
        }


        if ("03".equals(subscribeDetail.getType())) {
            if (!TextUtils.isEmpty(subscribeDetail.getCourtName())) {
                txt_courtName.setText(subscribeDetail.getCourtName());
            }
            if (!TextUtils.isEmpty(subscribeDetail.getAddrDetail())) {
                txt_addrDetail.setText(subscribeDetail.getAddrDetail());
            }
//            if (!TextUtils.isEmpty(subscribeDetail.getSubscribeDate())) {
//                txt_subscribeDate.setText(subscribeDetail.getSubscribeDate());
//            }

            if (!TextUtils.isEmpty(subscribeDetail.getAddress())) {
                txt_courtName.setText(subscribeDetail.getAddress());
            }
            if (!TextUtils.isEmpty(subscribeDetail.getExpectAmount())) {
                txt_addrDetail.setText("期望租金:" + subscribeDetail.getExpectAmount() + "元");
            }

            if (!TextUtils.isEmpty(subscribeDetail.getExpectAmount()) && !TextUtils.isEmpty(subscribeDetail.getCheckInDate())) {
                txt_addrDetail.setText("期望租金:" + subscribeDetail.getExpectAmount() + "元/月 入住时间:" + subscribeDetail.getCheckInDate());
            }
//            if (!TextUtils.isEmpty(subscribeDetail.getRemark())) {
//                txt_subscribeDate.setText(subscribeDetail.getRemark());
//            }

        } else {
            if (!TextUtils.isEmpty(subscribeDetail.getCourtName())) {
                txt_courtName.setText(subscribeDetail.getCourtName());
            }
            if (!TextUtils.isEmpty(subscribeDetail.getAddrDetail())) {
                txt_addrDetail.setText(subscribeDetail.getAddrDetail());
            }
            if (!TextUtils.isEmpty(subscribeDetail.getSubscribeDate())) {
                txt_subscribeDate.setText(subscribeDetail.getSubscribeDate());
            }

        }


        if (!TextUtils.isEmpty(subscribeDetail.getContactName())) {
            txt_contactName.setText(subscribeDetail.getContactName());
        }
        if (!TextUtils.isEmpty(subscribeDetail.getContactPhone())) {
            txt_contactPhone.setText(StringUtils.getPhoneFormat(subscribeDetail.getContactPhone()));
        }
        if (!TextUtils.isEmpty(subscribeDetail.getRemark())) {
            txt_remark.setText(subscribeDetail.getRemark());
        }
        if (!TextUtils.isEmpty(subscribeDetail.getBizName())) {
            txt_bizName.setText(subscribeDetail.getBizName());
        }
        if (!TextUtils.isEmpty(subscribeDetail.getTurnDesc())) {
            txt_turnDesc.setText(subscribeDetail.getTurnDesc());
        }
        if (!TextUtils.isEmpty(subscribeDetail.getSubscribeId())) {
            txt_subscribeId.setText("预约编号：" + subscribeDetail.getSubscribeId());
        }

        if (!TextUtils.isEmpty(subscribeDetail.getSubmitDate())) {
            txt_submitDate.setText("提交时间：" + subscribeDetail.getSubmitDate());
        }
//00-	自主预约
//01-	公共转单
//02-	同行转单
//    03-	发布
        layout_turn_content.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(subscribeDetail.getType())) {
            if ("00".equals(subscribeDetail.getType())) {
                txt_type.setText("预约类型：自主预约");
            } else if ("01".equals(subscribeDetail.getType())) {
                txt_type.setText("预约类型：公共转单");
            } else if ("02".equals(subscribeDetail.getType())) {
                layout_turn_content.setVisibility(View.VISIBLE);
                txt_type.setText("预约类型：同行转单");
            } else if ("03".equals(subscribeDetail.getType())) {
                txt_type.setText("预约类型：发布");
            }
        }
    }

    /**
     * 提交订单
     */
    private void onTransFerOrder() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_transfer_order:
                onTransFerOrder();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                onComplate();

                break;
        }
    }

    /**
     * 完成接口
     */
    private void onComplate() {
        showProgress();
        OkHttpUtils.post().addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("subscribeId", subscribeDetail.getSubscribeId())
                .url(NetConst.FINISH_SUBSCRIBE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                dismissProgress();
                if (checkResonse(response)) {
                    EventRxBus.getInstance().post(IntentFlag.DETAILS_COMPLATE_UPDATE, IntentFlag.DETAILS_COMPLATE_UPDATE);
                    finish();
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissProgress();
            }
        });
    }

}
