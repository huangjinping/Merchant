package com.hdfex.merchantqrshow.bean.salesman.bsy;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.math.BigDecimal;

/**
 * Created by harrishuang on 2016/11/25.
 */

public class ProductItem extends BaseBean {

    private String commodityId;
    private  String commodityName;
    private BigDecimal commodityPrice;
    private  String parameter;
    private  String commodityUrl;
    private   String countLeft;

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

    public BigDecimal getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(BigDecimal commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getCommodityUrl() {
        return commodityUrl;
    }

    public void setCommodityUrl(String commodityUrl) {
        this.commodityUrl = commodityUrl;
    }

    public String getCountLeft() {
        return countLeft;
    }

    public void setCountLeft(String countLeft) {
        this.countLeft = countLeft;
    }
}
