package com.hdfex.merchantqrshow.salesman.add.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;

import java.util.List;

/**
 * Created by harrishuang on 2017/3/21.
 */

public class InstallmentAdapter extends BaseAdapter {

    private List<Installment> dataList;
    private Context context;
    private ViewHolder viewHolder;
    private int index = -1;

    public InstallmentAdapter(List<Installment> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_periods, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        try {
            Installment installment = dataList.get(i);
            if (!TextUtils.isEmpty(installment.getDuration()) && viewHolder.img_period_counter != null) {
                viewHolder.img_period_counter.setText(installment.getDuration() + "期");
            }
            if (!TextUtils.isEmpty(installment.getCapitalName()) && viewHolder.img_period_name != null) {
                viewHolder.img_period_name.setText(installment.getCapitalName());
                if (!TextUtils.isEmpty(installment.getProfitSource())) {
                    viewHolder.img_period_name.setText(installment.getCapitalName() + "(" + installment.getProfitSource() + ")");
                }
            } else {
                if (!TextUtils.isEmpty(installment.getProfitSource()) && viewHolder.img_period_name != null) {
                    viewHolder.img_period_name.setText("(" + installment.getProfitSource() + ")");
                }
            }


            if (viewHolder.img_period_selection != null) {
                viewHolder.img_period_selection.setVisibility(View.INVISIBLE);
                if (index != -1) {
                    if (index == i) {
                        viewHolder.img_period_selection.setVisibility(View.VISIBLE);
                    }
                }
            }


            /**
             * 选择信息
             */
//            if (Installment.STATUS_YES.equals(installment.getStatus())){
//                viewHolder.layout_translucent.setVisibility(View.GONE);
//            }else {
//                viewHolder.layout_translucent.setVisibility(View.VISIBLE);
//            }


            if (viewHolder.img_period_icon != null) {
                Drawable nompic = context.getResources().getDrawable(R.mipmap.ic_defoult);
                Glide.with(context)
                        .load(installment.getCapitalLogo())
                        .error(nompic)
                        .placeholder(nompic)
                        .into(viewHolder.img_period_icon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     *
     */
    public class ViewHolder {
        public View rootView;
        public ImageView img_period_icon;
        public TextView img_period_counter;
        public TextView img_period_name;
        public ImageView img_period_selection;
        public LinearLayout layout_translucent;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.layout_translucent = (LinearLayout) rootView.findViewById(R.id.layout_translucent);
            this.img_period_icon = (ImageView) rootView.findViewById(R.id.img_period_icon);
            this.img_period_counter = (TextView) rootView.findViewById(R.id.img_period_counter);
            this.img_period_name = (TextView) rootView.findViewById(R.id.img_period_name);
            this.img_period_selection = (ImageView) rootView.findViewById(R.id.img_period_selection);
        }

    }
}
