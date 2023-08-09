package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2018/7/26.
 * email : huangjinping@hdfex.com
 */
public class OrderMonthlyDetailResponse  extends Response{
    private OrderMonthlyDetail result;

    public OrderMonthlyDetail getResult() {
        return result;
    }

    public void setResult(OrderMonthlyDetail result) {
        this.result = result;
    }
}
