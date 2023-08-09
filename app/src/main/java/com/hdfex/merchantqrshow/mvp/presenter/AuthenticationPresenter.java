package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.AuthenticationContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.camera.compress.Util;
import com.megvii.livenesslib.util.ConUtil;

import java.io.File;

import okhttp3.Call;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.hdfex.merchantqrshow.utils.FileUtils.saveBitmap;

/**
 * author Created by harrishuang on 2017/9/11.
 * email : huangjinping@hdfex.com
 */

public class AuthenticationPresenter extends AuthenticationContract.Presenter {

    @Override
    public void parseCardInfo(final Context context, final Bitmap bitmap, final String type, User user) {
        if (bitmap == null) {
            return;
        }

        if (getmMvpView() != null) {
            getmMvpView().startParse();
        }
//        getmMvpView().showToast("开始处理");


        Observable<File> observable = Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {


                Bitmap temp = Util.zoomBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight());
                subscriber.onNext(saveBitmap(context, temp));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Subscriber<File>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

                if (getmMvpView() != null) {
                    getmMvpView().showToast("请清理足够的内存空间！！！");
                    getmMvpView().stopParse();
                }


            }

            @Override
            public void onNext(File file) {
                /**
                 * 上传图片
                 */
                upLoadHoldImag(context, file, type);
            }
        });
    }

    @Override
    public void upLoadHoldImag(final Context context, final File imageFile, final String type) {

        if (getmMvpView() != null) {
            getmMvpView().startParse();
        }
        Luban.with(context)
                .load(imageFile)                     //传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        //  压缩开始前调用，可以在方法内启动 loading UI
//                        getmMvpView().showToast("正在处理");
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (getmMvpView() != null) {
                            getmMvpView().stopParse();
                        }
                        try {
                            if (imageFile.exists()) {
                                imageFile.delete();
                            }
                            //  压缩成功后调用，返回压缩后的图片文件
                            //TODO 上传图片
                            User user = UserManager.getUser(context);

                            Log.d("okhttp", file.getAbsolutePath());
                            Log.d("hjp", file.getAbsolutePath());
                            System.out.println(file.getAbsolutePath());

                            if (getmMvpView().IMAGE_TYPE_FONT.equals(type)) {
                                upLoadAdvancedinfo(user, "idCardFrontImg", "idCardFrontImg.jpg", file, type);
                            } else if (getmMvpView().IMAGE_TYPE_BACK.equals(type)) {
                                upLoadAdvancedinfo(user, "idCardBackImg", "idCardBackImg.jpg", file, type);
                            } else {
                                upLoadAdvancedinfo(user, "idCardFaceImg", "idCardFaceImg.jpg", file, type);
                            }
//                            getmMvpView().showToast("处理完成");
                        } catch (Exception e) {
                            if (getmMvpView() != null) {
                                getmMvpView().showToast("图片处理异常");
                            }
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getmMvpView() != null) {
                            getmMvpView().stopParse();
                            //  当压缩过去出现问题时调用
                            getmMvpView().showToast("图片处理异常");
                        }
                    }
                }).launch();    //启动压缩
    }

    @Override
    public void upLoadAdvancedinfo(User user, String name, String fileName, File file, String type) {
        if (getmMvpView() != null) {
            getmMvpView().returnUpLoadSuccess(file, type);
        }

    }

    @Override
    public void loadIdCard(User user) {
        if (getmMvpView() == null) {
            return;
        }

        getmMvpView().showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .url(NetConst.ID_CARD_QUERY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                if (getmMvpView() == null) {
                    return;
                }
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView() == null) {
                    return;
                }
                if (getmMvpView().checkResonse(response)) {
                    AdvancedInfoResponse house = GsonUtil.changeGsonToBean(response, AdvancedInfoResponse.class);
                    getmMvpView().returnCardInfo(house.getResult());
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                if (getmMvpView() == null) {
                    return;
                }
                getmMvpView().dismissProgress();
            }
        });
    }

    @Override
    public void parseFaceIdCardSDKResult(final Context context, User user, Intent data) {

        final byte[] idcardImgData = data.getByteArrayExtra("idcardImg");
        final int side = data.getIntExtra("side", 0);
        if (getmMvpView() != null) {
            getmMvpView().startParse();
        }
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String path = ConUtil.saveJPGFile(context, idcardImgData, "");
                subscriber.onNext(path);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (getmMvpView() != null) {
                    getmMvpView().showToast("请清理足够的内存空间！！！");
                    getmMvpView().stopParse();
                }
            }


            @Override
            public void onNext(String path) {
                if (getmMvpView() != null) {
                    getmMvpView().stopParse();
                }
                /**
                 * 上传图片
                 */
                final File file = new File(path);
                if (side == 0) {
                    if (getmMvpView() != null) {
                        getmMvpView().returnUpLoadSuccess(file, getmMvpView().IMAGE_TYPE_FONT);
                    }
                } else {
                    if (getmMvpView() != null) {
                        getmMvpView().returnUpLoadSuccess(file, getmMvpView().IMAGE_TYPE_BACK);
                    }
                }
            }
        });
    }
}
