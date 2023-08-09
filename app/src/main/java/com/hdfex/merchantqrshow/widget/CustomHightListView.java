package com.hdfex.merchantqrshow.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.hdfex.merchantqrshow.utils.log.LogUtil;

/**
 * Created by maidou521 on 2016/7/8.
 */
public class CustomHightListView extends ListView {
    public CustomHightListView(Context context) {
        super(context);
    }

    public CustomHightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 可自定义高度的ListView
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int height = getMeasuredHeight();
//        int width = getMeasuredHeight();
//        int childcount = getChildCount();
//        LogUtil.e("123",childcount);
//
//        if(childcount!=0){
//            View child = getChildAt(0);
//            measureChild(child, widthMeasureSpec, heightMeasureSpec);
//            if (childcount < 4) {
//                height = getMeasuredHeight();
//            } else {
//                height = 4 * child.getMeasuredHeight();
//            }
//            setMeasuredDimension(child.getMeasuredWidth(), height);
//            return;
//        }
//
//        setMeasuredDimension(width, height);
    }
}
