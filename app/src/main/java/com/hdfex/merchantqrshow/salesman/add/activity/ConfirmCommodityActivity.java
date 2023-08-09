package com.hdfex.merchantqrshow.salesman.add.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.bam.activity.AdminIndexActivity;
import com.hdfex.merchantqrshow.apliySale.main.activity.ApliyMainActivity;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Details;
import com.hdfex.merchantqrshow.bean.salesman.huabei.AlipayOrder;
import com.hdfex.merchantqrshow.bean.salesman.huabei.HuabeiRequest;
import com.hdfex.merchantqrshow.bean.salesman.huabei.PayInfoItem;
import com.hdfex.merchantqrshow.bean.salesman.huabei.PayOrderQueryResult;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.manager.main.activity.ManagerIndexActivity;
import com.hdfex.merchantqrshow.mvp.contract.ConfirmCommodityContract;
import com.hdfex.merchantqrshow.mvp.presenter.ConfirmCommodityPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.add.fragment.InputHuabeiFragment;
import com.hdfex.merchantqrshow.salesman.add.fragment.InputTaobaoFragment;
import com.hdfex.merchantqrshow.salesman.main.activity.IndexActivity;
import com.hdfex.merchantqrshow.utils.DeviceUtils;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.MathUtils;
import com.hdfex.merchantqrshow.utils.RegexUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.Utils;
import com.hdfex.merchantqrshow.utils.camera.compress.Util;
import com.hdfex.merchantqrshow.utils.dexter.OnPermissionListener;
import com.hdfex.merchantqrshow.utils.dexter.SampleMultipleBackgroundPermissionListener;
import com.hdfex.merchantqrshow.widget.DeleteEditText;
import com.hdfex.merchantqrshow.widget.HuabeiDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * 花呗页面
 * author Created by harrishuang on 2017/6/16.
 * email : huangjinping@hdfex.com
 */

public class ConfirmCommodityActivity extends BaseActivity implements View.OnClickListener, ConfirmCommodityContract.View, View.OnFocusChangeListener, InputHuabeiFragment.InputHuabeiCallback, InputTaobaoFragment.InputTaobaoCallback {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView img_scan;
    private DeleteEditText edt_custName;
    private DeleteEditText edt_custPhoneNo;
    private DeleteEditText edt_custIdNo;
    private EditText edt_commodityBarCode;
    private ConfirmCommodityContract.Presenter presenter;
    private EditText edt_procedures;
    private RelativeLayout layout_procedures;
    private User user;
    private Order order;
    private DeleteEditText edt_amount;
    private String duration;
    private String caseId;
    private String commodityId;
    private String orderId;
    private List<Installment> projectList;
    private int index = 0;
    private Installment currentInstallment;
    private EditText edt_commodity_name;
    private TextView tv_home;
    private RelativeLayout layout_huabei;
    private RelativeLayout layout_taobao;
    private LinearLayout layout_pay_huabei;
    private LinearLayout layout_pay_taobao;
    private TextView btn_submit;
    /**
     * 花呗支付
     */
    public final String PAY_FLAG_HUABEI = "00";
    /**
     * 支付宝支付
     */
    public final String PAY_FLAG_TAOBAO = "01";


    private TextView txt_huabei_payDate;
    private TextView txt_huabei_payStatus;
    private TextView txt_taobao_payDate;
    private TextView txt_taobao_payStatus;
    private RelativeLayout fl_fragment;
    private TextView txt_huabei_payAmount;
    private TextView txt_taobao_payAmount;
    private TextView txt_taobao_payChannel;
    private HuabeiRequest huabeiRequest;
    private TextView txt_select_commodity;
    private FrameLayout layout_custIdNo;
    private TextView txt_extends;

    public enum CreateOrder {
        UNCREATE, CREATEING, COMPLTE
    }

    CreateOrder createOrder;


    public static void startAction(Context context, HuabeiRequest huabeiRequest, PayOrderQueryResult payOrderQuery) {
        Intent intent = new Intent(context, ConfirmCommodityActivity.class);
        intent.putExtra(IntentFlag.SUBMIT_HUABEI_REQUEST, huabeiRequest);
        intent.putExtra(IntentFlag.PAY_ORDER_QUERY, payOrderQuery);
        context.startActivity(intent);
    }


