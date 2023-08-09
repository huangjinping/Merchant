package com.hdfex.merchantqrshow.salesman.my.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.message.MessageItem;

import java.util.List;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageItem> dataList;

    public MessageAdapter(Context context, List<MessageItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    private ViewHolder viewHolder;

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_message, null);
            viewHolder =new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        MessageItem messageItem = dataList.get(position);
        if (!TextUtils.isEmpty(messageItem.getTitle())){
                viewHolder.txt_title.setText(messageItem.getTitle());
        }
        if (!TextUtils.isEmpty(messageItem.getContent())){
            viewHolder.txt_content.setText(messageItem.getContent());
        }

        if (!TextUtils.isEmpty(messageItem.getPublishtime())){
            viewHolder.txt_publishtime.setText(messageItem.getPublishtime());
        }


        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_title;
        public TextView txt_content;
        public TextView txt_publishtime;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_title = (TextView) rootView.findViewById(R.id.txt_title);
            this.txt_content = (TextView) rootView.findViewById(R.id.txt_content);
            this.txt_publishtime = (TextView) rootView.findViewById(R.id.txt_publishtime);
        }

    }
}
