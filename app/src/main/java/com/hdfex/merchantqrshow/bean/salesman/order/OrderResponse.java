package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 16/7/5.
 */
public class OrderResponse  extends Response{

    private  Order result;

    public Order getResult() {
        return result;
    }

    public void setResult(Order result) {
        this.result = result;
    }

    @Override
    public String
    toString() {
        return "OrderResponse{" +
                "result=" + result +
                '}';
    }
}
