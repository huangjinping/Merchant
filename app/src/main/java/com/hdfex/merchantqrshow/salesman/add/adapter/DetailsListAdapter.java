package com.hdfex.merchantqrshow.salesman.add.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Details;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ShoppingCart;
import com.hdfex.merchantqrshow.widget.MyListViewClickListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by maidou521 on 2016/6/24.
 */
public class DetailsListAdapter extends BaseAdapter {
    private List DetailsList;
    private Context context;
    private MyListViewClickListener clickListener;

    public DetailsListAdapter(Context context, List DetailsList, MyListViewClickListener clickListener) {
        this.DetailsList = DetailsList;
        this.context = context;
        this.clickListener = clickListener;

    }

    public List getDetailsList() {
        return DetailsList;
    }

    public void setDetailsList(List detailsList) {
        DetailsList = detailsList;
    }

    @Override
    public int getCount() {
        return DetailsList.size();
    }

    @Override
    public Details getItem(int i) {
        if (DetailsList.size() == 0) {
            return null;
        }
        return (Details) DetailsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_details, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        try {
            TextView tv_name = holder.tv_commodity_name;
            final TextView tv_number = holder.tv_goods_number;
            TextView tv_price = holder.tv_goods_price;
            final ImageView btn_cut = holder.btn_cut;
            ImageView btn_add = holder.btn_add;
            ImageView iv_goods_pic = holder.iv_goods_pic;

            Details details = getItem(position);
            tv_name.setText(details.getCommodityName());
            tv_number.setText(details.getCommodityNumber());
            if (details.getCommodityNumber() == null) {
                tv_number.setText("" + 0);
            } else {
                tv_number.setText(details.getCommodityNumber());
            }
            if (tv_number.getText().equals("0")) {
                tv_number.setVisibility(View.INVISIBLE);
                btn_cut.setVisibility(View.INVISIBLE);
                btn_cut.setEnabled(false);
            } else {
                tv_number.setVisibility(View.VISIBLE);
                btn_cut.setVisibility(View.VISIBLE);
                btn_cut.setEnabled(true);
            }
            if (!TextUtils.isEmpty(details.getCommodityPrice())) {
                tv_price.setText(new BigDecimal(details.getCommodityPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            } else {
                tv_price.setText("");
            }
            if (!TextUtils.isEmpty(details.getCommodityUrl())) {
                Glide.with(context.getApplicationContext())
                        .load(details.getCommodityUrl())
                        .placeholder(R.mipmap.ic_defoult)
                        .error(R.mipmap.ic_defoult)
                        .into(iv_goods_pic);
            } else {
                iv_goods_pic.setBackgroundResource(R.mipmap.ic_defoult);
            }

            MyClickNumbersListener listener = new MyClickNumbersListener(btn_cut, tv_name, tv_number, tv_price, position);
            btn_add.setOnClickListener(listener);
            btn_cut.setOnClickListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    private class MyClickNumbersListener implements View.OnClickListener {
        private ImageView btn_cut;
        private TextView tv_name;
        private TextView tv_number;
        private TextView tv_price;
        private int position;

        public MyClickNumbersListener(ImageView btn_cut, TextView tv_name, TextView tv_number, TextView tv_price, int position) {
            this.tv_name = tv_name;
            this.tv_price = tv_price;
            this.tv_number = tv_number;
            this.btn_cut = btn_cut;
            this.position = position;
        }

        public void hidden(int number) {
            if (number <= 0) {
                AnimatorSet aniset = new AnimatorSet();
                ObjectAnimator alpha1 = ObjectAnimator.ofFloat(btn_cut, "alpha", 1f, 0f);
                alpha1.setDuration(250);
                ObjectAnimator alpha2 = ObjectAnimator.ofFloat(tv_number, "alpha", 1f, 0f);
                alpha2.setDuration(250);
                ObjectAnimator trans = ObjectAnimator.ofFloat(btn_cut, "translationX", 0f, 150f);
                trans.setDuration(250);
                ObjectAnimator rotation = ObjectAnimator.ofFloat(btn_cut, "rotation", 0f, -360f);
                rotation.setDuration(250);
                aniset.play(alpha1).with(alpha2).with(trans).with(rotation);
                aniset.start();
                btn_cut.setEnabled(false);
            }
        }

        public void show(int number) {
            if (number == 1) {
                btn_cut.setVisibility(View.VISIBLE);
                tv_number.setVisibility(View.VISIBLE);
                AnimatorSet aniset = new AnimatorSet();
                ObjectAnimator alpha1 = ObjectAnimator.ofFloat(btn_cut, "alpha", 0.2f, 1f);
                alpha1.setDuration(250);
                ObjectAnimator alpha2 = ObjectAnimator.ofFloat(tv_number, "alpha", 0f, 1f);
                alpha2.setDuration(250);
                ObjectAnimator trans = ObjectAnimator.ofFloat(btn_cut, "translationX", 150f, 0f);
                trans.setDuration(250);
                ObjectAnimator rotation = ObjectAnimator.ofFloat(btn_cut, "rotation", -360f, 0f);
                rotation.setDuration(250);
                aniset.play(alpha1).with(alpha2).with(trans).with(rotation);
                aniset.start();
                btn_cut.setEnabled(true);
            }
        }

        @Override
        public void onClick(View view) {
            Details details = getItem(position);
            if (details == null) {
                return;
            }
////            ToastUtils.makeText(context,"三\r星\n手\r\n机＼（）ｓ三＼ｒ＼ｎ星手机").show();
//            ToastUtils.makeText(context,details.getCommodityName()).show();
            ShoppingCart shopping = new ShoppingCart();
            BigDecimal number = new BigDecimal(tv_number.getText().toString());
            BigDecimal price = new BigDecimal(tv_price.getText().toString());
            shopping.setCommodityId(details.getCommodityId());
            shopping.setCommodityName(details.getCommodityName());
            shopping.setCommodityPrice("" + price);
            if (TextUtils.isEmpty(details.getDownpayment())) {
                shopping.setDownpayment("0.00");
            } else {
                shopping.setDownpayment(details.getDownpayment());
            }
            switch (view.getId()) {
                //数量限定为0和1
                case R.id.iv_add:
                    number = number.add(new BigDecimal(1));
                    show(Integer.parseInt(number.toString()));
                    tv_number.setText("" + number);
                    shopping.setCommodityNumber("" + number);
                    clickListener.itemClick(shopping, position);
                    break;

                case R.id.iv_cut:
                    number = number.subtract(new BigDecimal(1));
                    hidden(Integer.parseInt(number.toString()));
                    tv_number.setText(number.toString());
                    shopping.setCommodityNumber("" + number);
                    clickListener.itemClick(shopping, position);
                    break;
            }
        }
    }

    public void setList(List DetailsList) {
        this.DetailsList = DetailsList;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView iv_goods_pic;
        public TextView tv_commodity_name;
        public TextView tv_goods_price;
        public ImageView btn_cut;
        public TextView tv_goods_number;
        public ImageView btn_add;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_goods_pic = (ImageView) rootView.findViewById(R.id.iv_goods_pic);
            this.tv_commodity_name = (TextView) rootView.findViewById(R.id.tv_commodity_name);
            this.tv_goods_price = (TextView) rootView.findViewById(R.id.tv_goods_price);
            this.btn_add = (ImageView) rootView.findViewById(R.id.iv_add);
            this.btn_cut = (ImageView) rootView.findViewById(R.id.iv_cut);
            this.tv_goods_number = (TextView) rootView.findViewById(R.id.tv_goods_number);
        }
    }
}
