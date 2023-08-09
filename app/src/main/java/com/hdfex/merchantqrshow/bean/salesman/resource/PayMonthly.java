package com.hdfex.merchantqrshow.bean.salesman.resource;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2018/7/11.
 * email : huangjinping@hdfex.com
 */
public class PayMonthly extends BaseBean {

    private String bussinessId;
    private String commodityId;
    private String name;
    private String phone;
    private String startDate;
    private String endDate;
    private String downpaymentType;
    private String downpaymentTypeStr;
    private String monthRent;
    private String deposit;
    private String bussinessCustName;
    private String otherMonth;
    private String commodityName;

    private String packageId;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDownpaymentType() {
        return downpaymentType;
    }

    public void setDownpaymentType(String downpaymentType) {
        this.downpaymentType = downpaymentType;
    }

    public String getMonthRent() {
        return monthRent;
    }

    public void setMonthRent(String monthRent) {
        this.monthRent = monthRent;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getBussinessCustName() {
        return bussinessCustName;
    }

    public void setBussinessCustName(String bussinessCustName) {
        this.bussinessCustName = bussinessCustName;
    }

    public String getDownpaymentTypeStr() {
        return downpaymentTypeStr;
    }

    public void setDownpaymentTypeStr(String downpaymentTypeStr) {
        this.downpaymentTypeStr = downpaymentTypeStr;
    }

    public String getOtherMonth() {
        return otherMonth;
    }

    public void setOtherMonth(String otherMonth) {
        this.otherMonth = otherMonth;
    }
}
