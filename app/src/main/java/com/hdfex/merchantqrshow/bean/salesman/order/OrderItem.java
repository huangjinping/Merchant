package com.hdfex.merchantqrshow.bean.salesman.order;


import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.ArrayList;

/**
 * Created by harrishuang on 16/7/8.
 */
public class OrderItem extends BaseBean {

    private String id;
    private String businessName;
    private String totalPrice;
    private String downpayment;
    private String discountAmount;
    private String applyAmount;
    private String periodamount;
    private Integer duration;
    private String time;
    private String status;
    private int loanType;
    private long orderNo;
    private String orderDate;
    private String orderTime;
    private String custName;
    private String sendFlag;


    private ArrayList<OrderCommodityDetail> orderCommoditys;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(String downpayment) {
        this.downpayment = downpayment;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getPeriodamount() {
        return periodamount;
    }

    public void setPeriodamount(String periodamount) {
        this.periodamount = periodamount;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLoanType() {
        return loanType;
    }

    public void setLoanType(int loanType) {
        this.loanType = loanType;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
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

    public ArrayList<OrderCommodityDetail> getOrderCommoditys() {
        return orderCommoditys;
    }

    public void setOrderCommoditys(ArrayList<OrderCommodityDetail> orderCommoditys) {
        this.orderCommoditys = orderCommoditys;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }
}
