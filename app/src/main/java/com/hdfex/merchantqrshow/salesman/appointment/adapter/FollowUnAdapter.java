package com.hdfex.merchantqrshow.salesman.appointment.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpListQueryV2Result;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2017/10/25.
 * email : huangjinping@hdfex.com
 */

public class FollowUnAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FollowUpListQueryV2Result> dataList;
    private MyItemClickListener myItemClickListener;

    public FollowUnAdapter(List<FollowUpListQueryV2Result> dataList) {
        this.dataList = dataList;
    }

    public void setMyItemClickListener(MyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_followun, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemClickListener != null) {
                    myItemClickListener.onItemClick(v, position);
                }
            }
        });

        FollowUpListQueryV2Result result = dataList.get(position);
        Resources resources = viewHolder.txt_type_desc.getContext().getResources();
        if (!TextUtils.isEmpty(result.getType())) {
            String type = result.getType();
            viewHolder.txt_type_desc.setText("");
            if ("00".equals(type)) {
                type = "　电核异常　";
                viewHolder.txt_type.setTextColor(resources.getColor(R.color.btn_yellow_press_color));
                viewHolder.txt_type.setBackgroundResource(R.drawable.shape_red_border_redis);
            } else if ("01".equals(type)) {
                type = "　资料异常　";
                viewHolder.txt_type.setTextColor(resources.getColor(R.color.blue_light));
                viewHolder.txt_type.setBackgroundResource(R.drawable.shape_blue_border_redis);


            } else if ("02".equals(type)) {
                type = "　待还款　";
                viewHolder.txt_type.setTextColor(resources.getColor(R.color.blue_light));
                viewHolder.txt_type.setBackgroundResource(R.drawable.shape_blue_border_redis);

            } else if ("03".equals(type)) {
                type = "　逾期　";
                viewHolder.txt_type_desc.setText("逾期提醒，人人有责");
                viewHolder.txt_type.setTextColor(resources.getColor(R.color.red_light));
                viewHolder.txt_type.setBackgroundResource(R.drawable.shape_red_border_redis);

            } else if ("04".equals(type)) {
                type = "　未提交　";
                viewHolder.txt_type.setTextColor(resources.getColor(R.color.blue_light));
                viewHolder.txt_type.setBackgroundResource(R.drawable.shape_blue_border_redis);


            } else if ("05".equals(type)) {
                type = " 更换资方 ";
                viewHolder.txt_type.setTextColor(resources.getColor(R.color.blue_light));
                viewHolder.txt_type.setBackgroundResource(R.drawable.shape_blue_border_redis);


            } else if ("06".equals(type)) {
                type = "　月付审核　";
                viewHolder.txt_type.setTextColor(resources.getColor(R.color.blue_light));
                viewHolder.txt_type.setBackgroundResource(R.drawable.shape_blue_border_redis);


            }
            viewHolder.txt_type.setText(type);
        }


        if (!TextUtils.isEmpty(result.getDesc())) {
            viewHolder.txt_desc.setText(result.getDesc());
        }
//        if (!TextUtils.isEmpty(result.getType())) {
//            viewHolder.txt_status.setText("未跟进");
//        }
        if (!TextUtils.isEmpty(result.getRemindDesc())) {
            viewHolder.txt_remindDesc.setText(result.getRemindDesc());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 返回数据
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_type;
        public TextView txt_type_desc;
        public TextView txt_desc;
        public TextView txt_status;
        public TextView txt_remindDesc;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_type = (TextView) rootView.findViewById(R.id.txt_type);
            this.txt_type_desc = (TextView) rootView.findViewById(R.id.txt_type_desc);
            this.txt_desc = (TextView) rootView.findViewById(R.id.txt_desc);
            this.txt_status = (TextView) rootView.findViewById(R.id.txt_status);
            this.txt_remindDesc = (TextView) rootView.findViewById(R.id.txt_remindDesc);
        }

    }
}
