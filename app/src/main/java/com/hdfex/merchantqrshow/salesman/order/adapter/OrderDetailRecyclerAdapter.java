package com.hdfex.merchantqrshow.salesman.order.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderCommodityDetail;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderDetails;
import com.hdfex.merchantqrshow.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by maidou521 on 2016/8/18.
 */
public class OrderDetailRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private List commoditylist;
    private List templist;
    private List headerlist;
    private OrderDetails orderDetails;
    private final int HEADER = 1;
    private final int CONTENT = 2;
    private final int FOOTER = 3;
    private final int DIVIDER = 4;
    static boolean isOpen = false;

    public OrderDetailRecyclerAdapter(Context context, OrderDetails orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
        isOpen = false;
        commoditylist = orderDetails.getOrderCommoditys();
        headerlist = new ArrayList();
        headerlist.add(commoditylist.get(0));
        if (commoditylist.size() >= 2) {
            headerlist.add(commoditylist.get(1));
        }
        templist = headerlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case HEADER:
                View header_root = inflater.inflate(R.layout.item_commodity_header, parent, false);
                return new HeaderViewHolder(header_root);
            case CONTENT:
                View content_root = inflater.inflate(R.layout.item_commodity_detail, parent, false);
                return new ContentViewHolder(content_root);
            case FOOTER:
                View footer_root = inflater.inflate(R.layout.item_commodity_detail_footer, parent, false);
                return new FooterViewHolder(footer_root, this);
            case DIVIDER:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int count = 0;
        if (count == position) {
            HeaderViewHolder header = (HeaderViewHolder) holder;
            header.tv_status.setText(orderDetails.getStatus());
            if ("审核中".equals(orderDetails.getStatus())||"未提交".equals(orderDetails.getStatus())||"已完成".equals(orderDetails.getStatus())) {
                header.tv_status.setTextColor(header.tv_status.getContext().getResources().getColor(R.color.text_color3));
            } else if ("已打回".equals(orderDetails.getStatus())) {
                header.tv_status.setTextColor(header.tv_status.getContext().getResources().getColor(R.color.red_light));
            } else if ("已取消".equals(orderDetails.getStatus())||"已拒绝".equals(orderDetails.getStatus())) {
                header.tv_status.setTextColor(header.tv_status.getContext().getResources().getColor(R.color.text_status_reject));
            } else{
                header.tv_status.setTextColor(header.tv_status.getContext().getResources().getColor(R.color.blue_light));
            }
            header.tv_name.setText(orderDetails.getIdName());
            header.tv_telephone.setText(orderDetails.getPhone());
            header.tv_total_price.setText(orderDetails.getTotalPrice());
            header.tv_down_playment.setText(orderDetails.getDownpayment());
            header.tv_period_amount.setText(orderDetails.getPeriodAmount());
            header.tv_duration.setText("" + orderDetails.getDuration());
            int totalnum = 0;
            for (int j = 0; j < commoditylist.size(); j++) {
                OrderCommodityDetail commodityDetail = (OrderCommodityDetail) commoditylist.get(j);
                totalnum += commodityDetail.getCommodityCount();
            }
            header.tv_total_number.setText("共" + totalnum + "件商品");
        }
        count++;
        for (int i = 0; i < templist.size(); i++) {
            if (count == position) {
                ContentViewHolder content = (ContentViewHolder) holder;
                OrderCommodityDetail commodityDetail = (OrderCommodityDetail) templist.get(position - 1);
                content.tv_commodity_name.setText(commodityDetail.getCommodityName());
                content.tv_commodity_number.setText("" + commodityDetail.getCommodityCount());
                BigDecimal price = new BigDecimal(commodityDetail.getCommodityPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                content.tv_commodity_price.setText("" + price);
                if(i == templist.size()-1){
                    content.iv_divider.setVisibility(View.INVISIBLE);
                }else {
                    content.iv_divider.setVisibility(View.VISIBLE);
                }
            }
            count++;
        }
        if (count == position) {
            FooterViewHolder footer = (FooterViewHolder) holder;
            if (commoditylist.size() <= 2) {
                footer.ll_addmore.setVisibility(View.GONE);
//                footer.tv_footer.setText("共" + commoditylist.size() + "类商品");
//                footer.iv_status.setVisibility(View.GONE);
            } else {
                if (isOpen) {
                    footer.tv_footer.setText("点击收起列表");
                    footer.iv_status.setImageResource(R.mipmap.ic_upper);
                } else {
                    footer.tv_footer.setText("点击查看更多");
                    footer.iv_status.setImageResource(R.mipmap.ic_downer);
                }
            }
            footer.tv_order_number.setText(orderDetails.getOrderNo());
            footer.tv_time.setText(orderDetails.getTime());
            if("未提交".equals(orderDetails.getStatus())){
                footer.tv_order_title.setText("申请信息");
                footer.tv_order_number_title.setText("申请编号：");
                footer.tv_time_title.setText("申请时间：");
            }
        }
    }

    public void openDetail() {
        int sub = commoditylist.size() - templist.size();
        templist = commoditylist;
        notifyDataSetChanged();
//        notifyItemRangeInserted(3, sub);
    }

    public void closeDetail() {
        int sub = templist.size() - headerlist.size();
        templist = headerlist;
        notifyDataSetChanged();
//        notifyItemRangeRemoved(3, sub);
    }


    @Override
    public int getItemViewType(int position) {
        int count = 0;
        if (count == position) {
            return HEADER;
        }
        count++;
        for (int i = 0; i < templist.size(); i++) {
            if (count == position) {
                return CONTENT;
            }
            count++;
        }
        if (count == position) {
            return FOOTER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int count = templist.size() + 2;
        return count;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_status;
        private TextView tv_name;
        private TextView tv_telephone;
        private ImageView btn_call;
        private TextView tv_total_price;
        private TextView tv_down_playment;
        private TextView tv_period_amount;
        private TextView tv_duration;
        private TextView tv_total_number;

        public HeaderViewHolder(View view) {
            super(view);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_telephone = (TextView) view.findViewById(R.id.tv_telephone);
            btn_call = (ImageView) view.findViewById(R.id.img_call);
            tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
            tv_down_playment = (TextView) view.findViewById(R.id.tv_down_playment);
            tv_period_amount = (TextView) view.findViewById(R.id.tv_period_amount);
            tv_duration = (TextView) view.findViewById(R.id.tv_duration);
            tv_total_number = (TextView) view.findViewById(R.id.tv_total_number);
            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tv_telephone.getText()));
                    if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ToastUtils.e(v.getContext(), "请您检查并开启拨打电话权限");
                        return;
                    }
                    v.getContext().startActivity(intent);
                }
            });
            btn_call.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
