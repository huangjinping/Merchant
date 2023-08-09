/**
 * Project Name锛歐estinghouse
 * File Name锛欰utoScrollView.java
 * Package Name锛歝n.harrishuang.attea.view.autoscrollpager
 * Date锛�2015-1-31 涓婂崍9:25:52
 * Copyright (c) 2015, harris.huang All Rights Reserved.
 * harrishuang
 */
package com.hdfex.merchantqrshow.view.autoscroll;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.activity.WebActivity;
import com.hdfex.merchantqrshow.bean.salesman.home.Adcommodity;

import java.util.ArrayList;
import java.util.List;


/**
 * 轮播控件
 */
public class AutoScrollView extends LinearLayout {

    private AutoScrollViewPager vip_autoscroll;
    private LinearLayout layout_indicator;
    private List<View> viewList;
    private Activity context;
    private List<Adcommodity> dataAllList;
    public DisplayMetrics metrics;
    private int defoult = R.mipmap.ic_banner_defoult;

    public AutoScrollView(Context context) {
        super(context);
        this.context = (Activity) context;
        initView(context);
    }


    private void initView(Context context) {
        this.setOrientation(LinearLayout.VERTICAL);
        dataAllList = new ArrayList<Adcommodity>();
        View view = View.inflate(context, R.layout.layout_autoscrollviewpager,
                null);
        vip_autoscroll = (AutoScrollViewPager) view
                .findViewById(R.id.vip_autoscroll);
        layout_indicator = (LinearLayout) view
                .findViewById(R.id.layout_indicator);
        metrics = new DisplayMetrics();
        Activity activity = (Activity) context;
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(view, params);
        vip_autoscroll.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        vip_autoscroll.stopTimer();
                        break;
                    case MotionEvent.ACTION_UP:
                        vip_autoscroll.startTimer();
                        break;
                }
                return true;
            }
        });
    }

    public void initCarouslview(final List<Adcommodity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        if (dataList.size() >= 2) {
            layout_indicator.setVisibility(View.VISIBLE);
        } else {
            layout_indicator.setVisibility(View.INVISIBLE);
        }
        boolean isdouble = false;

        if (dataList.size() == 2) {
            isdouble = true;
            dataList.addAll(dataList);
        }
        this.dataAllList = dataList;
        this.setVisibility(View.VISIBLE);
        viewList = new ArrayList<View>();

        for (int i = 0; i < dataList.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    int position = vip_autoscroll.getCurrentItem() % dataList.size();
                    mIntent(position);
                }
            });

            Glide.with(context)
                    .load(dataList.get(i).getPic())
                    .error(context.getResources().getDrawable(defoult))
                    //放开出现加载延迟 先注掉
//                .placeholder(container.getContext().getResources().getDrawable(defoult))
                    .into(imageView);
            imageView.setScaleType(ScaleType.FIT_XY);

            viewList.add(imageView);

        }


        vip_autoscroll.start(context, viewList, 4000, layout_indicator,
                R.layout.ad_bottom_item, R.id.ad_item_v,
                R.drawable.dot_focused, R.drawable.dot_normal, isdouble);
    }

    public void onRestart() {
        if (vip_autoscroll.isStoped) {
            vip_autoscroll.startTimer();
        }

    }

    public void onStop() {
        vip_autoscroll.stopTimer();
    }


    private void mIntent(int position) {
        try {
            Adcommodity adcommodity = dataAllList.get(position);
            if (TextUtils.isEmpty(adcommodity.getJumpType()) && !TextUtils.isEmpty(adcommodity.getId())) {
//				Intent mIntent = new Intent(context, ProductDetailActivity.class);
//				mIntent.putExtra("id", Long.valueOf(dataAllList.get(position).getId()));
//				context.startActivity(mIntent);
                return;
            }
//                0：不跳转
//                1：本地跳转
//                2：网页跳转
            if (!TextUtils.isEmpty(adcommodity.getJumpType())) {
                if ("0".equals(adcommodity.getJumpType())) {
                } else if ("1".equals(adcommodity.getJumpType())) {
//					Intent mIntent = new Intent(context, ProductDetailActivity.class);
//					mIntent.putExtra("id", Long.valueOf(dataAllList.get(position).getId()));
//					context.startActivity(mIntent);

                } else if ("2".equals(adcommodity.getJumpType())) {
					Intent mIntent = new Intent(context, WebActivity.class);
					mIntent.putExtra("url", adcommodity.getPageUrl());
					mIntent.putExtra("title", "详情");
					context.startActivity(mIntent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
