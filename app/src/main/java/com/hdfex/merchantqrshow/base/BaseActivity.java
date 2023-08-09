package com.hdfex.merchantqrshow.base;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.salesman.my.activity.LoginActivity;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.NetWorkTool;
import com.hdfex.merchantqrshow.utils.RoleUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.statusbar.SystemBarTintManager;
import com.hdfex.merchantqrshow.widget.HDAlertDialog;
import com.hdfex.merchantqrshow.widget.LoadingDialog;
import com.hdfex.merchantqrshow.widget.RedPackageDialog;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.json.JSONObject;

import rx.functions.Action1;

public class BaseActivity extends AppCompatActivity {
    protected static final int REQUEST_CODE_UNKNOWN_APP = 2001;
    private LoadingDialog show;
    public static SystemBarTintManager tintManager;


    private RedPackageDialog redPackageDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.hjq.toast.ToastUtils.init(getApplication());


//        initStatusBarColor(R.color.blue_light);
//        show = KProgressHUD.create(this)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("加载中...")
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f);
//        .setDetailsLabel("Downloading data")
        PushAgent.getInstance(this).onAppStart();
        show = new LoadingDialog(this);
        redPackageDialog = new RedPackageDialog(this);

        try {
            String clazzName = getClass().getSimpleName();
            if (clazzName.equals("IndexActivity") || clazzName.equals("ManagerIndexActivity") || clazzName.equals("ApliyMainActivity") || clazzName.equals("AdminIndexActivity")) {
                if (!com.hjq.toast.ToastUtils.isNotificationEnabled(this)) {
                    showAppNotificationsPermissDeniedAlert();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void initHomeRxPermissions() {
        RxPermissions rxPermissions = RxPermissions.getInstance(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (!aBoolean) {
                    showPermissDeniedAlert();
                }
            }
        });
    }

    public void initStatusBarColor(int colorRes) {

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (tintManager == null) {
                tintManager = new SystemBarTintManager(this);
            }
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorRes);
        }


    }

    /**
     * 显示进度条
     */
    public void showProgress() {
        if (this.isFinishing()) {
            return;
        }
        if (show == null) {
            return;
        }
        if (show.isShowing()) {
            return;
        }

        try {
            show.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消进度条
     */
    public void dismissProgress() {
        if (this.isFinishing()) {
            return;
        }
        if (show == null) {
            return;
        }
        show.dismiss();
    }

    /**
     * 需要去手机设置里面设置权限fuck
     * 当用户禁止了权限提示之后的操作
     */

    protected void showPermissDeniedAlert() {

        if (this == null) {
            return;
        }
        if (this.isFinishing()) {
            return;
        }
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("由于您开启的权限过少，从而影响到使用，请设置权限")
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
                        finish();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:" + getPackageName()));
                        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(myAppSettings);
                        BaseActivity.this.finish();
                    }
                })
                .show();
    }


    /**
     * 提醒用户开启通知权限
     */

    protected void showAppNotificationsPermissDeniedAlert() {

        if (this == null) {
            return;
        }
        if (this.isFinishing()) {
            return;
        }
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("请开启您的通知权限否则将影响您接收App的提示信息")
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
                        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:" + getPackageName()));
                        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(myAppSettings);
                        BaseActivity.this.finish();
                    }
                })
                .show();
    }


    /**
     * 服务器或者数据异常的时候
     *
     * @param e
     */
    public void showWebEirr(Exception e) {
        if (e != null) {
            showToast("" + e.getMessage());
            return;
        }
        showWebEirr();
    }

    public void showWebEirr() {
        showToast("系统维护中，请稍后再试...");
    }

    /**
     * Toast message
     *
     * @param msg
     */
    public void showToast(String msg) {

        if (!TextUtils.isEmpty(msg)) {
            if (msg.toLowerCase().contains("<html>")) {
                ToastUtils.makeText(this, "服务异常！").show();
            } else {
                ToastUtils.makeText(this, msg).show();
            }
        }
    }

    /**
     *
     */
    public boolean checkResonse(String res) {
        if (TextUtils.isEmpty(res)) {
            showWebEirr();
            return false;
        }
        try {
            JSONObject json = new JSONObject(res);
            int code = json.optInt("code", -10000);
            if (code == -10000) {
                showWebEirr();
                return false;
            }
            switch (code) {
                case 0:
                    return true;
                case -1:
                    break;
                case -2:
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    break;
                case -3:
                    break;
                case -4:
                    break;
                case -100:
                    break;
            }
            showToast(json.optString("message"));

        } catch (Exception e) {
            showWebEirr(e);
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 判断需不需要弹出保留活动
     */
    protected void getAlwaysFinishAlert() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return;
        }

        int alwaysFinish = Settings.Global.getInt(getContentResolver(),
                Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0);
        if (alwaysFinish == 1) {
            final HDAlertDialog dialog1 = HDAlertDialog.getInstance(this)
                    .withTitle("提示")
                    .withMsg("由于您已开启'不保留活动',导致部分功能无法正常使用.我们建议您点击左下方'设置'按钮,在'开发者选项'中关闭'不保留活动'功能.");
            dialog1.withButton1Onclick(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               dialog1.dismiss();
                                               Intent intent = new Intent(
                                                       Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                                               startActivity(intent);
                                           }
                                       }
            ).withButton2Onclick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });

            dialog1.show();
        }


    }

    /**
     * 判断网络状态
     *
     * @return
     */
    protected boolean connect() {

        if (NetWorkTool.isNetworkAvailable(this)) {
            return true;
        } else {
            showToast("请确定联网后操作");
            return false;
        }

    }

    protected boolean isLogin() {
        if (UserManager.isLogin(this)) {
            return true;
        } else {
            Intent mIntent = new Intent(this, LoginActivity.class);
            startActivity(mIntent);
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        resetTime();

//        LogUtil.d("zbt","进入"+this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
//        LogUtil.d("zbt","离开"+this.getClass().getSimpleName());
    }

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    protected boolean sdcardExist() {
        boolean hasSdCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        return hasSdCard;
    }


    private final int SHOW_ANOTHER_ACTIVITY = 1234;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub

        resetTime();
        return super.dispatchTouchEvent(ev);
    }

    private void resetTime() {
        // TODO Auto-generated method stub
//        mHandler.removeMessages(SHOW_ANOTHER_ACTIVITY);//從消息隊列中移除
//        Message msg = mHandler.obtainMessage(SHOW_ANOTHER_ACTIVITY);
//        mHandler.sendMessageDelayed(msg, 1000*5);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.what == SHOW_ANOTHER_ACTIVITY) {

                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeMessages(SHOW_ANOTHER_ACTIVITY);//從消息隊列中移除
    }

    /**
     * 提交数据
     *
     * @param phone
     * @param smsBody
     */
    protected void showAlertForMessage(final String phone, final String smsBody) {
        final HDAlertDialog dialog1 = HDAlertDialog.getInstance(this)
                .withTitle("提示")
                .withMsg("呼叫：" + phone);
        dialog1.withButton1Onclick(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           dialog1.dismiss();
                                           Uri smsToUri = Uri.parse("smsto:" + phone);
                                           Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                                           intent.putExtra("sms_body", smsBody);
                                           startActivity(intent);
                                       }
                                   }
        ).withButton2Onclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    /**
     * 打电话
     *
     * @param phone
     */
    protected void showAlertForPhone(final String phone) {
        final HDAlertDialog dialog1 = HDAlertDialog.getInstance(this)
                .withTitle("提示")
                .withMsg("呼叫：" + phone);
        dialog1.withButton1Onclick(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           dialog1.dismiss();
                                           Intent intent = new Intent(Intent.ACTION_DIAL);
                                           Uri data = Uri.parse("tel:" + phone);
                                           intent.setData(data);
                                           startActivity(intent);
                                       }
                                   }
        ).withButton2Onclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.show();
    }

    /**
     * 弹出红包
     *
     * @param redPackage
     */
    public void showRedPackageDialog(RedPackage redPackage) {
        if (redPackageDialog == null) {
            return;
        }
        if (redPackageDialog.isShowing()) {
            return;
        }
        try {
            redPackageDialog.setRedPackage(redPackage);
            redPackageDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出登录
     */
    public void logout() {
        if (UserManager.isLogin(this)) {
            ToastUtils.makeText(this, "退出成功").show();
            UserManager.logout(this);
            RoleUtils.startActionAndIndex(this, UserManager.getUser(this), 3);


//            Intent intent = new Intent(this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
        }
        finish();
    }


//    public void  updateApkPermission(){
//        if (Build.VERSION.SDK_INT >= 26) {
//            //来判断应用是否有权限安装apk
//            boolean installAllowed= getPackageManager().canRequestPackageInstalls();
//            //有权限
//            if (installAllowed) {
//                //安装apk
//                install(apkPath);
//            } else {
//                //无权限 申请权限
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_APK_REQUESTCODE);
//            }
//        } else {
//            install(apkPath);
//        }
//
//    }


    @Override
    protected void onRestart() {
        super.onRestart();
        com.hjq.toast.ToastUtils.init(getApplication());

    }
}
