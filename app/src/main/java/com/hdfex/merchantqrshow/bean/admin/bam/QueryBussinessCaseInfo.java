package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.math.BigDecimal;

/**
 * author Created by harrishuang on 2017/12/7.
 * email : huangjinping@hdfex.com
 */

public class QueryBussinessCaseInfo extends BaseBean {

    /**
     * caseDuration : 5
     * caseId : 6283912396514788353
     * perAmount : 2400.0
     * perFee : 0.0
     */

    private int caseDuration;
    private String caseId;
    private BigDecimal perAmount;
    private BigDecimal perFee;

    public int getCaseDuration() {
        return caseDuration;
    }

    public void setCaseDuration(int caseDuration) {
        this.caseDuration = caseDuration;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public BigDecimal getPerAmount() {
        return perAmount;
    }

    public void setPerAmount(BigDecimal perAmount) {
        this.perAmount = perAmount;
    }

    public BigDecimal getPerFee() {
        return perFee;
    }

    public void setPerFee(BigDecimal perFee) {
        this.perFee = perFee;
    }
}
