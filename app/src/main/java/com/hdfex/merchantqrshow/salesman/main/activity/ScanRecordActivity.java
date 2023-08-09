package com.hdfex.merchantqrshow.salesman.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.QueryUncompelete;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.main.fragment.ScanRecordFragment;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.LinkListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 扫描记录
 * Created by harrishuang on 16/7/11.
 */
public class ScanRecordActivity extends BaseActivity implements View.OnClickListener, LinkListView.LinkListViewListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private LinearLayout layout_toolbar;
    private User user;
    private int page = 0;
    private List<QueryUncompelete> dataList;
    private String commodityName;
    private String phone;
    private boolean loadstate = false;
    private String searchData;
    private TextView txt_left_name;
    private ImageView iv_setting;
    private TextView tv_home;
    private SlidingTabLayout tab_layout_header;
    private ViewPager vip_resoures;
    private List<String> mTitlesList = new ArrayList<>();
    private List<BaseFragment> fragmentList;
    private SlidingAdapter slidingAdapter;
    private LinearLayout layout_segment;
    private LinearLayout layout_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanrecord);
        fragmentList = new ArrayList<>();
        user = UserManager.getUser(this);
        dataList = new ArrayList<>();
        initView();

        tb_tv_titile.setText("我的二维码");
        Map<String, String> map = user.getSourceTypeMap();

//        "租房", "教育", "月付",
        if (map != null) {
            if (!TextUtils.isEmpty(map.get("1"))) {
                mTitlesList.add("租房");
                fragmentList.add(ScanRecordFragment.getInstance(IntentFlag.SCAN_TYPE_ZUFANG));

            }
            if (!TextUtils.isEmpty(map.get("2"))) {
                mTitlesList.add("教育");
                fragmentList.add(ScanRecordFragment.getInstance(IntentFlag.SCAN_TYPE_OTHER));
            }

            if (!TextUtils.isEmpty(map.get("10"))) {
                mTitlesList.add("月付");
                fragmentList.add(ScanRecordFragment.getInstance(IntentFlag.SCAN_TYPE_PAY));
            }

            /**
             * 判断显示什么列表草
             */
            if (mTitlesList.size() != 1) {
                /**
                 * 租房和教育
                 */
                addBoth();

            } else if (!TextUtils.isEmpty(map.get("1"))) {
                /**
                 * 教育的单个
                 */
                addSingle(IntentFlag.SCAN_TYPE_OTHER);

            } else if (!TextUtils.isEmpty(map.get("2"))) {
                /**
                 * 显示的租房的单个
                 */
                addSingle(IntentFlag.SCAN_TYPE_ZUFANG);
            } else if (!TextUtils.isEmpty(map.get("10"))) {
                /**
                 * 显示的月付租房的单个
                 */
                addSingle(IntentFlag.SCAN_TYPE_PAY);
            }
        }


//        Intent intent = getIntent();
//        if (IntentFlag.SCAN_TYPE_OTHER.equals(intent.getStringExtra(IntentFlag.INTENT_SCAN_TYPE))){
//            vip_resoures.setCurrentItem(1,false);
//        }

        /**
         * 租房用户
         */
    }


    /**
     * 添加全部
     */
    private void addBoth() {
        String[] mTitles = mTitlesList.toArray(new String[mTitlesList.size()]);
        Log.d("okhttp", "mTitles" + mTitles.toString());
        slidingAdapter = new SlidingAdapter(getSupportFragmentManager(), fragmentList, mTitles);
        vip_resoures.setAdapter(slidingAdapter);
        tab_layout_header.setViewPager(vip_resoures, mTitles);
    }

    /**
     * 添加单个
     *
     * @param type
     */
    private void addSingle(String type) {
        tab_layout_header.setVisibility(View.GONE);
        vip_resoures.setVisibility(View.GONE);
        layout_content.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_content, ScanRecordFragment.getInstance(type)).commit();

    }


    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        txt_left_name.setOnClickListener(this);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);
        tab_layout_header = (SlidingTabLayout) findViewById(R.id.tab_layout_header);
        tab_layout_header.setOnClickListener(this);
        vip_resoures = (ViewPager) findViewById(R.id.vip_resoures);
        layout_content = (LinearLayout) findViewById(R.id.layout_content);
        layout_segment = (LinearLayout) findViewById(R.id.layout_segment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }


    @Override
    public boolean onRefreshOrMore(LinkListView linkListView, boolean isRefresh) {

        if (isRefresh) {

        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra(IntentFlag.INTENT_SCAN_TYPE))) {
            Intent intent = new Intent(this, IndexActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        finish();
    }


}
