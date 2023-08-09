package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.bean.salesman.order.OrderType;
import com.hdfex.merchantqrshow.mvp.contract.OrderListContract;
import com.hdfex.merchantqrshow.utils.OnComTabSelectListener;
import com.hdfex.merchantqrshow.view.SpannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrishuang on 2017/4/24.
 */

public class OrderListPersenter extends OrderListContract.Persenter implements OnComTabSelectListener {

//
//
//    订单状态：
//    订单状态：
//            0 未提交
//---进行中
//1 审批中
//2 已通过
//3 还款中
//
//---已完成
//4 提前结清
//5 已完成
//6 已取消
//7 已拒绝
//--逾期
//8
//
//

    List<String> item3Str = new ArrayList<>();
    List<String> item4Str = new ArrayList<>();

    List<OrderType> item3 = new ArrayList<>();
    List<OrderType> item4 = new ArrayList<>();

    public OrderListPersenter() {


        item3Str.add("审核中");
        item3Str.add("已通过");
        item3Str.add("还款中");

        item3.add(new OrderType("1", "审核中"));
        item3.add(new OrderType("2", "已通过"));
        item3.add(new OrderType("3", "还款中"));

        item4Str.add("提前结清");
        item4Str.add("已完成");
        item4Str.add("已取消");
        item4Str.add("已拒绝");

        item4.add(new OrderType("4", "提前结清"));
        item4.add(new OrderType("5", "已完成"));
        item4.add(new OrderType("6", "已取消"));
        item4.add(new OrderType("7", "已拒绝"));
    }


    private int currentTab;


    private int secendDefault = 0;

    private int thiredDefault = 2;


    @Override
    public void setCurrentTab(int currentTab, SpannerView layout_container) {
        this.currentTab = currentTab;

        layout_container.setOnComTabSelectListener(this);
        layout_container.dismiss();
        /**
         * 操作
         */
        if (currentTab == 2) {
            layout_container.showItem(item3Str,secendDefault, currentTab);
        }
        if (currentTab == 3) {
            layout_container.showItem(item4Str, thiredDefault, currentTab);
        }
    }

    @Override
    public void setCurrentTab(int position, boolean open, SpannerView layout_container) {

        this.currentTab = position;

        layout_container.setOnComTabSelectListener(this);
        layout_container.dismiss();
        /**
         * 操作
         */
        if (currentTab == 2) {
            layout_container.showItem(item3Str, secendDefault, currentTab);
        }
        if (currentTab == 3) {
            layout_container.showItem(item4Str, thiredDefault, currentTab);
        }
        if (open) {
            layout_container.show();
        } else {
            layout_container.dismiss();
        }

//        this.currentTab = position;
//        if (currentTab  >1) {
//            if (open) {
//                layout_container.show();
//            } else {
//                layout_container.dismiss();
//            }
//        } else {
//            layout_container.dismiss();
//        }
    }

    @Override
    public void onTabSelect(int position) {

        OrderType orderType=null;
        if (currentTab == 2) {
            secendDefault = position;
            orderType=item3.get(position);
        }
        if (currentTab == 3) {
            thiredDefault = position;
             orderType = item4.get(position);
        }

        getmMvpView().returnoNTabSelect(currentTab, position,orderType.getName(),orderType.getType());


    }

    @Override
    public void onTabReselect(int position, boolean open) {

    }
}
