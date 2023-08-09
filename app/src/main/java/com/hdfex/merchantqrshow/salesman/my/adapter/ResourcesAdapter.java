package com.hdfex.merchantqrshow.salesman.my.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.resource.ItemHouse;
import com.hdfex.merchantqrshow.net.IntentFlag;

import java.util.List;

/**
 * Created by harrishuang on 2017/2/9.
 * 房源库
 */

public class ResourcesAdapter extends BaseAdapter {

    private List<ItemHouse> dataSource;
    private Context context;
    private ViewHolder viewHolder;
    private String type;
    public void setShowArrow(boolean showArrow) {
        this.showArrow = showArrow;
    }
    private boolean showArrow = false;
    public ResourcesAdapter(Context context, List<ItemHouse> dataSource, String type) {
        this.context = context;
        this.dataSource = dataSource;
        this.type = type;
    }
    @Override
    public int getCount() {
        return dataSource.size();
    }
    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_resurces, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ItemHouse itemHouse = dataSource.get(position);
        if (!TextUtils.isEmpty(itemHouse.getDetailAddress())) {
            viewHolder.txt_commodity_address.setText(itemHouse.getDetailAddress());
        }
        if (!TextUtils.isEmpty(itemHouse.getCourtName())) {
            viewHolder.txt_court_name.setText(itemHouse.getCourtName());
        }

        if (!TextUtils.isEmpty(itemHouse.getRentType())) {

            if ("1".equals(itemHouse.getRentType())) {
                viewHolder.txt_renttype.setText("合租");
            } else if ("2".equals(itemHouse.getRentType())) {
                viewHolder.txt_renttype.setText("整租");
            }
        }
        if (IntentFlag.HOSUE_TYPE_ELIMINATE.equals(type)) {
            viewHolder.txt_house_status.setText("需清退");
        } else if (IntentFlag.HOSUE_TYPE_FREE.equals(type)) {
            viewHolder.txt_house_status.setText("闲置");
            viewHolder.txt_house_status.setTextColor(context.getResources().getColor(R.color.blue_light));
        } else if (IntentFlag.HOSUE_TYPE_RENTED.equals(type)) {
            viewHolder.txt_house_status.setText("已出租");
            viewHolder.txt_house_status.setTextColor(context.getResources().getColor(R.color.blue_light));
        }
        if (showArrow) {
            viewHolder.img_arrow_right.setVisibility(View.VISIBLE);
        } else {
            viewHolder.img_arrow_right.setVisibility(View.GONE);
        }
        return convertView;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView txt_court_name;
        public TextView txt_renttype;
        public TextView txt_commodity_address;
        public TextView txt_house_status;
        public ImageView img_arrow_right;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_court_name = (TextView) rootView.findViewById(R.id.txt_court_name);
            this.txt_renttype = (TextView) rootView.findViewById(R.id.txt_renttype);
            this.txt_commodity_address = (TextView) rootView.findViewById(R.id.txt_commodity_address);
            this.txt_house_status = (TextView) rootView.findViewById(R.id.txt_house_status);
            this.img_arrow_right = (ImageView) rootView.findViewById(R.id.img_arrow_right);
        }

    }
}
