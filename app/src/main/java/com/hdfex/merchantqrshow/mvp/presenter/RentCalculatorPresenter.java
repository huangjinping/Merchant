package com.hdfex.merchantqrshow.mvp.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.salesman.add.activity.RentCalculatorActivity;
import com.hdfex.merchantqrshow.bean.salesman.home.CalculateResponse;
import com.hdfex.merchantqrshow.bean.salesman.home.CalculateResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.RentCalculatorContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.view.MyItemClickListener;
import com.hdfex.merchantqrshow.widget.SingleActionView;
import com.hdfex.merchantqrshow.widget.picker.DatePicker;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import rx.Subscriber;

/**
 * 设置信息
 * Created by harrishuang on 2017/4/20.
 */

public class RentCalculatorPresenter extends RentCalculatorContract.Persenter {

    String[] monthArr = null;

    @Override
    public void selectData(Activity context, final TextView targetView) {
        DatePicker datePicker = new DatePicker(context);
        datePicker.setOnDateSelectListener(new DatePicker.OnDateSelectListener() {
            @Override
            public void onSelect(String year, String month, String day) {
                StringBuffer sb = new StringBuffer();
                sb.append(year);
                sb.append("-");
                sb.append(String.format("%02d", Integer.parseInt(month)));
                sb.append("-");
                sb.append(String.format("%02d", Integer.parseInt(day)));
                targetView.setText(sb.toString());
            }
        });
        datePicker.show();
    }

    @Override
    public void selectMonth(RentCalculatorActivity rentCalculatorActivity, final EditText et_entdata, FragmentManager fragmentManager) {
        if (monthArr == null) {
            monthArr = new String[12];
            for (int i = 0; i < monthArr.length; i++) {
                monthArr[i] = String.valueOf(i + 1);
            }
        }
        SingleActionView fragment = SingleActionView.getInstance();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putStringArray("data", monthArr);
        bundle.putString("title", "请选择租住月份");
        fragment.setArguments(bundle);
        fragment.setMyItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                et_entdata.setText(monthArr[postion]
                );
                et_entdata.setTag(monthArr[postion]);
            }
        });

        transaction.setCustomAnimations(
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer,
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer);
        transaction.replace(R.id.layout_fragment_content, fragment);
        transaction.addToBackStack("apartmentFragment");
        transaction.commit();
    }

    @Override
    public void requestResult(User user, String startDate, String duration, String terminationDate, String monthRent) {
        OkHttpUtils
                .post()
                .url(NetConst.CALCULATE_REFUNDAMT)
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("startDate", startDate)
                .addParams("duration", duration)
                .addParams("terminationDate", terminationDate)
                .addParams("monthRent", monthRent)

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

                            boolean b = getmMvpView().checkResonse(response);
                            if (b) {
                                CalculateResponse calculateResponse = GsonUtil.changeGsonToBean(response, CalculateResponse.class);
                                CalculateResult result = calculateResponse.getResult();
                                getmMvpView().returnCalculateResult(result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private Map<TextView, String> previewMap;


    @Override
    public void addEvents(final User user, final EditText et_startdata, final EditText et_entdata, final EditText et_unbinddata) {
        previewMap = new HashMap<>();
        Subscriber<TextViewTextChangeEvent> subscriber = new Subscriber<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                previewMap.put(textViewTextChangeEvent.view(), textViewTextChangeEvent.text() + "");
                /**
                 * 可以计算出结果了
                 */
                if (!TextUtils.isEmpty(previewMap.get(et_startdata)) && !TextUtils.isEmpty(previewMap.get(et_entdata)) && !TextUtils.isEmpty(previewMap.get(et_unbinddata))) {
                    OkHttpUtils
                            .post()
                            .url(NetConst.CALCULATE_REFUNDAMT)
                            .addParams("id", user.getId())
                            .addParams("token", user.getToken())
                            .addParams("startDate", previewMap.get(et_startdata))
                            .addParams("duration", previewMap.get(et_entdata))
                            .addParams("terminationDate", previewMap.get(et_unbinddata))
                            .addParams("monthRent", "6000")
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

                                        boolean b = getmMvpView().checkResonse(response);
                                        if (b) {
                                            CalculateResponse calculateResponse = GsonUtil.changeGsonToBean(response, CalculateResponse.class);
                                            CalculateResult result = calculateResponse.getResult();
                                            getmMvpView().returnTestCalculateResult(result);


                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        };
        RxTextView.textChangeEvents(et_startdata).subscribe(subscriber);
        RxTextView.textChangeEvents(et_entdata).subscribe(subscriber);
        RxTextView.textChangeEvents(et_unbinddata).subscribe(subscriber);


    }

    /**
     * 计算初始值和月份的总日期和当前的差量
     * @param start
     * @param month
     * @param end
     */


}
