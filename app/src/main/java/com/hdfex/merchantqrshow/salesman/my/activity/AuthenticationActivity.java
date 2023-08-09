package com.hdfex.merchantqrshow.salesman.my.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.my.activity.ResetPasswordActivity;
import com.hdfex.merchantqrshow.admin.my.activity.UpdatePasswordActivity;
import com.hdfex.merchantqrshow.admin.my.activity.WithdrawPasswordActivity;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfo;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.AuthenticationContract;
import com.hdfex.merchantqrshow.mvp.presenter.AuthenticationPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.activity.PhotoViewActivity;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.ActionSheet;
import com.hdfex.merchantqrshow.widget.DateUtils;
import com.hdfex.merchantqrshow.widget.picker.CertificateDatePicker;
import com.idcard.CardInfo;
import com.idcard.TFieldID;
import com.idcard.TRECAPIImpl;
import com.idcard.TStatus;
import com.idcard.TengineID;
import com.megvii.idcardlib.IDCardScanActivity;
import com.megvii.idcardquality.IDCardQualityLicenseManager;
import com.megvii.licensemanager.Manager;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.turui.bank.ocr.card.TRCardScan;

import java.io.File;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Request;
import rx.functions.Action1;


/**
 * 身份认证
 * author Created by harrishuang on 2017/9/7.
 * email : huangjinping@hdfex.com
 */

public class AuthenticationActivity extends BaseActivity implements AuthenticationContract.View, View.OnClickListener {

    public static final int REQUEST_CODE_CROP_IMAGE = 0x3; //请求码 裁剪图片
    private static final int INTO_IDCARDSCAN_PAGE = 100;
    private TRECAPIImpl engineDemo = new TRECAPIImpl();
    private ImageView img_idCardFrontImg;
    private ImageView img_idCardBackImg;
    private int targetImageView; //显示图片的标志位
    private AdvancedInfo mAdvancedInfo; //高级信息
    public int RESULT_GET_CARD_OK = 2;
    private AuthenticationContract.Presenter presenter;
    private User user;
    private EditText edt_idName;
    private EditText edt_idNO;
    private EditText edt_idAddress;
    private EditText et_validity_start;
    private EditText et_validity_end;
    private Button btn_submit;
    private ImageView img_gif;
    private RelativeLayout layout_progress_center;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        setTheme(R.style.ActionSheetStyle);
        presenter = new AuthenticationPresenter();
        presenter.attachView(this);
        initView();

        user = UserManager.getUser(this);
        mAdvancedInfo = new AdvancedInfo();
        initTORAsdk();
//        presenter.loadIdCard(user);
        tb_tv_titile.setText("实名认证");
        rxPermissions = RxPermissions.getInstance(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (!aBoolean) {
                    showPermissDeniedAlert();
                }
            }
        });

        /**
         * 先不用了
         */
