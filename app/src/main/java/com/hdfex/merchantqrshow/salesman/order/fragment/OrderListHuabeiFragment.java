package com.hdfex.merchantqrshow.salesman.order.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.huabei.AlipayOrder;
import com.hdfex.merchantqrshow.bean.salesman.huabei.AlipayOrderResponse;
import com.hdfex.merchantqrshow.bean.salesman.huabei.PayOrderQueryResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.AlipayOrderDetailsResponse;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderCommodityDetail;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.activity.ConfirmCommodityActivity;
import com.hdfex.merchantqrshow.salesman.order.activity.OrderHuabeiActivity;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.xlistView.XListView;
import com.hdfex.merchantqrshow.widget.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;


/**
 * 订单状态
 * Created by harrishuang on 16/9/27.
 */


public class OrderListHuabeiFragment extends BaseFragment {
    private String status;
    private List<AlipayOrder> dataList;
    private OrderListAdapter adapter;
    private XListView lisv_order_list;
    private User user;
    private int redColor;
    private int grayColor;
    private int blueColor;
    private int blackColor;
    private MultiStateView multiStateView;

    public static OrderListHuabeiFragment getInstance(String status) {
        OrderListHuabeiFragment sf = new OrderListHuabeiFragment();
        sf.status = status;
        return sf;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orderlist, container, false);
        dataList = new ArrayList<>();
        initView(view);
        adapter = new OrderListAdapter();
        lisv_order_list.setAdapter(adapter);
        redColor = getActivity().getResources().getColor(R.color.order_red);
        grayColor = getActivity().getResources().getColor(R.color.order_gray);
        blueColor = getActivity().getResources().getColor(R.color.order_blue);
        blackColor = getActivity().getResources().getColor(R.color.order_black);
        user = UserManager.getUser(getActivity());

