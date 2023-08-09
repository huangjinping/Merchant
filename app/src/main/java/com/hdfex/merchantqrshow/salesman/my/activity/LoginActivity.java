package com.hdfex.merchantqrshow.salesman.my.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.app.App;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.login.LoginResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.presenter.LoginPresenter;
import com.hdfex.merchantqrshow.mvp.view.LoginView;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.ACache;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.RoleUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.HDAlertDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;


/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {
    private Button btn_login;
    private EditText edt_username;
    private EditText edt_password;
    private ImageView img_login_phone;
    private FrameLayout fl_name;
    private ImageView img_login_password;
    private ImageView tv_logins_show;
    private LinearLayout hd_ll_logo;
    private LinearLayout layout_down;
    private ImageView img_cancel;
    private TextView txt_forgot_password;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initStatusBarColor(R.color.bg_gray_light2);
        presenter = new LoginPresenter();
        presenter.attachView(this);
        initView();
        addTextWatcher(edt_username);
        addTextWatcher(edt_password);
        try {

            User user = UserManager.getUser(this);
            if (user != null && !TextUtils.isEmpty(user.getPhone())) {
                edt_username.setText(user.getPhone());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        img_login_phone = (ImageView) findViewById(R.id.img_login_phone);
        fl_name = (FrameLayout) findViewById(R.id.fl_name);
        img_login_password = (ImageView) findViewById(R.id.img_login_password);
        tv_logins_show = (ImageView) findViewById(R.id.tv_logins_show);
        hd_ll_logo = (LinearLayout) findViewById(R.id.hd_ll_logo);
        layout_down = (LinearLayout) findViewById(R.id.layout_down);
        hd_ll_logo.setOnClickListener(this);
        layout_down.setOnClickListener(this);
        img_cancel = (ImageView) findViewById(R.id.img_cancel);
        img_cancel.setOnClickListener(this);
        edt_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    submit();
                    return true;
                }
                return false;
            }
        });
        txt_forgot_password = (TextView) findViewById(R.id.txt_forgot_password);
        txt_forgot_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
//                Intent mIntent = new Intent(this, UploadPhotoActivity.class);
//                startActivity(mIntent);
                submit();
                break;
            case R.id.img_cancel:
                finish();
                break;
            case R.id.hd_ll_logo:
            case R.id.layout_down:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                break;
            case R.id.txt_forgot_password:
                showAlertDialog();
//                RedPackageDialog dialog=new RedPackageDialog(this);
//                dialog.show();

                break;
        }
    }

    /**
     * 提交接口
     */
    private void submit() {
        if (!connect()) {
            return;
        }
        String username = edt_username.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            showToast("请输入账号");
            return;
        }
        String password = edt_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码");
            return;
        }
        UserManager.clearApartment(this);
        presenter.login(username, password);
    }

    /**
     * 完成接口
     */
    private void showAlertDialog() {
        final HDAlertDialog dialog1 = HDAlertDialog.getInstance(this)
                .withTitle("提示")
                .withMsg("如果您忘记了登录密码，请与贵公司管理员进行联系，感谢您的配合！");
        dialog1.withButton1Onclick("我知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }


    /**
     * 文字监听器
     *
     * @param edit
     */
    private void addTextWatcher(final EditText edit) {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_password == edit) {
                    if (TextUtils.isEmpty(s.toString())) {
                        img_login_password.setImageResource(R.mipmap.ic_login_password_off);
                    } else {
                        img_login_password.setImageResource(R.mipmap.ic_login_password);
                    }
                }
                if (edt_username == edit) {
                    if (TextUtils.isEmpty(s.toString())) {
                        img_login_phone.setImageResource(R.mipmap.ic_login_phone_off);
                    } else {
                        img_login_phone.setImageResource(R.mipmap.ic_login_phone);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void login(String response, String username) {
        try {
            boolean b = checkResonse(response);
            if (b) {
                dismissProgress();
                EventRxBus.getInstance().post(IntentFlag.LOGOUT, IntentFlag.LOGOUT);
                ACache.get(App.getAppContext()).remove(IntentFlag.SEARCH_NAME);
                LoginResponse mUserInfo = GsonUtil.changeGsonToBean(response, LoginResponse.class);
                User user = mUserInfo.getResult();

                PushAgent.getInstance(LoginActivity.this).addAlias(user.getId(), "hudong", new UTrack.ICallBack() {

                    @Override
                    public void onMessage(boolean b, String s) {

                    }
                });
                UserManager.setFollow(this, true);
                user.setCurrentBussinessId(user.getBussinessId());
                user.setCurrentBussinessName(user.getBussinessName());
                user.setAccount(username);
                UserManager.saveUser(LoginActivity.this, user);

                MobclickAgent.onProfileSignIn(mUserInfo.getResult().getId());
                RoleUtils.startAction(LoginActivity.this, mUserInfo.getResult(), RoleUtils.ROLE_FLAG_LOGIN);
            }
        } catch (Exception e) {
            showWebEirr();
        }
    }
}
