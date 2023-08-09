package com.hdfex.merchantqrshow.bean.salesman.installment;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by maidou521 on 2016/7/11.
 */
public class ShoppingList extends BaseBean {
    private String commodityId;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "commodityId='" + commodityId + '\'' +
                '}';
    }
}
