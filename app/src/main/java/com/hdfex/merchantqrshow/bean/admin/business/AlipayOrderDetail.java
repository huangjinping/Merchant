package com.hdfex.merchantqrshow.bean.admin.business;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/12/28.
 * email : huangjinping@hdfex.com
 */

public class AlipayOrderDetail extends BaseBean {

    private String applyDate;
    private String loanAmount;
    private String applyAmount;
    private String commodityName;
    private String applyStatus;
    private String applyDuration;
    private String redpacketAmt;

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyDuration() {
        return applyDuration;
    }

    public void setApplyDuration(String applyDuration) {
        this.applyDuration = applyDuration;
    }

    public String getRedpacketAmt() {
        return redpacketAmt;
    }

    public void setRedpacketAmt(String redpacketAmt) {
        this.redpacketAmt = redpacketAmt;
    }
}
