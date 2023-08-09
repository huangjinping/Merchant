package com.hdfex.merchantqrshow.utils.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.FileCallBack;
import com.hdfex.merchantqrshow.utils.DialogUtils.Effectstype;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.widget.HDAlertDialog;
import com.hdfex.merchantqrshow.widget.progressbar.IconRoundCornerProgressBar;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 更新工具
 */
public class UpdataUtil {
    private Context mContext;
    private String versionUrl;

    //    private SeekBar mProgressBar;
    private TextView mTextView;
    private IconRoundCornerProgressBar ic_progress;

    public UpdataUtil(Context context) {
        this.mContext = context;

    }

    public UpdataUtil(Context context, String versionUrl) {
        this.mContext = context;
        this.versionUrl = versionUrl;
    }

    /**
     * 自定义Dialog
     *
     * @param context
     * @param uri
     */

    private NiftyDialogBuilder dialogAlertBuilder;

    public void showMyDialog(Context context, final String uri, final boolean force) {
        if (dialogAlertBuilder != null && dialogAlertBuilder.isShowing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }


        dialogAlertBuilder = NiftyDialogBuilder.getInstance(context);
        dialogAlertBuilder
                .withTitle("发现新版本")
                .withTitleColor("#FFFFFF")
//                .withDividerColor("#11000000")
                .withMessage("加入新的功能，修复bug")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(context.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(Effectstype.Shake);

        if (!force) {
            dialogAlertBuilder.withButton1Text("取消");
        }

        dialogAlertBuilder.withButton2Text("立刻更新")
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)
//                .setCustomView(R.layout.custom_view, v.getContext())
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAlertBuilder.dismiss();

                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getSDPath() == null) {
                            showAnimDialog(uri);
                            Uri uris = Uri.parse(uri);
                            Intent it = new Intent(Intent.ACTION_VIEW, uris);
                            mContext.startActivity(it);
                        } else {
                            try {
                                getApp(uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        dialogAlertBuilder.dismiss();
                    }
                })
                .show();
    }

    /**
     * 原生对话框
     *
     * @param context
     * @param uri
     */
    public void showDialog(Context context, final String uri) {

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(false).create();

        dialog.setTitle("发现新版本");

        dialog.setButton("立刻更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getSDPath() == null) {
                    showAnimDialog(uri);
                    Uri uris = Uri.parse(uri);
                    Intent it = new Intent(Intent.ACTION_VIEW, uris);
                    mContext.startActivity(it);
                } else {

                    try {
                        getApp(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
            }
        });
        dialog.setButton2("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }


    public void showCustomDialog(final Context context, final String uri, boolean force) {

        final HDAlertDialog dialog = HDAlertDialog.getInstance(context).withMsg("加入新的功能，修复bug")
                .withTitle("版本更新")
                .withbuton("取消", "立刻更新");

        dialog.setTitle("发现新版本");

        if (force) {
            dialog.hideButon1();
        }

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                } else {
                    return false; //默认返回 false
                }
            }
        });


        dialog.withButton2Onclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sdcardExist()) {
                    ToastUtils.makeText(context, "您的存储空间不够").show();
                    return;
                }

                if (getSDPath() == null) {
                    showAnimDialog(uri);
                    Uri uris = Uri.parse(uri);
                    Intent it = new Intent(Intent.ACTION_VIEW, uris);
                    mContext.startActivity(it);
                } else {
                    try {
                        getApp(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                dialog.dismiss();

            }
        });
        dialog.withCancelableOnTouchOutside(false);
        dialog.withButton1Onclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }


    //SD卡是否存在
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();

    }

    AlertDialog progressDialog;

    //显示对话框
    private AlertDialog showAnimDialog(String s) {
        progressDialog = new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .create();
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        progressDialog.getWindow().setContentView(R.layout.widget_dialog_seekbar);
//        mProgressBar = (SeekBar) dialog.getWindow().findViewById(R.id.progressbar);
        ic_progress = (IconRoundCornerProgressBar) progressDialog.getWindow().findViewById(R.id.ic_progress);
        ic_progress.setProgressColor(mContext.getResources().getColor(R.color.btn_press_color));
        ic_progress.setSecondaryProgressColor(mContext.getResources().getColor(R.color.btn_unpress_color));
        ic_progress.setIconBackgroundColor(mContext.getResources().getColor(R.color.btn_unpress_color));
        ic_progress.setProgressBackgroundColor(mContext.getResources().getColor(R.color.transparent_quarter));
        ic_progress.setMax(100);
//        mProgressBar.setMax(100);
        mTextView = (TextView) progressDialog.getWindow().findViewById(R.id.tv_progress);
        progressDialog.getWindow().findViewById(R.id.tv_download_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressDialog.dismiss();
            }
        });
        return progressDialog;
    }

    public void getApp(final String url) throws IOException {
        //TODO 删除测试百度APP地址
        // String urls = "http://p.gdown.baidu.com/560c0095b2cf5705b4dbb5302d7637538516695bd34c37c29e5c30b2e1c104f3d31b4b5ffce20b203498f3285df3e4cbd1d4d02a3f8d5fd91e82a4d2724590df51bed47f4638480e55a0e9fca44123afa4fa2ccf8954f6e83c5cd12becb7485d7b1c9f220ed63d08101ea4712225acc4cd3eb0d91f4e822bc76de78c4dc02b9fc1250aa4f4faf97150e184b97734b2c0a8fdcf156abf30ef19ab83204ee7cc60ae0177a4d9d394115c196cb169674fb76fc479b41a477670e4bf042b21f45a1735b24431eee7a703716540a52c2b3109";
        final AlertDialog dialog = showAnimDialog(url);
        OkHttpUtils.get()
                .url(url)
                .build()
                .writeTimeOut(200000)
                .connTimeOut(200000)
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "fenfujun-shangjiaduan.apk") {

                    @Override
                    public void onBefore(Request request) {

                    }

                    @Override
                    public void inProgress(float progress) {
//                LogUtil.e(UpdataUtil.class.getSimpleName(), String.valueOf((int) (progress * 100)));
//                        mProgressBar.setProgress((int) (progress * 100));
                        ic_progress.setProgress(progress * 100);
                        mTextView.setText(String.format("已下载：%d%% %n", (int) (progress * 100)));
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        call.cancel();
                        // getApp("d");
                        ToastUtils.makeText(mContext, "下载失败，偿试重新下载！", Toast.LENGTH_LONG).show();

                        LogUtil.e(UpdataUtil.class.getSimpleName(), e.toString());

                    }

                    @Override
                    public void onResponse(File response) {
                        //
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(response),
                                "application/vnd.android.package-archive");
                        mContext.startActivity(intent);


                    }
                });

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

    private ReleaseInfo mReleaseInfo;

    public void getVersion() {

        // Execute the request and retrieve the response.
        new Thread(new Runnable() {
            @Override
            public void run() {

                final OkHttpClient client = new OkHttpClient();
                // Create request for remote resource.
                final Request request = new Request.Builder()
                        .url(versionUrl)
                        .build();
                try {
                    Response response = null;
                    response = client.newCall(request).execute();
                    // Deserialize HTTP response to concrete type.
                    ResponseBody body = response.body();
                    Reader charStream = body.charStream();
                    String appString = body.string();
//                    LogUtil.e("UpdataUtil", appString);
                    //   final JSONObject appInfoJson = new JSONObject(appString);

                    try {
                        mReleaseInfo = GsonUtil.changeGsonToBean(appString, ReleaseInfo.class);

                        LogUtil.e("UpdataUtil", mReleaseInfo.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PackageManager pm = mContext.getPackageManager();
                    PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);

                    if (mReleaseInfo != null) {

                        if (mReleaseInfo.getCode() == 0) {
//                            LogUtil.e("UpdataUtil", mReleaseInfo.getResult().getVersionCode() + "线上版本号");
                            //大于
                            if (mReleaseInfo.getResult().getVersionCode() > pi.versionCode) {

                                ((Activity) mContext).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        //是否强制更新
                                        if (mReleaseInfo.getResult().getIsForce() > 0) {
                                            //TODO 修改提示框样式  showDialog(mContext, appInfoJson.getString("URL"));
//                                            showCustomDialog(mContext, mReleaseInfo.getResult().getUrl(), true);
                                            showMyDialog(mContext, mReleaseInfo.getResult().getUrl(), true);
                                        } else {
                                            showMyDialog(mContext, mReleaseInfo.getResult().getUrl(), false);

                                            //TODO 修改提示框样式  showDialog(mContext, appInfoJson.getString("URL"));
//                                            showCustomDialog(mContext, mReleaseInfo.getResult().getUrl(), false);
                                        }
                                    }
                                });
                            }
                        }
                    }
                } catch (IOException e) {
//                    LogUtil.e(UpdataUtil.class.getSimpleName(), e.toString());
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 提交信息
     *
     * @param callBack
     */
    public void getVersionAfter26(final CallBack callBack) {

        // Execute the request and retrieve the response.
        new Thread(new Runnable() {
            @Override
            public void run() {

                final OkHttpClient client = new OkHttpClient();
                // Create request for remote resource.
                final Request request = new Request.Builder()
                        .url(versionUrl)
                        .build();
                try {
                    Response response = null;
                    response = client.newCall(request).execute();
                    // Deserialize HTTP response to concrete type.
                    ResponseBody body = response.body();
                    Reader charStream = body.charStream();
                    String appString = body.string();
//                    LogUtil.e("UpdataUtil", appString);
                    //   final JSONObject appInfoJson = new JSONObject(appString);

                    try {
                        mReleaseInfo = GsonUtil.changeGsonToBean(appString, ReleaseInfo.class);

                        LogUtil.e("UpdataUtil", mReleaseInfo.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PackageManager pm = mContext.getPackageManager();
                    PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);

                    if (mReleaseInfo != null) {

                        if (mReleaseInfo.getCode() == 0) {
//                            LogUtil.e("UpdataUtil", mReleaseInfo.getResult().getVersionCode() + "线上版本号");
                            //大于
                            if (mReleaseInfo.getResult().getVersionCode() > pi.versionCode) {

                                ((Activity) mContext).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (callBack != null) {
                                            callBack.onCall();
                                        }
                                    }
                                });
                            }
                        }
                    }
                } catch (IOException e) {
//                    LogUtil.e(UpdataUtil.class.getSimpleName(), e.toString());
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 更新应用需要您开启应用安装权限
     *
     * @param mContext
     * @param url
     * @param requestCode
     */

    NiftyDialogBuilder dialogPermissionBuilder;

    public void installAlert(final Activity mContext, final String url, final int requestCode) {

        if (dialogAlertBuilder != null && dialogAlertBuilder.isShowing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }
        if (dialogPermissionBuilder != null && dialogPermissionBuilder.isShowing()) {
            return;
        }

        dialogPermissionBuilder = NiftyDialogBuilder.getInstance(mContext);
        dialogPermissionBuilder
                .withTitle("提示")
                .withTitleColor("#FFFFFF")
//                .withDividerColor("#11000000")
                .withMessage("更新应用需要您开启应用安装权限,是否前往开启")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(mContext.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(Effectstype.Shake);
        dialogPermissionBuilder.withButton2Text("去开启")
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPermissionBuilder.dismiss();
                        Intent intent = new Intent(url);
                        mContext.startActivityForResult(intent, requestCode);

                    }
                })
                .show();
    }

    public interface CallBack {
        void onCall();
    }

    /**
     * 检测接口
     */
    public void checkUpdate() {
        if (mReleaseInfo != null) {

            if (mReleaseInfo.getCode() == 0) {
                Log.i("UpdataUtil", mReleaseInfo.getResult().getVersionCode() + "线上版本号");
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //是否强制更新
                        if (mReleaseInfo.getResult().getIsForce() > 0) {
                            //TODO 修改提示框样式  showDialog(mContext, appInfoJson.getString("URL"));
                            showMyDialog(mContext, mReleaseInfo.getResult().getUrl(), true);
                        } else {
                            //TODO 修改提示框样式  showDialog(mContext, appInfoJson.getString("URL"));
                            showMyDialog(mContext, mReleaseInfo.getResult().getUrl(), false);
                        }
                    }
                });
            }
        }
    }
}
