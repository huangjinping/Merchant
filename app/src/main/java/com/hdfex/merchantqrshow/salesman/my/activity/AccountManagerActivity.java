package com.hdfex.merchantqrshow.salesman.my.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfo;
import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.AccountManagerContract;
import com.hdfex.merchantqrshow.mvp.presenter.AccountManagerPresenter;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GlideCircleTransform;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.camera.EasyImage;
import com.hdfex.merchantqrshow.utils.camera.compress.FileImageUtils;
import com.hdfex.merchantqrshow.utils.camera.easyphotopicker.DefaultCallback;
import com.hdfex.merchantqrshow.utils.camera.easyphotopicker.EasyImageConfig;
import com.hdfex.merchantqrshow.widget.picker.DatePicker;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;

import okhttp3.Call;
import rx.functions.Action1;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 这个界面改成个人信息了
 */
public class AccountManagerActivity extends BaseActivity implements View.OnClickListener, AccountManagerContract.View {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView tv_account_number;
    private TextView btn_quit;
    private TextView txt_left_name;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private ImageView img_user_pic;
    private String catchpath;
    private User user;
    private TextView txt_username;
    private TextView txt_phone;
    private TextView txt_authentication;
    private TextView txt_desc;
    private AccountManagerContract.Presenter presenter;
    private TextView txt_induction_time;
    private RxPermissions rxPermissions;
    private TextView txt_bussinessName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        presenter = new AccountManagerPresenter();
        presenter.attachView(this);
        catchpath = getCacheDir().getAbsolutePath();
        user = UserManager.getUser(this);
        rxPermissions = RxPermissions.getInstance(this);

