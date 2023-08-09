package com.hdfex.merchantqrshow.bean.manager.finance;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/6/7.
 * email : huangjinping@hdfex.com
 */

public class Finance extends BaseBean {

    private String curdate;
    private String curDateLoanAmt;
    private String curMonthRefundAmt;
    private String curMonthLoanAmt;
    private String totalAmt;
    private String desc;
    private String perAmt;
    private String loanBal;

    public String getLoanBal() {
        return loanBal;
    }

    public void setLoanBal(String loanBal) {
        this.loanBal = loanBal;
    }

    public String getPerAmt() {
        return perAmt;
    }

    public void setPerAmt(String perAmt) {
        this.perAmt = perAmt;
    }

    public String getCurdate() {
        return curdate;
    }

    public void setCurdate(String curdate) {
        this.curdate = curdate;
    }

    public String getCurDateLoanAmt() {
        return curDateLoanAmt;
    }

    public void setCurDateLoanAmt(String curDateLoanAmt) {
        this.curDateLoanAmt = curDateLoanAmt;
    }

    public String getCurMonthRefundAmt() {
        return curMonthRefundAmt;
    }

    public void setCurMonthRefundAmt(String curMonthRefundAmt) {
        this.curMonthRefundAmt = curMonthRefundAmt;
    }

    public String getCurMonthLoanAmt() {
        return curMonthLoanAmt;
    }

    public void setCurMonthLoanAmt(String curMonthLoanAmt) {
        this.curMonthLoanAmt = curMonthLoanAmt;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override
    public String toString() {
        return "Finance{" +
                "curdate='" + curdate + '\'' +
                ", curDateLoanAmt='" + curDateLoanAmt + '\'' +
                ", curMonthRefundAmt='" + curMonthRefundAmt + '\'' +
                ", curMonthLoanAmt='" + curMonthLoanAmt + '\'' +
                ", totalAmt='" + totalAmt + '\'' +
                ", desc='" + desc + '\'' +
                ", perAmt='" + perAmt + '\'' +
                ", loanBal='" + loanBal + '\'' +
                '}';
    }
}
