package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2018/7/26.
 * email : huangjinping@hdfex.com
 */
public class MonthlyOrderBeanResponse extends Response {

    private List<MonthlyOrderBean> result;

    public List<MonthlyOrderBean> getResult() {
        return result;
    }

    public void setResult(List<MonthlyOrderBean> result) {
        this.result = result;
    }
}
