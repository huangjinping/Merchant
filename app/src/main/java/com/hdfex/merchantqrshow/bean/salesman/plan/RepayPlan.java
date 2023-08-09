package com.hdfex.merchantqrshow.bean.salesman.plan;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/5/26.
 * email : huangjinping@hdfex.com
 */

public class RepayPlan extends BaseBean {

    /**
     * period : 1
     * dueDate : 2019-01-30
     * inStmAmt :
     * setlInd : 02
     * psCommOdInt : null
     * psFeeAmt :
     * lateFee :
     * ovdueDays :
     * psOdInd : Y
     */

    private String period;
    private String dueDate;
    private String inStmAmt;
    private String setlInd;
    private Object psCommOdInt;
    private String psFeeAmt;
    private String lateFee;
    private String ovdueDays;
    private String psOdInd;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getInStmAmt() {
        return inStmAmt;
    }

    public void setInStmAmt(String inStmAmt) {
        this.inStmAmt = inStmAmt;
    }

    public String getSetlInd() {
        return setlInd;
    }

    public void setSetlInd(String setlInd) {
        this.setlInd = setlInd;
    }

    public Object getPsCommOdInt() {
        return psCommOdInt;
    }

    public void setPsCommOdInt(Object psCommOdInt) {
        this.psCommOdInt = psCommOdInt;
    }

    public String getPsFeeAmt() {
        return psFeeAmt;
    }

    public void setPsFeeAmt(String psFeeAmt) {
        this.psFeeAmt = psFeeAmt;
    }

    public String getLateFee() {
        return lateFee;
    }

    public void setLateFee(String lateFee) {
        this.lateFee = lateFee;
    }

    public String getOvdueDays() {
        return ovdueDays;
    }

    public void setOvdueDays(String ovdueDays) {
        this.ovdueDays = ovdueDays;
    }

    public String getPsOdInd() {
        return psOdInd;
    }

    public void setPsOdInd(String psOdInd) {
        this.psOdInd = psOdInd;
    }
}
