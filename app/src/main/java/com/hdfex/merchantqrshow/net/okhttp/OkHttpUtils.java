package com.hdfex.merchantqrshow.net.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.hdfex.merchantqrshow.net.okhttp.builder.GetBuilder;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFileBuilder;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostStringBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.Callback;
import com.hdfex.merchantqrshow.net.okhttp.cookie.SimpleCookieJar;
import com.hdfex.merchantqrshow.net.okhttp.https.HttpsUtils;
import com.hdfex.merchantqrshow.net.okhttp.request.RequestCall;
import com.hdfex.merchantqrshow.net.okhttp.utils.FailureToConnectException;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class OkHttpUtils {
    public static final String TAG = "OkHttpUtils";
    public static final long DEFAULT_MILLISECONDS = 30000;
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Context context;
    private boolean debug;
    private String tag;
    private OkHttpUtils() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //cookie enabled
        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        mDelivery = new Handler(Looper.getMainLooper());


        if (true) {
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }

        mOkHttpClient = okHttpClientBuilder.build();


    }

    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public OkHttpUtils debug(String tag) {
        debug = true;
        this.tag = tag;
        return this;
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (debug) {
            if (TextUtils.isEmpty(tag)) {
                tag = TAG;
            }
            Log.d(tag, "{method:" + requestCall.getRequest().method() + ", detail:" + requestCall.getRequest().toString() + "}");
        }
        Log.d(tag, "{method:" + requestCall.getRequest().method() + ", detail:" + requestCall.getRequest().toString() + "}");

        Log.i(OkHttpUtils.class.getSimpleName(), "{method:" + requestCall.getRequest().method() + ", detail:" + requestCall.getRequest().toString() + "}");

        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {


                try {
                    Log.i(OkHttpUtils.class.getSimpleName(), "onFailure" + e.toString());
                    if (e.toString().contains("Failed to connect")) {
                        try {
                            new FailureToConnectException();
                        } catch (Exception ex) {
                            sendFailResultCallback(call, ex, finalCallback);
                            Log.i(OkHttpUtils.class.getSimpleName(), "onFailure" + ex.toString());
                        }
                        finalCallback.onAfter();
                    }
                    if (e.toString().contains("Canceled")) {
                        // 取消请求  RxEventBus.getInstance().post();
                        Log.i(OkHttpUtils.class.getSimpleName(), "onFailure" + e.toString());
                        finalCallback.onAfter();
                    } else if (e.toString().contains("SocketTimeoutException")) {
                        // 网络超时 在 BaseActivity 中处理
//                    RxEventBus.getInstance().post("提示：网络连接超时");
                        try {
                            new MyTimeOutException();
                        } catch (Exception ex) {
                            sendFailResultCallback(call, ex, finalCallback);
                            Log.i(OkHttpUtils.class.getSimpleName(), "onFailure" + ex.toString());
                        }
                        finalCallback.onAfter();
                    } else if (e.toString().contains("SocketException")) {
                        // 网络超时 在 BaseActivity 中处理
//                    RxEventBus.getInstance().post("提示：网络连接异常");
                        finalCallback.onAfter();
                    } else if (e.toString().contains("ConnectException")) {
                        // 网络超时 在 BaseActivity 中处理
//                    RxEventBus.getInstance().post("提示：网络连接异常");
                        finalCallback.onAfter();
                    } else {
                        //取消请求时不发送异常
                        sendFailResultCallback(call, e, finalCallback);
                    }
                }catch (Exception e1){
                    e1.printStackTrace();
                }

            }

            @Override
            public void onResponse(final Call call, final Response response) {

                Log.i(OkHttpUtils.class.getSimpleName(), "onResponse" + response.code());

                if (response.code() >= 400 && response.code() <= 599) {
                    try {
                        sendFailResultCallback(call, new RuntimeException(response.body().string()), finalCallback);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                } else {

                    try {
                        Object o = finalCallback.parseNetworkResponse(response);
                        sendSuccessResultCallback(o, finalCallback);
                    } catch (Exception e) {
                        sendFailResultCallback(call, e, finalCallback);
                    }
                }


            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback) {
        if (callback == null) return;

        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback) {
        if (callback == null) return;
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                Log.i(OkHttpUtils.class.getSimpleName(), object.toString());
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }


    public void setCertificates(InputStream... certificates) {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null))
                .build();
    }


    public void setConnectTimeout(int timeout, TimeUnit units) {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .connectTimeout(timeout, units)
                .build();
    }
}

