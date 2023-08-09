package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.Map;

/**
 * author Created by harrishuang on 2017/6/22.
 * email : huangjinping@hdfex.com
 */

public class HubeiParam extends BaseBean {

    private Map<String, String> params;
    private AlipayOrder order;

    public AlipayOrder getOrder() {
        return order;
    }

    public void setOrder(AlipayOrder order) {
        this.order = order;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
