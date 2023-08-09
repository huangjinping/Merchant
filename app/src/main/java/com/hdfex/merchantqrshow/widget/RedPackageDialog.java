package com.hdfex.merchantqrshow.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;

/**
 * author Created by harrishuang on 2017/6/28.
 * email : huangjinping@hdfex.com
 */

public class RedPackageDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private ImageView img_open;
    private ImageView img_opening;
    private User user;
    private RedPackage redPackage;
    private TextView txt_remark;
    private TextView txt_desc;
    private TextView txt_redpackage_list;
    private ImageView img_cancel;


    public RedPackageDialog(@NonNull Context context) {
        super(context, R.style.widget_dialogbuilder_dialog_untran);
        this.context = context;
    }

    public void setRedPackage(RedPackage redPackage) {
        this.redPackage = redPackage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redpackage);
        img_open = (ImageView) findViewById(R.id.img_open);
        img_open.setOnClickListener(this);
        img_opening = (ImageView) findViewById(R.id.img_opening);
        txt_remark = (TextView) findViewById(R.id.txt_remark);
        txt_desc = (TextView) findViewById(R.id.txt_desc);
        img_cancel = (ImageView) findViewById(R.id.img_cancel);
        img_cancel.setOnClickListener(this);
        txt_redpackage_list = (TextView) findViewById(R.id.txt_redpackage_list);
        txt_redpackage_list.setOnClickListener(this);
        user = UserManager.getUser(context);
        if (!TextUtils.isEmpty(redPackage.getRemark())) {
            txt_remark.setVisibility(View.VISIBLE);
            txt_remark.setText(redPackage.getRemark());
        } else {
            txt_remark.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_open:
                img_open.setVisibility(View.GONE);
                img_opening.setVisibility(View.VISIBLE);
                AnimationDrawable animationDrawable = (AnimationDrawable) img_opening.getBackground();
                animationDrawable.start();
                openRedPackage();
                break;
            case R.id.txt_redpackage_list:
//                TaobaoAuthActivity.startAction(context);
                dismiss();
                break;
            case R.id.img_cancel:
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        try {
            txt_desc.setVisibility(View.GONE);
            img_open.setVisibility(View.VISIBLE);
            img_opening.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载数据问题
     */
    private void openRedPackage() {
        OkHttpUtils.post().addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("redPackageId", redPackage.getRedPackageId())
                .url(NetConst.OPEN_REDPACKAGE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        txt_desc.setText("打开失败");
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            Response res = GsonUtil.changeGsonToBean(response, Response.class);
                            if (0 == res.getCode()) {
                                if (!TextUtils.isEmpty(redPackage.getRedPacketAmt())) {
                                    txt_desc.setText(redPackage.getRedPacketAmt());
                                }
                            } else {
                                if (!TextUtils.isEmpty(res.getMessage())) {
                                    txt_desc.setText(res.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            txt_desc.setText("打开失败");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        txt_desc.setVisibility(View.VISIBLE);
                        img_opening.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        EventRxBus.getInstance().post(IntentFlag.RED_LOAD, IntentFlag.RED_LOAD);

    }
}
