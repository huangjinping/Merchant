package com.hdfex.merchantqrshow.widget.picker;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.CallSuper;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;



/**
 * 底部弹窗基类
 */
public abstract class BottomPopup<V extends View> implements DialogInterface.OnKeyListener {
    /**
     * The constant MATCH_PARENT.
     */
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    /**
     * The constant WRAP_CONTENT.
     */
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    protected Activity activity;
    protected int screenWidthPixels;
    protected int screenHeightPixels;
    private Popup popup;
    private int width = 0, height = 0;
    private boolean isFillScreen = false;
    private boolean isHalfScreen = false;

    /**
     * Instantiates a new Bottom popup.
     *
     * @param activity the activity
     */
    public BottomPopup(Activity activity) {
        this.activity = activity;
        DisplayMetrics displayMetrics = ScreenUtils.displayMetrics(activity);
        screenWidthPixels = displayMetrics.widthPixels;
        screenHeightPixels = displayMetrics.heightPixels;
        popup = new Popup(activity);
        popup.setOnKeyListener(this);
    }

    /**
     * Gets view.
     *
     * @return the view
     */
    protected abstract V makeContentView();

    /**
     * 弹出窗显示之前调用
     */
    private void onShowPrepare() {
        setContentViewBefore();
        V view = makeContentView();
        popup.setContentView(view);// 设置弹出窗体的布局
        setContentViewAfter(view);
        if (width == 0 && height == 0) {
            //未明确指定宽高，优先考虑全屏再考虑半屏然后再考虑包裹内容
            width = screenWidthPixels;
            if (isFillScreen) {
                height = MATCH_PARENT;
            } else if (isHalfScreen) {
                height = screenHeightPixels / 2;
            } else {
                height = WRAP_CONTENT;
            }
        }
        popup.setSize(width, height);
    }


    /**
     * Sets content view before.
     */
    protected void setContentViewBefore() {
    }

    /**
     * Sets content view after.
     *
     * @param contentView the content view
     */
    protected void setContentViewAfter(V contentView) {
    }


    /**
     * Is showing boolean.
     *
     * @return the boolean
     */
    public boolean isShowing() {
        return popup.isShowing();
    }

    /**
     * Show.
     */
    @CallSuper
    public void show() {
        onShowPrepare();
        popup.show();
    }

    /**
     * Dismiss.
     */
    public void dismiss() {
        popup.dismiss();
    }

    /**
     * On key down boolean.
     *
     * @param keyCode the key code
     * @param event   the event
     * @return the boolean
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public final boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            return onKeyDown(keyCode, event);
        }
        return false;
    }

    /**
     * Gets window.
     *
     * @return the window
     */
    public Window getWindow() {
        return popup.getWindow();
    }

    /**
     * Gets root view.
     *
     * @return the root view
     */
    public ViewGroup getRootView() {
        return popup.getRootView();
    }

}
