package com.hdfex.merchantqrshow.salesman.appointment.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.appointment.SubscribeItem;

import java.util.List;

/**
 * author Created by harrishuang on 2017/9/8.
 * email : huangjinping@hdfex.com
 */

public class ReservationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SubscribeItem> dataList;

    private ReservationCallBack callBack;
    private String status;


    public ReservationListAdapter(List<SubscribeItem> dataList, String status) {
        this.dataList = dataList;
        this.status = status;

    }

    public void setCallBack(ReservationCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservationlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        final SubscribeItem subscribeItem = dataList.get(position);


        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (callBack != null) {
                    callBack.onDetails(subscribeItem, position);
                }
            }
        });


        if (!TextUtils.isEmpty(subscribeItem.getStatus())) {
            if (SubscribeItem.STATUS_NOT_FOLLOW_UP.equals(subscribeItem.getStatus())) {
                viewHolder.txt_status.setText("未跟进");
            } else if (SubscribeItem.STATUS_FOLLOWED.equals(subscribeItem.getStatus())) {
                viewHolder.txt_status.setText("已跟进");

            } else if (SubscribeItem.STATUS_COMPLATE.equals(subscribeItem.getStatus())) {
                viewHolder.txt_status.setText("已完成");

            } else if (SubscribeItem.STATUS_TURNING.equals(subscribeItem.getStatus())) {
                viewHolder.txt_status.setText("转单中");
            }
        }

        viewHolder.txt_address.setText("");
        viewHolder.txt_subscribeDate.setText("");
        viewHolder.txt_bizName.setText("");
        viewHolder.txt_followTime.setText("");
        viewHolder.txt_turnDesc.setText("");
        viewHolder.txt_turnDesc.setText("");
        if ("03".equals(subscribeItem.getType())) {

            if (!TextUtils.isEmpty(subscribeItem.getAddress())) {
                viewHolder.txt_courtName.setText(subscribeItem.getAddress());
            }

            if (!TextUtils.isEmpty(subscribeItem.getExpectAmount())) {
                viewHolder.txt_address.setText("期望租金:" + subscribeItem.getExpectAmount() + "元/月 ");
            }

            if (!TextUtils.isEmpty(subscribeItem.getExpectAmount()) && !TextUtils.isEmpty(subscribeItem.getCheckInDate())) {
                viewHolder.txt_address.setText("期望租金:" + subscribeItem.getExpectAmount() + "元/月 入住时间:" + subscribeItem.getCheckInDate());
            }


            if (!TextUtils.isEmpty(subscribeItem.getRemark())) {
                viewHolder.txt_subscribeDate.setText(subscribeItem.getRemark());
            }
        } else {

            if (!TextUtils.isEmpty(subscribeItem.getCourtName())) {
                viewHolder.txt_courtName.setText(subscribeItem.getCourtName());
            }
            if (!TextUtils.isEmpty(subscribeItem.getAddrDetail())) {
                viewHolder.txt_address.setText(subscribeItem.getAddrDetail());
            }
            if (!TextUtils.isEmpty(subscribeItem.getExpectAmount())) {
                viewHolder.txt_address.setText("期望金额" + subscribeItem.getExpectAmount() + "元/月");
            }
            if (!TextUtils.isEmpty(subscribeItem.getSubscribeDate())) {
                viewHolder.txt_subscribeDate.setText(subscribeItem.getSubscribeDate() + "预约");
            }

        }
        if (!TextUtils.isEmpty(subscribeItem.getBizName())) {
            viewHolder.txt_bizName.setText(subscribeItem.getBizName());
        }

        if (!TextUtils.isEmpty(subscribeItem.getFollowTime())) {
            viewHolder.txt_followTime.setText(subscribeItem.getFollowTime() + "未跟进");
        }
        if (!TextUtils.isEmpty(subscribeItem.getTurnDesc())) {
            viewHolder.txt_turnDesc.setVisibility(View.VISIBLE);
            viewHolder.txt_turnDesc.setText(subscribeItem.getTurnDesc());
        } else {
            viewHolder.txt_turnDesc.setVisibility(View.GONE);
        }


