package com.hdfex.merchantqrshow.salesman.add.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;
import com.hdfex.merchantqrshow.widget.MyListViewClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/23.
 * email : huangjinping@hdfex.com
 */

public class CapitalTypeAdapter extends BaseAdapter {


    private List<Installment> dataList;
    private Context context;
    private MyListViewClickListener clickListener;

    private ViewHolder viewHolder;

    public CapitalTypeAdapter(Context context,List<Installment> dataList, MyListViewClickListener clickListener) {
        this.dataList = dataList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_capital, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Installment installment = dataList.get(position);
        if (!TextUtils.isEmpty(installment.getCapitalName())) {
            viewHolder.tv_capital_name.setText(installment.getCapitalName());
        }

//        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (clickListener!=null){
//                    clickListener.itemClick(v,position);
//                }
//            }
//        });
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_capital_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_capital_name = (TextView) rootView.findViewById(R.id.tv_capital_name);
        }

    }
}
