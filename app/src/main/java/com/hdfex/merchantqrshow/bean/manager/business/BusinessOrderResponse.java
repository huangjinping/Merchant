package com.hdfex.merchantqrshow.bean.manager.business;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class BusinessOrderResponse extends Response {


    private List<BusinessOrder>  result;

    public List<BusinessOrder> getResult() {
        return result;
    }

    public void setResult(List<BusinessOrder> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BusinessOrderResponse{" +
                "result=" + result +
                '}';
    }
}
