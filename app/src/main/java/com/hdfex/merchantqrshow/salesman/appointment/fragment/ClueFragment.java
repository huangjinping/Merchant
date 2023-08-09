package com.hdfex.merchantqrshow.salesman.appointment.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.appointment.SubscribeItem;
import com.hdfex.merchantqrshow.bean.salesman.appointment.TurnRequest;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.customer.activity.BIZListActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.MessageActivity;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.keyboardvisibilityevent.KeyboardManager;
import com.hdfex.merchantqrshow.utils.keyboardvisibilityevent.KeyboardVisibilityEvent;
import com.hdfex.merchantqrshow.utils.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import com.hdfex.merchantqrshow.utils.keyboardvisibilityevent.SoftKeyBoardListener;
import com.nineoldandroids.animation.AnimatorSet;

import rx.Subscriber;

/**
 * 线索
 * author Created by harrishuang on 2017/9/8.
 * email : huangjinping@hdfex.com
 */

public class ClueFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private final int OPEN_TYPE = 1;
    private final int CLOASE_TYPE = 0;
    private RadioButton rad_order_first;
    private RadioButton rad_order_secend;
    private RadioGroup rag_segment;
    private LinearLayout layout_content;
    private FragmentManager mFragmentManager;
    private LinearLayout layout_clue_top;
    private LinearLayout layout_public_calue;
    private RelativeLayout layout_public_nav;
    private LinearLayout layout_public_content;
    private TextView txt_transfer_order;
    private LinearLayout layout_turn_content;
    private EditText edt_input_desc;
    private SubscribeItem item;
    private ImageView img_cancel;
    private ImageView img_arrow_down;
    private TextView txt_public_left_title;
    private TextView txt_public_center_title;
    private TextView tv_message;
    private AnimatorSet mAnimatorSet;

    private KeyboardManager keyboardManager;


    public static ClueFragment getInstance() {
        ClueFragment fragment = new ClueFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_clue, container, false);
        initView(view);
        keyboardManager = new KeyboardManager();

        mAnimatorSet = new AnimatorSet();

        mFragmentManager = getChildFragmentManager();
        setCurrentFragment(R.id.rad_order_first);

        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                mAnimatorSet.playTogether(
                        com.nineoldandroids.animation.ObjectAnimator.ofFloat(layout_turn_content, "translationY", 0, -edt_input_desc.getHeight() + 40).setDuration(200)
                );
                mAnimatorSet.start();
            }

            @Override
            public void keyBoardHide(int height) {

                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_turn_content.getLayoutParams();
                layoutParams.bottomMargin = 1;
                layout_turn_content.setLayoutParams(layoutParams);
                layout_turn_content.invalidate();
                layout_turn_content.setVisibility(View.GONE);
                mAnimatorSet.playTogether(
                        com.nineoldandroids.animation.ObjectAnimator.ofFloat(layout_turn_content, "translationY", -height, 0).setDuration(200)
                );
                mAnimatorSet.start();

            }
        });


        KeyboardVisibilityEvent.setEventListener(getActivity(), new KeyboardVisibilityEventListener() {

            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_turn_content.getLayoutParams();
//                    layoutParams.bottomMargin = edt_input_desc.getHeight();
//                    layout_turn_content.setLayoutParams(layoutParams);
//                    layout_turn_content.invalidate();
                }
            }
        });

        EventRxBus.getInstance().register(IntentFlag.TURN_DESC_KEY).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(Object o) {
                item = (SubscribeItem) o;
                requestOpenTurnDesc();
            }
        });


        layout_public_calue.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                try {
                    // 务必取消监听，否则会多次调用
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                        layout_public_calue.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        layout_public_calue.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    ObjectAnimator animator = ObjectAnimator.ofFloat(layout_public_calue, "translationY", 0, layout_content.getHeight());
                    animator.setDuration(1);
                    animator.start();
                    layout_public_calue.setTag(OPEN_TYPE);
                    FragmentManager childFragmentManager = getChildFragmentManager();
                    FragmentTransaction transcation = childFragmentManager.beginTransaction();
                    transcation.replace(R.id.layout_public_content, ReservationListFragment.getInstance("03")).commit();

                    txt_public_left_title.setVisibility(View.VISIBLE);
                    img_arrow_down.setVisibility(View.GONE);
                    txt_public_center_title.setVisibility(View.GONE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }


    /**
     * 显示界面
     */
    private void requestOpenTurnDesc() {
        layout_turn_content.setVisibility(View.VISIBLE);
        edt_input_desc.setFocusable(true);
        edt_input_desc.setFocusableInTouchMode(true);
        edt_input_desc.requestFocus();
        //打开软键盘
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private void initView(View view) {
        rad_order_first = (RadioButton) view.findViewById(R.id.rad_order_first);
        rad_order_secend = (RadioButton) view.findViewById(R.id.rad_order_secend);
        rag_segment = (RadioGroup) view.findViewById(R.id.rag_segment);
        layout_content = (LinearLayout) view.findViewById(R.id.layout_content);
        rag_segment.setOnCheckedChangeListener(this);
        layout_clue_top = (LinearLayout) view.findViewById(R.id.layout_clue_top);
        layout_clue_top.setOnClickListener(this);
        layout_public_calue = (LinearLayout) view.findViewById(R.id.layout_public_calue);
        layout_public_nav = (RelativeLayout) view.findViewById(R.id.layout_public_nav);
        layout_public_nav.setOnClickListener(this);
        layout_public_content = (LinearLayout) view.findViewById(R.id.layout_public_content);
        layout_public_content.setOnClickListener(this);
        txt_transfer_order = (TextView) view.findViewById(R.id.txt_transfer_order);
        txt_transfer_order.setOnClickListener(this);
        layout_turn_content = (LinearLayout) view.findViewById(R.id.layout_turn_content);
        layout_turn_content.setOnClickListener(this);
        edt_input_desc = (EditText) view.findViewById(R.id.edt_input_desc);
        edt_input_desc.setOnClickListener(this);
        img_cancel = (ImageView) view.findViewById(R.id.img_cancel);
        img_cancel.setOnClickListener(this);
        img_arrow_down = (ImageView) view.findViewById(R.id.img_arrow_down);
        txt_public_left_title = (TextView) view.findViewById(R.id.txt_public_left_title);
        txt_public_center_title = (TextView) view.findViewById(R.id.txt_public_center_title);
        tv_message = (TextView) view.findViewById(R.id.tv_message);
        tv_message.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        setCurrentFragment(checkedId);
    }

    /**
     * 设置当前试图
     *
     * @param checkedId
     */
    private void setCurrentFragment(int checkedId) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.rad_order_first:
                fragmentTransaction.replace(R.id.layout_fragment_content, FollowOrderFragment.getInstance());
                break;
            case R.id.rad_order_secend:
                fragmentTransaction.replace(R.id.layout_fragment_content, CustomerReservationFragment.getInstance());
                break;
        }
        fragmentTransaction.commit();
    }


    private void openAnimation(final LinearLayout layout, FragmentManager supportFragmentManager) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(layout, "translationY", -layout.getHeight(), 0);
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationStart(Animator animation) {
                layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        animator.start();

    }


    public void closeAnimation(final LinearLayout layout) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(layout, "translationY", layout_content.getHeight(), 0);
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationStart(Animator animation) {
                layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                txt_public_left_title.setVisibility(View.GONE);
                img_arrow_down.setVisibility(View.VISIBLE);
                txt_public_center_title.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        animator.start();
    }

    /**
     * 文件数据问题
     * @param layout
     */
    public void openAnimation(final LinearLayout layout) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(layout, "translationY", 0, layout_content.getHeight());
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
            @Override
            public void onAnimationStart(Animator animation) {
                layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                txt_public_left_title.setVisibility(View.VISIBLE);
                img_arrow_down.setVisibility(View.GONE);
                txt_public_center_title.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        animator.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_public_nav:
                if (OPEN_TYPE == (int) layout_public_calue.getTag()) {
                    closeAnimation(layout_public_calue);
                    layout_public_calue.setTag(CLOASE_TYPE);
                } else {
                    openAnimation(layout_public_calue);
                    layout_public_calue.setTag(OPEN_TYPE);
                }
                break;
            case R.id.txt_transfer_order:
                onTurn();
                break;
            case R.id.img_cancel:
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt_input_desc.getWindowToken(), 0);
                break;
            case R.id.tv_message:
                if (isLogin()) {
                    MessageActivity.startAction(getActivity());
                }
                break;
        }
    }

    /**
     * 转单描述
     */
    private void onTurn() {
        String desc = edt_input_desc.getText().toString().trim();
        if (TextUtils.isEmpty(desc)) {
            showToast("请输入转单描述");
            return;
        }
        TurnRequest turnReqeust = new TurnRequest();
        turnReqeust.setSubscribeId(item.getSubscribeId());
        turnReqeust.setTurnDesc(desc);
        BIZListActivity.startAction(getActivity(), turnReqeust);
    }

}
