package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/8/4.
 * email : huangjinping@hdfex.com
 */

public class PayInfoItem extends BaseBean {
    private String payStatus;
    private String payStatusName;
    private String failReason;
    private String payDate;
    private String payWay;
    private String payAmount;
    private String payChannel;


    public static  String PAYWAY_HUABEI = "00";
    public static String PAYWAY_APLIY = "01";

    public static String PAYSTATUS_YES = "00";
    public static String PAYSTATUS_NO = "01";



    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayAmount() {

        return payAmount;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }
}
