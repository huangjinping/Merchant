package com.hdfex.merchantqrshow.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.cameroon.banner.Banner;
import com.cameroon.banner.listener.OnBannerListener;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.activity.WebActivity;
import com.hdfex.merchantqrshow.bean.salesman.home.Notice;
import com.hdfex.merchantqrshow.utils.GlideImageLoader;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.List;

/**
 * author Created by harrishuang on 2018/10/26.
 * email : huangjinping@hdfex.com
 */
public class RewardView extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {
    private ImageView img_target;
    private ImageView img_kid;
    private DKDragView txt_dragView;
    private Banner ban_notice;
    private ImageView img_cancel;
    public boolean isAnimationIng = false;
    private OnClickListener onTargetClickListener;
    private List<Notice> dataList;
    private LinearLayout layout_banner_container;


    public RewardView(Context context) {
        super(context);
    }

    public RewardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnTargetClickListener(OnClickListener onTargetClickListener) {
        this.onTargetClickListener = onTargetClickListener;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_animation, this, false);
        this.img_target = (ImageView) view.findViewById(R.id.img_target);
        this.img_kid = (ImageView) view.findViewById(R.id.img_kid);
        this.txt_dragView = (DKDragView) view.findViewById(R.id.txt_dragView);
        this.ban_notice = (Banner) view.findViewById(R.id.ban_notice);
        this.img_cancel = (ImageView) view.findViewById(R.id.img_cancel);
        this.layout_banner_container=(LinearLayout)view.findViewById(R.id.layout_banner_container);
        txt_dragView.setBoundary(0, 0, 0, 400);

        this.img_kid.setOnClickListener(this);
        this.img_kid.setOnTouchListener(this);
        this.img_target.setOnClickListener(this);
//        this.txt_dragView.setOnClickListener(this);
        this.addView(view);
        txt_dragView.setAnimation(false);
        txt_dragView.setOnDragViewClickListener(new DKDragView.onDragViewClickListener() {
            @Override
            public void onClick() {
                if (img_target.getVisibility() == VISIBLE) {
                    startAnimation(txt_dragView, img_target);
                } else {
                    resetAnimation(txt_dragView, img_target);
                }
            }
        });

        img_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissBannerView();
            }
        });
    }

    /**
     * 开始动画
     *
     * @param view
     * @param target
     */
    private void startAnimation(final View view, final View target) {
        if (isAnimationIng) {
            return;
        }
        isAnimationIng = true;
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int[] location2 = new int[2];
        target.getLocationInWindow(location2);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", 0, location[0] - location2[0] - target.getWidth() / 2 + (view.getWidth() / 2));
        translationX.setDuration(1000);
        translationX.start();
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", 0, location[1] - location2[1] - target.getHeight() / 2 + (view.getHeight() / 2));
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", 1f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", 1f, 0f);

        //创建透明度动画
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1.0f, 0f);
        //动画集合
        AnimatorSet set = new AnimatorSet();
        //添加动画
        set.play(translationX).with(translationY).with(scaleX).with(scaleY);
        //设置时间等
        set.setDuration(1000);
        set.start();
        //动画监听
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                RewardView.this.setBackgroundColor(getResources().getColor(R.color.transparent));
                target.setVisibility(INVISIBLE);
                isAnimationIng = false;
                RewardView.this.setClickable(false);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
    }

    /**
     * 重试动画
     *
     * @param view
     * @param target
     */
    private void resetAnimation(View view, final View target) {
        target.setVisibility(VISIBLE);
        this.setOnClickListener(this);
        RewardView.this.setBackgroundColor(getResources().getColor(R.color.black_32));
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", 0, 0);
        translationX.start();
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", 0, 0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", 1f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", 1f, 1f);
        //创建透明度动画
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1.0f, 0f);
        //动画集合
        AnimatorSet set = new AnimatorSet();
        //添加动画
        set.play(translationX).with(translationY).with(scaleX).with(scaleY);
        //设置时间等
        set.setDuration(1);
        set.start();
        //动画监听
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == this) {
            if (img_target != null && img_target.getVisibility() == View.VISIBLE) {
                startAnimation(txt_dragView, img_target);
//                startAnimation(img_kid, img_target);

            }
            return;
        }
        switch (v.getId()) {
            case R.id.img_kid:
                if (img_target.getVisibility() == VISIBLE) {
                    startAnimation(img_kid, img_target);
                } else {
                    resetAnimation(img_kid, img_target);
                }
                break;
            case R.id.img_target:
                if (onTargetClickListener != null) {
                    onTargetClickListener.onClick(img_target);
                }
                startAnimation(txt_dragView, img_target);


                break;
            case R.id.txt_dragView:
                if (img_target.getVisibility() == VISIBLE) {
                    startAnimation(txt_dragView, img_target);
                } else {
                    resetAnimation(txt_dragView, img_target);
                }
                break;

        }
    }

    /**
     * 显示开关
     */
    public void showSpirit() {
//        if (img_kid != null) {
//            img_kid.setVisibility(View.VISIBLE);
//        }

        if (txt_dragView != null) {
            txt_dragView.setVisibility(View.VISIBLE);
        }
    }




    public void  showBannerView(){
        layout_banner_container.setVisibility(VISIBLE);

    }

    public void  dismissBannerView(){
        layout_banner_container.setVisibility(GONE);

    }

    /**
     * 隐藏广告View
     */
    public void showAdView() {
        if (img_target != null) {
            img_target.setVisibility(View.VISIBLE);
            resetAnimation(img_kid, img_target);

        }
    }


    private boolean isTouchPointInView(View targetView, int xAxis, int yAxis) {
        if (targetView == null) {
            return false;
        }
        int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + targetView.getMeasuredWidth();
        int bottom = top + targetView.getMeasuredHeight();
        if (yAxis >= top && yAxis <= bottom && xAxis >= left
                && xAxis <= right) {
            return true;
        }
        return false;
    }

    private float mDownX, mDownY, x, y;
    private int dx, dy, il, ir, it, ib;


    public void setDataList(final List<Notice> dataList) {
        this.dataList = dataList;
        ban_notice.setImageLoader(new GlideImageLoader());

        ban_notice.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int i) {
                Notice notice = dataList.get(i);
                WebActivity.start(ban_notice.getContext(), notice.getTitle(), notice.getContentUrl());
            }
        });
        //设置图片集合
        ban_notice.setImages(dataList);
        //banner设置方法全部调用完毕时最后调用
        ban_notice.start();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        return false;
    }

    /**
     * 提交信息
     *
     * @param url
     */
    public void setTargetImage(String url) {
        Glide.with(getContext()).load(url).into(img_target);
    }


}
