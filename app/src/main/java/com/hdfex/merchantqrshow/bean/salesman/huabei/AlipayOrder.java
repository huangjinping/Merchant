package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.BaseBean;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/22.
 * email : huangjinping@hdfex.com
 */

public class AlipayOrder extends BaseBean {

    /**
     * id : null
     * remarks : null
     * createDate : null
     * updateDate : null
     * orderDate : 今天
     * orderTime : 13:34
     * custName : null
     * status : 已完成
     * applyAmount : 0.1
     * orderNo : null
     * commodityId : 6283863614376903681
     * commodityName : 华北分歧
     * commodityPrice : null
     * pic : https://hdfex.com/hd-merchant-web/mobile/picture/down?file=null
     * commodityCount : 1
     * commodityBarcode : 444644644
     */

    private String id;
    private String remarks;
    private String createDate;
    private String updateDate;
    private String orderDate;
    private String orderTime;
    private String custName;
    private String status;
    private String applyAmount;
    private String orderNo;
    private String commodityId;
    private String commodityName;
    private String commodityPrice;
    private String pic;
    private int commodityCount;
    private String commodityBarcode;
    private boolean respay;
    private String custIdNo;
    private String caseId;
    private String duration;
    private String profitSource;
    private String custPhoneNo;
    private String commodityPic;

    private String serviceFee;
    private String feeRate;
    private String totalAmount;

    private String failReason;
    private String payAmout;
    private String orderId;
    private String payWay;



    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getPayAmout() {
        return payAmout;
    }

    public void setPayAmout(String payAmout) {
        this.payAmout = payAmout;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    private List<Installment> commodityCaseList;


    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustIdNo() {
        return custIdNo;
    }

    public void setCustIdNo(String custIdNo) {
        this.custIdNo = custIdNo;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getProfitSource() {
        return profitSource;
    }

    public void setProfitSource(String profitSource) {
        this.profitSource = profitSource;
    }

    public String getCustPhoneNo() {
        return custPhoneNo;
    }

    public void setCustPhoneNo(String custPhoneNo) {
        this.custPhoneNo = custPhoneNo;
    }

    public String getCommodityPic() {
        return commodityPic;
    }

    public void setCommodityPic(String commodityPic) {
        this.commodityPic = commodityPic;
    }

    public boolean isRespay() {
        return respay;
    }

    public void setRespay(boolean respay) {
        this.respay = respay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getCommodityCount() {
        return commodityCount;
    }

    public void setCommodityCount(int commodityCount) {
        this.commodityCount = commodityCount;
    }

    public String getCommodityBarcode() {
        return commodityBarcode;
    }

    public void setCommodityBarcode(String commodityBarcode) {
        this.commodityBarcode = commodityBarcode;
    }

    public List<Installment> getCommodityCaseList() {
        return commodityCaseList;
    }

    public void setCommodityCaseList(List<Installment> commodityCaseList) {
        this.commodityCaseList = commodityCaseList;
    }
}
