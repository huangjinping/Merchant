package com.hdfex.merchantqrshow.utils.camera.compress;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @Title：文件路径常量管理
 * @Description：
 * @Package com.harrishuang.communityservice.common
 * @ClassName Constants
 * @author harris   
 * @date 2015-4-3 上午10:02:56
 * @version 
 */ 



	
public class Constants {

	
	/**
	 * @Fields LIMITS_SIZE: 
	 */
		
	public  static  final  int  LIMITS_SIZE=150;
	
	public static String CACHE_PATH_NAME = "naoisdn";

	/**
	 * @Title: getCachePath
	 * @Description:设置路径
	 * @param context
	 * @return
	 */

	public static String getCachePath(Context context) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File file = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/" + CACHE_PATH_NAME);
			if (!file.exists()) {
				file.mkdir();
			}
			return file.getPath();
		} else {
			return context.getCacheDir().getPath();
		}
	}
}
