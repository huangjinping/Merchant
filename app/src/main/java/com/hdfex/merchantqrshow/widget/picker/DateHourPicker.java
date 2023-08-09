package com.hdfex.merchantqrshow.widget.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.hdfex.merchantqrshow.widget.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * author Created by harrishuang on 2017/10/26.
 * email : huangjinping@hdfex.com
 */

public class DateHourPicker extends WheelPicker {


    private View headerView;


    private OnDateSelectListener onDateSelectListener;

    public void setOnDateSelectListener(OnDateSelectListener onDateSelectListener) {
        this.onDateSelectListener = onDateSelectListener;
    }

    String[] minArr = {"00分", "10分", "20分", "30分", "40分", "50分"};
    private List<String> minList = Arrays.asList(minArr);

    /**
     * Instantiates a new Wheel picker.
     *
     * @param activity the activity
     */
    public DateHourPicker(Activity activity) {
        super(activity);
    }


    public DateHourPicker(Activity activity, View headerView) {
        super(activity);
        this.activity = activity;
        this.headerView = headerView;
    }

    private Calendar currentCalendar;
    private String currentCalendarStr;
    private String hourDate;
    private String hourStr;

    private String minDate;
    private String minStr;


    @NonNull
    @Override
    protected View makeCenterView() {
        textSize = 15;
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams dataWeekParams = new LinearLayout.LayoutParams(screenWidthPixels / 2, WRAP_CONTENT);


        final WheelView dataWeek = new WheelView(activity);
        dataWeek.setLayoutParams(dataWeekParams);
        dataWeek.setTextSize(textSize);
        dataWeek.setTextColor(textColorNormal, textColorFocus);
        dataWeek.setLineVisible(lineVisible);
        dataWeek.setLineColor(lineColor);
        dataWeek.setOffset(offset);
        layout.addView(dataWeek);

        List<String> afterMonthData = getAfterMonthData();
        dataWeek.setItems(afterMonthData, 1);
        currentCalendar = calendars.get(0);
        currentCalendarStr = afterMonthData.get(0);
        dataWeek.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                currentCalendar = calendars.get(selectedIndex);
                currentCalendarStr = item;
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidthPixels / 4, WRAP_CONTENT);
        final WheelView hour = new WheelView(activity);
        hour.setLayoutParams(params);
        hour.setTextSize(textSize);
        hour.setTextColor(textColorNormal, textColorFocus);
        hour.setLineVisible(lineVisible);
        hour.setLineColor(lineColor);
        hour.setOffset(offset);
        layout.addView(hour);
        List<String> numItem = getNumItem(6, 22);
        hourStr = numItem.get(0);
        hourDate = hourStr.substring(0, hourStr.length() - 1);
        hour.setItems(numItem, 1);
        hour.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                hourStr = item;
                hourDate = hourStr.substring(0, hourStr.length() - 1);
            }
        });


        final WheelView min = new WheelView(activity);
        min.setLayoutParams(params);
        min.setTextSize(textSize);
        min.setTextColor(textColorNormal, textColorFocus);
        min.setLineVisible(lineVisible);
        min.setLineColor(lineColor);
        min.setOffset(offset);
        layout.addView(min);
        min.setItems(minList, 1);
        minStr = minList.get(0);
        minDate = minStr.substring(0, minStr.length() - 1);
        min.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                minStr = item;
                minDate = minStr.substring(0, minStr.length() - 1);

            }
        });

        LinearLayout rootView = new LinearLayout(activity);
        rootView.setOrientation(LinearLayout.VERTICAL);
        if (headerView != null) {
            rootView.addView(headerView);
        }
        rootView.addView(layout);
        return rootView;
    }

    public List<Calendar> calendars = new ArrayList<>();

    public List<String> getAfterMonthData() {
        calendars.clear();
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            calendars.add(calendar);
            // 获取月
            int month = calendar.get(Calendar.MONTH);
            int date = calendar.get(Calendar.DATE);
            dataList.add((month + 1) + "月" + date + "日" + DateUtils.dateToWeek(calendar.getTime()));
        }
        return dataList;
    }


    @Override
    protected void onSubmit() {

        if (onDateSelectListener != null) {

            Log.d("hjp",Integer.parseInt(hourDate)+"=============="+Integer.parseInt(minDate));
            currentCalendar.set(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DATE), Integer.parseInt(hourDate), Integer.parseInt(minDate));
            Date time = currentCalendar.getTime();
            onDateSelectListener.onSelect(currentCalendar, DateUtils.date2String(time, DateUtils.DATE_FORMAT3));
        }
        super.onSubmit();

    }

    /**
     * 提交其他问题
     *
     * @param start
     * @param end
     * @return
     */
    public List<String> getNumItem(int start, int end) {
        List<String> item = new ArrayList<>();
        for (int i = 0; i < (end - start) + 1; i++) {
            item.add((start + i) + "时");
        }
        return item;
    }


    public interface OnDateSelectListener {
        void onSelect(Calendar calendar, String data);
    }
}
