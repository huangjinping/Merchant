package com.hdfex.merchantqrshow.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetWorkTool {
	/**
	 * 判断网络.
	 * 
	 * @param context
	 *            the context
	 * @return true - 有网络可用，false -网网络
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (manager == null) {
			return false;
		}

		NetworkInfo networkinfo = manager.getActiveNetworkInfo();

		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		if (networkinfo != null && networkinfo.isConnected()) {
			// 判断当前网络是否已经连接
			if (networkinfo.getState() == NetworkInfo.State.CONNECTED) {
				return true;
			} else {
				return false;
			}
		}
		return true;

	}
}  

