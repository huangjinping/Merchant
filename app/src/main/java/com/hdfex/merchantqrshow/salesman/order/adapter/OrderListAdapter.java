package com.hdfex.merchantqrshow.salesman.order.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.salesman.order.activity.MutliOrderDetailsActivity;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderCommodityDetail;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidou521 on 2016/6/28.
 */
public class OrderListAdapter extends BaseAdapter {
    private Context context;
    private List<OrderItem> orderlist;
    private ViewHolder viewHolder;

    public OrderListAdapter(Context context, List<OrderItem> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
    }

    @Override
    public int getCount() {
        return orderlist.size();
    }

    @Override
    public Object getItem(int i) {
        return orderlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = View.inflate(context, R.layout.item_order_list, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.layout_item_orderlist.setTag(i);
        viewHolder.layout_item_orderlist.setOnClickListener(new OrderListClickListener());
        OrderItem orderItem = orderlist.get(i);
        if (!TextUtils.isEmpty(orderItem.getOrderCommoditys().get(0).getPic())) {
            Glide.with(context.getApplicationContext())
                    .load(orderItem.getOrderCommoditys().get(0).getPic())
                    .placeholder(R.mipmap.ic_defoult)
                    .error(R.mipmap.ic_defoult)
                    .into(viewHolder.img_order_pic);
        } else {
            viewHolder.img_order_pic.setBackgroundResource(R.mipmap.ic_defoult);
        }

        if (!TextUtils.isEmpty(orderItem.getTime())) {
            viewHolder.txt_order_time.setText(orderItem.getTime());
        }
        if (!TextUtils.isEmpty(orderItem.getStatus())) {
            viewHolder.txt_order_status.setText(orderItem.getStatus());
        }
        ArrayList<OrderCommodityDetail> commoditylists = orderItem.getOrderCommoditys();
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < commoditylists.size(); j++) {
            if (j != commoditylists.size() - 1) {
                sb.append(commoditylists.get(j).getCommodityName() + "，");
            } else {
                sb.append(commoditylists.get(j).getCommodityName());
            }
            viewHolder.txt_commodity_name.setText(sb.toString());
        }

        if (orderItem.getOrderNo() != 0) {
            viewHolder.txt_apply_id.setText("" + orderItem.getOrderNo());
        }

        if (!TextUtils.isEmpty(orderItem.getTotalPrice())) {
            viewHolder.txt_amount.setText(orderItem.getTotalPrice());
        }
        return view;
    }

    private class OrderListClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(context, OrderDetailNewActivity.class);
//            intent.putExtra("applyId", "" + orderItem.getOrderNo());
//            context.startActivity(intent);
            int position = (int) view.getTag();
            OrderItem orderItem = orderlist.get(position);
            MutliOrderDetailsActivity.startAction(context, orderItem.getOrderNo() + "", view);
        }
    }


    public static class ViewHolder {
        public View rootView;
        public TextView txt_order_status;
        public ImageView img_order_pic;
        public TextView txt_commodity_name;
        public TextView txt_apply_id;
        public TextView txt_amount;
        public LinearLayout layout_item_orderlist;
        public TextView txt_order_time;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_order_status = (TextView) rootView.findViewById(R.id.txt_order_status);
            this.img_order_pic = (ImageView) rootView.findViewById(R.id.img_order_pic);
            this.txt_order_time = (TextView) rootView.findViewById(R.id.txt_order_time);

            this.txt_commodity_name = (TextView) rootView.findViewById(R.id.txt_commodity_name);
            this.txt_apply_id = (TextView) rootView.findViewById(R.id.txt_apply_id);
            this.txt_amount = (TextView) rootView.findViewById(R.id.txt_amount);
            this.layout_item_orderlist = (LinearLayout) rootView.findViewById(R.id.layout_item_orderlist);
            this.layout_item_orderlist = (LinearLayout) rootView.findViewById(R.id.layout_item_orderlist);
        }

    }


}
