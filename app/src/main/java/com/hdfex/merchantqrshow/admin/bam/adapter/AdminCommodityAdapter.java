package com.hdfex.merchantqrshow.admin.bam.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.admin.bam.FindCommodityItem;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/5.
 * email : huangjinping@hdfex.com
 */

public class AdminCommodityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FindCommodityItem> dataList;
    private MyItemClickListener itemClickListener;
    private String counterFlag;
    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public AdminCommodityAdapter(List<FindCommodityItem> dataList, String counterFlag) {
        this.dataList = dataList;
        this.counterFlag = counterFlag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_commodity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        FindCommodityItem item = dataList.get(position);
        if (!TextUtils.isEmpty(item.getCommodityName())) {
            viewHolder.txt_commodityName.setText(item.getCommodityName());
        }
        if (!TextUtils.isEmpty(item.getCommoditCategory())) {
            viewHolder.txt_commoditCategory.setText(item.getCommoditCategory());
        }
        if (!TextUtils.isEmpty(item.getCommodityPrice())) {
            viewHolder.txt_commodityPrice.setText(item.getCommodityPrice());
        }
        if ("1".equals(counterFlag)) {
            /**
             * 上架
             */
            viewHolder.btn_edit.setVisibility(View.GONE);
            viewHolder.btn_soldOut.setVisibility(View.VISIBLE);
            viewHolder.btn_putaway.setVisibility(View.GONE);

        } else {
            /**下架
             *
             */
            viewHolder.btn_edit.setVisibility(View.GONE);
            viewHolder.btn_soldOut.setVisibility(View.GONE);
            viewHolder.btn_putaway.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(item.getPic())){
            Glide.with(viewHolder.rootView.getContext().getApplicationContext())
                    .load(item.getPic())
                    .placeholder(R.mipmap.ic_defoult)
                    .error(R.mipmap.ic_defoult)
                    .into(viewHolder.img_icon);
        }


        viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemClickListener != null) {
                    v.setTag("3");
                    itemClickListener.onItemClick(v, position);
                }
            }
        });
        viewHolder.btn_soldOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    v.setTag("2");
                    itemClickListener.onItemClick(v, position);
                }
            }
        });
        viewHolder.btn_putaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    v.setTag("1");
                    itemClickListener.onItemClick(v, position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView img_icon;
        public TextView txt_commodityName;
        public TextView txt_commodityPrice;
        public TextView txt_commoditCategory;
        public Button btn_edit;
        public Button btn_soldOut;
        public Button btn_putaway;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.img_icon = (ImageView) rootView.findViewById(R.id.img_icon);
            this.img_icon = (ImageView) rootView.findViewById(R.id.img_icon);
            this.txt_commodityName = (TextView) rootView.findViewById(R.id.txt_commodityName);
            this.txt_commodityPrice = (TextView) rootView.findViewById(R.id.txt_commodityPrice);
            this.txt_commoditCategory = (TextView) rootView.findViewById(R.id.txt_commoditCategory);
            this.btn_edit = (Button) rootView.findViewById(R.id.btn_edit);
            this.btn_soldOut = (Button) rootView.findViewById(R.id.btn_soldOut);
            this.btn_putaway = (Button) rootView.findViewById(R.id.btn_putaway);
        }

    }

}
