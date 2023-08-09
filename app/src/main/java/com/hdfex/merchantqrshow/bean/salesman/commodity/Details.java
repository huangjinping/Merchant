package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by maidou521 on 2016/7/5.
 */
public class Details extends BaseBean{
    private String commodityId;
    private String commodityName;
    private String commodityPrice;
    public String commodityNumber;
    private String downpayment;
    private String commodityUrl;

    public Details(){}
    public Details(String commodityId, String commodityName, String commodityPrice) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.commodityPrice = commodityPrice;
    }


    public String getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(String downpayment) {
        this.downpayment = downpayment;
    }

    public String getCommodityUrl() {
        return commodityUrl;
    }

    public void setCommodityUrl(String commodityUrl) {
        this.commodityUrl = commodityUrl;
    }

    public String getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(String commodityNumber) {
        this.commodityNumber = commodityNumber;
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

    @Override
    public String toString() {
        return "Details{" +
                "commodityId='" + commodityId + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", commodityPrice='" + commodityPrice + '\'' +
                ", commodityNumber='" + commodityNumber + '\'' +
                ", downpayment='" + downpayment + '\'' +
                ", commodityUrl='" + commodityUrl + '\'' +
                '}';
    }
}
