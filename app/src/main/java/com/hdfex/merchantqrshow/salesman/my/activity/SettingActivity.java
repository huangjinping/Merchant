package com.hdfex.merchantqrshow.salesman.my.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.activity.WebActivity;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.mvp.contract.SettingContract;
import com.hdfex.merchantqrshow.mvp.presenter.SettingPresenter;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;

/**
 * Created by harrishuang on 16/10/18.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener, SettingContract.View {

    private ImageView img_back;
    private TextView txt_left_name;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private TextView tv_version_name;
    private TextView btn_quit;
    private LinearLayout layout_load_app;
    private LinearLayout layout_weixin;
    private SettingContract.Presenter presenter;
    private TextView tv_we_push;
    private LinearLayout ll_we_push;
    private TextView txt_push_setting;
    private LinearLayout layout_permission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        presenter = new SettingPresenter();
        presenter.attachView(this);
        initView();
        tv_version_name.setText(getVersion());
        tb_tv_titile.setText("设置");
        if (!UserManager.isLogin(this)) {
            btn_quit.setVisibility(View.GONE);
        }
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        img_back.setOnClickListener(this);
        btn_quit = (TextView) findViewById(R.id.btn_quit);
        btn_quit.setOnClickListener(this);
        layout_load_app = (LinearLayout) findViewById(R.id.layout_load_app);
        layout_load_app.setOnClickListener(this);
        layout_weixin = (LinearLayout) findViewById(R.id.layout_weixin);
        layout_weixin.setOnClickListener(this);
        tv_we_push = (TextView) findViewById(R.id.tv_we_push);
        tv_we_push.setOnClickListener(this);
        ll_we_push = (LinearLayout) findViewById(R.id.ll_we_push);
        ll_we_push.setOnClickListener(this);
        txt_push_setting = (TextView) findViewById(R.id.txt_push_setting);
        txt_push_setting.setOnClickListener(this);
        layout_permission = (LinearLayout) findViewById(R.id.layout_permission);
        layout_permission.setOnClickListener(this);
    }

    /**
     * 获取版本信息
     *
     * @return
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "最新版本";
        }
    }

    @Override
    public void onClick(View v) {
        Intent mIntent;

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_quit:
                if (UserManager.isLogin(this)) {
                    UserManager.logout(this);
                    ToastUtils.makeText(this, "退出成功").show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                finish();
                break;
            case R.id.layout_load_app:
                mIntent = new Intent(this, LoadAppForCActivity.class);
                startActivity(mIntent);
                overridePendingTransition(R.anim.activity_up_in_slow, R.anim.activity_up_out_slow);
                break;
            case R.id.layout_weixin:
                mIntent = new Intent(this, WeixinActivity.class);
                startActivity(mIntent);
                overridePendingTransition(R.anim.activity_up_in_slow, R.anim.activity_up_out_slow);
                break;
            case R.id.txt_push_setting:
                presenter.pushSetting(this, tv_we_push);

                break;
            case R.id.layout_permission:

                WebActivity.start(this, getString(R.string.app_name), NetConst.PERMISSION_URL);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