//                            btn_call.setTextColor(v.getResources().getColor(R.color.white));
                            break;
                        case MotionEvent.ACTION_UP:
//                            btn_call.setTextColor(v.getResources().getColor(R.color.text_color3));
                            break;
                        case MotionEvent.ACTION_CANCEL:
//                            btn_call.setTextColor(v.getResources().getColor(R.color.text_color3));
                            break;
                    }
                    return false;
                }
            });
        }
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_commodity_name;
        private TextView tv_commodity_number;
        private TextView tv_commodity_price;
        private ImageView iv_divider;

        public ContentViewHolder(View view) {
            super(view);
            tv_commodity_name = (TextView) view.findViewById(R.id.tv_commodity_name);
            tv_commodity_number = (TextView) view.findViewById(R.id.tv_commodity_number);
            tv_commodity_price = (TextView) view.findViewById(R.id.tv_commodity_price);
            iv_divider = (ImageView) view.findViewById(R.id.iv_divider);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_footer;
        private ImageView iv_status;
        private TextView tv_order_number;
        private TextView tv_time;
        private LinearLayout ll_addmore;
        private TextView tv_order_title;
        private TextView tv_order_number_title;
        private TextView tv_time_title;

        public FooterViewHolder(View view, final OrderDetailRecyclerAdapter adapter) {
            super(view);
            ll_addmore = (LinearLayout) view.findViewById(R.id.ll_addmore);
            tv_footer = (TextView) view.findViewById(R.id.tv_footer);
            iv_status = (ImageView) view.findViewById(R.id.iv_status);
            tv_order_number = (TextView) view.findViewById(R.id.tv_order_number);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_order_title = (TextView) view.findViewById(R.id.tv_order_title);
            tv_order_number_title = (TextView) view.findViewById(R.id.tv_order_number_title);
            tv_time_title = (TextView) view.findViewById(R.id.tv_time_title);
            ll_addmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOpen) {
                        adapter.closeDetail();
                    } else {
                        adapter.openDetail();
                    }
                    isOpen = !isOpen;
                }
            });
        }
    }

    public static class DividerViewHolder extends RecyclerView.ViewHolder {
        public DividerViewHolder(View view) {
            super(view);
        }
    }
}
