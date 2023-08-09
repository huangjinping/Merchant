package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by maidou521 on 2016/7/18.
 */
public class OrderDetailsResponse extends Response {
    public OrderDetails result;

    public OrderDetails getResult() {
        return result;
    }

    public void setResult(OrderDetails result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "OrderDetailsResponse{" +
                "result='" + result + '\'' +
                '}';
    }
}
