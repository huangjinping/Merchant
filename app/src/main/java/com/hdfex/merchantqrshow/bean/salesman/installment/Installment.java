package com.hdfex.merchantqrshow.bean.salesman.installment;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by maidou521 on 2016/7/11.
 */
public class Installment extends BaseBean {
    public static final String NEED_NO = "0";
    public static final String NEED_YES = "1";

    public static final String STATUS_YES = "1";
    public static final String STATUS_NO = "0";

    public static final String  CAPITALID_ALIPAY="alipay";

    public static final String DISCOUNT_TYPE_YES="1";
    public static final String DISCOUNT_TYPE_NO="0";


    private String duration;
    private String discount;
    private String caseId;
    private String capitalLogo;
    private String capitalName;
    private String isNeedContract;
    private String capitalId;


    private String status;

    private String profitSource;

    public String getProfitSource() {
        return profitSource;
    }

    public void setProfitSource(String profitSource) {
        this.profitSource = profitSource;
    }

    public String getCapitalId() {
        return capitalId;
    }

    public void setCapitalId(String capitalId) {
        this.capitalId = capitalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCapitalLogo() {
        return capitalLogo;
    }

    public void setCapitalLogo(String capitalLogo) {
        this.capitalLogo = capitalLogo;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public String getIsNeedContract() {
        return isNeedContract;
    }

    public void setIsNeedContract(String isNeedContract) {
        this.isNeedContract = isNeedContract;
    }


}
