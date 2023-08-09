package com.hdfex.merchantqrshow.comm.taobao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageResult;
import com.hdfex.merchantqrshow.bean.salesman.taobao.AliPayUserAuth;
import com.hdfex.merchantqrshow.bean.salesman.taobao.RedPackageAccountInfo;
import com.hdfex.merchantqrshow.bean.salesman.taobao.WithdrawDeposit;
import com.hdfex.merchantqrshow.mvp.contract.TaobaoAuthContract;
import com.hdfex.merchantqrshow.mvp.dagger.Component.DaggerTaobaoAuthComponent;
import com.hdfex.merchantqrshow.mvp.dagger.Component.TaobaoAuthComponent;
import com.hdfex.merchantqrshow.mvp.dagger.module.TaobaoAuthModule;
import com.hdfex.merchantqrshow.salesman.my.adapter.RedPackageAdapter;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.GlideCircleTransform;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.Utils;
import com.hdfex.merchantqrshow.view.xlistView.XListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * https://doc.open.alipay.com/doc2/detail.htm?treeId=54&articleId=104509&docType=1
 * 淘宝授权界面
 * author Created by harrishuang on 2017/6/26.
 * email : huangjinping@hdfex.com
 */

public class TaobaoAuthActivity extends BaseActivity implements TaobaoAuthContract.View, View.OnClickListener {

