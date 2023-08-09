package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2019/11/12.
 * email : huangjinping1000@163.com
 */
public class OrderChange extends BaseBean {


    /**
     * curCaseId : 6402811380327515137
     * idName : 黄金平
     * commodityIds : 6402093485142311937
     * oldPackageId : 6594850808578180097
     * changeId : 2019111215080001
     * applyDuration : 12
     * status : 取消
     * commodityName : 晋商测试商户-教育
     * applyAmount : 4000.00
     */

    private String curCaseId;
    private String idName;
    private String commodityIds;
    private String oldPackageId;
    private String changeId;
    private String applyDuration;
    private String status;
    private String commodityName;
    private String applyAmount;

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
