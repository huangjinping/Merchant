package com.hdfex.merchantqrshow.manager.team.adapter;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.manager.team.TeamPerform;

import java.math.BigDecimal;
import java.util.List;

/**
 * author Created by harrishuang on 2017/6/1.
 * email : huangjinping@hdfex.com
 */


public class ChartAdapter extends BaseAdapter {

    private List<TeamPerform> dataList;
    private Activity context;
    private DisplayMetrics displayMetrics;


    public ChartAdapter(List<TeamPerform> dataList, Activity context) {
        this.dataList = dataList;
        this.context = context;
        displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

    }

    ViewHolder viewholder;

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chart, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        if (position == dataList.size() - 1) {
            viewholder.img_line.setVisibility(View.VISIBLE);
        } else {
            viewholder.img_line.setVisibility(View.GONE);
        }
        TeamPerform teamPerform = dataList.get(position);

        if (!TextUtils.isEmpty(teamPerform.getRegion())) {
            viewholder.txt_region.setText(teamPerform.getRegion());
        }
        if (!TextUtils.isEmpty(teamPerform.getTeamAmt())) {
            viewholder.txt_teamAmt.setText(teamPerform.getTeamAmt());
        }


        int maxLen = (displayMetrics.widthPixels - viewholder.txt_region.getLayoutParams().width) / 2;
        performAnimate(viewholder.img_len, 0, getLen(dataList, position, maxLen));

        return convertView;
    }

    /**
     * 获取当前值
     *
     * @param dataList
     */
    private int getLen(List<TeamPerform> dataList, int position, int maxlen) {
        int current = 0;
        try {
            int max = getMax(dataList);
            if (max == 0) {
                return current;
            }
            BigDecimal bigDecimal = new BigDecimal(dataList.get(position).getTeamAmt());
            current = bigDecimal.intValue();
            current = current * maxlen / max;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return current;
    }

    /**
     * 处理疏浚
     *
     * @param dataList
     * @return
     */
    private int getMax(List<TeamPerform> dataList) {
        int max = 0;
        for (int i = 0; i < dataList.size(); i++) {
            TeamPerform teamPerform = dataList.get(i);
            BigDecimal bigDecimal = new BigDecimal(teamPerform.getTeamAmt());
            int temp = bigDecimal.intValue();
            if (max < temp) {
                max = temp;
            }
        }
        return max;
    }


    private void performAnimate(final View target, final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer) animator.getAnimatedValue();
//                Log.d("ddd", "current value: " + currentValue);
                //计算当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = currentValue / 100f;
                //这里我偷懒了，不过有现成的干吗不用呢
                //直接调用整型估值器通过比例计算出宽度，然后再设给Button
                target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });
        valueAnimator.setDuration(5000).start();
    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_region;
        public ImageView img_len;
        public TextView txt_teamAmt;
        public ImageView img_line;
        public LinearLayout layout_len;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_region = (TextView) rootView.findViewById(R.id.txt_region);
            this.img_len = (ImageView) rootView.findViewById(R.id.img_len);
            this.txt_teamAmt = (TextView) rootView.findViewById(R.id.txt_teamAmt);
            this.img_line = (ImageView) rootView.findViewById(R.id.img_line);
            this.layout_len = (LinearLayout) rootView.findViewById(R.id.layout_len);
        }

    }
}