    @Inject
    TaobaoAuthContract.Presenter presenter;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private XListView rec_record;
    private TextView txt_balamt;
    private TextView txt_withdrawals;
    private List<RedPackage> dataList;
    private RedPackageAdapter adapter;
    private User user;
    private LinearLayout layout_account;
    private ImageView img_taobao_icon;
    private TextView txt_taobao_nicename;
    private TextView txt_taobao_account;
    private LinearLayout layout_band_alipay_flag_yes;
    private TextView txt_band_alipay_flag_no;
    private TextView tv_home;
    private RedPackageAccountInfo redPackageAccountInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ActionSheetStyle);
        setContentView(R.layout.activity_taobaoauth);
        initView();
        TaobaoAuthComponent build = DaggerTaobaoAuthComponent.builder().taobaoAuthModule(new TaobaoAuthModule()).build();
        build.inject(this);
        presenter.attachView(this);
        user = UserManager.getUser(this);
        presenter.setUser(user);
        dataList = new ArrayList<>();
        adapter = new RedPackageAdapter(dataList, this);
        rec_record.setAdapter(adapter);
        Intent intent = getIntent();

        if (intent.getSerializableExtra(RedPackageAccountInfo.class.getSimpleName()) != null) {
            redPackageAccountInfo = (RedPackageAccountInfo) intent.getSerializableExtra(RedPackageAccountInfo.class.getSimpleName());
            setViewByData(redPackageAccountInfo);
        } else {
            presenter.loadRedPackage(user, this);

        }
        presenter.loadRedPackageList(user, this, LoadMode.NOMAL);
        rec_record.setPullLoadEnable(false);
        rec_record.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                presenter.loadRedPackageList(user, TaobaoAuthActivity.this, LoadMode.PULL_REFRSH);
            }

            @Override
            public void onLoadMore() {
                presenter.loadRedPackageList(user, TaobaoAuthActivity.this, LoadMode.UP_REFRESH);
            }
        });
    }

    /**
     * 设置属性
     */
    private void setViewByData(RedPackageAccountInfo result) {
//        dataList.clear();
//        if (result.getRedPacketList() != null) {
//            dataList.addAll(result.getRedPacketList());
//        }
//        adapter.notifyDataSetChanged();
        if (!TextUtils.isEmpty(result.getBalAmt())) {
            txt_balamt.setText(result.getBalAmt());
        }
        if (RedPackageResult.BAND_ALIPAY_FLAG_YES.equals(result.getBand_alipay_flag())) {
            txt_band_alipay_flag_no.setVisibility(View.GONE);
            layout_band_alipay_flag_yes.setVisibility(View.VISIBLE);
            tv_home.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(result.getNickName())) {
                txt_taobao_nicename.setText(result.getNickName());
            }
            if (!TextUtils.isEmpty(result.getAvatar())) {
                Glide.with(this).load(result.getAvatar()).transform(new GlideCircleTransform(this)).into(img_taobao_icon);
            }
        } else {
            txt_band_alipay_flag_no.setVisibility(View.VISIBLE);
            layout_band_alipay_flag_yes.setVisibility(View.GONE);
            tv_home.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        rec_record = (XListView) findViewById(R.id.rec_record);
        txt_balamt = (TextView) findViewById(R.id.txt_balamt);
        txt_balamt.setOnClickListener(this);
        txt_withdrawals = (TextView) findViewById(R.id.txt_withdrawals);
        txt_withdrawals.setOnClickListener(this);
        tb_tv_titile.setText("红包");
        layout_account = (LinearLayout) findViewById(R.id.layout_account);
        layout_account.setOnClickListener(this);
        img_taobao_icon = (ImageView) findViewById(R.id.img_taobao_icon);
        img_taobao_icon.setOnClickListener(this);
        txt_taobao_nicename = (TextView) findViewById(R.id.txt_taobao_nicename);
        txt_taobao_nicename.setOnClickListener(this);
        txt_taobao_account = (TextView) findViewById(R.id.txt_taobao_account);
        txt_taobao_account.setOnClickListener(this);
        layout_band_alipay_flag_yes = (LinearLayout) findViewById(R.id.layout_band_alipay_flag_yes);
        layout_band_alipay_flag_yes.setOnClickListener(this);
        txt_band_alipay_flag_no = (TextView) findViewById(R.id.txt_band_alipay_flag_no);
        txt_band_alipay_flag_no.setOnClickListener(this);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);
        tv_home.setTextSize(Utils.sp2px(this, 15));

        tv_home.setText("···");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_band_alipay_flag_no:
                presenter.toTaobaoAuth(this);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_withdrawals:
                if (redPackageAccountInfo != null) {
                    if (RedPackageAccountInfo.BAND_ALIPAY_FLAG_YES.equals(redPackageAccountInfo.getBand_alipay_flag())) {


//                        BigDecimal bigDecimal=new BigDecimal(redPackageAccountInfo.getBalAmt());
//                        if (bigDecimal.compareTo(BigDecimal.ZERO) <= 0) {
//                            showToast("");
//                            return;
//                        }
                        showAlertDialog();
                    } else {
                        presenter.toTaobaoAuth(this);
                    }
                }
                break;
            case R.id.tv_home:
                presenter.showActionSheet(this, user, getSupportFragmentManager());
                break;
        }
    }

    /**
     * 显示问题
     */
    public void showAlertDialog() {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("确认提现！！")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(this.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
                .withButton1Text("取消")
                .withButton2Text("提现")
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        presenter.getWithdrawDeposit(user, redPackageAccountInfo.getBalAmt(), TaobaoAuthActivity.this);

                    }
                })
                .show();
    }


    /**
     * 打开页面
     *
     * @param context
     */
    public static void startAction(Context context, RedPackageAccountInfo result) {
        Intent intent = new Intent(context, TaobaoAuthActivity.class);
        intent.putExtra(RedPackageAccountInfo.class.getSimpleName(), result);
        context.startActivity(intent);
    }


    /**
     * 打开页面
     *
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, TaobaoAuthActivity.class);
        context.startActivity(intent);
    }

    /**
     * 打开页面
     *
     * @param context
     */
    public static void startAction(Context context, RedPackageResult result) {
        Intent intent = new Intent(context, TaobaoAuthActivity.class);
        intent.putExtra(RedPackageResult.class.getSimpleName(), result);
        context.startActivity(intent);
    }

    @Override
    public void returnAliPayUserAuth(AliPayUserAuth result) {
        txt_band_alipay_flag_no.setVisibility(View.GONE);
        layout_band_alipay_flag_yes.setVisibility(View.VISIBLE);
        tv_home.setVisibility(View.VISIBLE);
        txt_withdrawals.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(result.getNick_name())) {
            txt_taobao_nicename.setText(result.getNick_name());
        }
        if (!TextUtils.isEmpty(result.getAvatar())) {
            Glide.with(this).load(result.getAvatar()).transform(new GlideCircleTransform(this)).into(img_taobao_icon);
        }
        if (redPackageAccountInfo != null) {
            redPackageAccountInfo.setBand_alipay_flag(RedPackageAccountInfo.BAND_ALIPAY_FLAG_YES);
        }
        showAlertDialog();
    }

    @Override
    public void returnWithdrawDeposit(WithdrawDeposit result) {
        if (result != null) {
            showToast(result.getTransferDesc());
        }
    }

    @Override
    public void returnRedPackageResult(RedPackageResult result, LoadMode loadMode) {
        if (result.getRedPacketList() != null) {
            if (loadMode != LoadMode.UP_REFRESH) {
                dataList.clear();
            }
            dataList.addAll(result.getRedPacketList());
        }
        if (dataList.size() > 14) {
            rec_record.setPullLoadEnable(true);
        } else {
            rec_record.setPullLoadEnable(false);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void returnRedPackage(RedPackageAccountInfo result) {
        redPackageAccountInfo = result;
        setViewByData(result);
    }

    @Override
    public void loadAfter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    rec_record.stopRefresh();
                    rec_record.stopLoadMore();
                    rec_record.setRefreshTime("刚刚");
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }


}
