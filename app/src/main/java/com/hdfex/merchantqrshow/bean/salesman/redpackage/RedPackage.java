package com.hdfex.merchantqrshow.bean.salesman.redpackage;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class RedPackage extends BaseBean {

    private String redPacketAmt;
    private String tradeType;
    private String operDate;
    private String remark;
    /**
     * String	0-	未提现
     1-	已提现
     */
    public static final String WITH_DRAW_FLAG_NO="0";
    public static final String WITH_DRAW_FLAG_YES="1";



    private String withdrawFlag;
    /**
     * 红包打开标识
     2-	未打开
     3-	已打开
     */
    private String openFlag;


    private String redPackageId;



    public String getRedPacketAmt() {
        return redPacketAmt;
    }

    public void setRedPacketAmt(String redPacketAmt) {
        this.redPacketAmt = redPacketAmt;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWithdrawFlag() {
        return withdrawFlag;
    }

    public void setWithdrawFlag(String withdrawFlag) {
        this.withdrawFlag = withdrawFlag;
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public String getRedPackageId() {
        return redPackageId;
    }

    public void setRedPackageId(String redPackageId) {
        this.redPackageId = redPackageId;
    }

    @Override
    public String toString() {
        return "RedPackage{" +
                "redPacketAmt='" + redPacketAmt + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", operDate='" + operDate + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
