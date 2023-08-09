package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * Created by maidou521 on 2016/7/15.
 */
public class OrderListResponse extends Response {
    private List<OrderItem> result;

    public List<OrderItem> getResult() {
        return result;
    }

    public void setResult(List<OrderItem> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "OrderListResponse{" +
                "result=" + result +
                '}';
    }
}
