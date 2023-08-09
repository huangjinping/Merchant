package com.hdfex.merchantqrshow.bean.salesman.plan;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2017/5/26.
 * email : huangjinping@hdfex.com
 */

public class PlanDetail extends BaseBean {

    private String dueAmt;
    private String settledAmt;
    private String preAmt;
    private String cardNo;
    private String bankName;
    private String fundSource;
    private String lastRepayDate;
    private String status;
    private String payStatus;
    private String failedReason;
    private String repaymentNotice;

    private List<RepayDetail> repayDetailList;
    private List<RepayPlan> repayPlanList;

    public String getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(String dueAmt) {
        this.dueAmt = dueAmt;
    }

    public String getSettledAmt() {
        return settledAmt;
    }

    public void setSettledAmt(String settledAmt) {
        this.settledAmt = settledAmt;
    }

    public String getPreAmt() {
        return preAmt;
    }

    public void setPreAmt(String preAmt) {
        this.preAmt = preAmt;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getFundSource() {
        return fundSource;
    }

    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
    }

    public String getLastRepayDate() {
        return lastRepayDate;
    }

    public void setLastRepayDate(String lastRepayDate) {
        this.lastRepayDate = lastRepayDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public List<RepayDetail> getRepayDetailList() {
        return repayDetailList;
    }

    public void setRepayDetailList(List<RepayDetail> repayDetailList) {
        this.repayDetailList = repayDetailList;
    }

    public List<RepayPlan> getRepayPlanList() {
        return repayPlanList;
    }

    public void setRepayPlanList(List<RepayPlan> repayPlanList) {
        this.repayPlanList = repayPlanList;
    }

    public String getRepaymentNotice() {
        return repaymentNotice;
    }

    public void setRepaymentNotice(String repaymentNotice) {
        this.repaymentNotice = repaymentNotice;
    }

    @Override
    public String toString() {
        return "PlanDetail{" +
                "dueAmt='" + dueAmt + '\'' +
                ", settledAmt='" + settledAmt + '\'' +
                ", preAmt='" + preAmt + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", bankName='" + bankName + '\'' +
                ", fundSource='" + fundSource + '\'' +
                ", lastRepayDate='" + lastRepayDate + '\'' +
                ", status='" + status + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", failedReason='" + failedReason + '\'' +
                ", repaymentNotice='" + repaymentNotice + '\'' +
                ", repayDetailList=" + repayDetailList +
                ", repayPlanList=" + repayPlanList +
                '}';
    }
}
