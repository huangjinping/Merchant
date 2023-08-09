package com.hdfex.merchantqrshow.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.hdfex.merchantqrshow.utils.log.LogUtil;

/**
 * Created by maidou521 on 2016/7/26.
 */
public class KeyBoardLayout extends RelativeLayout {
    private static final String TAG = KeyBoardLayout.class.getSimpleName();
    public static final byte KEYBOARD_STATE_SHOW = -3;
    public static final byte KEYBOARD_STATE_HIDE = -2;
    public static final byte KEYBOARD_STATE_INIT = -1;
    private boolean mHasInit;
    private boolean mHasKeybord;
    private int mHeight;
    private onKybdsChangeListener mListener;

    public KeyBoardLayout(Context context) {
        super(context);
    }

    public KeyBoardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyBoardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnkbdStateListener(onKybdsChangeListener listener){
        mListener = listener;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!mHasInit) {
            mHasInit = true;
            mHeight = b;
            if (mListener != null) {
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_INIT);
            }
        } else {
            mHeight = mHeight < b ? b : mHeight;
        }
        if (mHasInit && mHeight > b) {
            mHasKeybord = true;
            if (mListener != null) {
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_SHOW);
            }
//            LogUtil.e(TAG, "show keyboard.......");
        }
        if (mHasInit && mHasKeybord && mHeight == b) {
            mHasKeybord = false;
            if (mListener != null) {
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_HIDE);
            }
//            LogUtil.e(TAG, "hide keyboard.......");
        }
    }

    public interface onKybdsChangeListener{
        public void onKeyBoardStateChange(int state);
    }
}
