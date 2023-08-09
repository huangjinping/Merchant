package com.hdfex.merchantqrshow.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by harrishuang on 2017/4/21.
 */

public class FileUtils {

    private static final String SD_PATH = "/sdcard/funfujun/pic/";
    private static final String IN_PATH = "/funfujun/pic/";

    /**
     * 保存bitmap到本地
     * @param context
     * @param mBitmap
     * @return
     */
    public static File saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return filePic;
    }


    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }


    public static Bitmap getfileBitmap(final String url) {

        File file = new File(url);
        if (file.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(url);
            if (bmp == null) {
                file.delete();
            } else {

                return bmp;
            }
        }
        return null;

    }
}
