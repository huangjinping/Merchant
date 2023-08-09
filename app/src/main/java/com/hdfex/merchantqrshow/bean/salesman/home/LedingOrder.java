package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2019-11-27.
 * email : huangjinping1000@163.com
 */
public class LedingOrder extends BaseBean {
    private String curCaseId;
    private String idName;
    private String commodityIds;
    private String oldPackageId;
    private String changeId;
    private String applyDuration;
    private String status;
    private String commodityName;
    private String applyAmount;
    private String applyId;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getCurCaseId() {
        return curCaseId;
    }

    public void setCurCaseId(String curCaseId) {
        this.curCaseId = curCaseId;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getCommodityIds() {
        return commodityIds;
    }

    public void setCommodityIds(String commodityIds) {
        this.commodityIds = commodityIds;
    }

    public String getOldPackageId() {
        return oldPackageId;
    }

    public void setOldPackageId(String oldPackageId) {
        this.oldPackageId = oldPackageId;
    }

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    public String getApplyDuration() {
        return applyDuration;
    }

    public void setApplyDuration(String applyDuration) {
        this.applyDuration = applyDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }
}
