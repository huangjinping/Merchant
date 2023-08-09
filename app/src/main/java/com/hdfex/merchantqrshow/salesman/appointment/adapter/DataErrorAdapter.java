package com.hdfex.merchantqrshow.salesman.appointment.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.appointment.DataErrorItem;
import com.hdfex.merchantqrshow.bean.salesman.appointment.RepayListItem;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2017/10/24.
 * email : huangjinping@hdfex.com
 */

public class DataErrorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int HEADER = 111;
    private final int ITEM = 333;
    private List<DataErrorItem> dataList;
    private String type;

    public DataErrorAdapter(List<DataErrorItem> dataList, String type) {
        this.dataList = dataList;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_follow_details_header, parent, false);
            return new ViewHolderHeader(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_error_details, parent, false);
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
         final    int index = position - 1;
            DataErrorItem item = dataList.get(index);
           final ViewHolder viewholder = (ViewHolder) holder;
            if (!TextUtils.isEmpty(item.getBizName())){
                viewholder.txt_custName.setText(item.getBizName());
            }
            if (!TextUtils.isEmpty(item.getStatus())){
                viewholder.txt_status.setText(item.getStatus());
            }
            if (!TextUtils.isEmpty(item.getFollowTime())){
                viewholder.txt_followTime.setText(item.getFollowTime()+"未跟进");
            }
            if (!TextUtils.isEmpty(item.getCommodityName())){
                viewholder.txt_commodityName.setText(item.getCommodityName());
            }

            if (!TextUtils.isEmpty(item.getLoanStatus())){
                viewholder.txt_loanStatus.setText(item.getLoanStatus());
            }
            if (itemClickListener!=null){
                viewholder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(viewholder.txt_commodityName,index);
                    }
                });
            }
            viewholder.txt_overDesc.setText("");
            if ("01".equals(item.getType())){
                viewholder.txt_overDesc.setText("资料异常：");
            }else if ("00".equals(item.getType())){
                viewholder.txt_overDesc.setText("电核异常：");
            }

        } else {
            ViewHolderHeader viewholder = (ViewHolderHeader) holder;
            int i = dataList.size();
            if (i == 0) {
                return;
            }

            viewholder.txt_title_name.setText("请按要求修改后提交订单");

//            if (type.equals("01")) {
//                /**
//                 * 逾期
//                 */
//                viewholder.txt_title_name.setText("当前还有" + i + "位待还款客户，请您跟进");
//            } else if (type.equals("00")) {
//                /**
//                 * 待还款
//                 */
//                viewholder.txt_title_name.setText("当前还有" + i + "位待还款客户，请您跟进");
//            }
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_custName;
        public TextView txt_status;
        public TextView txt_psDueDt;
        public TextView txt_overDesc;
        public TextView txt_followTime;
        public TextView txt_commodityName;
        public TextView txt_loanStatus;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_loanStatus=(TextView)rootView.findViewById(R.id.txt_loanStatus);
            this.txt_custName = (TextView) rootView.findViewById(R.id.txt_custName);
            this.txt_status = (TextView) rootView.findViewById(R.id.txt_status);
            this.txt_psDueDt = (TextView) rootView.findViewById(R.id.txt_psDueDt);
            this.txt_overDesc = (TextView) rootView.findViewById(R.id.txt_overDesc);
            this.txt_followTime = (TextView) rootView.findViewById(R.id.txt_followTime);
            this.txt_commodityName=(TextView)rootView.findViewById(R.id.txt_commodityName);
        }

    }
}
