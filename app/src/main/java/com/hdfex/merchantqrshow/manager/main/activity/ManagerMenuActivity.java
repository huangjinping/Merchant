package com.hdfex.merchantqrshow.manager.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;

/**
 * author Created by harrishuang on 2017/6/1.
 * email : huangjinping@hdfex.com
 */

public class ManagerMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managermenu);
    }


    public static void startAction(Context context) {
        Intent intent = new Intent(context, ManagerMenuActivity.class);
        context.startActivity(intent);
    }
}
