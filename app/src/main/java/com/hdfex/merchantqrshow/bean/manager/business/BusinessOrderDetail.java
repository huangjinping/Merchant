package com.hdfex.merchantqrshow.bean.manager.business;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class BusinessOrderDetail extends BaseBean {

    private String custName;
    private String orderAmt;
    private String failedReason;
    private String followDays;
    private String ownSalesman;
    private String applyDate;

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
}
