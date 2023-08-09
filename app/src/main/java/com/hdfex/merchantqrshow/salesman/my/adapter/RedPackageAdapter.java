package com.hdfex.merchantqrshow.salesman.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.manager.finance.fragment.IncomeFragment;

import java.util.List;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class RedPackageAdapter extends BaseAdapter {

    private List<RedPackage> dataList;

    private  ViewHolder viewHolder;

    private Context context;


    public RedPackageAdapter(List<RedPackage> dataList, Context context) {
        this.dataList = dataList;
        this.context=context;

    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_redpackage, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        RedPackage redPackage = dataList.get(position);

        if (!TextUtils.isEmpty(redPackage.getOperDate())) {
            viewHolder.txt_operdate.setText(redPackage.getOperDate());
        }
        if (!TextUtils.isEmpty(redPackage.getRemark())) {
            viewHolder.txt_tradetype.setText(redPackage.getRemark());
        }

        if (!TextUtils.isEmpty(redPackage.getRedPacketAmt())) {
            viewHolder.txt_redpacketamt.setText(redPackage.getRedPacketAmt());
        }

//        if (RedPackage.WITH_DRAW_FLAG_YES.equals(redPackage.getWithdrawFlag())){
//            viewHolder.txt_remark.setText("已提现");
//            viewHolder.txt_remark.setTextColor(context.getResources().getColor(R.color.gray));
//        }
//        if (RedPackage.WITH_DRAW_FLAG_NO.equals(redPackage.getWithdrawFlag())){
//            viewHolder.txt_remark.setText("未提现");
//            viewHolder.txt_remark.setTextColor(context.getResources().getColor(R.color.black));
//        }


        return convertView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_tradetype;
        public TextView txt_redpacketamt;
        public TextView txt_operdate;
        public TextView txt_remark;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_tradetype = (TextView) rootView.findViewById(R.id.txt_tradetype);
            this.txt_redpacketamt = (TextView) rootView.findViewById(R.id.txt_redpacketamt);
            this.txt_operdate = (TextView) rootView.findViewById(R.id.txt_operdate);
            this.txt_remark = (TextView) rootView.findViewById(R.id.txt_remark);
        }

    }
}
