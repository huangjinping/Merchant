package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.math.BigDecimal;

/**
 * author Created by harrishuang on 2018/7/26.
 * email : huangjinping@hdfex.com
 */
public class OrderMonthlyDetail  extends BaseBean{

    /**
     * statusDesc : 审核中
     * endDate : 2019-07-18
     * renterIdNo : 410211198904083033
     * rentAmt : 1977.0
     * url :
     * payTypeName : 年付
     * payType : 03
     * buttonDescTitle :
     * deposit : 12.0
     * renterName : 黄金平
     * startDate : 2018-07-18
     * renterPhone : 13611290917
     * status : 00
     * commodityName : 呵呵呵呵呵u
     */

    private String statusDesc;
    private String endDate;
    private String renterIdNo;
    private BigDecimal rentAmt;
    private String url;
    private String payTypeName;
    private String payType;
    private String buttonDescTitle;
    private BigDecimal deposit;
    private String renterName;
    private String startDate;
    private String renterPhone;
    private String status;
    private String commodityName;
    private String contractId;
    private String applyId;
    private String examineStatusDesc;
    private String examineStatus;
    private String businessId;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getExamineStatus() {
        return examineStatus;
    }

    public void setExamineStatus(String examineStatus) {
        this.examineStatus = examineStatus;
    }

    public String getExamineStatusDesc() {
        return examineStatusDesc;
    }

    public void setExamineStatusDesc(String examineStatusDesc) {
        this.examineStatusDesc = examineStatusDesc;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRenterIdNo() {
        return renterIdNo;
    }

    public void setRenterIdNo(String renterIdNo) {
        this.renterIdNo = renterIdNo;
    }

    public BigDecimal getRentAmt() {
        return rentAmt;
    }

    public void setRentAmt(BigDecimal rentAmt) {
        this.rentAmt = rentAmt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getButtonDescTitle() {
        return buttonDescTitle;
    }

    public void setButtonDescTitle(String buttonDescTitle) {
        this.buttonDescTitle = buttonDescTitle;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getRenterPhone() {
        return renterPhone;
    }

    public void setRenterPhone(String renterPhone) {
        this.renterPhone = renterPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }
}
