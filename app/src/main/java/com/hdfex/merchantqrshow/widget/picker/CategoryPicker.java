package com.hdfex.merchantqrshow.widget.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

/**
 * author Created by harrishuang on 2017/12/4.
 * email : huangjinping@hdfex.com
 */

public class CategoryPicker extends WheelPicker {

    /**
     * Instantiates a new Wheel picker.
     *
     * @param activity the activity
     */
    String[] minArr = {"手机"};
    private List<String> minList = Arrays.asList(minArr);
    private List<String> dataArr;
    private int selectedIndex;
    private  OnItemClickLitener onItemClickListener;

    public void setOnItemClickListener(OnItemClickLitener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CategoryPicker(Activity context, List<String> dataArr) {
        super(context);
        this.dataArr = dataArr;
    }

    @NonNull
    @Override
    protected View makeCenterView() {

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams dataWeekParams = new LinearLayout.LayoutParams(screenWidthPixels / 2, WRAP_CONTENT);
        /**
         *
         */
        final WheelView one = new WheelView(activity);
        one.setLayoutParams(dataWeekParams);
        one.setTextSize(textSize);
        one.setTextColor(textColorNormal, textColorFocus);
        one.setLineVisible(lineVisible);
        one.setLineColor(lineColor);
        one.setOffset(offset);
        layout.addView(one);
        one.setItems(minList, 1);


        final WheelView two = new WheelView(activity);
        two.setLayoutParams(dataWeekParams);
        two.setTextSize(textSize);
        two.setTextColor(textColorNormal, textColorFocus);
        two.setLineVisible(lineVisible);
        two.setLineColor(lineColor);
        two.setOffset(offset);
        layout.addView(two);
        two.setItems(dataArr, 1);
        selectedIndex = 1;
        two.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                CategoryPicker.this.selectedIndex = selectedIndex;
            }
        });
        return layout;
    }
    @Override
    protected void onSubmit() {
        super.onSubmit();
        if (onItemClickListener!=null){
            onItemClickListener.onSelected(selectedIndex);
        }
    }

    public interface  OnItemClickLitener{
        void  onSelected(int selectedIndex);
    }
}
