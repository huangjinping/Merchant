package com.hdfex.merchantqrshow.salesman.my.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.add.activity.AddHouseActivity;
import com.hdfex.merchantqrshow.salesman.add.fragment.ResourcesAllFragment;
import com.hdfex.merchantqrshow.salesman.add.fragment.ResourcesFragment;
import com.hdfex.merchantqrshow.salesman.add.fragment.ResourcesPayAllFragment;
import com.hdfex.merchantqrshow.salesman.add.fragment.ResourcesPayFragment;
import com.hdfex.merchantqrshow.salesman.my.adapter.SlidingAdapter;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.ActionSheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by harrishuang on 2017/2/9.
 * 资源目录
 */

public class ResourcesActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private SlidingTabLayout tab_layout_header;
    private ViewPager vip_resoures;
    private final String[] mTitles = {
            "需清退", "已出租", "闲置",
    };
    private List<BaseFragment> fragmentList;
    private SlidingAdapter adapter;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private User user;
    private RadioButton rad_order_first;
    private RadioButton rad_order_secend;
    private RadioGroup rag_segment;
    private LinearLayout layout_fragment_content;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        setTheme(R.style.ActionSheetStyle);
        mFragmentManager = getSupportFragmentManager();

        user = UserManager.getUser(this);
        fragmentList = new ArrayList<>();
        initView();
        tb_tv_titile.setText("我的房源");
//        showZufang();

        setCurrentFragment(R.id.rad_order_first);

        String houseFlag = user.getAddHouseFlag();
        if (IntentFlag.ADD_HOUSE_FLAG_NO.equals(houseFlag)) {
            iv_setting.setVisibility(View.GONE);
            return;
        }
        updatePromiss();
    }

    private String[] sourceTypeArr;

    private void updatePromiss() {
        if (UserManager.isLogin(this)) {
            User user = UserManager.getUser(this);
            String sourceType = user.getSourceType();
            if (TextUtils.isEmpty(sourceType)) {
                return;
            }
            String[] arr = sourceType.split("[,]+");
            sourceTypeArr = arr;
            if (arr != null) {
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("2")) {
                        rad_order_first.setVisibility(View.VISIBLE);
                    } else if (arr[i].equals("10")) {
                        rad_order_secend.setVisibility(View.VISIBLE);
                    }
                }
            }

            if (sourceTypeArr != null) {
                int len = 0;
                for (int i = 0; i < rag_segment.getChildCount(); i++) {
                    View child = rag_segment.getChildAt(i);
                    if (View.VISIBLE == child.getVisibility()) {
                        len++;
                    }
                }
                if (len > 1) {
                    tb_tv_titile.setVisibility(View.GONE);
                    rag_segment.setVisibility(View.VISIBLE);
                    if (rad_order_first.getVisibility() == View.VISIBLE) {
                        setCurrentFragment(R.id.rad_order_first);
                    } else if (rad_order_secend.getVisibility() == View.VISIBLE) {
                        setCurrentFragment(R.id.rad_order_secend);
                    }
                } else {
                    tb_tv_titile.setVisibility(View.VISIBLE);
                    rag_segment.setVisibility(View.GONE);
                    if (sourceTypeArr[0].equals("10")) {
                        tb_tv_titile.setText("月付租房");
                        setCurrentFragment(R.id.rad_order_secend);
                    } else if (sourceTypeArr[0].equals("2")) {
                        tb_tv_titile.setText("分期租房");
                        setCurrentFragment(R.id.rad_order_first);
                    }
                }
            }
        }
    }

    /**
     * 分期租房
     */
    private void showZufang() {
        fragmentList.add(ResourcesFragment.getInstance("0"));
        fragmentList.add(ResourcesFragment.getInstance("2"));
        fragmentList.add(ResourcesFragment.getInstance("1"));
        adapter = new SlidingAdapter(getSupportFragmentManager(), fragmentList, mTitles);
        vip_resoures.setAdapter(adapter);
        tab_layout_header.setViewPager(vip_resoures, mTitles);
    }

    /**
     * 分期租房
     */
    private void showPayZufang() {
        fragmentList.add(ResourcesPayFragment.getInstance("0"));
        fragmentList.add(ResourcesPayFragment.getInstance("2"));
        fragmentList.add(ResourcesPayFragment.getInstance("1"));
        adapter = new SlidingAdapter(getSupportFragmentManager(), fragmentList, mTitles);
        vip_resoures.setAdapter(adapter);
        tab_layout_header.setViewPager(vip_resoures, mTitles);
    }


    private void initView() {
        tab_layout_header = (SlidingTabLayout) findViewById(R.id.tab_layout_header);
        vip_resoures = (ViewPager) findViewById(R.id.vip_resoures);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setOnClickListener(this);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_setting.setVisibility(View.VISIBLE);
        iv_setting.setImageResource(R.mipmap.ic_add);
        iv_setting.setOnClickListener(this);
        rad_order_first = (RadioButton) findViewById(R.id.rad_order_first);
        rad_order_secend = (RadioButton) findViewById(R.id.rad_order_secend);
        rag_segment = (RadioGroup) findViewById(R.id.rag_segment);
        rag_segment.setOnCheckedChangeListener(this);
        layout_fragment_content = (LinearLayout) findViewById(R.id.layout_fragment_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.iv_setting:
                setCurrentHouseType();
                break;
        }
    }

    /**
     * 设置当前试图
     *
     * @param checkedId
     */
    private void setCurrentFragment(int checkedId) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.rad_order_first:
                fragmentTransaction.replace(R.id.layout_fragment_content, ResourcesAllFragment.getInstance());


                String houseFlag = user.getAddHouseFlag();
                if (IntentFlag.ADD_HOUSE_FLAG_NO.equals(houseFlag)) {
                    iv_setting.setVisibility(View.GONE);
                } else {
                    iv_setting.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rad_order_secend:
                fragmentTransaction.replace(R.id.layout_fragment_content, ResourcesPayAllFragment.getInstance());
                iv_setting.setVisibility(View.GONE);

                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 设置当前房屋类型
     */
    private void setCurrentHouseType() {
        user = UserManager.getUser(this);
        Map<String, String> houseTypeMap = user.getHouseTypeMap();
        if (houseTypeMap != null) {
            if (houseTypeMap.containsKey(IntentFlag.HOUSE_TYPE_CONCENTRATE) && houseTypeMap.containsKey(IntentFlag.HOUSE_TYPE_DISPERSE)) {
                showSelectHouseType();
            } else if (houseTypeMap.containsKey(IntentFlag.HOUSE_TYPE_CONCENTRATE)) {
                AddHouseActivity.start(ResourcesActivity.this, IntentFlag.HOUSE_TYPE_CONCENTRATE);
            } else if (houseTypeMap.containsKey(IntentFlag.HOUSE_TYPE_DISPERSE)) {
                AddHouseActivity.start(ResourcesActivity.this, IntentFlag.HOUSE_TYPE_DISPERSE);
            } else {
                AddHouseActivity.start(ResourcesActivity.this, IntentFlag.HOUSE_TYPE_DISPERSE);
            }
        }
    }

    /**
     * 选择房屋类型
     */
    private void showSelectHouseType() {
        final String[] dataArr = {"集中式", "分散式"};
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                if (index == 0) {
                    AddHouseActivity.start(ResourcesActivity.this, IntentFlag.HOUSE_TYPE_CONCENTRATE);
                } else if (index == 1) {
                    AddHouseActivity.start(ResourcesActivity.this, IntentFlag.HOUSE_TYPE_DISPERSE);
                }
            }
        }).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        setCurrentFragment(checkedId);
    }
}