        initView();
        initDate();
        setIconByfile();
    }

    /**
     * 初始化数据
     */
    private void setViewbyDate() {
        txt_username.setText("");
        txt_phone.setText("");
        tv_account_number.setText("");

        if (UserManager.isLogin(this)) {
            User user = UserManager.getUser(this);
            if (!TextUtils.isEmpty(user.getName())) {
                txt_username.setText(user.getName());
            }
            if (!TextUtils.isEmpty(user.getRemark())) {
                tv_account_number.setText(user.getRemark());
            }
            if (!TextUtils.isEmpty(user.getPhone())) {
                txt_phone.setText(user.getPhone());
            }
            if (!TextUtils.isEmpty(user.getBussinessName())) {
                txt_bussinessName.setText(user.getBussinessName());
            }


        }
    }

    private void initDate() {
        tb_tv_titile.setText("个人资料");
        setViewbyDate();

    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tv_account_number = (TextView) findViewById(R.id.tv_account_number);
        btn_quit = (TextView) findViewById(R.id.btn_quit);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        img_user_pic = (ImageView) findViewById(R.id.txt_user_pic);
        img_user_pic.setOnClickListener(this);
        btn_quit.setOnClickListener(this);
        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_username.setOnClickListener(this);
        txt_phone = (TextView) findViewById(R.id.txt_phone);
        txt_phone.setOnClickListener(this);
        txt_authentication = (TextView) findViewById(R.id.txt_authentication);
        txt_authentication.setOnClickListener(this);
        txt_desc = (TextView) findViewById(R.id.txt_desc);
        txt_desc.setOnClickListener(this);
        txt_induction_time = (TextView) findViewById(R.id.txt_induction_time);
        txt_induction_time.setOnClickListener(this);
        txt_bussinessName = (TextView) findViewById(R.id.txt_bussinessName);
        txt_bussinessName.setOnClickListener(this);
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
    protected void onResume() {
        super.onResume();
        loadUserInfo();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_quit:
                if (UserManager.isLogin(this)) {
                    UserManager.logout(this);
                    ToastUtils.makeText(this, "退出成功").show();
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_user_pic:
                getPic();
                break;
            case R.id.txt_authentication:
                AuthenticationActivity.startAction(this);
                break;

            case R.id.txt_induction_time:
                getTime(txt_induction_time);
                break;


        }
    }

    /**
     * 下载数据
     */
    private void loadUserInfo() {
        showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .url(NetConst.ID_CARD_QUERY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {

                try {
                    if (checkResonse(response)) {
                        AdvancedInfoResponse advancedInfoResponse = GsonUtil.changeGsonToBean(response, AdvancedInfoResponse.class);
                        AdvancedInfo result = advancedInfoResponse.getResult();
                        if (result != null) {
                            if (!TextUtils.isEmpty(result.getEndDate())) {
                                txt_desc.setText(Html.fromHtml(result.getDesc()));
                            }
                            if (!TextUtils.isEmpty(result.getEndDate()) && !TextUtils.isEmpty(result.getDesc())) {
                                txt_desc.setText(Html.fromHtml(result.getDesc() + "<font color='red'>" + result.getEndDate() + "</font>"));
                            }
                            txt_authentication.setCompoundDrawables(null, null, null, null);
                            txt_authentication.setEnabled(true);

                            if (!TextUtils.isEmpty(result.getAuthStatus())) {
                                if (AdvancedInfo.AUTH_STATUS_NO.equals(result.getAuthStatus())) {
                                    txt_authentication.setText("去认证");
                                } else {
                                    if (!TextUtils.isEmpty(result.getIdName())) {
                                        txt_authentication.setEnabled(false);
                                        txt_authentication.setText(StringUtils.getStarString(result.getIdName(), 0, result.getIdName().length() - 1));
                                    }
                                }
                            } else {
                                txt_authentication.setText("去认证");
                            }
//                            if (AdvancedInfo.AUTH_STATUS_NO.equals(result.getAuthStatus())) {
//                                txt_authentication.setText("去认证");
//                            } else if (AdvancedInfo.AUTH_STATUS_ING.equals(result.getAuthStatus())) {
//                                txt_authentication.setText("认证中");
//                                txt_authentication.setEnabled(false);
//                            } else if (AdvancedInfo.AUTH_STATUS_SUCCESS.equals(result.getAuthStatus())) {
//                                txt_authentication.setText("认证成功");
//                                txt_authentication.setEnabled(false);
//                            } else if (AdvancedInfo.AUTH_STATUS_EIRR.equals(result.getAuthStatus())) {
//                                txt_authentication.setText("认证失败");
//                            }


                            if (!TextUtils.isEmpty(result.getEntryDate())) {
                                txt_induction_time.setText(result.getEntryDate());
                            }

                            Glide.with(AccountManagerActivity.this).load(result.getAvatar()).bitmapTransform(new GlideCircleTransform(AccountManagerActivity.this)).placeholder(R.mipmap.ic_combined_shape).error(R.mipmap.ic_combined_shape).into(img_user_pic);
                        }
                    }
                } catch (Exception e) {
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

    /**
     * 获取图片接口
     */
    private void getPic() {
        if (!sdcardExist()) {
            ToastUtils.makeText(this, "未检测到SD卡").show();
            return;
        }
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    EasyImage.openChooser(AccountManagerActivity.this, "选择图片来源");
                } else {
                    showPermissDeniedAlert();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case EasyImageConfig.REQ_PICK_PICTURE_FROM_DOCUMENTS:
            case EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY:
            case EasyImageConfig.REQ_SOURCE_CHOOSER:
            case EasyImageConfig.REQ_TAKE_PICTURE:
                processEasyImage(requestCode, resultCode, data);
                break;
            default:
                break;
        }
    }

    /**
     * 处理图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void processEasyImage(int requestCode, int resultCode, Intent data) {
        EasyImage.handleActivityResult(requestCode, resultCode, data, AccountManagerActivity.this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source) {

                ToastUtils.i(AccountManagerActivity.this, "拍照过程中遇到问题" + e.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source) {

                Log.d("okhttp", "======>>>>" + "成功onImagePicked" + imageFile.toString());
                Luban.with(AccountManagerActivity.this)
                        .load(imageFile)                     //传人要压缩的图片
                        .setCompressListener(new OnCompressListener() { //设置回调
                            @Override
                            public void onStart() {
                                //  压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                user.setFile(file);
                                UserManager.saveUser(AccountManagerActivity.this, user);
                                presenter.avatarUpload(user, file);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }).launch();    //启动压缩
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source) {
                ToastUtils.i(AccountManagerActivity.this, "拍照过程中遇到问题onCanceled", Toast.LENGTH_LONG).show();
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(AccountManagerActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    /**
     * 中间
     *
     * @param
     */
    private void setIconByfile() {
        try {
            boolean login = UserManager.isLogin(this);

            Bitmap bitmap = FileImageUtils.getfileBitmap(user.getFile().getAbsolutePath(), this);
            img_user_pic.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();


    }

    /**
     * 获取图片
     *
     * @param edittext
     */
    private void getTime(final TextView edittext) {
        DatePicker datePicker = new DatePicker(this);
        datePicker.setOnDateSelectListener(new DatePicker.OnDateSelectListener() {
            @Override
            public void onSelect(String year, String month, String day) {
                StringBuffer sb = new StringBuffer();
                sb.append(year);
                sb.append("-");
                sb.append(String.format("%02d", Integer.parseInt(month)));
                sb.append("-");
                sb.append(String.format("%02d", Integer.parseInt(day)));
                presenter.saveEntryDate(user, sb.toString(), edittext);
            }
        });
        datePicker.show();

    }

    @Override
    public void returnAvatarUpload(File file) {
        Glide.with(AccountManagerActivity.this).load(file).bitmapTransform(new GlideCircleTransform(AccountManagerActivity.this)).placeholder(R.mipmap.ic_combined_shape).error(R.mipmap.ic_combined_shape).into(img_user_pic);
    }

    @Override
    public void returnEntryDate(String entryDate) {

    }
}
