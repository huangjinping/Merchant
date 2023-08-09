package com.hdfex.merchantqrshow.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.utils.OnComTabSelectListener;

import java.util.List;

/**
 * Created by harrishuang on 2017/4/24.
 */

public class SpannerView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private int currentTab;
    private LinearLayout mTabsContainer;
    private int position;


    public SpannerView(Context context) {
        this(context, null, 0);
    }

    public SpannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mTabsContainer = new LinearLayout(mContext);
        mTabsContainer.setOrientation(VERTICAL);
        addView(mTabsContainer);
    }

    private OnComTabSelectListener mListener;


    public void setOnComTabSelectListener(OnComTabSelectListener mListener) {
        this.mListener = mListener;
    }

    /**
     * @param item
     * @param position
     * @param
     */
    public void showItem(List<String> item, int position, int tab) {
        if (item == null) {
            return;
        }
        this.setVisibility(VISIBLE);
        currentTab = tab;
        this.position = position;
        mTabsContainer.removeAllViews();
        for (int i = 0; i < item.size(); i++) {
            TextView view = (TextView) View.inflate(mContext, R.layout.item_spanner, null);
            view.setSelected(false);
            LinearLayout.LayoutParams lp_tab = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mTabsContainer.addView(view, lp_tab);
            view.setText(item.get(i));
            view.setTag(i);
            view.setOnClickListener(this);
        }


    }


    public void show() {
        this.setVisibility(VISIBLE);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        notifyDataSetChanged();
    }

    /**
     * 界面的刷新
     */
    private void notifyDataSetChanged() {
        LinearLayout.LayoutParams lp_tab = new LinearLayout.LayoutParams(getWidth() / 4, LayoutParams.WRAP_CONTENT);
        lp_tab.leftMargin = currentTab * (getWidth() / 4);
        mTabsContainer.setBackgroundResource(R.color.background_gray);
        for (int i = 0; i <mTabsContainer.getChildCount() ; i++) {
            mTabsContainer.getChildAt(i).setSelected(false);
        }
        if (position < mTabsContainer.getChildCount()) {
            TextView button = (TextView) mTabsContainer.getChildAt(position);
            button.setSelected(true);
        }
        mTabsContainer.setLayoutParams(lp_tab);
        mTabsContainer.invalidate();
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if (mListener != null && isChecked) {
//            if (position!=(Integer) buttonView.getTag()){
//                mListener.onTabSelect((Integer) buttonView.getTag());
//                this.setVisibility(GONE);
//            }
//        }
//    }

    public void dismiss() {
        this.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
      int  pos=  (Integer) v.getTag();
        mListener.onTabSelect(pos);
        position=pos;
        this.setVisibility(GONE);
    }
}
