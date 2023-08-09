package com.hdfex.merchantqrshow.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackageListResponse;
import com.hdfex.merchantqrshow.bean.salesman.taobao.AliPayUserAuthResponse;
import com.hdfex.merchantqrshow.bean.salesman.taobao.RedPackageAccountInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.taobao.WithdrawDepositResponse;
import com.hdfex.merchantqrshow.comm.taobao.AuthResult;
import com.hdfex.merchantqrshow.comm.taobao.PayResult;
import com.hdfex.merchantqrshow.comm.taobao.util.OrderInfoUtil2_0;
import com.hdfex.merchantqrshow.mvp.contract.TaobaoAuthContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.widget.ActionSheet;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/6/26.
 * email : huangjinping@hdfex.com
 */

public class TaobaoAuthPresenter extends TaobaoAuthContract.Presenter {


    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017061407486192";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088721232778940";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "123";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC4FpdjOWkGxhd/ZNCyM0oj4/LoyZR3uGcZ9zciv+b6WdLTWy8P6K30nmIdNKmsqgV/pXrE3QPMVXs/hC6XwSnzoXcnzPi9o5BnELXiRIyuPCq+ouG6CWtI196RdtQNBlm1ATY508V8qZHnzOWS6cX+Arz6aPFHm5SLS8zuQ1Noh1CcG60ZSsVFszVwinxttYi0lG4KwwK/ZBbpcOHyyDmqyY3CMTWcMfvnmkdeuS8rHwqSb75mjfaAwW5ga52w+oG+Z5MuMDvQw60w5I7R1KW5LI/62CiRJKQsfqWmhf6rKEe+NdLUzZjrthvB6zjJG1UXLSJ7e6NSy2T393Ql4VpvAgMBAAECggEAK3n6aw7GBTmHlyjCOTPv0KYnWl8xV00P3IwMU4KrFHleV1hztvgnDVzSoz8qy4KkJc95PUFNgla18vxO3LxcIhQCRj4ciP02P4Es07GI4CWJmthxIzTSR7Pj9srvrdvQF/WfTuz6+UQ7F1nG5nmbrzAKXcGnKlhOLs/KKINyEX9s+9zOz8tvvwzMKfBgEO7l+RVitPHMzX8Q4LygLPSQT+HHOqcuGR0oF6e86CtvXKRSZnnNHxT94X0F9aS0LxGgULuTrqcHgi91+DhtQe35yDWJ7rh4qoTwUG2ZEn50JMXOeejW3NfFSFMXQxjFH4UsAfYb9S4tO2+9J33V6fZLwQKBgQDohS54EEuOyhl60IHjZIfJMBNX9Olghdp3y8eyX0vPL2rB64ABAJmOSzHiN0ss7ogj5+6ZkubzX4UifACqPt++ZhxaDe0L1Kti9Xqr8Om6qm9EZIhL2D/qyZTzXXZVyPkBs5j8MNa1cXCn0T3eMLpaK2DGcbpPuwroxzoj/6HdMQKBgQDKrWhIpCJiNJqAuW3z0Q93NO0gXhY0tgzGQHfqco9ZcFICYQ5HKQ3wSyKOPWv3L9v0BE+p/TKwltmp04DaLU2Mwh2zBOTotkgetIKSbI7DqDz6ie8EM9KETgId/WI/eQ9MZblffpMNr2Ps+U9wOWdOXHbRajZBNQcIhm5t0yZJnwKBgBBppjgPXmX72gSTRzfX1sXawOLKhNatXBhsp/HtTBqZzD1X9Dcd5bjnkePX51SKmc1/B5aLigXPzkP64MkjmwODjajFpt/Y6SgZD8mnPbhRY30+Y2iHISvfgsYS6NFxWDJIjRnwEJfKJBuUZ3uRkPXgbfpT+MS7/S8lMPNtje1hAoGAZplR6TPtk/k9HVzBJGP4iEfqoi/+3cAqx1FrYuuLVaNeYIXxTGSkAbiHTaoaNY+ssOJd0ZezSWGT3jyLRecZEuBRWIZpusRLzf9i+eyqeLF57PpWXLI/J2q196iFS8xkyi/Un1rb5lmbJCMw64ajnS+BlpFF1cyaOt6SmFYDU9ECgYBnt4lX9uq4HG+xsbvEV5YvYWMtLX52Ks24LO3Ft40de7Hc4W6+1jVp8QH0ctNGI9OX6J3NjORWrGRrzkDBNyobHYI7Y8fgB4LhBTgkmsHy8OLNxSCeX5/cW/Q2AgM1X8xjsc2rM1ubIpxwECmrD9AdMziDHk7mlQehT8PhQ2EmJQ==";
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。

                        getmMvpView().showToast("支付成功");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        getmMvpView().showToast("支付失败");

                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();


                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
//                        getmMvpView().showToast("授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));

