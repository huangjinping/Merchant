package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2017/8/15.
 * email : huangjinping@hdfex.com
 */

public class DurationPayment extends BaseBean {

    private String feeAmount;
    private String realLoanAmount;
    private String commodityAmount;
    private List<AlipayPlan> durationList;
    private String feeAmountAver;

    public String getFeeAmountAver() {
        return feeAmountAver;
    }

    public void setFeeAmountAver(String feeAmountAver) {
        this.feeAmountAver = feeAmountAver;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public List<AlipayPlan> getDurationList() {
        return durationList;
    }

    public void setDurationList(List<AlipayPlan> durationList) {
        this.durationList = durationList;
    }

    public String getRealLoanAmount() {
        return realLoanAmount;
    }

    public void setRealLoanAmount(String realLoanAmount) {
        this.realLoanAmount = realLoanAmount;
    }

    public String getCommodityAmount() {
        return commodityAmount;
    }

    public void setCommodityAmount(String commodityAmount) {
        this.commodityAmount = commodityAmount;
    }

    @Override
    public String toString() {
        return "DurationPayment{" +
                "feeAmount='" + feeAmount + '\'' +
                ", realLoanAmount='" + realLoanAmount + '\'' +
                ", commodityAmount='" + commodityAmount + '\'' +
                ", durationList=" + durationList +
                '}';
    }
}
