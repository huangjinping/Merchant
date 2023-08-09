package com.hdfex.merchantqrshow.widget.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

/**
 * author Created by harrishuang on 2017/9/12.
 * email : huangjinping@hdfex.com
 */

public class  CertificateDatePicker extends WheelPicker {


    private List<String> item;
    private OnDurationPickerSelectedListener onDurationPickerSelectedListener;
    private int selectedIndex;
    private String selectedItem;


    public void setOnDurationPickerSelectedListener(OnDurationPickerSelectedListener onDurationPickerSelectedListener) {
        this.onDurationPickerSelectedListener = onDurationPickerSelectedListener;
    }

    public CertificateDatePicker(Activity activity, List<String> item) {
        super(activity);
        this.item = item;

    }

    public CertificateDatePicker(Activity activity, List<String> item, OnDurationPickerSelectedListener onDurationPickerSelectedListener) {
        super(activity);
        this.item = item;
        this.onDurationPickerSelectedListener = onDurationPickerSelectedListener;
    }

    @Nullable
    @Override
    protected View makeHeaderView() {
        return super.makeHeaderView();
    }

    @NonNull
    @Override
    protected View makeCenterView() {
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        final WheelView provinceView = new WheelView(activity);
        provinceView.setLayoutParams(new LinearLayout.LayoutParams(screenWidthPixels, WRAP_CONTENT));
        provinceView.setTextSize(textSize);
        provinceView.setTextColor(textColorNormal, textColorFocus);
        provinceView.setLineVisible(lineVisible);
        provinceView.setLineColor(lineColor);
        provinceView.setOffset(offset);
        layout.addView(provinceView);
        provinceView.setItems(item);
        provinceView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                CertificateDatePicker.this.selectedIndex = selectedIndex;
                selectedItem = item;

            }
        });

        return layout;
    }

    @Override
    protected void onSubmit() {
        super.onSubmit();

        if (onDurationPickerSelectedListener != null) {
            onDurationPickerSelectedListener.selected(selectedIndex, selectedItem);
        }
    }


    /**
     * 选择监听器
     */
    public interface OnDurationPickerSelectedListener {

        void selected(int index, String item);
    }
}