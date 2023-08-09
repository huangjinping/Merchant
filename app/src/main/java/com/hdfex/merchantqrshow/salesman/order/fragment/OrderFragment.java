package com.hdfex.merchantqrshow.salesman.order.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.mvp.contract.OrderListContract;
import com.hdfex.merchantqrshow.mvp.presenter.OrderListPersenter;
import com.hdfex.merchantqrshow.utils.TabEntity;
import com.hdfex.merchantqrshow.view.ComTabLayout;
import com.hdfex.merchantqrshow.view.SpannerView;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 //订单状态：
 //订单状态：
 //0 未提交
 //---进行中
 //1 审批中
 //2 已通过
 //3 还款中
 //---已完成
 //4 提前结清
 //5 已完成
 //6 已取消
 //7 已拒绝
 //--逾期8
 * Created by harrishuang on 16/9/27.
 */

public class OrderFragment extends BaseFragment implements View.OnClickListener, OrderListContract.View {


    private OrderPagerAdapter mAdapter;


//订单状态：
//订单状态：
//0 未提交
//---进行中
//1 审批中
//2 已通过
//3 还款中
//---已完成
//4 提前结清
//5 已完成
//6 已取消
//7 已拒绝
//--逾期8


    private final String[] mTitles = {
            "1", "2", "3", "4"
    };

    private ViewPager vip_order;
    private SlidingTabLayout tab_layout_header;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private TextView txt_left_name;
    private CommonTabLayout layout_tab_com;
    private ComTabLayout layout_tab_order;
    private SpannerView sp_view;
    private ArrayList<TabEntity> tabDataList;
    private TextView txt_tab_name0;
    private TextView txt_tab_name1;
    private TextView txt_tab_name2;
    private ImageView img_tab_icon2;
    private TextView txt_tab_name3;
    private ImageView img_tab_icon3;
    private LinearLayout layout_mylayout;
    private LinearLayout layout_tab0;
    private LinearLayout layout_tab1;
    private LinearLayout layout_tab2;
    private LinearLayout layout_tab3;
    private String currentType;
    private List<OrderListFragment> fragmentList;
    private OrderListContract.Persenter persenter;

