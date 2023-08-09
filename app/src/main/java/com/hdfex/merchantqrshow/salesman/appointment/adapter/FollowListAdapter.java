package com.hdfex.merchantqrshow.salesman.appointment.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpItem;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2017/9/8.
 * email : huangjinping@hdfex.com
 */

public class FollowListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FollowUpItem> dataList;
    private MyItemClickListener itemClickListener;


    public FollowListAdapter(List<FollowUpItem> dataList) {
        this.dataList = dataList;
    }

    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        FollowUpItem item = dataList.get(position);
        if (!TextUtils.isEmpty(item.getCustName())) {
            viewHolder.txt_custName.setText(item.getCustName());
        }
        if (!TextUtils.isEmpty(item.getStatus())) {

            if ("00".equals(item.getStatus())) {
                viewHolder.txt_status.setText("未跟进");
                if (!TextUtils.isEmpty(item.getFollowTime())) {
                    viewHolder.txt_followTime.setText(item.getFollowTime() + "未跟进");
                }

            } else if ("01".equals(item.getStatus())) {
                viewHolder.txt_status.setText("已跟进");
                if (!TextUtils.isEmpty(item.getBizCallFllowDate())) {
                    viewHolder.txt_followTime.setText("跟进时间" + item.getBizCallFllowDate());
                }
            }
        }
        if (!TextUtils.isEmpty(item.getCustPhone())) {
            viewHolder.txt_custPhone.setText(StringUtils.getPhoneFormat(item.getCustPhone()));
        }
        if (!TextUtils.isEmpty(item.getLoanStatus())) {
            viewHolder.txt_loanStatus.setText(item.getLoanStatus());
        }
        if (!TextUtils.isEmpty(item.getCallStatus())) {
            viewHolder.txt_callStatus.setText(item.getCallStatus());
        }

        if (!TextUtils.isEmpty(item.getBizName())) {
            viewHolder.txt_bizName.setText(item.getBizName());
        }

        if (!TextUtils.isEmpty(item.getBizName()) && !TextUtils.isEmpty(item.getBelongGroup())) {
            viewHolder.txt_bizName.setText(item.getBizName() + "　[" + item.getBelongGroup() + "]");

        }


        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_custName;
        public TextView txt_status;
        public TextView txt_custPhone;
        public TextView txt_loanStatus;
        public TextView txt_callStatus;
        public TextView txt_bizName;
        public TextView txt_followTime;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_custName = (TextView) rootView.findViewById(R.id.txt_custName);
            this.txt_status = (TextView) rootView.findViewById(R.id.txt_status);
            this.txt_custPhone = (TextView) rootView.findViewById(R.id.txt_custPhone);
            this.txt_loanStatus = (TextView) rootView.findViewById(R.id.txt_loanStatus);
            this.txt_callStatus = (TextView) rootView.findViewById(R.id.txt_callStatus);
            this.txt_bizName = (TextView) rootView.findViewById(R.id.txt_bizName);
            this.txt_followTime = (TextView) rootView.findViewById(R.id.txt_followTime);
        }

    }
}
