package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * Created by harrishuang on 2017/3/2.
 */

public class PeriodAmountResult extends BaseBean {

    private String periodAmount;
    private String serviceFee;
    private Integer gracePeriod;
    private String managementFees;
    private String period;
    private String desc;

    private List<RepayPlan> repayPlanList;

    public List<RepayPlan> getRepayPlanList() {
        return repayPlanList;
    }

    public void setRepayPlanList(List<RepayPlan> repayPlanList) {
        this.repayPlanList = repayPlanList;
    }

    public String getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(String periodAmount) {
        this.periodAmount = periodAmount;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Integer getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Integer gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public String getManagementFees() {
        return managementFees;
    }

    public void setManagementFees(String managementFees) {
        this.managementFees = managementFees;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
