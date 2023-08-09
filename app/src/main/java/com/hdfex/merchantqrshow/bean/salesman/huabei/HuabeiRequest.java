package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.BaseBean;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/13.
 * email : huangjinping@hdfex.com
 */

public class HuabeiRequest extends BaseBean {

    private List<Installment> commodityCaseList;
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<Installment> getCommodityCaseList() {
        return commodityCaseList;
    }

    public void setCommodityCaseList(List<Installment> commodityCaseList) {
        this.commodityCaseList = commodityCaseList;
    }
}
