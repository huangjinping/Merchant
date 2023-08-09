package com.hdfex.merchantqrshow.widget.picker;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;



/**
 * 获取屏幕宽高等信息、全屏切换、保持屏幕常亮、截屏等
 *
 */
public final class ScreenUtils {


    /**
     * Display metrics display metrics.
     *
     * @param context the context
     * @return the display metrics
     */
    public static DisplayMetrics displayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);

        return dm;
    }



}
