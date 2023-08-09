package com.hdfex.merchantqrshow.widget.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.admin.bam.SectionItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author Created by harrishuang on 2017/12/7.
 * email : huangjinping@hdfex.com
 */

public class FlowlayoutPicker extends WheelPicker {

    /**
     * Instantiates a new Wheel picker.
     *
     * @param activity the activity
     */

    private List<String> dataSearchList;
    private List<SectionItem> dataSelecedList;
    private FlowLayoutAdapter mAdapter;
    private OnResultListener onResultListener;


    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public FlowlayoutPicker(Activity activity, List<String> dataSearchList) {
        super(activity);
        this.dataSearchList = dataSearchList;
    }

    @NonNull
    @Override
    protected View makeCenterView() {
        dataSelecedList = new ArrayList<>();
        for (int i = 0; i < dataSearchList.size(); i++) {
            SectionItem item = new SectionItem();
            item.setName(dataSearchList.get(i));
            item.setSelected(false);
            dataSelecedList.add(item);
        }
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams dataWeekParams = new LinearLayout.LayoutParams(screenWidthPixels, WRAP_CONTENT);
        View view = LayoutInflater.from(activity).inflate(R.layout.picker_flowlayout, layout, false);
        ListView lisv_flow = (ListView) view.findViewById(R.id.lisv_flow);
//        ListView listView=new ListView(activity);
        mAdapter = new FlowLayoutAdapter();
        lisv_flow.setAdapter(mAdapter);
        layout.addView(view, dataWeekParams);
        mAdapter.notifyDataSetChanged();
        lisv_flow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataSelecedList.get(position).setSelected(!dataSelecedList.get(position).isSelected());
                mAdapter.notifyDataSetChanged();
            }
        });
        return layout;
    }

    public class FlowLayoutAdapter extends BaseAdapter {
        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return dataSelecedList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataSelecedList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.item_flow, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            SectionItem sectionItem = dataSelecedList.get(position);
            viewHolder.txt_item_name.setText(sectionItem.getName());
            if (sectionItem.isSelected()) {
                viewHolder.txt_item_name.setTextColor(activity.getResources().getColor(R.color.blue_light));
            } else {
                viewHolder.txt_item_name.setTextColor(activity.getResources().getColor(R.color.gray));

            }

            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public TextView txt_item_name;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.txt_item_name = (TextView) rootView.findViewById(R.id.txt_item_name);
            }

        }
    }

    public   interface  OnResultListener{
        void  onResult(Set<Integer>  set);
    }

    @Override
    protected void onSubmit() {
        super.onSubmit();
        Set<Integer>  set=new HashSet();
        for (int i = 0; i < dataSelecedList.size(); i++) {
            SectionItem sectionItem = dataSelecedList.get(i);
            if (sectionItem.isSelected()){
                set.add(i);
            }
        }
        if (onResultListener!=null){
            onResultListener.onResult(set);
        }
    }
}
