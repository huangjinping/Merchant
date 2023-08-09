package com.hdfex.merchantqrshow.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.AdditionalInfo;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.AdditionalResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.view.QRCodeView;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.FileUtils;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ShotUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.widget.ActionSheet;
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
 * Created by harrishuang on 2017/2/15.
 */

public class QrCodePresenter extends BasePresenter<QRCodeView> {
    private AdditionalInfo info;

    /**
     * @param user
     */
    public void onModify(User user) {
        if (info != null) {
            getmMvpView().addPassWordFragmentToStack(info);
        } else {
            additional(user);
        }
    }

    /**
     * 加载首付方式和分期月份
     */
    private void additional(User user) {

        OkHttpUtils.post()
                .url(NetConst.ADDITIONAL_DATA)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("bussinessId", user.getBussinessId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showWebEirr(e);
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        getmMvpView().showProgress();
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        getmMvpView().dismissProgress();
                    }

                    @Override
                    public void onResponse(String response) {


                        try {
                            boolean b = getmMvpView().checkResonse(response);
                            if (b) {
                                AdditionalResponse additionalResponse = GsonUtil.changeGsonToBean(response, AdditionalResponse.class);
                                info = additionalResponse.getResult();
                                getmMvpView().addPassWordFragmentToStack(info);

//                                    if (info.getPayTypeList() != null) {
//                                        downpaymentLists = info.getPayTypeList();
//                                    }
//                                    if (info.getDurationList() != null) {
//                                        durationLists = info.getDurationList();
//                                    }
                            }
                        } catch (Exception e) {
                            getmMvpView().showWebEirr(e);
                            e.printStackTrace();
                        }
                    }
                });

    }


    /**
     * @param context
     * @param fragmentManager
     */
    public void showActionSheet(final Context context, FragmentManager fragmentManager, final LinearLayout layout_rootview, final android.view.View[] views) {
        final String[] dataArr = {"保存到相册", "微信分享"};
        ActionSheet.createBuilder(context, fragmentManager)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {

                if (index == 0) {
                    getmMvpView().saveCurrentImage();

                } else {
                    shotAllView(context, layout_rootview, views);
                }
            }
        }).show();
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getmMvpView().dismissProgress();

        }
    };

    public void startSaveImage() {
        getmMvpView().showProgress();
        Message message = handler.obtainMessage();
        handler.sendMessageDelayed(message, 1000 * 3);
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


    private File savefile;


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
            saveImageToGalleryNew(context, bitmap);


        } catch (Exception e) {
            e.printStackTrace();
            /**
             * 是不是应该提示给用户点啥
             */
        }
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

}