//        String	转单类型
//        00-	发起方01-	接收方
        viewHolder.txt_accept.setVisibility(View.GONE);
        viewHolder.txt_turn.setVisibility(View.GONE);
        viewHolder.txt_refuse.setVisibility(View.GONE);
        viewHolder.txt_rushOrder.setVisibility(View.GONE);

        /**
         * 公共的调用
         */
        if (SubscribeItem.public_LIST.equals(status)) {
            subscribeItem.setRefuseType(SubscribeItem.REFUSE_TYPE_PUBLIC);
            viewHolder.txt_rushOrder.setVisibility(View.VISIBLE);
            viewHolder.txt_refuse.setVisibility(View.VISIBLE);
        } else {
            /**
             * 个人信息
             */
            subscribeItem.setRefuseType(SubscribeItem.REFUSE_TYPE_PRIVATE);
            if (!TextUtils.isEmpty(subscribeItem.getStatus())) {
                if (SubscribeItem.STATUS_NOT_FOLLOW_UP.equals(subscribeItem.getStatus())) {
//                    未跟进
                    viewHolder.txt_accept.setVisibility(View.VISIBLE);
                    viewHolder.txt_refuse.setVisibility(View.VISIBLE);
                    viewHolder.txt_turn.setVisibility(View.VISIBLE);
                } else if (SubscribeItem.STATUS_TURNING.equals(subscribeItem.getStatus())) {
//                    转单中
//                    00-	发起方
//                    01-	接收方
                    if (SubscribeItem.TURN_TYPE_SEND.equals(subscribeItem.getTurnType())) {

                    }
                    if (SubscribeItem.TURN_TYPE_RECEIVE.equals(subscribeItem.getTurnType())) {
                        viewHolder.txt_accept.setVisibility(View.VISIBLE);
                        viewHolder.txt_refuse.setVisibility(View.VISIBLE);
                    }
                }
            }

        }


        viewHolder.txt_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onAccpet(subscribeItem, position);
                }
            }
        });
        viewHolder.txt_turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onTurn(subscribeItem, position);
                }
            }
        });

        viewHolder.txt_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onRefuse(subscribeItem, position);
                }
            }
        });

        viewHolder.txt_rushOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onRushOrder(subscribeItem, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }


    public interface ReservationCallBack {

        void onAccpet(SubscribeItem item, int index);

        void onTurn(SubscribeItem item, int index);

        void onRefuse(SubscribeItem item, int index);

        void onDetails(SubscribeItem item, int index);

        void onRushOrder(SubscribeItem item, int index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_courtName;
        public TextView txt_status;
        public TextView txt_address;
        public TextView txt_turnType;
        public TextView txt_subscribeDate;
        public TextView txt_bizName;
        public TextView txt_followTime;
        public TextView txt_turnDesc;
        public TextView txt_accept;
        public TextView txt_turn;
        public TextView txt_refuse;
        public TextView txt_rushOrder;


        public ViewHolder(View rootView) {
            super(rootView);

            this.rootView = rootView;
            this.txt_courtName = (TextView) rootView.findViewById(R.id.txt_courtName);
            this.txt_status = (TextView) rootView.findViewById(R.id.txt_status);
            this.txt_address = (TextView) rootView.findViewById(R.id.txt_address);
            this.txt_turnType = (TextView) rootView.findViewById(R.id.txt_turnType);
            this.txt_subscribeDate = (TextView) rootView.findViewById(R.id.txt_subscribeDate);
            this.txt_bizName = (TextView) rootView.findViewById(R.id.txt_bizName);
            this.txt_followTime = (TextView) rootView.findViewById(R.id.txt_followTime);
            this.txt_turnDesc = (TextView) rootView.findViewById(R.id.txt_turnDesc);
            this.txt_accept = (TextView) rootView.findViewById(R.id.txt_accept);
            this.txt_turn = (TextView) rootView.findViewById(R.id.txt_turn);
            this.txt_refuse = (TextView) rootView.findViewById(R.id.txt_refuse);
            this.txt_rushOrder = (TextView) rootView.findViewById(R.id.txt_rushOrder);
        }

    }
}
