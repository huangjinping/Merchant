package com.hdfex.merchantqrshow.widget;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by harrishuang on 16/10/17.
 * <p>
 * 日期工具
 */

public class DateUtils {

    public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final String DATE_FORMAT3 = "yyyy-MM-dd HH:mm";

    public static String getMathAndDay(String date) {
        try {
            Date parse = dateFormat.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(parse);
            int i = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DATE);
            return (i + 1) + "." + mDay;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }


    /**
     * 格式化
     */
    static SimpleDateFormat dateformatShort = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 只有时间大于等于当前时间的时候return true;else  return false
     *
     * @param date
     * @return
     */
    public static boolean compareNowDate(String date) {

        try {
            Date nowDate = new Date();
            Date target = dateformatShort.parse(date);
            if (target.getTime() >= nowDate.getTime()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String[] WEEK = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public static final int WEEKDAYS = 7;

    public static String dateToWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];
    }

    /**
     * 比对时间
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static boolean compare_date(String DATE1, String DATE2) {
        try {
            Date dt1 = dateformatShort.parse(DATE1);
            Date dt2 = dateformatShort.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception exception) {
            Log.d("hjp", "exception" + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    public static final String DATE_FORMAT2 = "yyyy-MM-dd HH24:mm:ss";


    /**
     * 获取日期显示格式，为空默认为yyyy-mm-dd HH:mm:ss
     *
     * @param format
     * @return SimpleDateFormat
     * @author longtaoge
     * @data Dec 30, 2013
     */
    private static SimpleDateFormat getFormat(String format) {
        if (format == null || "".equals(format)) {
            format = DateUtils.DATE_FORMAT2;
        }
        return new SimpleDateFormat(format);
    }

    /**
     * 将日期格式转换成String
     *
     * @param value  需要转换的日期
     * @param format 日期格式
     * @return String
     * @author longtaoge
     * @data Dec 31, 2013
     */
    public static String date2String(Date value, String format) {
        if (value == null) {
            return null;
        }

        SimpleDateFormat sdf = getFormat(format);
        return sdf.format(value);
    }
}