                        getAliPayUserAuth(authResult.getAuthCode());

                    } else {
//                        getmMvpView().showToast("授权失败" + String.format("authCode:%s", authResult.getAuthCode()));
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

//    public void showBackDialog() {
//        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
//        dialogBuilder
//                .withTitle("信息提示")
//                .withTitleColor("#FFFFFF")
//                .withMessage("是否返回首页?")
//                .withMessageColor("#000000")
//                .withDialogColor("#FFFFFF")
//                .withIcon(this.getResources().getDrawable(R.mipmap.ic_merchant_logo))
//                .isCancelableOnTouchOutside(true)
//                .withDuration(100)
//                .withButton1Text("否")
//                .withButton2Text("是")
//                .isCancelable(false)
//                .isCancelableOnTouchOutside(false)
//                .setButton1Click(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogBuilder.dismiss();
//                    }
//                })
//                .setButton2Click(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogBuilder.dismiss();
//                        Intent mIntent = new Intent(ZuFangQRCodeProduceActivity.this, IndexActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(mIntent);
//                        finish();
//                    }
//                })
//                .show();
//    }


    @Override
    public void toTaobaoAuth(final Activity context) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(context).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //

                        }
                    }).show();
            return;
        }
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(context);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }


    /**
     * @param grantCode
     */
    private void getAliPayUserAuth(String grantCode) {
        if (TextUtils.isEmpty(grantCode)) {
            return;
        }

        OkHttpUtils
                .post()
                .url(NetConst.ALIPAY_USERAUTH)
                .addParams("token", getUser().getToken())
                .addParams("id", getUser().getId())
                .addParams("grantCode", grantCode)

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
                        getmMvpView().showToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = getmMvpView().checkResonse(response);
                            if (b) {
                                AliPayUserAuthResponse response1 = GsonUtil.changeGsonToBean(response, AliPayUserAuthResponse.class);
                                getmMvpView().returnAliPayUserAuth(response1.getResult());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getmMvpView().showToast("服务异常！");
                        }
                    }
                });
    }


    @Override
    public void getWithdrawDeposit(final User user, String balAmt, final Activity activity) {
        if (TextUtils.isEmpty(balAmt)) {
            return;
        }
        OkHttpUtils
                .post()
                .url(NetConst.ALIPAY_WITHDRAW_DEPOSIT)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
//                .addParams("amount", "0.1")
                .addParams("remark", "体现")
                .tag(activity)
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
                            boolean b = getmMvpView().checkResonse(response);
                            if (b) {
                                WithdrawDepositResponse response1 = GsonUtil.changeGsonToBean(response, WithdrawDepositResponse.class);
                                getmMvpView().returnWithdrawDeposit(response1.getResult());
                                loadRedPackageList(user, activity, LoadMode.NOMAL);
                                loadRedPackage(user, activity);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getmMvpView().showToast("服务异常");
                        }
                    }
                });
    }

    @Override
    public void showActionSheet(final Activity context, final User user, FragmentManager supportFragmentManager) {
        final String[] dataArr = {"解绑支付宝"};
        ActionSheet.createBuilder(context, supportFragmentManager)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                /**
                 * 解绑淘宝
                 */

                unbindTaobao(user, context);
            }
        }).show();
    }


    public void unbindTaobao(final User user, final Activity context) {
        OkHttpUtils
                .post()
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .tag(context)
                .url(NetConst.ALIPAY_ACCOUNTUNBUND)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                Response response1 = GsonUtil.changeGsonToBean(response, Response.class);
                                getmMvpView().showToast("账号已解绑");
                                loadRedPackageList(user, context, LoadMode.NOMAL);
                                loadRedPackage(user, context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        getmMvpView().dismissProgress();
                    }
                });
    }


    private int page = 0;

    @Override
    public void loadRedPackageList(User user, final Activity activity, final LoadMode loadMode) {

        if (loadMode != LoadMode.UP_REFRESH) {
            page = 0;
        }
        OkHttpUtils
                .post()
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("pageIndex", page + "")
                .addParams("pageSize", "15")
                .tag(activity)
                .url(NetConst.RED_PACKAGE_LIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        if (getmMvpView()!=null&&getmMvpView().checkResonse(response)) {
                            RedPackageListResponse house = GsonUtil.changeGsonToBean(response, RedPackageListResponse.class);
                            if (house.getResult() != null) {
                                page++;
                                getmMvpView().returnRedPackageResult(house.getResult(), loadMode);
                            } else {
                                getmMvpView().showToast("数据异常！");
                            }

                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();

                        if (getmMvpView()!=null){
                            getmMvpView().loadAfter();
                            getmMvpView().dismissProgress();
                        }

                    }
                });
    }


    @Override
    public void loadRedPackage(User user, final Activity activity) {
        OkHttpUtils
                .post()
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .tag(activity)
                .url(NetConst.RED_PACKAGEACCOUNT_INFO)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        getmMvpView().showToast("数据异常！");
                    }

                    @Override
                    public void onResponse(String response) {
                        if (getmMvpView()!=null&&getmMvpView().checkResonse(response)) {
                            RedPackageAccountInfoResponse house = GsonUtil.changeGsonToBean(response, RedPackageAccountInfoResponse.class);
                            if (house.getResult() != null) {
                                getmMvpView().returnRedPackage(house.getResult());
                            } else {
                                getmMvpView().showToast("数据异常！");
                            }
                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        if (getmMvpView()!=null){
                            getmMvpView().dismissProgress();
                        }
                    }
                });
    }
}
