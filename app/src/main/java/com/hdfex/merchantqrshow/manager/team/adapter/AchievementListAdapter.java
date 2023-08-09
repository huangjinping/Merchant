package com.hdfex.merchantqrshow.manager.team.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.manager.team.PersonRecord;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/1.
 * email : huangjinping@hdfex.com
 */

public class AchievementListAdapter extends BaseAdapter {
    private Context context;
    private List<PersonRecord> dataList;
    private ViewHolder holder;


    public AchievementListAdapter(Context context, List<PersonRecord> dataList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_achievementlist, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PersonRecord personRecord = dataList.get(position);
        if (!TextUtils.isEmpty(personRecord.getName())) {
            holder.txt_achievement_name.setText(personRecord.getName());
        }
        if (!TextUtils.isEmpty(personRecord.getPersonalAmt())) {
            holder.txt_personalAmt.setText(personRecord.getPersonalAmt());
        }
        if (!TextUtils.isEmpty(personRecord.getRegion())) {
            holder.txt_region.setText(personRecord.getRegion());
        }
        return convertView;
    }

    class ViewHolder {
        public View rootView;
        public ImageView img_icon;
        public TextView txt_achievement_name;
        public TextView txt_region;
        public TextView txt_personalAmt;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.img_icon = (ImageView) rootView.findViewById(R.id.img_icon);
            this.txt_achievement_name = (TextView) rootView.findViewById(R.id.txt_achievement_name);
            this.txt_region = (TextView) rootView.findViewById(R.id.txt_region);
            this.txt_personalAmt = (TextView) rootView.findViewById(R.id.txt_personalAmt);
        }

    }
}
