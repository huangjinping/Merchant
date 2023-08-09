package com.hdfex.merchantqrshow.manager.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.resource.Apartment;
import com.hdfex.merchantqrshow.utils.StringUtils;

import java.util.List;

/**
 * Created by harrishuang on 2017/3/20.
 */

public class CommAdapter extends BaseAdapter {
    private Context context;

    private List<String>  dataList;
    private  ViewHolder holder;

    public CommAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_apertment, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder= (ViewHolder) view.getTag();
        }
        String s = dataList.get(i);
        holder.txt_house_name.setText(s);
        return view;
    }
    public static class ViewHolder {
        public View rootView;
        public TextView txt_house_name;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_house_name = (TextView) rootView.findViewById(R.id.txt_house_name);
        }
    }
}
