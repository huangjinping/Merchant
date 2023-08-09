package com.hdfex.merchantqrshow.admin.my.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.admin.bam.CreateQRCode;
import com.hdfex.merchantqrshow.utils.ShotUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * author Created by harrishuang on 2017/12/29.
 * email : huangjinping@hdfex.com
 */

public class ReceiptQrCodeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView img_qrcode;
    private LinearLayout layout_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_qr_code);
        initView();
        Intent intent = getIntent();
        Serializable serializable = intent.getSerializableExtra(CreateQRCode.class.getSimpleName());
        if (serializable != null) {
            CreateQRCode result = (CreateQRCode) serializable;
            Glide.with(getApplicationContext())
                    .load(result.getQrcodeUrl())
                    .placeholder(R.mipmap.ic_defoult)
                    .error(R.mipmap.ic_defoult)
                    .into(img_qrcode);
        }
    }


    /**
     * 提交数据
     *
     * @param view
     */
    public void onComplate(View view) {


        /**
         * 用户点击的速度特别快的时候会主线程内存溢出；哈哈哈哈
         */
        try {
            Bitmap bitmap = ShotUtils.shotMultiView(layout_root, new View[]{layout_root});
            saveImageToGalleryNew(this, bitmap);

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
//                                shareImage((Activity) context, file);


                            }

                            @Override
                            public void onError(Throwable e) {
                                //  当压缩过去出现问题时调用
                            }
                        }).launch();    //启动压缩

            }
        });
    }


    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        img_qrcode = (ImageView) findViewById(R.id.img_qrcode);
        img_back.setOnClickListener(this);
        tb_tv_titile.setText("收款码");
        layout_root = (LinearLayout) findViewById(R.id.layout_root);
        layout_root.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    /**
     * 开始界面
     *
     * @param context
     * @param result
     */
    public static void startAction(Context context, CreateQRCode result) {
        Intent intent = new Intent(context, ReceiptQrCodeActivity.class);
        intent.putExtra(CreateQRCode.class.getSimpleName(), result);
        context.startActivity(intent);
    }
}
