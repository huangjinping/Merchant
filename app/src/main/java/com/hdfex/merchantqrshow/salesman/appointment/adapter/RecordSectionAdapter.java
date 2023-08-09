package com.hdfex.merchantqrshow.salesman.appointment.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpItemWeek;

import java.util.List;

/**
 * author Created by harrishuang on 2017/10/26.
 * email : huangjinping@hdfex.com
 */

public class RecordSectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FollowUpItemWeek> dataList;

    public RecordSectionAdapter(List<FollowUpItemWeek> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record_section, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewholder = (ViewHolder) holder;
        FollowUpItemWeek followUpItemWeek = dataList.get(position);
        if (!TextUtils.isEmpty(followUpItemWeek.getBizCallFllowDate())) {
            viewholder.txt_bizCallFllowDate.setText(followUpItemWeek.getBizCallFllowDate());
        }
        if (!TextUtils.isEmpty(followUpItemWeek.getBizRemark())) {
            viewholder.txt_bizRemark.setText(followUpItemWeek.getBizRemark());
        }
        if (!TextUtils.isEmpty(followUpItemWeek.getFollowUpListOutweek())) {
            viewholder.txt_status.setText(followUpItemWeek.getFollowUpListOutweek());
        }
    }
    
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_bizCallFllowDate;
        public TextView txt_status;
        public TextView txt_bizRemark;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_bizCallFllowDate = (TextView) rootView.findViewById(R.id.txt_bizCallFllowDate);
            this.txt_status = (TextView) rootView.findViewById(R.id.txt_status);
            this.txt_bizRemark = (TextView) rootView.findViewById(R.id.txt_bizRemark);
        }

    }
}
