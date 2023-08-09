package com.hdfex.merchantqrshow.admin.bam.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.manager.team.Person;
import com.hdfex.merchantqrshow.bean.manager.team.PersonDetail;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.AddEmployeeContract;
import com.hdfex.merchantqrshow.mvp.presenter.AddEmployeePresenter;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;

/**
 * 添加页面
 * author Created by harrishuang on 2017/11/30.
 * email : huangjinping@hdfex.com
 */

public class AddEmployeeActivity extends BaseActivity implements AddEmployeeContract.View, View.OnClickListener {
    private AddEmployeeContract.Presenter presenter;
    private EditText edt_user_name;
    private ImageView img_phone_select;
    private EditText edt_user_password;
    private TextView txt_select_category;
    private CheckBox chc_password;
    private EditText edt_user_phone;
    private EditText edt_remark;
    private User user;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private String id;
    private PersonDetail result;
    private TextView txt_status;
    private Button btn_edit_employee;
    private LinearLayout layout_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        initView();
        presenter = new AddEmployeePresenter();
        presenter.attachView(this);
        chc_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//选择状态 显示明文--设置为可见的密码
                    edt_user_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
//默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    edt_user_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        /***
         *
         */
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            tb_tv_titile.setText("编辑员工");
            btn_edit_employee.setText("提交");
            result = (PersonDetail) intent.getSerializableExtra(PersonDetail.class.getSimpleName());
            if (!TextUtils.isEmpty(result.getName())) {
                edt_user_name.setText(result.getName());
            }
            layout_status.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(result.getPhoneNo())) {
                edt_user_phone.setText(result.getPhoneNo());
            }
            if (!TextUtils.isEmpty(result.getRegion())) {
                edt_remark.setText(result.getRegion());
            }

            updateView(result);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = UserManager.getUser(this);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AddEmployeeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            presenter.onActivityResult(this, requestCode, resultCode, data);
        } catch (Exception e) {
            ToastUtils.i(this, "选择联系人失败，请手动输入").show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setPhoneMessage(int requestCode, String name, String phoneNum) {
        edt_user_name.setText(name);
        edt_user_phone.setText(phoneNum);
    }

    @Override
    public void onRegionReturn(int index, String item) {
        edt_remark.setText(item);
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

    /**
     * update  更新View
     * @param result
     */
    private void updateView(PersonDetail result) {
        if (!TextUtils.isEmpty(result.getStatus())) {
            if (Person.STATUS_DISENABLED.equals(result.getStatus())) {
                txt_status.setText("禁用");
                txt_status.setTextColor(getResources().getColor(R.color.red_light));
            } else if (Person.STATUS_ENABLED.equals(result.getStatus())) {
                txt_status.setText("在职");
                txt_status.setTextColor(getResources().getColor(R.color.blue_light));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_phone_select:
                presenter.selectContacts(this, 1);
                break;
            case R.id.txt_select_category:
                presenter.getRegionList(user, this);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_status:
                if (Person.STATUS_DISENABLED.equals(result.getStatus())) {
//                    txt_status.setText("禁用");
                    presenter.requestPersonForbid(user, id, Person.STATUS_ENABLED);
                } else if (Person.STATUS_ENABLED.equals(result.getStatus())) {
//                    txt_status.setText("在职");
                    presenter.requestPersonForbid(user, id, Person.STATUS_DISENABLED);
                }
                break;
            case R.id.btn_edit_employee:
                submit();
                break;
        }

    }

    private void initView() {
        edt_user_name = (EditText) findViewById(R.id.edt_user_name);
        img_phone_select = (ImageView) findViewById(R.id.img_phone_select);
        edt_user_password = (EditText) findViewById(R.id.edt_user_password);
        txt_select_category = (TextView) findViewById(R.id.txt_select_category);
        img_phone_select.setOnClickListener(this);
        txt_select_category.setOnClickListener(this);
        chc_password = (CheckBox) findViewById(R.id.chc_password);
        edt_user_phone = (EditText) findViewById(R.id.edt_user_phone);
        edt_user_phone.setOnClickListener(this);
        edt_remark = (EditText) findViewById(R.id.edt_remark);
        edt_remark.setOnClickListener(this);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setOnClickListener(this);
        tb_tv_titile.setText("添加员工");
        txt_status = (TextView) findViewById(R.id.txt_status);
        txt_status.setOnClickListener(this);
        btn_edit_employee = (Button) findViewById(R.id.btn_edit_employee);
        btn_edit_employee.setOnClickListener(this);
        layout_status = (LinearLayout) findViewById(R.id.layout_status);
        layout_status.setOnClickListener(this);
    }


    private void submit() {
        // validate
        String name = edt_user_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("从通讯录选择更方便");
            return;
        }
        String phone = edt_user_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入正确手机号");
            return;
        }

        String remark = edt_remark.getText().toString().trim();
        if (TextUtils.isEmpty(remark)) {
            showToast("输入新标签");
            return;
        }
        String password = edt_user_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码");
            return;
        }

        // TODO validate success, do something



        String  url="";
        if (!TextUtils.isEmpty(id)){
            url=NetConst.UPDATE_BIZ_USER;
        }else {
            url=NetConst.SAVE_BIZ_USER;
        }



        showProgress();
        OkHttpUtils.post()
                .addParams("id", user.getBussinessId())
                .addParams("token", user.getToken())
                .addParams("phoneNo", phone)
                .addParams("password", password)
                .addParams("name", name)
                .addParams("userId",id)
                .addParams("remark", remark)
                .url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (checkResonse(response)) {
                        Response response1 = GsonUtil.changeGsonToBean(response, Response.class);
                        showToast("操作成功");
                        finish();
                    }
                } catch (Exception e) {
                    showToast("数据异常");
                    e.printStackTrace();
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissProgress();
            }
        });
    }


    public static void startAction(Context context, PersonDetail result, String id) {
        Intent intent = new Intent(context, AddEmployeeActivity.class);
        intent.putExtra(PersonDetail.class.getSimpleName(), result);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }
}
