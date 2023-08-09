package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/9/14.
 * email : huangjinping@hdfex.com
 */

public class FollowUpItem extends BaseBean {


    /**
     * custId : 6240464907816945665
     * applyId : 6240469384358265857
     * loanStatus : 审核中
     * status : 01
     * custPhone : 18510147090
     * belongGroup : 测试
     * bizCallFllowDate : 2017-09-14 11:22:33
     * bizName : 测试
     * custName : 梁栋
     * callStatus : 未接通
     */
    /**
     * 未跟进
     */
    public static final String UN_FOLLOW="00";
    /**
     * 已跟进
     */
    public static final String FOLLOWED="01";


    private String custId;
    private String applyId;
    private String loanStatus;
    private String status;
    private String custPhone;
    private String belongGroup;
    private String bizCallFllowDate;
    private String bizName;
    private String custName;
    private String callStatus;

    private String followTime;
    private String examineId;

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getBelongGroup() {
        return belongGroup;
    }

    public void setBelongGroup(String belongGroup) {
        this.belongGroup = belongGroup;
    }

    public String getBizCallFllowDate() {
        return bizCallFllowDate;
    }

    public void setBizCallFllowDate(String bizCallFllowDate) {
        this.bizCallFllowDate = bizCallFllowDate;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }
}
