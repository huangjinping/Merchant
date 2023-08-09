package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2017/11/17.
 * email : huangjinping@hdfex.com
 */

public class BackOrder extends BaseBean {


    /**
     * applyId : 6263306410836100097
     * status : 已打回
     * reason :
     * idName : 黄金平
     * commityList : [{"id":null,"remarks":null,"createDate":null,"updateDate":null,"commodityId":null,"commodityName":"V+公寓朱辛庄五河公寓360号16号楼我的层微信的朋友室","commodityPrice":null,"pic":null,"commodityCount":null,"startDate":null,"endDate":null,"commodityAddress":"V+公寓朱辛庄五河公寓360号16号楼我的层微信的朋友室","downpaymentName":null,"commoditCategory":"04"}]
     */

    private String applyId;
    private String status;
    private String reason;
    private String idName;
    private String followTime;
    private String examineId;
    private String phoneCount;
    private String phoneNo;
    private String examineTime;

    public String getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(String examineTime) {
        this.examineTime = examineTime;
    }

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

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    private List<CommityListBean> commityList;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public List<CommityListBean> getCommityList() {
        return commityList;
    }

    public void setCommityList(List<CommityListBean> commityList) {
        this.commityList = commityList;
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }
}
