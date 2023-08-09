package com.hdfex.merchantqrshow.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by harrishuang on 2017/2/16.
 */

public class DeviceUtils {


    /***
     *判断是不是授权联系人信息
     * @return
     */
    public static boolean isPermission(Context context, String perm){
        PackageManager pm = context.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission(perm, context.getPackageName()));
        if (permission) {
            Log.d("hjp","授权过来");
            return true;
        }else {
            Log.d("hjp","没有授权");
            return false;
        }

    }
}
