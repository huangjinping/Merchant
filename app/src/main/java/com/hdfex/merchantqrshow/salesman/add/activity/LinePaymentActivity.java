package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.add.fragment.InputHuabeiFragment;
import com.hdfex.merchantqrshow.widget.DeleteEditText;

import java.util.List;

/**
 * author Created by harrishuang on 2017/7/31.
 * email : huangjinping@hdfex.com
 */

public class LinePaymentActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_huabei_quota;
    private DeleteEditText edt_apply_amount;
    private TextView txt_commodity_name;
    private Order order;
    private List<Installment> projectList;
    private String commodityId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linepayment);
        initView();
        tb_tv_titile.setText("在线支付");
        Intent intent = getIntent();
        parseConfirm(intent);

    }

    /**
     * 解析数据
     *
     * @param intent
     */
    private void parseConfirm(Intent intent) {
        if (intent.getSerializableExtra(IntentFlag.SUBMIT_ORDER) != null) {
            order = (Order) intent.getSerializableExtra(IntentFlag.SUBMIT_ORDER);
            projectList = order.getList();
            edt_apply_amount.setText(order.getTotalPrice() + "");
            txt_commodity_name.setText(order.getCommoditysList().get(0).getCommodityName());
            commodityId = order.getCommoditysList().get(0).getCommodityId();
        }
    }


    public static void startAction(Context context, Order order) {
        Intent intent = new Intent(context, LinePaymentActivity.class);
        intent.putExtra(IntentFlag.SUBMIT_ORDER, order);
        context.startActivity(intent);
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        txt_huabei_quota = (TextView) findViewById(R.id.txt_huabei_quota);
        txt_huabei_quota.setOnClickListener(this);
        edt_apply_amount = (DeleteEditText) findViewById(R.id.edt_apply_amount);
        edt_apply_amount.setOnClickListener(this);
        txt_commodity_name = (TextView) findViewById(R.id.txt_commodity_name);
        txt_commodity_name.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_huabei_quota:
                HuabeiQuotaActivity.startAction(this);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.edt_apply_amount:
                break;
        }
    }



}
