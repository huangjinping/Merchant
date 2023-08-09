package com.hdfex.merchantqrshow.admin.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.admin.business.AlipayOrderForAdmin;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/26.
 * email : huangjinping@hdfex.com
 */

public class AdminOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<AlipayOrderForAdmin> dataList;
    private MyItemClickListener itemClickListener;
    private String status;


    public AdminOrderAdapter(List<AlipayOrderForAdmin> dataList, String status) {
        this.dataList = dataList;
        this.status=status;
    }

    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, position);
                }
            }
        });
        AlipayOrderForAdmin item = dataList.get(position);
        if (!TextUtils.isEmpty(item.getCommodityName())){
            viewHolder.txt_commityName.setText(item.getCommodityName());
        }
        if (!TextUtils.isEmpty(item.getApplyAmount())){
            viewHolder.txt_applyAmount.setText("(商品金额:"+item.getApplyAmount()+")");
        }
        if (!TextUtils.isEmpty(item.getApplyDate())){
            viewHolder.txt_applyDate.setText(item.getApplyDate());
        }

        Log.d("status",status);
        if ("01".equals(status)){
            viewHolder.txt_status.setText("已完成");
        }else {
            viewHolder.txt_status.setText("未完成");
        }

        if (!TextUtils.isEmpty(item.getBizName())){
            viewHolder.txt_bizName.setText(item.getBizName());
        }
        if (!TextUtils.isEmpty(item.getLoanAmount())){
            viewHolder.txt_loanAmount.setText("分期金额"+item.getLoanAmount());
        }


    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public TextView txt_commityName;
        public TextView txt_loanAmount;
        public TextView txt_applyAmount;
        public TextView txt_status;
        public TextView txt_applyDate;
        public TextView txt_bizName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_commityName = (TextView) rootView.findViewById(R.id.txt_commityName);
            this.txt_loanAmount = (TextView) rootView.findViewById(R.id.txt_loanAmount);
            this.txt_applyAmount = (TextView) rootView.findViewById(R.id.txt_applyAmount);
            this.txt_status = (TextView) rootView.findViewById(R.id.txt_status);
            this.txt_applyDate = (TextView) rootView.findViewById(R.id.txt_applyDate);
            this.txt_bizName = (TextView) rootView.findViewById(R.id.txt_bizName);
        }

    }
}
