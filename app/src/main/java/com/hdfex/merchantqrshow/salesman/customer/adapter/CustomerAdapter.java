package com.hdfex.merchantqrshow.salesman.customer.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.customer.CustomerResult;
import com.hdfex.merchantqrshow.salesman.customer.fragment.CustomerFragment;

import java.util.List;

/**
 * author Created by harrishuang on 2017/9/13.
 * email : huangjinping@hdfex.com
 */

public class CustomerAdapter extends BaseAdapter {

    private Context context;

    private List<CustomerResult> dataList;

    public CustomerAdapter(Context context, List<CustomerResult> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    private CustomerAdapter.ViewHolder viewHolder;

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_customer, null);
            viewHolder = new CustomerAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomerAdapter.ViewHolder) convertView.getTag();
        }
        CustomerResult customerResult = dataList.get(position);
        viewHolder.name.setText(customerResult.getCustName());
        viewHolder.alpha.setVisibility(View.GONE);
        if (position == 0) {
            viewHolder.alpha.setVisibility(View.VISIBLE);
        }

        String phoneticName = customerResult.getPhoneticName();
        if (!TextUtils.isEmpty(phoneticName) && phoneticName.length() > 0) {
            if (position > 0) {
                char c = phoneticName.charAt(0);
                CustomerResult customerResult1 = dataList.get(position - 1);
                if (customerResult1 != null && !TextUtils.isEmpty(customerResult1.getPhoneticName())) {
                    char c1 = customerResult1.getPhoneticName().charAt(0);
                    if (c != c1) {
                        viewHolder.alpha.setVisibility(View.VISIBLE);
                    }
                }
            }
            viewHolder.alpha.setText(phoneticName.charAt(0) + "");
        }
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
