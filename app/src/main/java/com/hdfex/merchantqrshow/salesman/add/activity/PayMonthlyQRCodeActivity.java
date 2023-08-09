package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.AdditionalInfo;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.Commoditys;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.bean.salesman.realtyQR.CommodityInfoForHR;
import com.hdfex.merchantqrshow.bean.salesman.realtyQR.CommodityInfoForHRResponse;
import com.hdfex.merchantqrshow.bean.salesman.resource.PayMonthly;
import com.hdfex.merchantqrshow.mvp.presenter.QrCodePresenter;
import com.hdfex.merchantqrshow.mvp.view.QRCodeView;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.fragment.ModifyInfomationFragment;
import com.hdfex.merchantqrshow.salesman.main.activity.IndexActivity;
import com.hdfex.merchantqrshow.salesman.main.activity.ScanRecordActivity;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.HDAlertDialog;
import com.jakewharton.rxbinding.view.RxView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Request;
import rx.Observer;

/**
 * 月付二维码展示
 */
public class PayMonthlyQRCodeActivity extends BaseActivity implements View.OnClickListener, QRCodeView {
    private TextView tb_tv_titile;
    private ImageView img_back;
    private TextView txt_commodity_price;
    private TextView txt_down_payment;
    private TextView txt_apply_amount;
    private ImageView ic_qrcode;
    private TextView tv_home;
    private TextView tv_album;
    private TextView tv_telephone;
    private TextView tv_start;
    private TextView tv_monthrent;
    private LinearLayout ll_commodity_root;
    private LinearLayout ll_realty_root;
    private PayMonthly payMonthly;
    private TextView tv_downpayment_type;
    private TextView tv_simple_commodity_name;
    private TextView txt_period_amount;
    private Intent comIntent;
    private QrCodePresenter presenter;
    private User user;
    private LinearLayout layout_scan_pic;
    private TextView txt_code_createTime;
    private LinearLayout layout_card_view;
    private LinearLayout layout_package_status;
    private TextView txt_package_status;
    private ScrollView scrl_root;
    private LinearLayout layout_root;
    private TextView tv_grace_period;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ActionSheetStyle);
        setContentView(R.layout.activity_paymonthlyqr);
        user = UserManager.getUser(this);
        presenter = new QrCodePresenter();
        presenter.attachView(this);
        comIntent = getIntent();
        initView();
        initDate();
    }

    /**
     *
     */
    private void initDate() {
        tb_tv_titile.setText("二维码");
        if (IntentFlag.CONFORM.equals(comIntent.getStringExtra(IntentFlag.INTENT_NAME))) {
            //加载商品类布
            ll_commodity_root.setVisibility(View.GONE);
            ll_realty_root.setVisibility(View.VISIBLE);
            loadData(getIntent().getStringExtra("packageId"));
        } else {
            //加载租房类布局
            ll_commodity_root.setVisibility(View.GONE);
            ll_realty_root.setVisibility(View.VISIBLE);
            getData();
        }

    }

    /**
     * 地产类数据展示
     */
    private void getData() {
        Serializable serializable = comIntent.getExtras().getSerializable(IntentFlag.SERIALIZABLE);
        payMonthly = (PayMonthly) serializable;
        getNetData();
    }

    /**
     * 网络数据展示（含二维码）
     */
    private void getNetData() {
        if (connect()) {
            OkHttpUtils.post()
                    .url(NetConst.QUERY_COMMODITY_INFO_FORHR)
                    .addParams("token", user.getToken())
                    .addParams("id", user.getId())
                    .addParams("packageId", payMonthly.getPackageId())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            showWebEirr();
                        }

                        @Override
                        public void onBefore(Request request) {
                            super.onBefore(request);
                            showProgress();
                        }

                        @Override
                        public void onAfter() {
                            super.onAfter();
                            dismissProgress();
                        }

                        @Override
                        public void onResponse(String response) {
                            try {
                                if (checkResonse(response)) {
                                    CommodityInfoForHRResponse realtyQrResponse = GsonUtil.changeGsonToBean(response, CommodityInfoForHRResponse.class);
                                    CommodityInfoForHR createModel = realtyQrResponse.getResult();
                                    setLocalData(createModel);
                                }
                            } catch (Exception e) {
                                showToast("服务异常");
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }


    /**
     * 填充数据
     *
     * @param createModel 数据源
     */
    private void setLocalData(CommodityInfoForHR createModel) {

        List<Commoditys> commoditys = createModel.getCommoditys();
        if (commoditys != null && commoditys.size() != 0) {
            Commoditys commodity = commoditys.get(0);
            if (!TextUtils.isEmpty(commodity.getCommodityName())) {
                tv_simple_commodity_name.setText(commodity.getCommodityName());
            }

            if (commodity.getMonthRent() != null) {
                tv_grace_period.setText(commodity.getMonthRent().toString() + "元");
            }

            if (commodity.getDeposit() != null) {
                tv_monthrent.setText(commodity.getDeposit().toString() + "元");
            }

            if (!TextUtils.isEmpty(commodity.getEndDate()) && !TextUtils.isEmpty(commodity.getStartDate())) {
                tv_start.setText(commodity.getStartDate() + "至" + commodity.getEndDate());
            }

            if (!TextUtils.isEmpty(commodity.getPayTypeName())) {
                tv_downpayment_type.setText(commodity.getPayTypeName());
            }

        }
        if (createModel.getQrcodeUrl() != null) {
            Glide.with(getApplicationContext())
                    .load(createModel.getQrcodeUrl())
                    .placeholder(R.mipmap.ic_defoult)
                    .error(R.mipmap.ic_defoult)
                    .into(ic_qrcode);
        }
    }

    /**
     * 给订单设置信息
     *
     * @param order
     */
    private void setViewByData(Order order) {

        List<Commoditys> commoditys = order.getCommoditys();
        if (commoditys != null && commoditys.size() != 0) {
            Commoditys commodity = commoditys.get(0);
            if (!TextUtils.isEmpty(commodity.getCommodityName())) {
                tv_simple_commodity_name.setText(commodity.getCommodityName());
            }

            if (commodity.getMonthRent() != null) {
                tv_grace_period.setText(commodity.getMonthRent().toString() + "元");
            }

            if (commodity.getDeposit() != null) {
                tv_monthrent.setText(commodity.getDeposit().toString() + "元");
            }

            if (!TextUtils.isEmpty(commodity.getEndDate()) && !TextUtils.isEmpty(commodity.getStartDate())) {
                tv_start.setText(commodity.getStartDate() + "至" + commodity.getEndDate());
            }

            if (!TextUtils.isEmpty(commodity.getPayTypeName())) {
                tv_downpayment_type.setText(commodity.getPayTypeName());
            }

        }
        if (order.getQrcodeUrl() != null) {
            Glide.with(getApplicationContext())
                    .load(order.getQrcodeUrl())
                    .placeholder(R.mipmap.ic_defoult)
                    .error(R.mipmap.ic_defoult)
                    .into(ic_qrcode);
        }

    }

    private void initView() {
        txt_package_status = (TextView) findViewById(R.id.txt_package_status);
        layout_package_status = (LinearLayout) findViewById(R.id.layout_package_status);
        layout_card_view = (LinearLayout) findViewById(R.id.layout_card_view);
        txt_code_createTime = (TextView) findViewById(R.id.txt_code_createTime);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        layout_scan_pic = (LinearLayout) findViewById(R.id.layout_scan_pic);
        txt_commodity_price = (TextView) findViewById(R.id.txt_commodity_price);
        txt_down_payment = (TextView) findViewById(R.id.txt_down_payment);
        txt_apply_amount = (TextView) findViewById(R.id.txt_apply_amount);
        ic_qrcode = (ImageView) findViewById(R.id.ic_qrcode);
        txt_period_amount = (TextView) findViewById(R.id.txt_period_amount);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setText("更多");
        tv_home.setVisibility(View.VISIBLE);
        tv_album = (TextView) findViewById(R.id.tv_album);
        tv_album.setOnClickListener(this);
        tv_home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tv_album.setTextColor(getResources().getColor(R.color.text_press));
                        ToastUtils.makeText(PayMonthlyQRCodeActivity.this, "正在保存图片，请稍后...").show();
                        break;
                    case MotionEvent.ACTION_UP:
                        tv_album.setTextColor(getResources().getColor(R.color.white));
                        break;
                }
                return false;
            }
        });


        tv_telephone = (TextView) findViewById(R.id.tv_telephone);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_monthrent = (TextView) findViewById(R.id.tv_monthrent);
        ll_commodity_root = (LinearLayout) findViewById(R.id.ll_commodity_root);
        ll_realty_root = (LinearLayout) findViewById(R.id.ll_realty_root);
        tv_downpayment_type = (TextView) findViewById(R.id.tv_downpayment_type);
        tv_simple_commodity_name = (TextView) findViewById(R.id.tv_simple_commodity_name);
        RxView.clicks(tv_home).throttleFirst(5 * 1000, TimeUnit.MILLISECONDS).subscribe(new Observer<Void>() {
                                                                                            @Override
                                                                                            public void onCompleted() {

                                                                                            }

                                                                                            @Override
                                                                                            public void onError(Throwable e) {

                                                                                            }

                                                                                            @Override
                                                                                            public void onNext(Void aVoid) {
                                                                                                presenter.showActionSheet(PayMonthlyQRCodeActivity.this, getSupportFragmentManager(), layout_root, new View[]{layout_root});
                                                                                            }
                                                                                        }
        );
        scrl_root = (ScrollView) findViewById(R.id.scrl_root);
        layout_root = (LinearLayout) findViewById(R.id.layout_root);
        tv_grace_period = (TextView) findViewById(R.id.tv_grace_period);
        tv_grace_period.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_album:
                presenter.onModify(user);
                break;
            case R.id.img_back:
                onBackPressed();
                break;

        }
    }

    public void showBackDialog() {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("是否返回首页?")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(this.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
                .withButton1Text("否")
                .withButton2Text("是")
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
                        Intent mIntent = new Intent(PayMonthlyQRCodeActivity.this, IndexActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mIntent);
                        finish();
                    }
                })
                .show();
    }

    /**
     * 保存图片到相册
     */

    static String path = "nide";

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        File cacheDir = context.getCacheDir();
        File appDir = new File(cacheDir.getAbsolutePath(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 其次把文件插入到系统图库
         */
        try {


            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            ToastUtils.makeText(context, "图片保存成功").show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /**
         *最后通知图库更新
         */
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }

    private void loadData(String packageId) {

        if (!connect()) {
            return;
        }

        OkHttpUtils
                .post()
                .url(NetConst.QUERY_COMMODITY_INFO_FORHR)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("packageId", packageId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showWebEirr();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.optString("result");
                                Order order = GsonUtil.changeGsonToBean(result, Order.class);
                                setViewByData(order);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void saveCurrentImage() {
        //获取当前屏幕的大小
        int width = layout_card_view.getWidth();
        int height = layout_card_view.getHeight();
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //找到当前页面的跟布局
        View view = layout_card_view;
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片
        temBitmap = view.getDrawingCache();
//      saveImageToGallery(this, temBitmap);
        saveImageToGalleryNew(this, temBitmap);
    }


    public void saveImageToGalleryNew(Context context, Bitmap bmp) {
        if (bmp == null) {
            ToastUtils.d(context, "保存出错了...").show();
            return;
        }
        // 首先保存图片
        File appDir = new File(context.getCacheDir(), "ywq");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            ToastUtils.d(context, "文件未发现...").show();

            e.printStackTrace();
        } catch (IOException e) {
            ToastUtils.d(context, "保存出错了...").show();

            e.printStackTrace();
        } catch (Exception e) {
            ToastUtils.d(context, "保存出错了...").show();
            e.printStackTrace();
        }
        // 最后通知图库更新
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        showAlertDialog("保存成功!");

    }


    private void showAlertDialog(String title) {
        final HDAlertDialog dialog1 = HDAlertDialog.getInstance(this)
                .withTitle("提示")
                .withMsg(title);
        dialog1.withButton1Onclick("我知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }


    private ModifyInfomationFragment modifyInfomationFragment;

    @Override
    public void addPassWordFragmentToStack(AdditionalInfo info) {
        modifyInfomationFragment = ModifyInfomationFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.INTENT_ADDITIONAL, info);
        modifyInfomationFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer,
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer);
        transaction.replace(R.id.fl_fragment, modifyInfomationFragment);
        transaction.addToBackStack("modifyInfomationFragment");
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    @Override
    public void onBackPressed() {

        if (IntentFlag.CONFORM.equals(comIntent.getStringExtra(IntentFlag.INTENT_NAME))) {
            //加载商品类布
            finish();
        } else {
            Intent mIntent = new Intent(PayMonthlyQRCodeActivity.this, ScanRecordActivity.class);
            mIntent.putExtra(IntentFlag.INTENT_SCAN_TYPE, IntentFlag.SCAN_TYPE_ZUFANG);
            startActivity(mIntent);
            finish();
        }
    }

}
