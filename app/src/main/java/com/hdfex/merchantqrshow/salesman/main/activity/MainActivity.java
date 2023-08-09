package com.hdfex.merchantqrshow.salesman.main.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.salesman.add.activity.CommodityListActivity;
import com.hdfex.merchantqrshow.salesman.add.activity.CreateCommodityActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.AccountManagerActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.LoadAppForCActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.LoginActivity;
import com.hdfex.merchantqrshow.salesman.order.activity.OrderListManagerActivity;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.update.UpdataUtil;

/**
 * 没用的首页
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView tb_tv_titile;
    private LinearLayout layout_toolbar;
    private ImageView iv_show;
    private ImageView img_back;
    private RelativeLayout layout_load_app;
    private RelativeLayout layout_order_add;
    private RelativeLayout layout_order_manager;
    private DisplayMetrics displayMetrics;
    private ImageView iv_setting;
    private long firstClick = 0;
    private TextView tv_home;
    private RelativeLayout layout_create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        initView();

        //检测版本和更新
        String upateUrl = NetConst.BASEURL + getString(R.string.upateUrl);
        UpdataUtil mUpdataUtil = new UpdataUtil(MainActivity.this, upateUrl);
        mUpdataUtil.getVersion();

        img_back.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_show.getLayoutParams();
        params.height = (displayMetrics.widthPixels * 600) / 1125;
        iv_show.setLayoutParams(params);
        iv_show.invalidate();

        try {
            initDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        Log.d("hjp", UserManager.getUser().toString());
    }

    private void initDate() {
        tb_tv_titile.setText("首页");
        try {
            User user = UserManager.getUser(this);
            Boolean createCommFlag = user.getCreateCommFlag();
            Drawable d = getResources().getDrawable(R.mipmap.ic_banner);
            RequestManager with = Glide.with(this);
            with.load(user.getBannerUrl())
                    .error(d)
                    .placeholder(d)
                    .into(iv_show);
            if (createCommFlag) {
                layout_create.setVisibility(View.VISIBLE);
            } else {
                layout_create.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化数据库
     */
    private void initDataBase() {

        initDate();

    }

    private void initView() {
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        iv_show = (ImageView) findViewById(R.id.iv_show);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        layout_load_app = (RelativeLayout) findViewById(R.id.layout_load_app);
        layout_load_app.setOnClickListener(this);
        layout_order_add = (RelativeLayout) findViewById(R.id.layout_order_add);
        layout_order_add.setOnClickListener(this);
        layout_order_manager = (RelativeLayout) findViewById(R.id.layout_order_manager);
        layout_order_manager.setOnClickListener(this);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_setting.setVisibility(View.VISIBLE);
        iv_setting.setOnClickListener(this);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);
        layout_create = (RelativeLayout) findViewById(R.id.layout_create);
        layout_create.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent mIntent;
        if (!UserManager.isLogin(this)) {
            mIntent = new Intent(this, LoginActivity.class);
            startActivity(mIntent);
            return;
        }
        switch (v.getId()) {
            case R.id.layout_order_add:
                mIntent = new Intent(this, CommodityListActivity.class);
                startActivity(mIntent);
                break;
            case R.id.layout_order_manager:
                mIntent = new Intent(this, OrderListManagerActivity.class);
                startActivity(mIntent);
                break;
            case R.id.layout_load_app:
                mIntent = new Intent(this, LoadAppForCActivity.class);
                startActivity(mIntent);
                overridePendingTransition(R.anim.activity_down_in_slow, R.anim.activity_down_out_slow);

                break;
            case R.id.iv_setting:
                mIntent = new Intent(this, AccountManagerActivity.class);
                startActivity(mIntent);
                break;
            case R.id.layout_create:
                mIntent = new Intent(this, CreateCommodityActivity.class);
                UserManager.clearUpLoad(this);
                startActivity(mIntent);
                break;
        }
    }


}
