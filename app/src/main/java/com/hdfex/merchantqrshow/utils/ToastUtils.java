package com.hdfex.merchantqrshow.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfex.merchantqrshow.R;


public class ToastUtils extends Toast {
    /**
     * 自定义吐丝布局
     */
    private static View mToastView;
    /**
     * 默认显示时间
     */
    private static int mDuration = Toast.LENGTH_SHORT;
    // 吐丝
    private static Toast mToast;

    /**
     * 构造
     *
     * @param context
     */
    public ToastUtils(Context context) {
        super(context);
    }

    /**
     * 默认显示时间的吐丝
     *
     * @param context
     * @param text
     * @return
     */
    public static Toast makeText(Context context, CharSequence text) {

        return ToastUtils.makeText(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * @param context
     * @param text
     * @param duration 时长
     * @return 自定义吐丝
     */
    public static Toast makeText(Context context, CharSequence text,
                                 int duration) {
        mToast = com.hjq.toast.ToastUtils.getToast();
        mToast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflate.inflate(R.layout.toast_item, null);
        mToastView = mView.findViewById(R.id.toast_layout);
        TextView tv = (TextView) mView.findViewById(R.id.textview_toast);
        tv.setText(text);
        mToast.setText(text+"");
        mToast.setView(mView);


//        Toast toast = com.hjq.toast.ToastUtils.getToast();
//        toast.setText("ddddd");
//        toast.show();
        return mToast;
    }


    /**
     * @param context
     * @param
     * @param
     * @return 自定义吐丝
     */
    public static Toast makeTextOrder(Context context
    ) {

        if (mToast == null) {
            mToast = new Toast(context);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflate.inflate(R.layout.layout_toast_success, null);
        mToastView = mView.findViewById(R.id.toast_layout);
        mToast.setView(mView);
        return mToast;
    }


    /**
     * 自定义背景颜色
     *
     * @param context
     * @param text
     * @param duration
     * @param Color
     * @return
     */
    public static Toast makeText(Context context, CharSequence text,
                                 int duration, int Color) {
        mToast = ToastUtils.makeText(context, text, duration);

        if (mToastView != null) {
            mToastView.setBackgroundColor(Color);
        } else {
            mToast.getView().setBackgroundColor(Color);
        }

        return mToast;
    }

    /**
     * @param context
     * @param text
     * @param duration
     * @param drawable
     * @return 自定义背景 资源
     */
    public static Toast makeText(Context context, CharSequence text,
                                 int duration, Drawable drawable) {
        mToast = ToastUtils.makeText(context, text, duration);

        if (mToastView != null) {
            mToastView.setPadding(10, 0, 10, 0);
            mToastView.setBackgroundDrawable(drawable);
        } else {
            mToast.getView().setBackgroundDrawable(drawable);
        }

        return mToast;
    }

    /**
     * @param context
     * @param text
     * @param duration
     * @param view
     * @return 自定义布局
     */
    public static Toast makeText(Context context, CharSequence text,
                                 int duration, View view) {
        mToast = ToastUtils.makeText(context, text, duration);
        mToast.setView(view);
        return mToast;
    }

    /**
     * @param context
     * @param text
     * @param duration
     * @param view
     * @param onClickListener
     * @return 自定义带点击事件的布局
     */
    public static Toast makeText(Context context, CharSequence text,
                                 int duration, View view, OnClickListener onClickListener) {

        mToast = ToastUtils.makeText(context, text, duration);
        view.setOnClickListener(onClickListener);
        mToast.setView(view);
        return mToast;
    }

    /**
     * @param context
     * @param text
     * @param duration
     * @return 绿色背景 的吐丝 用于显示正确信息
     */
    public static Toast i(Context context, CharSequence text, int duration) {
        return ToastUtils.makeText(
                context,
                text,
                duration,
                context.getResources().getDrawable(
                        R.drawable.background_standard_black)); //   R.drawable.background_standard_green));
    }

    /**
     * @param context
     * @param text
     * @return 绿色背景 的吐丝 用于显示正确信息 默认时长
     */
    public static Toast i(Context context, CharSequence text) {
        return ToastUtils.i(context, text + "", mDuration);
    }

    /**
     * @param context
     * @param text
     * @param duration
     * @return 红色背景，用于错误提示
     */
    public static Toast e(Context context, CharSequence text, int duration) {
        return ToastUtils
                .makeText(context, text + "", duration, context.getResources()
                        .getDrawable(R.drawable.background_standard_red));
    }

    /**
     * @param context
     * @param text
     * @return 红色背景 默认时长
     */
    public static Toast e(Context context, CharSequence text) {
        return ToastUtils.e(context, text + "", mDuration);
    }

    /**
     * @param context
     * @param text
     * @param duration
     * @return 蓝色背景 显示调试信息时用
     */
    public static Toast d(Context context, CharSequence text, int duration) {
        return ToastUtils.makeText(context, text, duration, context
                .getResources()
                .getDrawable(R.drawable.background_standard_blue));
    }

    /**
     * @param context
     * @param text
     * @return 蓝色背景 默认时长
     */
    public static Toast d(Context context, CharSequence text) {

        return ToastUtils.d(context, text, mDuration);
    }

    /**
     * @param context
     * @param text
     * @param duration
     * @return 警告背景 用于提示警告信息
     */
    public static Toast w(Context context, CharSequence text, int duration) {
        return ToastUtils.makeText(
                context,
                text,
                duration,
                context.getResources().getDrawable(
                        R.drawable.background_standard_orange));
    }

    /**
     * @param context
     * @param text
     * @return 警告背景 默认时长
     */
    public static Toast w(Context context, CharSequence text) {

        return ToastUtils.w(context, text, mDuration);
    }

}
