package com.hdfex.merchantqrshow.salesman.order.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.appointment.CommityListBean;
import com.hdfex.merchantqrshow.bean.salesman.home.OrderChange;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2019/11/11.
 * email : huangjinping1000@163.com
 */
public class ChangeFondSourceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int HEADER = 111;
    private final int ITEM = 333;
    private List<OrderChange> dataList;
    private String type;

    public ChangeFondSourceListAdapter(List<OrderChange> dataList, String type) {
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
                    .inflate(R.layout.item_change_fund_source, parent, false);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position > 0) {
            final int index = position - 1;
            OrderChange item = dataList.get(index);
            ViewHolder viewholder = (ViewHolder) holder;
            if (!TextUtils.isEmpty(item.getIdName())) {
                viewholder.txt_username.setText(item.getIdName());
            }

            if (!TextUtils.isEmpty(item.getStatus())) {
                viewholder.txt_status.setText(item.getStatus());
            }
            if (!TextUtils.isEmpty(item.getApplyAmount())) {
                viewholder.txt_commodityName.setText(item.getApplyAmount());
            }

            if (!TextUtils.isEmpty(item.getApplyDuration())) {
                viewholder.txt_duration.setText(item.getApplyDuration() + "期");
            }

            if (!TextUtils.isEmpty(item.getCommodityName())) {
                viewholder.txt_apply_amount.setText(item.getCommodityName());
            }

            viewholder.txt_change_fund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onChangeFundClickListener != null) {
                        onChangeFundClickListener.onItemClick(v, index);
                    }
                }
            });

            viewholder.txt_refuse_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRefuseOrderClickListener != null) {
                        onRefuseOrderClickListener.onItemClick(v, index);
                    }
                }
            });
        } else {
            ViewHolderHeader viewholder = (ViewHolderHeader) holder;
            int i = dataList.size();

            if (type.equals("01")) {
                /**
                 * 逾期
                 */
                viewholder.txt_title_name.setText("当前还有" + i + "位待处理客户，请您跟进");
            } else if (type.equals("00")) {
                /**
                 * 待还款
                 */
                viewholder.txt_title_name.setText("当前还有" + i + "位待处理客户，请您跟进");
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    private MyItemClickListener onChangeFundClickListener;
    private MyItemClickListener onRefuseOrderClickListener;


    public void setOnChangeFundClickListener(MyItemClickListener onChangeFundClickListener) {
        this.onChangeFundClickListener = onChangeFundClickListener;
    }


    public void setOnRefuseOrderClickListener(MyItemClickListener onRefuseOrderClickListener) {
        this.onRefuseOrderClickListener = onRefuseOrderClickListener;
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


    class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_username;
        public TextView txt_status;
        public TextView txt_commodityName;
        public TextView txt_apply_amount;
        public TextView txt_duration;
        public TextView txt_change_fund;
        public TextView txt_refuse_apply;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_username = (TextView) rootView.findViewById(R.id.txt_username);
            this.txt_status = (TextView) rootView.findViewById(R.id.txt_status);
            this.txt_commodityName = (TextView) rootView.findViewById(R.id.txt_commodityName);
            this.txt_apply_amount = (TextView) rootView.findViewById(R.id.txt_apply_amount);
            this.txt_duration = (TextView) rootView.findViewById(R.id.txt_duration);
            this.txt_change_fund = (TextView) rootView.findViewById(R.id.txt_change_fund);
            this.txt_refuse_apply = (TextView) rootView.findViewById(R.id.txt_refuse_apply);
        }

    }
}
