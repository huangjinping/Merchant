package com.hdfex.merchantqrshow.salesman.order.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.salesman.order.activity.OrderListActivity;
import com.hdfex.merchantqrshow.salesman.order.activity.OrderMonthlyListActivity;
import com.hdfex.merchantqrshow.utils.UserManager;

/**
 * 订单列表界面
 * Created by harrishuang on 2016/11/29.
 */

public class OrderAllFragment extends BaseFragment {

    private RadioGroup rag_segment;
    private LinearLayout layout_fragment_content;

    private ImageView img_back;
    private ImageView img_search;
    private TextView tb_tv_titile;
    private User user;
    private RadioButton rad_order_first;
    private RadioButton rad_order_secend;
    private RadioButton rad_order_huabei;
    private RadioButton rad_order_monthly;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_all, container, false);
        initView(view);
        initData();
        setOnLiteners();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Bundle bundle = getArguments();
        boolean back = false;
        if (bundle != null) {
            back = bundle.getBoolean("back", false);
        }
        if (back) {
            img_back.setVisibility(View.VISIBLE);
        } else {
            img_back.setVisibility(View.GONE);
        }

        user = UserManager.getUser(getActivity());
        tb_tv_titile.setVisibility(View.VISIBLE);
        updatePromiss();

    }


    private String[] sourceTypeArr;

    private void updatePromiss() {
        if (UserManager.isLogin(getActivity())) {
            User user = UserManager.getUser(getActivity());
            String sourceType = user.getSourceType();
            if (TextUtils.isEmpty(sourceType)) {
                return;
            }
            String[] arr = sourceType.split("[,]+");
            sourceTypeArr = arr;
            if (arr != null) {
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("3")) {
                        rad_order_secend.setVisibility(View.VISIBLE);
                    } else if (arr[i].equals("6")) {
                        rad_order_huabei.setVisibility(View.VISIBLE);
                    } else if (arr[i].equals("10")) {
                        rad_order_monthly.setVisibility(View.VISIBLE);
                    } else {
                        rad_order_first.setVisibility(View.VISIBLE);
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
                        setSelection(0);
                    } else if (rad_order_secend.getVisibility() == View.VISIBLE) {
                        setSelection(1);
                    } else if (rad_order_monthly.getVisibility() == View.VISIBLE) {
                        setSelection(3);
                    } else {
                        setSelection(2);
                    }
                } else {
                    tb_tv_titile.setVisibility(View.VISIBLE);
                    rag_segment.setVisibility(View.GONE);
                    if (sourceTypeArr[0].equals("3")) {
                        tb_tv_titile.setText("货款订单");
                        setSelection(1);
                    } else if (sourceTypeArr[0].equals("6")) {
                        tb_tv_titile.setText("花呗订单");
                        setSelection(2);
                    } else if (sourceTypeArr[0].equals("10")) {
                        tb_tv_titile.setText("月付订单");
                        setSelection(3);
                    } else {
                        tb_tv_titile.setText("分期订单");
                        setSelection(0);
                    }
                }


//                if (sourceTypeArr.length > 1) {
//
//                    /**
//                     * 两个及以上的时候
//                     */
//                    Map<String, String> sourceTypeMap = user.getSourceTypeMap();
//
//
//
//                    if ((rad_order_first.getVisibility() == View.VISIBLE) && (sourceTypeMap.containsKey("3") || sourceTypeMap.containsKey("6") || sourceTypeArr[0].equals("10"))) {
//                        /**
//                         * 含有3、6
//                         */
//                        tb_tv_titile.setVisibility(View.GONE);
//                        rag_segment.setVisibility(View.VISIBLE);
//                        if (rad_order_first.getVisibility() == View.VISIBLE) {
//                            setSelection(0);
//                        } else if (rad_order_secend.getVisibility() == View.VISIBLE) {
//                            setSelection(1);
//                        } else if (rad_order_monthly.getVisibility() == View.VISIBLE) {
//                            setSelection(3);
//                        } else {
//                            setSelection(2);
//                        }
//                    } else {
//                        /**
//                         * 多行不含有3、6
//                         */
//                        setSelection(0);
//                        tb_tv_titile.setText("分期订单");
//                        tb_tv_titile.setVisibility(View.VISIBLE);
//                        rag_segment.setVisibility(View.GONE);
//                    }
//                }

            }


        }
    }

    /**
     * 设置监听器
     */
    private void setOnLiteners() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rad_order_first.isChecked() == true) {
                    OrderListActivity.start(getActivity());

                } else if (rad_order_monthly.isChecked() == true) {
                    OrderMonthlyListActivity
                            .start(getActivity());

                }
            }
        });

        rag_segment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rad_order_first:
                        setSelection(0);
                        break;
                    case R.id.rad_order_secend:
                        setSelection(1);
                        break;
                    case R.id.rad_order_huabei:
                        setSelection(2);
                        break;
                    case R.id.rad_order_monthly:
                        setSelection(3);
                        break;
                }
            }


        });
    }
//    bsyFragment = new OrderBSYFragment();
//    orderFragment = new OrderFragment();
//    orderMonthlyFragment = new OrderMonthlyFragment();
//    orderHuabeiFragment = new OrderHuabeiFragment();

    /**
     * @param index
     */
    private void setSelection(int index) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();
        if (index == 0) {
            Bundle bundle = getArguments();
            OrderFragment orderFragment = new OrderFragment();
            orderFragment.setArguments(bundle);
            transaction.replace(R.id.layout_fragment_content, orderFragment).commit();
        } else if (index == 1) {
            transaction.replace(R.id.layout_fragment_content, new OrderBSYFragment()).commit();
        } else if (index == 2) {
            Log.d("hjp", "是花呗");
            transaction.replace(R.id.layout_fragment_content, new OrderHuabeiFragment()).commit();
        } else if (index == 3) {
            Log.d("hjp", "月付");
            transaction.replace(R.id.layout_fragment_content, new OrderMonthlyFragment()).commit();
        }
    }


    private void initView(View view) {
        img_back = (ImageView) view.findViewById(R.id.img_back);
        rag_segment = (RadioGroup) view.findViewById(R.id.rag_segment);
        layout_fragment_content = (LinearLayout) view.findViewById(R.id.layout_fragment_content);
        img_search = (ImageView) view.findViewById(R.id.img_search);
        tb_tv_titile = (TextView) view.findViewById(R.id.tb_tv_titile);
        rad_order_first = (RadioButton) view.findViewById(R.id.rad_order_first);
        rad_order_secend = (RadioButton) view.findViewById(R.id.rad_order_secend);
        rad_order_huabei = (RadioButton) view.findViewById(R.id.rad_order_huabei);
        rad_order_monthly = (RadioButton) view.findViewById(R.id.rad_order_monthly);

    }
}
