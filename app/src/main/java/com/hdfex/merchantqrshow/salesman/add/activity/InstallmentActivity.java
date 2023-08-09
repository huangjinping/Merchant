package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.add.adapter.InstallmentAdapter;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.ScreenUtil;
import com.hdfex.merchantqrshow.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分期选择
 * Created by harrishuang on 2017/3/28.
 */

public class InstallmentActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private ListView lisv_periods;
    private InstallmentAdapter adapter;
    private List<Installment> dataList;
    private int index = -1;
    private TextView txt_left_name;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private TextView text_periods_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periods);
        initView();
        dataList = new ArrayList<>();
        adapter = new InstallmentAdapter(dataList, this);
        Order order = (Order) getIntent().getSerializableExtra(IntentFlag.INTENT_NAME);
        if (order != null && order.getList() != null) {
            dataList.addAll(order.getList());
        }
        index = getIntent().getIntExtra(IntentFlag.INDEX, 0);
        lisv_periods.setAdapter(adapter);
        adapter.setIndex(index);
        adapter.notifyDataSetChanged();
        lisv_periods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                EventRxBus.getInstance().post(IntentFlag.CURRENT_DURATION, i);
                InstallmentActivity.this.finish();
//                try {
//                    Installment typeModel = dataList.get(i);
//                    if (Installment.STATUS_YES.equals(typeModel.getStatus())){
//
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
            }
        });
        lisv_periods.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                try {
                    // 务必取消监听，否则会多次调用
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                        lisv_periods.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        lisv_periods.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    int count = adapter.getCount();
                    int height = 0;
                    for (int i = 0; i < count; i++) {
                        View temp = adapter.getView(i, null, lisv_periods);
                        temp.measure(0, 0);
                        height += temp.getMeasuredHeight();
                    }
                    int temp = ScreenUtil.getHeight(InstallmentActivity.this) - Utils.dp2px(InstallmentActivity.this, 100);
                    if (temp > height) {
                        text_periods_alert.setVisibility(View.VISIBLE);
                    } else {
                        View view = LayoutInflater.from(InstallmentActivity.this).inflate(R.layout.layout_periods_footer, null);
                        lisv_periods.addFooterView(view);
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        lisv_periods = (ListView) findViewById(R.id.lisv_periods);
        img_back.setOnClickListener(this);
        tb_tv_titile.setText("选择分期产品");
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        txt_left_name.setOnClickListener(this);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        layout_toolbar.setOnClickListener(this);
        text_periods_alert = (TextView) findViewById(R.id.text_periods_alert);
        text_periods_alert.setOnClickListener(this);
    }


    public static void start(Context context, Order order, int index) {
        Intent intent = new Intent(context, InstallmentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.INTENT_NAME, order);
        bundle.putInt(IntentFlag.INDEX, index);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                InstallmentActivity.this.finish();
                break;
        }
    }
}
