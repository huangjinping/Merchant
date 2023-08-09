package com.hdfex.merchantqrshow.admin.bam.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.bam.adapter.OrganizationAdapter;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.manager.team.RegionResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织架构页
 * author Created by harrishuang on 2017/11/30.
 * email : huangjinping@hdfex.com
 */

public class OrganizationActivity extends BaseActivity implements View.OnClickListener {
    private OrganizationAdapter adapter;
    private List<RegionResult> dataList;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ExpandableListView exp_organization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        initView();
        dataList = new ArrayList<>();
        adapter = new OrganizationAdapter(this, dataList);
        exp_organization.setAdapter(adapter);
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        exp_organization = (ExpandableListView) findViewById(R.id.exp_organization);
        tb_tv_titile.setText("组织架构");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
