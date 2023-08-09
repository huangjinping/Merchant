package com.hdfex.merchantqrshow.bean.salesman.customer;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * Created by harrishuang on 16/10/14.
 */

public class CustomerResponse extends Response {

    private List<CustomerResult> result;

    public List<CustomerResult> getResult() {
        return result;
    }

    public void setResult(List<CustomerResult> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CustomerResponse{" +
                "result=" + result +
                '}';
    }
}