    public static void startAction(Context context, Order order, PayOrderQueryResult payOrderQuery) {
        Intent intent = new Intent(context, ConfirmCommodityActivity.class);
        intent.putExtra(IntentFlag.SUBMIT_ORDER, order);
        intent.putExtra(IntentFlag.PAY_ORDER_QUERY, payOrderQuery);
        context.startActivity(intent);
    }

    public static void startAction(Context context, AlipayOrder alipayOrder, PayOrderQueryResult payOrderQuery) {
        Intent intent = new Intent(context, ConfirmCommodityActivity.class);
        intent.putExtra(IntentFlag.HUABEI_DETAILS, alipayOrder);
        intent.putExtra(IntentFlag.PAY_ORDER_QUERY, payOrderQuery);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ActionSheetStyle);
        setContentView(R.layout.activity_confirm_commodity);
        presenter = new ConfirmCommodityPresenter();
        presenter.attachView(this);
        user = UserManager.getUser(this);
        initView();
//        initPopupView();
        Intent intent = getIntent();
        parseConfirm(intent);
        parseOrder(intent);
        parseHuabeiRequest(intent);
        parsePayOrderQuery(intent);

        EventRxBus.getInstance().register(ConfirmCommodityActivity.class.getSimpleName()).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {
                if (o != null) {
                    Log.d("okhttp", o.toString());
                    if (TextUtils.isEmpty(o.toString())) {
                        showToast("请扫描条形码");
                        return;
                    }
                    edt_commodityBarCode.setText(o.toString());
                }
            }
        });

        EventRxBus.getInstance().register(IntentFlag.ORDER_ID).subscribe(new Subscriber<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

                orderId = (String) o;
            }
        });

        EventRxBus.getInstance().register(IntentFlag.HUABEI_SELECT_COMMITY).subscribe(new Subscriber<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Details details = (Details) o;
                edt_amount.setText(details.getCommodityPrice());
                edt_commodity_name.setText(details.getCommodityName());

            }
        });

        EventRxBus.getInstance().register(IntentFlag.HUABEI_SCAN).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                if (o != null) {
                    submit(o.toString(), PAY_FLAG_HUABEI, loanAmount);
                }
            }
        });


        EventRxBus.getInstance().register(IntentFlag.TAOBAO_SCAN).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                if (o != null) {

                    submit(o.toString(), PAY_FLAG_TAOBAO, currentTaobaoAccount);

                }
            }
        });
    }

    /**
     * 花呗提交数据
     *
     * @param intent
     */
    private void parseHuabeiRequest(Intent intent) {
        if (intent.getSerializableExtra(IntentFlag.SUBMIT_HUABEI_REQUEST) != null) {
            HuabeiRequest request = (HuabeiRequest) intent.getSerializableExtra(IntentFlag.SUBMIT_HUABEI_REQUEST);
            projectList = request.getCommodityCaseList();
            this.huabeiRequest = request;
            order = new Order();
            if (projectList.size() != 0) {
                updateView(projectList.size() - 1);
            }
            edt_commodity_name.setEnabled(true);
            txt_select_commodity.setVisibility(View.VISIBLE);
            order.setOrderId(request.getOrderId());
            order.setList(projectList);
            orderId = request.getOrderId();
        }
    }


    /**
     * 解析数据查询
     *
     * @param intent
     */
    private void parsePayOrderQuery(Intent intent) {
        if (intent.getSerializableExtra(IntentFlag.PAY_ORDER_QUERY) != null) {
            PayOrderQueryResult payOrderQuery = (PayOrderQueryResult) intent.getSerializableExtra(IntentFlag.PAY_ORDER_QUERY);
            returnPayOrderQuery(payOrderQuery, presenter.ACTION_QUERY_SHOW);
        }
    }

    /**
     * 解析数据问题
     *
     * @param intent
     */
    private void parseOrder(Intent intent) {
        if (intent.getSerializableExtra(IntentFlag.HUABEI_DETAILS) != null) {
            AlipayOrder alipayOrder = (AlipayOrder) intent.getSerializableExtra(IntentFlag.HUABEI_DETAILS);
            projectList = alipayOrder.getCommodityCaseList();
            if (projectList.size() != 0) {
                updateView(projectList.size() - 1);
            }

            order = new Order();
            order.setList(projectList);
            edt_amount.setEnabled(false);
            edt_commodity_name.setEnabled(false);
            edt_custName.setEnabled(false);
            edt_custPhoneNo.setEnabled(false);
            edt_custIdNo.setEnabled(false);
            edt_commodityBarCode.setEnabled(false);
            img_scan.setEnabled(false);



            caseId = alipayOrder.getCaseId();
            duration = alipayOrder.getDuration();
            commodityId = alipayOrder.getCommodityId();
            orderId = alipayOrder.getOrderNo();
            order.setOrderId(orderId);
        }


    }

    @Override
    public void returnPayOrderQuery(PayOrderQueryResult result, String type) {
        layout_pay_taobao.setVisibility(View.GONE);
        layout_pay_huabei.setVisibility(View.GONE);
        if (result != null) {
            List<PayInfoItem> payInfoList = result.getPayInfoList();
            if (payInfoList != null) {
                btn_submit.setVisibility(View.GONE);
                createOrder = CreateOrder.COMPLTE;
                if (payInfoList.size() == 1) {
                    btn_submit.setVisibility(View.VISIBLE);
                    createOrder = CreateOrder.CREATEING;
                }
                for (PayInfoItem info : payInfoList) {
                    if (PayInfoItem.PAYWAY_HUABEI.equals(info.getPayWay())) {
                        layout_pay_huabei.setVisibility(View.VISIBLE);
                        txt_huabei_payDate.setText(info.getPayDate());
                        txt_huabei_payStatus.setText(info.getPayStatusName());
                        txt_huabei_payAmount.setText(info.getPayAmount());

                        if (PayInfoItem.PAYSTATUS_YES.equals(info.getPayStatus())) {
                            try {
                                layout_taobao.setVisibility(View.VISIBLE);
                                currentTaobaoAccount = MathUtils.sub(edt_amount.getText().toString(), currentApplyAmount);
                                getSupportFragmentManager().popBackStack();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            layout_huabei.setEnabled(false);
                        } else {
                            layout_huabei.setEnabled(true);
                            createOrder = CreateOrder.CREATEING;
                            if (presenter.ACTION_SUBMIT_HUABEI.equals(type)) {
                                if (!TextUtils.isEmpty(info.getFailReason())) {
                                    showToast(info.getFailReason());
                                }
                            }

                            txt_huabei_payStatus.setTextColor(getResources().getColor(R.color.red_light));
                            btn_submit.setVisibility(View.VISIBLE);
                        }
                    } else if (PayInfoItem.PAYWAY_APLIY.equals(info.getPayWay())) {
                        layout_pay_taobao.setVisibility(View.VISIBLE);
                        txt_taobao_payDate.setText(info.getPayDate());
                        txt_taobao_payStatus.setText(info.getPayStatusName());
                        txt_taobao_payAmount.setText(info.getPayAmount());
                        if (!TextUtils.isEmpty(info.getPayChannel())) {
                            txt_taobao_payChannel.setText(info.getPayChannel());
                        }

                        if (PayInfoItem.PAYSTATUS_YES.equals(info.getPayStatus())) {
                            try {
                                getSupportFragmentManager().popBackStack();
//                                layout_taobao.setEnabled(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            layout_taobao.setEnabled(false);
                        } else {
                            layout_taobao.setEnabled(true);
                            createOrder = CreateOrder.CREATEING;
                            btn_submit.setVisibility(View.VISIBLE);
                            txt_taobao_payStatus.setTextColor(getResources().getColor(R.color.red_light));
                            if (presenter.ACTION_SUBMIT_TAOBAO.equals(type)) {
                                if (!TextUtils.isEmpty(info.getFailReason())) {
                                    showToast(info.getFailReason());
                                }

                            }
                        }
                    }
                }
                btn_submit.setVisibility(View.GONE);

                for (PayInfoItem info : payInfoList) {
                    if (PayInfoItem.PAYSTATUS_YES.equals(info.getPayStatus())) {
                        btn_submit.setVisibility(View.VISIBLE);
                    }
                }

            }
            /**
             * 允许使用支付宝
             */
            if (PayOrderQueryResult.PAY_FLAG_YES.equals(result.getPayFlag())) {
                layout_taobao.setVisibility(View.VISIBLE);
            } else {
                layout_taobao.setVisibility(View.GONE);
            }


        }
        order.setTotalPrice(result.getTotalAmount());


        if (!TextUtils.isEmpty(result.getTotalAmount())) {
            edt_amount.setText(result.getTotalAmount() + "");
        }
        if (!TextUtils.isEmpty(result.getCommodityName())) {
            edt_commodity_name.setText(result.getCommodityName());
        }
        if (!TextUtils.isEmpty(result.getCustName())) {
            edt_custName.setText(result.getCustName());
        }

        if (!TextUtils.isEmpty(result.getCustPhone())) {
            edt_custPhoneNo.setText(result.getCustPhone());
        }
        if (!TextUtils.isEmpty(result.getCustIdNo())) {
            edt_custIdNo.setText(result.getCustIdNo());
        }
        if (!TextUtils.isEmpty(result.getCommodityBarCode())) {
            edt_commodityBarCode.setText(result.getCommodityBarCode());
        }


        edt_amount.clearDrawable();
        edt_custName.clearDrawable();
        edt_custPhoneNo.clearDrawable();
        edt_custIdNo.clearDrawable();
        edt_custPhoneNo.clearDrawable();
    }

    /**
     * 解析数据
     *
     * @param intent
     */

    private void parseConfirm(Intent intent) {
        if (intent.getSerializableExtra(IntentFlag.SUBMIT_ORDER) != null) {
            order = (Order) intent.getSerializableExtra(IntentFlag.SUBMIT_ORDER);
            projectList = order.getList();
            if (projectList.size() != 0) {
                updateView(projectList.size() - 1);
            }
            edt_amount.setText(order.getTotalPrice() + "");
            edt_commodity_name.setText(order.getCommoditysList().get(0).getCommodityName());
            commodityId = order.getCommoditysList().get(0).getCommodityId();
            orderId = order.getOrderId();
        }
    }


    /**
     * @param position
     */
    private void updateView(int position) {
        index = position;
        currentInstallment = projectList.get(position);
        duration = projectList.get(index).getDuration();
        caseId = projectList.get(index).getCaseId();
        if (!TextUtils.isEmpty(currentInstallment.getProfitSource())) {
            edt_procedures.setText(currentInstallment.getProfitSource());
        }
    }


    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        img_scan = (ImageView) findViewById(R.id.img_scan);
        tb_tv_titile.setText("在线支付");
        img_scan.setOnClickListener(this);
        img_back.setOnClickListener(this);


        edt_custName = (DeleteEditText) findViewById(R.id.edt_custName);
        edt_custPhoneNo = (DeleteEditText) findViewById(R.id.edt_custPhoneNo);
        edt_custIdNo = (DeleteEditText) findViewById(R.id.edt_custIdNo);
        edt_commodityBarCode = (EditText) findViewById(R.id.edt_commodityBarCode);
        edt_procedures = (EditText) findViewById(R.id.edt_procedures);
        layout_procedures = (RelativeLayout) findViewById(R.id.layout_procedures);
        layout_procedures.setOnClickListener(this);
        edt_amount = (DeleteEditText) findViewById(R.id.edt_amount);
        edt_commodity_name = (EditText) findViewById(R.id.edt_commodity_name);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setText("查看花呗额度");
        tv_home.setVisibility(View.VISIBLE);
        tv_home.setOnClickListener(this);
        layout_huabei = (RelativeLayout) findViewById(R.id.layout_huabei);
        layout_huabei.setOnClickListener(this);
        layout_taobao = (RelativeLayout) findViewById(R.id.layout_taobao);
        layout_taobao.setOnClickListener(this);
        layout_pay_huabei = (LinearLayout) findViewById(R.id.layout_pay_huabei);
        layout_pay_huabei.setOnClickListener(this);
        layout_pay_taobao = (LinearLayout) findViewById(R.id.layout_pay_taobao);
        layout_pay_taobao.setOnClickListener(this);
        btn_submit = (TextView) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        txt_huabei_payDate = (TextView) findViewById(R.id.txt_huabei_payDate);
        txt_huabei_payDate.setOnClickListener(this);
        txt_huabei_payStatus = (TextView) findViewById(R.id.txt_huabei_payStatus);
        txt_huabei_payStatus.setOnClickListener(this);
        txt_taobao_payDate = (TextView) findViewById(R.id.txt_taobao_payDate);
        txt_taobao_payDate.setOnClickListener(this);
        txt_taobao_payStatus = (TextView) findViewById(R.id.txt_taobao_payStatus);
        txt_taobao_payStatus.setOnClickListener(this);
        fl_fragment = (RelativeLayout) findViewById(R.id.fl_fragment);
        txt_huabei_payAmount = (TextView) findViewById(R.id.txt_huabei_payAmount);
        txt_huabei_payAmount.setOnClickListener(this);
        txt_taobao_payAmount = (TextView) findViewById(R.id.txt_taobao_payAmount);
        txt_taobao_payAmount.setOnClickListener(this);
        txt_taobao_payChannel = (TextView) findViewById(R.id.txt_taobao_payChannel);
        txt_select_commodity = (TextView) findViewById(R.id.txt_select_commodity);
        txt_select_commodity.setOnClickListener(this);
        layout_custIdNo = (FrameLayout) findViewById(R.id.layout_custIdNo);
        txt_extends = (TextView) findViewById(R.id.txt_extends);
        txt_extends.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_back:
                finish();
                break;
            case R.id.img_scan:
                CaptureActivity.startAction(this, ConfirmCommodityActivity.class.getSimpleName());
                break;
            case R.id.layout_procedures:
                presenter.selectProcedures(this, getSupportFragmentManager());
                break;

            case R.id.tv_home:
                HuabeiQuotaActivity.startAction(this);
                break;

            case R.id.layout_huabei:
                inputAccount(PAY_FLAG_HUABEI);
                break;
            case R.id.layout_taobao:
                inputAccount(PAY_FLAG_TAOBAO);

                break;
            case R.id.btn_submit:
                onCompalteSubmit();
                break;
            case R.id.txt_select_commodity:
                Intent mIntent = new Intent(this, CommodityListV2Activity.class);
                startActivity(mIntent);
                break;
            case R.id.txt_extends:
                upDataCustIdNoLayout();
                break;
        }
    }

    /**
     * 提交数据
     */
    private void onCompalteSubmit() {
        boolean follow = UserManager.getFollow(this);
        if (follow){
            final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
            dialogBuilder
                    .withTitle("信息提示")
                    .withTitleColor("#FFFFFF")
                    .withMessage("关注支付宝生活号")
                    .withMessageColor("#000000")
                    .withDialogColor("#FFFFFF")
                    .withIcon(getResources().getDrawable(R.mipmap.ic_merchant_logo))
                    .isCancelableOnTouchOutside(true)
                    .withDuration(100)
                    .withButton1Text("忽略")
                    .withButton2Text("去关注")
                    .isCancelable(false)
                    .isCancelableOnTouchOutside(false)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();
                            presenter.finishOrder(ConfirmCommodityActivity.this, user, orderId);
                                UserManager.setFollow(ConfirmCommodityActivity.this,false);
                        }
                    })
                    .setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();
                            Utils.startIntentUrl(ConfirmCommodityActivity.this, "alipayqr://platformapi/startapp?appId=20000047&publicId=2017060807447836");
                            presenter.finishOrder(ConfirmCommodityActivity.this, user, orderId);
                        }
                    })
                    .show();
        }else {
            presenter.finishOrder(this, user, orderId);

        }


    }

    /**
     * 更新身份证订单
     */
    private void upDataCustIdNoLayout(){
        if (layout_custIdNo.getVisibility()==View.VISIBLE){
            layout_custIdNo.setVisibility(View.GONE);
            txt_extends.setText("完善更多信息");
        }else {
            layout_custIdNo.setVisibility(View.VISIBLE);
            txt_extends.setText("收起");
        }
    }

    /**
     * 提交选择输入信息
     */
    private void inputAccount(String payFlag) {
        String custName = edt_custName.getText().toString().trim();


        String commodityBarCode = edt_commodityBarCode.getText().toString().trim();
        if (TextUtils.isEmpty(commodityBarCode)) {
            showToast("请输入商品IMEI编码或者扫描");
            return;
        }

        String amount = edt_amount.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            showToast("请输入贷款金额");
            return;
        }

        if (TextUtils.isEmpty(custName)) {
            showToast("请输入客户的真实姓名");
            return;
        }

        String custPhoneNo = edt_custPhoneNo.getText().toString().trim();
        if (TextUtils.isEmpty(custPhoneNo)) {
            showToast("请输入客户的实名认证手机号");
            return;
        }
        if (!RegexUtils.phone(custPhoneNo)) {
            showToast("请输入正确的手机号码");
            return;
        }


        String custIdNo = edt_custIdNo.getText().toString().trim();

        if (!TextUtils.isEmpty(custIdNo)) {
            if (!RegexUtils.idcard(custIdNo)) {
                showToast("请输入正确身份证号码");
                return;
            }

        }
