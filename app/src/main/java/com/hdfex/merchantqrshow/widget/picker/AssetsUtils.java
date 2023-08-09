package com.hdfex.merchantqrshow.widget.picker;

import android.content.Context;

import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.widget.ConvertUtils;


/**
 * 操作安装包中的“assets”目录下的文件
 *
 */
public class AssetsUtils {

    /**
     * read file content
     *
     * @param context   the context
     * @param assetPath the asset path
     * @return String string
     */
    public static String readText(Context context, String assetPath) {
        LogUtil.d(AssetsUtils.class.getSimpleName(),"read assets file as text: " + assetPath);
        try {
            return ConvertUtils.toString(context.getAssets().open(assetPath));
        } catch (Exception e) {
            LogUtil.e(AssetsUtils.class.getSimpleName(),e.toString());
            return "";
        }
    }

}
