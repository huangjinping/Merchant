package com.hdfex.merchantqrshow.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2019/9/5.
 * email : huangjinping1000@163.com
 */
public class OrderTypePopWindow extends PopupWindow {

    private Context context;
    private View view;
    private ListView listView;
    private List<String> list = new ArrayList<>();
    private onOrderTypeCallBack onOrderTypeCallBack;

    public void setOnOrderTypeCallBack(OrderTypePopWindow.onOrderTypeCallBack onOrderTypeCallBack) {
        this.onOrderTypeCallBack = onOrderTypeCallBack;
    }

    public OrderTypePopWindow(Context context, List<String> list) {
        this(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.list.clear();
        this.list.addAll(list);
        initView();
    }

    public OrderTypePopWindow(Context context, int with, int height) {
        this.context = context;
        setWidth(with);
        setHeight(height);
        //设置可以获得焦点
        setFocusable(true);
        //设置弹窗内可点击
        setTouchable(true);
        //设置弹窗外可点击
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());

//        setAnimationStyle(R.style.Animation_Cart);

    }

    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_order_type_popwindow, null);
        setContentView(view);
        initData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = list.get(position);
                dismiss();
                if (onOrderTypeCallBack != null) {
                    onOrderTypeCallBack.callBack(title, position);
                }
            }
        });
    }


    public interface onOrderTypeCallBack {
        void callBack(String title, int position);
    }


    private void initData() {
        listView = (ListView) view.findViewById(R.id.title_list);
//        list = new ArrayList<String>();
//        list.add("全部");
//        list.add("扫码未提交");
//        list.add("待开户");
//        list.add("待签约");
//        list.add("已提交");
//        list.add("已打回");
//        list.add("已取消");
//        list.add("已拒绝");
//        list.add("审核中");
//        list.add("审核通过");
//        list.add("还款中");
//        list.add("已结清");
        //设置列表的适配器
        listView.setAdapter(new BaseAdapter() {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = null;

                if (convertView == null) {
                    textView = new TextView(context);
                    textView.setTextColor(Color.rgb(255, 255, 255));
                    textView.setTextSize(14);
                    //设置文本居中
                    textView.setGravity(Gravity.CENTER);
                    //设置文本域的范围
                    textView.setPadding(0, 18, 0, 18);
                    //设置文本在一行内显示（不换行）
                    textView.setSingleLine(true);
                } else {
                    textView = (TextView) convertView;
                }
                //设置文本文字
                textView.setText(list.get(position));
                //设置文字与图标的间隔
//                textView.setCompoundDrawablePadding(0);
//                //设置在文字的左边放一个图标
//                textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap., null, null, null);

                return textView;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }


}
