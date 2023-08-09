package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * author Created by harrishuang on 2018/7/26.
 * email : huangjinping@hdfex.com
 */
public class MonthlyOrderBean extends BaseBean {

    private String id;
    private String applyAmount;
    private String status;
    private String examineStatus;
    private String examineStatusDesc;
    private String orderDate;
    private String orderTime;
    private String sendFlag;
    private String custName;
    private ArrayList<OrderCommodityDetail> orderCommoditys;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public ArrayList<OrderCommodityDetail> getOrderCommoditys() {
        return orderCommoditys;
    }

    public void setOrderCommoditys(ArrayList<OrderCommodityDetail> orderCommoditys) {
        this.orderCommoditys = orderCommoditys;
    }
}
