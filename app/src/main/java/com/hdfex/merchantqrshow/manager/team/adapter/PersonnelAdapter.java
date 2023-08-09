package com.hdfex.merchantqrshow.manager.team.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.manager.team.Person;
import com.hdfex.merchantqrshow.bean.manager.team.RegionResult;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.EventRxBus;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class PersonnelAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<RegionResult> dataList;

    private ViewParentHolder viewParentHolder;

    private ViewHolder viewHolder;


    public PersonnelAdapter(Context context, List<RegionResult> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getGroupCount() {
        return dataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (dataList.get(groupPosition).getPersonList() == null) {
            return 0;
        }
        return dataList.get(groupPosition).getPersonList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (dataList.get(groupPosition).getPersonList() == null) {
            return "";
        }


        return dataList.get(groupPosition).getPersonList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_personnel_parent, null);
            viewParentHolder = new ViewParentHolder(convertView);
            convertView.setTag(viewParentHolder);
        } else {
            viewParentHolder = (ViewParentHolder) convertView.getTag();
        }
        if (isExpanded) {
            viewParentHolder.img_arrow.setImageResource(R.mipmap.ic_arrow_down);
        } else {
            viewParentHolder.img_arrow.setImageResource(R.mipmap.ic_arrow_right);
        }
        RegionResult regionResult = dataList.get(groupPosition);
        viewParentHolder.txt_region.setText("");

        if (!TextUtils.isEmpty(regionResult.getRegionName())) {
            viewParentHolder.txt_region.setText(regionResult.getRegionName() + "（" + regionResult.getCustCount() + "）");
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_personnel_child, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       final Person person = dataList.get(groupPosition).getPersonList().get(childPosition);

        if (!TextUtils.isEmpty(person.getName())) {
            viewHolder.txt_name.setText(person.getName());
        }
        if (!TextUtils.isEmpty(person.getPhoneNo())) {
            viewHolder.txt_phoneNo.setText(person.getPhoneNo());
        }
        if (!TextUtils.isEmpty(person.getStatus())) {
            viewHolder.txt_name.setText(person.getName());
        }


        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventRxBus.getInstance().post(IntentFlag.SALES_DETAILS, person.getId());
            }
        });
        viewHolder.txt_status.setText("");
        if (Person.STATUS_DISENABLED.equals(person.getStatus())) {
            viewHolder.txt_status.setText("禁用");
            viewHolder.txt_status.setTextColor(context.getResources().getColor(R.color.red_light));
        } else if (Person.STATUS_ENABLED.equals(person.getStatus())) {
            viewHolder.txt_status.setText("在职");
            viewHolder.txt_status.setTextColor(context.getResources().getColor(R.color.blue_light));
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {


        return true;
    }


    public class ViewParentHolder {
        public View rootView;
        public TextView txt_region;
        public ImageView img_arrow;

        public ViewParentHolder(View rootView) {
            this.rootView = rootView;
            this.txt_region = (TextView) rootView.findViewById(R.id.txt_region);
            this.img_arrow = (ImageView) rootView.findViewById(R.id.img_arrow);
        }

    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_name;
        public TextView txt_phoneNo;
        public TextView txt_status;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_name = (TextView) rootView.findViewById(R.id.txt_name);
            this.txt_phoneNo = (TextView) rootView.findViewById(R.id.txt_phoneNo);
            this.txt_status = (TextView) rootView.findViewById(R.id.txt_status);
        }

    }
}
