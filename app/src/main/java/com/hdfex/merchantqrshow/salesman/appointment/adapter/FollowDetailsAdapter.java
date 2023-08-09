package com.hdfex.merchantqrshow.salesman.appointment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2017/10/24.
 * email : huangjinping@hdfex.com
 */

public class FollowDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int HEADER = 111;
    private final int ITEM = 333;

    private List<String> dataList;

    public FollowDetailsAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_follow_details, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {

            return HEADER;
        }


        return ITEM;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    private MyItemClickListener itemClickListener;

    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_username;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_username = (TextView) rootView.findViewById(R.id.txt_username);
        }

    }
}
