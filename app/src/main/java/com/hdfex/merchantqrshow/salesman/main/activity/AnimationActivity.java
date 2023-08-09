package com.hdfex.merchantqrshow.salesman.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * author Created by harrishuang on 2018/10/23.
 * email : huangjinping@hdfex.com
 */
public class AnimationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_target;
    private ImageView img_kid;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AnimationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initView();
    }

    private void initView() {
        img_target = (ImageView) findViewById(R.id.img_target);
        img_target.setOnClickListener(this);
        img_kid = (ImageView) findViewById(R.id.img_kid);
        img_kid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_kid:
                startAnimation(img_kid, img_target);
                break;
        }
    }

    //    /**
//     * 开始动画
//     *
//     * @param view
//     */
    private void startAnimation(final View view, final View target) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int[] location2 = new int[2];
        target.getLocationInWindow(location2);
        Log.d("okhttp", location[0] + "===========" + location[1] + "");
        Log.d("okhttp", location2[0] + "=====location2======" + location2[1] + "");

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
        set.setDuration(3000);
        set.start();
        //动画监听
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                resetAnimation(view, target);
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


    private void resetAnimation(View view, final View target) {

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

}
