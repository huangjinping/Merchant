package com.hdfex.merchantqrshow.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by maidou521 on 2016/6/11.
 */
public class SharedPreferencesUtils {
    public static SharedPreferences.Editor editor;
    public static Context mContext;
    public static SharedPreferences sp;

    public static void buildSP(Context context) {
        mContext = context;
        sp = mContext.getSharedPreferences("Maidou", Activity.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static void putString(Context context, String tag, String value) {
        buildSP(context);
        editor.putString(tag, value);
        editor.commit();
    }

    public static String getString(Context context, String tag, String defaults) {
        buildSP(context);
        String value = sp.getString(tag, defaults);
        return value;
    }

    public static void putInt(Context context, String tag, int value) {
        buildSP(context);
        editor.putInt(tag, value);
        editor.commit();
    }

    public static int getInt(Context context, String tag, int defaults) {
        buildSP(context);
        int value = sp.getInt(tag, defaults);
        return value;
    }

    public static void putBoolean(Context context, String tag, boolean value) {
        buildSP(context);
        editor.putBoolean(tag, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String tag, Boolean value) {
        buildSP(context);
        boolean b = sp.getBoolean(tag, value);
        return b;
    }
}
