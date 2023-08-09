package com.hdfex.merchantqrshow.bean.manager.business;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class BusinessForWarnResult extends BaseBean {

    private String overDueCustCount;
    private String repayCustCount;
    private String renewalCount;

    public String getOverDueCustCount() {
        return overDueCustCount;
    }

    public void setOverDueCustCount(String overDueCustCount) {
        this.overDueCustCount = overDueCustCount;
    }

    public String getRepayCustCount() {
        return repayCustCount;
    }

    public void setRepayCustCount(String repayCustCount) {
        this.repayCustCount = repayCustCount;
    }

    public String getRenewalCount() {
        return renewalCount;
    }

    public void setRenewalCount(String renewalCount) {
        this.renewalCount = renewalCount;
    }

    @Override
    public String toString() {
        return "BusinessForWarnResult{" +
                "overDueCustCount='" + overDueCustCount + '\'' +
                ", repayCustCount='" + repayCustCount + '\'' +
                ", renewalCount='" + renewalCount + '\'' +
                '}';
    }
}
