package com.hdfex.merchantqrshow.admin.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.client.android.CaptureActivity;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.admin.bam.CreateQRCode;
import com.hdfex.merchantqrshow.bean.admin.bam.CreateQRCodeResponse;
import com.hdfex.merchantqrshow.bean.apliysale.QRCodeResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;
import okhttp3.Request;
import rx.Subscriber;

/**
 * author Created by harrishuang on 2017/12/28.
 * email : huangjinping@hdfex.com
 */

public class MergeQrCodeActivity extends BaseActivity implements View.OnClickListener {
    private TextView tb_tv_titile;
    private ImageView img_wx;
    private ImageView img_alipay;
    private ImageView img_back;
    private User user;
    private LinearLayout layout_create;
    private LinearLayout layout_reset;
    private ImageView img_qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mergeqrcode);
        user = UserManager.getUser(this);
        initView();
        EventRxBus.getInstance().register(IntentFlag.WX_QRCODE).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {
                String code = (String) o;
                if (code.startsWith("wxp://")) {
                    code = code.replace("wxp://", "");
                    img_wx.setTag(code);
                    img_wx.setImageResource(R.mipmap.ic_wx_qrcode);
                } else {
                    showToast("请扫描微信二维码");
                }
            }
        });
        EventRxBus.getInstance().register(IntentFlag.ALIPAY_QRCODE).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {
                String code = (String) o;
                if (code.startsWith("HTTPS://QR.ALIPAY.COM/")) {
                    code = code.replace("HTTPS://QR.ALIPAY.COM/", "");
                    img_alipay.setTag(code);
                    img_alipay.setImageResource(R.mipmap.ic_alipay_qrcode);
                } else {
                    showToast("请扫描支付宝二维码");
                }
            }
        });
        parseCode(getIntent());
    }

    /**
     * @param intent
     */
    private void parseCode(Intent intent) {
        if (intent.getSerializableExtra(QRCodeResult.class.getSimpleName()) != null) {
            QRCodeResult qrcode = (QRCodeResult) intent.getSerializableExtra(QRCodeResult.class.getSimpleName());
            if (!TextUtils.isEmpty(qrcode.getQrcodeUrl())) {
                Glide.with(getApplicationContext())
                        .load(qrcode.getQrcodeUrl())
                        .placeholder(R.mipmap.ic_defoult)
                        .error(R.mipmap.ic_defoult)
                        .into(img_qrcode);
                layout_reset.setVisibility(View.VISIBLE);
                return;
            }
        }

        layout_create.setVisibility(View.VISIBLE);

    }

    private void initView() {
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        img_wx = (ImageView) findViewById(R.id.img_wx);
        img_alipay = (ImageView) findViewById(R.id.img_alipay);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        img_wx.setOnClickListener(this);
        img_alipay.setOnClickListener(this);
        tb_tv_titile.setText("收款码合并");
        layout_create = (LinearLayout) findViewById(R.id.layout_create);
        layout_create.setOnClickListener(this);
        layout_reset = (LinearLayout) findViewById(R.id.layout_reset);
        layout_reset.setOnClickListener(this);
        img_qrcode = (ImageView) findViewById(R.id.img_qrcode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_wx:
                CaptureActivity.startAction(this, IntentFlag.WX_QRCODE);
                break;
            case R.id.img_alipay:
                CaptureActivity.startAction(this, IntentFlag.ALIPAY_QRCODE);
                break;
        }
    }

    /**
     * 拼接
     */
    public void onMerge(View view) {

        if (img_wx.getTag() == null) {
            showToast("请扫描微信收款码");
            return;
        }

        if (img_alipay.getTag() == null) {
            showToast("请扫描支付宝收款码");
            return;
        }
        String url = NetConst.CREATE_QRCODE_PAY;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("bussinessId",user.getBussinessId())
                .addParams("admin/createQRCode", user.getBussinessId())
                .addParams("wxQrCode", img_wx.getTag().toString())
                .addParams("alipayQrCode", img_alipay.getTag().toString());
        postFormBuilder.build()
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
                    }

                    @Override
                    public void onResponse(String response) {

                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                CreateQRCodeResponse response1 = GsonUtil.changeGsonToBean(response, CreateQRCodeResponse.class);
                                CreateQRCode result = response1.getResult();
                                ReceiptQrCodeActivity.startAction(MergeQrCodeActivity.this, result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 提交
     *
     * @param context
     * @param result
     */
    public static void startAction(Context context, QRCodeResult result) {
        Intent intent = new Intent(context, MergeQrCodeActivity.class);
        intent.putExtra(QRCodeResult.class.getSimpleName(), result);
        context.startActivity(intent);
    }


    /**
     * 提交
     *
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, MergeQrCodeActivity.class);
        context.startActivity(intent);
    }


    public void onReset(View view) {
        layout_create.setVisibility(View.VISIBLE);
        layout_reset.setVisibility(View.GONE);
    }
}
