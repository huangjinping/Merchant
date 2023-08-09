package com.turui.bank.ocr;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.WindowManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by admin on 2015/7/29.
 */
public class CameraParaUtil {
    private static final String tag = "CameraParaUtil";
    private CameraSizeComparator sizeComparator = new CameraSizeComparator();
    private static CameraParaUtil myCamPara = null;
    public static float mRate = 1.33f;

    private CameraParaUtil() {

    }

    /**
     * 得到最佳分辨率
     *
     * @param context
     * @param parameters
     * @return int数组，第一个是宽度，第二个是高度
     */
    public static int[] getBestResolution(Context context, Camera.Parameters parameters) {

        /**
         * 得到设备分辨率
         */
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point outSize = new Point();
        wm.getDefaultDisplay().getSize(outSize);
        int screenWidth = outSize.x;
        int screenHeight = outSize.y;

        /**
         * 计算最佳分辨率
         */
        int bestWidth = screenWidth;
        int bestHeight = screenHeight;
        int distance = 4000;

        //获得支持分辨率列表
        List<Camera.Size> list = parameters.getSupportedPreviewSizes();

        // surfaceView的宽度和高度依次减去该设备支持的分辨率，得到两个差值的绝对值
        for (int i = 0; i < list.size(); i++) {
            int distance_width = Math.abs(screenWidth - list.get(i).height);
            int distance_height = Math.abs(screenHeight - list.get(i).width);

            // 找到最小的差值绝对值，将它的宽度和高度设为新的width_new和height_new
            if ((distance_width + distance_height) < distance) {
                distance = distance_width + distance_height;
                bestHeight = list.get(i).height;
                bestWidth = list.get(i).width;
            }
        }

        int[] a = new int[2];
        a[0] = bestWidth;
        a[1] = bestHeight;
        return a;
    }




    public static CameraParaUtil getInstance() {
        if (myCamPara == null) {
            myCamPara = new CameraParaUtil();
            return myCamPara;
        } else {
            return myCamPara;
        }
    }

    public Size getPreviewSize(List<Size> list, int th) {
        Collections.sort(list, sizeComparator);

        int i = 0;
        for (Size s : list) {
            //if ((s.width > th) && equalRate(s, 1.33f)) {//除保证比率外，还保证用户需要设置的尺寸宽度最小值。这个大家根据需要随便改。
            if ((s.width > th) && equalRate(s, mRate)) {//除保证比率外，还保证用户需要设置的尺寸宽度最小值。这个大家根据需要随便改。
                Log.i(tag, "最终设置预览尺寸:w = " + s.width + "h = " + s.height);
                break;
            }
            i++;
        }
        if(i==list.size()){
            i=0;
        }

        return list.get(i);
    }

    public Size getPictureSize(List<Size> list, int th) {
        Collections.sort(list, sizeComparator);
        int i = 0;
        for (Size s : list) {
            //if ((s.width > th) && equalRate(s, 1.33f)) {
            if ((s.width > th) && equalRate(s, mRate)) {
                Log.i(tag, "最终设置图片尺寸:w = " + s.width + "h = " + s.height);
                break;
            }
            i++;
        }

        return list.get(i);
    }

    /**
     * 保证Size的长宽比率。一般而言这个比率为1.333/1.7777即通常说的4:3和16:9比率。
     * @param s
     * @param rate
     * @return
     */
    public boolean equalRate(Size s, float rate) {
        float r = (float) (s.width) / (float) (s.height);
        if (Math.abs(r - rate) <= 0.2) {
            return true;
        } else {
            return false;
        }
    }

    public class CameraSizeComparator implements Comparator<Size> {
        //按升序排列
        public int compare(Size lhs, Size rhs) {
            // TODO Auto-generated method stub
            if (lhs.width == rhs.width) {
                return 0;
            } else if (lhs.width > rhs.width) {
                return 1;
            } else {
                return -1;
            }
        }

    }
}
