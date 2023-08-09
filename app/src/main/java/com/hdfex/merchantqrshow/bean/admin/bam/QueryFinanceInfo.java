package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/12/19.
 * email : huangjinping@hdfex.com
 */

public class QueryFinanceInfo extends BaseBean {

    private String curRedPacketAmt;
    private String totalRedPacketAmt ;
    private String  totalAmount;
    private String alipayNo;


    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }

    public String getCurRedPacketAmt() {
        return curRedPacketAmt;
    }

    public void setCurRedPacketAmt(String curRedPacketAmt) {
        this.curRedPacketAmt = curRedPacketAmt;
    }

    public String getTotalRedPacketAmt() {
        return totalRedPacketAmt;
    }

    public void setTotalRedPacketAmt(String totalRedPacketAmt) {
        this.totalRedPacketAmt = totalRedPacketAmt;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
