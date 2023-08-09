package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.BaseBean;
import com.hdfex.merchantqrshow.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created by harrishuang on 16/7/8.
 */
public class OrderDetails extends BaseBean {

    public static final String SEND_FLAG_NO = "0";
    /***
     * 需要发送数据
     */
    public static final String SEND_FALG_YES = "1";


    public static final String TYPE_ZUFANG = "0";
    public static final String TYPE_OTHER = "1";


    private String type;
    private String businessId;
    private String businessName;
    private String pic;
    private String orderNo;
    private String time;
    private String totalPrice;
    private String periodAmount;
    private Integer duration;
    private String downpayment;
    private String applyAmount;
    private String status;
    private String feedback;
    private String bankName;
    private String cardNo;//后四位
    private String phone;
    private String idName;
    private String sendFlag;

    private String preAmt;
    private String dueDate;
    private String dueDay;

    private String repayStatus;
    private String reason;
    private String overdueDays;

    private String startDate;
    private String endDate;
    private String downpaymentName;

    private String statusReason;

    private String gracePeriodAmount;
    private String gracePeriod;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public static String getSendFlagNo() {
        return SEND_FLAG_NO;
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

    public String getPreAmt() {
        return preAmt;
    }

    public void setPreAmt(String preAmt) {
        this.preAmt = preAmt;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDay() {
        return dueDay;
    }

    public void setDueDay(String dueDay) {
        this.dueDay = dueDay;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private ArrayList<OrderCommodityDetail> orderCommoditys;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public ArrayList<OrderCommodityDetail> getOrderCommoditys() {
        return orderCommoditys;
    }

    public void setOrderCommoditys(ArrayList<OrderCommodityDetail> orderCommoditys) {
        this.orderCommoditys = orderCommoditys;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "businessId='" + businessId + '\'' +
                ", businessName='" + businessName + '\'' +
                ", pic='" + pic + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", time='" + time + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", periodAmount='" + periodAmount + '\'' +
                ", duration=" + duration +
                ", downpayment='" + downpayment + '\'' +
                ", applyAmount='" + applyAmount + '\'' +
                ", status='" + status + '\'' +
                ", feedback='" + feedback + '\'' +
                ", bankName='" + bankName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", phone='" + phone + '\'' +
                ", idName='" + idName + '\'' +
                ", sendFlag='" + sendFlag + '\'' +
                ", orderCommoditys=" + orderCommoditys +
                '}';
    }
}