//        initSDK();
    }



    private void initSDK(){
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Manager manager = new Manager(AuthenticationActivity.this);
                    IDCardQualityLicenseManager idCardLicenseManager = new IDCardQualityLicenseManager(
                            AuthenticationActivity.this);
                    manager.registerLicenseManager(idCardLicenseManager);
                    String uuid = "13213214321424";
                    manager.takeLicenseFromNetwork(uuid);
                    String contextStr = manager.getContext(uuid);
                    Log.w("ceshi", "contextStr====" + contextStr);

                    Log.w("ceshi",
                            "idCardLicenseManager.checkCachedLicense()===" + idCardLicenseManager.checkCachedLicense());
//                if (idCardLicenseManager.checkCachedLicense() > 0)
//                    UIAuthState(true);
//                else
//                    UIAuthState(false);
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initTORAsdk() {
        TRCardScan.SetEngineType(TengineID.TIDCARD2);
        TStatus tStatus = engineDemo.TR_StartUP();
        if (tStatus == TStatus.TR_TIME_OUT) {
            Toast.makeText(getBaseContext(), "引擎过期", Toast.LENGTH_SHORT).show();
        } else if (tStatus == TStatus.TR_FAIL) {
            Toast.makeText(getBaseContext(), "引擎初始化失败", Toast.LENGTH_SHORT).show();
        }
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AuthenticationActivity.class);
        context.startActivity(intent);
    }

    /**
     * 提交
     *
     * @param context
     * @param intent
     */
    public static void startAction(Context context, Intent intent) {
        intent.setClass(context, AuthenticationActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        img_idCardFrontImg = (ImageView) findViewById(R.id.img_idCardFrontImg);
        img_idCardBackImg = (ImageView) findViewById(R.id.img_idCardBackImg);
        img_idCardBackImg.setOnClickListener(this);
        img_idCardFrontImg.setOnClickListener(this);
        edt_idName = (EditText) findViewById(R.id.edt_idName);
        edt_idNO = (EditText) findViewById(R.id.edt_idNO);
        edt_idAddress = (EditText) findViewById(R.id.edt_idAddress);
        et_validity_start = (EditText) findViewById(R.id.et_validity_start);
        et_validity_start.setOnClickListener(this);
        et_validity_end = (EditText) findViewById(R.id.et_validity_end);
        et_validity_end.setOnClickListener(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        img_gif = (ImageView) findViewById(R.id.img_gif);
        img_gif.setOnClickListener(this);
        layout_progress_center = (RelativeLayout) findViewById(R.id.layout_progress_center);
        layout_progress_center.setOnClickListener(this);
        Glide.with(this).load("file:///android_asset/ic_loading.gif").asGif().into(img_gif);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setOnClickListener(this);
    }


    /**
     * 正面身份证选择
     */
    private void showActionSheetForFont() {
        String[] action = null;
        if (mAdvancedInfo.getIdCardFrontImg() != null || !TextUtils.isEmpty(mAdvancedInfo.getIdCardFrontImgStr())) {
            action = new String[]{"开始扫描", "预览"};
        } else {
            action = new String[]{"开始扫描"};
        }


        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(action)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                switch (index) {
                    case 0:
                        boolean b = sdcardExist();
                        if (!b) {
                            ToastUtils.e(getApplicationContext(), "未插入内存卡").show();
                            return;
                        }





//                        Intent intent = new Intent(AuthenticationActivity.this, IDCardScanActivity.class);
//                        intent.putExtra("side", 0);
//                        intent.putExtra("isvertical", false);
//                        startActivityForResult(intent, INTO_IDCARDSCAN_PAGE);


                        TRCardScan.isOpenProgress = true;
                        targetImageView = R.id.img_idCardFrontImg;
                        Intent intent = new Intent(AuthenticationActivity.this, TRCardScan.class);
                        intent.putExtra("engine", engineDemo);
                        intent.putExtra(TRCardScan.tag, TRCardScan.ONFONT);
                        startActivityForResult(intent, RESULT_GET_CARD_OK);








                        break;
                    case 1:
                        PhotoViewActivity.startAction(AuthenticationActivity.this, mAdvancedInfo.getIdCardFrontImg(), mAdvancedInfo.getIdCardFrontImgStr());

                        break;
                }
            }
        }).show();

    }


    /**
     * 选择反面扫描图片
     */
    private void showActionSheetForBack() {
        String[] action = null;
        if (mAdvancedInfo.getIdCardBackImg() != null || !TextUtils.isEmpty(mAdvancedInfo.getIdCardBackImgStr())) {
            action = new String[]{"开始扫描", "预览"};
        } else {
            action = new String[]{"开始扫描"};
        }
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(action)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                switch (index) {
                    case 0:
                        boolean b = sdcardExist();
                        if (!b) {
                            ToastUtils.e(getApplicationContext(), "未插入内存卡").show();
                            return;
                        }


//
//                        Intent intent = new Intent(AuthenticationActivity.this, IDCardScanActivity.class);
//                        intent.putExtra("side", 1);
//                        intent.putExtra("isvertical", false);
//                        startActivityForResult(intent, INTO_IDCARDSCAN_PAGE);
                        TRCardScan.isOpenProgress = true;
                        targetImageView = R.id.img_idCardBackImg;
                        Intent intent = new Intent(AuthenticationActivity.this, TRCardScan.class);
                        intent.putExtra("engine", engineDemo);
                        intent.putExtra(TRCardScan.tag, TRCardScan.ONBACK);
                        startActivityForResult(intent, RESULT_GET_CARD_OK);
                        break;
                    case 1:
                        PhotoViewActivity.startAction(AuthenticationActivity.this, mAdvancedInfo.getIdCardBackImg(), mAdvancedInfo.getIdCardBackImgStr());
                        break;
                }

            }
        }).show();

    }


    @Override
    protected void onPause() {
        super.onPause();
        dismissProgress();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTO_IDCARDSCAN_PAGE && resultCode == RESULT_OK) {
            presenter.parseFaceIdCardSDKResult(this, user, data);
            return;
        }

        if (requestCode == RESULT_GET_CARD_OK) {
            if (targetImageView == R.id.img_idCardFrontImg) {
                Bitmap takeFront = TRCardScan.TakeBitmap;  // 正面
                if (takeFront == null) {
                    return;
                }
                CardInfo cardInfo = (CardInfo) data.getSerializableExtra("cardinfo");
                if (TextUtils.isEmpty(cardInfo.getFieldString(TFieldID.NAME)) || TextUtils.isEmpty(cardInfo.getFieldString(TFieldID.ADDRESS)) || TextUtils.isEmpty(cardInfo.getFieldString(TFieldID.NUM))) {
                    ToastUtils.i(AuthenticationActivity.this, "扫描失败，请保证身份证清晰").show();
                    return;
                }
                presenter.parseCardInfo(AuthenticationActivity.this, takeFront, IMAGE_TYPE_FONT, user);


                mAdvancedInfo.setIdName(cardInfo.getFieldString(TFieldID.NAME));
                mAdvancedInfo.setIdNo(cardInfo.getFieldString(TFieldID.NUM));
                mAdvancedInfo.setIdAddress(cardInfo.getFieldString(TFieldID.ADDRESS));

                if (!TextUtils.isEmpty(mAdvancedInfo.getIdName())) {
                    edt_idName.setText(mAdvancedInfo.getIdName());
                }

                if (!TextUtils.isEmpty(mAdvancedInfo.getIdNo())) {
                    edt_idNO.setText(mAdvancedInfo.getIdNo());
                }
                if (!TextUtils.isEmpty(mAdvancedInfo.getIdAddress())) {
                    edt_idAddress.setText(mAdvancedInfo.getIdAddress());
                }


                targetImageView = R.id.img_idCardFrontImg;
                return;
            } else if (targetImageView == R.id.img_idCardBackImg) {
                Bitmap takeBack = TRCardScan.TakeBitmap;  // 反面
                if (takeBack == null) {
                    return;
                }
                CardInfo cardInfo = (CardInfo) data.getSerializableExtra("cardinfo");
                if (TextUtils.isEmpty(cardInfo.getFieldString(TFieldID.PERIOD)) || !verityPeriod(cardInfo.getFieldString(TFieldID.PERIOD))) {
                    ToastUtils.i(AuthenticationActivity.this, "扫描失败，请保证身份证清晰").show();
                    return;
                }
                presenter.parseCardInfo(AuthenticationActivity.this, takeBack, IMAGE_TYPE_BACK, user);
                setPeriod(cardInfo.getFieldString(TFieldID.PERIOD));
                if (!TextUtils.isEmpty(mAdvancedInfo.getIdTermBegin())) {
                    et_validity_start.setText(mAdvancedInfo.getIdTermBegin());
                }

                if (!TextUtils.isEmpty(mAdvancedInfo.getIdTermEnd())) {
                    et_validity_end.setText(mAdvancedInfo.getIdTermEnd());
                }

                targetImageView = R.id.img_idCardBackImg;
                return;
            }
        }
    }

    private void setPeriod(String period) {

        String[] split = period.split("[-]+");
        try {
            if (split != null && split.length == 2) {
                String start = split[0].replace(".", "-");
                String end = split[1].replace(".", "-");
                mAdvancedInfo.setIdTermBegin(start);
                mAdvancedInfo.setIdTermEnd(end);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验有效期
     *
     * @param period
     * @return
     */
    private boolean verityPeriod(String period) {
        String[] split = period.split("[-]+");
        try {
            if (split != null && split.length == 2) {
                String start = split[0].replace(".", "-");
                String end = split[1].replace(".", "-");
                if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {

                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void startParse() {
        layout_progress_center.setVisibility(View.VISIBLE);

    }

    @Override
    public void stopParse() {
        layout_progress_center.setVisibility(View.GONE);

    }

    @Override
    public void returnUpLoadSuccess(File imageFile, String type) {
        if (IMAGE_TYPE_FONT.equals(type)) {
            Glide.with(AuthenticationActivity.this).load(imageFile).into(img_idCardFrontImg);
            mAdvancedInfo.setIdCardFrontImg(imageFile);
            targetImageView = -1;
        } else if (IMAGE_TYPE_BACK.equals(type)) {
            Glide.with(AuthenticationActivity.this).load(imageFile).into(img_idCardBackImg);
            mAdvancedInfo.setIdCardBackImg(imageFile);
            targetImageView = -1;
        }
    }

    @Override
    public void returnCardInfo(AdvancedInfo result) {
        if (result == null) {
            return;
        }
        mAdvancedInfo = result;

        if (mAdvancedInfo.getIdCardFrontImgStr() != null) {
            Glide.with(this).load(mAdvancedInfo.getIdCardFrontImgStr()).into(img_idCardFrontImg);
        }
        if (mAdvancedInfo.getIdCardBackImgStr() != null) {
            Glide.with(this).load(mAdvancedInfo.getIdCardBackImgStr()).into(img_idCardBackImg);
        }

        if (!TextUtils.isEmpty(mAdvancedInfo.getIdName())) {
            edt_idName.setText(mAdvancedInfo.getIdName());
        }

        if (!TextUtils.isEmpty(mAdvancedInfo.getIdNo())) {
            edt_idNO.setText(mAdvancedInfo.getIdNo() + "");
        }

        if (!TextUtils.isEmpty(mAdvancedInfo.getIdAddress())) {
            edt_idAddress.setText(mAdvancedInfo.getIdAddress());
        }

        if (!TextUtils.isEmpty(mAdvancedInfo.getIdTermBegin())) {
            et_validity_start.setText(mAdvancedInfo.getIdTermBegin());
        }

        if (!TextUtils.isEmpty(mAdvancedInfo.getIdTermEnd())) {
            et_validity_end.setText(mAdvancedInfo.getIdTermEnd());
        }


    }

    /**
     * 提交数据
     *
     * @return
     */
    private boolean attempt() {
        String idName = edt_idName.getText().toString().trim();
        if (TextUtils.isEmpty(idName)) {
            showToast("请输入真实姓名");
            return false;
        }
        mAdvancedInfo.setIdName(idName);


        String idNo = edt_idNO.getText().toString().trim();
        if (TextUtils.isEmpty(idNo)) {
            showToast("请输入真实身份证号码");
            return false;
        }
        mAdvancedInfo.setIdNo(idNo);
        String idAddress = edt_idAddress.getText().toString().trim();
        if (TextUtils.isEmpty(idAddress)) {
            showToast("请输入真实户籍地址");
            return false;
        }
        mAdvancedInfo.setIdAddress(idAddress);

        String start = et_validity_start.getText().toString().trim();
        if (TextUtils.isEmpty(start)) {
            showToast("请选择有效期");
            return false;
        }

        String end = et_validity_end.getText().toString();
        if (TextUtils.isEmpty(end)) {
            showToast("请选择有效期");
            return false;
        }


        if (et_validity_end.getText().toString().contains("长期")) {

        } else {
            if (!DateUtils.isValidDate(et_validity_end.getText().toString())) {
                showToast("有效期错误");
                return false;
            }
            boolean b = DateUtils.compare_date(et_validity_start.getText().toString(), et_validity_end.getText().toString());
            if (b) {
                showToast("结束日应大于开始日期");
                return false;
            }
        }

        mAdvancedInfo.setIdTermBegin(start);
        mAdvancedInfo.setIdTermEnd(end);

        if (TextUtils.isEmpty(mAdvancedInfo.getIdCardFrontImgStr())) {
            if (mAdvancedInfo.getIdCardFrontImg() == null) {
                ToastUtils.e(this, "请拍照后上传").show();
                return false;
            } else if (!mAdvancedInfo.getIdCardFrontImg().exists()) {
                ToastUtils.e(this, "由于您的手机内存不足,无法找到身份证正面照").show();
                return false;
            }
        }
        if (TextUtils.isEmpty(mAdvancedInfo.getIdCardBackImgStr())) {
            if (mAdvancedInfo.getIdCardBackImg() == null) {
                ToastUtils.e(this, "请拍照后上传").show();
                return false;
            } else if (!mAdvancedInfo.getIdCardBackImg().exists()) {
                ToastUtils.e(this, "由于您的手机内存不足,无法找到身份证背面照").show();
                return false;
            }
        }
        return true;
    }


    /**
     * 提交数据
     */
    private void submit() {
        showProgress();
        PostFormBuilder builder = OkHttpUtils
                .post()
                .url(NetConst.ID_CARD_UPLOAD)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("idNo", mAdvancedInfo.getIdNo())
                .addParams("idName", mAdvancedInfo.getIdName())
                .addParams("idAddress", mAdvancedInfo.getIdAddress())
                .addParams("idTermBegin", mAdvancedInfo.getIdTermBegin())
                .addParams("idTermEnd", mAdvancedInfo.getIdTermEnd());
        if (mAdvancedInfo.getIdCardBackImg() != null) {
            builder.addFile("idCardBackImg", "idCardBackImg.jpg", mAdvancedInfo.getIdCardBackImg());
        }
        if (mAdvancedInfo.getIdCardFrontImg() != null) {
            builder.addFile("idCardFrontImg", "idCardFrontImg.jpg", mAdvancedInfo.getIdCardFrontImg());
        }

        builder.build()
                .execute(new StringCallback() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        if (checkResonse(response)) {
                            Response house = GsonUtil.changeGsonToBean(response, Response.class);
                            showToast("已提交认证");
                            Intent intent = getIntent();
                            if (IntentFlag.FORGOT_LOGIN_PASSWORD.equals(intent.getStringExtra(IntentFlag.INTENT_NAME))) {
                                ResetPasswordActivity.startAction(AuthenticationActivity.this);
                            } else if (IntentFlag.UPDATE_LOGIN_PASSWORD.equals(intent.getStringExtra(IntentFlag.INTENT_NAME))) {
                                UpdatePasswordActivity.startAction(AuthenticationActivity.this);
                            } else if (IntentFlag.SETTING_WITHDRAW_PASSWORD.equals(intent.getStringExtra(IntentFlag.INTENT_NAME))) {
                                WithdrawPasswordActivity.startAction(AuthenticationActivity.this, intent);
                            }
                            finish();
                        }
                    }
                });
    }

    /**
     * 选择结束日期
     */
    private void showEndPicker() {
        String start = et_validity_start.getText().toString().trim();
        if (TextUtils.isEmpty(start)) {
            ToastUtils.e(this, "请先输入证件有效起始日期").show();
            return;
        }
        String[] arraystart = start.split("-");
        int year = Integer.parseInt(arraystart[0]);
        String month = "-" + arraystart[1] + "-";
        String day = arraystart[2];

        ArrayList<String> ends = new ArrayList<>();
        ends.add(new StringBuffer().append(year + 5).append(month).append(day).toString());
        ends.add(new StringBuffer().append(year + 10).append(month).append(day).toString());
        ends.add(new StringBuffer().append(year + 20).append(month).append(day).toString());
        ends.add("长期");
        CertificateDatePicker picker = new CertificateDatePicker(this, ends, new CertificateDatePicker.OnDurationPickerSelectedListener() {
            @Override
            public void selected(int index, String item) {
                et_validity_end.setText(item);
            }
        });
        picker.show();
    }

    /**
     * 选择开始时间
     */
    private void onStartTime() {
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        StringBuffer sb = new StringBuffer();
                        StringBuffer sb2 = new StringBuffer();
                        sb.append(String.valueOf(year));
                        sb.append(String.format("%02d", (monthOfYear + 1)));
                        sb.append(String.format("%02d", dayOfMonth));
                        sb2.append(String.valueOf(year));
                        sb2.append("-");
                        sb2.append(String.format("%02d", (monthOfYear + 1)));
                        sb2.append("-");
                        sb2.append(String.format("%02d", dayOfMonth));
                        et_validity_start.setText(sb2.toString());
                    }
                },
                2016, 0, 18).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        layout_progress_center.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_idCardFrontImg:
                showActionSheetForFont();
                break;
            case R.id.img_idCardBackImg:
                showActionSheetForBack();
                break;
            case R.id.et_validity_start:
                onStartTime();
                break;
            case R.id.et_validity_end:
                showEndPicker();
                break;
            case R.id.btn_submit:
                if (attempt()) {
                    submit();
                }
                break;
            case R.id.tb_tv_titile:
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
