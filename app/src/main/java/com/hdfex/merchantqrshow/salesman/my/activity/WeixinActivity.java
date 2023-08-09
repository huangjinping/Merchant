package com.hdfex.merchantqrshow.salesman.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;

/**
 * 下载分夫君
 * Created by harrishuang on 16/7/5.
 */
public class WeixinActivity extends BaseActivity  implements View.OnClickListener{
    private TextView tb_tv_titile;
    private LinearLayout layout_toolbar;
    private ImageView img_load;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin);
        initView();
        tb_tv_titile.setText("微信公众号");
    }

    private void initView() {
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        img_load = (ImageView) findViewById(R.id.img_load);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setImageResource(R.mipmap.ic_yulan);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                this.finish();
                break;
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_down_in_slow, R.anim.activity_down_out_slow);
    }
}
