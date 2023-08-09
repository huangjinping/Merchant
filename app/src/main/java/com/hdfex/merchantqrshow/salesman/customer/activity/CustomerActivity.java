package com.hdfex.merchantqrshow.salesman.customer.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.customer.CustomerResult;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;

/**
 * 客户界面
 * Created by harrishuang on 16/9/28.
 */

public class CustomerActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt_customer_name;
    private TextView txt_biaoqian;
    private TextView txt_customer_phone;
    private ImageView img_back;
    private TextView txt_left_name;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private TextView txt_save_tel;
    private LinearLayout layout_save_tel;
    private CustomerResult customerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        initView();
        tb_tv_titile.setText("客户信息");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        customerResult = (CustomerResult) extras.getSerializable(IntentFlag.INTENT_CUSTOMER);
        if (!TextUtils.isEmpty(customerResult.getCustName())) {
            txt_customer_name.setText(customerResult.getCustName());
        }
        if (!TextUtils.isEmpty(customerResult.getPhone())) {
            txt_customer_phone.setText(StringUtils.getPhoneSpliteFormat(customerResult.getPhone()));
        }
    }

    private void initView() {
        txt_customer_name = (TextView) findViewById(R.id.txt_customer_name);
        txt_biaoqian = (TextView) findViewById(R.id.txt_biaoqian);
        txt_customer_phone = (TextView) findViewById(R.id.txt_customer_phone);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        txt_save_tel = (TextView) findViewById(R.id.txt_save_tel);
        layout_save_tel = (LinearLayout) findViewById(R.id.layout_save_tel);
        layout_save_tel.setOnClickListener(this);
        layout_toolbar.setBackgroundResource(R.color.white);
        tb_tv_titile.setTextColor(getResources().getColor(R.color.black));
        img_back.setImageResource(R.mipmap.ic_arrow_left);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_save_tel:
                saveExist(customerResult.getCustName(), customerResult.getPhone());
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    /**
     * save phone contact
     *
     * @param name
     * @param phone
     */
    public void saveExist(String name, String phone) {
        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
        intent.setType("vnd.android.cursor.item/person");
        intent.setType("vnd.android.cursor.item/contact");
        intent.setType("vnd.android.cursor.item/raw_contact");
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, 3);
        startActivity(intent);
    }


    public void onCall(View view) {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + customerResult.getPhone()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ToastUtils.e(this, "请您检查并开启拨打电话权限");
            return;
        }
        startActivity(intent);
    }


}
