package com.hdfex.merchantqrshow.admin.financial.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/12/12.
 * email : huangjinping@hdfex.com
 */

public class BalanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> dataList;
    private MyItemClickListener itemClickListener;

    public BalanceAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_balance, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        List<String> itemList = new ArrayList<>();
        itemList.add("sss");
        itemList.add("sss");
        itemList.add("sss");
        itemList.add("sss");
        ViewHolder viewHolder = (ViewHolder) holder;
        Context context = viewHolder.rec_balance_item.getContext();
//        BalanceItemAdapter itemAdapter = new BalanceItemAdapter(itemList);
        viewHolder.rec_balance_item.setLayoutManager(new LinearLayoutManager(context));
//        viewHolder.rec_balance_item.setAdapter(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_days;
        public RecyclerView rec_balance_item;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_days = (TextView) rootView.findViewById(R.id.txt_days);
            this.rec_balance_item = (RecyclerView) rootView.findViewById(R.id.rec_balance_item);
        }

    }
}
