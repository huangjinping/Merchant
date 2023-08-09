package com.hdfex.merchantqrshow.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderDetails;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderDetailsResponse;
import com.hdfex.merchantqrshow.bean.salesman.plan.PlanDetail;
import com.hdfex.merchantqrshow.bean.salesman.plan.PlanResponse;
import com.hdfex.merchantqrshow.mvp.contract.OrderDetailsContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.order.activity.PlanDetailsActivity;
import com.hdfex.merchantqrshow.utils.FileUtils;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ShotUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by harrishuang on 2017/4/21.
 */

public class OrderDetailsPresenter extends OrderDetailsContract.Persenter {

    private File savefile;


    @Override
    public void shotAllView(Context context, LinearLayout layout_rootview, View[] views) {

        if (savefile != null) {
            shareImage((Activity) context, savefile);

            return;
        }


        /**
         * 用户点击的速度特别快的时候会主线程内存溢出；哈哈哈哈
         */
        try {
            Bitmap bitmap = ShotUtils.shotMultiView(layout_rootview, views);
            getmMvpView().returnShotBitmap(bitmap);
            saveImageToGalleryNew(context, bitmap);


        } catch (Exception e) {
            e.printStackTrace();
            /**
             * 是不是应该提示给用户点啥
             */
        }
    }


    /**
     * @param context
     * @param bmp
     */
    public void saveImageToGalleryNew(final Context context, final Bitmap bmp) {
        if (bmp == null) {
            ToastUtils.d(context, "保存出错了...").show();
            return;
        }
        Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
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
                    subscriber.onError(e);
                    e.printStackTrace();
                } catch (IOException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                } catch (Exception e) {
                    subscriber.onError(e);
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
                subscriber.onNext(file);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<File>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.d(context, "截图处理失败").show();
            }

            @Override
            public void onNext(File file) {
                ToastUtils.d(context, "保存成功了...").show();
//                shareImage((Activity) context,file);

                getmMvpView().returnShotFile(file);

                Luban.with(context)
                        .load(file)                     //传人要压缩的图片
                        .setCompressListener(new OnCompressListener() { //设置回调

                            @Override
                            public void onStart() {
                                //  压缩开始前调用，可以在方法内启动 loading UI

                            }

                            @Override
                            public void onSuccess(File file) {
//    压缩成功后调用，返回压缩后的图片文件
                                shareImage((Activity) context, file);


                            }

                            @Override
                            public void onError(Throwable e) {
                                //  当压缩过去出现问题时调用
                            }
                        }).launch();    //启动压缩

            }
        });
    }

    /**
     * 分享Image
     *
     * @param context
     * @param file
     */
    public void shareImage(final Activity context, final File file) {
        savefile = file;
        ShareAction mShareAction = new ShareAction(context).setDisplayList(
                SHARE_MEDIA.WEIXIN)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
//                        SnsPlatform snsPlatform = SHARE_MEDIA.WEIXIN.toSnsPlatform();

                        Log.d("okhttp", file.getAbsolutePath().toString());

                        Bitmap bitmap = FileUtils.getfileBitmap(file.getAbsolutePath());
                        UMImage imagelocal = new UMImage(context, bitmap);
//                        imagelocal.setThumb(new UMImage(context, file));
                        imagelocal.setThumb(new UMImage(context, bitmap));

                        new ShareAction(context).withMedia(imagelocal)
                                .setPlatform(snsPlatform.mPlatform)
                                .setCallback(new UMShareListener() {
                                    @Override
                                    public void onStart(SHARE_MEDIA share_media) {
                                    }

                                    @Override
                                    public void onResult(SHARE_MEDIA share_media) {
                                        ToastUtils.d(context, "分享成功", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                        ToastUtils.d(context, "分享错误", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancel(SHARE_MEDIA share_media) {
                                        ToastUtils.d(context, "分享取消", Toast.LENGTH_SHORT).show();
                                    }
                                }).share();
                    }
                });
        mShareAction.open();
    }


    @Override
    public void loadOrderDetails(String applyId, User user) {
        OkHttpUtils
                .post()
                .url(NetConst.ORDER_DETAIL)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("applyId", applyId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        getmMvpView().dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        getmMvpView().showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showToast("服务异常");
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
//                            LogUtil.e("zbt1",response);

                            if (getmMvpView().checkResonse(response)) {
                                OrderDetailsResponse orderDetailsResponse = GsonUtil.changeGsonToBean(response, OrderDetailsResponse.class);
                                OrderDetails orderDetails = orderDetailsResponse.getResult();

                                getmMvpView().returnDetails(orderDetails);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getmMvpView().showToast("服务异常");
                        }
                    }
                });

    }

    @Override
    public void loadRepayDetail(final Context context, User user, final String applyId) {
        OkHttpUtils
                .post()
                .url(NetConst.GET_REPAY_DETAIL)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("applyId", applyId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        getmMvpView().dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        getmMvpView().showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showToast("服务异常");
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                PlanResponse planResponse = GsonUtil.changeGsonToBean(response, PlanResponse.class);
                                PlanDetail planDetail = planResponse.getResult();
                                if (planDetail == null) {
                                    getmMvpView().showToast("服务异常");
                                    return;
                                }
                                PlanDetailsActivity.startActionModel(context, applyId, planDetail);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getmMvpView().showToast("服务异常");
                        }
                    }
                });

    }
}
