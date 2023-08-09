package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by maidou521 on 2016/7/12.
 */
public class Prouct extends BaseBean {
    private String commodityId;
    private String commodityCount;

    private String commodityName;

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityCount() {
        return commodityCount;
    }

    public void setCommodityCount(String commodityCount) {
        this.commodityCount = commodityCount;
    }

    @Override
    public String toString() {
        return "Prouct{" +
                "commodityId='" + commodityId + '\'' +
                ", commodityCount='" + commodityCount + '\'' +
                ", commodityName='" + commodityName + '\'' +
                '}';
    }
}
