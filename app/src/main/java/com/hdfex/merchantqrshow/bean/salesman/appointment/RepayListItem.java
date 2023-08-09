package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2017/11/17.
 * email : huangjinping@hdfex.com
 */

public class RepayListItem extends BaseBean {

    /**
     * custId : 6263249393697461249
     * psDueDt : 2017-10-17
     * applyId : 6336837908834550785
     * desc : 仅支持到期自动扣款:尾号64634
     * days : 31
     * idName : 黄金平
     * commityList : [{"id":null,"remarks":null,"createDate":null,"updateDate":null,"commodityId":null,"commodityName":"测试现金贷","commodityPrice":null,"pic":null,"commodityCount":null,"startDate":null,"endDate":null,"commodityAddress":null,"downpaymentName":null,"commoditCategory":"0802"}]
     * fundSource : 12
     * repaymentCardNo : 4664646466464634
     * prepaymentDt : 2017-10-17
     * repayAmt : 4.57
     */

    private String custId;
    private String psDueDt;
    private String applyId;
    private String desc;
    private String days;
    private String idName;
    private String fundSource;
    private String repaymentCardNo;
    private String prepaymentDt;
    private String repayAmt;
    private String overDesc;
    private String planId;
    private String phoneCount;
    private String SMSCount;
    private String phoneNo;



    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPhoneCount() {
        return phoneCount;
    }

    public void setPhoneCount(String phoneCount) {
        this.phoneCount = phoneCount;
    }

    public String getSMSCount() {
        return SMSCount;
    }

    public void setSMSCount(String SMSCount) {
        this.SMSCount = SMSCount;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getOverDesc() {
        return overDesc;
    }

    public void setOverDesc(String overDesc) {
        this.overDesc = overDesc;
    }

    private List<CommityListBean> commityList;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPsDueDt() {
        return psDueDt;
    }

    public void setPsDueDt(String psDueDt) {
        this.psDueDt = psDueDt;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getFundSource() {
        return fundSource;
    }

    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
    }

    public String getRepaymentCardNo() {
        return repaymentCardNo;
    }

    public void setRepaymentCardNo(String repaymentCardNo) {
        this.repaymentCardNo = repaymentCardNo;
    }

    public String getPrepaymentDt() {
        return prepaymentDt;
    }

    public void setPrepaymentDt(String prepaymentDt) {
        this.prepaymentDt = prepaymentDt;
    }

    public String getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(String repayAmt) {
        this.repayAmt = repayAmt;
    }

    public List<CommityListBean> getCommityList() {
        return commityList;
    }

    public void setCommityList(List<CommityListBean> commityList) {
        this.commityList = commityList;
    }
}
