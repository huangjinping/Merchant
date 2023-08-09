package com.hdfex.merchantqrshow.salesman.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.home.Adcommodity;
import com.hdfex.merchantqrshow.bean.salesman.home.HomeResponse;
import com.hdfex.merchantqrshow.bean.salesman.home.HomeResult;
import com.hdfex.merchantqrshow.bean.salesman.home.WeekCust;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.MainContract;
import com.hdfex.merchantqrshow.mvp.presenter.MainPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.main.activity.ScanRecordActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.LoadAppForCActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.MessageActivity;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.HJPLineChartView;
import com.hdfex.merchantqrshow.view.autoscroll.AutoScrollView;
import com.hdfex.merchantqrshow.widget.DateUtils;
import com.hdfex.sdk.pullrefreshlayout.BGANormalComRefreshViewHolder;
import com.hdfex.sdk.pullrefreshlayout.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 首页数据
 * Created by harrishuang on 16/9/27.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate,MainContract.View {
    private RelativeLayout layout_line_content;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private TextView txt_left_name;
    private HJPLineChartView line_chart;
    private User user;
    private TextView txt_monthTotAmount;
    private TextView txt_checkingOrders;
    private TextView txt_payingOrders;
    private TextView txt_monthCustCount;
    private TextView txt_totCustCount;
    private LinearLayout layout_constomer_null;
    private BGARefreshLayout refreshLayout;
    private LinearLayout layout_child_content;
    private ScrollView scrl_childView;
    private DisplayMetrics displayMetrics;
    private LinearLayout layout_checkingOrders;
    private LinearLayout layout_payingOrders;
    private OrderDelegate delegate;
    private LinearLayout layout_auto;
    private List<Adcommodity> mLoopAdcommodity;
    private MainContract.Presenter presenter;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        user = UserManager.getUser(getActivity());
        mLoopAdcommodity = new ArrayList<>();
        presenter=new MainPresenter();
        presenter.attachView(this);
        initView(view);
        BGANormalComRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalComRefreshViewHolder(getActivity(), false);
        refreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        refreshLayout.setPullDownRefreshEnable(true);
        refreshLayout.setDelegate(this);
        initData();
        addView();
        handler.sendMessageDelayed(handler.obtainMessage(), 100);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserManager.isLogin(getActivity())) {
            loadData();
            EventRxBus.getInstance().post(IntentFlag.RED_LOAD, IntentFlag.RED_LOAD);
        }
    }

    private void initData() {
        String[] xdate = new String[]{"09.20", "09.21", "09.22", "09.23", "09.24", "09.25", "09.26"};
        String[] ydata = line_chart.getFundWeekYdata("5", "1");
        float[] data1 = new float[]{4.00f, 2.00f, 3.40f, 2.50f, 5f};
        line_chart.setData(xdate, ydata, data1);
    }

    private void initView(View view) {
        layout_line_content = (RelativeLayout) view.findViewById(R.id.layout_line_content);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        tb_tv_titile = (TextView) view.findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) view.findViewById(R.id.iv_setting);
        tv_home = (TextView) view.findViewById(R.id.tv_home);
