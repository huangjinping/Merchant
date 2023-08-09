package com.hdfex.merchantqrshow.salesman.appointment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * author Created by harrishuang on 2017/9/8.
 * email : huangjinping@hdfex.com
 */

public class PublicClueAdapter extends BaseAdapter {

    private Context context;
    private List<String> dataList;

    public PublicClueAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
