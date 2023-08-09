package com.hdfex.merchantqrshow.salesman.my.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.activity.WebActivity;
import com.hdfex.merchantqrshow.bean.salesman.home.SignStatusItem;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2019/5/31.
 * email : huangjinping1000@163.com
 */
public class SignResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SignStatusItem> dataList;
    private MyItemClickListener itemClickListener;


    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SignResultAdapter(List<SignStatusItem> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        final SignStatusItem signStatusItem = dataList.get(position);

        viewHolder.txt_customerName.setText("");
        viewHolder.txt_phone.setText("");
        viewHolder.txt_idNo.setText("");
        viewHolder.txt_comName.setText("");
        viewHolder.txt_customerName.setText("");
        viewHolder.btn_open_agreement.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(signStatusItem.getComName())) {
            viewHolder.txt_customerName.setText(signStatusItem.getComName());
        }
        if (!TextUtils.isEmpty(signStatusItem.getPhone())) {
            viewHolder.txt_phone.setText(signStatusItem.getPhone());
        }
        if (!TextUtils.isEmpty(signStatusItem.getIdNo())) {
            viewHolder.txt_idNo.setText(signStatusItem.getIdNo());
        }
        if (!TextUtils.isEmpty(signStatusItem.getComName())) {
            viewHolder.txt_comName.setText(signStatusItem.getComName());
        }
        if (!TextUtils.isEmpty(signStatusItem.getCustomerName())) {
            viewHolder.txt_customerName.setText(signStatusItem.getCustomerName());
        }

        if (!TextUtils.isEmpty(signStatusItem.getLinkUrl())) {
            viewHolder.btn_open_agreement.setVisibility(View.VISIBLE);
        } else {
            viewHolder.btn_open_agreement.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(signStatusItem.getDownLoadUrl())) {
            viewHolder.btn_shared_agreement.setVisibility(View.VISIBLE);
        } else {
            viewHolder.btn_shared_agreement.setVisibility(View.GONE);
        }

        viewHolder.btn_shared_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, position);
                }
            }
        });


        viewHolder.btn_open_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebActivity.start(v.getContext(), "优选加", signStatusItem.getLinkUrl());


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_customerName;
        public TextView txt_phone;
        public TextView txt_idNo;
        public TextView txt_comName;
        public Button btn_open_agreement;
        public Button btn_shared_agreement;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.btn_open_agreement = (Button) rootView.findViewById(R.id.btn_open_agreement);
            this.txt_customerName = (TextView) rootView.findViewById(R.id.txt_customerName);
            this.txt_phone = (TextView) rootView.findViewById(R.id.txt_phone);
            this.txt_idNo = (TextView) rootView.findViewById(R.id.txt_idNo);
            this.txt_comName = (TextView) rootView.findViewById(R.id.txt_comName);
            this.btn_shared_agreement = (Button) rootView.findViewById(R.id.btn_shared_agreement);
        }

    }


}
