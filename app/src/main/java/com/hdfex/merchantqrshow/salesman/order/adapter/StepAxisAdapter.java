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
import com.hdfex.merchantqrshow.bean.salesman.order.UserStep;

import java.util.List;

/**
 * author Created by harrishuang on 2019/9/5.
 * email : huangjinping1000@163.com
 */
public class StepAxisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<UserStep> dataList;

    public StepAxisAdapter(List<UserStep> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_stepaxis, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.txt_step_name.setText("");
        viewHolder.img_step.setImageResource(R.mipmap.ic_row_unselected);
            Context context= viewHolder.txt_step_name.getContext();
        UserStep userStep = dataList.get(position);
        if (!TextUtils.isEmpty(userStep.getName())) {
            viewHolder.txt_step_name.setText(userStep.getName());
        }
        if ("1".equals(userStep.getValue())) {
            viewHolder.img_step.setImageResource(R.mipmap.ic_row_selected);
            viewHolder.txt_step_name.setTextColor(context.getResources().getColor(R.color.blue_light));
        }else {
            viewHolder.txt_step_name.setTextColor(context.getResources().getColor(R.color.black_32));

        }


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView img_step;
        public TextView txt_step_name;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.img_step = (ImageView) rootView.findViewById(R.id.img_step);
            this.txt_step_name = (TextView) rootView.findViewById(R.id.txt_step_name);
        }

    }
}
