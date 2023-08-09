package com.hdfex.merchantqrshow.manager.team.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.manager.team.PersonalPerform;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/1.
 * email : huangjinping@hdfex.com
 */

public class AchievementGridAdapter extends BaseAdapter {
    private Context context;
    private List<PersonalPerform> dataList;
    private ViewHolder holder;

    public AchievementGridAdapter(Context context, List<PersonalPerform> dataList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_achievementgrid, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PersonalPerform personalPerform = dataList.get(position);
        if (!TextUtils.isEmpty(personalPerform.getName())){
            holder.txt_name.setText(personalPerform.getName());
        }
        if (!TextUtils.isEmpty(personalPerform.getPersonalAmt())){
            holder.txt_personalAmt.setText(personalPerform.getPersonalAmt());
        }

        holder.txt_level.setText(position+1+"");
        return convertView;
    }



    class ViewHolder {
        public View rootView;
        public TextView txt_name;
        public TextView txt_personalAmt;
        public LinearLayout layout_content;
        public TextView txt_level;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_name = (TextView) rootView.findViewById(R.id.txt_name);
            this.txt_personalAmt = (TextView) rootView.findViewById(R.id.txt_personalAmt);
            this.layout_content = (LinearLayout) rootView.findViewById(R.id.layout_content);
            this.txt_level = (TextView) rootView.findViewById(R.id.txt_level);
        }

    }
}
