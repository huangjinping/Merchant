package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by maidou521 on 2016/8/18.
 */
public class OrderCommodityDetail extends BaseBean {
    private String commodityId;
    private String commodityName;
    private int commodityCount;
    private String commodityPrice;
    private String commodityAddress;
    private String pic;
    private int count;



    private String startDate;
    private String  endDate;
    private String  downpaymentName;


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

    public int getCommodityCount() {
        return commodityCount;
    }

    public void setCommodityCount(int commodityCount) {
        this.commodityCount = commodityCount;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getCommodityAddress() {
        return commodityAddress;
    }

    public void setCommodityAddress(String commodityAddress) {
        this.commodityAddress = commodityAddress;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public String getDownpaymentName() {
        return downpaymentName;
    }

    public void setDownpaymentName(String downpaymentName) {
        this.downpaymentName = downpaymentName;
    }
}
