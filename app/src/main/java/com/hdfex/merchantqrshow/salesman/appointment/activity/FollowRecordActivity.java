package com.hdfex.merchantqrshow.salesman.appointment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.appointment.DataErrorHisItem;
import com.hdfex.merchantqrshow.bean.salesman.appointment.DataErrorHisListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.appointment.adapter.FollowRecordAdapter;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/10/25.
 * email : huangjinping@hdfex.com
 */

public class FollowRecordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private RecyclerView rec_follow_record;
    private FollowRecordAdapter adapter;
    private User user;
    private String type;
    private String applyId;
    private String examineId;
    private DataErrorHisItem result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followrecord);
        user = UserManager.getUser(this);
        initView();
        result = new DataErrorHisItem();
        rec_follow_record.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FollowRecordAdapter(result);
        rec_follow_record.setAdapter(adapter);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        applyId = intent.getStringExtra("applyId");
        examineId = intent.getStringExtra("examineId");
        if (!TextUtils.isEmpty(type)) {
            loadData(type, applyId, examineId);
        }
    }

    /**
     * 这些都是数据
     *
     * @param type
     * @param applyId
     * @param examineId
     */
    public static void startAction(Context context, String type, String applyId, String examineId) {
        Intent intent = new Intent(context, FollowRecordActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("applyId", applyId);
        intent.putExtra("examineId", examineId);
        context.startActivity(intent);
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        rec_follow_record = (RecyclerView) findViewById(R.id.rec_follow_record);
        img_back.setOnClickListener(this);
        tb_tv_titile.setText("客户");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    /**
     * 开始界面
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, FollowRecordActivity.class);
        context.startActivity(intent);
    }

    /**
     * 文件数据为
     */
    private void loadData(String type, String applyId, String examineId) {
        String url = NetConst.DATA_ERROR_HIS_LIST;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("type", type)
                .addParams("applyId", applyId)
                .addParams("examineId", examineId);
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                DataErrorHisListResponse dataHisResponse = GsonUtil.changeGsonToBean(response, DataErrorHisListResponse.class);
                                if (dataHisResponse != null) {
                                    result = dataHisResponse.getResult();
                                    adapter = new FollowRecordAdapter(result);
                                    rec_follow_record.setAdapter(adapter);
                                    Log.d("okhttp", GsonUtil.createGsonString(result));
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
