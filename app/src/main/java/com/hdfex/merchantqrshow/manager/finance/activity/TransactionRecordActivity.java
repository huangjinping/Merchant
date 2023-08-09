package com.hdfex.merchantqrshow.manager.finance.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.manager.finance.adapter.RecordAdapter;
import com.hdfex.merchantqrshow.view.xlistView.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/5/31.
 * email : huangjinping@hdfex.com
 */

public class TransactionRecordActivity extends BaseActivity implements View.OnClickListener {

    private RecordAdapter adapter;
    private List<String> dataList;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private XListView rec_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactionrecord);
        initView();
        dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("i");
        }
        adapter = new RecordAdapter(dataList, this);
        rec_record.setAdapter(adapter);
        rec_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransactionDetailsActivity.startAction(TransactionRecordActivity.this);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                TransactionRecordActivity.this.finish();
                break;
        }
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        rec_record = (XListView) findViewById(R.id.rec_record);
        tb_tv_titile.setText(R.string.transaction_record);
        img_back.setOnClickListener(this);

    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, TransactionRecordActivity.class);
        context.startActivity(intent);

    }


}
