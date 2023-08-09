package com.hdfex.merchantqrshow.salesman.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.net.IntentFlag;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * 下载分夫君
 * Created by harrishuang on 16/7/5.
 */
public class LoadAppForCActivity extends BaseActivity  implements View.OnClickListener{
    private TextView tb_tv_titile;
    private LinearLayout layout_toolbar;
    private ImageView img_load;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadapp);
        initView();
        tb_tv_titile.setText("分付君下载");
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

    /**
     * OEPN APP
     */
    private  void openPic(){

    }
    @Override
    public void finish() {
        super.finish();
        Intent intent = getIntent();
        String extra = intent.getStringExtra(IntentFlag.INTENT_NAME);
        if (IntentFlag.MAIN.equals(extra)){
            overridePendingTransition(R.anim.activity_up_in_slow, R.anim.activity_up_out_slow);
            return;
        }
        overridePendingTransition(R.anim.activity_down_in_slow, R.anim.activity_down_out_slow);

    }
}
