package com.hdfex.merchantqrshow.salesman.order.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.salesman.order.fragment.OrderAllFragment;

/**
 * Created by harrishuang on 16/10/19.
 */

public class OrderListNewActvitity extends BaseActivity {

    private LinearLayout layout_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlistnew);
        initView();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        OrderAllFragment orderFragment = new OrderAllFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", getIntent().getIntExtra("index", 0));
        bundle.putBoolean("back",true);
        orderFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.layout_fragment, orderFragment);
        fragmentTransaction.commit();
    }


    private void initView() {
        layout_fragment = (LinearLayout) findViewById(R.id.layout_fragment);
    }
}
