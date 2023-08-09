package com.hdfex.merchantqrshow.salesman.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.order.MultiOrder;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2019/9/5.
 * email : huangjinping1000@163.com
 */
public class CashOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<MultiOrder> dataList;

    private MyItemClickListener onItemClickListener;

    public void setOnItemClickListener(MyItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CashOrderAdapter(List<MultiOrder> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_cash_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MultiOrder orderItem = dataList.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.txt_idName.setText("");
        viewHolder.txt_status.setText("");
        viewHolder.txt_commodityName.setText("");
        viewHolder.txt_phoneNo.setText("");
        viewHolder.txt_orderDate.setText("");

        if (!TextUtils.isEmpty(orderItem.getIdName())) {
            viewHolder.txt_idName.setText(orderItem.getIdName());
        }
        if (!TextUtils.isEmpty(orderItem.getStatusDesc())) {
            viewHolder.txt_status.setText(orderItem.getStatusDesc());
        }


        if (!TextUtils.isEmpty(orderItem.getCommodityName())) {
            viewHolder.txt_commodityName.setText(orderItem.getCommodityName());
        }


        if (!TextUtils.isEmpty(orderItem.getApplyTime())) {
            viewHolder.txt_orderDate.setText(orderItem.getApplyTime());
        }

        if (!TextUtils.isEmpty(orderItem.getPhone())) {
            viewHolder.txt_phoneNo.setText(StringUtils.getPhoneText(orderItem.getPhone()));
        }

        viewHolder.img_call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_idName;
        public TextView txt_status;
        public TextView txt_commodityName;
        public TextView txt_phoneNo;
        public ImageView img_call_phone;
        public TextView txt_orderDate;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_idName = (TextView) rootView.findViewById(R.id.txt_idName);
            this.txt_status = (TextView) rootView.findViewById(R.id.txt_status);
            this.txt_commodityName = (TextView) rootView.findViewById(R.id.txt_commodityName);
            this.txt_phoneNo = (TextView) rootView.findViewById(R.id.txt_phoneNo);
            this.img_call_phone = (ImageView) rootView.findViewById(R.id.img_call_phone);
            this.txt_orderDate = (TextView) rootView.findViewById(R.id.txt_orderDate);
        }

    }
}
