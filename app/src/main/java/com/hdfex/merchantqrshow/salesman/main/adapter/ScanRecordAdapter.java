package com.hdfex.merchantqrshow.salesman.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderCommodityDetail;
import com.hdfex.merchantqrshow.bean.salesman.order.QueryUncompelete;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.widget.MyListViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrishuang on 16/7/12.
 */
public class ScanRecordAdapter extends BaseAdapter {
    private Context context;
    private List<QueryUncompelete> orderlist;
    private ViewHolder viewHolder;
    private MyListViewClickListener listViewClickListener;

    private  String type;

    public void setListViewClickListener(MyListViewClickListener listViewClickListener) {
        this.listViewClickListener = listViewClickListener;
    }

    public ScanRecordAdapter(Context context, List<QueryUncompelete> orderlist,String type) {
        this.context = context;
        this.orderlist = orderlist;
        this.type=type;
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
            view = View.inflate(context, R.layout.item_scan_list, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.layout_item_orderlist.setTag(i);
        viewHolder.layout_item_orderlist.setOnClickListener(new OrderListClickListener());
        QueryUncompelete queryUncompelete = orderlist.get(i);


        if (!TextUtils.isEmpty(queryUncompelete.getStatus())) {
            viewHolder.txt_order_status.setText(queryUncompelete.getStatus() + "");
        } else {
            viewHolder.txt_order_status.setText("");
        }

        if (!TextUtils.isEmpty(queryUncompelete.getCreateTime())) {
            viewHolder.txt_order_time.setText(queryUncompelete.getCreateTime());
        }

        if (IntentFlag.SCAN_TYPE_ZUFANG.equals(type)){
            viewHolder.txt_apply_id.setVisibility(View.VISIBLE);
        }
        if (queryUncompelete.getPhone() != null) {
            viewHolder.txt_apply_id.setText("" + queryUncompelete.getPhone());
        }

        ArrayList<OrderCommodityDetail> commoditys = queryUncompelete.getCommoditys();

        if (commoditys != null && commoditys.size() > 0) {
            StringBuilder commodity_names = new StringBuilder();
            for (int j = 0; j < commoditys.size(); j++) {
                if (j == commoditys.size() - 1) {
                    commodity_names.append(commoditys.get(j).getCommodityName());
                } else {
                    commodity_names.append(commoditys.get(j).getCommodityName() + "，");
                }
            }
            viewHolder.txt_commodity_name.setText(commodity_names.toString());
        }


        if (!TextUtils.isEmpty(queryUncompelete.getPeriodAmount())) {
            viewHolder.txt_amount.setText(queryUncompelete.getPeriodAmount() + "*" + queryUncompelete.getDuration());
        }
//        if (!TextUtils.isEmpty(queryUncompelete.getCommoditys().get(0).getPic())) {
//            Glide.with(context.getApplicationContext())
//                    .load(queryUncompelete.getCommoditys().get(0).getPic())
//                    .placeholder(R.mipmap.ic_defoult)
//                    .error(R.mipmap.ic_defoult)
//                    .into(viewHolder.img_order_pic);
//        } else {
//            viewHolder.img_order_pic.setBackgroundResource(R.mipmap.ic_defoult);
//        }

        return view;
    }

    private class OrderListClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {


            int position = (int) view.getTag();

            if (listViewClickListener != null) {
                QueryUncompelete queryUncompelete = orderlist.get(position);

                listViewClickListener.itemClick(queryUncompelete, position);
            }

//            Bundle bundle = new Bundle();
//            bundle.putSerializable(IntentFlag.SCAN_DETAILS, queryUncompelete);
//            intent.putExtras(bundle);

        }
    }


    public static class ViewHolder {
        public View rootView;
        public TextView txt_order_status;
        public TextView txt_commodity_name;
        public TextView txt_apply_id;
        public TextView txt_amount;
        public LinearLayout layout_item_orderlist;
        public TextView txt_order_time;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_order_status = (TextView) rootView.findViewById(R.id.txt_order_status);
            this.txt_order_time = (TextView) rootView.findViewById(R.id.txt_order_time);

            this.txt_commodity_name = (TextView) rootView.findViewById(R.id.txt_commodity_name);
            this.txt_apply_id = (TextView) rootView.findViewById(R.id.txt_apply_id);
            this.txt_amount = (TextView) rootView.findViewById(R.id.txt_amount);
            this.layout_item_orderlist = (LinearLayout) rootView.findViewById(R.id.layout_item_orderlist);
            this.layout_item_orderlist = (LinearLayout) rootView.findViewById(R.id.layout_item_orderlist);
        }

    }


}
