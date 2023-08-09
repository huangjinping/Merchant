package com.hdfex.merchantqrshow.salesman.appointment.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.appointment.CommityListBean;
import com.hdfex.merchantqrshow.bean.salesman.appointment.RepayListItem;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2017/10/24.
 * email : huangjinping@hdfex.com
 */

public class RepayDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int HEADER = 111;
    private final int ITEM = 333;
    private List<RepayListItem> dataList;
    private String type;

    public RepayDetailsAdapter(List<RepayListItem> dataList, String type) {
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
                    .inflate(R.layout.item_follow_details, parent, false);
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
            RepayListItem item = dataList.get(index);
            ViewHolder viewholder = (ViewHolder) holder;
            if (!TextUtils.isEmpty(item.getIdName())) {
                viewholder.txt_username.setText(item.getIdName());
            }
            if (!TextUtils.isEmpty(item.getRepayAmt())) {
                viewholder.txt_repayAmt.setText("还款金额:" + item.getRepayAmt());
            }
            if (!TextUtils.isEmpty(item.getPsDueDt())) {
                viewholder.txt_psDueDt.setText("最后还款日:" + item.getPsDueDt());
            }
            if (!TextUtils.isEmpty(item.getDays())) {

                if (type.equals("01")) {
                    /**
                     * 逾期
                     */
                    viewholder.txt_days.setText("本期剩余" + item.getDays()+"天");
                } else if (type.equals("00")) {
                    /**
                     * 待还款
                     */
                    viewholder.txt_days.setText("本期剩余" + item.getDays()+"天");
                }
            }
            if (!TextUtils.isEmpty(item.getDesc())) {
                viewholder.txt_desc.setText(item.getDesc());
            }
            if (!TextUtils.isEmpty(item.getOverDesc())) {
                viewholder.txt_overDesc.setVisibility(View.VISIBLE);
                viewholder.txt_overDesc.setText(item.getOverDesc());
            } else {
                viewholder.txt_overDesc.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(item.getPhoneCount())) {
                viewholder.txt_phone_number.setText("电话提醒(" + item.getPhoneCount() + "次)");
            }
            if (!TextUtils.isEmpty(item.getSMSCount())) {
                viewholder.txt_message_number.setText("短信提醒(" + item.getSMSCount() + "次)");
            }
            viewholder.txt_commodityName.setVisibility(View.GONE);

            String addressByCommodity = getAddressByCommodity(item.getCommityList());
            if (!TextUtils.isEmpty(addressByCommodity)){
                viewholder.txt_commodityName.setVisibility(View.VISIBLE);
                viewholder.txt_commodityName.setText(addressByCommodity);
            }


            viewholder.txt_phone_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCallSendClickListener != null) {
                        onCallSendClickListener.onItemClick(v, index);
                    }
                }
            });

            viewholder.txt_message_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSendMessageClickListener != null) {
                        onSendMessageClickListener.onItemClick(v, index);
                    }
                }
            });




        } else {
            ViewHolderHeader viewholder = (ViewHolderHeader) holder;
            int i = dataList.size();
            if (i == 0) {
                return;
            }
            if (type.equals("01")) {
                /**
                 * 逾期
                 */
                viewholder.txt_title_name.setText("当前还有" + i + "位待还款客户，请您跟进");
            } else if (type.equals("00")) {
                /**
                 * 待还款
                 */
                viewholder.txt_title_name.setText("当前还有" + i + "位待还款客户，请您跟进");
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    private MyItemClickListener onCallSendClickListener;
    private MyItemClickListener onSendMessageClickListener;

    public void setOnCallSendClickListener(MyItemClickListener onCallSendClickListener) {
        this.onCallSendClickListener = onCallSendClickListener;
    }

    public void setOnSendMessageClickListener(MyItemClickListener onSendMessageClickListener) {
        this.onSendMessageClickListener = onSendMessageClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_username;
        public TextView txt_repayAmt;
        public TextView txt_psDueDt;
        public TextView txt_days;
        public TextView txt_desc;
        public TextView txt_overDesc;
        public TextView txt_phone_number;
        public TextView txt_message_number;
        public TextView txt_commodityName;


        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_username = (TextView) rootView.findViewById(R.id.txt_username);
            this.txt_repayAmt = (TextView) rootView.findViewById(R.id.txt_repayAmt);
            this.txt_psDueDt = (TextView) rootView.findViewById(R.id.txt_psDueDt);
            this.txt_days = (TextView) rootView.findViewById(R.id.txt_days);
            this.txt_desc = (TextView) rootView.findViewById(R.id.txt_desc);
            this.txt_overDesc = (TextView) rootView.findViewById(R.id.txt_overDesc);
            this.txt_phone_number = (TextView) rootView.findViewById(R.id.txt_phone_number);
            this.txt_message_number = (TextView) rootView.findViewById(R.id.txt_message_number);
            this.txt_commodityName=(TextView)rootView.findViewById(R.id.txt_commodityName);
        }

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

}
