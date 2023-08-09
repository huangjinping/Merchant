package com.hdfex.merchantqrshow.bean.manager.business;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class BusinessForTotalOrderResult extends BaseBean {

    private String loanedCount;
    private String cancelOrderCount;
    private String rejectOrderCount;
    private String overDueRate;

    public String getLoanedCount() {
        return loanedCount;
    }

    public void setLoanedCount(String loanedCount) {
        this.loanedCount = loanedCount;
    }

    public String getCancelOrderCount() {
        return cancelOrderCount;
    }

    public void setCancelOrderCount(String cancelOrderCount) {
        this.cancelOrderCount = cancelOrderCount;
    }

    public String getRejectOrderCount() {
        return rejectOrderCount;
    }

    public void setRejectOrderCount(String rejectOrderCount) {
        this.rejectOrderCount = rejectOrderCount;
    }

    public String getOverDueRate() {
        return overDueRate;
    }

    public void setOverDueRate(String overDueRate) {
        this.overDueRate = overDueRate;
    }

    @Override
    public String toString() {
        return "BusinessForTotalOrderResult{" +
                "loanedCount='" + loanedCount + '\'' +
                ", cancelOrderCount='" + cancelOrderCount + '\'' +
                ", rejectOrderCount='" + rejectOrderCount + '\'' +
                ", overDueRate='" + overDueRate + '\'' +
                '}';
    }
}
