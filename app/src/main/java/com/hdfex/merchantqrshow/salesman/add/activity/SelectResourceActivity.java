package com.hdfex.merchantqrshow.salesman.add.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.add.fragment.ResourcesFragment;
import com.hdfex.merchantqrshow.salesman.add.fragment.ResourcesSearchFragment;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.ActionSheet;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by harrishuang on 2017/2/9.
 * 选择房源界面
 */

public class SelectResourceActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tb_tv_titile;
    public String status = "2";
    private RelativeLayout layout_add;
    private User user;
    private TextView tv_home;
    /**
     * 编辑
     */
    private final String EDIT = "1";
    /**
     * 完成
     */
    private final String COMPLATE = "2";
    private ResourcesFragment fragment;
    private ImageView iv_setting;
    private ImageView iv_eite;
    private FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectresource);
        setTheme(R.style.ActionSheetStyle);
        supportFragmentManager = getSupportFragmentManager();
        initView();
        user = UserManager.getUser(this);
        fragment = ResourcesFragment.getInstance("1", true,true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_content, fragment).commit();
        Observable<Object> register = EventRxBus.getInstance().register(IntentFlag.INTENT_SELECT_HOUSE);
        register.subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {
                SelectResourceActivity.this.finish();
            }
        });
        updateView();


        Observable<Object> registerOpenSearch = EventRxBus.getInstance().register(IntentFlag.INTENT_OPEN_SEARCH);
        registerOpenSearch.subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                ResourcesSearchFragment fragment = ResourcesSearchFragment.getInstance();
                FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                transaction.setCustomAnimations(
                        R.anim.fragment_up_enter,
                        R.anim.fragment_down_outer,
                        R.anim.fragment_up_enter,
                        R.anim.fragment_down_outer);
                transaction.replace(R.id.layout_fragment_content, fragment);
                transaction.addToBackStack("ResourcesSearchFragment");
                transaction.commit();
            }
        });
        initAddAuth();
    }

    /**
     * 设置当前房屋类型
     */
    private void initAddAuth() {
        user = UserManager.getUser(this);
        if (IntentFlag.ADD_HOUSE_FLAG_NO.equals(user.getAddHouseFlag())) {
            iv_setting.setVisibility(View.GONE);
            return;
        }
        iv_setting.setVisibility(View.VISIBLE);
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setText("选择房间");
        layout_add = (RelativeLayout) findViewById(R.id.layout_add);
        layout_add.setOnClickListener(this);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);
        iv_setting.setImageResource(R.mipmap.ic_add);
        iv_setting.setVisibility(View.VISIBLE);
        iv_eite = (ImageView) findViewById(R.id.iv_eite);
        iv_eite.setOnClickListener(this);
        iv_eite.setVisibility(View.VISIBLE);
        iv_eite.setTag(EDIT);
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
            case R.id.iv_eite:
                updateView();
                break;

        }
    }

    /**
     * 更新数据问题
     */
    private void updateView() {

        String tag = (String) iv_eite.getTag();
        if (EDIT.equals(tag)) {
            iv_eite.setTag(COMPLATE);
//            tv_home.setText("编辑");
            iv_eite.setImageResource(R.drawable.ic_hous_edit1);
            tb_tv_titile.setText("选择房间");
            iv_setting.setVisibility(View.VISIBLE);
            fragment.setCanSelect(true);
        } else {
            iv_eite.setTag(EDIT);
//            tv_home.setText("完成");
            tb_tv_titile.setText("编辑房间");
            iv_eite.setImageResource(R.drawable.ic_hous_edit0);

            iv_setting.setVisibility(View.GONE);
            fragment.setCanSelect(false);

        }

        if (IntentFlag.ADD_HOUSE_FLAG_NO.equals(user.getAddHouseFlag())) {
            iv_setting.setVisibility(View.GONE);
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventRxBus.getInstance().unregister(IntentFlag.INTENT_SELECT_HOUSE);
        EventRxBus.getInstance().unregister(IntentFlag.INTENT_OPEN_SEARCH);

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
                return;
            } else if (houseTypeMap.containsKey(IntentFlag.HOUSE_TYPE_CONCENTRATE)) {
                AddHouseActivity.start(SelectResourceActivity.this, IntentFlag.HOUSE_TYPE_CONCENTRATE);
                return;
            } else if (houseTypeMap.containsKey(IntentFlag.HOUSE_TYPE_DISPERSE)) {
                AddHouseActivity.start(SelectResourceActivity.this, IntentFlag.HOUSE_TYPE_DISPERSE);
                return;
            }
        }


        AddHouseActivity.start(SelectResourceActivity.this, IntentFlag.HOUSE_TYPE_DISPERSE);
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
                    AddHouseActivity.start(SelectResourceActivity.this, IntentFlag.HOUSE_TYPE_CONCENTRATE);
                } else if (index == 1) {
                    AddHouseActivity.start(SelectResourceActivity.this, IntentFlag.HOUSE_TYPE_DISPERSE);
                }
            }
        }).show();
    }


}
