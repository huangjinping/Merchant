package com.hdfex.merchantqrshow.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 配置文件内容
 * 
 * @author harris.huang
 * 
 */
public class PreferencesUtils {

	private Context ctx;
	private Editor editor;
	private SharedPreferences sp;

	public static String PREFERENCE_NAME = "SttxAndroidCommon";

	// 根据文件名，取配置文件
	public PreferencesUtils(Context context, String preferenceName) {
		ctx = context;
		sp = ctx.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
	}

	// 不包含文件名，取默认配置文件
	public PreferencesUtils(Context context) {
		ctx = context;
		sp = PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	public boolean getBoolean(String key, boolean defValue) {
		return sp.getBoolean(key, defValue);
	}

	public void putBoolean(String key, boolean state) {
		editor = sp.edit();
		editor.putBoolean(key, state);
		editor.commit();
	}

	public String getString(String key, String defValue) {
		return sp.getString(key, defValue);
	}

	public void putString(String key, String value) {
		editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public int getInt(String key, int defValue) {
		return sp.getInt(key, defValue);
	}

	public void putInt(String key, int value) {
		editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public void putLong(String key, long value) {
		editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public long getLong(String key, Long defValue) {
		return sp.getLong(key, defValue);
	}

	public boolean contains(String key) {
		return sp.contains(key);
	}

	/**
	 * get boolean preferences
	 *
	 * @param context
	 * @param key          The name of the preference to retrieve
	 * @param defaultValue Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
	 * this name that is not a boolean
	 */
	public static boolean getBoolean(Context context, String key, boolean defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		return settings.getBoolean(key, defaultValue);
	}

	/**
	 * put boolean preferences
	 *
	 * @param context
	 * @param key     The name of the preference to modify
	 * @param value   The new value for the preference
	 * @return True if the new values were successfully written to persistent storage.
	 */
	public static boolean putBoolean(Context context, String key, boolean value) {
		SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

}