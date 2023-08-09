package com.hdfex.merchantqrshow.manager.finance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;

import java.util.List;

/**
 * author Created by harrishuang on 2017/5/31.
 * email : huangjinping@hdfex.com
 */

public class IncomeAdapter extends BaseAdapter {

    private List<String> dataList;
    private Context context;
    private ViewHolder viewHolder;

    public IncomeAdapter(List<String> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_income, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_time;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_time = (TextView) rootView.findViewById(R.id.txt_time);
        }

    }
}
