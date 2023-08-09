package com.hdfex.merchantqrshow.manager.team.activity;

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
import com.hdfex.merchantqrshow.bean.manager.team.Person;
import com.hdfex.merchantqrshow.bean.manager.team.PersonDetail;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.SalesmanContract;
import com.hdfex.merchantqrshow.mvp.presenter.SalesmanPresenter;
import com.hdfex.merchantqrshow.utils.UserManager;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class SalesmanActivity extends BaseActivity implements View.OnClickListener, SalesmanContract.View {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_name;
    private TextView txt_region;
    private TextView txt_phoneNo;
    private TextView txt_status;
    private PersonDetail result;
    private SalesmanContract.Presenter presenter;
    private Button btn_enable;
    private Button btn_disenable;
    private User user;
    private String id;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesman);
        initView();
        presenter = new SalesmanPresenter();
        presenter.attachView(this);
        user = UserManager.getUser(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        result = (PersonDetail) intent.getSerializableExtra(PersonDetail.class.getSimpleName());
        if (!TextUtils.isEmpty(result.getName())) {
            txt_name.setText(result.getName());
        }
        if (!TextUtils.isEmpty(result.getPhoneNo())) {
            txt_phoneNo.setText(result.getPhoneNo());
        }
        if (!TextUtils.isEmpty(result.getRegion())) {
            txt_region.setText(result.getRegion());
        }
        updateView(result);
    }


    private void updateView(PersonDetail result) {
        if (!TextUtils.isEmpty(result.getStatus())) {
            if (Person.STATUS_DISENABLED.equals(result.getStatus())) {
                txt_status.setText("禁用");
                btn_enable.setVisibility(View.VISIBLE);
                btn_disenable.setVisibility(View.GONE);
            } else if (Person.STATUS_ENABLED.equals(result.getStatus())) {
                txt_status.setText("在职");
                btn_disenable.setVisibility(View.VISIBLE);
                btn_enable.setVisibility(View.GONE);
            }
        }

    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_region = (TextView) findViewById(R.id.txt_region);
        txt_phoneNo = (TextView) findViewById(R.id.txt_phoneNo);
        txt_status = (TextView) findViewById(R.id.txt_status);
        tb_tv_titile.setText("业务员详情");
        btn_enable = (Button) findViewById(R.id.btn_enable);
        btn_enable.setOnClickListener(this);
        btn_disenable = (Button) findViewById(R.id.btn_disenable);
        btn_disenable.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_back:
                finish();
                break;
            case R.id.btn_enable:
                presenter.requestPersonForbid(user, id, Person.STATUS_ENABLED);

                break;
            case R.id.btn_disenable:
                presenter.requestPersonForbid(user, id, Person.STATUS_DISENABLED);

                break;
        }
    }

    public static void startAction(Context context, PersonDetail result, String id) {
        Intent intent = new Intent(context, SalesmanActivity.class);
        intent.putExtra(PersonDetail.class.getSimpleName(), result);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void returnResult(String status) {

        if (Person.STATUS_ENABLED.equals(status)) {
            result.setStatus(Person.STATUS_ENABLED);
        } else {
            result.setStatus(Person.STATUS_DISENABLED);
        }
        updateView(result);
    }
}
