package com.hdfex.merchantqrshow.bean.manager.business;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class BusinessOrder extends BaseBean {
//    int	订单状态：
//            0-	已打回
//1-	已取消
//2-	已拒绝

    public static final String STATUS_LOANED = "0";
    public static final String STATUS_CANCEL = "1";
    public static final String STATUS_REJECT = "2";


//    private String regionName;
//    private String custCount;
//    private int orderCount;



    private String custName;
    private String orderAmt;
    private String failedReason;
    private String followDays;
    private String ownSalesman;
    private String applyDate;
    private String region;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public String getFollowDays() {
        return followDays;
    }

    public void setFollowDays(String followDays) {
        this.followDays = followDays;
    }

    public String getOwnSalesman() {
        return ownSalesman;
    }

    public void setOwnSalesman(String ownSalesman) {
        this.ownSalesman = ownSalesman;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "BusinessOrder{" +
                "custName='" + custName + '\'' +
                ", orderAmt='" + orderAmt + '\'' +
                ", failedReason='" + failedReason + '\'' +
                ", followDays='" + followDays + '\'' +
                ", ownSalesman='" + ownSalesman + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