        lisv_order_list.setPullLoadEnable(false);
        lisv_order_list.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                loadData(LoadMode.PULL_REFRSH);

            }

            @Override
            public void onLoadMore() {
                loadData(LoadMode.UP_REFRESH);

            }
        });

        lisv_order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    AlipayOrder alipayOrder = dataList.get(position - 1);
                    loadAlipayOrder(alipayOrder.getOrderNo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void initView(View view) {
        lisv_order_list = (XListView) view.findViewById(R.id.lisv_order_list);
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (UserManager.isLogin(getActivity())) {
            loadData(LoadMode.NOMAL);
        }
    }

    /**
     * 订单列表
     */
    private class OrderListAdapter extends BaseAdapter {
        private ViewHolder viewHolder;

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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_orderlist, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            AlipayOrder orderItem = dataList.get(position);
            if (!TextUtils.isEmpty(orderItem.getStatus())) {
                viewHolder.txt_order_time.setText(orderItem.getStatus());
            }
            viewHolder.txt_order_time.setTextColor(setTextViewStute(orderItem.getStatus()));

            if (!TextUtils.isEmpty(orderItem.getTotalAmount())) {
                viewHolder.txt_order_price.setText(orderItem.getTotalAmount() + "元");
            }
            if (!TextUtils.isEmpty(orderItem.getOrderDate())) {
                viewHolder.txt_coustomer_day.setText(orderItem.getOrderDate());
            }
            if (!TextUtils.isEmpty(orderItem.getOrderTime())) {
                viewHolder.txt_coustomer_time.setText(orderItem.getOrderTime());
            }

            if (!TextUtils.isEmpty(orderItem.getCustName())) {
                viewHolder.txt_username.setText(orderItem.getCustName());
            }


            if (!TextUtils.isEmpty(orderItem.getCommodityName())) {
                viewHolder.txt_order_address.setText(orderItem.getCommodityName());

            }


            return convertView;
        }


        private int setTextViewStute(String status) {
            int color = grayColor;

            if ("审核中".equals(status)) {
                color = blueColor;
            } else if ("已打回".equals(status)) {
                color = redColor;
            } else if ("已取消".equals(status)) {
                color = grayColor;
            } else if ("已通过".equals(status)) {
                color = blackColor;
            } else if ("还款中".equals(status)) {
                color = blueColor;
            } else if ("已完成".equals(status)) {
                color = blackColor;
            } else if ("已拒绝".equals(status)) {
                color = grayColor;
            }
            return color;
        }


        /**
         * @param orderCommoditys
         * @return
         */
        private String getAddressByCommodity(ArrayList<OrderCommodityDetail> orderCommoditys) {
            String s = "";

            try {
                StringBuffer StringBuffer = new StringBuffer();
                if (orderCommoditys == null || orderCommoditys.size() == 0) {
                    return StringBuffer.toString();
                }
                for (OrderCommodityDetail detail : orderCommoditys) {
                    if (!TextUtils.isEmpty(detail.getCommodityAddress())) {
                        StringBuffer.append(detail.getCommodityAddress());
                        StringBuffer.append(",");
                    } else {
                        StringBuffer.append(detail.getCommodityName());
                        StringBuffer.append(",");
                    }
                }
                s = StringBuffer.toString();
                if (s.endsWith(",")) {
                    s = s.substring(0, s.length() - 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return s;
        }


        /**
         * 数据问题
         */
        public final class ViewHolder {
            public View rootView;
            public TextView txt_coustomer_day;
            public TextView txt_coustomer_time;
            public TextView txt_username;
            public TextView txt_order_price;
            public TextView txt_order_time;
            public TextView txt_order_address;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.txt_coustomer_day = (TextView) rootView.findViewById(R.id.txt_coustomer_day);
                this.txt_coustomer_time = (TextView) rootView.findViewById(R.id.txt_coustomer_time);
                this.txt_username = (TextView) rootView.findViewById(R.id.txt_username);
                this.txt_order_price = (TextView) rootView.findViewById(R.id.txt_order_price);
                this.txt_order_time = (TextView) rootView.findViewById(R.id.txt_order_time);
                this.txt_order_address = (TextView) rootView.findViewById(R.id.txt_order_address);
            }

        }
    }


    /**
     * 下载数据
     *
     * @param loadMode
     */
    private int page = 0;

    private void loadData(final LoadMode loadMode) {
        if (!connect()) {
            return;
        }

        if (loadMode == LoadMode.NOMAL) {
        }
        if (loadMode != LoadMode.UP_REFRESH) {
            page = 0;
        }
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(NetConst.ALIPAY_ORDER)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("status", "0")
                .addParams("pageIndex", page + "")
                .addParams("pageSize", "15")
                .addParams("status", status);
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        onLoad();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                AlipayOrderResponse orderListResponse = GsonUtil.changeGsonToBean(response, AlipayOrderResponse.class);
                                if (orderListResponse == null) {
                                    if (page == 0) {
                                        return;
                                    }
                                }



                                ArrayList<AlipayOrder> orderItemslist = (ArrayList<AlipayOrder>) orderListResponse.getResult();

                                page++;
                                if (loadMode != LoadMode.UP_REFRESH) {
                                    dataList.clear();
                                }
                                dataList.addAll(orderItemslist);
                                adapter.notifyDataSetChanged();

                                if (orderItemslist == null || orderItemslist.size() == 0) {
                                    onEmpty();

                                    if (orderItemslist == null) {
                                        return;
                                    }
                                    if (page == 0) {
                                        return;
                                    }
                                    ToastUtils.makeText(getActivity(), "没有更多数据").show();
                                    return;
                                }
                                if (dataList.size() == 0) {
                                    onEmpty();
                                } else {
                                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                                }

                                if (dataList.size() < 14) {
                                    lisv_order_list.setPullLoadEnable(false);
                                } else {
                                    lisv_order_list.setPullLoadEnable(true);
                                }

                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (dataList.size() == 0) {
                            onEmpty();
                        } else {
                            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        }
                    }


                });
    }


    private void onLoad() {

        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    lisv_order_list.stopRefresh();
                    lisv_order_list.stopLoadMore();
                    lisv_order_list.setRefreshTime("刚刚");
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }


    public void onEmpty() {

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);

            }
        });
    }


    public void onErrr() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);

            }
        });


    }






    /**
     * 获取订单详情接口
     *
     * @param orderId
     */
    private void loadAlipayOrder(String orderId) {

        PostFormBuilder postFormBuilder = OkHttpUtils
                .post().tag(this)
                .url(NetConst.ALIPAY_ORDER_DETAILS)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("orderId", orderId);
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showToast("服务异常！");
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                AlipayOrderDetailsResponse orderListResponse = GsonUtil.changeGsonToBean(response, AlipayOrderDetailsResponse.class);
                                if (orderListResponse.getResult() != null) {
                                    AlipayOrder alipayOrder = orderListResponse.getResult();
                                    if ("0".equals(status)) {
                                        alipayOrder.setRespay(true);
                                        payOrderQuery(getActivity(),user,alipayOrder);

                                    } else {
                                        OrderHuabeiActivity.startAction(getActivity(), alipayOrder);

                                    }
                                }
                            }
                        } catch (Exception e) {
                            showToast("数据异常！");
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void payOrderQuery(final Context context, User user, final AlipayOrder alipayOrder) {
        showProgress();

        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("orderId", alipayOrder.getOrderNo())
                .url(NetConst.PAY_ORDER_QUERY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (checkResonse(response)) {
                        PayOrderQueryResponse payOrderQuery = GsonUtil.changeGsonToBean(response, PayOrderQueryResponse.class);
                        ConfirmCommodityActivity.startAction(context, alipayOrder,payOrderQuery.getResult());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissProgress();
            }
        });
    }
}
