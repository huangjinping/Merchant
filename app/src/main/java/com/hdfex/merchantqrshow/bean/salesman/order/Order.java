package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.BaseBean;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;

import java.util.List;


/**
 * Created by harrishuang on 16/7/5.
 */
public class Order extends BaseBean {



    private int  index;
    private String  currentApplyAmount;
    private String orderId;
    private String duration;
    private String downpayment;
    private String applyAmount;
    private String createTime;
    private String totalPrice;
    private String periodAmount;
    private String commodityName;
    private String logoUrl;
    private String qrcodeUrl;
    private String custId;
    private String phone;
    private List<Installment> list;
    private String prouctList;
    private List<Commoditys> commoditys;
    private List<Prouct>  commoditysList;
    private String gracePeriodAmount;
    private String gracePeriod;
    public String getGracePeriodAmount() {
        return gracePeriodAmount;
    }

    public void setGracePeriodAmount(String gracePeriodAmount) {
        this.gracePeriodAmount = gracePeriodAmount;
    }

    public String getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(String gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public List<Prouct> getCommoditysList() {
        return commoditysList;
    }

    public void setCommoditysList(List<Prouct> commoditysList) {
        this.commoditysList = commoditysList;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(String downpayment) {
        this.downpayment = downpayment;
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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(String periodAmount) {
        this.periodAmount = periodAmount;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Installment> getList() {
        return list;
    }

    public void setList(List<Installment> list) {
        this.list = list;
    }

    public String getProuctList() {
        return prouctList;
    }

    public void setProuctList(String prouctList) {
        this.prouctList = prouctList;
    }

    public List<Commoditys> getCommoditys() {
        return commoditys;
    }

    public void setCommoditys(List<Commoditys> commoditys) {
        this.commoditys = commoditys;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCurrentApplyAmount() {
        return currentApplyAmount;
    }

    public void setCurrentApplyAmount(String currentApplyAmount) {
        this.currentApplyAmount = currentApplyAmount;
    }
}