//        if (TextUtils.isEmpty(custIdNo)) {
//            showToast("请输入正确身份证号码");
//            return;
//        }


        if (PAY_FLAG_HUABEI.equals(payFlag)) {
            addHuabeiFragmentToStack();
        } else {

            alipayAlert();
        }
    }


    /**
     * 权限监听器
     */
    SampleMultipleBackgroundPermissionListener multiplePermissionListener = new SampleMultipleBackgroundPermissionListener(new OnPermissionListener() {
        @Override
        public void showPermissionGranted(String permission) {
            if (DeviceUtils.isPermission(ConfirmCommodityActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) && DeviceUtils.isPermission(ConfirmCommodityActivity.this, Manifest.permission.CAMERA)) {

                return;
            }
        }

        @Override
        public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {
            showPermissDeniedAlert();
        }

        @Override
        public void showPermissionRationale(PermissionToken token) {
            token.continuePermissionRequest();
        }
    });


    @Override
    protected void onResume() {
        super.onResume();
        initPermission();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.payUpDateOrderQuery(this, user, order);
    }

    /**
     * 初始化权限
     */
    private void initPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (DeviceUtils.isPermission(this, Manifest.permission.READ_PHONE_STATE)
                    ) {
                return;
            }
            if (Dexter.isRequestOngoing()) {
                return;
            }
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Dexter.checkPermissionsOnSameThread(multiplePermissionListener, Manifest.permission.READ_PHONE_STATE);
                }
            }.start();
        } else {

        }
    }

    /**
     * 生成二维码
     *
     * @param payFlag
     * @param payAmount
     */
    private void submitOnPrecreate(String payFlag, String payAmount, String callBackMessage) {
        // validate
        String custName = edt_custName.getText().toString().trim();
        if (TextUtils.isEmpty(custName)) {
            showToast("请输入客户的真实姓名");
            return;
        }


        String amount = edt_amount.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            showToast("请输入贷款金额");
            return;
        }


        String custPhoneNo = edt_custPhoneNo.getText().toString().trim();
        if (TextUtils.isEmpty(custPhoneNo)) {
            showToast("请输入客户的实名认证手机号");
            return;
        }
        if (!RegexUtils.phone(custPhoneNo) || custPhoneNo.length() != 11) {
            showToast("请输入正确的手机号码");
            return;
        }


        String custIdNo = edt_custIdNo.getText().toString().trim();
        if (!TextUtils.isEmpty(custIdNo)) {
            if (!RegexUtils.idcard(custIdNo)) {
                showToast("请输入正确身份证号码");
                return;
            }
        }


        String commodityBarCode = edt_commodityBarCode.getText().toString().trim();
        if (TextUtils.isEmpty(commodityBarCode)) {
            showToast("请输入或者扫描");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("payFlag", payFlag);
        params.put("custPhoneNo", custPhoneNo);
        params.put("custName", custName);
        params.put("custIdNo", custIdNo);
        params.put("payAmount", payAmount);
        params.put("commodityName", edt_commodity_name.getText().toString());
        params.put("token", user.getToken());
        params.put("id", user.getId());
        params.put("commodityBarCode", commodityBarCode);
        params.put("custPhoneNo", custPhoneNo);
        params.put("imei", ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId());
        params.put("bussinessId", user.getBussinessId());
        if (!TextUtils.isEmpty(callBackMessage)) {
            params.put("callBackMessage", callBackMessage);
        }
        params.put("totalAmount", amount);

        if ("0".equals(amount)) {
            params.put("totalAmount", payAmount);
        }

        if (PAY_FLAG_HUABEI.equals(payFlag)) {
            params.put("caseId", caseId);
            params.put("duration", duration);
        }
        if (!TextUtils.isEmpty(orderId)) {
            params.put("orderId", orderId);
        }

        if (huabeiRequest != null) {
            presenter.preSubmitOrderV2(this, params);
        } else {
            presenter.submitOnPrecreate(this, params);

        }

    }

    /**
     * 提交花呗订单
     *
     * @param authCode
     * @param payFlag
     * @param payAmount
     */
    private void submit(String authCode, String payFlag, String payAmount) {
        // validate
        String custName = edt_custName.getText().toString().trim();
        if (TextUtils.isEmpty(custName)) {
            showToast("请输入客户的真实姓名");
            return;
        }


        String amount = edt_amount.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            showToast("请输入贷款金额");
            return;
        }


        String custPhoneNo = edt_custPhoneNo.getText().toString().trim();
        if (TextUtils.isEmpty(custPhoneNo)) {
            showToast("请输入客户的实名认证手机号");
            return;
        }
        if (!RegexUtils.phone(custPhoneNo) || custPhoneNo.length() != 11) {
            showToast("请输入正确的手机号码");
            return;
        }


        String custIdNo = edt_custIdNo.getText().toString().trim();
        if (!TextUtils.isEmpty(custIdNo)) {
            if (!RegexUtils.idcard(custIdNo)) {
                showToast("请输入正确身份证号码");
                return;
            }
        }


        String commodityBarCode = edt_commodityBarCode.getText().toString().trim();
        if (TextUtils.isEmpty(commodityBarCode)) {
            showToast("请输入或者扫描");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("authCode", authCode);
        params.put("payFlag", payFlag);
        params.put("custPhoneNo", custPhoneNo);
        params.put("custName", custName);
        params.put("custIdNo", custIdNo);
        params.put("payAmount", payAmount);
        params.put("commodityName", edt_commodity_name.getText().toString());
        params.put("token", user.getToken());
        params.put("id", user.getId());
        params.put("commodityBarCode", commodityBarCode);
        params.put("custPhoneNo", custPhoneNo);
        params.put("imei", ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId());
        params.put("bussinessId", user.getBussinessId());

        params.put("totalAmount", amount);

        if ("0".equals(amount)) {
            params.put("totalAmount", payAmount);
        }

        if (PAY_FLAG_HUABEI.equals(payFlag)) {
            params.put("caseId", caseId);
            params.put("duration", duration);
        }
        if (!TextUtils.isEmpty(orderId)) {
            params.put("orderId", orderId);
        }

        if (huabeiRequest != null) {
            presenter.scanPayV3(this, params);
        } else {
            presenter.submitData(this, params);
        }


    }


    @Override
    public void returnProceduresType(String s, int index) {
        edt_procedures.setText(s);
    }


    @Override
    public void returnSubmitSuccess() {
        presenter.payOrderQuery(this, user, orderId, "");
    }

    @Override
    public void returnOrder() {
        btn_submit.setVisibility(View.GONE);
        createOrder = CreateOrder.COMPLTE;
//        layout_taobao.setEnabled(false);
        layout_huabei.setEnabled(false);
        finish();
    }

    @Override
    public void returnSanpay(Response res) {


    }

    private HuabeiDialog huabeiDialog;

    @Override
    public void showLoading() {
        huabeiDialog = HuabeiDialog.getInstance(this);
        huabeiDialog.show();
    }

    @Override
    public void dismissLoading() {
        huabeiDialog.dismiss();
    }

    @Override
    public void returnTimeout() {
        showToast("支付超时！请重新支付！");
    }

    @Override
    public void returnPayEirr(Response res) {
        if (!TextUtils.isEmpty(res.getMessage())) {
            showToast(res.getMessage());
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    /**
     * 提交输入框
     */
    private InputHuabeiFragment inputHuabeiFragment;

    /**
     * 弹出键盘
     */
    private void addHuabeiFragmentToStack() {
        inputHuabeiFragment = InputHuabeiFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.INTENT_NAME, order);
        order.setIndex(index);
        order.setCurrentApplyAmount(currentApplyAmount);
        inputHuabeiFragment.setArguments(bundle);
        inputHuabeiFragment.setCallback(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer,
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer);
        transaction.add(R.id.fl_fragment, inputHuabeiFragment);
        transaction.addToBackStack("inputHuabeiFragment");
        transaction.commit();
    }

    /**
     * 提交输入框
     */
    private InputTaobaoFragment inputTaobaoFragment;


    /**
     * 弹出键盘
     */
    private void addTabobaoFragmentToStack() {


        inputTaobaoFragment = InputTaobaoFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.INTENT_NAME, currentTaobaoAccount);
        inputTaobaoFragment.setArguments(bundle);
        inputTaobaoFragment.setCallback(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer,
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer);
        transaction.add(R.id.fl_fragment, inputTaobaoFragment);
        transaction.addToBackStack("inputTaobaoFragment");
        transaction.commit();
    }


    private String currentApplyAmount;
    private String loanAmount;

    @Override
    public void onDuration(int index) {
        this.index = index;
        updateView(index);
    }

    @Override
    public void onAccountChange(String currentApplyAmount, String loanAmount) {
        this.currentApplyAmount = currentApplyAmount;
        this.loanAmount = loanAmount;
    }

    @Override
    public void onSubmitOrder() {
        CaptureActivity.startAction(this, IntentFlag.HUABEI_SCAN);
    }

    @Override
    public void onHuabeiSubmitPrecreate(String callBackMessage) {
        submitOnPrecreate(PAY_FLAG_HUABEI, loanAmount, callBackMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        EventRxBus.getInstance().unregister(ConfirmCommodityActivity.class.getSimpleName());
        EventRxBus.getInstance().unregister(IntentFlag.ORDER_ID);
        EventRxBus.getInstance().unregister(IntentFlag.HUABEI_SCAN);
        EventRxBus.getInstance().unregister(IntentFlag.TAOBAO_SCAN);

    }

    private String currentTaobaoAccount;

    @Override
    public void onTaobaoAccountChange(String account) {
        currentTaobaoAccount = account;
    }

    @Override
    public void onTaobeoSubmitOrder() {
        CaptureActivity.startAction(this, IntentFlag.TAOBAO_SCAN);
    }

    @Override
    public void onTaobeoSubmitPrecreate() {
        submitOnPrecreate(PAY_FLAG_TAOBAO, currentTaobaoAccount, null);

    }

    @Override
    public void finish() {
        if (btn_submit.getVisibility() == View.VISIBLE) {
            presenter.showUnComplate(this);
            return;
        }


        if (createOrder == CreateOrder.COMPLTE) {
            Intent intent = null;
            if (IntentFlag.ROLE_ADMINISTRATOR.equals(user.getBizRole())) {
                intent = new Intent(this, ManagerIndexActivity.class);
            } else if (IntentFlag.ROLE_ADMIN.equals(user.getBizRole())) {
                intent = new Intent(this, AdminIndexActivity.class);
            }else if (IntentFlag.ROLE_APLIY_SALE.equals(user.getBizRole())){
                intent = new Intent(this, ApliyMainActivity.class);
            }else {
                intent = new Intent(this, IndexActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(IntentFlag.INDEX, 1);
            startActivity(intent);
        }
        super.finish();

    }


    /**
     * 淘宝提示
     */
    private void alipayAlert() {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("此操作不需要客户确认就可以完成交易，请您谨慎操作。")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
                .withButton1Text("我知道了")
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        addTabobaoFragmentToStack();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }


}
