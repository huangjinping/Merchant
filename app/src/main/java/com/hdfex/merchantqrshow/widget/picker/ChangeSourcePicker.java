package com.hdfex.merchantqrshow.widget.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 更换资方的东西
 * author Created by harrishuang on 2019/11/8.
 * email : huangjinping1000@163.com
 */
public class ChangeSourcePicker extends WheelPicker {


    /**
     * Instantiates a new Wheel picker.
     *
     * @param activity the activity
     */
    public ChangeSourcePicker(Activity activity) {
        super(activity);

    }


    @NonNull
    @Override
    protected View makeCenterView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidthPixels / 2, WRAP_CONTENT);
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        textSize=13;
        final WheelView leftView = new WheelView(activity);
        leftView.setLayoutParams(params);
        leftView.setTextSize(textSize);
        leftView.setTextColor(textColorNormal, textColorFocus);
        leftView.setLineVisible(lineVisible);
        leftView.setLineColor(lineColor);
        leftView.setOffset(offset);
        leftView.setItems(getNumItem(1, 12), 1);
        leftView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {


            }
        });
        layout.addView(leftView);
        final WheelView rightView = new WheelView(activity);
        rightView.setLayoutParams(params);
        rightView.setTextSize(textSize);
        rightView.setTextColor(textColorNormal, textColorFocus);
        rightView.setLineVisible(lineVisible);
        rightView.setLineColor(lineColor);
        rightView.setOffset(offset);
        rightView.setItems(getNumItem(1, 1000), 1);
        rightView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {

            }
        });

        layout.addView(rightView);
        return layout;
    }



    @Nullable
    @Override
    protected View makeHeaderView() {
        titleText="选择分期";
        return super.makeHeaderView();
    }

    /**
     * 提交其他问题
     *
     * @param start
     * @param end
     * @return
     */
    public List<String> getNumItem(int start, int end) {
        List<String> item = new ArrayList<>();
        for (int i = 0; i < (end - start) + 1; i++) {
            item.add((start + i) + "中国中国中国中国中国中国");
        }
        return item;
    }
}