//        tv_home.setVisibility(View.VISIBLE);
        tv_home.setText("消息");
        tv_home.setTextColor(getResources().getColor(R.color.black));
        tv_home.setOnClickListener(this);
        layout_toolbar = (LinearLayout) view.findViewById(R.id.layout_toolbar);
        tb_tv_titile.setText("统计");
        tb_tv_titile.setTextColor(getResources().getColor(R.color.black));
        layout_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        txt_left_name = (TextView) view.findViewById(R.id.txt_left_name);

        img_back.setImageResource(R.mipmap.ic_order_normal);
        line_chart = (HJPLineChartView) view.findViewById(R.id.line_chart);
        txt_monthTotAmount = (TextView) view.findViewById(R.id.txt_monthTotAmount);
        txt_checkingOrders = (TextView) view.findViewById(R.id.txt_checkingOrders);
        txt_payingOrders = (TextView) view.findViewById(R.id.txt_payingOrders);
        txt_monthCustCount = (TextView) view.findViewById(R.id.txt_monthCustCount);
        txt_totCustCount = (TextView) view.findViewById(R.id.txt_totCustCount);

        layout_constomer_null = (LinearLayout) view.findViewById(R.id.layout_constomer_null);
        layout_constomer_null.setOnClickListener(this);
        refreshLayout = (BGARefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnClickListener(this);
        layout_child_content = (LinearLayout) view.findViewById(R.id.layout_child_content);
        scrl_childView = (ScrollView) view.findViewById(R.id.scrl_childView);
        layout_checkingOrders = (LinearLayout) view.findViewById(R.id.layout_checkingOrders);
        layout_checkingOrders.setOnClickListener(this);
        layout_payingOrders = (LinearLayout) view.findViewById(R.id.layout_payingOrders);
        layout_payingOrders.setOnClickListener(this);
        layout_auto = (LinearLayout) view.findViewById(R.id.layout_auto);
        img_back.setOnClickListener(this);
        img_back.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
            case R.id.txt_left_name:
                Intent intent = new Intent(getActivity(), ScanRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_checkingOrders:
                if (delegate != null) {
                    delegate.onSelection(1);
                }
                break;
            case R.id.layout_payingOrders:
                if (delegate != null) {
                    delegate.onSelection(3);
                }
                break;
            case R.id.tv_home:
                MessageActivity.startAction(getActivity());
                break;
        }
    }

    /**
     * 下载数据
     */
    private void loadData() {
        if (!connect()) {
            return;
        }
        OkHttpUtils
                .post()
                .url(NetConst.INDEX)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
//                        dismissProgress();
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    refreshLayout.endLoadingMore();
                                    refreshLayout.endRefreshing();
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
//                        showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {


                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                HomeResponse homeResponse = GsonUtil.changeGsonToBean(response, HomeResponse.class);
                                HomeResult result = homeResponse.getResult();
                                setViewByData(result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                });
    }

    /**
     * 结果数据
     *
     * @param result
     */
    private void setViewByData(HomeResult result) {
        if (result.getMonthTotAmount() != null) {
            txt_monthTotAmount.setText(result.getMonthTotAmount().toString());
        }
        if (!TextUtils.isEmpty(result.getCheckingOrders())) {
            txt_checkingOrders.setText(result.getCheckingOrders());
        }
        if (!TextUtils.isEmpty(result.getPayingOrders())) {
            txt_payingOrders.setText(result.getPayingOrders());
        }
        if (!TextUtils.isEmpty(result.getMonthCustCount())) {
            txt_monthCustCount.setText(result.getMonthCustCount());
        }
        if (!TextUtils.isEmpty(result.getTotCustCount())) {
            txt_totCustCount.setText(result.getTotCustCount());
        }
        mLoopAdcommodity.clear();
        mLoopAdcommodity.addAll(result.getBanners());
        addView();

        List<WeekCust> weekCustList = result.getWeekCustList();

        String[] xdate = new String[weekCustList.size()];

        int max = 0;
        int min = 0;
        float[] data = new float[weekCustList.size()];
        for (int i = 0; i < weekCustList.size(); i++) {
            WeekCust weekCust = weekCustList.get(i);
            String mathAndDay = DateUtils.getMathAndDay(weekCust.getDate());
            xdate[i] = mathAndDay;
            int count = Integer.parseInt(weekCust.getCustCount());
            if (max <= count) {
                max = count;
            }
            if (count <= min) {
                min = count;
            }
            data[i] = count;
        }
        if (max == 0) {
            layout_constomer_null.setVisibility(View.VISIBLE);
        } else {
            layout_constomer_null.setVisibility(View.GONE);
        }
//        String[] xdate = new String[]{"09.20", "09.21", "09.22", "09.23", "09.24", "09.25", "09.26"};
        String[] ydata = line_chart.getFundWeekYdata(max + "", min + "");
        line_chart.setData(xdate, ydata, data);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadData();
        Intent mIntent = new Intent(getActivity(), LoadAppForCActivity.class);
        mIntent.putExtra(IntentFlag.INTENT_NAME, IntentFlag.MAIN);
        startActivity(mIntent);

        getActivity().overridePendingTransition(R.anim.activity_down_in_slow, R.anim.activity_down_out_slow);
    }


    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    Handler handler = new Handler() {
        @Override
        public String getMessageName(Message message) {

            return super.getMessageName(message);
        }
    };


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            delegate = (OrderDelegate) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (this.isAdded()&&this.isResumed()){
        }
    }

    public interface OrderDelegate {
        void onSelection(int status);
    }

    private void addView() {
        layout_auto.removeAllViews();
        AutoScrollView autoScrollView = new AutoScrollView(getActivity());
        autoScrollView.initCarouslview(mLoopAdcommodity);
        layout_auto.addView(autoScrollView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
