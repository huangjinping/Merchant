package com.hdfex.merchantqrshow.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.plan.RepayDetail;

import java.util.List;

/**
 * author Created by harrishuang on 2017/5/26.
 * email : huangjinping@hdfex.com
 */

public class ListDialog extends Dialog {

    private TextView txt_title;
    private ListView lisv_list;
    private Button btn_cancel;
    private List<RepayDetail> dataList;
    private Context context;
    private ListAdapter adapter;
    private String title;
    private View.OnClickListener onClickListener;

    public ListDialog(@NonNull Context context, List<RepayDetail> dataList, String title, View.OnClickListener onClickListener) {
        super(context, R.style.widget_dialogbuilder_dialog_untran);
        this.context = context;
        this.dataList = dataList;
        this.title = title;
        this.onClickListener = onClickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list);
        initView();

        if (!TextUtils.isEmpty(title)) {
            txt_title.setText(title);
        }
        adapter = new ListAdapter();
        lisv_list.setAdapter(adapter);
        btn_cancel.setOnClickListener(onClickListener);
    }

    private void initView() {
        txt_title = (TextView) findViewById(R.id.txt_title);
        lisv_list = (ListView) findViewById(R.id.lisv_list);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
    }


    private class ListAdapter extends BaseAdapter {
        ViewHolder viewHolder;

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
                convertView = LayoutInflater.from(context).inflate(R.layout.item_listdialog, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            RepayDetail repayDetail = dataList.get(position);
            viewHolder.txt_detailPeriod_1.setText("第" + repayDetail.getDetailPeriod() + "期本金");
            if (!TextUtils.isEmpty(repayDetail.getDetailInStmAmt())) {
                viewHolder.txt_detailInStmAmt.setText(repayDetail.getDetailInStmAmt());
            }
            viewHolder.txt_detailPeriod_2.setText("第" + repayDetail.getDetailPeriod() + "期滞纳金");
            if (!TextUtils.isEmpty(repayDetail.getLateFee())) {
                viewHolder.txt_lateFee.setText(repayDetail.getLateFee());
            }
            return convertView;
        }


        class ViewHolder {
            public View rootView;
            public TextView txt_detailPeriod_1;
            public TextView txt_detailInStmAmt;
            public TextView txt_detailPeriod_2;
            public TextView txt_lateFee;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.txt_detailPeriod_1 = (TextView) rootView.findViewById(R.id.txt_detailPeriod_1);
                this.txt_detailInStmAmt = (TextView) rootView.findViewById(R.id.txt_detailInStmAmt);
                this.txt_detailPeriod_2 = (TextView) rootView.findViewById(R.id.txt_detailPeriod_2);
                this.txt_lateFee = (TextView) rootView.findViewById(R.id.txt_lateFee);
            }

        }
    }

}
