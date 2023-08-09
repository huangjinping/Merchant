package com.hdfex.merchantqrshow.salesman.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.message.MessageItem;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;
import com.hdfex.merchantqrshow.salesman.my.fragment.MessageFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 *
 */

public class MessageActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ViewPager vie_message;
    private String[] titles;
    private List<BaseFragment> fragmentList;
    private SlidingAdapter adapter;
    private TabLayout tab_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_actiity);
        initView();
        titles = new String[]{"消息", "公告", "商户公告"};
        tb_tv_titile.setText(titles[0]);
        fragmentList = new ArrayList<>();
        fragmentList.add(MessageFragment.getInstance(MessageItem.MESSAGE_NEWS));
        fragmentList.add(MessageFragment.getInstance(MessageItem.MESSAGE_NOTICE));
        fragmentList.add(MessageFragment.getInstance(MessageItem.MESSAGE_ANNOUNCEMENT));

        adapter = new SlidingAdapter(getSupportFragmentManager(), fragmentList, titles);
        vie_message.setAdapter(adapter);

        tab_message.setTabsFromPagerAdapter(adapter);
        tab_message.setupWithViewPager(vie_message);
        tab_message.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        vie_message = (ViewPager) findViewById(R.id.vie_message);
        img_back.setOnClickListener(this);

        tab_message = (TabLayout) findViewById(R.id.tab_message);
        tab_message.setOnClickListener(this);
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
     * 开启意图
     *
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, MessageActivity.class);
        context.startActivity(intent);
    }


}
