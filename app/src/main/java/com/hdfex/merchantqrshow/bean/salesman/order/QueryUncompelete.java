package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.ArrayList;

/**
 * Created by harrishuang on 16/7/11.
 */
public class QueryUncompelete extends BaseBean {

    private String packageId;
    private String totalPrice;
    private String applyAmount;
    private String scanDate;
    private String downpayment;
    private String periodAmount;
    private Integer duration;
    private String idName;
    private String phone;
    private  String status;
    private String createTime;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private ArrayList<OrderCommodityDetail> commoditys;

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<OrderCommodityDetail> getCommoditys() {
        return commoditys;
    }

    public void setCommoditys(ArrayList<OrderCommodityDetail> commoditys) {
        this.commoditys = commoditys;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(String downpayment) {
        this.downpayment = downpayment;
    }

    public String getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(String periodAmount) {
        this.periodAmount = periodAmount;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }


    public String getPackageId() {
        return packageId;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "QueryUncompelete{" +
                "packageId='" + packageId + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", applyAmount='" + applyAmount + '\'' +
                ", scanDate='" + scanDate + '\'' +
                ", downpayment='" + downpayment + '\'' +
                ", periodAmount='" + periodAmount + '\'' +
                ", duration=" + duration +
                ", idName='" + idName + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                ", commoditys=" + commoditys +
                '}';
    }
}
