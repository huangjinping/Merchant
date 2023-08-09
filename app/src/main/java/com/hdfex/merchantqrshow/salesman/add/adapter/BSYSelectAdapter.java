package com.hdfex.merchantqrshow.salesman.add.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.bsy.ProductItem;

import java.util.List;

/**
 *
 * Created by harrishuang on 2016/11/24.
 */

public class BSYSelectAdapter extends BaseAdapter {

    private List<ProductItem> dataList;
    private Context context;

    public BSYSelectAdapter(List<ProductItem> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    private  ViewHolder viewHolder;

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bsyselect, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ProductItem productItem = dataList.get(position);
        if (productItem!=null){
            Glide.with(context.getApplicationContext())
                    .load(productItem.getCommodityUrl())
                    .placeholder(R.mipmap.ic_defoult)
                    .error(R.mipmap.ic_defoult)
                    .into(viewHolder.img_order_pic);
            if (!TextUtils.isEmpty(productItem.getCommodityName())){
                viewHolder. txt_commodity_name.setText(productItem.getCommodityName());
            }
            if (!TextUtils.isEmpty(productItem.getParameter())){
                viewHolder. txt_product_id.setText(productItem.getParameter());
            }
            if (productItem.getCommodityPrice()!=null){
                viewHolder. txt_amount.setText(productItem.getCommodityPrice().toString());
            }
            if (productItem.getCommodityPrice()!=null){
                viewHolder. txt_amount.setText(productItem.getCommodityPrice().toString());
            }
            if (!TextUtils.isEmpty(productItem.getCountLeft())){
                viewHolder. txt_product_status.setText("库存"+productItem.getCountLeft()+"件");
            }else {
                viewHolder. txt_product_status.setText("暂无库存");
            }

        }
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView img_order_pic;
        public TextView txt_commodity_name;
        public TextView txt_product_id;
        public TextView txt_product_status;
        public TextView txt_amount;
        public LinearLayout layout_item_orderlist;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.img_order_pic = (ImageView) rootView.findViewById(R.id.img_order_pic);
            this.txt_commodity_name = (TextView) rootView.findViewById(R.id.txt_commodity_name);
            this.txt_product_id = (TextView) rootView.findViewById(R.id.txt_product_id);
            this.txt_product_status = (TextView) rootView.findViewById(R.id.txt_product_status);
            this.txt_amount = (TextView) rootView.findViewById(R.id.txt_amount);
            this.layout_item_orderlist = (LinearLayout) rootView.findViewById(R.id.layout_item_orderlist);
        }

    }
}
