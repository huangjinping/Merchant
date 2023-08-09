package com.hdfex.merchantqrshow.salesman.add.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdfex.merchantqrshow.bean.salesman.commodity.Type;
import com.hdfex.merchantqrshow.utils.DisplayUtils;
import com.hdfex.merchantqrshow.widget.MyListViewClickListener;
import com.hdfex.merchantqrshow.R;

import java.util.List;

/**
 *
 * Created by maidou521 on 2016/6/24.
 */
public class TypeListAdapter extends BaseAdapter {
    private List typeList;
    private Context context;
    private MyListViewClickListener clickListener;
    private int selectItem = -1;

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public TypeListAdapter(Context context, List typeList,MyListViewClickListener clickListener) {
        this.typeList = typeList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return typeList.size();
    }

    @Override
    public String getItem(int i) {
        return ((Type)typeList.get(i)).getCategory();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(context, R.layout.item_type, null);
        TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
        View v_divide = view.findViewById(R.id.v_divide);
        if(i==selectItem){
            tv_type.setSelected(true);
        }else {
            tv_type.setSelected(false);
        }
        ViewGroup.LayoutParams layoutParams = tv_type.getLayoutParams();
        if ("null".equals(getItem(i))|| TextUtils.isEmpty(getItem(i))){
            int tvHight = DisplayUtils.dip2px(context,44);
            layoutParams.height = tvHight;
            tv_type.setLayoutParams(layoutParams);
            tv_type.setBackgroundResource(R.color.bg_gray_light2);
            tv_type.setText(" ");
            v_divide.setVisibility(View.GONE);
        }else {
            int tvHight = DisplayUtils.dip2px(context,59);
            layoutParams.height = tvHight;
            tv_type.setText(getItem(i));
            v_divide.setVisibility(View.VISIBLE);
        }

        MyClickListener listener = new MyClickListener(i);
        tv_type.setOnClickListener(listener);
        return view;
    }

    private class MyClickListener implements View.OnClickListener {
        private int position;
        public MyClickListener(int i) {
            this.position = i;
        }
        @Override
        public void onClick(View view) {
            setSelectItem(position);
            clickListener.itemClick(view,position);
            notifyDataSetChanged();
        }
    }
}
