package com.hdfex.merchantqrshow.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.utils.OnComTabSelectListener;
import com.hdfex.merchantqrshow.utils.TabEntity;

import java.util.ArrayList;

/**
 * Created by harrishuang on 2017/4/24.
 */

public class ComTabLayout extends FrameLayout {


    private Context mContext;

    private int mCurrentTab;

    private int mTabCount;


    private OnComTabSelectListener mListener;

    private int mTextSelectColor;
    private int mTextUnselectColor;

    public void setOnTabSelectListener(OnComTabSelectListener listener) {
        this.mListener = listener;
    }


    private ArrayList<CustomTabEntity> mTabEntitys = new ArrayList<>();

    private LinearLayout mTabsContainer;

    public ComTabLayout(@NonNull Context context) {
        this(context, null, 0);
    }

    public ComTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mTabsContainer = new LinearLayout(context);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        addView(mTabsContainer);
        obtainAttributes(context, attrs);

    }

    /**
     * 设置属性
     *
     * @param context
     * @param attrs
     */
    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ComTabLayout);
        mTextSelectColor = ta.getColor(R.styleable.ComTabLayout_mTextSelectColor, Color.parseColor("#ffffff"));
        mTextUnselectColor = ta.getColor(R.styleable.ComTabLayout_mTextUnselectColor, Color.parseColor("#AAffffff"));
    }

    /**
     * @param tabEntitys
     */
    public void setTabData(ArrayList<TabEntity> tabEntitys) {
        if (tabEntitys == null || tabEntitys.size() == 0) {
            throw new IllegalStateException("TabEntitys can not be NULL or EMPTY !");
        }

        this.mTabEntitys.clear();
        this.mTabEntitys.addAll(tabEntitys);
        notifyDataSetChanged();
    }

    /**
     * 更新数据
     */
    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        this.mTabCount = mTabEntitys.size();
        for (int i = 0; i < mTabCount; i++) {
            View tabView = View.inflate(mContext, R.layout.item_comtab, null);
            tabView.setTag(i);
            addTab(i, tabView);
        }
        updateTabStyles();
    }

    /**
     * updateTabStyles更新数据
     */
    private void updateTabStyles() {


        Log.d("okhttp", mTextSelectColor + "=====================" + mTextUnselectColor);
        for (int i = 0; i < mTabCount; i++) {
            View tabView = mTabsContainer.getChildAt(i);
            TextView tabName = (TextView) tabView.findViewById(R.id.txt_tab_name);
            ImageView img_tab_icon = (ImageView) tabView.findViewById(R.id.img_tab_icon);
            tabName.setTextColor(i == mCurrentTab ? mTextSelectColor : mTextUnselectColor);

        }
    }

    /**
     * @param position
     * @param tabView
     */
    private void addTab(final int position, View tabView) {
        final TextView tabName = (TextView) tabView.findViewById(R.id.txt_tab_name);
        final ImageView img_tab_icon = (ImageView) tabView.findViewById(R.id.img_tab_icon);
        CustomTabEntity customTabEntity = mTabEntitys.get(position);
        tabName.setTag(false);
        /**
         *
         */
        if (mTabEntitys.get(position).getTabUnselectedIcon() == 0) {
            img_tab_icon.setVisibility(View.GONE);
        } else {
            img_tab_icon.setImageResource(mTabEntitys.get(position).getTabUnselectedIcon());
            img_tab_icon.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(customTabEntity.getTabTitle())) {
            /**
             *书写文字
             */
            tabName.setText(customTabEntity.getTabTitle());
        } else {
            tabName.setText("");
        }
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                if (mCurrentTab != position) {
                    setCurrentTab(position);
                    if (mListener != null) {
                        mListener.onTabSelect(position);
                    }
                } else {
                    if (mListener != null) {
                        if ((boolean) tabName.getTag()) {
                            tabName.setTag(false);
                            mListener.onTabReselect(position, false);
                            if ((mTabEntitys.get(position).getTabUnselectedIcon() != 0) && (mTabEntitys.get(position).getTabSelectedIcon() != 0)) {
                                img_tab_icon.setImageResource(mTabEntitys.get(position).getTabUnselectedIcon());
                            }
                        } else {
                            tabName.setTag(true);
                            mListener.onTabReselect(position, true);
                            if ((mTabEntitys.get(position).getTabUnselectedIcon() != 0) && (mTabEntitys.get(position).getTabSelectedIcon() != 0)) {
                                img_tab_icon.setImageResource(mTabEntitys.get(position).getTabSelectedIcon());

                            }
                        }
                    }
                }
            }
        });
        LinearLayout.LayoutParams lp_tab = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        mTabsContainer.addView(tabView, position, lp_tab);
    }

    public void setCurrentTab(int currentTab) {
        this.mCurrentTab = currentTab;
        updateTabSelection(currentTab);
    }








    public void  resetTabSelection(int position){
        this.mCurrentTab = position;
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            TextView tab_title = (TextView) tabView.findViewById(R.id.txt_tab_name);
            tab_title.setTag(false);
            tab_title.setTextColor(isSelect ? mTextSelectColor : mTextUnselectColor);
            ImageView iv_tab_icon = (ImageView) tabView.findViewById(R.id.img_tab_icon);
            CustomTabEntity tabEntity = mTabEntitys.get(i);
            iv_tab_icon.setImageResource(tabEntity.getTabUnselectedIcon());

        }
    }


    /**
     * @param position
     */
    public void updateTabSelection(int position) {
        this.mCurrentTab = position;
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            TextView tab_title = (TextView) tabView.findViewById(R.id.txt_tab_name);
            if (!isSelect) {
                tab_title.setTag(false);
            } else {
                tab_title.setTag(true);
            }
            tab_title.setTextColor(isSelect ? mTextSelectColor : mTextUnselectColor);
            ImageView iv_tab_icon = (ImageView) tabView.findViewById(R.id.img_tab_icon);
            CustomTabEntity tabEntity = mTabEntitys.get(i);
            iv_tab_icon.setImageResource(isSelect?tabEntity.getTabSelectedIcon():tabEntity.getTabUnselectedIcon());

        }
    }
}
