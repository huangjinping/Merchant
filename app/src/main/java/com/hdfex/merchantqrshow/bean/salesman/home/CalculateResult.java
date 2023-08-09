package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by harrishuang on 2017/4/26.
 */

public class CalculateResult extends BaseBean {

    private  String liveDay;
    private  String  unliveDay;
    private  String refundAmt;
    private  String backAmt;

    public String getLiveDay() {
        return liveDay;
    }

    public void setLiveDay(String liveDay) {
        this.liveDay = liveDay;
    }

    public String getUnliveDay() {
        return unliveDay;
    }

    public void setUnliveDay(String unliveDay) {
        this.unliveDay = unliveDay;
    }

    public String getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(String refundAmt) {
        this.refundAmt = refundAmt;
    }

    public String getBackAmt() {
        return backAmt;
    }

    public void setBackAmt(String backAmt) {
        this.backAmt = backAmt;
    }
}
