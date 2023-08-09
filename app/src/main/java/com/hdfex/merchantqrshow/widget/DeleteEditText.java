package com.hdfex.merchantqrshow.widget;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.utils.DisplayUtils;
import com.hdfex.merchantqrshow.utils.log.LogUtil;


/**
 * 带删除按钮
 */
public class DeleteEditText extends AppCompatEditText {
    private final static String TAG = "EditTextWithDel";
    private Drawable imgInable;
    private Drawable imgAble;
    private Context mContext;
    private boolean hideIcon;

    public DeleteEditText(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DeleteEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public DeleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
//        imgInable = mContext.getResources().getDrawable(R.drawable.delete_gray);
        imgAble = mContext.getResources().getDrawable(R.mipmap.ic_delete_bigge);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
        setOnFocusChangeListener(onFocusChangeListener);

    }


    public void setDrawable() {
        if (length() < 1) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {

            if (hideIcon) {
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
            }

        }
    }

    public void clearDrawable() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();

            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(eventX, eventY))
                if (isEnabled()){
                    setText("");
                }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public boolean isShowIcon() {
        return hideIcon;
    }

    public void setShowIcon(boolean showIcon) {
        this.hideIcon = showIcon;
    }

    OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!v.hasFocus()) {
                clearDrawable();
            } else {
                setDrawable();
            }
        }
    };

}
