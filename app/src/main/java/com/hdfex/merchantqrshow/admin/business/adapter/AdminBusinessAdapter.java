package com.hdfex.merchantqrshow.admin.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/26.
 * email : huangjinping@hdfex.com
 */

public class AdminBusinessAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> dataList;
    private  MyItemClickListener itemClickListener;

    public AdminBusinessAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_business, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder= (ViewHolder) holder;

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_status;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_status = (TextView) rootView.findViewById(R.id.txt_status);
        }

    }
}
