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
import com.hdfex.merchantqrshow.salesman.main.activity.IndexActivity;

/**
 * author Created by harrishuang on 2017/7/5.
 * email : huangjinping@hdfex.com
 */

public class HuaBeiFailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView tv_result_content;
    private Button button_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huabeifails);
        initView();
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        if (!TextUtils.isEmpty(result)){
            tv_result_content.setText(result);
        }
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tv_result_content = (TextView) findViewById(R.id.tv_result_content);
        button_next = (Button) findViewById(R.id.button_next);
        tb_tv_titile.setText("支付失败");
        button_next.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_back.setVisibility(View.GONE);
    }

    public static void startAction(Context context,String tv_result_content) {
        Intent intent=new Intent(context,HuaBeiFailsActivity.class);
        intent.putExtra("result",tv_result_content);
        context.startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        toMain();
    }

    private void toMain(){
        Intent mIntent = new Intent(this, IndexActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_next:
                toMain();
                break;
        }
    }
}
