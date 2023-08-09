package com.hdfex.merchantqrshow.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hdfex.merchantqrshow.utils.log.LogUtil;

/**
 * 自定义宽度 下拉刷新 加载更多
 * Created by maidou521 on 2016/6/23.
 */
public class LinkListView extends ListView implements AbsListView.OnScrollListener {
    public LinkListView(Context context) {
        super(context);
        initThis(context);
    }

    public LinkListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initThis(context);
    }

    public LinkListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initThis(context);
    }

    /**
     * 可自定义宽度的listView
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            int childcount = getChildCount();
            for (int i = 0; i < childcount; i++) {
                View child = getChildAt(i);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                width = Math.max(width, child.getMeasuredWidth());
            }
        }
        setMeasuredDimension(width, height);
    }


    /**
     * 监听器
     * 监听控件的刷新或者加载更多事件
     * 所有的条目事件都会有一个偏移量，也就是position应该减1才是你适配器中的条目
     */
    public interface LinkListViewListener {
        /**
         * @param linkListView
         * @param isRefresh    为true的时候代表的是刷新，为false的时候代表的是加载更多
         * @return true:刷新或者加载更多动作完成，刷新或者加载更多的动画自动消失 false:刷新或者加载更多未完成，
         * 需要在数据加载完成之后去调用控件的doneRefresh()或者doneMore()方法
         */
        public boolean onRefreshOrMore(LinkListView linkListView, boolean isRefresh);
    }

    /**
     * 状态控件（StatusView，列表头上和底端的）的状态枚举
     */
    enum RefreshStatus {
        none, normal, willrefresh, refreshing
    }

    /**
     * 状态控件
     */
    class StatusView extends LinearLayout {
        public int height;
        public int width;
        private ProgressBar progressBar = null;
        private TextView textView = null;
        private RefreshStatus refreshStatus = RefreshStatus.none;
        private String normalString = "下拉刷新";
        private String willrefreshString = "松开刷新";
        private String refreshingString = "正在刷新";

        public StatusView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initThis(context);
        }

        public StatusView(Context context) {
            super(context);
            initThis(context);
        }

        private void initThis(Context context) {
            this.setOrientation(LinearLayout.HORIZONTAL);
            this.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

            progressBar = new ProgressBar(context);
            progressBar.setLayoutParams(new LinearLayout.LayoutParams(30, 30));
            textView = new TextView(context);
            textView.setPadding(5, 0, 0, 0);

            this.addView(progressBar);
            this.addView(textView);

            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            this.measure(w, h);

            height = this.getMeasuredHeight();
            width = this.getMeasuredWidth();

            this.setRefreshStatus(RefreshStatus.normal);
        }

        public RefreshStatus getRefreshStatus() {
            return refreshStatus;
        }

        public void setRefreshStatus(RefreshStatus refreshStatus) {
            if (this.refreshStatus != refreshStatus) {
                this.refreshStatus = refreshStatus;
                if (refreshStatus == RefreshStatus.refreshing) {
                    this.progressBar.setVisibility(View.VISIBLE);
                } else {
                    this.progressBar.setVisibility(View.GONE);
                }
                refreshStatusString();
                this.invalidate();
            }
        }

        private void refreshStatusString() {
            switch (refreshStatus) {
                case normal:
                    textView.setText(normalString);
                    progressBar.setProgress(0);
                    break;
                case willrefresh:
                    textView.setText(willrefreshString);
                    break;
                case refreshing:
                    textView.setText(refreshingString);
                    break;
                default:
                    break;
            }
        }

        /**
         * 设置状态字符串
         *
         * @param normalString      平时的字符串
         * @param willrefreshString 松开后刷新（或加载）的字符串
         * @param refreshingString  正在刷新（或加载）的字符串
         */
        public void setStatusStrings(String normalString, String willrefreshString, String refreshingString) {
            this.normalString = normalString;
            this.willrefreshString = willrefreshString;
            this.refreshingString = refreshingString;
            this.refreshStatusString();
        }
    }

    private StatusView refreshView;
    private StatusView moreView;
    private int itemFlag = -1;
    private boolean isRecorded = false;
    private int downY = -1;
    private final float minTimesToRefresh = 1.5f;
    private final static int ITEM_FLAG_FIRST = 1;
    private final static int ITEM_FLAG_NONE = 0;
    private final static int ITEM_FLAG_LAST = -1;

    // 两个监听器
    private LinkListViewListener onRefreshListener;
    private LinkListViewListener onMoreListener;
    // 滚动到低端的时候是否自动加载更多
    private boolean doMoreWhenBottom = false;

    private void initThis(Context context) {
        refreshView = new StatusView(context);
        moreView = new StatusView(context);
        refreshView.setStatusStrings("下拉刷新", "松开刷新", "正在刷新");
        moreView.setStatusStrings("上拉加载更多", "松开加载", "正在加载");
        this.addHeaderView(refreshView, null, false);
        this.addFooterView(moreView, null, false);
        this.setOnScrollListener(this);
        doneRefresh();
        doneMore();
    }

    public void removeHeaderOrFoot(boolean isheader){
        if(isheader){
            this.removeHeaderView(refreshView);
        }else{
            this.removeFooterView(moreView);
        }
    }
    // 监听器操作
    public LinkListViewListener getOnRefreshListener() {
        return onRefreshListener;
    }

    public void setOnRefreshListener(LinkListViewListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public LinkListViewListener getOnMoreListener() {
        return onMoreListener;
    }

    public void setOnMoreListener(LinkListViewListener onMoreListener) {
        this.onMoreListener = onMoreListener;
    }

    // 设置
    public boolean isDoMoreWhenBottom() {
        return doMoreWhenBottom;
    }

    public void setDoMoreWhenBottom(boolean doMoreWhenBottom) {
        this.doMoreWhenBottom = doMoreWhenBottom;
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0)
            itemFlag = ITEM_FLAG_FIRST;
        else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
            itemFlag = ITEM_FLAG_LAST;
            if (doMoreWhenBottom && onMoreListener != null && moreView.getRefreshStatus() != RefreshStatus.refreshing) {
                doMore();
            }
        } else {
            itemFlag = ITEM_FLAG_NONE;
//          isRecorded = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isRecorded == false && (itemFlag == ITEM_FLAG_FIRST && onRefreshListener != null && refreshView.getRefreshStatus() == RefreshStatus.normal || itemFlag == ITEM_FLAG_LAST && onMoreListener != null && moreView.getRefreshStatus() == RefreshStatus.normal)) {
                    downY = (int) ev.getY(0);
                    isRecorded = true;
//              LogUtil.e("按下，记录：%d flag:%d", downY+"....."+itemFlag);
                }
                break;
            case MotionEvent.ACTION_UP: {
                isRecorded = false;
                if (onRefreshListener != null && refreshView.getRefreshStatus() == RefreshStatus.willrefresh) {
                    doRefresh();
                } else if (refreshView.getRefreshStatus() == RefreshStatus.normal) {
                    refreshView.setPadding(0, -1 * refreshView.height, 0, 0);
                }

                if (onMoreListener != null && moreView.getRefreshStatus() == RefreshStatus.willrefresh) {
                    doMore();
                } else if (moreView.getRefreshStatus() == RefreshStatus.normal) {
                    moreView.setPadding(0, 0, 0, -1 * moreView.height);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
//                LogUtil.e("按下", "移动0");
                if (isRecorded == false && (itemFlag == ITEM_FLAG_FIRST && onRefreshListener != null && refreshView.getRefreshStatus() == RefreshStatus.normal ||
                        itemFlag == ITEM_FLAG_LAST && onMoreListener != null && moreView.getRefreshStatus() == RefreshStatus.normal)) {
                    downY = (int) ev.getY(0);
                    isRecorded = true;
//                    LogUtil.e("按下", "移动1");
//              LogUtil.e("按下，记录：%d flag:%d", downY+"..."+itemFlag);
                } else if (isRecorded) {
//                    LogUtil.e("按下", "移动2");
                    int nowY = (int) ev.getY(0);
                    int offset = nowY - downY;
                    if (offset > 0 && itemFlag == ITEM_FLAG_FIRST) {
                        // 下拉
//                        setSelection(0);
//                        LogUtil.e("按下", "移动3");
                        if (offset >= (minTimesToRefresh * refreshView.height)) {
                            refreshView.setRefreshStatus(RefreshStatus.willrefresh);
                        } else {
                            refreshView.setRefreshStatus(RefreshStatus.normal);
                        }

                        refreshView.setPadding(0, -1 * (refreshView.height - offset), 0, 0);
                    } else if (itemFlag == ITEM_FLAG_LAST) {
                        // 上拉
//                        setSelection(this.getCount());
//                        LogUtil.e("按下", "移动4");
                        if (offset <= -1 * (minTimesToRefresh * moreView.height)) {
                            moreView.setRefreshStatus(RefreshStatus.willrefresh);
                        } else {
                            moreView.setRefreshStatus(RefreshStatus.normal);
                        }
                        moreView.setPadding(0, 0, 0, -1 * (moreView.height + offset));
                    }
//              LogUtil.e("位移:%d", offset);
                }
                break;
            }
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 开始刷新
     */
    private void doRefresh() {
//      log("开始刷新");
        refreshView.setRefreshStatus(RefreshStatus.refreshing);
        refreshView.setPadding(0, 0, 0, 0);
        if (onRefreshListener.onRefreshOrMore(this, true))
            doneRefresh();
    }

    /**
     * 开始加载更多
     */
    private void doMore() {
//      log("加载更多");
        moreView.setRefreshStatus(RefreshStatus.refreshing);
        moreView.setPadding(0, 0, 0, 0);
        if (onMoreListener.onRefreshOrMore(this, false))
            doneMore();
    }

    /**
     * 刷新完成之后调用，用于取消刷新的动画
     */
    public void doneRefresh() {
//      log("刷新完成!");
        refreshView.setRefreshStatus(RefreshStatus.normal);
        refreshView.setPadding(0, -1 * refreshView.height, 0, 0);
    }

    /**
     * 加载更多完成之后调用，用于取消加载更多的动画
     */
    public void doneMore() {
//      log("加载完成!");
        moreView.setRefreshStatus(RefreshStatus.normal);
        moreView.setPadding(0, 0, 0, -1 * moreView.height);
    }

    /**
     * 获取刷新的状态
     *
     * @return 一般 将要刷新 刷新完成
     */
    public RefreshStatus getRefreshStatus() {
        return refreshView.getRefreshStatus();
    }

    /**
     * 获取加载更多的状态
     *
     * @return 一般 将要加载 加载完成
     */
    public RefreshStatus getMoreStatus() {
        return moreView.getRefreshStatus();
    }

}
