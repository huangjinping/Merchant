package com.hdfex.merchantqrshow.admin.financial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/12.
 * email : huangjinping@hdfex.com
 */

public class BalanceItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RedPackage> dataList;


    public BalanceItemAdapter(List<RedPackage> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_balance_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        RedPackage redPackage = dataList.get(position);

        if (!TextUtils.isEmpty(redPackage.getOperDate())){
            viewHolder. txt_days_child.setText(redPackage.getOperDate());
        }
        if (!TextUtils.isEmpty(redPackage.getTradeType())){
            viewHolder. txt_desc.setText(redPackage.getTradeType());
        }
        if (!TextUtils.isEmpty(redPackage.getRedPacketAmt())){
            viewHolder. txt_child_time.setText(redPackage.getRedPacketAmt());
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{
        public View rootView;
        public TextView txt_days_child;
        public TextView txt_desc;
        public TextView txt_child_time;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_days_child = (TextView) rootView.findViewById(R.id.txt_days_child);
            this.txt_desc = (TextView) rootView.findViewById(R.id.txt_desc);
            this.txt_child_time = (TextView) rootView.findViewById(R.id.txt_child_time);
        }

    }
}
