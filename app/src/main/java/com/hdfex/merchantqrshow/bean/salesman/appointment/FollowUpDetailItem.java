package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/11/20.
 * email : huangjinping@hdfex.com
 */

public class FollowUpDetailItem extends BaseBean {


    /**
     * result : 不清晰
     * duration : 4
     * desc : 我们在2017-04-27 18:25:33审核了客户黄金平的资料
     * loanAmount : 172.0
     * amountPer : 43.0
     * bizRemark :
     * commodityName : 北京市北京市东城区V+公寓朱辛庄五河公寓360号16号楼我的层微信的朋友室
     * examineId : 6263306910142825473
     */

    private String result;
    private int duration;
    private String desc;
    private String loanAmount;
    private String amountPer;
    private String bizRemark;
    private String commodityName;
    private String examineId;
    private String applyId;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getAmountPer() {
        return amountPer;
    }

    public void setAmountPer(String amountPer) {
        this.amountPer = amountPer;
    }

    public String getBizRemark() {
        return bizRemark;
    }

    public void setBizRemark(String bizRemark) {
        this.bizRemark = bizRemark;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }
}
