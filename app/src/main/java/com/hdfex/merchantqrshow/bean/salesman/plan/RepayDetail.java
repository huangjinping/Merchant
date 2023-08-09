package com.hdfex.merchantqrshow.bean.salesman.plan;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/5/26.
 * email : huangjinping@hdfex.com
 */

public class RepayDetail extends BaseBean {


    private String detailPeriod;
    private String lateFee;
    private String detailInStmAmt;

    public String getDetailInStmAmt() {
        return detailInStmAmt;
    }

    public void setDetailInStmAmt(String detailInStmAmt) {
        this.detailInStmAmt = detailInStmAmt;
    }

    public String getDetailPeriod() {
        return detailPeriod;
    }

    public void setDetailPeriod(String detailPeriod) {
        this.detailPeriod = detailPeriod;
    }

    public String getLateFee() {
        return lateFee;
    }

    public void setLateFee(String lateFee) {
        this.lateFee = lateFee;
    }

    @Override
    public String toString() {
        return "RepayDetail{" +
                "detailPeriod='" + detailPeriod + '\'' +
                ", lateFee='" + lateFee + '\'' +
                '}';
    }
}
