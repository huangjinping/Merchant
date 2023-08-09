package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2017/8/4.
 * email : huangjinping@hdfex.com
 */

public class PayOrderQueryResult extends BaseBean {

    private String commodityName;
    private String imei;
    private String totalAmount;
    private String custPhone;
    private String custName;
    private String custIdNo;
    private String commodityBarCode;

    public static  final String PAY_FLAG_YES="1";
    public static  final String PAY_FLAG_NO="0";

    private String payFlag;



    public String getCommodityBarCode() {
        return commodityBarCode;
    }

    public void setCommodityBarCode(String commodityBarCode) {
        this.commodityBarCode = commodityBarCode;
    }

    private List<PayInfoItem> payInfoList;

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustIdNo() {
        return custIdNo;
    }

    public void setCustIdNo(String custIdNo) {
        this.custIdNo = custIdNo;
    }

    public List<PayInfoItem> getPayInfoList() {
        return payInfoList;
    }

    public void setPayInfoList(List<PayInfoItem> payInfoList) {
        this.payInfoList = payInfoList;
    }

    public String getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }
}
