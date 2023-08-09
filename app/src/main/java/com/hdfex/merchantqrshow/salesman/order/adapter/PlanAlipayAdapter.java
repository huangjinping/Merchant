package com.hdfex.merchantqrshow.salesman.order.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.huabei.AlipayPlan;

import java.util.List;

/**
 * author Created by harrishuang on 2017/8/14.
 * email : huangjinping@hdfex.com
 */

public class PlanAlipayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AlipayPlan> dataList;

    public PlanAlipayAdapter(List<AlipayPlan> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planalipay, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AlipayPlan alipayPlan = dataList.get(position);
        ViewHolder viewholder = (ViewHolder) holder;
        if (!TextUtils.isEmpty(alipayPlan.getDuration())) {
            viewholder.txt_duration.setText("第"+alipayPlan.getDuration()+"期");
        }
        if (!TextUtils.isEmpty(alipayPlan.getDurationAmt())) {
            viewholder.txt_periodamount.setText(alipayPlan.getDurationAmt()+"元");
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_duration;
        public TextView txt_periodamount;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_duration = (TextView) rootView.findViewById(R.id.txt_duration);
            this.txt_periodamount = (TextView) rootView.findViewById(R.id.txt_periodamount);
        }

    }
}
