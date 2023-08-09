package com.hdfex.merchantqrshow.salesman.add.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ShoppingCart;
import com.hdfex.merchantqrshow.widget.MyListViewClickListener;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by maidou521 on 2016/6/28.
 * 购物车ListView适配器
 */
public class PreSubmitGoodsAdapter extends BaseAdapter {
    private Context context;
    private List shoppinglist;
    private MyListViewClickListener clickListener;

    public PreSubmitGoodsAdapter(Context context, List shoppinglist, MyListViewClickListener clickListener) {
        this.context = context;
        this.shoppinglist = shoppinglist;
        this.clickListener = clickListener;
    }

    public List getShoppinglist() {
        return shoppinglist;
    }

    @Override
    public int getCount() {
        return shoppinglist.size();
    }

    @Override
    public Object getItem(int i) {
        return shoppinglist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.item_prepare_submit, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        TextView tv_goods_name = holder.tv_goods_name;
        TextView tv_price = holder.tv_goods_price;
        TextView tv_number = holder.tv_goods_number;
        ImageView iv_add = holder.iv_add;
        ImageView iv_cut = holder.iv_cut;

        if (shoppinglist.size() > 0) {
            ShoppingCart shopping = (ShoppingCart) shoppinglist.get(i);
            tv_goods_name.setText(shopping.getCommodityName());
            tv_number.setText(shopping.getCommodityNumber());
            tv_price.setText(shopping.getCommodityPrice());
        }

        MyClickNumberListener myClickNumberListener = new MyClickNumberListener(tv_number, tv_price, i);
        iv_add.setOnClickListener(myClickNumberListener);
        iv_cut.setOnClickListener(myClickNumberListener);

        return view;
    }

    private class MyClickNumberListener implements View.OnClickListener {
        private TextView tv_number;
        private TextView tv_price;
        private int position;

        public MyClickNumberListener(TextView tv_number, TextView tv_price, int position) {
            this.position = position;
            this.tv_number = tv_number;
            this.tv_price = tv_price;
        }

        @Override
        public void onClick(View view) {
            ShoppingCart shopping = (ShoppingCart) shoppinglist.get(position);
            BigDecimal number = new BigDecimal(tv_number.getText().toString());
            BigDecimal totalPrice = new BigDecimal(shopping.getCommodityPrice());
            BigDecimal unitprice = totalPrice.divide(number,2,BigDecimal.ROUND_HALF_UP);

            switch (view.getId()) {
                case R.id.iv_add:
                    number = number.add(new BigDecimal(1));
                    hiddenOrShow(Integer.parseInt(number.toString()));
                    tv_number.setText("" + number);
                    tv_price.setText("" + number.multiply(unitprice));
                    shopping.setCommodityNumber("" + number);
                    shopping.setCommodityPrice("" + number.multiply(unitprice));
                    clickListener.itemClick(shopping, position);
                    break;
                case R.id.iv_cut:
                    number = number.subtract(new BigDecimal(1));
                    hiddenOrShow(Integer.parseInt(number.toString()));
                    tv_number.setText("" + number);
                    tv_price.setText("" + number.multiply(unitprice));
                    shopping.setCommodityNumber("" + number);
                    shopping.setCommodityPrice("" + number.multiply(unitprice));
                    clickListener.itemClick(shopping, position);
                    break;
            }
        }

        private void hiddenOrShow(int number) {
            if (number <= 0) {
//                Log.e("123","删除");
                shoppinglist.remove(position);
            }
        }
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_goods_name;
        public TextView tv_goods_price;
        public ImageView iv_cut;
        public TextView tv_goods_number;
        public ImageView iv_add;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_goods_name = (TextView) rootView.findViewById(R.id.tv_goods_name);
            this.tv_goods_price = (TextView) rootView.findViewById(R.id.tv_goods_price);
            this.iv_cut = (ImageView) rootView.findViewById(R.id.iv_cut);
            this.tv_goods_number = (TextView) rootView.findViewById(R.id.tv_goods_number);
            this.iv_add = (ImageView) rootView.findViewById(R.id.iv_add);
        }

    }
}
