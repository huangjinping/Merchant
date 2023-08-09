package com.hdfex.merchantqrshow.manager.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.manager.business.RegionForOrder;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/7.
 * email : huangjinping@hdfex.com
 */

public class FilterOrderAdapter extends BaseAdapter {

    private Context context;
    private List<RegionForOrder> dataList;
    private MerchantListAdapter.ViewHolder holder;

    public FilterOrderAdapter(Context context, List<RegionForOrder> dataList) {
        this.context = context;
        this.dataList = dataList;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_filterorder, null);
            holder = new MerchantListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MerchantListAdapter.ViewHolder) convertView.getTag();
        }
        RegionForOrder regionForOrder = dataList.get(position);
        if (!TextUtils.isEmpty(regionForOrder.getRegionName())) {
            holder.txt_merchant_name.setText(regionForOrder.getRegionName());
        }
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_merchant_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_merchant_name = (TextView) rootView.findViewById(R.id.txt_merchant_name);
        }

    }
}
