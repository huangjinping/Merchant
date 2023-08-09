package com.hdfex.merchantqrshow.salesman.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.customer.CustomerResult;

import java.util.List;

/**
 * author Created by harrishuang on 2017/9/13.
 * email : huangjinping@hdfex.com
 */

public class CustomerSearchAdapter extends BaseAdapter {
    private Context context;

    private List<CustomerResult> dataSearchList;

    public CustomerSearchAdapter(Context context, List<CustomerResult> dataSearchList) {
        this.context = context;
        this.dataSearchList = dataSearchList;
    }

    private CustomerSearchAdapter.ViewHolder viewHolder;

    @Override
    public int getCount() {
        return dataSearchList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSearchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_customer, null);
            viewHolder = new CustomerSearchAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomerSearchAdapter.ViewHolder) convertView.getTag();
        }
        CustomerResult customerResult = dataSearchList.get(position);
        viewHolder.name.setText(customerResult.getCustName());
        viewHolder.alpha.setVisibility(View.GONE);
        return convertView;
    }

    /**
     * 数据问题
     */
    public final class ViewHolder {
        public View rootView;
        public TextView alpha;
        public TextView name;
        public LinearLayout ll_item;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.alpha = (TextView) rootView.findViewById(R.id.alpha);
            this.name = (TextView) rootView.findViewById(R.id.name);
            this.ll_item = (LinearLayout) rootView.findViewById(R.id.ll_item);
        }

    }
}