    private void resetView() {
        txt_tab_name0.setSelected(false);
        txt_tab_name1.setSelected(false);
        txt_tab_name2.setSelected(false);
        txt_tab_name3.setSelected(false);
        img_tab_icon2.setImageResource(R.mipmap.ic_arrow_down);
        img_tab_icon3.setImageResource(R.mipmap.ic_arrow_down);
        layout_tab2.setTag(false);
        layout_tab3.setTag(false);
        sp_view.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        initView(view);
        persenter = new OrderListPersenter();
        persenter.attachView(this);
        fragmentList = new ArrayList<>();
        fragmentList.add(OrderListFragment.getInstance("0"));
        fragmentList.add(OrderListFragment.getInstance("8"));
        fragmentList.add(OrderListFragment.getInstance("1"));
        fragmentList.add(OrderListFragment.getInstance("6"));
        mAdapter = new OrderPagerAdapter(getChildFragmentManager());
        vip_order.setAdapter(mAdapter);
        tab_layout_header.setViewPager(vip_order, mTitles);
//        tab_layout_header.showDot(0);
        Bundle arguments = getArguments();
        if (arguments != null) {
            int index = arguments.getInt("index", 0);
            setCurrent(index);
            img_back.setVisibility(View.VISIBLE);
            tb_tv_titile.setTextColor(getResources().getColor(R.color.white));
            layout_toolbar.setBackgroundResource(R.color.blue_light);
        } else {
            img_back.setVisibility(View.GONE);
        }
        initTab();
        vip_order.setCurrentItem(0);
        vip_order.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                resetView();
                String status = fragmentList.get(position).getStatus();
                switch (position) {
                    case 0:
                        txt_tab_name0.setSelected(true);
                        break;
                    case 1:
                        txt_tab_name1.setSelected(true);
                        break;
                    case 2:
                        txt_tab_name2.setTag(status);
                        txt_tab_name2.setSelected(true);
                        if (status.equals("1")){
                            txt_tab_name3.setText("审核中");
                        }

                        break;
                    case 3:
                        txt_tab_name3.setTag(status);
                        txt_tab_name3.setSelected(true);
                        if (status.equals("6")){
                            txt_tab_name3.setText("已取消");
                        }

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }


    /**
     * 显示的数据问题
     */
    private void initTab() {

        resetView();
        txt_tab_name0.setSelected(true);
        txt_tab_name2.setTag("1");
        txt_tab_name3.setTag("6");
        currentFragment(0, "0");
        layout_tab0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetView();
                txt_tab_name0.setSelected(true);
                currentFragment(0, "0");
                resetAnimation(img_tab_icon2);
                resetAnimation(img_tab_icon3);
            }
        });
        layout_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetView();
                txt_tab_name1.setSelected(true);
                currentFragment(1, "8");
                resetAnimation(img_tab_icon3);

                resetAnimation(img_tab_icon2);
            }
        });
        layout_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAnimation(img_tab_icon3);

                boolean open = (boolean) layout_tab2.getTag();
                resetView();
                txt_tab_name2.setSelected(true);
                currentFragment(2, txt_tab_name2.getTag() + "");

                persenter.setCurrentTab(2, !open, sp_view);
                if (open) {
                    layout_tab2.setTag(false);
                    img_tab_icon2.setImageResource(R.mipmap.ic_arrow_down);
                    resetAnimation(img_tab_icon2);
                } else {
                    layout_tab2.setTag(true);
//                    img_tab_icon2.setImageResource(R.mipmap.ic_arrow_up);
                    startAnimationBy(img_tab_icon2);
                }
            }
        });
        /**
         *设置标记
         */
        layout_tab3.setTag(false);
        layout_tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAnimation(img_tab_icon2);
                boolean open = (boolean) layout_tab3.getTag();
                resetView();
                txt_tab_name3.setSelected(true);
                currentFragment(3, txt_tab_name3.getTag() + "");
                persenter.setCurrentTab(3, !open, sp_view);
                if (open) {
                    layout_tab3.setTag(false);
                    img_tab_icon3.setImageResource(R.mipmap.ic_arrow_down);
                    resetAnimation(img_tab_icon3);
                } else {
                    layout_tab3.setTag(true);
                    startAnimationBy(img_tab_icon3);
                }
            }
        });
    }


    /**
     * 开始动画View
     */
    private void startAnimationBy(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 180).setDuration(500);
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight() / 2);
        animator.start();
    }

    /**
     * 初始化View
     *
     * @param view
     */
    private void resetAnimation(View view) {
        float rotation = view.getRotation();
        if (0 < rotation && rotation <= 180) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", -180, 0).setDuration(500);
            view.setPivotX(view.getWidth() / 2);
            view.setPivotY(view.getHeight() / 2);
            animator.start();
        }
    }


    /**
     * 设置当前的位置
     */
    private void currentFragment(int tab, String type) {
        if (type.equals(currentType)) {
            return;
        }
        currentType = type;
        /**
         * 设置
         */
//        vip_order.setCurrentItem(tab);
//        if (tab > 1) {
//            OrderListFragment orderListFragment = fragmentList.get(tab);
//            orderListFragment.setStatus(type);
//        }
        /**
         * vip_order  订单维护信息
         */
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_content_view, OrderListFragment.getInstance(type));
        fragmentTransaction.commit();
    }


    private void initView(View view) {
        vip_order = (ViewPager) view.findViewById(R.id.vip_order);
        tab_layout_header = (SlidingTabLayout) view.findViewById(R.id.tab_layout_header);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) view.findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) view.findViewById(R.id.iv_setting);
        tv_home = (TextView) view.findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) view.findViewById(R.id.layout_toolbar);
        tb_tv_titile.setText("订单");
        tb_tv_titile.setTextColor(getResources().getColor(R.color.black));
        layout_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        txt_left_name = (TextView) view.findViewById(R.id.txt_left_name);

        layout_tab_com = (CommonTabLayout) view.findViewById(R.id.layout_tab_com);
        layout_tab_com.setOnClickListener(this);
        layout_tab_order = (ComTabLayout) view.findViewById(R.id.layout_tab_order);
        layout_tab_order.setOnClickListener(this);
        sp_view = (SpannerView) view.findViewById(R.id.sp_view);
        sp_view.setOnClickListener(this);
        txt_tab_name0 = (TextView) view.findViewById(R.id.txt_tab_name0);
        txt_tab_name1 = (TextView) view.findViewById(R.id.txt_tab_name1);
        txt_tab_name2 = (TextView) view.findViewById(R.id.txt_tab_name2);
        img_tab_icon2 = (ImageView) view.findViewById(R.id.img_tab_icon2);
        txt_tab_name3 = (TextView) view.findViewById(R.id.txt_tab_name3);
        img_tab_icon3 = (ImageView) view.findViewById(R.id.img_tab_icon3);
        layout_mylayout = (LinearLayout) view.findViewById(R.id.layout_mylayout);
        layout_tab0 = (LinearLayout) view.findViewById(R.id.layout_tab0);
        layout_tab1 = (LinearLayout) view.findViewById(R.id.layout_tab1);
        layout_tab2 = (LinearLayout) view.findViewById(R.id.layout_tab2);
        layout_tab3 = (LinearLayout) view.findViewById(R.id.layout_tab3);
    }


    public void setCurrent(int index) {
        if (tab_layout_header != null && vip_order != null) {
            tab_layout_header.setCurrentTab(index);
            vip_order.setCurrentItem(index);
        }
    }

    @Override
    public void returnoNTabSelect(int currentTab, int position, String name, String type) {
        if (currentTab == 2) {
            resetView();
            txt_tab_name2.setText(name);
            txt_tab_name2.setTag(type);

            txt_tab_name2.setSelected(true);
            currentFragment(2, type);

        } else if (currentTab == 3) {
            resetView();
            txt_tab_name3.setText(name);
            txt_tab_name3.setTag(type);
            txt_tab_name3.setSelected(true);
            currentFragment(3, type);

        }
        resetAnimation(img_tab_icon2);
        resetAnimation(img_tab_icon3);
    }


    /**
     *
     */
    private class OrderPagerAdapter extends FragmentPagerAdapter {
        public OrderPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        persenter.detachView();
    }
}
