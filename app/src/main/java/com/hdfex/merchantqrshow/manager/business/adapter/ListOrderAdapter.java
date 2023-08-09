package com.hdfex.merchantqrshow.manager.business.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.manager.business.BusinessOrder;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/7.
 * email : huangjinping@hdfex.com
 */

public class ListOrderAdapter extends BaseAdapter {

    private Context context;
    private List<BusinessOrder> dataList;


    public ListOrderAdapter(Context context, List<BusinessOrder> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_businessorderitem, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BusinessOrder detail = dataList.get(position);





        if (!TextUtils.isEmpty(detail.getCustName())) {
            viewHolder.txt_custName.setText(detail.getCustName());
        }else {
            viewHolder.txt_custName.setText("");
        }
        if (!TextUtils.isEmpty(detail.getApplyDate())) {
            viewHolder.txt_applyDate.setText(detail.getApplyDate());
        }else {
            viewHolder.txt_applyDate.setText("");
        }
        if (!TextUtils.isEmpty(detail.getOrderAmt())) {
            viewHolder.txt_orderAmt.setText(detail.getOrderAmt());
        }else {
            viewHolder.txt_orderAmt.setText("");
        }
        if (!TextUtils.isEmpty(detail.getFailedReason())) {
            viewHolder.txt_failedReason.setText(detail.getFailedReason());
        }else {
            viewHolder.txt_failedReason.setText("");
        }
        if (!TextUtils.isEmpty(detail.getFollowDays())) {
            viewHolder.txt_followDays.setText(viewHolder.txt_followDays.getContext().getString(R.string.followDays, detail.getFollowDays()));
        }else {
            viewHolder.txt_followDays.setText("");
        }
        if (!TextUtils.isEmpty(detail.getOwnSalesman())) {
            viewHolder.txt_ownSalesman.setText(detail.getRegion() + "/" + detail.getOwnSalesman());
        }else {
            viewHolder.txt_ownSalesman.setText("");
        }
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_custName;
        public TextView txt_orderAmt;
        public TextView txt_failedReason;
        public TextView txt_followDays;
        public TextView txt_ownSalesman;
        public TextView txt_applyDate;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_custName = (TextView) rootView.findViewById(R.id.txt_custName);
            this.txt_orderAmt = (TextView) rootView.findViewById(R.id.txt_orderAmt);
            this.txt_failedReason = (TextView) rootView.findViewById(R.id.txt_failedReason);
            this.txt_followDays = (TextView) rootView.findViewById(R.id.txt_followDays);
            this.txt_ownSalesman = (TextView) rootView.findViewById(R.id.txt_ownSalesman);
            this.txt_applyDate = (TextView) rootView.findViewById(R.id.txt_applyDate);
        }

    }
}
