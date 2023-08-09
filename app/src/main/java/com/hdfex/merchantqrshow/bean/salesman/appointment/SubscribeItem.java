package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/9/13.
 * email : huangjinping@hdfex.com
 */

public class SubscribeItem extends BaseBean {
    /**
     * 公共列表
     */
    public static final String public_LIST = "03";


//    01-	拒绝
//02-	公共拒绝

    /**
     * 公共拒绝
     */
    public static final String REFUSE_TYPE_PUBLIC = "02";
    /**
     * 私有拒绝
     */
    public static final String REFUSE_TYPE_PRIVATE = "01";


//    状态
//00-	未跟进
//01-	已跟进
//02-	已完成03-	转单中

    public static final String STATUS_NOT_FOLLOW_UP = "00";
    public static final String STATUS_FOLLOWED = "01";
    public static final String STATUS_COMPLATE = "02";
    public static final String STATUS_TURNING = "03";

//    转单类型
//00-	发起方01-	接收方

    public static final String TURN_TYPE_SEND = "00";
    public static final String TURN_TYPE_RECEIVE = "01";

    private String subscribeId;
    private String turnType;
    private String followTime;
    private String status;
    private String type;
    private String address;
    private String expectAmount;
    private String checkInDate;
    private String remark;
    private String courtName;
    private String addrDetail;
    private String subscribeDate;
    private String bizName;
    private String turnDesc;
    private String belongGroup;
    private String refuseType;


    public String getRefuseType() {
        return refuseType;
    }

    public void setRefuseType(String refuseType) {
        this.refuseType = refuseType;
    }

    public String getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(String subscribeId) {
        this.subscribeId = subscribeId;
    }

    public String getTurnType() {
        return turnType;
    }

    public void setTurnType(String turnType) {
        this.turnType = turnType;
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpectAmount() {
        return expectAmount;
    }

    public void setExpectAmount(String expectAmount) {
        this.expectAmount = expectAmount;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getSubscribeDate() {
        return subscribeDate;
    }

    public void setSubscribeDate(String subscribeDate) {
        this.subscribeDate = subscribeDate;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getTurnDesc() {
        return turnDesc;
    }

    public void setTurnDesc(String turnDesc) {
        this.turnDesc = turnDesc;
    }

    public String getBelongGroup() {
        return belongGroup;
    }

    public void setBelongGroup(String belongGroup) {
        this.belongGroup = belongGroup;
    }
}
