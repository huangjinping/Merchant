package com.hdfex.merchantqrshow.bean.salesman.realtyQR;

import com.hdfex.merchantqrshow.bean.BaseBean;
import com.hdfex.merchantqrshow.bean.salesman.order.Commoditys;

import java.math.BigDecimal;
import java.util.List;

/**
 * author Created by harrishuang on 2018/7/12.
 * email : huangjinping@hdfex.com
 */
public class CommodityInfoForHR extends BaseBean {


    /**
     * periodAmount : 10077.0
     * createTime : 2018-07-12 13:37:18
     * phone : 13611290917
     * custId :
     * downpayment : 19.0
     * qrcodeUrl : http://182.92.6.16:8081/hd-merchant-biz-app/app/getCommodityPackageCode?packageId=6423047420556346369&type=3
     */
    private BigDecimal periodAmount;
    private String createTime;
    private String phone;
    private String custId;
    private BigDecimal downpayment;
    private String qrcodeUrl;
    private List<Commoditys> commoditys;

    public BigDecimal getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(BigDecimal periodAmount) {
        this.periodAmount = periodAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public BigDecimal getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(BigDecimal downpayment) {
        this.downpayment = downpayment;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public List<Commoditys> getCommoditys() {
        return commoditys;
    }

    public void setCommoditys(List<Commoditys> commoditys) {
        this.commoditys = commoditys;
    }
}
