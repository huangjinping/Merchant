package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2019/11/12.
 * email : huangjinping1000@163.com
 */
public class CaseWithCase extends BaseBean {


    /**
     * caseId : 6562987902895129601
     * duration : 18
     * caseName : 918期
     * discount : 0
     * capitalLogo : https://daiyutech.com/hd-merchant-web/mobile/picture/down?file=/capital_logo/91online.png
     * capitalName : 91旺财
     * isNeedContract : 0
     * capitalId : 91online
     * profitSource : 客户付息-宽限期6
     */

    private String caseId;
    private String duration;
    private String caseName;
    private String discount;
    private String capitalLogo;
    private String capitalName;
    private String isNeedContract;
    private String capitalId;
    private String profitSource;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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

    public String getCapitalId() {
        return capitalId;
    }

    public void setCapitalId(String capitalId) {
        this.capitalId = capitalId;
    }

    public String getProfitSource() {
        return profitSource;
    }

    public void setProfitSource(String profitSource) {
        this.profitSource = profitSource;
    }
}
