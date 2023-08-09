package com.hdfex.merchantqrshow.salesman.appointment.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.appointment.BackOrder;
import com.hdfex.merchantqrshow.bean.salesman.appointment.CommityListBean;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2017/10/24.
 * email : huangjinping@hdfex.com
 */

public class BackOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int HEADER = 111;
    private final int ITEM = 333;
    private List<BackOrder> dataList;
    private String type;


    public BackOrderAdapter(List<BackOrder> dataList, String type) {
        this.dataList = dataList;
        this.type = type;
    }

    private MyItemClickListener onCallSendClickListener;

    public void setOnCallSendClickListener(MyItemClickListener onCallSendClickListener) {
        this.onCallSendClickListener = onCallSendClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_follow_details_header, parent, false);


            return new ViewHolderHeader(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_back_order_details, parent, false);

            return new ViewHolder(view);
        }


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
        if (position > 0) {
            final int index = position - 1;
            BackOrder item = dataList.get(index);
            ViewHolder viewholder = (ViewHolder) holder;
            if (!TextUtils.isEmpty(item.getStatus())) {
                viewholder.txt_status.setText(item.getStatus());
            }
            if (!TextUtils.isEmpty(item.getIdName())) {
                viewholder.txt_idName.setText(item.getIdName());
            }
            if (!TextUtils.isEmpty(item.getReason())) {
                viewholder.txt_reason.setText(item.getReason());
            }
            if (!TextUtils.isEmpty(item.getFollowTime())) {
                viewholder.txt_followTime.setText(item.getFollowTime());
            }
            viewholder.txt_commodityName.setText(getAddressByCommodity(item.getCommityList()));
            if (itemClickListener != null) {
                itemClickListener.onItemClick(viewholder.txt_commodityName, index);
            }

            if (!TextUtils.isEmpty(item.getPhoneCount())) {
                viewholder.txt_phone_number.setText("电话提醒(" + item.getPhoneCount() + "次)");
            }


            viewholder.txt_phone_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCallSendClickListener != null) {
                        onCallSendClickListener.onItemClick(v, index);
                    }
                }
            });


        } else {
            ViewHolderHeader viewholder = (ViewHolderHeader) holder;
            viewholder.txt_title_name.setText("请督促客户按要求修改后提交订单");
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    private MyItemClickListener itemClickListener;

    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    class ViewHolderHeader extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_title_name;

        public ViewHolderHeader(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_title_name = (TextView) rootView.findViewById(R.id.txt_title_name);
        }

    }

    /**
     * @param orderCommoditys
     * @return
     */
    private String getAddressByCommodity(List<CommityListBean> orderCommoditys) {
        String s = "";

        try {
            StringBuffer StringBuffer = new StringBuffer();
            if (orderCommoditys == null || orderCommoditys.size() == 0) {
                return StringBuffer.toString();
            }
            for (CommityListBean detail : orderCommoditys) {
                if (!TextUtils.isEmpty(detail.getCommodityAddress())) {
                    StringBuffer.append(detail.getCommodityAddress());
                    StringBuffer.append(",");
                } else {
                    StringBuffer.append(detail.getCommodityName());
                    StringBuffer.append(",");
                }
            }
            s = StringBuffer.toString();
            if (s.endsWith(",")) {
                s = s.substring(0, s.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_idName;
        public TextView txt_repayAmt;
        public TextView txt_psDueDt;
        public TextView txt_status;
        public TextView txt_commodityName;
        public TextView txt_reason;
        public TextView txt_phone_number;
        public TextView txt_followTime;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_idName = (TextView) rootView.findViewById(R.id.txt_idName);
            this.txt_repayAmt = (TextView) rootView.findViewById(R.id.txt_repayAmt);
            this.txt_psDueDt = (TextView) rootView.findViewById(R.id.txt_psDueDt);
            this.txt_status = (TextView) rootView.findViewById(R.id.txt_status);
            this.txt_commodityName = (TextView) rootView.findViewById(R.id.txt_commodityName);
            this.txt_reason = (TextView) rootView.findViewById(R.id.txt_reason);
            this.txt_phone_number = (TextView) rootView.findViewById(R.id.txt_phone_number);
            this.txt_followTime = (TextView) rootView.findViewById(R.id.txt_followTime);
        }

    }
}
