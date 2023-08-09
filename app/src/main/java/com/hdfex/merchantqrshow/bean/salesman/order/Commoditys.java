package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by harrishuang on 16/8/17.
 */

public class Commoditys  extends BaseBean{
    /**
     * startDate : 2017-02-25
     * monthRent : null
     * commodityPrice : 18000
     * endDate : 2018-02-25
     * commodityName : 测试商户-产品部
     * pic : http://123.56.41.230/hd-merchant-web/mobile/picture/down?file=null
     * commodityId : 6239637972321586177
     * downpaymentType : 1
     */

    private String startDate;
    private Object monthRent;
    private String commodityPrice;
    private String endDate;
    private String commodityName;
    private String pic;
    private String commodityId;
    private String downpaymentType;
    private String deposit;
    private String payTypeName;
    private String payType;

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

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public void setMonthRent(String monthRent) {
        this.monthRent = monthRent;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Object getMonthRent() {
        return monthRent;
    }

    public void setMonthRent(Object monthRent) {
        this.monthRent = monthRent;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getDownpaymentType() {
        return downpaymentType;
    }

    public void setDownpaymentType(String downpaymentType) {
        this.downpaymentType = downpaymentType;
    }


    private  int  commodityCount;

    public int getCommodityCount() {
        return commodityCount;
    }

    public void setCommodityCount(int commodityCount) {
        this.commodityCount = commodityCount;
    }

    @Override
    public String toString() {
        return "Commoditys{" +
                "startDate='" + startDate + '\'' +
                ", monthRent=" + monthRent +
                ", commodityPrice='" + commodityPrice + '\'' +
                ", endDate='" + endDate + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", pic='" + pic + '\'' +
                ", commodityId='" + commodityId + '\'' +
                ", downpaymentType='" + downpaymentType + '\'' +
                ", commodityCount=" + commodityCount +
                '}';
    }
}
