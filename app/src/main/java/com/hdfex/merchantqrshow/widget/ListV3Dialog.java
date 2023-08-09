package com.hdfex.merchantqrshow.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.order.RepayPlan;

import java.math.BigDecimal;
import java.util.List;

/**
 * author Created by harrishuang on 2017/5/26.
 * email : huangjinping@hdfex.com
 */

public class ListV3Dialog extends Dialog {

    private TextView txt_title;
    private RecyclerView rec_key_value;
    private Button btn_cancel;
    private Context context;
    private String title;
    private View.OnClickListener onClickListener;
    private List<RepayPlan> dataList;
    private ProductKeyAdapter adapter;


    public ListV3Dialog(@NonNull Context context, List<RepayPlan> dataList, String title, View.OnClickListener onClickListener) {
        super(context, R.style.widget_dialogbuilder_dialog_untran);
        this.context = context;
        this.dataList = dataList;
        this.title = title;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list_v2);
        initView();
        if (!TextUtils.isEmpty(title)) {
            txt_title.setText(title);
        }
        btn_cancel.setOnClickListener(onClickListener);
        adapter = new ProductKeyAdapter();
        rec_key_value.setLayoutManager(new LinearLayoutManager(context));
        rec_key_value.setAdapter(adapter);
    }

    private void initView() {
        txt_title = (TextView) findViewById(R.id.txt_title);
        rec_key_value = (RecyclerView) findViewById(R.id.rec_key_value);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
    }


    private class ProductKeyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listdialog_v3, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ViewHolder viewHolder = (ViewHolder) holder;
            RepayPlan repayPlan = dataList.get(position);
            viewHolder.txt_psPerdNo.setText("");
            viewHolder.txt_psInstmAmt.setText("");
            viewHolder.txt_psDueDt.setText("");

            BigDecimal mPs = new BigDecimal("0");

            if (repayPlan.getPsInstmAmt() != null) {
                mPs = mPs.add(repayPlan.getPsInstmAmt());
            }
            if (repayPlan.getPsIntAmt() != null) {
                mPs = mPs.add(repayPlan.getPsIntAmt());
            }
            if (repayPlan.getPsFeeAmt() != null) {
                mPs = mPs.add(repayPlan.getPsFeeAmt());
            }


            if (!TextUtils.isEmpty(repayPlan.getPsPerdNo())) {
                viewHolder.txt_psPerdNo.setText("第" + repayPlan.getPsPerdNo() + "期");
            }
            viewHolder.txt_psInstmAmt.setText(mPs.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "元");

            if (!TextUtils.isEmpty(repayPlan.getPsDueDt())) {
                viewHolder.txt_psDueDt.setText(repayPlan.getPsDueDt());
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View rootView;
            public TextView txt_psPerdNo;
            public TextView txt_psInstmAmt;
            public TextView txt_psDueDt;

            public ViewHolder(View rootView) {
                super(rootView);
                this.rootView = rootView;
                this.txt_psPerdNo = (TextView) rootView.findViewById(R.id.txt_psPerdNo);
                this.txt_psInstmAmt = (TextView) rootView.findViewById(R.id.txt_psInstmAmt);
                this.txt_psDueDt = (TextView) rootView.findViewById(R.id.txt_psDueDt);
            }

        }
    }

}
